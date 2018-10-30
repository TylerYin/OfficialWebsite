/**
 * 
 */
package com.fwzs.master.modules.fwzs.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fwzs.master.common.persistence.Page;
import com.fwzs.master.common.service.CrudService;
import com.fwzs.master.modules.fwzs.dao.FwmRelateDao;
import com.fwzs.master.modules.fwzs.entity.BsProduct;
import com.fwzs.master.modules.fwzs.entity.FwmFile;
import com.fwzs.master.modules.fwzs.entity.FwmQrcode;
import com.fwzs.master.modules.fwzs.entity.FwmRelate;

/**
 * 防伪码Service
 * 
 * @author yjd
 * @version 2017-10-08
 */
@Service
@Transactional(readOnly = true)
public class FwmRelateService extends CrudService<FwmRelateDao, FwmRelate> {

	public FwmRelate get(String id) {
		return super.get(id);
	}

	public List<FwmRelate> findList(FwmRelate fwmRelate) {
		return super.findList(fwmRelate);
	}

	public Page<FwmRelate> findPage(Page<FwmRelate> page, FwmRelate fwmRelate) {
		return super.findPage(page, fwmRelate);
	}

	@Transactional(readOnly = false)
	public void save(FwmRelate fwmRelate) {
		super.save(fwmRelate);
	}

	@Transactional(readOnly = false)
	public void delete(FwmRelate fwmRelate) {
		super.delete(fwmRelate);
	}

}