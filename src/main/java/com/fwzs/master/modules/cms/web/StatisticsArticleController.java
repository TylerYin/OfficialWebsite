package com.fwzs.master.modules.cms.web;

import java.util.List;
import java.util.Map;

import com.fwzs.master.modules.cms.service.StatisticsService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fwzs.master.common.web.BaseController;
import com.fwzs.master.modules.cms.entity.Category;

/**
 * 统计Controller
 *
 * @author ly
 * @version 2013-5-21
 */
@Controller
@RequestMapping(value = "${adminPath}/cms/statistics")
public class StatisticsArticleController extends BaseController {

    @Autowired
    private StatisticsService statisticsService;

    /**
     * 文章信息量
     *
     * @param paramMap
     * @param model
     * @return
     */
    @RequiresPermissions("cms:statistics:article")
    @RequestMapping(value = "article")
    public String article(@RequestParam Map<String, Object> paramMap, Model model) {
        List<Category> list = statisticsService.article(paramMap);
        model.addAttribute("list", list);
        model.addAttribute("paramMap", paramMap);
        return "modules/cms/statisticsArticle";
    }

}
