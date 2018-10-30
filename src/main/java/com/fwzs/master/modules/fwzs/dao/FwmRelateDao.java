/**
 * 
 */
package com.fwzs.master.modules.fwzs.dao;

import com.fwzs.master.common.persistence.CrudDao;
import com.fwzs.master.common.persistence.annotation.MyBatisDao;
import com.fwzs.master.modules.fwzs.entity.FwmRelate;

/**
 * 防伪码关联DAO接口
 * @author yjd
 * @version 2017-10-08
 */
@MyBatisDao
public interface FwmRelateDao extends CrudDao<FwmRelate> {
}