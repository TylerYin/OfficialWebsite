package com.fwzs.master.modules.fwzs.web;

import com.fwzs.master.common.config.Global;
import com.fwzs.master.common.utils.StringUtils;
import com.fwzs.master.common.web.BaseController;
import com.fwzs.master.modules.fwzs.entity.AntiRegionalParaSetting;
import com.fwzs.master.modules.fwzs.service.AntiRegionalParaSettingService;
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
import java.util.List;

/**
 * @author Tyler Yin
 * @create 2018-03-15 15:20
 * @description AntiRegionalParaSetting Controller
 **/
@Controller
@RequestMapping(value = "${adminPath}/fwzs/antiRegionalParaSetting")
public class AntiRegionalParaSettingController extends BaseController {

    @Autowired
    private AntiRegionalParaSettingService antiRegionalParaSettingService;

    @ModelAttribute
    public AntiRegionalParaSetting get(@RequestParam(required = false) String id) {
        AntiRegionalParaSetting entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = antiRegionalParaSettingService.get(id);
        }
        if (entity == null) {
            entity = new AntiRegionalParaSetting();
        }
        return entity;
    }

    @RequestMapping(value = {"list", ""})
    public String list(AntiRegionalParaSetting antiRegionalParaSetting, HttpServletRequest request, HttpServletResponse response, Model model) {
        boolean isSystemManager = UserUtils.isSystemManager();
        List<AntiRegionalParaSetting> antiRegionalParaSettings = antiRegionalParaSettingService.findList(antiRegionalParaSetting, isSystemManager);
        model.addAttribute("isDealer", UserUtils.isDealer());
        model.addAttribute("isSystemManager", isSystemManager);
        model.addAttribute("antiRegionalParaSettings", antiRegionalParaSettings);
        return "modules/fwzs/antiRegionalParaSettingList";
    }

    @RequestMapping(value = "form")
    public String form(AntiRegionalParaSetting antiRegionalParaSetting, Model model) {
        model.addAttribute("antiRegionalParaSetting", antiRegionalParaSetting);
        return "modules/fwzs/antiRegionalParaSettingForm";
    }

    @RequestMapping(value = "save")
    public String save(AntiRegionalParaSetting antiRegionalParaSetting, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, antiRegionalParaSetting)) {
            return form(antiRegionalParaSetting, model);
        }
        antiRegionalParaSettingService.save(antiRegionalParaSetting);
        addMessage(redirectAttributes, "保存防窜货参数设置成功");
        return "redirect:" + Global.getAdminPath() + "/fwzs/antiRegionalParaSetting/?repage";
    }

    @RequestMapping(value = "delete")
    public String delete(AntiRegionalParaSetting antiRegionalParaSetting, RedirectAttributes redirectAttributes) {
        antiRegionalParaSettingService.delete(antiRegionalParaSetting);
        addMessage(redirectAttributes, "删除防窜货参数设置成功");
        return "redirect:" + Global.getAdminPath() + "/fwzs/antiRegionalParaSetting/?repage";
    }
}
