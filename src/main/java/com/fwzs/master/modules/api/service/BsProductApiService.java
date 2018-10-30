/**
 * 
 */
package com.fwzs.master.modules.api.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fwzs.master.common.persistence.Page;
import com.fwzs.master.common.service.CrudService;
import com.fwzs.master.modules.api.dao.BsProductApiDao;
import com.fwzs.master.modules.api.entity.BsProductVo;
import com.fwzs.master.modules.fwzs.entity.BsProduct;
import com.fwzs.master.modules.fwzs.dao.BsProductDao;

/**
 * 防伪查询记录表Service
 * @author ly
 * @version 2017-09-30
 */
@Service
@Transactional(readOnly = true)
public class BsProductApiService extends CrudService<BsProductApiDao, BsProductVo> {

	public BsProductVo get(String id) {
		return super.get(id);
	}
	
	public List<BsProductVo> findList(BsProductVo bsProductVo) {
		return super.findList(bsProductVo);
	}
	
	
}