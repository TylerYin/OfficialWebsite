package com.fwzs.master.modules.fwzs.web;

import com.fwzs.master.common.config.Global;
import com.fwzs.master.common.persistence.Page;
import com.fwzs.master.common.utils.ModelUtils;
import com.fwzs.master.common.utils.StringUtils;
import com.fwzs.master.common.web.BaseController;
import com.fwzs.master.modules.fwzs.entity.BsProduct;
import com.fwzs.master.modules.fwzs.entity.FwmSpec;
import com.fwzs.master.modules.fwzs.entity.RealtimeStockLevel;
import com.fwzs.master.modules.fwzs.entity.Warehouse;
import com.fwzs.master.modules.fwzs.service.BsProductService;
import com.fwzs.master.modules.fwzs.service.FwmSpecService;
import com.fwzs.master.modules.fwzs.service.RealtimeStockLevelService;
import com.fwzs.master.modules.sys.utils.UserUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Tyler Yin
 * @create 2017-11-20 14:06
 * @description 实时库存查询Controller
 **/
@Controller
@RequestMapping(value = "${adminPath}/fwzs/stocklevelquery")
public class RealtimeStockLevelController extends BaseController {
    @Autowired
    private FwmSpecService fwmSpecService;

    @Autowired
    private BsProductService bsProductService;
    @Autowired
    private RealtimeStockLevelService realtimeStockLevelService;

    @ModelAttribute("realtimeStockLevel")
    public RealtimeStockLevel get(@RequestParam(required = false) String id) {
        if (StringUtils.isNotBlank(id)) {
            return realtimeStockLevelService.get(id);
        } else {
            return new RealtimeStockLevel();
        }
    }

    /**
     * 获取企业库存数据
     *
     * @param realtimeStockLevel
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("fwzs:dealer:view")
    @RequestMapping(value = "enterprise")
    public String enterpriseStockLevelList(RealtimeStockLevel realtimeStockLevel, HttpServletRequest request,
                       HttpServletResponse response, Model model) {
        realtimeStockLevel.setCurrentUser(UserUtils.getUser());
        model.addAttribute("bsProducts", bsProductService.findList(new BsProduct()));
        model.addAttribute("fwmSpecs", fwmSpecService.findList(new FwmSpec()));

        boolean isDealer = UserUtils.isDealer();
        model.addAttribute("isDealer", isDealer);

        final String warehouseName = ModelUtils.getValueFromModel(model, "warehouseName");
        if (StringUtils.isNotEmpty(warehouseName)) {
            final Warehouse warehouse = new Warehouse();
            warehouse.setName(warehouseName);
            realtimeStockLevel.setWarehouse(warehouse);
        }

        final String productName = ModelUtils.getValueFromModel(model, "productName");
        if (StringUtils.isNotEmpty(productName)) {
            final BsProduct bsProduct = new BsProduct();
            bsProduct.setProdName(productName);
            realtimeStockLevel.setBsProduct(bsProduct);
        }

        Page<RealtimeStockLevel> page = realtimeStockLevelService.findEnterpriseList(new Page<RealtimeStockLevel>(
                request, response), realtimeStockLevel);
        model.addAttribute("page", page);

        return "modules/fwzs/enterpriseStockLevelList";
    }

    /**
     * 获取经销商库存数据
     *
     * @param realtimeStockLevel
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("fwzs:dealer:view")
    @RequestMapping(value = "dealer")
    public String dealerStockLevelList(RealtimeStockLevel realtimeStockLevel, HttpServletRequest request,
                       HttpServletResponse response, Model model) {
        realtimeStockLevel.setCurrentUser(UserUtils.getUser());
        model.addAttribute("bsProducts", bsProductService.findList(new BsProduct()));

        boolean isDealer = UserUtils.isDealer();
        model.addAttribute("isDealer", isDealer);

        final String productName = ModelUtils.getValueFromModel(model, "productName");
        if (StringUtils.isNotEmpty(productName)) {
            final BsProduct bsProduct = new BsProduct();
            bsProduct.setProdName(productName);
            realtimeStockLevel.setBsProduct(bsProduct);
        }

        Page<RealtimeStockLevel> page = realtimeStockLevelService.findDealerList(new Page<RealtimeStockLevel>(
                request, response), realtimeStockLevel);
        model.addAttribute("page", page);
        return "modules/fwzs/dealerStockLevelList";
    }

    @RequiresPermissions("fwzs:dealer:view")
    @RequestMapping(value = "form")
    public String form(RealtimeStockLevel realtimeStockLevel, Model model) {
        model.addAttribute("realtimeStockLevel", realtimeStockLevel);
        return "modules/fwzs/realtimeStockLevelForm";
    }

    @RequiresPermissions("fwzs:dealer:edit")
    @RequestMapping(value = "save")
    public String save(RealtimeStockLevel realtimeStockLevel, Model model,
                       RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, realtimeStockLevel)) {
            return form(realtimeStockLevel, model);
        }

        realtimeStockLevel.setCompany(UserUtils.getOfficeBelongToUser());
        realtimeStockLevelService.save(realtimeStockLevel);
        addMessage(redirectAttributes, "保存库存记录表成功");
        return "redirect:" + Global.getAdminPath() + "/fwzs/stocklevelquery/list/?repage";
    }

    @RequiresPermissions("fwzs:dealer:edit")
    @RequestMapping(value = "delete")
    public String delete(RealtimeStockLevel realtimeStockLevel, final HttpServletRequest request,
                         RedirectAttributes redirectAttributes) {
        realtimeStockLevelService.delete(realtimeStockLevel);
        addMessage(redirectAttributes, "删除库存记录表成功");

        redirectAttributes.addFlashAttribute("warehouseName", request.getParameter("warehouse.name"));
        redirectAttributes.addFlashAttribute("productName", request.getParameter("bsProduct.prodName"));

        return "redirect:" + Global.getAdminPath() + "/fwzs/stocklevelquery/list/?repage";
    }
}
