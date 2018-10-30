/**
 * 
 */
package com.fwzs.master.modules.api.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.fwzs.master.common.persistence.CrudDao;
import com.fwzs.master.common.persistence.annotation.MyBatisDao;
import com.fwzs.master.modules.api.entity.ScPlanVo;
import com.fwzs.master.modules.fwzs.entity.FwmQrcode;
import com.fwzs.master.modules.fwzs.entity.ScPlan;

/**
 * 任务计划DAO接口
 * @author ly
 * @version 2017-09-30
 */
@MyBatisDao
public interface ScPlanApiDao extends CrudDao<ScPlanVo> {
	
}