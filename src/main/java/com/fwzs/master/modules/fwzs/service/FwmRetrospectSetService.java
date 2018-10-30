/**
 * 
 */
package com.fwzs.master.modules.fwzs.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fwzs.master.common.persistence.Page;
import com.fwzs.master.common.service.CrudService;
import com.fwzs.master.modules.fwzs.entity.FwmRetrospectSet;
import com.fwzs.master.modules.fwzs.dao.FwmRetrospectSetDao;

/**
 * 追溯展示设置Service
 * @author ly
 * @version 2017-09-30
 */
@Service
@Transactional(readOnly = true)
public class FwmRetrospectSetService extends CrudService<FwmRetrospectSetDao, FwmRetrospectSet> {

	public FwmRetrospectSet get(String id) {
		return super.get(id);
	}
	
	public List<FwmRetrospectSet> findList(FwmRetrospectSet fwmRetrospectSet) {
		return super.findList(fwmRetrospectSet);
	}
	
	public Page<FwmRetrospectSet> findPage(Page<FwmRetrospectSet> page, FwmRetrospectSet fwmRetrospectSet) {
		return super.findPage(page, fwmRetrospectSet);
	}
	
	@Transactional(readOnly = false)
	public void save(FwmRetrospectSet fwmRetrospectSet) {
		super.save(fwmRetrospectSet);
	}
	
	@Transactional(readOnly = false)
	public void delete(FwmRetrospectSet fwmRetrospectSet) {
		super.delete(fwmRetrospectSet);
	}
	public FwmRetrospectSet getRetrospectByProductId(String productId){
		return dao.getRetrospectByProductId(productId);
	}
}