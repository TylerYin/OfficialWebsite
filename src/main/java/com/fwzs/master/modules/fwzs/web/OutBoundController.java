package com.fwzs.master.modules.fwzs.web;

import com.fwzs.master.common.config.Global;
import com.fwzs.master.common.persistence.Page;
import com.fwzs.master.common.utils.IdGen;
import com.fwzs.master.common.utils.StringUtils;
import com.fwzs.master.common.web.BaseController;
import com.fwzs.master.modules.fwzs.entity.*;
import com.fwzs.master.modules.fwzs.service.BsProductService;
import com.fwzs.master.modules.fwzs.service.FwmSpecService;
import com.fwzs.master.modules.fwzs.service.OutBoundService;
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
@RequestMapping(value = "${adminPath}/fwzs/outBound")
public class OutBoundController extends BaseController {
    @Autowired
    private BsProductService bsProductService;
    @Autowired
    private OutBoundService outBoundService;
    @Autowired
    PdaUserService pdaUserService;
    @Autowired
    private FwmSpecService fwmSpecService;

    @ModelAttribute
    public OutBound get(@RequestParam(required = false) String id) {
        OutBound entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = outBoundService.get(id);
        }
        if (entity == null) {
            entity = new OutBound();
        }
        return entity;
    }

    @RequiresPermissions("fwzs:dealer:view")
    @RequestMapping(value = {"list", ""})
    public String list(OutBound outBound, HttpServletRequest request, HttpServletResponse response, Model model) {
        if (StringUtils.isBlank(request.getParameter("status"))) {
            outBound.setStatus(Global.BOUNDING);
        } else {
            outBound.setStatus(request.getParameter("status"));
        }

        Page<OutBound> page = outBoundService.findPage(new Page<OutBound>(request, response), outBound);
        model.addAttribute("page", page);
        model.addAttribute("delivery", new OutBound());
        model.addAttribute("fwmSpecs", fwmSpecService.findList(new FwmSpec()));
        model.addAttribute("bsProducts", bsProductService.findList(new BsProduct()));
        return "modules/fwzs/outBoundList";
    }

    @RequiresPermissions("fwzs:dealer:view")
    @RequestMapping(value = "form")
    public String form(OutBound outBound, Model model) {
        final List<BsProduct> bsProducts = bsProductService.findList(new BsProduct());

        PdaUser pdaUser = new PdaUser();
        pdaUser.setCompany(UserUtils.getUser().getCompany());
        final List<PdaUser> pdaUsers = pdaUserService.findPdaByUser(pdaUser);

        if (StringUtils.isBlank(outBound.getId())) {
            outBound.setOutBoundNo(IdGen.genProdFileSerialNum(3, "CKD"));
            final List<BsProduct> bsProductList = new ArrayList<>();
            final BsProduct bsProduct = new BsProduct();
            bsProductList.add(bsProduct);
            outBound.setBsProductList(bsProductList);
            model.addAttribute("outBound", outBound);
        }
        model.addAttribute("pdaUsers", pdaUsers);
        model.addAttribute("bsProducts", bsProducts);
        return "modules/fwzs/outBoundForm";
    }

    @RequiresPermissions("fwzs:dealer:edit")
    @RequestMapping(value = "save")
    public String save(OutBound outBound, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, outBound)) {
            return form(outBound, model);
        }
        String status = outBoundService.saveOutBound(outBound);
        if (Global.BOUND_SUCCESS.equals(status)) {
            addMessage(redirectAttributes, "保存出库计划成功");
        } else if (Global.BOUND_OUTOFSTOCK.equals(status)) {
            addMessage(redirectAttributes, "保存出库计划失败，库存数量不足");
        } else {
            addMessage(redirectAttributes, "保存出库计划失败，库存不存在");
        }
        return "redirect:" + Global.getAdminPath() + "/fwzs/outBound/?repage";
    }

    /**
     * 根据出库查询防伪码
     *
     * @param outBound
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "qrcodeDetailList")
    public String findQrcodeById(final OutBound outBound, HttpServletRequest request, HttpServletResponse response, Model model) {
        final Qrcode2BoxcodeMapping qrcode2BoxcodeMapping = new Qrcode2BoxcodeMapping();
        qrcode2BoxcodeMapping.setBoundId(outBound.getId());
        if (null != outBound.getBsProduct() && StringUtils.isNotBlank(outBound.getBsProduct().getId())) {
            qrcode2BoxcodeMapping.setProdId(outBound.getBsProduct().getId());
            model.addAttribute("bsProduct", bsProductService.get(outBound.getBsProduct().getId()));
        }

        Page<Qrcode2BoxcodeMapping> page = outBoundService.findBoxcodePageByOutboundId(new Page<Qrcode2BoxcodeMapping>(request,
                response), qrcode2BoxcodeMapping);
        model.addAttribute("page", page);
        model.addAttribute("receiverType", request.getParameter("receiverType"));
        return "modules/fwzs/qrOutBoundCodeDetailList";
    }

    /**
     * 根据出库查询防伪码并导出成Excel
     *
     * @param outBound
     * @param model
     * @return
     */
    @RequestMapping(value = "exportQrcode")
    public String exportQrcode(final OutBound outBound, Model model) {
        model.addAttribute("qrCodeList", outBoundService.findBoxcodePageByOutboundId(outBound));
        model.addAttribute("boundNo", outBound.getOutBoundNo());
        if (null != outBound.getBsProduct() && StringUtils.isNotBlank(outBound.getBsProduct().getId())) {
            model.addAttribute("productName", bsProductService.get(outBound.getBsProduct().getId()).getProdName());
        }
        return "qrCodeExcel";
    }

    @ResponseBody
    @RequestMapping(value = "delete")
    public boolean delete(String outBoundIds, RedirectAttributes redirectAttributes) {
        addMessage(redirectAttributes, "删除出库计划成功");
        return outBoundService.deleteOutBound(outBoundIds);
    }

    /**
     * 更新出库单状态为已发货
     *
     * @param outBoundId
     * @param status
     */
    @RequestMapping(value = "updateStatus")
    public String updateStatus(final String outBoundId, final String status, Model model, HttpServletRequest request) {
        OutBound outBound = new OutBound();
        outBound.setId(outBoundId);
        outBound.setStatus(Global.SHIPPING);
        outBoundService.updateStatus(outBound);
        return "redirect:" + Global.getAdminPath() + "/fwzs/outBound/?repage&status=" + status;
    }

    /**
     * 更新物流数据
     *
     */
    @RequestMapping(value = "deliveryGoods", method = RequestMethod.POST)
    public String deliveryGoods(OutBound delivery, Model model, HttpServletRequest request) {
        OutBound outBound = new OutBound();
        outBound.setId(delivery.getId());
        outBound.setShipNo(request.getParameter("shipNo"));
        outBound.setShipName(request.getParameter("shipName"));
        outBound.setStatus(Global.SHIPPING);
        outBoundService.updateStatus(outBound);
        return "redirect:" + Global.getAdminPath() + "/fwzs/outBound/?repage&status=0";
    }

    /**
     * 验证产品是否允许出库
     *
     * @param outBoundId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "validateRealNumber")
    public boolean validateRealNumber(final String outBoundId) {
        OutBound outBound = new OutBound();
        outBound.setId(outBoundId);
        return outBoundService.validateRealNumber(outBound);
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
        return outBoundService.getLatestStockLevel(request.getParameter("prodId"),
                request.getParameter("warehouseId"));
    }
}
