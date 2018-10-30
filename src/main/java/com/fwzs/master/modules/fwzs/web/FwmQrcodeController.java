/**
 * 
 */
package com.fwzs.master.modules.fwzs.web;

import com.fwzs.master.common.config.Global;
import com.fwzs.master.common.persistence.Page;
import com.fwzs.master.common.utils.StringUtils;
import com.fwzs.master.common.web.BaseController;
import com.fwzs.master.modules.fwzs.entity.FwmQrcode;
import com.fwzs.master.modules.fwzs.entity.FwmQuery;
import com.fwzs.master.modules.fwzs.service.FwmQrcodeService;
import com.fwzs.master.modules.fwzs.service.FwmQueryService;
import com.fwzs.master.modules.sys.entity.User;
import com.fwzs.master.modules.sys.utils.UserUtils;
import org.apache.commons.collections.CollectionUtils;
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
 * 防伪码Controller
 * @author yjd
 * @version 2017-10-08
 */
@Controller
@RequestMapping(value = "${adminPath}/fwzs/fwmQrcode")
public class FwmQrcodeController extends BaseController {

	@Autowired
	private FwmQrcodeService fwmQrcodeService;

	@Autowired
	private FwmQueryService fwmQueryService;
	
	@ModelAttribute
	public FwmQrcode get(@RequestParam(required=false) String id) {
		FwmQrcode entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = fwmQrcodeService.get(id);
		}
		if (entity == null){
			entity = new FwmQrcode();
		}
		return entity;
	}
	
	@RequiresPermissions("fwzs:fwmQrcode:view")
	@RequestMapping(value = {"list", ""})
	public String list(FwmQrcode fwmQrcode, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<FwmQrcode> page = fwmQrcodeService.findPage(new Page<FwmQrcode>(request, response), fwmQrcode); 
		model.addAttribute("fwmQrcode", fwmQrcode);
		model.addAttribute("page", page);
		return "modules/fwzs/fwmQrcodeList";
	}

	@RequiresPermissions("fwzs:fwmQrcode:view")
	@RequestMapping(value = "form")
	public String form(FwmQrcode fwmQrcode, Model model) {
		model.addAttribute("fwmQrcode", fwmQrcode);
		return "modules/fwzs/fwmQrcodeForm";
	}
	
	@RequiresPermissions("fwzs:fwmQrcode:edit")
	@RequestMapping(value = "save")
	public String save(FwmQrcode fwmQrcode, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, fwmQrcode)){
			return form(fwmQrcode, model);
		}
		fwmQrcodeService.save(fwmQrcode);
		addMessage(redirectAttributes, "保存防伪码成功");
		return "redirect:"+Global.getAdminPath()+"/fwzs/fwmQrcode/?repage";
	}
	
	@RequiresPermissions("fwzs:fwmQrcode:edit")
	@RequestMapping(value = "delete")
	public String delete(FwmQrcode fwmQrcode, RedirectAttributes redirectAttributes) {
		fwmQrcodeService.delete(fwmQrcode);
		addMessage(redirectAttributes, "删除防伪码成功");
		return "redirect:"+Global.getAdminPath()+"/fwzs/fwmQrcode/?repage";
	}

	@RequiresPermissions("fwzs:fwmQrcode:view")
	@RequestMapping(value = "query")
	public String query(@RequestParam(required = false) String qrCode, Model model) {
		FwmQuery fwmQuery = null;
		if (StringUtils.isNotBlank(qrCode)) {
			User user = UserUtils.getUser();
			if (null != user && StringUtils.isNotBlank(user.getId())) {
				List<FwmQuery> fwmQueryList = fwmQueryService.getFwmInfoByQrCode(qrCode, user.getId(), FwmQuery.DEL_FLAG_NORMAL);
				if (CollectionUtils.isNotEmpty(fwmQueryList)) {
					fwmQuery = fwmQueryList.get(0);
				}
			}
		}

		if (null == fwmQuery) {
			fwmQuery = new FwmQuery();
			fwmQuery.setQrCode(qrCode);
		}
		model.addAttribute("fwmQuery", fwmQuery);
		return "modules/fwzs/fwmQuery";
	}

}