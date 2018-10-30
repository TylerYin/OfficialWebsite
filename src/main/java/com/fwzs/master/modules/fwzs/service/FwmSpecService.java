/**
 * 
 */
package com.fwzs.master.modules.fwzs.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fwzs.master.common.persistence.Page;
import com.fwzs.master.common.service.CrudService;
import com.fwzs.master.modules.fwzs.entity.FwmSpec;
import com.fwzs.master.modules.fwzs.dao.FwmSpecDao;

/**
 * 产品规格Service
 * @author ly
 * @version 2017-09-30
 */
@Service
@Transactional(readOnly = true)
public class FwmSpecService extends CrudService<FwmSpecDao, FwmSpec> {

	public FwmSpec get(String id) {
		return super.get(id);
	}
	
	public List<FwmSpec> findList(FwmSpec fwmSpec) {
		fwmSpec.getSqlMap().put("dsf", dataScopeFilter(fwmSpec.getCurrentUser(), "o", "u"));
		return super.findList(fwmSpec);
	}
	
	public Page<FwmSpec> findPage(Page<FwmSpec> page, FwmSpec fwmSpec) {
		fwmSpec.getSqlMap().put("dsf", dataScopeFilter(fwmSpec.getCurrentUser(), "o", "u"));
		return super.findPage(page, fwmSpec);
	}
	
	public int getRowCountBySpecCode(final HashMap searchCondition){
		return super.dao.getRowCountBySpecCode(searchCondition);
	}
	
	@Transactional(readOnly = false)
	public void save(FwmSpec fwmSpec) {
		super.save(fwmSpec);
	}
	
	@Transactional(readOnly = false)
	public void delete(FwmSpec fwmSpec) {
		super.delete(fwmSpec);
	}
	
}