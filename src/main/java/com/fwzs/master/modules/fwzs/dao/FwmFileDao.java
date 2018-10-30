/**
 * 
 */
package com.fwzs.master.modules.fwzs.dao;

import com.fwzs.master.common.persistence.CrudDao;
import com.fwzs.master.common.persistence.annotation.MyBatisDao;
import com.fwzs.master.modules.fwzs.entity.FwmFile;

/**
 * 防伪码文件列表DAO接口
 * @author ly
 * @version 2017-09-30
 */
@MyBatisDao
public interface FwmFileDao extends CrudDao<FwmFile> {
	
}