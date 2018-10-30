/**
 * 
 */
package com.fwzs.master.modules.fwzs.web;

import com.fwzs.master.common.config.Global;
import com.fwzs.master.common.persistence.Page;
import com.fwzs.master.common.utils.StringUtils;
import com.fwzs.master.common.web.BaseController;
import com.fwzs.master.modules.fwzs.entity.ScLines;
import com.fwzs.master.modules.fwzs.entity.ScPlan;
import com.fwzs.master.modules.fwzs.service.ScLinesService;
import com.fwzs.master.modules.fwzs.service.ScPlanService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 任务数据管理Controller
 * 
 * @author ly
 * @version 2017-09-30
 */
@Controller
@RequestMapping(value = "${adminPath}/fwzs/manager/scPlan")
public class ScPlanManagerController extends BaseController {

	@Autowired
	private ScPlanService scPlanService;
	@Autowired
	private ScLinesService scLinesService;
	@ModelAttribute
	public ScPlan get(@RequestParam(required = false) String id) {
		ScPlan entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = scPlanService.getByIdAndDelFlag(id);
		}
		if (entity == null) {
			entity = new ScPlan();
		}
		return entity;
	}

	@RequiresPermissions("fwzs:scPlan:view")
	@RequestMapping(value = { "list", "" })
	public String list(ScPlan scPlan, HttpServletRequest request,
			HttpServletResponse response, Model model) {
		scPlan.setStatus(Global.DRAFT);
		Page<ScPlan> page = scPlanService.findPlanByStatusIsNotDraft(new Page<ScPlan>(request,
				response), scPlan);
		model.addAttribute("page", page);
		return "modules/fwzs/scPlanListManager";
	}
	
	@RequiresPermissions("fwzs:scPlan:view")
	@RequestMapping(value = "form")
	public String form(ScPlan scPlan, Model model) {
		List<ScLines> scLines = scLinesService.findListForScPlan(new ScLines());
        model.addAttribute("users", scPlanService.findQcUser());
		model.addAttribute("scPlan", scPlan);
		model.addAttribute("scLines", scLines);
		return "modules/fwzs/scDataManageForm";
	}

	@RequiresPermissions("fwzs:scPlan:edit")
	@RequestMapping(value = "save")
	public String save(ScPlan scPlan, Model model,
			RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, scPlan)) {
			return form(scPlan, model);
		}
		if (StringUtils.isBlank(scPlan.getId())) {
			//scPlan.setRealNumber("0");
			scPlan.setStatus("2");
		}
		scPlanService.save(scPlan);
		addMessage(redirectAttributes, "保存任务计划成功");
		return "redirect:" + Global.getAdminPath() + "/fwzs/manager/scPlan/?repage";
	}

	@RequiresPermissions("fwzs:scPlan:edit")
	@RequestMapping(value = "delete")
	public String delete(ScPlan scPlan, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		String mappingId = request.getParameter("mappingId");
		scPlanService.deleteSCPlanAndMapping(mappingId, scPlan);
		addMessage(redirectAttributes, "删除任务计划成功");
		return "redirect:" + Global.getAdminPath() + "/fwzs/manager/scPlan/?repage";
	}

}