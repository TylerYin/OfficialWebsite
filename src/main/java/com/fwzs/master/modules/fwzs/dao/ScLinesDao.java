/**
 * 
 */
package com.fwzs.master.modules.fwzs.dao;

import com.fwzs.master.common.persistence.CrudDao;
import com.fwzs.master.common.persistence.annotation.MyBatisDao;
import com.fwzs.master.modules.fwzs.entity.ScLines;

import java.util.HashMap;
import java.util.List;

/**
 * 生产线DAO接口
 * @author ly
 * @version 2017-09-30
 */
@MyBatisDao
public interface ScLinesDao extends CrudDao<ScLines> {
	int getRowCountByLineNo(final HashMap searchCondition);
    List<ScLines> findListForScPlan(final ScLines scLines);
}