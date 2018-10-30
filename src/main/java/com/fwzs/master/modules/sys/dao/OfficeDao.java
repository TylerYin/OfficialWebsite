/**
 * 
 */
package com.fwzs.master.modules.sys.dao;

import com.fwzs.master.common.persistence.TreeDao;
import com.fwzs.master.common.persistence.annotation.MyBatisDao;
import com.fwzs.master.modules.sys.entity.Office;

/**
 * 机构DAO接口
 * @author ly
 * @version 2014-05-16
 */
@MyBatisDao
public interface OfficeDao extends TreeDao<Office> {
	
}
