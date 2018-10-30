package com.fwzs.master.modules.cms.dao;

import com.fwzs.master.common.persistence.CrudDao;
import com.fwzs.master.common.persistence.annotation.MyBatisDao;
import com.fwzs.master.modules.cms.entity.GuestBook;

/**
 * 留言DAO接口
 *
 * @author ly
 * @version 2013-8-23
 */
@MyBatisDao
public interface GuestBookDao extends CrudDao<GuestBook> {

}
