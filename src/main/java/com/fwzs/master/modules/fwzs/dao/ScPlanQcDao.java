/**
 * 
 */
package com.fwzs.master.modules.fwzs.dao;

import com.fwzs.master.common.persistence.CrudDao;
import com.fwzs.master.common.persistence.annotation.MyBatisDao;
import com.fwzs.master.modules.fwzs.entity.ScPlanQc;

import java.util.List;

/**
 * 任务计划质检DAO接口
 * @author Tyler
 * @version 2018-01-23
 */
@MyBatisDao
public interface ScPlanQcDao extends CrudDao<ScPlanQc> {
	List<ScPlanQc> findQcNotPass(ScPlanQc scPlanQc);

	void insertScPlanQc(ScPlanQc scPlanQc);
}