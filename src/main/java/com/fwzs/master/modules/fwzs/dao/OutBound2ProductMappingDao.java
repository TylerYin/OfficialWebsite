package com.fwzs.master.modules.fwzs.dao;

import com.fwzs.master.common.persistence.CrudDao;
import com.fwzs.master.common.persistence.annotation.MyBatisDao;
import com.fwzs.master.modules.fwzs.entity.OutBound2ProductMapping;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Tyler Yin
 * @create 2017-11-24 11:23
 * @description OutBound2ProductMapping Dao
 **/
@MyBatisDao
public interface OutBound2ProductMappingDao extends CrudDao<OutBound2ProductMapping>{
    void updateRealNumber(OutBound2ProductMapping outBound2ProductMapping);
    List<OutBound2ProductMapping> getMappingsByOutBoundIds(@Param("outBoundIdList") List<String> outBoundIdList);
    void deleteByOutBoundIds(@Param("outBoundIdList") List<String> outBoundIdList, @Param("delFlag") String delFlag);
    int findOutBoundBoxCount(OutBound2ProductMapping outBound2ProductMapping);
}
