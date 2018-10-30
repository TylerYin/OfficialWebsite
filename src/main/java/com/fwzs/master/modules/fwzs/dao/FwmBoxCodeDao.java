/**
 * 
 */
package com.fwzs.master.modules.fwzs.dao;

import com.fwzs.master.common.persistence.CrudDao;
import com.fwzs.master.common.persistence.annotation.MyBatisDao;
import com.fwzs.master.modules.fwzs.entity.FwmBoxCode;

/**
 * 箱码DAO接口
 * @author ly
 * @version 2017-10-09
 */
@MyBatisDao
public interface FwmBoxCodeDao extends CrudDao<FwmBoxCode> {
    int findBoxCodeCount(FwmBoxCode fwmBoxCode);
}