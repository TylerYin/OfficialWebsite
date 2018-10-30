package com.fwzs.master.modules.cms.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fwzs.master.modules.cms.constant.ConfigConstant;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fwzs.master.common.mapper.JsonMapper;
import com.fwzs.master.common.persistence.Page;
import com.fwzs.master.common.utils.StringUtils;
import com.fwzs.master.common.web.BaseController;
import com.fwzs.master.modules.cms.entity.Article;
import com.fwzs.master.modules.cms.entity.Category;
import com.fwzs.master.modules.cms.entity.Site;
import com.fwzs.master.modules.cms.service.ArticleDataService;
import com.fwzs.master.modules.cms.service.ArticleService;
import com.fwzs.master.modules.cms.service.CategoryService;
import com.fwzs.master.modules.cms.service.FileTplService;
import com.fwzs.master.modules.cms.service.SiteService;
import com.fwzs.master.modules.cms.util.CmsUtils;
import com.fwzs.master.modules.cms.util.TplUtils;
import com.fwzs.master.modules.sys.utils.UserUtils;

/**
 * 文章Controller
 *
 * @author ly
 * @version 2013-3-23
 */
@Controller
@RequestMapping(value = "${adminPath}/cms/article")
public class ArticleController extends BaseController {

    @Autowired
    private ArticleService articleService;
    @Autowired
    private ArticleDataService articleDataService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private FileTplService fileTplService;
    @Autowired
    private SiteService siteService;

    @ModelAttribute
    public Article get(@RequestParam(required = false) String id) {
        if (StringUtils.isNotBlank(id)) {
            return articleService.get(id);
        } else {
            return new Article();
        }
    }

    @RequiresPermissions("cms:article:view")
    @RequestMapping(value = {"list", ""})
    public String list(Article article, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<Article> page = articleService.findPage(new Page<Article>(request, response), article, true);
        model.addAttribute("page", page);
        return "modules/cms/articleList";
    }

    @RequiresPermissions("cms:article:view")
    @RequestMapping(value = "form")
    public String form(Article article, Model model) {
        // 如果当前传参有子节点，则选择取消传参选择
        if (article.getCategory() != null && StringUtils.isNotBlank(article.getCategory().getId())) {
            List<Category> list = categoryService.findByParentId(article.getCategory().getId(), Site.getCurrentSiteId());
            if (list.size() > 0) {
                article.setCategory(null);
            } else {
                article.setCategory(categoryService.get(article.getCategory().getId()));
            }
        }
        article.setArticleData(articleDataService.get(article.getId()));

        model.addAttribute("contentViewList", getTplContent());
        model.addAttribute("article_DEFAULT_TEMPLATE", Article.DEFAULT_TEMPLATE);
        model.addAttribute("article", article);
        CmsUtils.addViewConfigAttribute(model, article.getCategory());
        return "modules/cms/articleForm";
    }

    @RequiresPermissions("cms:article:edit")
    @RequestMapping(value = "save")
    public String save(Article article, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, article)) {
            return form(article, model);
        }
        articleService.save(article);
        addMessage(redirectAttributes, "保存文章'" + StringUtils.abbr(article.getTitle(), 50) + "'成功");
        String categoryId = article.getCategory() != null ? article.getCategory().getId() : null;
        return "redirect:" + adminPath + "/cms/article/?repage";
    }

    @RequiresPermissions("cms:article:edit")
    @RequestMapping(value = "delete")
    public String delete(Article article, String categoryId, @RequestParam(required = false) String status, RedirectAttributes redirectAttributes) {
        // 如果没有审核权限，则不允许删除或发布
        if (!UserUtils.getSubject().isPermitted(ConfigConstant.CMS_ARTICLE_AUDIT)) {
            addMessage(redirectAttributes, "你没有删除或发布权限");
        }
        articleService.delete(article, status);

        String message;
        if (ConfigConstant.AUDITED_STATUS.equals(status)) {
            message = "删除文章成功";
        } else if (ConfigConstant.DELETED_STATUS.equals(status)) {
            message = "恢复审核文章成功";
        } else {
            message = "文章发布成功";
        }
        addMessage(redirectAttributes, message);
        return "redirect:" + adminPath + "/cms/article/?repage&delFlag=" + status;
    }

    /**
     * 文章选择列表
     */
    @RequiresPermissions("cms:article:view")
    @RequestMapping(value = "selectList")
    public String selectList(Article article, HttpServletRequest request, HttpServletResponse response, Model model) {
        list(article, request, response, model);
        return "modules/cms/articleSelectList";
    }

    /**
     * 通过编号获取文章标题
     */
    @RequiresPermissions("cms:article:view")
    @ResponseBody
    @RequestMapping(value = "findByIds")
    public String findByIds(String ids) {
        List<Object[]> list = articleService.findByIds(ids);
        return JsonMapper.nonDefaultMapper().toJson(list);
    }

    private List<String> getTplContent() {
        List<String> tplList = fileTplService.getNameListByPrefix(siteService.get(Site.getCurrentSiteId()).getSolutionPath());
        tplList = TplUtils.tplTrim(tplList, Article.DEFAULT_TEMPLATE, "");
        return tplList;
    }
}
