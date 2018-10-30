package com.fwzs.master.modules.fwzs.dao;

import com.fwzs.master.common.persistence.CrudDao;
import com.fwzs.master.common.persistence.annotation.MyBatisDao;
import com.fwzs.master.modules.fwzs.entity.DealerBound2ProductMapping;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Tyler Yin
 * @create 2017-11-24 11:23
 * @description DealerBound2ProductMapping Dao
 **/
@MyBatisDao
public interface DealerBound2ProductMappingDao extends CrudDao<DealerBound2ProductMapping>{
    void updateRealNumber(DealerBound2ProductMapping dealerBound2ProductMapping);
    List<DealerBound2ProductMapping> getMappingsByDealerBoundIds(@Param("dealerBoundIdList") List<String> dealerBoundIdList);
    void deleteByDealerBoundIds(@Param("dealerBoundIdList") List<String> dealerBoundIdList, @Param("delFlag") String delFlag);
    int findOutBoundBoxCount(DealerBound2ProductMapping dealerBound2ProductMapping);
}
