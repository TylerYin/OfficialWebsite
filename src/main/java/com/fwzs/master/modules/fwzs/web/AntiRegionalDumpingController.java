package com.fwzs.master.modules.fwzs.web;

import com.fwzs.master.common.config.Global;
import com.fwzs.master.common.persistence.Page;
import com.fwzs.master.common.utils.StringUtils;
import com.fwzs.master.common.web.BaseController;
import com.fwzs.master.modules.fwzs.entity.AntiRegionalDumping;
import com.fwzs.master.modules.fwzs.entity.BsProduct;
import com.fwzs.master.modules.fwzs.service.AntiRegionalDumpingService;
import com.fwzs.master.modules.fwzs.service.BsProductService;
import com.fwzs.master.modules.fwzs.service.DealerService;
import com.fwzs.master.modules.sys.utils.UserUtils;
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
 * @create 2018-03-15 15:20
 * @description AntiRegionalParaSetting Controller
 **/
@Controller
@RequestMapping(value = "${adminPath}/fwzs/antiRegionalDumping")
public class AntiRegionalDumpingController extends BaseController {

    @Autowired
    private DealerService dealerService;

    @Autowired
    private BsProductService bsProductService;

    @Autowired
    private AntiRegionalDumpingService antiRegionalDumpingService;

    @ModelAttribute
    public AntiRegionalDumping get(@RequestParam(required = false) String id) {
        AntiRegionalDumping entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = antiRegionalDumpingService.get(id);
        }
        if (entity == null) {
            entity = new AntiRegionalDumping();
        }
        return entity;
    }

    @RequestMapping(value = {"list", ""})
    public String list(AntiRegionalDumping antiRegionalDumping, HttpServletRequest request, HttpServletResponse response, Model model) {
        String refreshDateInterval = Global.getConfig("anti.regional.dumping.refresh");
        if (StringUtils.isNotBlank(refreshDateInterval)) {
            model.addAttribute("refreshDateInterval", refreshDateInterval);
        } else {
            model.addAttribute("refreshDateInterval", "60000");
        }

        model.addAttribute("bsProducts", bsProductService.findList(new BsProduct()));
        model.addAttribute("isSystemManager", UserUtils.isSystemManager());
        Page<AntiRegionalDumping> page = antiRegionalDumpingService.findPage(new Page<AntiRegionalDumping>(request, response), antiRegionalDumping);
        model.addAttribute("page", page);
        return "modules/fwzs/antiRegionalDumpingList";
    }

    @RequestMapping(value = "listDetail")
    public String listDetail(AntiRegionalDumping antiRegionalDumping, HttpServletRequest request, HttpServletResponse response, Model model) {
        model.addAttribute("bsProducts", bsProductService.findList(new BsProduct()));
        model.addAttribute("isSystemManager", UserUtils.isSystemManager());
        Page<AntiRegionalDumping> page = antiRegionalDumpingService.findDetailPage(new Page<AntiRegionalDumping>(request, response), antiRegionalDumping);
        antiRegionalDumping.setDealer(dealerService.get(antiRegionalDumping.getDealer()));
        antiRegionalDumping.setBsProduct(bsProductService.get(antiRegionalDumping.getBsProduct()));
        model.addAttribute("page", page);
        return "modules/fwzs/antiRegionalDumpingDetail";
    }

    @RequestMapping(value = "form")
    public String form(AntiRegionalDumping antiRegionalDumping, Model model) {
        model.addAttribute("antiRegionalDumping", antiRegionalDumping);
        return "modules/fwzs/antiRegionalParaSettingForm";
    }

    @RequestMapping(value = "delete")
    public String delete(AntiRegionalDumping antiRegionalDumping, RedirectAttributes redirectAttributes) {
        antiRegionalDumpingService.delete(antiRegionalDumping);
        addMessage(redirectAttributes, "删除防窜货数据成功");
        return "redirect:" + Global.getAdminPath() + "/fwzs/antiRegionalDumping/?repage";
    }
}
