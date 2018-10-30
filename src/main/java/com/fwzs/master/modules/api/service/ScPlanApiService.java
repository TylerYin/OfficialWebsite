/**
 * 
 */
package com.fwzs.master.modules.api.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fwzs.master.common.persistence.Page;
import com.fwzs.master.common.service.CrudService;
import com.fwzs.master.modules.api.dao.ScPlanApiDao;
import com.fwzs.master.modules.api.entity.BsProductVo;
import com.fwzs.master.modules.api.entity.ScPlanVo;
import com.fwzs.master.modules.fwzs.entity.ScPlan;

/**
 * 任务计划Service
 * @author ly
 * @version 2017-09-30
 */
@Service
@Transactional(readOnly = true)
public class ScPlanApiService extends CrudService<ScPlanApiDao, ScPlanVo> {

	public ScPlanVo get(String id) {
		return super.get(id);
	}
	public List<ScPlanVo> findList(ScPlanVo scPlanVo) {
		return super.findList(scPlanVo);
	}
	
}