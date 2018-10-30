package com.fwzs.master.modules.fwzs.web;

import com.fwzs.master.common.web.BaseController;
import com.fwzs.master.modules.fwzs.entity.FwmQrcode;
import com.fwzs.master.modules.fwzs.entity.FwmTrace;
import com.fwzs.master.modules.fwzs.service.FwmQrcodeService;
import com.fwzs.master.modules.fwzs.service.FwmTraceService;
import com.fwzs.master.modules.sys.utils.UserUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author Tyler Yin
 * @create 2018-01-19 9:03
 * @description 防伪码追溯Controller
 **/
@Controller
@RequestMapping(value = "${adminPath}/fwzs/fwmTrace")
public class FwmTraceController extends BaseController {

    @Autowired
    private FwmTraceService fwmTraceService;

    @Autowired
    private FwmQrcodeService fwmQrcodeService;

    /**
     * 根据单品码查询物流信息
     *
     * @param fwmTrace
     * @param model
     * @return
     */
    @RequestMapping(value = "form")
    public String form(FwmTrace fwmTrace, Model model) {
        final String qrCode = fwmTrace.getQrCode();
        final FwmQrcode fwmQrcode = fwmQrcodeService.getByQrcode(qrCode);
        if (null != fwmQrcode && null!= fwmQrcode.getBsProduct() && null!=fwmQrcode.getBsProduct().getCompany()
                && UserUtils.getUser().getCompany().getId().equals(fwmQrcode.getBsProduct().getCompany().getId())) {
            FwmTrace outBound = fwmTraceService.findTraceByFwmQrcode(fwmTrace);
            if (null == outBound) {
                outBound = new FwmTrace();
                outBound.setQrCode(qrCode);
                model.addAttribute("fwmTrace", outBound);
            } else {
                model.addAttribute("fwmTrace", outBound);
            }
            List<FwmTrace> fwmTraces = fwmTraceService.findDealerTraceByFwmQrcode(fwmTrace);
            model.addAttribute("fwmDealerTraces", fwmTraces);

            if (null == outBound || StringUtils.isBlank(outBound.getOutboundNo())) {
                model.addAttribute("errorMessage", "暂时没有物流信息！");
            }
        } else {
            fwmTrace.setQrCode(qrCode);
            model.addAttribute("fwmTrace", fwmTrace);
            model.addAttribute("errorMessage", "您输入的单品码不存在，请确认是否通过正规渠道购买！");
        }
        return "modules/fwzs/fwmTraceForm";
    }
}
