package com.fwzs.master.modules.cms.web.front;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fwzs.master.modules.cms.constant.ConfigConstant;
import com.fwzs.master.modules.cms.entity.GuestBook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fwzs.master.common.persistence.Page;
import com.fwzs.master.common.utils.StringUtils;
import com.fwzs.master.common.web.BaseController;
import com.fwzs.master.modules.cms.entity.Article;
import com.fwzs.master.modules.cms.entity.Site;
import com.fwzs.master.modules.cms.service.ArticleService;
import com.fwzs.master.modules.cms.service.GuestBookService;
import com.fwzs.master.modules.cms.util.CmsUtils;
import com.fwzs.master.modules.sys.utils.UserUtils;

/**
 * 网站搜索Controller
 *
 * @author ly
 * @version 2013-5-29
 */
@Controller
@RequestMapping(value = "${frontPath}/search")
public class FrontSearchController extends BaseController {

    @Autowired
    private ArticleService articleService;
    @Autowired
    private GuestBookService guestBookService;

    /**
     * 全站搜索
     */
    @RequestMapping(value = "")
    public String search(String t, @RequestParam(required = false) String q, @RequestParam(required = false) String qand, @RequestParam(required = false) String qnot,
                         @RequestParam(required = false) String a, @RequestParam(required = false) String cid, @RequestParam(required = false) String bd,
                         @RequestParam(required = false) String ed, HttpServletRequest request, HttpServletResponse response, Model model) {
        long start = System.currentTimeMillis();
        Site site = CmsUtils.getSite(Site.defaultSiteId());
        model.addAttribute("site", site);

        // 重建索引（需要超级管理员权限）
        if (ConfigConstant.CMS_REINDEX.equals(q)) {
            if (UserUtils.getUser().isAdmin()) {
                // 文章模型
                if (StringUtils.isBlank(t) || ConfigConstant.ARTICLE.equals(t)) {
                    articleService.createIndex();
                }
                // 留言模型
                else if (ConfigConstant.GUEST_BOOK.equals(t)) {
                    guestBookService.createIndex();
                }
                model.addAttribute("message", "重建索引成功，共耗时 " + (System.currentTimeMillis() - start) + "毫秒。");
            } else {
                model.addAttribute("message", "你没有执行权限。");
            }
        }
        // 执行检索
        else {
            String qStr = StringUtils.replace(StringUtils.replace(q, "，", " "), ", ", " ");
            // 如果是高级搜索
            if (ConfigConstant.POS_ID_ONE.equals(a)) {
                if (StringUtils.isNotBlank(qand)) {
                    qStr += " +" + StringUtils.replace(StringUtils.replace(StringUtils.replace(qand, "，", " "), ", ", " "), " ", " +");
                }
                if (StringUtils.isNotBlank(qnot)) {
                    qStr += " -" + StringUtils.replace(StringUtils.replace(StringUtils.replace(qnot, "，", " "), ", ", " "), " ", " -");
                }
            }
            // 文章检索
            if (StringUtils.isBlank(t) || ConfigConstant.ARTICLE.equals(t)) {
                Page<Article> page = articleService.search(new Page<Article>(request, response), qStr, cid, bd, ed);
                page.setMessage("匹配结果，共耗时 " + (System.currentTimeMillis() - start) + "毫秒。");
                model.addAttribute("page", page);
            }
            // 留言检索
            else if (ConfigConstant.GUEST_BOOK.equals(t)) {
                Page<GuestBook> page = guestBookService.search(new Page<GuestBook>(request, response), qStr, bd, ed);
                page.setMessage("匹配结果，共耗时 " + (System.currentTimeMillis() - start) + "毫秒。");
                model.addAttribute("page", page);
            }

        }
        // 搜索类型
        model.addAttribute("t", t);
        // 搜索关键字
        model.addAttribute("q", q);
        // 包含以下全部的关键词
        model.addAttribute("qand", qand);
        // 不包含以下关键词
        model.addAttribute("qnot", qnot);
        // 搜索类型
        model.addAttribute("cid", cid);
        return "modules/cms/front/themes/" + site.getTheme() + "/frontSearch";
    }
}
