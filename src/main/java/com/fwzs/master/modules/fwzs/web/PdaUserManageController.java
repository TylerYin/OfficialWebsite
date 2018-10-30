package com.fwzs.master.modules.fwzs.web;

import com.fwzs.master.common.config.Global;
import com.fwzs.master.common.persistence.Page;
import com.fwzs.master.common.utils.IdGen;
import com.fwzs.master.common.utils.ModelUtils;
import com.fwzs.master.common.utils.StringUtils;
import com.fwzs.master.common.web.BaseController;
import com.fwzs.master.modules.fwzs.entity.PdaUser;
import com.fwzs.master.modules.fwzs.service.PdaUserService;
import com.fwzs.master.modules.sys.utils.UserUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Tyler Yin
 * @create 2018-02-01 9:07
 * @description PDA用户管理
 **/
@Controller
@RequestMapping(value = "${adminPath}/fwzs/pda")
public class PdaUserManageController extends BaseController {
    @Autowired
    private PdaUserService pdaUserService;

    @ModelAttribute
    public PdaUser get(@RequestParam(required = false) String id) {
        PdaUser entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = pdaUserService.get(id);
        }
        if (entity == null) {
            entity = new PdaUser();
        }
        return entity;
    }

    @RequiresPermissions("fwzs:dealer:view")
    @RequestMapping(value = {"list", ""})
    public String list(PdaUser pdaUser, HttpServletRequest request,
                       HttpServletResponse response, Model model) {
        final String pdaNo = ModelUtils.getValueFromModel(model, "no");
        if (StringUtils.isNotEmpty(pdaNo)) {
            pdaUser.setNo(pdaNo);
        }

        final String pdaName = ModelUtils.getValueFromModel(model, "name");
        if (StringUtils.isNotEmpty(pdaName)) {
            pdaUser.setName(pdaName);
        }

        Page<PdaUser> page = pdaUserService.findPage(new Page<PdaUser>(request,
                response), pdaUser);
        model.addAttribute("isDealer", UserUtils.isDealer());
        model.addAttribute("page", page);
        return "modules/fwzs/pdaUserList";
    }

    @RequiresPermissions("fwzs:dealer:view")
    @RequestMapping(value = "form")
    public String form(PdaUser pdaUser, Model model, HttpServletRequest request) {
        if (StringUtils.isBlank(pdaUser.getId())) {
            pdaUser.setNo(IdGen.genProdFileSerialNum(3, "PDA"));
            model.addAttribute("pdaUser", pdaUser);
        }
        return "modules/fwzs/pdaUserForm";
    }

    @RequiresPermissions("fwzs:dealer:edit")
    @RequestMapping(value = "save")
    public String save(PdaUser pdaUser, Model model,
                       RedirectAttributes redirectAttributes, HttpServletRequest request) {
        if (!beanValidator(model, pdaUser)) {
            return form(pdaUser, model, request);
        }
        pdaUserService.save(pdaUser);
        addMessage(redirectAttributes, "保存PDA用户成功");
        return "redirect:" + Global.getAdminPath() + "/fwzs/pda/?repage";
    }

    @RequiresPermissions("fwzs:dealer:edit")
    @RequestMapping(value = "delete")
    public String delete(PdaUser pdaUser, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        pdaUserService.delete(pdaUser);
        addMessage(redirectAttributes, "删除PDA用户成功");
        redirectAttributes.addFlashAttribute("no", request.getParameter("no"));
        redirectAttributes.addFlashAttribute("name", request.getParameter("name"));
        return "redirect:" + Global.getAdminPath() + "/fwzs/pda/?repage";
    }

    /**
     * 根据登录用户名和所属公司查询帐户是否合法。
     * 若数据库中已经存在，则表明该帐户非法，否则表明该帐户有效。
     *
     * @param loginName
     * @param originLoginName
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/isUserAccountValidate", method = RequestMethod.GET)
    public boolean isUserAccountValidate(@RequestParam("loginName") String loginName, @RequestParam("originLoginName") String originLoginName) {
        return StringUtils.isNotBlank(loginName) && originLoginName.equals(loginName) ? true :
                !pdaUserService.isUserAccountValidate(loginName, UserUtils.getUser().getCompany());
    }
}
