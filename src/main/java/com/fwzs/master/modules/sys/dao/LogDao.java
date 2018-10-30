/**
 * 
 */
package com.fwzs.master.modules.sys.dao;

import com.fwzs.master.common.persistence.CrudDao;
import com.fwzs.master.common.persistence.annotation.MyBatisDao;
import com.fwzs.master.modules.sys.entity.Log;

/**
 * 日志DAO接口
 * @author ly
 * @version 2014-05-16
 */
@MyBatisDao
public interface LogDao extends CrudDao<Log> {

}
