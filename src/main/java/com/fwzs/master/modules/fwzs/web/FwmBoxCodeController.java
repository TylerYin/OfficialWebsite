/**
 * 
 */
package com.fwzs.master.modules.fwzs.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fwzs.master.common.config.Global;
import com.fwzs.master.common.persistence.Page;
import com.fwzs.master.common.web.BaseController;
import com.fwzs.master.common.utils.StringUtils;
import com.fwzs.master.modules.fwzs.entity.FwmBoxCode;
import com.fwzs.master.modules.fwzs.service.FwmBoxCodeService;

/**
 * 箱码Controller
 * @author ly
 * @version 2017-10-09
 */
@Controller
@RequestMapping(value = "${adminPath}/fwzs/fwmBoxCode")
public class FwmBoxCodeController extends BaseController {

	@Autowired
	private FwmBoxCodeService fwmBoxCodeService;
	
	@ModelAttribute
	public FwmBoxCode get(@RequestParam(required=false) String id) {
		FwmBoxCode entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = fwmBoxCodeService.get(id);
		}
		if (entity == null){
			entity = new FwmBoxCode();
		}
		return entity;
	}
	
	@RequiresPermissions("fwzs:fwmBoxCode:view")
	@RequestMapping(value = {"list", ""})
	public String list(FwmBoxCode fwmBoxCode, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<FwmBoxCode> page = fwmBoxCodeService.findPage(new Page<FwmBoxCode>(request, response), fwmBoxCode); 
		model.addAttribute("page", page);
		return "modules/fwzs/fwmBoxCodeList";
	}

	@RequiresPermissions("fwzs:fwmBoxCode:view")
	@RequestMapping(value = "form")
	public String form(FwmBoxCode fwmBoxCode, Model model) {
		model.addAttribute("fwmBoxCode", fwmBoxCode);
		return "modules/fwzs/fwmBoxCodeForm";
	}

	@RequiresPermissions("fwzs:fwmBoxCode:edit")
	@RequestMapping(value = "save")
	public String save(FwmBoxCode fwmBoxCode, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, fwmBoxCode)){
			return form(fwmBoxCode, model);
		}
		fwmBoxCodeService.save(fwmBoxCode);
		addMessage(redirectAttributes, "保存箱码成功");
		return "redirect:"+Global.getAdminPath()+"/fwzs/fwmBoxCode/?repage";
	}
	
	@RequiresPermissions("fwzs:fwmBoxCode:edit")
	@RequestMapping(value = "delete")
	public String delete(FwmBoxCode fwmBoxCode, RedirectAttributes redirectAttributes) {
		fwmBoxCodeService.delete(fwmBoxCode);
		addMessage(redirectAttributes, "删除箱码成功");
		return "redirect:"+Global.getAdminPath()+"/fwzs/fwmBoxCode/?repage";
	}

}