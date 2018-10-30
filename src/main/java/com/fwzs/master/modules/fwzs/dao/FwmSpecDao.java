/**
 * 
 */
package com.fwzs.master.modules.fwzs.dao;

import com.fwzs.master.common.persistence.CrudDao;
import com.fwzs.master.common.persistence.annotation.MyBatisDao;
import com.fwzs.master.modules.fwzs.entity.FwmSpec;

import java.util.HashMap;

/**
 * 产品规格DAO接口
 * @author ly
 * @version 2017-09-30
 */
@MyBatisDao
public interface FwmSpecDao extends CrudDao<FwmSpec> {
	int getRowCountBySpecCode(final HashMap searchCondition);
}