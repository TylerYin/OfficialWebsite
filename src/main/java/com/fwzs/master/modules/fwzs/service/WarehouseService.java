package com.fwzs.master.modules.fwzs.service;

import com.fwzs.master.common.persistence.Page;
import com.fwzs.master.common.service.CrudService;
import com.fwzs.master.common.utils.BaiDuPositionUtils;
import com.fwzs.master.common.utils.ExtractPinYinFromHanZiUtils;
import com.fwzs.master.common.utils.StringUtils;
import com.fwzs.master.modules.fwzs.dao.WarehouseDao;
import com.fwzs.master.modules.fwzs.entity.TreeMenu;
import com.fwzs.master.modules.fwzs.entity.Warehouse;
import com.fwzs.master.modules.sys.entity.User;
import com.fwzs.master.modules.sys.utils.UserUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Tyler Yin
 * @create 2017-11-17 9:24
 * @description 仓库Service
 **/
@Service
@Transactional(readOnly = true)
public class WarehouseService extends CrudService<WarehouseDao, Warehouse>{
    public Warehouse get(String id) {
        return super.get(id);
    }

    public List<Warehouse> findList(Warehouse warehouse) {
        return super.findList(warehouse);
    }

    public Map<String, List<TreeMenu>> findTreeMenuDataList(final Warehouse warehouse) {
        final List<Warehouse> warehouseList = this.dao.findTreeMenuDataList(warehouse);
        return generateTreeMenu(warehouseList);
    }

    private Map<String, List<TreeMenu>> generateTreeMenu(final List<Warehouse> warehouseList){
        final List<TreeMenu> warehouseTreeList = new ArrayList<>();
        final Map<String, List<TreeMenu>> warehouseTreeMap = new HashMap<>();
        if (CollectionUtils.isNotEmpty(warehouseList)) {
            for (final Warehouse warehouse : warehouseList) {
                TreeMenu treeMenu = new TreeMenu();
                treeMenu.setId(warehouse.getId());
                if (null != warehouse.getParentWarehouse() && StringUtils.isNotEmpty(warehouse.getParentWarehouse().getId())) {
                    treeMenu.setpId(warehouse.getParentWarehouse().getId());
                } else {
                    treeMenu.setpId("0");
                }
                treeMenu.setOpen(true);
                treeMenu.setName(warehouse.getName());
                warehouseTreeList.add(treeMenu);
            }
        }
        warehouseTreeMap.put("warehouseTreeMap", warehouseTreeList);
        return warehouseTreeMap;
    }

    private Warehouse findChildrenWarehouseById(final Map<String, String> map){
        return this.dao.findChildrenWarehouse(map);
    }

    public List<Warehouse> findChildrenTreeMenuDataList(final Map<String, String> map) {
        final List<Warehouse> childrenList = new ArrayList<>();
        final Warehouse warehouse = findChildrenWarehouseById(map);
        if (null != warehouse && CollectionUtils.isNotEmpty(warehouse.getChildrenWarehouse())) {
            for (Warehouse ws : warehouse.getChildrenWarehouse()) {
                childrenList.add(ws);
                map.put("warehouseId", ws.getId());
                childrenList.addAll(findChildrenTreeMenuDataList(map));
            }
        }
        return childrenList;
    }

    public Page<Warehouse> findPage(Page<Warehouse> page, Warehouse warehouse) {
        warehouse.getSqlMap().put("dsf", dataScopeFilter(warehouse.getCurrentUser(), "o", "u"));
        final Map<String, String> map = new HashMap<>();
        final User currentUser = UserUtils.getUser();
        if (null != currentUser.getCompany()) {
            map.put("company", currentUser.getCompany().getId());
        }
        map.put("delFlag", Warehouse.DEL_FLAG_NORMAL);
        return super.findPage(page, warehouse);
    }

    @Transactional(readOnly = false)
    public void save(Warehouse warehouse) {
        warehouse.setCompany(UserUtils.getOfficeBelongToUser());
        final String address = warehouse.getSalesArea().getName() + warehouse.getAddress();
        final Map<String, String> position = BaiDuPositionUtils.generatePosition(address);
        if (null != position.get("lng") && null != position.get("lat")) {
            warehouse.setLongitude(String.valueOf(position.get("lng")));
            warehouse.setLatitude(String.valueOf(position.get("lat")));
        }

        if (StringUtils.isNotBlank(warehouse.getName())) {
            warehouse.setWarehouseJianMa(ExtractPinYinFromHanZiUtils.extractPinYinFirstSpell(warehouse.getName()));
        }
        super.save(warehouse);
    }

    @Transactional(readOnly = false)
    public void delete(final Map<String, String> map) {
        final List<String> childrenIdList = new ArrayList<>();
        if (StringUtils.isNotEmpty(map.get("warehouseId"))) {
            childrenIdList.add(map.get("warehouseId"));
            final List<Warehouse> childrenList = findChildrenTreeMenuDataList(map);
            for (Warehouse ws : childrenList) {
                childrenIdList.add(ws.getId());
            }
        }

        if(CollectionUtils.isNotEmpty(childrenIdList)){
            final Warehouse warehouse = new Warehouse();
            warehouse.setDelFlag(warehouse.DEL_FLAG_NORMAL);
            warehouse.getAllChildrenWarehouse().addAll(childrenIdList);
            this.dao.deleteWarehouseByIds(warehouse);
        }
    }
}
