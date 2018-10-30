/**
 * 
 */
package com.fwzs.master.modules.fwzs.dao;

import com.fwzs.master.common.persistence.CrudDao;
import com.fwzs.master.common.persistence.annotation.MyBatisDao;
import com.fwzs.master.modules.fwzs.entity.FwmBigboxCode;

/**
 * 剁码DAO接口
 * @author ly
 * @version 2017-10-09
 */
@MyBatisDao
public interface FwmBigboxCodeDao extends CrudDao<FwmBigboxCode> {
	
}