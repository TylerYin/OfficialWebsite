/**
 * 
 */
package com.fwzs.master.modules.fwzs.web;

import com.fwzs.master.common.config.Global;
import com.fwzs.master.common.persistence.Page;
import com.fwzs.master.common.utils.StringUtils;
import com.fwzs.master.common.web.BaseController;
import com.fwzs.master.modules.fwzs.entity.BsProduct;
import com.fwzs.master.modules.fwzs.entity.FwmFile;
import com.fwzs.master.modules.fwzs.entity.ScLines;
import com.fwzs.master.modules.fwzs.entity.ScPlan;
import com.fwzs.master.modules.fwzs.service.FwmFileService;
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
import java.util.Collections;
import java.util.List;

/**
 * 任务数据管理Controller
 * 
 * @author Tyler
 * @version 2017-10-29
 */
@Controller
@RequestMapping(value = "${adminPath}/fwzs/dataMaintenance/scPlan")
public class ScPlanDataMaintenanceController extends BaseController {

	@Autowired
	private ScLinesService scLinesService;
	@Autowired
	private ScPlanService scPlanService;
    @Autowired
    private FwmFileService fwmFileService;

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
	@RequestMapping(value = "form")
	public String form(ScPlan scPlan, Model model, HttpServletRequest request) {
		List<ScLines> scLines = scLinesService.findListForScPlan(new ScLines());
        model.addAttribute("users", scPlanService.findQcUser());
		model.addAttribute("scPlan", scPlan);
		model.addAttribute("scLines", scLines);
		return "modules/fwzs/scDataMaintenanceForm";
	}

    @RequiresPermissions("fwzs:scPlan:edit")
    @RequestMapping(value = "save")
    public String save(ScPlan scPlan, Model model,
                       RedirectAttributes redirectAttributes, HttpServletRequest request) {
        if (!beanValidator(model, scPlan)) {
            return form(scPlan, model, request);
        }

        if (StringUtils.isBlank(scPlan.getStatus())) {
            scPlan.setStatus(Global.AUDITING);
        }

        scPlanService.save(scPlan);
        deleteScPlan(request);
        addMessage(redirectAttributes, "保存任务计划成功");
        return "redirect:" + Global.getAdminPath() + "/fwzs/dataMaintenance/scPlan";
    }

    private void deleteScPlan(final HttpServletRequest request){
        final String deleteKyes = request.getParameter("deleteKey");
        if(StringUtils.isNotEmpty(deleteKyes)){
            scPlanService.deleteSCPlanAndMapping(deleteKyes);
        }
    }

    /**
     * 查询指定产品的所有码文件
     *
     * @param prodId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "findFwmFileByProduct")
    public List<FwmFile> findFwmFileByProduct(final String prodId) {
        if (StringUtils.isNotBlank(prodId)) {
            final BsProduct bsProduct = new BsProduct();
            bsProduct.setId(prodId);
            final FwmFile fwmFile = new FwmFile();
            fwmFile.setBsProduct(bsProduct);
            return fwmFileService.findList(fwmFile);
        } else {
            return Collections.emptyList();
        }
    }

	@RequiresPermissions("fwzs:scPlan:edit")
	@RequestMapping(value = "deleteScPlan")
	public String delete(ScPlan scPlan, RedirectAttributes redirectAttributes) {
		scPlanService.delete(scPlan);
		addMessage(redirectAttributes, "删除任务计划成功");
		return "redirect:" + Global.getAdminPath() + "/fwzs/dataMaintenance/scPlan/?repage";
	}

	@RequiresPermissions("fwzs:scPlan:view")
	@RequestMapping(value = { "list", "" })
	public String list(ScPlan scPlan, HttpServletRequest request,
									  HttpServletResponse response, Model model) {
		scPlan.setStatus(Global.DRAFT);
		Page<ScPlan> page = scPlanService.findPlanByStatusIsNotDraft(new Page<ScPlan>(request,
				response), scPlan);
		model.addAttribute("page", page);
		return "modules/fwzs/dataMaintenanceList";
	}
}