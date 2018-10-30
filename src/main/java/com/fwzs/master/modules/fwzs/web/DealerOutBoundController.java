package com.fwzs.master.modules.fwzs.web;

import com.fwzs.master.common.config.Global;
import com.fwzs.master.common.persistence.Page;
import com.fwzs.master.common.utils.IdGen;
import com.fwzs.master.common.utils.StringUtils;
import com.fwzs.master.common.web.BaseController;
import com.fwzs.master.modules.fwzs.entity.BsProduct;
import com.fwzs.master.modules.fwzs.entity.DealerBound;
import com.fwzs.master.modules.fwzs.entity.PdaUser;
import com.fwzs.master.modules.fwzs.entity.Qrcode2BoxcodeMapping;
import com.fwzs.master.modules.fwzs.service.BsProductService;
import com.fwzs.master.modules.fwzs.service.DealerBoundService;
import com.fwzs.master.modules.fwzs.service.PdaUserService;
import com.fwzs.master.modules.sys.utils.UserUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Tyler Yin
 * @create 2017-11-22 16:35
 * @description 出库Controller
 **/
@Controller
@RequestMapping(value = "${adminPath}/fwzs/dealerOutBound")
public class DealerOutBoundController extends BaseController {
    @Autowired
    private BsProductService bsProductService;
    @Autowired
    private DealerBoundService dealerBoundService;
    @Autowired
    PdaUserService pdaUserService;

    @ModelAttribute
    public DealerBound get(@RequestParam(required = false) String id) {
        DealerBound entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = dealerBoundService.get(id);
        }
        if (entity == null) {
            entity = new DealerBound();
        }
        return entity;
    }

    @RequiresPermissions("fwzs:dealer:view")
    @RequestMapping(value = {"list", ""})
    public String list(DealerBound dealerBound, HttpServletRequest request, HttpServletResponse response, Model model) {
        if (StringUtils.isBlank(request.getParameter("status"))) {
            dealerBound.setStatus(Global.BOUNDING);
        } else {
            dealerBound.setStatus(request.getParameter("status"));
        }

        model.addAttribute("isDealer", UserUtils.isDealer());
        Page<DealerBound> page = dealerBoundService.findPage(new Page<DealerBound>(request, response), dealerBound);
        model.addAttribute("page", page);
        model.addAttribute("delivery", new DealerBound());
        model.addAttribute("bsProducts", bsProductService.findList(new BsProduct()));
        return "modules/fwzs/dealerBoundList";
    }

    @RequiresPermissions("fwzs:dealer:view")
    @RequestMapping(value = "form")
    public String form(DealerBound dealerBound, Model model) {
        final List<BsProduct> bsProducts = bsProductService.findList(new BsProduct());

        PdaUser pdaUser = new PdaUser();
        pdaUser.setCompany(UserUtils.getUser().getCompany());
        pdaUser.setDealer(UserUtils.findCurrentDealer());
        final List<PdaUser> pdaUsers = pdaUserService.findPdaByUser(pdaUser);

        if (StringUtils.isBlank(dealerBound.getId())) {
            dealerBound.setBoundNo(IdGen.genProdFileSerialNum(3, "JXSCKD"));
            final List<BsProduct> bsProductList = new ArrayList<>();
            final BsProduct bsProduct = new BsProduct();
            bsProductList.add(bsProduct);
            dealerBound.setBsProductList(bsProductList);
            model.addAttribute("dealerBound", dealerBound);
        }
        model.addAttribute("pdaUsers", pdaUsers);
        model.addAttribute("bsProducts", bsProducts);
        return "modules/fwzs/dealerBoundForm";
    }

    @RequiresPermissions("fwzs:dealer:edit")
    @RequestMapping(value = "save")
    public String save(DealerBound dealerBound, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, dealerBound)) {
            return form(dealerBound, model);
        }
        String status = dealerBoundService.saveDealerOutBound(dealerBound);
        if (Global.BOUND_SUCCESS.equals(status)) {
            addMessage(redirectAttributes, "保存出库计划成功");
        } else if (Global.BOUND_OUTOFSTOCK.equals(status)) {
            addMessage(redirectAttributes, "保存出库计划失败，库存数量不足");
        } else {
            addMessage(redirectAttributes, "保存出库计划失败，库存不存在");
        }
        return "redirect:" + Global.getAdminPath() + "/fwzs/dealerOutBound/?repage";
    }

    /**
     * 根据出库查询防伪码
     *
     * @param dealerBound
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "qrcodeDetailList")
    public String findQrcodeById(final DealerBound dealerBound, HttpServletRequest request, HttpServletResponse response, Model model) {
        final Qrcode2BoxcodeMapping qrcode2BoxcodeMapping = new Qrcode2BoxcodeMapping();
        qrcode2BoxcodeMapping.setBoundId(dealerBound.getId());
        if (null != dealerBound.getBsProduct() && StringUtils.isNotBlank(dealerBound.getBsProduct().getId())) {
            qrcode2BoxcodeMapping.setProdId(dealerBound.getBsProduct().getId());
            model.addAttribute("bsProduct", bsProductService.get(dealerBound.getBsProduct().getId()));
        }

        Page<Qrcode2BoxcodeMapping> page = dealerBoundService.findBoxcodePageByDealerBoundId(new Page<Qrcode2BoxcodeMapping>(request,
                response), qrcode2BoxcodeMapping);
        model.addAttribute("page", page);
        return "modules/fwzs/qrDealerBoundCodeDetailList";
    }

    /**
     * 根据出库查询防伪码并导出成Excel
     *
     * @param dealerBound
     * @param model
     * @return
     */
    @RequestMapping(value = "exportQrcode")
    public String exportQrcode(final DealerBound dealerBound, Model model) {
        model.addAttribute("qrCodeList", dealerBoundService.findBoxcodePageByDealerBoundId(dealerBound));
        model.addAttribute("boundNo", dealerBound.getBoundNo());
        if (null != dealerBound.getBsProduct() && StringUtils.isNotBlank(dealerBound.getBsProduct().getId())) {
            model.addAttribute("productName", bsProductService.get(dealerBound.getBsProduct().getId()).getProdName());
        }
        return "qrCodeExcel";
    }

    @ResponseBody
    @RequestMapping(value = "delete")
    public boolean delete(final String dealerBoundIds, RedirectAttributes redirectAttributes) {
        addMessage(redirectAttributes, "删除出库计划成功");
        return dealerBoundService.deleteDealerBound(dealerBoundIds);
    }

    /**
     * 更新发货单状态为已发货
     *
     * @param dealerBoundId
     */
    @RequestMapping(value = "updateStatus")
    public String updateStatus(final String dealerBoundId, final String status, Model model, HttpServletRequest request) {
        DealerBound dealerBound = new DealerBound();
        dealerBound.setShipNo(request.getParameter("shipNo"));
        dealerBound.setShipName(request.getParameter("shipName"));
        dealerBound.setId(dealerBoundId);
        dealerBound.setStatus(Global.SHIPPING);
        dealerBoundService.updateStatus(dealerBound);
        return "redirect:" + Global.getAdminPath() + "/fwzs/dealerOutBound/?repage&status=" + status;
    }

    /**
     * 更新物流数据
     *
     */
    @RequestMapping(value = "deliveryGoods", method = RequestMethod.POST)
    public String deliveryGoods(DealerBound delivery, Model model, HttpServletRequest request) {
        DealerBound dealerBound = new DealerBound();
        dealerBound.setId(delivery.getId());
        dealerBound.setShipNo(request.getParameter("shipNo"));
        dealerBound.setShipName(request.getParameter("shipName"));
        dealerBound.setStatus(Global.SHIPPING);
        dealerBoundService.updateStatus(dealerBound);
        return "redirect:" + Global.getAdminPath() + "/fwzs/dealerOutBound/?repage&status=0";
    }

    /**
     * 验证产品是否允许出库
     *
     * @param dealerBoundId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "validateRealNumber")
    public boolean validateRealNumber(final String dealerBoundId) {
        DealerBound dealerBound = new DealerBound();
        dealerBound.setId(dealerBoundId);
        return dealerBoundService.validateRealNumber(dealerBound);
    }

    /**
     * 根据产品和仓库获取最新库存
     * 若找到则返回最新库存，若没有找到则返回0
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getLatestStockLevel", method = RequestMethod.POST)
    public long getLatestStockLevel(HttpServletRequest request) {
        return dealerBoundService.getLatestStockLevel(request.getParameter("prodId"));
    }
}
