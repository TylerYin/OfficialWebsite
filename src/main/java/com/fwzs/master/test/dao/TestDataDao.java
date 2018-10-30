/**
 * 
 */
package com.fwzs.master.test.dao;

import com.fwzs.master.common.persistence.CrudDao;
import com.fwzs.master.common.persistence.annotation.MyBatisDao;
import com.fwzs.master.test.entity.TestData;

/**
 * 单表生成DAO接口
 * @author ly
 * @version 2015-04-06
 */
@MyBatisDao
public interface TestDataDao extends CrudDao<TestData> {
	
}