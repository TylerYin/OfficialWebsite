package com.fwzs.master.modules.fwzs.dao;

import com.fwzs.master.common.persistence.CrudDao;
import com.fwzs.master.common.persistence.annotation.MyBatisDao;
import com.fwzs.master.modules.fwzs.entity.Warehouse;

import java.util.List;
import java.util.Map;

/**
 * @author Tyler Yin
 * @create 2017-11-17 9:23
 * @description 仓库DAO接口
 **/
@MyBatisDao
public interface WarehouseDao extends CrudDao<Warehouse>{
    List<Warehouse> findTreeMenuDataList(final Warehouse warehouse);
    Warehouse findChildrenWarehouse(final Map<String, String> map);
    void deleteWarehouseByIds(final Warehouse warehouse);
}
