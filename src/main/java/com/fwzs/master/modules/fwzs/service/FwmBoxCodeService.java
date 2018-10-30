/**
 * 
 */
package com.fwzs.master.modules.fwzs.service;

import com.fwzs.master.common.persistence.Page;
import com.fwzs.master.common.service.CrudService;
import com.fwzs.master.modules.fwzs.dao.FwmBoxCodeDao;
import com.fwzs.master.modules.fwzs.entity.FwmBoxCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 箱码Service
 * @author ly
 * @version 2017-10-09
 */
@Service
@Transactional(readOnly = true)
public class FwmBoxCodeService extends CrudService<FwmBoxCodeDao, FwmBoxCode> {

	public FwmBoxCode get(String id) {
		return super.get(id);
	}
	
	public List<FwmBoxCode> findList(FwmBoxCode fwmBoxCode) {
		return super.findList(fwmBoxCode);
	}
	
	public Page<FwmBoxCode> findPage(Page<FwmBoxCode> page, FwmBoxCode fwmBoxCode) {
		return super.findPage(page, fwmBoxCode);
	}
	
	@Transactional(readOnly = false)
	public void save(FwmBoxCode fwmBoxCode) {
		super.save(fwmBoxCode);
	}
	
	@Transactional(readOnly = false)
	public void delete(FwmBoxCode fwmBoxCode) {
		super.delete(fwmBoxCode);
	}

    /**
     * 查询boxCode是否存在
     *
     * @param fwmBoxCode
     * @return
     */
    public int findBoxCodeCount(FwmBoxCode fwmBoxCode) {
        return dao.findBoxCodeCount(fwmBoxCode);
    }
}