/**
 * 
 */
package com.fwzs.master.modules.fwzs.service;

import com.fwzs.master.common.persistence.Page;
import com.fwzs.master.common.service.CrudService;
import com.fwzs.master.common.utils.IdGen;
import com.fwzs.master.modules.fwzs.dao.FwmQueryHistoryDao;
import com.fwzs.master.modules.fwzs.entity.FwmQueryHistory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 防伪查询记录表Service
 * @author ly
 * @version 2017-09-30
 */
@Service
@Transactional(readOnly = true)
public class FwmQueryHistoryService extends CrudService<FwmQueryHistoryDao, FwmQueryHistory> {

	public FwmQueryHistory get(String id) {
		return super.get(id);
	}
	
	public List<FwmQueryHistory> findList(FwmQueryHistory fwmQueryHistory) {
		return super.findList(fwmQueryHistory);
	}
	
	public Page<FwmQueryHistory> findPage(Page<FwmQueryHistory> page, FwmQueryHistory fwmQueryHistory) {
		fwmQueryHistory.getSqlMap().put("dsf", dataScopeFilter(fwmQueryHistory.getCurrentUser(), "o", "u"));
		return super.findPage(page, fwmQueryHistory);
	}

    /**
     * 为手机端专门提供的查询接口
     *
     * @param page
     * @param fwmQueryHistory
     * @return
     */
    public Page<FwmQueryHistory> findPageForMobile(Page<FwmQueryHistory> page, FwmQueryHistory fwmQueryHistory) {
        return super.findPage(page, fwmQueryHistory);
    }

	public Page<FwmQueryHistory> findInvalidList(Page<FwmQueryHistory> page, FwmQueryHistory fwmQueryHistory) {
		fwmQueryHistory.setPage(page);
		page.setList(dao.findInvalidList(fwmQueryHistory));
		return page;
	}

	@Transactional(readOnly = false)
	public void save(FwmQueryHistory fwmQueryHistory) {
		super.save(fwmQueryHistory);
	}

	@Transactional(readOnly = false)
	public void saveInvalidFWMQrcode(FwmQueryHistory fwmQueryHistory) {
		fwmQueryHistory.setId(IdGen.uuid());
		super.dao.insertInvalidFWMCode(fwmQueryHistory);
	}
	
	@Transactional(readOnly = false)
	public void delete(FwmQueryHistory fwmQueryHistory) {
		super.delete(fwmQueryHistory);
	}
	
}