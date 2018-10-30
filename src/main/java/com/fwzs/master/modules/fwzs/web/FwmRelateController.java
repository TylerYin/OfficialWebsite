/**
 * 
 */
package com.fwzs.master.modules.fwzs.web;

import com.fwzs.master.common.config.Global;
import com.fwzs.master.common.persistence.Page;
import com.fwzs.master.common.utils.StringUtils;
import com.fwzs.master.common.web.BaseController;
import com.fwzs.master.modules.fwzs.entity.FwmRelate;
import com.fwzs.master.modules.fwzs.service.BsProductService;
import com.fwzs.master.modules.fwzs.service.FwmQrcodeService;
import com.fwzs.master.modules.fwzs.service.FwmRelateService;
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
 * 防伪码关联Controller
 * 
 * @author yjd
 * @version 2017-10-08
 */
@Controller
@RequestMapping(value = "${adminPath}/fwzs/fwmRelate")
public class FwmRelateController extends BaseController {

	@Autowired
	private FwmRelateService fwmRelateService;

	@Autowired
	private FwmQrcodeService fwmQrcodeService;

    @Autowired
    private ScPlanService scPlanService;

    @Autowired
    private BsProductService bsProductService;

	@ModelAttribute
	public FwmRelate get(@RequestParam(required = false) String id) {
		FwmRelate entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = fwmRelateService.get(id);
		}
		if (entity == null) {
			entity = new FwmRelate();
		}
		return entity;
	}

	@RequiresPermissions("fwzs:fwmRelate:view")
	@RequestMapping(value = { "list", "" })
	public String list(FwmRelate fwmRelate, HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Page<FwmRelate> page = fwmRelateService.findPage(new Page<FwmRelate>(
				request, response), fwmRelate);
		model.addAttribute("page", page);
		model.addAttribute("fwmRelate1", fwmRelate);
		return "modules/fwzs/fwmQrcodeRelate";
	}

    /**
     * 导出防伪码
     *
     * @param fwmRelate
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("fwzs:fwmRelate:view")
    @RequestMapping(value = "export")
    public String export(FwmRelate fwmRelate, HttpServletRequest request,
                         HttpServletResponse response, Model model) {
        List<FwmRelate> fwmRelates = fwmRelateService.findList(fwmRelate);
        if (null != fwmRelate.getScPlan()) {
            if (StringUtils.isNotBlank(fwmRelate.getScPlan().getId())) {
                model.addAttribute("scPlan", scPlanService.get(fwmRelate.getScPlan().getId()));
            }
            if (null != fwmRelate.getScPlan().getBsProduct() && StringUtils.isNotBlank(fwmRelate.getScPlan().getBsProduct().getId())) {
                model.addAttribute("bsProduct", bsProductService.get(fwmRelate.getScPlan().getBsProduct().getId()));
            }
        }
        model.addAttribute("fwmRelates", fwmRelates);
        return "fwmRelateExcel";
    }

	@RequiresPermissions("fwzs:fwmRelate:view")
	@RequestMapping(value = "form")
	public String form(FwmRelate fwmRelate, Model model) {
		model.addAttribute("fwmRelate", fwmRelate);
		return "modules/fwzs/fwmRelateForm";
	}

	/**
	 * 关联码列表
	 * 
	 * @param fwmRelate
	 * @param model
	 * @return
	 */
	@RequiresPermissions("fwzs:fwmRelate:view")
	@RequestMapping(value = "relate")
	public String relate(FwmRelate fwmRelate, HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Page<FwmRelate> page = fwmRelateService.findPage(new Page<FwmRelate>(
				request, response), fwmRelate);
		model.addAttribute("page", page);
		model.addAttribute("fwmRelate1", fwmRelate);
		System.out.println(page.getList());
		return "modules/fwzs/fwmQrcodeRelate";
	}

	@RequiresPermissions("fwzs:fwmRelate:edit")
	@RequestMapping(value = "save")
	public String save(FwmRelate fwmRelate, Model model,
			RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, fwmRelate)) {
			return form(fwmRelate, model);
		}
		fwmRelateService.save(fwmRelate);
		addMessage(redirectAttributes, "保存防伪码成功");
		return "redirect:" + Global.getAdminPath() + "/fwzs/fwmRelate/?repage";
	}

	@RequiresPermissions("fwzs:fwmRelate:edit")
	@RequestMapping(value = "delete")
	public String delete(FwmRelate fwmRelate, RedirectAttributes redirectAttributes) {
		fwmQrcodeService.delete(fwmRelate.getFwmQrcode());
		addMessage(redirectAttributes, "删除防伪码成功");
		return "redirect:" + Global.getAdminPath() + "/fwzs/fwmRelate?scPlan.id=" + fwmRelate.getScPlan().getId() + "&scPlan.bsProduct.id=" + fwmRelate.getScPlan().getBsProduct().getId() + "&repage";
	}
}