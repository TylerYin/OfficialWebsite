package com.fwzs.master.modules.cms.dao;

import java.util.List;

import com.fwzs.master.common.persistence.CrudDao;
import com.fwzs.master.common.persistence.annotation.MyBatisDao;
import com.fwzs.master.modules.cms.entity.Link;

/**
 * 链接DAO接口
 * @author ly
 * @version 2013-8-23
 */
@MyBatisDao
public interface LinkDao extends CrudDao<Link> {
    /**
     * 查找链接
     * @param ids
     * @return
     */
	List<Link> findByIdIn(String[] ids);
//	{
//		return find("front Like where id in (:p1)", new Parameter(new Object[]{ids}));
//	}

    /**
     * 更新链接
     * @param link
     * @return
     */
	int updateExpiredWeight(Link link);
//	{
//		return update("update Link set weight=0 where weight > 0 and weightDate < current_timestamp()");
//	}
//	List<Link> fjindListByEntity();
}
