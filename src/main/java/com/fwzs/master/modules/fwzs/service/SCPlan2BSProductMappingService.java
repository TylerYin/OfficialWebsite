/**
 * 
 */
package com.fwzs.master.modules.fwzs.service;

import com.fwzs.master.common.persistence.Page;
import com.fwzs.master.common.service.CrudService;
import com.fwzs.master.modules.fwzs.dao.BsProductDao;
import com.fwzs.master.modules.fwzs.dao.SCPlan2BSProductMappingDao;
import com.fwzs.master.modules.fwzs.entity.BsProduct;
import com.fwzs.master.modules.fwzs.entity.SCPlan2BSProductMapping;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 生产计划和产品映射表Service
 * @author Tyler
 * @version 2017-11-01
 */
@Service
@Transactional(readOnly = true)
public class SCPlan2BSProductMappingService extends CrudService<SCPlan2BSProductMappingDao, SCPlan2BSProductMapping> {

	public SCPlan2BSProductMapping get(String id) {
		return super.get(id);
	}
	
	public List<SCPlan2BSProductMapping> findList(SCPlan2BSProductMapping scPlan2BSProductMapping) {
		return super.findList(scPlan2BSProductMapping);
	}
	
	public Page<SCPlan2BSProductMapping> findPage(Page<SCPlan2BSProductMapping> page, SCPlan2BSProductMapping scPlan2BSProductMapping) {
		return super.findPage(page, scPlan2BSProductMapping);
	}
	
	@Transactional(readOnly = false)
	public void save(SCPlan2BSProductMapping scPlan2BSProductMapping) {
		super.save(scPlan2BSProductMapping);
	}
	
	@Transactional(readOnly = false)
	public void delete(SCPlan2BSProductMapping scPlan2BSProductMapping) {
		super.delete(scPlan2BSProductMapping);
	}
	
}