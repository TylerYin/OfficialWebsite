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
import com.fwzs.master.modules.fwzs.entity.FwmQueryHistory;
import com.fwzs.master.modules.fwzs.service.FwmQueryHistoryService;

/**
 * 防伪查询记录表Controller
 * @author ly
 * @version 2017-09-30
 */
@Controller
@RequestMapping(value = "${adminPath}/fwzs/fwmQueryHistory")
public class FwmQueryHistoryController extends BaseController {

	@Autowired
	private FwmQueryHistoryService fwmQueryHistoryService;
	
	@ModelAttribute
	public FwmQueryHistory get(@RequestParam(required=false) String id) {
		FwmQueryHistory entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = fwmQueryHistoryService.get(id);
		}
		if (entity == null){
			entity = new FwmQueryHistory();
		}
		return entity;
	}
	
	@RequiresPermissions("fwzs:fwmQueryHistory:view")
	@RequestMapping(value = {"list", ""})
	public String list(FwmQueryHistory fwmQueryHistory, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<FwmQueryHistory> page = fwmQueryHistoryService.findPage(new Page<FwmQueryHistory>(request, response), fwmQueryHistory); 
		model.addAttribute("page", page);
		return "modules/fwzs/fwmQueryHistoryList";
	}

	@RequiresPermissions("fwzs:fwmQueryHistory:view")
	@RequestMapping(value = "form")
	public String form(FwmQueryHistory fwmQueryHistory, Model model) {
		model.addAttribute("fwmQueryHistory", fwmQueryHistory);
		return "modules/fwzs/fwmQueryHistoryForm";
	}

	@RequiresPermissions("fwzs:fwmQueryHistory:edit")
	@RequestMapping(value = "save")
	public String save(FwmQueryHistory fwmQueryHistory, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, fwmQueryHistory)){
			return form(fwmQueryHistory, model);
		}
		fwmQueryHistoryService.save(fwmQueryHistory);
		addMessage(redirectAttributes, "保存防伪查询记录表成功");
		return "redirect:"+Global.getAdminPath()+"/fwzs/fwmQueryHistory/?repage";
	}
	
	@RequiresPermissions("fwzs:fwmQueryHistory:edit")
	@RequestMapping(value = "delete")
	public String delete(FwmQueryHistory fwmQueryHistory, RedirectAttributes redirectAttributes) {
		fwmQueryHistoryService.delete(fwmQueryHistory);
		addMessage(redirectAttributes, "删除防伪查询记录表成功");
		return "redirect:"+Global.getAdminPath()+"/fwzs/fwmQueryHistory/?repage";
	}

	/**
	 * 查找无效的防伪码
	 * @param fwmQueryHistory
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("fwzs:fwmQueryHistory:view")
	@RequestMapping(value = "invalidList")
	public String invalidList(FwmQueryHistory fwmQueryHistory, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<FwmQueryHistory> page = fwmQueryHistoryService.findInvalidList(new Page<FwmQueryHistory>(request, response), fwmQueryHistory);
		model.addAttribute("page", page);
		return "modules/fwzs/fwmQueryInvalidHistoryList";
	}

}