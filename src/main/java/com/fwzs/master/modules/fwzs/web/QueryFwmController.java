/**
 * 
 */
package com.fwzs.master.modules.fwzs.web;


import com.fwzs.master.common.config.Global;
import com.fwzs.master.common.persistence.Page;
import com.fwzs.master.common.utils.IdGen;
import com.fwzs.master.common.utils.PropertiesLoader;
import com.fwzs.master.common.utils.StringUtils;
import com.fwzs.master.common.web.BaseController;
import com.fwzs.master.modules.fwzs.entity.*;
import com.fwzs.master.modules.fwzs.service.*;
import com.fwzs.master.modules.sys.entity.Area;
import com.fwzs.master.modules.sys.entity.Office;
import com.fwzs.master.modules.sys.entity.User;
import com.fwzs.master.modules.sys.utils.UserUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.Properties;

/**
 * 防伪码查询管理Controller
 * 
 * @author yjd
 * @version 2017-08-30
 */
@Controller
@RequestMapping(value = "${adminPath}/phone")
public class QueryFwmController extends BaseController {
	@Autowired
	private FwmQueryHistoryService fwmQueryHistoryService;
	@Autowired
	private FwmQrcodeService fwmQrcodeService;
    @Autowired
    private FwmTraceService fwmTraceService;
    @Autowired
    private DealerService dealerService;
    @Autowired
    private AntiRegionalDumpingService antiRegionalDumpingService;

    private PropertiesLoader loader = new PropertiesLoader();

    private final static String PROPERTY_FILE_NAME = "retrospectTemplate.properties";
    private final static String RETROSPECT_TEMPLATE_DEFAULT_KEY = "retrospect.template.default";

	@ModelAttribute
	public FwmQrcode get(@RequestParam(required = false) String id) {
		FwmQrcode entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = fwmQrcodeService.get(id);
		}
		if (entity == null) {
			entity = new FwmQrcode();
		}
		return entity;
	}

	/**
	 * 扫码主界面
	 * 
	 * @param qrCode
	 * @return
	 */
	@RequestMapping(value = "sm_main")
	public String sm_main(String qrCode, RedirectAttributes redirectAttributes, Model model) {
		model.addAttribute("qrCode", qrCode);
		return "redirect:" + adminPath + "/phone/fw?qrCode=" + qrCode;
		// return "modules/sys/query_main";
	}

	/**
	 * 防伪
	 * 
	 * @param qrCode
	 * @return
	 */
	@RequestMapping(value = "fw")
	public String fw(String qrCode, RedirectAttributes redirectAttributes, Model model) {
        return getFWInfo(qrCode, redirectAttributes, model);
	}

	private String getFWInfo(String qrCode, RedirectAttributes redirectAttributes, Model model){
        model.addAttribute("baiduAPIKey", Global.getConfig("apikey"));
        FwmQrcode fwmQrcode = fwmQrcodeService.getByQrcode(qrCode);
        if (null == fwmQrcode) {
            fwmQrcode = new FwmQrcode();
            fwmQrcode.setQrcode(qrCode);
            model.addAttribute("qrCode", qrCode);
            return "modules/fwzs/query_fw_qrcode_invalid";
        }

        if (null != fwmQrcode && null != fwmQrcode.getBsProduct()
                && StringUtils.isNotEmpty(fwmQrcode.getBsProduct().getImgUrl())) {
            fwmQrcode.getBsProduct().setImgUrl(
                    formatImgUrl(fwmQrcode.getBsProduct().getImgUrl()));
        }

        if (null != fwmQrcode.getSelectNum()) {
            fwmQrcode.setSelectNum(fwmQrcode.getSelectNum() + 1);
        } else {
            fwmQrcode.setSelectNum(1L);
        }

        model.addAttribute("fwmQrcode", fwmQrcode);

        if (Global.RetrospectTemplateSetting.DEFAULT_TEMPLATE.equalsIgnoreCase(getRetrospectTemplate())) {
            return "modules/fwzs/query_fw";
        }
        return "modules/fwzs/traceInfo";
    }

    /**
     * 获取防伪追溯手机端模板名称
     * @return
     */
    private String getRetrospectTemplate() {
        Properties prop = loader.getRealtimeProperties(PROPERTY_FILE_NAME);
        String defaultTemplate = Global.RetrospectTemplateSetting.DEFAULT_TEMPLATE;
        for (String key : prop.stringPropertyNames()) {
            if (key.equalsIgnoreCase(RETROSPECT_TEMPLATE_DEFAULT_KEY)) {
                defaultTemplate = prop.getProperty(key);
                break;
            }
        }
        return defaultTemplate;
    }

    /**
	 * 更新查询次数、添加查询记录
	 * 
	 * @param qrCode
	 * @param location
     * @param province
     * @param city
     * @param town
     * @param lng
     * @param lat
	 */
	
	@RequestMapping(value = "/updateQueryData")
	public void updateData(String qrCode, String location, String province,
            String city, String town, String lng, String lat, HttpServletRequest request) {
		String queryResult = "1";
		// 更新查询次数
		if (qrCode != null) {
			fwmQrcodeService.updateSelectNum(qrCode);
			queryResult = "0";
		}
		// 插入历史查询记录
		FwmQueryHistory fwmQueryHistory = new FwmQueryHistory();
        fwmQueryHistory.setIsNewRecord(true);
		fwmQueryHistory.setId(IdGen.uuid());
		fwmQueryHistory.setLatitude(lat);
		fwmQueryHistory.setLongitude(lng);
        fwmQueryHistory.setTown(town);
        fwmQueryHistory.setCity(city);
        fwmQueryHistory.setProvince(province);
		fwmQueryHistory.setAddress(location);
        fwmQueryHistory.setQueryDate(new Date());
		fwmQueryHistory.setQrcode(qrCode);

        final FwmQrcode fwmQrcode = fwmQrcodeService.getByQrcode(qrCode);
        if (null != fwmQrcode && null != fwmQrcode.getCreateBy()
                && StringUtils.isNotBlank(fwmQrcode.getCreateBy().getId())) {
            fwmQueryHistory.setCreateBy(fwmQrcode.getCreateBy());
        }

        // 扫码方式
        fwmQueryHistory.setQueryType("1");
		fwmQueryHistory.setQueryResult(queryResult);
		// 当前插入这为空，原因：扫码插入不需要登陆，只是记录操作
		fwmQueryHistory.setQuerySource(StringUtils.getRemoteAddr(request));
		fwmQueryHistoryService.save(fwmQueryHistory);

        List<Dealer> dealers = dealerService.findDealerByQrcode(qrCode);
        if (null != fwmQrcode && null != fwmQrcode.getBsProduct() && CollectionUtils.isNotEmpty(dealers)) {
            insertAntiRegionalDumpingData(dealers, province, city, town, qrCode, fwmQrcode.getBsProduct(), fwmQueryHistory);
        }
    }

    /**
     * 判断产品是否窜货
     *
     * @param dealers
     * @param province
     * @param city
     * @param town
     * @return
     */
    private void insertAntiRegionalDumpingData(List<Dealer> dealers,
        String province, String city, String town, String qrCode, BsProduct bsProduct, FwmQueryHistory fwmQueryHistory) {
        for (Dealer dealer : dealers) {
            List<Area> areas = dealerService.findAreaByDealer(dealer);
            boolean antiRegional = true;
            for (Area area : areas) {
                if (area.getName().equals(province) || area.getName().equals(city) || area.getName().equals(town)) {
                    antiRegional = false;
                    break;
                }
            }

            if (antiRegional && antiRegionalDumpingService.getAntiRegionlByQrCode(qrCode, dealer.getId()) == 0) {
                AntiRegionalDumping antiRegionalDumping = new AntiRegionalDumping();
                antiRegionalDumping.setDealer(dealer);
                antiRegionalDumping.setQrCode(qrCode);
                antiRegionalDumping.setFwmQueryHistory(fwmQueryHistory);
                antiRegionalDumping.setBsProduct(bsProduct);
                antiRegionalDumping.setCompany(dealer.getCompany());
                antiRegionalDumpingService.save(antiRegionalDumping);
            }
        }
    }

	/**
	 * 追溯
	 * 
	 * @param qrCode
	 * @return
	 */
	@RequestMapping(value = "zs")
	public String zs(String qrCode, Model model) {
		// 根据产品获取已选溯源属性
		FwmQrcode fwmQrcode = fwmQrcodeService.getByQrcode(qrCode);
		if (null == fwmQrcode) {
			fwmQrcode = new FwmQrcode();
			fwmQrcode.setQrcode(qrCode);
		}

        if (null != fwmQrcode.getScPlan() && null != fwmQrcode.getScPlan().getQcBy()
                && StringUtils.isNotBlank(fwmQrcode.getScPlan().getQcBy().getId())) {
            if (Global.QUALITY_CONTROL_PASS.equals(fwmQrcode.getStatus())) {
                User zj = UserUtils.get(fwmQrcode.getScPlan().getQcBy().getId());
                fwmQrcode.getScPlan().getQcBy().setName(zj.getName());
            }
        }

		if (null != fwmQrcode && null != fwmQrcode.getBsProduct()
				&& StringUtils.isNotEmpty(fwmQrcode.getBsProduct().getImgUrl())) {
			fwmQrcode.getBsProduct().setImgUrl(
					formatImgUrl(fwmQrcode.getBsProduct().getImgUrl()));
		}
		
		model.addAttribute("fwmQrcode", fwmQrcode);
		return "modules/fwzs/query_zs";
	}
	
	private String formatImgUrl(final String imgUrl)
	{
		if(StringUtils.isNoneEmpty(imgUrl))
		{
			return imgUrl.substring(1, imgUrl.length());
		}
		return StringUtils.EMPTY;
	}

	/**
	 * 手机端查询历史
	 * 
	 * @return
	 */
	@RequestMapping(value = "history")
	public String history(String qrCode, HttpServletRequest request, HttpServletResponse response,
			Model model) {
		FwmQueryHistory fwmQueryHistory = new FwmQueryHistory();
		fwmQueryHistory.setQrcode(qrCode);
		Page<FwmQueryHistory> page = fwmQueryHistoryService.findPageForMobile(
				new Page<FwmQueryHistory>(request, response), fwmQueryHistory);
		model.addAttribute("fwmQueryHistorys", page.getList());
		return "modules/fwzs/query_history";
	}

    /**
     * 根据单品码查询物流
     *
     * @param qrCode
     * @param model
     * @return
     */
    @RequestMapping("trace")
    public String trace(@RequestParam("qrCode") String qrCode, HttpServletRequest request, Model model) {
        final FwmQrcode fwmQrcode = fwmQrcodeService.getByQrcode(qrCode);
        FwmTrace fwmTrace = new FwmTrace();
        fwmTrace.setQrCode(qrCode);
        if (null != fwmQrcode) {
            if (null != fwmQrcode.getBsProduct() && StringUtils.isNotEmpty(fwmQrcode.getBsProduct().getImgUrl())) {
                fwmQrcode.getBsProduct().setImgUrl(
                        formatImgUrl(fwmQrcode.getBsProduct().getImgUrl()));
            }

            model.addAttribute("fwmQrcode", fwmQrcode);
            List<FwmTrace> outBounds = fwmTraceService.findTrace(fwmTrace);
            if (CollectionUtils.isNotEmpty(outBounds)) {
                FwmTrace outBound = outBounds.get(0);
                model.addAttribute("fwmTrace", outBound);
                List<FwmTrace> fwmTraces = fwmTraceService.findDealerTraceByFwmQrcode(fwmTrace);
                model.addAttribute("fwmDealerTraces", fwmTraces);

                if (null == outBound.getOutboundDate()) {
                    model.addAttribute("errorMessage", "暂时没有物流信息！");
                }
            } else {
                model.addAttribute("errorMessage", "暂时没有物流信息！");
            }
        } else {
            fwmTrace.setQrCode(qrCode);
            model.addAttribute("fwmTrace", fwmTrace);
            model.addAttribute("errorMessage", "您输入的单品码不存在，请确认是否通过正规渠道购买！");
        }
        return "modules/fwzs/fwmTrace";
    }

    /**
     * 获取手机端防伪追溯界面的防伪码追溯相关信息
     * @param qrCode
     * @param request
     * @param redirectAttributes
     * @param model
     * @return
     */
    @RequestMapping("traceInfo")
    public String traceInfo(String qrCode, HttpServletRequest request, RedirectAttributes redirectAttributes, Model model) {
        model.addAttribute("qrCode", qrCode);
        return getFWInfo(qrCode, redirectAttributes, model);
    }

    /**
     * 获取手机端防伪追溯界面的企业相关信息
     * @param request
     * @param model
     * @return
     */
    @RequestMapping("company/{information}")
    public String contactUs(@PathVariable("information") String information, String qrCode, HttpServletRequest request, Model model) {
        Office office = fwmQrcodeService.getOfficeInfoByQrcode(qrCode);
        model.addAttribute("qrCode", qrCode);
        model.addAttribute("office", office);
        return "modules/fwzs/company" + StringUtils.capitalize(information);
    }

    /**
     * 获取手机端防伪追溯界面的产品扩展属性相关信息
     * @param qrCode
     * @param request
     * @param model
     * @return
     */
    @RequestMapping("product/extendAttribute")
    public String productParameterInfo(String qrCode, HttpServletRequest request, Model model) {
        model.addAttribute("qrCode", qrCode);
        model.addAttribute("bsProduct", fwmQrcodeService.getBsProductExtendAttributes(qrCode));
        return "modules/fwzs/productExtendAttribute";
    }
}