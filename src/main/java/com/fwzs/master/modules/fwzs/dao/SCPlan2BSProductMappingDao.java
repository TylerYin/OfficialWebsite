/**
 * 
 */
package com.fwzs.master.modules.fwzs.dao;

import com.fwzs.master.common.persistence.CrudDao;
import com.fwzs.master.common.persistence.annotation.MyBatisDao;
import com.fwzs.master.modules.fwzs.entity.BsProduct;
import com.fwzs.master.modules.fwzs.entity.SCPlan2BSProductMapping;

/**
 * 生产计划和产品映射表DAO接口
 * @author Tyler
 * @version 2017-10-31
 */
@MyBatisDao
public interface SCPlan2BSProductMappingDao extends CrudDao<SCPlan2BSProductMapping> {
	
}