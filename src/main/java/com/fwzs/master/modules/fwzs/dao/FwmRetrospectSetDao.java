/**
 * 
 */
package com.fwzs.master.modules.fwzs.dao;

import org.apache.ibatis.annotations.Param;

import com.fwzs.master.common.persistence.CrudDao;
import com.fwzs.master.common.persistence.annotation.MyBatisDao;
import com.fwzs.master.modules.fwzs.entity.FwmRetrospectSet;

/**
 * 追溯展示设置DAO接口
 * @author ly
 * @version 2017-09-30
 */
@MyBatisDao
public interface FwmRetrospectSetDao extends CrudDao<FwmRetrospectSet> {
	public FwmRetrospectSet getRetrospectByProductId(@Param("productId") String productId);
}