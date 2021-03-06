/**
 * 
 */
package com.fwzs.master.modules.sys.dao;

import com.fwzs.master.common.persistence.TreeDao;
import com.fwzs.master.common.persistence.annotation.MyBatisDao;
import com.fwzs.master.modules.sys.entity.Area;

import java.util.List;

/**
 * 区域DAO接口
 * @author ly
 * @version 2014-05-16
 */
@MyBatisDao
public interface AreaDao extends TreeDao<Area> {
    List<Area> findChildrenAreas(Area area);
}
