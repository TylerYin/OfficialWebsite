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
import com.fwzs.master.modules.fwzs.entity.FwmBigboxCode;
import com.fwzs.master.modules.fwzs.service.FwmBigboxCodeService;

/**
 * 剁码Controller
 * @author ly
 * @version 2017-10-09
 */
@Controller
@RequestMapping(value = "${adminPath}/fwzs/fwmBigboxCode")
public class FwmBigboxCodeController extends BaseController {

	@Autowired
	private FwmBigboxCodeService fwmBigboxCodeService;
	
	@ModelAttribute
	public FwmBigboxCode get(@RequestParam(required=false) String id) {
		FwmBigboxCode entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = fwmBigboxCodeService.get(id);
		}
		if (entity == null){
			entity = new FwmBigboxCode();
		}
		return entity;
	}
	
	@RequiresPermissions("fwzs:fwmBigboxCode:view")
	@RequestMapping(value = {"list", ""})
	public String list(FwmBigboxCode fwmBigboxCode, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<FwmBigboxCode> page = fwmBigboxCodeService.findPage(new Page<FwmBigboxCode>(request, response), fwmBigboxCode); 
		model.addAttribute("page", page);
		return "modules/fwzs/fwmBigboxCodeList";
	}

	@RequiresPermissions("fwzs:fwmBigboxCode:view")
	@RequestMapping(value = "form")
	public String form(FwmBigboxCode fwmBigboxCode, Model model) {
		model.addAttribute("fwmBigboxCode", fwmBigboxCode);
		return "modules/fwzs/fwmBigboxCodeForm";
	}

	@RequiresPermissions("fwzs:fwmBigboxCode:edit")
	@RequestMapping(value = "save")
	public String save(FwmBigboxCode fwmBigboxCode, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, fwmBigboxCode)){
			return form(fwmBigboxCode, model);
		}
		fwmBigboxCodeService.save(fwmBigboxCode);
		addMessage(redirectAttributes, "保存剁码成功");
		return "redirect:"+Global.getAdminPath()+"/fwzs/fwmBigboxCode/?repage";
	}
	
	@RequiresPermissions("fwzs:fwmBigboxCode:edit")
	@RequestMapping(value = "delete")
	public String delete(FwmBigboxCode fwmBigboxCode, RedirectAttributes redirectAttributes) {
		fwmBigboxCodeService.delete(fwmBigboxCode);
		addMessage(redirectAttributes, "删除剁码成功");
		return "redirect:"+Global.getAdminPath()+"/fwzs/fwmBigboxCode/?repage";
	}

}