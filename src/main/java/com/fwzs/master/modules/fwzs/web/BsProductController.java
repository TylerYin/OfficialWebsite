/**
 *
 */
package com.fwzs.master.modules.fwzs.web;

import com.fwzs.master.common.config.Global;
import com.fwzs.master.common.persistence.Page;
import com.fwzs.master.common.utils.IdGen;
import com.fwzs.master.common.utils.StringUtils;
import com.fwzs.master.common.web.BaseController;
import com.fwzs.master.modules.fwzs.entity.BsProduct;
import com.fwzs.master.modules.fwzs.entity.FwmSpec;
import com.fwzs.master.modules.fwzs.entity.ProductSpec;
import com.fwzs.master.modules.fwzs.service.BsProductService;
import com.fwzs.master.modules.fwzs.service.FwmSpecService;
import com.fwzs.master.modules.sys.entity.Office;
import com.fwzs.master.modules.sys.utils.DictUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 防伪查询记录表Controller
 * 
 * @author ly
 * @version 2017-09-30
 */
@Controller
@RequestMapping(value = "${adminPath}/fwzs/bsProduct")
public class BsProductController extends BaseController {
    @Autowired
    private FwmSpecService fwmSpecService;

    @Autowired
    private BsProductService bsProductService;

    private final String PROD_UNIT = "prod_unit";

    @ModelAttribute
    public BsProduct get(@RequestParam(required = false) String id) {
        BsProduct entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = bsProductService.get(id);
        }
        if (entity == null) {
            entity = new BsProduct();
        }
        return entity;
    }

    @RequiresPermissions("fwzs:bsProduct:view")
    @RequestMapping(value = {"list", ""})
    public String list(BsProduct bsProduct, HttpServletRequest request,
                       HttpServletResponse response, Model model) {
        Page<BsProduct> page = bsProductService.findPage(new Page<BsProduct>(
                request, response), bsProduct);
        model.addAttribute("fwmSpecs", fwmSpecService.findList(new FwmSpec()));
        model.addAttribute("page", page);
        return "modules/fwzs/bsProductList";
    }

    @RequestMapping(value = "findProducts")
    public String findProducts(BsProduct bsProduct, HttpServletRequest request,
                               HttpServletResponse response, Model model) {
        String prodName = request.getParameter("prodName");
        String prodSpec = request.getParameter("prodSpec");

        List<BsProduct> bsProducts;
        if (StringUtils.isBlank(prodName) && StringUtils.isBlank(prodSpec)) {
            bsProducts = bsProductService.findList(bsProduct);
        } else {
            if (StringUtils.isNotBlank(prodName)) {
                bsProduct.setProdName(prodName);
            }

            if (StringUtils.isNotBlank(prodSpec)) {
                FwmSpec fwmSpec = new FwmSpec();
                fwmSpec.setSpecDesc(prodSpec);
                bsProduct.setProdSpec(fwmSpec);
            }

            bsProducts = bsProductService.findProducts(bsProduct);
        }
        model.addAttribute("bsProducts", bsProducts);
        return "modules/fwzs/productsList";
    }

    @RequiresPermissions("fwzs:bsProduct:view")
    @RequestMapping(value = "form")
    public String form(BsProduct bsProduct, Model model) {
        model.addAttribute("bsProduct", bsProduct);
        if (null != bsProduct) {
            if (StringUtils.isBlank(bsProduct.getId())) {
                bsProduct.setProdNo(IdGen.getFixLenthString(12));
            }
        }

        getFwmSpecs(model);
        return "modules/fwzs/bsProductForm";
    }

    @RequiresPermissions("fwzs:bsProduct:edit")
    @RequestMapping(value = "save")
    public String save(BsProduct bsProduct, Model model,
                       RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, bsProduct)) {
            return form(bsProduct, model);
        } else if (duplicateCount(bsProduct)) {
            this.addMessage(redirectAttributes, new String[] { "该产品已经存在，保存产品失败！" });
            return "redirect:" + Global.getAdminPath() + "/fwzs/bsProduct/?repage";
        }
        bsProductService.save(bsProduct);
        addMessage(redirectAttributes, "保存产品记录表成功");
        return "redirect:" + Global.getAdminPath() + "/fwzs/bsProduct/?repage";
    }

    @RequiresPermissions("fwzs:bsProduct:edit")
    @RequestMapping(value = "multiSave")
    public String multiSave(BsProduct bsProduct, Model model,
                            RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, bsProduct)) {
            return form(bsProduct, model);
        }

        if (bsProductService.saveProductForMultiSpec(bsProduct)) {
            addMessage(redirectAttributes, "保存产品记录表成功");
        } else {
            addMessage(redirectAttributes, "保存产品记录表失败");
        }
        return "redirect:" + Global.getAdminPath() + "/fwzs/bsProduct/?repage";
    }

    @RequiresPermissions("fwzs:bsProduct:view")
    @RequestMapping(value = "multiProductForm")
    public String multiProductForm(BsProduct bsProduct, Model model) {
        if (null != bsProduct) {
            if (StringUtils.isBlank(bsProduct.getId())) {
                final List<ProductSpec> productSpecs = new ArrayList<>();
                final ProductSpec productSpec = new ProductSpec();
                productSpecs.add(productSpec);
                bsProduct.setProductSpecs(productSpecs);
                productSpec.setProdNo(IdGen.getFixLenthString(12));
            }
        }

        getFwmSpecs(model);
        getProductUnits(model);
        model.addAttribute("bsProduct", bsProduct);
        return "modules/fwzs/multiProductForm";
    }

    @ResponseBody
    @RequestMapping("generateProdNo")
    public String generateProdNo() {
        return IdGen.getFixLenthString(12);
    }

    @RequiresPermissions("fwzs:bsProduct:edit")
    @RequestMapping(value = "delete")
    public String delete(BsProduct bsProduct,
                         RedirectAttributes redirectAttributes) {
        bsProductService.delete(bsProduct);
        addMessage(redirectAttributes, "删除产品记录表成功");
        return "redirect:" + Global.getAdminPath() + "/fwzs/bsProduct/?repage";
    }

    /**
     * 根据字典类型获取字典列表
     *
     * @param
     */
    private void getProductUnits(final Model model) {
        model.addAttribute("prodUnits", DictUtils.getDictList(PROD_UNIT));
    }

    /**
     * 获取生产规格列表
     *
     * @param model
     */
    private void getFwmSpecs(final Model model) {
        model.addAttribute("fwmSpecs", fwmSpecService.findList(new FwmSpec()));
    }

    /**
     * 判断是否添加重复产品
     *
     * @param bsProduct
     * @return
     */
    private boolean duplicateCount(BsProduct bsProduct) {
        boolean isDuplicate = false;
        String oldId = bsProduct.getId();
        List<String> ids = bsProductService.duplicateRowId(bsProduct);
        if (StringUtils.isEmpty(oldId)) {
            if (CollectionUtils.isNotEmpty(ids)) {
                isDuplicate = true;
            }
        } else {
            if (CollectionUtils.isNotEmpty(ids)) {
                if (!ids.contains(oldId)) {
                    isDuplicate = true;
                }
            }
        }
        return isDuplicate;
    }

    @ResponseBody
    @RequestMapping(value = "isDuplicateProduct")
    public boolean isDuplicateProduct(@RequestParam String regCode, @RequestParam String companyId, @RequestParam String prodName,
                                      @RequestParam String prodSpec, @RequestParam String prodUnit) {
        BsProduct bsProduct = new BsProduct();
        bsProduct.setProdUnit(prodUnit);
        bsProduct.setProdName(prodName);
        bsProduct.setRegCode(regCode);
        Office company = new Office();
        company.setId(companyId);
        bsProduct.setCompany(company);

        FwmSpec spec = new FwmSpec();
        spec.setId(prodSpec);
        bsProduct.setProdSpec(spec);

        return duplicateCount(bsProduct);
    }

    @RequiresPermissions("fwzs:bsProduct:view")
    @RequestMapping(value = "updateExtendAttributesForm")
    public String updateExtendAttributesForm(BsProduct bsProduct, Model model) {
        model.addAttribute("bsProduct", bsProduct);
        return "modules/fwzs/updateExtendAttributesForm";
    }

    @RequiresPermissions("fwzs:bsProduct:edit")
    @RequestMapping(value = "updateExtendAttributes")
    public String updateExtendAttributes(BsProduct bsProduct, Model model,
            RedirectAttributes redirectAttributes) {
        if (StringUtils.isNotBlank(bsProduct.getProdParameter())) {
            bsProduct.setProdParameter(StringEscapeUtils.unescapeHtml4(bsProduct.getProdParameter()));
        }
        if (StringUtils.isNotBlank(bsProduct.getProdFeature())) {
            bsProduct.setProdFeature(StringEscapeUtils.unescapeHtml4(bsProduct.getProdFeature()));
        }
        if (StringUtils.isNotBlank(bsProduct.getProdConsideration())) {
            bsProduct.setProdConsideration(StringEscapeUtils.unescapeHtml4(bsProduct.getProdConsideration()));
        }

        bsProductService.updateExtendAttributes(bsProduct);
        addMessage(redirectAttributes, "更新产品记录表成功");
        return "redirect:" + Global.getAdminPath() + "/fwzs/bsProduct/?repage";
    }
}