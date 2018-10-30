/**
 * 
 */
package com.fwzs.master.modules.fwzs.web;


import com.fwzs.master.common.utils.StringUtils;
import com.fwzs.master.common.web.BaseController;
import com.fwzs.master.modules.fwzs.entity.FwmQrcode;
import com.fwzs.master.modules.fwzs.entity.FwmQueryHistory;
import com.fwzs.master.modules.fwzs.service.FwmQrcodeService;
import com.fwzs.master.modules.fwzs.service.FwmQueryHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * 无效防伪码查询Controller
 * 
 * @author Tyler
 * @version 2017-10-29
 */
@Controller
@RequestMapping(value = "${adminPath}/phone/invalid")
public class QueryInvalidFwmController extends BaseController {
	@Autowired
	private FwmQueryHistoryService fwmQueryHistoryService;
	@Autowired
	private FwmQrcodeService fwmQrcodeService;

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

	@RequestMapping(value = "/insertInvalidFWMData")
    public void insertInvalidFWMData(String qrCode, String location, String province,
            String city, String town, String lat, String lng, HttpServletRequest request) {
        final FwmQueryHistory invalidFWMQrcode = new FwmQueryHistory();
		invalidFWMQrcode.setQrcode(qrCode);
		invalidFWMQrcode.setAddress(location);
		invalidFWMQrcode.setLatitude(lat);
		invalidFWMQrcode.setLongitude(lng);
        invalidFWMQrcode.setProvince(province);
        invalidFWMQrcode.setCity(city);
        invalidFWMQrcode.setTown(town);
		invalidFWMQrcode.setQueryType("1");
		invalidFWMQrcode.setQueryDate(new Date());
		invalidFWMQrcode.setCreateDate(new Date());
		invalidFWMQrcode.setUpdateDate(new Date());
		invalidFWMQrcode.setQueryResult("1");
		invalidFWMQrcode.setQuerySource(StringUtils.getRemoteAddr(request));
		fwmQueryHistoryService.saveInvalidFWMQrcode(invalidFWMQrcode);
	}
}