/**
 * 
 */
package com.fwzs.master.modules.api.dao;

import com.fwzs.master.common.persistence.CrudDao;
import com.fwzs.master.common.persistence.annotation.MyBatisDao;
import com.fwzs.master.modules.api.entity.BsProductVo;
import com.fwzs.master.modules.fwzs.entity.BsProduct;

/**
 * 防伪查询记录表DAO接口
 * @author ly
 * @version 2017-09-30
 */
@MyBatisDao
public interface BsProductApiDao extends CrudDao<BsProductVo> {
	
}