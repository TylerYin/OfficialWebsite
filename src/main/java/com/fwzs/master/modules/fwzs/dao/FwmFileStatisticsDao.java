package com.fwzs.master.modules.fwzs.dao;

import com.fwzs.master.common.persistence.CrudDao;
import com.fwzs.master.common.persistence.annotation.MyBatisDao;
import com.fwzs.master.modules.fwzs.entity.FwmFileStatistics;

import java.util.List;

/**
 * @author Tyler Yin
 * @create 2018-03-06 16:33
 * @description FwmFileStatisticsDao
 **/

@MyBatisDao
public interface FwmFileStatisticsDao extends CrudDao<FwmFileStatistics> {
    List<FwmFileStatistics> findListByCompany(FwmFileStatistics fwmFileStatistics);
    List<FwmFileStatistics> findFwmFileStatistics(FwmFileStatistics fwmFileStatistics);
    long findTotalCount(FwmFileStatistics fwmFileStatistics);
    long findTotalCountByCompany(FwmFileStatistics fwmFileStatistics);
}
