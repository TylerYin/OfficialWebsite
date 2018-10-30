package com.fwzs.master.modules.fwzs.dao;

import com.fwzs.master.common.persistence.CrudDao;
import com.fwzs.master.common.persistence.annotation.MyBatisDao;
import com.fwzs.master.modules.fwzs.entity.OutBound;
import com.fwzs.master.modules.fwzs.entity.OutProduct2BoxCodeMapping;

/**
 * @author Tyler Yin
 * @create 2017-11-24 11:22
 * @description OutProduct2BoxCodeMapping Dao
 **/
@MyBatisDao
public interface OutProduct2BoxCodeMappingDao extends CrudDao<OutProduct2BoxCodeMapping> {
    OutBound getOutBound2Product(final String outBound2ProductId);
    void deleteByOutBound2ProductMappingId(OutProduct2BoxCodeMapping outProduct2BoxCodeMapping);
    int getCountByOutBound2ProductId(final String outBound2ProductId);
}