/**
 * 
 */
package com.fwzs.master.modules.fwzs.web;

import java.io.File;
import java.util.List;

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
import com.fwzs.master.common.utils.DateUtils;
import com.fwzs.master.common.utils.FileUtils;
import com.fwzs.master.common.utils.IdGen;
import com.fwzs.master.common.utils.StringUtils;
import com.fwzs.master.common.web.BaseController;
import com.fwzs.master.common.web.Servlets;
import com.fwzs.master.modules.fwzs.entity.BsProduct;
import com.fwzs.master.modules.fwzs.entity.FwmFile;
import com.fwzs.master.modules.fwzs.service.BsProductService;
import com.fwzs.master.modules.fwzs.service.FwmFileService;
import com.fwzs.master.modules.fwzs.service.FwmQrcodeService;

/**
 * 防伪码文件列表Controller
 * @author ly
 * @version 2017-09-30
 */
@Controller
@RequestMapping(value = "${adminPath}/fwzs/fwmFile")
public class FwmFileController extends BaseController {

	@Autowired
	private FwmFileService fwmFileService;
	@Autowired
	private BsProductService bsProductService;
	@Autowired
	private FwmQrcodeService fwmQrcodeService;
	@ModelAttribute
	public FwmFile get(@RequestParam(required=false) String id) {
		FwmFile entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = fwmFileService.get(id);
		}
		if (entity == null){
			entity = new FwmFile();
		}
		return entity;
	}
	
	@RequiresPermissions("fwzs:fwmFile:view")
	@RequestMapping(value = {"list", ""})
	public String list(FwmFile fwmFile, HttpServletRequest request, HttpServletResponse response, Model model) {
		final List<BsProduct> bsProducts = bsProductService.findList(new BsProduct());
		if (null != fwmFile.getBsProduct() && StringUtils.isNotEmpty(fwmFile.getBsProduct().getId())) {
			fwmFile.getBsProduct().setProdName(bsProductService.get(fwmFile.getBsProduct().getId()).getProdName());
		}
		Page<FwmFile> page = fwmFileService.findPage(new Page<FwmFile>(request, response), fwmFile);
		model.addAttribute("page", page);
		model.addAttribute("bsProducts", bsProducts);
		return "modules/fwzs/fwmFileList";
	}

	@RequiresPermissions("fwzs:fwmFile:view")
	@RequestMapping(value = "form")
	public String form(FwmFile fwmFile, Model model) {
		String fileName=IdGen.genProdFileSerialNum(3,"CF");
		fwmFile.setFileName(fileName);
		fwmFile.setBsProduct(new BsProduct());
		//查询所有产品
		List<BsProduct> bsProducts= bsProductService.findList(new BsProduct());
		
		model.addAttribute("fwmFile", fwmFile);
		model.addAttribute("bsProducts", bsProducts);
		return "modules/fwzs/fwmFileForm";
	}
	
	@RequiresPermissions("fwzs:fwmFile:edit")
	@RequestMapping(value = "save")
	public String save(FwmFile fwmFile,HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
		String fileName = "";
		FwmFile fwmFile2;
//		String path = request.getSession().getServletContext()
//				.getRealPath("upload");
		String ymd=DateUtils.getDate("yyMMdd");
		String path = Global.getUserfilesBaseDir() + "userfiles/qrCodeFile/"+ymd;
		String fileUrl = FileUtils.path(Servlets.getRequest().getContextPath() + Global.USERFILES_BASE_URL  + "qrCodeFile/"+ymd) + fileName;
		if (!beanValidator(model, fwmFile)){
			return form(fwmFile, model);
		}
		//fileName = DateUtils.getDate("yyyyMMddHHmmss") + ".txt";
		fileName=fwmFile.getFileName()+".txt";
		//获取产品信息
		BsProduct bsProduct= bsProductService.get(fwmFile.getBsProduct().getId());
		// 生码并写入文件
		List<String> codes = fwmFileService.genCode(bsProduct,
				fwmFile.getCodeNumber(), path + "/" + fileName);
		if (null != codes) {
			// 生成码文件入库
			fwmFile.setFileName(fileName);
			fwmFileService
					.save(fwmFile, fileUrl, String.valueOf(codes.size()));
			//获取防伪码文件信息
			 fwmFile2=	fwmFileService.findList(fwmFile).get(0);
			// 生码到数据库
			fwmQrcodeService.saveByBatchaPre(codes,bsProduct,fwmFile2);
						
			addMessage(redirectAttributes, "保存防伪码文件列表成功");
		}
		
		return "redirect:"+Global.getAdminPath()+"/fwzs/fwmFile/?repage";
	}
	
	@RequiresPermissions("fwzs:fwmFile:edit")
	@RequestMapping(value = "delete")
	public String delete(FwmFile fwmFile,  HttpServletRequest request, RedirectAttributes redirectAttributes) {
		fwmFile.setId(request.getParameter("fwmFileId"));
		fwmFileService.delete(fwmFile);
		addMessage(redirectAttributes, "删除防伪码文件列表成功");
		final String fileName = request.getParameter("fileName");
		final String createDate = request.getParameter("createDate");
		final String prodId = request.getParameter("prodId");
		return "redirect:" + Global.getAdminPath() + "/fwzs/fwmFile?fileName=" + fileName + "&createDate=" + createDate + "&bsProduct.id=" + prodId + "&repage";
	}
	
	@RequiresPermissions("fwzs:fwmFile:edit")
	@RequestMapping(value = "download")
	public void download(FwmFile fwmFile,HttpServletRequest request,HttpServletResponse response) {
		if (null!=fwmFile) {
			String filePath=FileUtils.path(fwmFile.getFileUrl().replace(request.getContextPath(),Global.getUserfilesBaseDir())+"/")+fwmFile.getFileName();
			FileUtils.downFile(new File(filePath), request, response);
		}
	}

}