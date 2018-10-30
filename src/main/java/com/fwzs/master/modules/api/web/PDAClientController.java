package com.fwzs.master.modules.api.web;

import com.fwzs.master.common.config.Global;
import com.fwzs.master.common.utils.BaseBeanJson;
import com.fwzs.master.common.utils.BaseBeanListJson;
import com.fwzs.master.common.web.BaseController;
import com.fwzs.master.modules.fwzs.entity.DealerBound;
import com.fwzs.master.modules.fwzs.entity.OutBound;
import com.fwzs.master.modules.fwzs.entity.PdaBound;
import com.fwzs.master.modules.fwzs.entity.PdaUser;
import com.fwzs.master.modules.fwzs.service.DealerBoundService;
import com.fwzs.master.modules.fwzs.service.InBoundService;
import com.fwzs.master.modules.fwzs.service.OutBoundService;
import com.fwzs.master.modules.fwzs.service.PdaUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author Tyler Yin
 * @create 2018-02-11 9:29
 * @description PDA Call API
 **/
@Controller
@RequestMapping(value = "/pda/api")
public class PDAClientController extends BaseController {

    @Autowired
    private InBoundService inBoundService;

    @Autowired
    private OutBoundService outBoundService;

    @Autowired
    private DealerBoundService dealerBoundService;

    @Autowired
    private PdaUserService pdaUserService;

    /**
     * 企业入库
     * PDA调用该接口，保存入库相关数据
     *
     * @param userId
     * @param planId
     * @param inBoundNumber
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/saveInBoundData", method = RequestMethod.POST)
    public void saveInBoundData(final String userId, final String planId, int inBoundNumber, HttpServletResponse response) {
        PdaBound pdaBound = inBoundService.saveInBoundData(userId, planId, inBoundNumber);
        renderString(response, new BaseBeanJson(Integer.parseInt(pdaBound.getStatus()), Global.BOUND_STATUS_MSG_MAP.get(pdaBound.getStatus())));
    }

    /**
     * 企业出库
     * 更新出库计划和实时库存记录
     *
     * @param outBound2ProductId
     * @param boxCodeList
     * @param boxCodeType
     * @param scanDate
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/saveBoxCode", method = RequestMethod.POST)
    public void saveBoxCode(final String outBound2ProductId, String[] boxCodeList,
                            final String boxCodeType, final Date scanDate, HttpServletResponse response) {
        PdaBound pdaBound = outBoundService.saveBoxCode(outBound2ProductId, Arrays.asList(boxCodeList), boxCodeType, scanDate);
        if (Global.BOUND_INVALID_BOXCODES.equals(pdaBound.getStatus())) {
            renderString(response, new BaseBeanListJson(Integer.parseInt(pdaBound.getStatus()),
                    Global.BOUND_STATUS_MSG_MAP.get(pdaBound.getStatus()), pdaBound.getInvalidCodes()));
        } else if (Global.BOUND_DUPLICATE_BOXCODES.equals(pdaBound.getStatus())) {
            renderString(response, new BaseBeanListJson(Integer.parseInt(pdaBound.getStatus()),
                    Global.BOUND_STATUS_MSG_MAP.get(pdaBound.getStatus()), pdaBound.getDuplicateCodes()));
        } else {
            renderString(response, new BaseBeanJson(Integer.parseInt(pdaBound.getStatus()), Global.BOUND_STATUS_MSG_MAP.get(pdaBound.getStatus())));
        }
    }

    /**
     * 经销商出库
     * 更新出库计划和实时库存记录
     *
     * @param dealerBound2ProductId
     * @param boxCodeList
     * @param boxCodeType
     * @param scanDate
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/saveDealerBoxCode", method = RequestMethod.POST)
    public void saveDealerBoxCode(final String dealerBound2ProductId, String[] boxCodeList, final String boxCodeType, final Date scanDate, HttpServletResponse response) {
        PdaBound pdaBound = dealerBoundService.saveBoxCode(dealerBound2ProductId, Arrays.asList(boxCodeList), boxCodeType, scanDate);
        if (Global.BOUND_INVALID_BOXCODES.equals(pdaBound.getStatus())) {
            renderString(response, new BaseBeanListJson(Integer.parseInt(pdaBound.getStatus()),
                    Global.BOUND_STATUS_MSG_MAP.get(pdaBound.getStatus()), pdaBound.getInvalidCodes()));
        } else if (Global.BOUND_DUPLICATE_BOXCODES.equals(pdaBound.getStatus())) {
            renderString(response, new BaseBeanListJson(Integer.parseInt(pdaBound.getStatus()),
                    Global.BOUND_STATUS_MSG_MAP.get(pdaBound.getStatus()), pdaBound.getDuplicateCodes()));
        } else {
            renderString(response, new BaseBeanJson(Integer.parseInt(pdaBound.getStatus()), Global.BOUND_STATUS_MSG_MAP.get(pdaBound.getStatus())));
        }
    }

    /**
     * 获取企业出库计划
     * 根据用户Id获取待发货的出库计划
     *
     * @param userId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getBounds", method = RequestMethod.POST)
    public List<OutBound> getOutBoundList(final String userId) {
        return outBoundService.findOutBoundsForPDA(userId);
    }

    /**
     * 获取经销商出库计划
     * 根据经销商Id获取待发货的出库计划
     *
     * @param userId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getDealerBounds", method = RequestMethod.POST)
    public List<DealerBound> getDealerOutBoundList(final String userId) {
        return dealerBoundService.findOutBoundsForPDA(userId);
    }

    /**
     * 根据企业和经销商获取PDA用户信息
     *
     * @param companyId
     * @param dealerId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getPDAInfor", method = RequestMethod.POST)
    public List<PdaUser> getPDAInfor(final String companyId, final String dealerId) {
        return pdaUserService.findPdaInfor(companyId, dealerId);
    }
}
