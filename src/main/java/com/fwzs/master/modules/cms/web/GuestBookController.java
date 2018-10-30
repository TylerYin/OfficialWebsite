package com.fwzs.master.modules.cms.web;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fwzs.master.modules.cms.constant.ConfigConstant;
import com.fwzs.master.modules.cms.entity.GuestBook;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fwzs.master.common.persistence.Page;
import com.fwzs.master.common.utils.StringUtils;
import com.fwzs.master.common.web.BaseController;
import com.fwzs.master.modules.cms.service.GuestBookService;
import com.fwzs.master.modules.sys.utils.DictUtils;
import com.fwzs.master.modules.sys.utils.UserUtils;

/**
 * 留言Controller
 *
 * @author ly
 * @version 2013-3-23
 */
@Controller
@RequestMapping(value = "${adminPath}/cms/guestBook")
public class GuestBookController extends BaseController {

    @Autowired
    private GuestBookService guestBookService;

    @ModelAttribute
    public GuestBook get(@RequestParam(required = false) String id) {
        if (StringUtils.isNotBlank(id)) {
            return guestBookService.get(id);
        } else {
            return new GuestBook();
        }
    }

    @RequiresPermissions("cms:guestBook:view")
    @RequestMapping(value = {"list", ""})
    public String list(GuestBook guestBook, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<GuestBook> page = guestBookService.findPage(new Page<GuestBook>(request, response), guestBook);
        model.addAttribute("page", page);
        return "modules/cms/guestBookList";
    }

    @RequiresPermissions("cms:guestBook:view")
    @RequestMapping(value = "form")
    public String form(GuestBook guestBook, Model model) {
        model.addAttribute("guestBook", guestBook);
        return "modules/cms/guestBookForm";
    }

    @RequiresPermissions("cms:guestBook:edit")
    @RequestMapping(value = "save")
    public String save(GuestBook guestBook, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, guestBook)) {
            return form(guestBook, model);
        }
        if (guestBook.getReUser() == null) {
            guestBook.setReUser(UserUtils.getUser());
            guestBook.setReDate(new Date());
        }
        guestBookService.save(guestBook);
        addMessage(redirectAttributes, DictUtils.getDictLabel(guestBook.getDelFlag(), "cms_del_flag", "保存")
                + "留言'" + guestBook.getName() + "'成功");
        return "redirect:" + adminPath + "/cms/guestBook/?repage&status=2";
    }

    @RequiresPermissions("cms:guestBook:edit")
    @RequestMapping(value = "delete")
    public String delete(GuestBook guestBook, @RequestParam(required = false) String status, RedirectAttributes redirectAttributes) {
        guestBookService.delete(guestBook, status);
        String message;
        if (ConfigConstant.AUDITED_STATUS.equals(status)) {
            message = "删除留言成功";
        } else if (ConfigConstant.DELETED_STATUS.equals(status)) {
            message = "恢复审核留言成功";
        } else {
            message = "留言发布成功";
        }
        addMessage(redirectAttributes, message);
        return "redirect:" + adminPath + "/cms/guestBook/?repage&delFlag=" + status;
    }
}
