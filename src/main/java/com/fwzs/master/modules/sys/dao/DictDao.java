/**
 * 
 */
package com.fwzs.master.modules.sys.dao;

import java.util.List;

import com.fwzs.master.common.persistence.CrudDao;
import com.fwzs.master.common.persistence.annotation.MyBatisDao;
import com.fwzs.master.modules.sys.entity.Dict;

/**
 * 字典DAO接口
 * @author ly
 * @version 2014-05-16
 */
@MyBatisDao
public interface DictDao extends CrudDao<Dict> {

	public List<String> findTypeList(Dict dict);
	
}
