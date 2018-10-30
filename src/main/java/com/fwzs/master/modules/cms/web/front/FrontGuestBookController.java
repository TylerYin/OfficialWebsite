package com.fwzs.master.modules.cms.web.front;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import com.fwzs.master.modules.cms.entity.GuestBook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fwzs.master.common.config.Global;
import com.fwzs.master.common.persistence.Page;
import com.fwzs.master.common.web.BaseController;
import com.fwzs.master.modules.cms.entity.Site;
import com.fwzs.master.modules.cms.service.GuestBookService;
import com.fwzs.master.modules.cms.util.CmsUtils;

/**
 * 留言板Controller
 *
 * @author ly
 * @version 2013-3-15
 */
@Controller
@RequestMapping(value = "${frontPath}/guestBook")
public class FrontGuestBookController extends BaseController {

    @Autowired
    private GuestBookService guestBookService;

    /**
     * 留言板
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String guestBook(@RequestParam(required = false, defaultValue = "1") Integer pageNo,
                            @RequestParam(required = false, defaultValue = "30") Integer pageSize, Model model) {
        Site site = CmsUtils.getSite(Site.defaultSiteId());
        model.addAttribute("site", site);
        Page<GuestBook> page = new Page<>(pageNo, pageSize);
        GuestBook guestBook = new GuestBook();
        guestBook.setDelFlag(GuestBook.DEL_FLAG_NORMAL);
        page = guestBookService.findPage(page, guestBook);
        model.addAttribute("page", page);
        return "modules/cms/front/themes/" + site.getTheme() + "/frontGuestBook";
    }

    /**
     * 留言板-保存留言信息
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    public String guestBookSave(GuestBook guestBook, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        guestBook.setIp(request.getRemoteAddr());
        guestBook.setCreateDate(new Date());
        guestBook.setDelFlag(GuestBook.DEL_FLAG_AUDIT);
        guestBookService.save(guestBook);
        addMessage(redirectAttributes, "提交成功，谢谢！");
        return "redirect:" + Global.getFrontPath() + "/guestBook";
    }
}
