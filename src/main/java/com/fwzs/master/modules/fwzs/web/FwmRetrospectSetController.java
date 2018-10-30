/**
 * 
 */
package com.fwzs.master.modules.fwzs.web;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fwzs.master.common.utils.StringUtils;
import com.fwzs.master.common.web.BaseController;
import com.fwzs.master.modules.fwzs.entity.BsProduct;
import com.fwzs.master.modules.fwzs.entity.FwmRetrospectSet;
import com.fwzs.master.modules.fwzs.service.BsProductService;
import com.fwzs.master.modules.fwzs.service.FwmRetrospectSetService;

/**
 * 追溯展示设置Controller
 * @author ly
 * @version 2017-09-30
 */
@Controller
@RequestMapping(value = "${adminPath}/fwzs/retrospectSet")
public class FwmRetrospectSetController extends BaseController {

	@Autowired
	private FwmRetrospectSetService fwmRetrospectSetService;
	@Autowired
	private BsProductService bsProductService;
	@ModelAttribute
	public FwmRetrospectSet get(@RequestParam(required=false) String id) {
		FwmRetrospectSet entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = fwmRetrospectSetService.get(id);
		}
		if (entity == null){
			entity = new FwmRetrospectSet();
		}
		return entity;
	}
	
	@RequestMapping(value = "form")
	public String form(Model model, BsProduct fwmProduce) {
		// 获取企业所有的产品
		List<BsProduct> fwmProduces = bsProductService.findList(fwmProduce);

		model.addAttribute("fwmProduces", fwmProduces);

		return "modules/fwzs/retrospectSetForm";
	}

	@ResponseBody
	@RequestMapping(value = "selectProductProperty")
	public String selectProductProperty(HttpServletResponse response,Model model,String productId) {
		HashMap<String,Object> map=new HashMap<String,Object>();
		FwmRetrospectSet fwmRetrospectSet=null;
		// 获取所有的溯源属性
//		List<KeyValue> propertys = qrcodeService.getAllColomn();
		//去掉不用的属性
		
		if (StringUtils.isNoneBlank(productId)) {
			//根据产品获取已选溯源属性
		 fwmRetrospectSet=	fwmRetrospectSetService.getRetrospectByProductId(productId);
		 map.put("fwmRetrospectSet",fwmRetrospectSet);
		}
//		map.put("propertys",propertys);
		// fwmQueryHistoryService.save(fwmQueryHistory);
//		model.addAttribute("propertys", propertys);
		 renderString(response, map);
		 return null;
	}
	
	@RequestMapping(value = "save")
	@ResponseBody
	public boolean save(Model model,FwmRetrospectSet fwmRetrospectSet, RedirectAttributes redirectAttributes,HttpServletRequest request,String pId,String productId) {
		if (StringUtils.isNoneBlank(pId)&&StringUtils.isNotBlank(productId)) {
			//pro
			fwmRetrospectSet.setProdId(productId);
			fwmRetrospectSet.setProperty(pId);
			fwmRetrospectSetService.save(fwmRetrospectSet);
		}

		return true;
	}
}