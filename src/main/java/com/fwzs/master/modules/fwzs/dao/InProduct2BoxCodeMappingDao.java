package com.fwzs.master.modules.fwzs.dao;

import com.fwzs.master.common.persistence.CrudDao;
import com.fwzs.master.common.persistence.annotation.MyBatisDao;
import com.fwzs.master.modules.fwzs.entity.InBound;
import com.fwzs.master.modules.fwzs.entity.InProduct2BoxCodeMapping;

/**
 * @author Tyler Yin
 * @create 2017-11-24 11:22
 * @description OutProduct2BoxCodeMapping Dao
 **/
@MyBatisDao
public interface InProduct2BoxCodeMappingDao extends CrudDao<InProduct2BoxCodeMapping> {
    InBound getInBound2Product(final String inBound2ProductId);
    void deleteByInBound2ProductMappingId(InProduct2BoxCodeMapping inProduct2BoxCodeMapping);
    int getCountByInBound2ProductId(final String inBound2ProductId);
}