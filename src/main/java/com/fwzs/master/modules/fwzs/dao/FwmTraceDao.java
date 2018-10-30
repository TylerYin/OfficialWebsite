package com.fwzs.master.modules.fwzs.dao;

import com.fwzs.master.common.persistence.CrudDao;
import com.fwzs.master.common.persistence.annotation.MyBatisDao;
import com.fwzs.master.modules.fwzs.entity.FwmTrace;

import java.util.List;

/**
 * @author Tyler Yin
 * @create 2018-01-19 9:08
 * @description 防伪码追溯Dao
 **/
@MyBatisDao
public interface FwmTraceDao extends CrudDao<FwmTrace> {
    List<FwmTrace> findTrace(FwmTrace fwmTrace);

    FwmTrace findTraceByFwmQrcode(FwmTrace fwmTrace);

    List<FwmTrace> findDealerTraceByFwmQrcode(FwmTrace fwmTrace);
}
