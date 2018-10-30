/**
 * 
 */
package com.fwzs.master.modules.fwzs.dao;

import com.fwzs.master.common.persistence.CrudDao;
import com.fwzs.master.common.persistence.annotation.MyBatisDao;
import com.fwzs.master.modules.fwzs.entity.ScPlan;

import java.util.List;
import java.util.Map;

/**
 * 任务计划DAO接口
 * @author ly
 * @version 2017-09-30
 */
@MyBatisDao
public interface ScPlanDao extends CrudDao<ScPlan> {
	int updatePlanStatus(ScPlan scPlan);

	List<ScPlan> findPlanByStatus(ScPlan entity);

	List<ScPlan> findPlanByQC(ScPlan entity);

	List<ScPlan> findPlanByStatusIsNotDraft(ScPlan entity);

	ScPlan getByIdAndDelFlag(final Map<String, String> parameterMap);
}