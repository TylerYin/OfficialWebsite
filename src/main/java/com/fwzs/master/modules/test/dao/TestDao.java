/**
 * 
 */
package com.fwzs.master.modules.test.dao;

import com.fwzs.master.common.persistence.CrudDao;
import com.fwzs.master.common.persistence.annotation.MyBatisDao;
import com.fwzs.master.modules.test.entity.Test;

/**
 * 测试DAO接口
 * @author ly
 * @version 2013-10-17
 */
@MyBatisDao
public interface TestDao extends CrudDao<Test> {
	
}
