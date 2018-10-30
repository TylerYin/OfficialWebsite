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
import com.fwzs.master.modules.fwzs.entity.FwmSpec;
import com.fwzs.master.modules.fwzs.service.FwmSpecService;

import java.util.HashMap;

/**
 * 产品规格Controller
 * @author ly
 * @version 2017-09-30
 */
@Controller
@RequestMapping(value = "${adminPath}/fwzs/fwmSpec")
public class FwmSpecController extends BaseController {

	@Autowired
	private FwmSpecService fwmSpecService;
	
	@ModelAttribute
	public FwmSpec get(@RequestParam(required=false) String id) {
		FwmSpec entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = fwmSpecService.get(id);
		}
		if (entity == null){
			entity = new FwmSpec();
		}
		return entity;
	}
	
	@RequiresPermissions("fwzs:fwmSpec:view")
	@RequestMapping(value = {"list", ""})
	public String list(FwmSpec fwmSpec, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<FwmSpec> page = fwmSpecService.findPage(new Page<FwmSpec>(request, response), fwmSpec); 
		model.addAttribute("page", page);
		return "modules/fwzs/fwmSpecList";
	}

	@RequiresPermissions("fwzs:fwmSpec:view")
	@RequestMapping(value = "form")
	public String form(FwmSpec fwmSpec, Model model) {
		model.addAttribute("fwmSpec", fwmSpec);
		return "modules/fwzs/fwmSpecForm";
	}
	
	@ResponseBody
	@RequestMapping(value = "/validateCode/{specCode}", method = RequestMethod.GET)
	public boolean validateCode(@PathVariable("specCode") String specCode) {
		final HashMap searchCondition = new HashMap<String,String>();
		searchCondition.put("companyId", UserUtils.getUser().getCompany().getId());
		searchCondition.put("specCode", specCode);
		searchCondition.put("delFlag", 0);
		return fwmSpecService.getRowCountBySpecCode(searchCondition) > 0 ? false : true;
	}

	@RequiresPermissions("fwzs:fwmSpec:edit")
	@RequestMapping(value = "save")
	public String save(FwmSpec fwmSpec, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, fwmSpec)){
			return form(fwmSpec, model);
		}
		fwmSpecService.save(fwmSpec);
		addMessage(redirectAttributes, "保存产品规格成功");
		return "redirect:"+Global.getAdminPath()+"/fwzs/fwmSpec/?repage";
	}
	
	@RequiresPermissions("fwzs:fwmSpec:edit")
	@RequestMapping(value = "delete")
	public String delete(FwmSpec fwmSpec, RedirectAttributes redirectAttributes) {
		fwmSpecService.delete(fwmSpec);
		addMessage(redirectAttributes, "删除产品规格成功");
		return "redirect:"+Global.getAdminPath()+"/fwzs/fwmSpec/?repage";
	}

}