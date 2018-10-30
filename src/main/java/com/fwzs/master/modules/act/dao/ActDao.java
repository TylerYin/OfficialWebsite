/**
 * 
 */
package com.fwzs.master.modules.act.dao;

import com.fwzs.master.common.persistence.CrudDao;
import com.fwzs.master.common.persistence.annotation.MyBatisDao;
import com.fwzs.master.modules.act.entity.Act;

/**
 * 审批DAO接口
 * @author ly
 * @version 2014-05-16
 */
@MyBatisDao
public interface ActDao extends CrudDao<Act> {

	public int updateProcInsIdByBusinessId(Act act);
	
}
