package com.fwzs.master.modules.fwzs.service;

import com.fwzs.master.common.service.CrudService;
import com.fwzs.master.common.utils.StringUtils;
import com.fwzs.master.modules.fwzs.dao.FwmTraceDao;
import com.fwzs.master.modules.fwzs.entity.FwmTrace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

/**
 * @author Tyler Yin
 * @create 2018-01-19 9:06
 * @description 防伪码追溯Service
 **/
@Service
@Transactional(readOnly = true)
public class FwmTraceService extends CrudService<FwmTraceDao, FwmTrace> {

    @Autowired
    private FwmQrcodeService fwmQrcodeService;

    /**
     * 根据单品码追溯产品从企业到经销商的流通轨迹, 桌面端用
     *
     * @param fwmTrace
     * @return
     */
    public List<FwmTrace> findTrace(FwmTrace fwmTrace) {
        return this.dao.findTrace(fwmTrace);
    }

    /**
     * 根据单品码追溯产品从企业到经销商的流通轨迹，手机端用
     *
     * @param fwmTrace
     * @return
     */
    public FwmTrace findTraceByFwmQrcode(FwmTrace fwmTrace) {
        if (StringUtils.isNotBlank(fwmTrace.getQrCode())) {
            return this.dao.findTraceByFwmQrcode(fwmTrace);
        } else {
            return new FwmTrace();
        }
    }

    /**
     * 根据单品码追溯产品在整个经销商销售环节的流通轨迹
     *
     * @param fwmTrace
     * @return
     */
    public List<FwmTrace> findDealerTraceByFwmQrcode(FwmTrace fwmTrace) {
        if (StringUtils.isNotBlank(fwmTrace.getQrCode()) && null != fwmQrcodeService.getByQrcode(fwmTrace.getQrCode())) {
            return this.dao.findDealerTraceByFwmQrcode(fwmTrace);
        } else {
            return Collections.emptyList();
        }
    }
}