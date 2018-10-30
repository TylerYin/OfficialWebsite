/**
 * 
 */
package com.fwzs.master.modules.fwzs.web;

import com.fwzs.master.common.persistence.Page;
import com.fwzs.master.common.utils.StringUtils;
import com.fwzs.master.common.web.BaseController;
import com.fwzs.master.modules.fwzs.entity.BsProduct;
import com.fwzs.master.modules.fwzs.entity.EChart.EChart;
import com.fwzs.master.modules.fwzs.entity.FwmFileStatistics;
import com.fwzs.master.modules.fwzs.service.BsProductService;
import com.fwzs.master.modules.fwzs.service.FwmFileService;
import com.fwzs.master.modules.sys.utils.UserUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.util.List;

/**
 * 防伪码Controller
 * @author yjd
 * @version 2017-10-08
 */
@Controller
@RequestMapping(value = "${adminPath}/fwzs/fwmQrcodeStatistics")
public class FwmQrcodeStatisticsController extends BaseController {

    @Autowired
    private FwmFileService fwmFileService;
    @Autowired
    private BsProductService bsProductService;

    @RequiresPermissions("fwzs:fwmFile:view")
    @RequestMapping(value = {"list", ""})
    public String list(FwmFileStatistics fwmFileStatistics, HttpServletRequest request, HttpServletResponse response, Model model) {
        final List<BsProduct> bsProducts = bsProductService.findList(new BsProduct());
        if (null != fwmFileStatistics.getBsProduct() && StringUtils.isNotEmpty(fwmFileStatistics.getBsProduct().getId())) {
            fwmFileStatistics.getBsProduct().setProdName(bsProductService.get(fwmFileStatistics.getBsProduct().getId()).getProdName());
        }

        Page<FwmFileStatistics> page = fwmFileService.findFwmFileStatistics(new Page<FwmFileStatistics>(request, response), fwmFileStatistics);
        model.addAttribute("page", page);
        model.addAttribute("bsProducts", bsProducts);
        model.addAttribute("isSystemManager", UserUtils.isSystemManager());
        model.addAttribute("fwmFileStatistics", fwmFileStatistics);
        model.addAttribute("totalCount", fwmFileService.caculateCodeCount(fwmFileStatistics));
        return "modules/fwzs/fwmQrcodeStatistics";
    }

    @ResponseBody
    @RequestMapping(value = "getChartData")
    public EChart getChartData(FwmFileStatistics fwmFileStatistics, HttpServletRequest request) {
        try {
            fwmFileStatistics.setBeginDate(DateUtils.parseDate(request.getParameter("beginDate"), "yyyy-MM-dd"));
            fwmFileStatistics.setEndDate(DateUtils.parseDate(request.getParameter("endDate"), "yyyy-MM-dd"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return fwmFileService.getChartData(fwmFileStatistics);
    }

    @RequiresPermissions("fwzs:fwmFile:view")
    @RequestMapping(value = "findListForCompany")
    public String findListForCompany(FwmFileStatistics fwmFileStatistics, HttpServletRequest request, HttpServletResponse response, Model model) {
        final List<BsProduct> bsProducts = bsProductService.findList(new BsProduct());
        if (null != fwmFileStatistics.getBsProduct() && StringUtils.isNotEmpty(fwmFileStatistics.getBsProduct().getId())) {
            fwmFileStatistics.getBsProduct().setProdName(bsProductService.get(fwmFileStatistics.getBsProduct().getId()).getProdName());
        }

        Page<FwmFileStatistics> page = fwmFileService.findFwmFileStatistics(new Page<FwmFileStatistics>(request, response), fwmFileStatistics);
        model.addAttribute("page", page);
        model.addAttribute("bsProducts", bsProducts);
        model.addAttribute("isSystemManager", UserUtils.isSystemManager());
        model.addAttribute("fwmFileStatistics", fwmFileStatistics);
        return "modules/fwzs/fwmQrcodeStatistics";
    }
}