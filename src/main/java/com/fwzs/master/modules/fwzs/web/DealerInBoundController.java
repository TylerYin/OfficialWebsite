package com.fwzs.master.modules.fwzs.web;

import com.fwzs.master.common.config.Global;
import com.fwzs.master.common.persistence.Page;
import com.fwzs.master.common.utils.StringUtils;
import com.fwzs.master.common.web.BaseController;
import com.fwzs.master.modules.fwzs.entity.BsProduct;
import com.fwzs.master.modules.fwzs.entity.DealerInBound;
import com.fwzs.master.modules.fwzs.service.BsProductService;
import com.fwzs.master.modules.fwzs.service.DealerBoundService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Tyler Yin
 * @create 2017-11-22 16:35
 * @description 出库Controller
 **/
@Controller
@RequestMapping(value = "${adminPath}/fwzs/dealerInBound")
public class DealerInBoundController extends BaseController {
    @Autowired
    private BsProductService bsProductService;

    @Autowired
    private DealerBoundService dealerBoundService;

    @RequiresPermissions("fwzs:dealer:view")
    @RequestMapping(value = {"list", ""})
    public String list(DealerInBound dealerInBound, HttpServletRequest request, HttpServletResponse response, Model model) {
        if (StringUtils.isBlank(request.getParameter("status"))
                || Global.SHIPPING.equals(request.getParameter("status"))) {
            dealerInBound.setStatus(Global.SHIPPING);
        } else {
            dealerInBound.setStatus(Global.RECEIVED);
        }

        Page<DealerInBound> page = dealerBoundService.findDealerInBoundPage(new Page<DealerInBound>(request, response), dealerInBound);
        model.addAttribute("page", page);
        model.addAttribute("bsProducts", bsProductService.findList(new BsProduct()));
        return "modules/fwzs/dealerInBoundList";
    }

    @RequestMapping(value = "updateStatus")
    public String updateStatus(DealerInBound dealerInBound, RedirectAttributes redirectAttributes) {
        dealerInBound.setStatus(Global.RECEIVED);
        dealerBoundService.updateInBoundStatus(dealerInBound);
        return "redirect:" + Global.getAdminPath() + "/fwzs/dealerInBound/?repage&status=1";
    }

}
