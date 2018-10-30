package com.fwzs.master.modules.fwzs.web;

import com.fwzs.master.common.config.Global;
import com.fwzs.master.common.utils.PropertiesLoader;
import com.fwzs.master.common.web.BaseController;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

/**
 * @author Tyler Yin
 * @create 2018-06-07 14:11
 * @description System Config Controller
 **/
@Controller
@RequestMapping(value = "${adminPath}/fwzs/systemSetting")
public class SystemConfigController extends BaseController {

    private PropertiesLoader loader = new PropertiesLoader();

    /**
     * 防伪追溯模板设置
     *
     * @return
     */
    @RequestMapping("retrospectTemplate")
    public String fwRetrospectTemplateSetting(HttpServletRequest request, HttpServletResponse response, Model model) {
        final List<String> retrospectTemplateList = new ArrayList<>();
        Properties prop = loader.getRealtimeProperties(Global.RetrospectTemplateSetting.PROPERTY_FILE_NAME);
        for (String key : prop.stringPropertyNames()) {
            if (key.contains(Global.RetrospectTemplateSetting.RETROSPECT_TEMPLATE_PREFIX) && !key.equalsIgnoreCase(Global.RetrospectTemplateSetting.RETROSPECT_TEMPLATE_DEFAULT_KEY)) {
                retrospectTemplateList.add(prop.getProperty(key));
            }
        }
        model.addAttribute("defaultRetrospectTemplate", prop.getProperty(Global.RetrospectTemplateSetting.RETROSPECT_TEMPLATE_DEFAULT_KEY));
        model.addAttribute("retrospectTemplateList", retrospectTemplateList);
        return "modules/fwzs/retrospectTemplateSettingForm";
    }

    /**
     * 保存防伪追溯模板设置
     *
     * @return
     */
    @ResponseBody
    @RequestMapping("saveRetrospectTemplate")
    public Boolean saveFwRetrospectTemplateSetting(String templateSetting, HttpServletRequest request, HttpServletResponse response, Model model) {
        Boolean isSettingTemplateSuccess = true;
        Properties prop = loader.getRealtimeProperties(Global.RetrospectTemplateSetting.PROPERTY_FILE_NAME);
        try {
            prop.setProperty(Global.RetrospectTemplateSetting.RETROSPECT_TEMPLATE_DEFAULT_KEY, templateSetting);
            Writer w = new FileWriter(this.getClass().getClassLoader().getResource(Global.RetrospectTemplateSetting.PROPERTY_FILE_NAME).getPath());
            prop.store(w, "Update date : " + DateFormatUtils.format(new Date(), Global.DateFormate.PATTERN_CHINATIMEFORMATE));
            w.close();
        } catch (IOException ex) {
            logger.error("设置防伪追溯模板失败！", ex);
            isSettingTemplateSuccess = false;
        }
        return isSettingTemplateSuccess;
    }
}