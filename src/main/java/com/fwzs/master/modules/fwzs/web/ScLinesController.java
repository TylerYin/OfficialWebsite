/**
 * 
 */
package com.fwzs.master.modules.fwzs.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fwzs.master.modules.sys.utils.UserUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fwzs.master.common.config.Global;
import com.fwzs.master.common.persistence.Page;
import com.fwzs.master.common.web.BaseController;
import com.fwzs.master.common.utils.StringUtils;
import com.fwzs.master.modules.fwzs.entity.ScLines;
import com.fwzs.master.modules.fwzs.service.ScLinesService;

import java.util.HashMap;

/**
 * 生产线Controller
 * @author ly
 * @version 2017-09-30
 */
@Controller
@RequestMapping(value = "${adminPath}/fwzs/scLines")
public class ScLinesController extends BaseController {

	@Autowired
	private ScLinesService scLinesService;
	
	@ModelAttribute
	public ScLines get(@RequestParam(required=false) String id) {
		ScLines entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = scLinesService.get(id);
		}
		if (entity == null){
			entity = new ScLines();
		}
		return entity;
	}
	
	@RequiresPermissions("fwzs:scLines:view")
	@RequestMapping(value = {"list", ""})
	public String list(ScLines scLines, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ScLines> page = scLinesService.findPage(new Page<ScLines>(request, response), scLines); 
		model.addAttribute("page", page);
		return "modules/fwzs/scLinesList";
	}

	@RequiresPermissions("fwzs:scLines:view")
	@RequestMapping(value = "form")
	public String form(ScLines scLines, Model model) {
		model.addAttribute("scLines", scLines);
		return "modules/fwzs/scLinesForm";
	}
	
	@ResponseBody
	@RequestMapping(value = "/validateCode/{lineNo}", method = RequestMethod.GET)
	public boolean validateCode(@PathVariable("lineNo") String lineNo) {
		final HashMap searchCondition = new HashMap<String,String>();
		searchCondition.put("companyId", UserUtils.getUser().getCompany().getId());
		searchCondition.put("lineNo", lineNo);
		searchCondition.put("delFlag", 0);
		return scLinesService.getRowCountByLineNo(searchCondition) > 0 ? false : true;
	}

	@RequiresPermissions("fwzs:scLines:edit")
	@RequestMapping(value = "save")
	public String save(ScLines scLines, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, scLines)){
			return form(scLines, model);
		}
		scLinesService.save(scLines);
		addMessage(redirectAttributes, "保存生产线成功");
		return "redirect:"+Global.getAdminPath()+"/fwzs/scLines/?repage";
	}
	
	@RequiresPermissions("fwzs:scLines:edit")
	@RequestMapping(value = "delete")
	public String delete(ScLines scLines, RedirectAttributes redirectAttributes) {
		scLinesService.delete(scLines);
		addMessage(redirectAttributes, "删除生产线成功");
		return "redirect:"+Global.getAdminPath()+"/fwzs/scLines/?repage";
	}

}