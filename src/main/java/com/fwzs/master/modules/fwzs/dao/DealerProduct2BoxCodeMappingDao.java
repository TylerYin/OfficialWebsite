package com.fwzs.master.modules.fwzs.dao;

import com.fwzs.master.common.persistence.CrudDao;
import com.fwzs.master.common.persistence.annotation.MyBatisDao;
import com.fwzs.master.modules.fwzs.entity.DealerBound;
import com.fwzs.master.modules.fwzs.entity.DealerProduct2BoxCodeMapping;

/**
 * @author Tyler Yin
 * @create 2017-11-24 11:22
 * @description OutProduct2BoxCodeMapping Dao
 **/
@MyBatisDao
public interface DealerProduct2BoxCodeMappingDao extends CrudDao<DealerProduct2BoxCodeMapping> {
    DealerBound getDealerBound2Product(final String dealerBound2ProductId);
    void deleteByDealerBound2ProductMappingId(DealerProduct2BoxCodeMapping dealerProduct2BoxCodeMapping);
    int getCountByDealerBound2ProductId(final String dealerBound2ProductId);
}