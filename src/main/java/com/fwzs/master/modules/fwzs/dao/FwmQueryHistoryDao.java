/**
 * 
 */
package com.fwzs.master.modules.fwzs.dao;

import com.fwzs.master.common.persistence.CrudDao;
import com.fwzs.master.common.persistence.annotation.MyBatisDao;
import com.fwzs.master.modules.fwzs.entity.FwmQueryHistory;

import java.util.List;

/**
 * 防伪查询记录表DAO接口
 * @author ly
 * @version 2017-09-30
 */
@MyBatisDao
public interface FwmQueryHistoryDao extends CrudDao<FwmQueryHistory> {
	void insertInvalidFWMCode(FwmQueryHistory invalidFWMCode);
	List<FwmQueryHistory> findInvalidList(FwmQueryHistory fwmQueryHistory);
}