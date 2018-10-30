package com.fwzs.master.modules.fwzs.dao;

import com.fwzs.master.common.persistence.CrudDao;
import com.fwzs.master.common.persistence.annotation.MyBatisDao;
import com.fwzs.master.modules.fwzs.entity.RealtimeStockLevel;

import java.util.List;

/**
 * @author Tyler Yin
 * @create 2017-11-20 14:00
 * @description 实时库存DAO
 **/
@MyBatisDao
public interface RealtimeStockLevelDao extends CrudDao<RealtimeStockLevel>{
    RealtimeStockLevel findStock(final RealtimeStockLevel realtimeStockLevel);
    List<RealtimeStockLevel> findEnterpriseList(final RealtimeStockLevel realtimeStockLevel);
    List<RealtimeStockLevel> findDealerList(final RealtimeStockLevel realtimeStockLevel);
    List<RealtimeStockLevel> findDealerForCompanyList(final RealtimeStockLevel realtimeStockLevel);
}