/**
 * 
 */
package com.fwzs.master.modules.fwzs.web;

import com.fwzs.master.common.config.Global;
import com.fwzs.master.common.persistence.Page;
import com.fwzs.master.common.utils.StringUtils;
import com.fwzs.master.common.web.BaseController;
import com.fwzs.master.modules.fwzs.entity.BsProduct;
import com.fwzs.master.modules.fwzs.entity.ScLines;
import com.fwzs.master.modules.fwzs.entity.ScPlan;
import com.fwzs.master.modules.fwzs.entity.ScPlanQc;
import com.fwzs.master.modules.fwzs.service.BsProductService;
import com.fwzs.master.modules.fwzs.service.ScLinesService;
import com.fwzs.master.modules.fwzs.service.ScPlanService;
import com.fwzs.master.modules.sys.utils.UserUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 任务数据检验报告Controller
 * 
 * @author ly
 * @version 2017-09-30
 */
@Controller
@RequestMapping(value = "${adminPath}/fwzs/check/scPlan")
public class ScPlanCheckController extends BaseController {

	@Autowired
	private ScPlanService scPlanService;
	@Autowired
	private BsProductService bsProductService;
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
    @RequestMapping(value = {"list", ""})
    public String list(ScPlan scPlan, HttpServletRequest request,
                       HttpServletResponse response, Model model) {
        final List<String> statusList = new ArrayList<>();
        final String scPlanStatus = request.getParameter("scPlanType");
        if (StringUtils.isBlank(scPlanStatus)) {
            statusList.add(Global.COMPLETED);
        } else {
            statusList.add(scPlanStatus);
        }

        model.addAttribute("scPlanType", statusList.get(0));
        scPlan.setStatusList(statusList);

        Page<ScPlan> page;
        if (StringUtils.containsIgnoreCase(UserUtils.getUser().getName(), "zj")) {
            page = scPlanService.findPlanByQC(new Page<ScPlan>(request, response), scPlan);
        } else {
            page = scPlanService.findPlanByStatus(new Page<ScPlan>(request, response), scPlan);
        }

        model.addAttribute("page", page);
        return "modules/fwzs/scPlanListCheck";
    }

    @RequiresPermissions("fwzs:scPlan:view")
    @RequestMapping(value = { "findPlanQcFaildList"})
    public String findPlanQcFaildList(ScPlan scPlan, HttpServletRequest request,
                       HttpServletResponse response, Model model) {
        final List<String> statusList = new ArrayList<>();
        statusList.add(Global.QUALITY_CONTROL_NOT_PASS);
        scPlan.setStatusList(statusList);
        Page<ScPlan> page = scPlanService.findPlanByStatus(new Page<ScPlan>(request,
                response), scPlan);
        model.addAttribute("page", page);
        return "modules/fwzs/scPlanQcFaildList";
    }

    @RequiresPermissions("fwzs:scPlan:view")
    @RequestMapping(value = "qcFaildForm")
    public String qcFaildForm(ScPlan scPlan, HttpServletRequest request, Model model) {
        // 查询所有产品
        List<BsProduct> bsProducts = bsProductService.findList(new BsProduct());
        //生产线
        List<ScLines> scLines= scLinesService.findListForScPlan(new ScLines());
        model.addAttribute("bsProducts", bsProducts);
        model.addAttribute("scPlan", scPlan);
        model.addAttribute("scLines",scLines);
        return "modules/fwzs/scPlanQcFaildForm";
    }

    @RequiresPermissions("fwzs:scPlan:view")
	@RequestMapping(value = "form")
	public String form(ScPlan scPlan, HttpServletRequest request, Model model) {
		// 查询所有产品
		List<BsProduct> bsProducts = bsProductService.findList(new BsProduct());

        String scPlanType = request.getParameter("scPlanType");
        if(StringUtils.isNotBlank(scPlanType)){
            model.addAttribute("scPlanType", scPlanType);
        }

		//生产线
		List<ScLines> scLines= scLinesService.findListForScPlan(new ScLines());
		model.addAttribute("bsProducts", bsProducts);
		model.addAttribute("scPlan", scPlan);
		model.addAttribute("scLines",scLines);
		return "modules/fwzs/scPlanFormCheck";
	}

    @RequiresPermissions("fwzs:scPlan:edit")
    @RequestMapping(value = "save")
    public String save(ScPlan scPlan, Model model, HttpServletRequest request,
                       RedirectAttributes redirectAttributes) {
        scPlanService.save(scPlan);
        addMessage(redirectAttributes, "保存任务计划成功");
        if (StringUtils.isNotBlank(request.getParameter("editQcNotPassPlan"))) {
            return "redirect:" + Global.getAdminPath() + "/fwzs/check/scPlan/findPlanQcFaildList/?repage";
        }
        return "redirect:" + Global.getAdminPath() + "/fwzs/check/scPlan/?repage&scPlanType="
                + request.getParameter("scPlanType");
    }

    /**
     * 根据生产计划主键查找质检没有通过列表
     *
     * @param planId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "findQcNotPass")
    public List<ScPlanQc> findQcNotPass(String planId) {
        return scPlanService.findQcNotPass(planId);
    }

	@RequiresPermissions("fwzs:scPlan:edit")
	@RequestMapping(value = "delete")
	public String delete(ScPlan scPlan, RedirectAttributes redirectAttributes) {
		scPlanService.delete(scPlan);
		addMessage(redirectAttributes, "删除任务计划成功");
		return "redirect:" + Global.getAdminPath() + "/fwzs/check/scPlan/?repage";
	}

}