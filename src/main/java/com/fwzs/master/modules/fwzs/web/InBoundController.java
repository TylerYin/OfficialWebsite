package com.fwzs.master.modules.fwzs.web;

import com.fwzs.master.common.persistence.Page;
import com.fwzs.master.common.utils.StringUtils;
import com.fwzs.master.common.web.BaseController;
import com.fwzs.master.modules.fwzs.entity.BsProduct;
import com.fwzs.master.modules.fwzs.entity.InBound;
import com.fwzs.master.modules.fwzs.entity.Qrcode2BoxcodeMapping;
import com.fwzs.master.modules.fwzs.service.BsProductService;
import com.fwzs.master.modules.fwzs.service.InBoundService;
import com.fwzs.master.modules.fwzs.service.ScPlanService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Tyler Yin
 * @create 2017-11-29 13:59
 * @description 入库Controller
 **/
@Controller
@RequestMapping(value = "${adminPath}/fwzs/inBound")
public class InBoundController extends BaseController {
    @Autowired
    private InBoundService inBoundService;
    @Autowired
    private ScPlanService scPlanService;
    @Autowired
    private BsProductService bsProductService;

    @ModelAttribute
    public InBound get(@RequestParam(required = false) String id) {
        InBound entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = inBoundService.get(id);
        }
        if (entity == null) {
            entity = new InBound();
        }
        return entity;
    }

    @RequiresPermissions("fwzs:dealer:view")
    @RequestMapping(value = {"list", ""})
    public String list(InBound inBound, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<InBound> page = inBoundService.findPage(new Page<InBound>(request, response), inBound);
        model.addAttribute("page", page);
        model.addAttribute("bsProducts", bsProductService.findList(new BsProduct()));
        return "modules/fwzs/inBoundList";
    }

    /**
     * 根据入库查询防伪码
     *
     * @param inBound
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "qrcodeDetailList")
    public String findQrcodeById(final InBound inBound, HttpServletRequest request, HttpServletResponse response, Model model) {
        final Qrcode2BoxcodeMapping qrcode2BoxcodeMapping = new Qrcode2BoxcodeMapping();

        if (null != inBound.getScPlan() && StringUtils.isNotBlank(inBound.getScPlan().getId())) {
            qrcode2BoxcodeMapping.setPlanId(inBound.getScPlan().getId());
            model.addAttribute("scPlan", scPlanService.get(inBound.getScPlan().getId()));
        }

        if (null != inBound.getBsProduct() && StringUtils.isNotBlank(inBound.getBsProduct().getId())) {
            qrcode2BoxcodeMapping.setProdId(inBound.getBsProduct().getId());
            model.addAttribute("bsProduct", bsProductService.get(inBound.getBsProduct().getId()));
        }

        Page<Qrcode2BoxcodeMapping> page = inBoundService.findBoxcodePageByInBoundId(new Page<Qrcode2BoxcodeMapping>(request,
                response), qrcode2BoxcodeMapping);
        model.addAttribute("page", page);
        return "modules/fwzs/qrInBoundCodeDetailList";
    }

    /**
     * 根据出库查询防伪码并导出成Excel
     *
     * @param inBound
     * @param model
     * @return
     */
    @RequestMapping(value = "exportQrcode")
    public String exportQrcode(final InBound inBound, Model model) {
        model.addAttribute("qrCodeList", inBoundService.findBoxcodePageByInBoundId(inBound));
        model.addAttribute("scPlanNo", scPlanService.get(inBound.getScPlan().getId()).getPlanNo());
        if (null != inBound.getBsProduct() && StringUtils.isNotBlank(inBound.getBsProduct().getId())) {
            model.addAttribute("productName", bsProductService.get(inBound.getBsProduct().getId()).getProdName());
        }
        return "qrCodeExcel";
    }
}
