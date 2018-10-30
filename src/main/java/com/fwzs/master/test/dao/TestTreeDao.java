/**
 * 
 */
package com.fwzs.master.test.dao;

import com.fwzs.master.common.persistence.TreeDao;
import com.fwzs.master.common.persistence.annotation.MyBatisDao;
import com.fwzs.master.test.entity.TestTree;

/**
 * 树结构生成DAO接口
 * @author ly
 * @version 2015-04-06
 */
@MyBatisDao
public interface TestTreeDao extends TreeDao<TestTree> {
	
}