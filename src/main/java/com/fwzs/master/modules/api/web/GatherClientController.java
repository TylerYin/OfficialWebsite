package com.fwzs.master.modules.api.web;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.fwzs.master.common.config.Global;
import com.fwzs.master.common.utils.BaseBeanJson;
import com.fwzs.master.common.utils.BaseBeanListJson;
import com.fwzs.master.common.utils.DateUtils;
import com.fwzs.master.common.utils.StringUtils;
import com.fwzs.master.common.web.BaseController;
import com.fwzs.master.modules.api.entity.BsProductVo;
import com.fwzs.master.modules.api.entity.FwmQrcodeVo;
import com.fwzs.master.modules.api.entity.GatherUploadVo;
import com.fwzs.master.modules.api.entity.ScPlanVo;
import com.fwzs.master.modules.api.service.BsProductApiService;
import com.fwzs.master.modules.api.service.FwmQrcodeApiService;
import com.fwzs.master.modules.api.service.ScPlanApiService;
import com.fwzs.master.modules.fwzs.entity.PdaBound;
import com.fwzs.master.modules.fwzs.entity.ScPlan;
import com.fwzs.master.modules.fwzs.service.InBoundService;
import com.fwzs.master.modules.fwzs.service.ScPlanService;
import com.fwzs.master.modules.sys.entity.Office;
import com.fwzs.master.modules.sys.service.OfficeService;
import com.fwzs.master.modules.sys.utils.DictUtils;

/**
 * 采集端api Controller
 * 
 * @author ly
 * @version 2017-09-30
 */
@Controller
@RequestMapping(value = "/api")
public class GatherClientController extends BaseController {
	@Autowired
	private InBoundService inBoundService;
	@Autowired
	private OfficeService officeService;
	@Autowired
	private BsProductApiService bsProductApiService;
	@Autowired
	private ScPlanApiService planApiService;
	@Autowired
	private ScPlanService planService;
	@Autowired
	private FwmQrcodeApiService fwmQrcodeApiService;

	@RequestMapping(value = { "product", "" })
	@ResponseBody
	public void product(BsProductVo bsProduct, String key,
			HttpServletRequest request, HttpServletResponse response,
			Model model) {

		if (StringUtils.isNoneBlank(bsProduct.getOfficeId())) {
			Office office = officeService.get(bsProduct.getOfficeId());
			if (null != office) {
				List<BsProductVo> bsProductVos = bsProductApiService
						.findList(bsProduct);
				renderString(response, new BaseBeanListJson(0, "成功",
						getProdUints(bsProductVos)));
			} else {
				renderString(response, new BaseBeanJson(1, "公司信息不存在"));
			}
		} else {
			renderString(response, new BaseBeanJson(2, "参数不能为空"));
		}

	}

	/**
	 * 获取单位
	 * 
	 * @return
	 */
	public List<BsProductVo> getProdUints(List<BsProductVo> bsProductVos) {
		for (BsProductVo bsProductVo : bsProductVos) {
			bsProductVo.setProdUnit(DictUtils.getDictLabel(
					bsProductVo.getProdUnit(), Global.PROD_UNIT, "0"));

		}
		return bsProductVos;
	}

	@RequestMapping(value = { "scPlan", "" })
	@ResponseBody
	public void scPlan(ScPlanVo scPlanVo, String key,
			HttpServletRequest request, HttpServletResponse response,
			Model model) {
		if (StringUtils.isNoneBlank(scPlanVo.getOfficeId())) {
			Office office = officeService.get(scPlanVo.getOfficeId());
			if (null != office) {
				scPlanVo.setStatus(Global.AUDITING);
				List<ScPlanVo> scPlanVos = planApiService.findList(scPlanVo);
				renderString(response, new BaseBeanListJson(0, "成功", scPlanVos));
			} else {
				renderString(response, new BaseBeanJson(1, "公司信息不存在"));
			}
		} else {
			renderString(response, new BaseBeanJson(2, "参数不能为空"));
		}
	}

	@RequestMapping(value = { "upload2server", "" })
	@ResponseBody
	public void upload2server(
			@RequestParam(value = "fileData", required = false) MultipartFile file,
			GatherUploadVo gatherUploadVo, HttpServletRequest request,
			HttpServletResponse response, Model model) {
		String savePath = Global.getUserfilesBaseDir() + "gatherClientFile/"
				+ DateUtils.getDate("yyMMdd");
		Long startTime = System.currentTimeMillis();
		if (StringUtils.isBlank(gatherUploadVo.getPlanId())
				|| StringUtils.isBlank(gatherUploadVo.getStatus() + "")
				|| StringUtils.isBlank(gatherUploadVo.getRealNumber() + "")) {
			renderString(response, new BaseBeanJson(5, "参数不能为空"));
		} else {
			execWork(gatherUploadVo, file, savePath, startTime, response);
		}

	}

	public void execWork(GatherUploadVo gatherUploadVo, MultipartFile file,
			String savePath, Long startTime, HttpServletResponse response) {
		// 核对计划号
		if (checkPlanNo(gatherUploadVo.getPlanId())) {
			// 文件上传并解析

			if (null != file) {
				String fileName = file.getOriginalFilename();
				File targFile = uploadFile(file, savePath, fileName);
				if (null != targFile) {
					// 更新计划信息
					ScPlanVo scPlanVo = new ScPlanVo();
					BeanUtils.copyProperties(gatherUploadVo, scPlanVo,
							getNullPropertyNames(gatherUploadVo));

					scPlanVo.setId(gatherUploadVo.getPlanId());
					planApiService.save(scPlanVo);
					// 解析数据
					List<FwmQrcodeVo> fwmQrcodeVos = parseFile(targFile,
							scPlanVo);
					Long endTime = System.currentTimeMillis();
					System.out.println("upload2server——parseFile耗时："
							+ (endTime - startTime) + "毫秒");
					if (fwmQrcodeVos.size() > 0) {
						// 保存码信息（箱码和剁码信息未写逻辑）
						fwmQrcodeApiService.updateByBatch(fwmQrcodeVos);
						//保存箱码信息
//						/保存剁码信息
						
						renderString(response, new BaseBeanJson(0, "文件解析成功"));
						// 调用入库
						
					PdaBound pdaBound=	inBoundService.saveInBoundData(gatherUploadVo
								.getOperateBy(),
								gatherUploadVo.getPlanId(), Integer
										.valueOf(gatherUploadVo
												.getRealNumber()));
						
//						renderString(response,
//								new BaseBeanJson(6,Global.BOUND_STATUS_MSG_MAP.get(pdaBound.getStatus())));
					//入库不成功则打印日志
						if (!"12".equals(pdaBound.getStatus()))
							logger.error("GatherClientController_inboundStatus:"+Global.BOUND_STATUS_MSG_MAP.get(pdaBound.getStatus()));
					} else {
						renderString(response, new BaseBeanJson(1,
								"文件解析失败,上传的文件中无防伪码信息"));
					}

				} else {
					renderString(response, new BaseBeanJson(2, "上传文件异常"));
				}
			} else {
				renderString(response, new BaseBeanJson(3, "无文件信息上传"));
			}
		} else {
			renderString(response, new BaseBeanJson(4, "计划信息不存在"));
		}
	}

	public Boolean checkPlanNo(String planId) {
		Boolean isExist = false;
		ScPlanVo planVo = planApiService.get(planId);
		if (null != planVo) {
			isExist = true;
		}
		return isExist;

	}

	public List<FwmQrcodeVo> parseFile(File file, ScPlanVo scPlanVo) {
		FwmQrcodeVo fwmQrcodeVo;
		List<FwmQrcodeVo> fwmQrcodeVos = new ArrayList<FwmQrcodeVo>();
		FileReader reader = null;
		BufferedReader br = null;
		try {
			reader = new FileReader(file);
			br = new BufferedReader(reader);
			String str = null;
			while ((str = br.readLine()) != null) {
				String[] codes = str.split(";");
				for (int i = 0; i < codes.length; i++) {
					fwmQrcodeVo = new FwmQrcodeVo();
					// 解析data文件
					if (StringUtils.isNoneBlank(codes[0])) {
						String[] ccs = codes[0].split(",");
						fwmQrcodeVo
								.setQrcode(StringUtils.isNoneBlank(ccs[0]) ? ccs[0]
										: "");
						fwmQrcodeVo.setUpdateDate(StringUtils
								.isNoneBlank(ccs[1]) ? DateUtils
								.parseDate(ccs[1]) : new Date());
						fwmQrcodeVo.setScPlan(scPlanVo.getId());
						// fwmQrcode.setp
						fwmQrcodeVos.add(fwmQrcodeVo);
					}
				}

			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {

				br.close();
				reader.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		System.out.println("码数" + fwmQrcodeVos.size() + "个");
		return fwmQrcodeVos;
	}

	// //
	// public void parseCodeSave(String codes) {
	//
	// }

	public File uploadFile(MultipartFile file, String savePath, String fileName) {
		File targetFile = new File(savePath, fileName);
		if (!targetFile.exists()) {
			targetFile.mkdirs();
		}

		// 保存
		try {
			file.transferTo(targetFile);
		} catch (Exception e) {
			// e.printStackTrace();
			return null;

		}
		return targetFile;
	}

	public static String[] getNullPropertyNames(Object source) {
		final BeanWrapper src = new BeanWrapperImpl(source);
		java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

		Set<String> emptyNames = new HashSet<String>();
		for (java.beans.PropertyDescriptor pd : pds) {
			Object srcValue = src.getPropertyValue(pd.getName());
			if (srcValue == null)
				emptyNames.add(pd.getName());
		}
		String[] result = new String[emptyNames.size()];
		return emptyNames.toArray(result);
	}

	@RequestMapping(value = { "updateProductionStatus", "" })
	@ResponseBody
	public void updateProductionStatus(String id, String key,
			HttpServletRequest request, HttpServletResponse response,
			Model model) {
		if (StringUtils.isNoneBlank(id)) {
			ScPlan scPlan = planService.get(id);
			if (null != scPlan) {
				scPlan.setStatus(Global.MANUFACTURING);
				scPlan.getPlanList().add(id);
				planService.updatePlanStatus(scPlan);
				renderString(response, new BaseBeanJson(0, "成功"));
			} else {
				renderString(response, new BaseBeanJson(1, "任务信息不存在"));
			}
		} else {
			renderString(response, new BaseBeanJson(2, "参数不能为空"));
		}
	}
}
