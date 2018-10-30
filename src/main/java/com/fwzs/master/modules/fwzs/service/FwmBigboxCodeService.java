/**
 * 
 */
package com.fwzs.master.modules.fwzs.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fwzs.master.common.persistence.Page;
import com.fwzs.master.common.service.CrudService;
import com.fwzs.master.modules.fwzs.entity.FwmBigboxCode;
import com.fwzs.master.modules.fwzs.dao.FwmBigboxCodeDao;

/**
 * 剁码Service
 * @author ly
 * @version 2017-10-09
 */
@Service
@Transactional(readOnly = true)
public class FwmBigboxCodeService extends CrudService<FwmBigboxCodeDao, FwmBigboxCode> {

	public FwmBigboxCode get(String id) {
		return super.get(id);
	}
	
	public List<FwmBigboxCode> findList(FwmBigboxCode fwmBigboxCode) {
		return super.findList(fwmBigboxCode);
	}
	
	public Page<FwmBigboxCode> findPage(Page<FwmBigboxCode> page, FwmBigboxCode fwmBigboxCode) {
		return super.findPage(page, fwmBigboxCode);
	}
	
	@Transactional(readOnly = false)
	public void save(FwmBigboxCode fwmBigboxCode) {
		super.save(fwmBigboxCode);
	}
	
	@Transactional(readOnly = false)
	public void delete(FwmBigboxCode fwmBigboxCode) {
		super.delete(fwmBigboxCode);
	}
	
}