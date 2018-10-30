package com.fwzs.master.common.utils;

import com.fwzs.master.modules.fwzs.entity.TreeMenu;
import com.fwzs.master.modules.sys.entity.Area;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Tyler Yin
 * @create 2017-11-17 10:23
 * @description 树型菜单工具类
 **/
public class TreeMenuUtils {

    /**
     * 获取区域树型下拉选择菜单数据
     * @return
     */
    public static Map<String, List<TreeMenu>> generateAreaTreeMenu(final List<Area> areaList) {
        final List<TreeMenu> areaTreeList = new ArrayList<>();
        final Map<String, List<TreeMenu>> areaTreeMap = new HashMap<>();
        if (CollectionUtils.isNotEmpty(areaList)) {
            for (final Area area : areaList) {
                TreeMenu treeMenu = new TreeMenu();
                treeMenu.setId(area.getId());
                treeMenu.setpId(area.getParentId());
                treeMenu.setName(area.getName());
                treeMenu.setOpen(true);
                areaTreeList.add(treeMenu);
            }
        }
        areaTreeMap.put("areaTreeMap", areaTreeList);
        return areaTreeMap;
    }

}
