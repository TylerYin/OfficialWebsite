/**
 * 
 */
package com.fwzs.master.modules.fwzs.web;

import com.fwzs.master.common.config.Global;
import com.fwzs.master.common.persistence.Page;
import com.fwzs.master.common.utils.IdGen;
import com.fwzs.master.common.utils.StringUtils;
import com.fwzs.master.common.web.BaseController;
import com.fwzs.master.modules.fwzs.entity.BsProduct;
import com.fwzs.master.modules.fwzs.entity.ScLines;
import com.fwzs.master.modules.fwzs.entity.ScPlan;
import com.fwzs.master.modules.fwzs.service.BsProductService;
import com.fwzs.master.modules.fwzs.service.ScLinesService;
import com.fwzs.master.modules.fwzs.service.ScPlanService;
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
 * 任务计划Controller
 * 
 * @author ly
 * @version 2017-09-30
 */
@Controller
@RequestMapping(value = "${adminPath}/fwzs/scPlan")
public class ScPlanController extends BaseController {

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
	@RequestMapping(value = { "list", "" })
	public String list(ScPlan scPlan, HttpServletRequest request,
			HttpServletResponse response, Model model) {
		scPlan.setStatus(Global.DRAFT);
		Page<ScPlan> page = scPlanService.findPage(new Page<ScPlan>(request,
				response), scPlan);
		model.addAttribute("page", page);
		return "modules/fwzs/scPlanList";
	}
	
	@ResponseBody
	@RequestMapping(value = "updateStatus")
	public int updatePlanStatus(String planIds, ScPlan scPlan) {
		scPlan.setStatus(Global.AUDITING);
		if (StringUtils.isNoneEmpty(planIds)) {
			final List<String> planList = new ArrayList<String>();
			for (String planId : planIds.split(",")) {
				planList.add(planId.trim());
			}
			scPlan.setPlanList(planList);
			return scPlanService.updatePlanStatus(scPlan);
		}
		return 0;
	}

	@RequiresPermissions("fwzs:scPlan:view")
	@RequestMapping(value = "form")
	public String form(ScPlan scPlan, Model model, HttpServletRequest request) {
		// 查询所有产品
		List<BsProduct> bsProducts = bsProductService.findList(new BsProduct());
		if (StringUtils.isBlank(scPlan.getId())) {
			scPlan.setPlanNo(IdGen.genProdFileSerialNum(3, "SC"));
			final List<BsProduct> bsProductList = new ArrayList<>();
			final BsProduct bsProduct = new BsProduct();
			bsProduct.setBatchNo(IdGen.genProdFileSerialNum(3, "BN"));
			bsProductList.add(bsProduct);
			scPlan.setBsProductList(bsProductList);
			model.addAttribute("newPlan", "newPlan");
		}

		// 生产线
		List<ScLines> scLines = scLinesService.findListForScPlan(new ScLines());
        model.addAttribute("scLines", scLines);

        model.addAttribute("users", scPlanService.findQcUser());

		model.addAttribute("bsProducts", bsProducts);
		model.addAttribute("scPlan", scPlan);

		final String formTitle = request.getParameter("formTitle");
		model.addAttribute("formTitle", formTitle);

		return "modules/fwzs/scPlanForm";
	}

	@RequiresPermissions("fwzs:scPlan:view")
	@RequestMapping(value = "review")
	public String review(Model model) {
        model.addAttribute("users", scPlanService.findQcUser());
		final List<ScLines> scLines = scLinesService.findListForScPlan(new ScLines());
		model.addAttribute("scLines", scLines);
		return "modules/fwzs/scPlanReview";
	}

	@ResponseBody
	@RequestMapping(value = "generateBatchNo")
	public String generateBatchNo(){
		return IdGen.genProdFileSerialNum(3, "BN");
	}

	@RequiresPermissions("fwzs:scPlan:edit")
	@RequestMapping(value = "save")
	public String save(ScPlan scPlan, Model model,
			RedirectAttributes redirectAttributes, HttpServletRequest request) {
        if (!beanValidator(model, scPlan)) {
            return form(scPlan, model, request);
        }

        if (StringUtils.isBlank(scPlan.getId())) {
            scPlan.setStatus(Global.DRAFT);
        }

        scPlanService.save(scPlan);
		deleteScPlan(request);
		addMessage(redirectAttributes, "保存任务计划成功");

		final String formTitle = request.getParameter("formTitle");
		if(StringUtils.isEmpty(formTitle) || Global.PLANMANAGEMENT.equalsIgnoreCase(formTitle)){
			return "redirect:" + Global.getAdminPath() + "/fwzs/scPlan/?repage";
		} else {
			return "redirect:" + Global.getAdminPath() + "/fwzs/manager/scPlan";
		}
	}

	@RequiresPermissions("fwzs:scPlan:edit")
	@RequestMapping(value = "delete")
	public String delete(ScPlan scPlan, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		String prodId = request.getParameter("mappingId");
		scPlanService.deleteSCPlanAndMapping(prodId, scPlan);
		addMessage(redirectAttributes, "删除任务计划成功");
		return "redirect:" + Global.getAdminPath() + "/fwzs/scPlan/?repage";
	}

	@RequiresPermissions("fwzs:scPlan:edit")
	@RequestMapping(value = "deleteScPlan")
	public String delete(ScPlan scPlan, RedirectAttributes redirectAttributes) {
		scPlanService.delete(scPlan);
		addMessage(redirectAttributes, "删除任务计划成功");
		return "redirect:" + Global.getAdminPath() + "/fwzs/scPlan/?repage";
	}

	private void deleteScPlan(final HttpServletRequest request){
		final String deleteKyes = request.getParameter("deleteKey");
		if(StringUtils.isNotEmpty(deleteKyes)){
            scPlanService.deleteSCPlanAndMapping(deleteKyes);
		}
	}

}