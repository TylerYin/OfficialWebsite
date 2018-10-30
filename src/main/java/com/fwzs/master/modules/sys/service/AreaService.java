/**
 * 
 */
package com.fwzs.master.modules.sys.service;

import com.fwzs.master.common.config.Global;
import com.fwzs.master.common.service.TreeService;
import com.fwzs.master.modules.sys.dao.AreaDao;
import com.fwzs.master.modules.sys.entity.Area;
import com.fwzs.master.modules.sys.utils.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 区域Service
 * @author ly
 * @version 2014-05-16
 */
@Service
@Transactional(readOnly = true)
public class AreaService extends TreeService<AreaDao, Area> {

	public List<Area> findAll(){
		return UserUtils.getAreaList();
	}

    /**
     * 获取县区级以上的行政区域
     *
     * @return
     */
    public List<Area> findAboveCityLevelArea() {
        List<Area> filteredAreas = new ArrayList<>();
        for (Area area : findAll()) {
            if (!Global.AREA_TYPE_TOWN.equals(area.getType())) {
                filteredAreas.add(area);
            }
        }
        return filteredAreas;
    }

    /**
     * 根据上级行政区域编码获取所有的直接下级行政区域
     *
     * @param parentId
     * @return
     */
    public List<Area> getChildArea(String parentId) {
        List<Area> filteredAreas = new ArrayList<>();
        for (Area area : findAll()) {
            if (parentId.equals(area.getParentId())) {
                filteredAreas.add(area);
            }
        }
        return filteredAreas;
    }

    /**
     * 查找所有下属的行政区域
     *
     * @param area
     * @return
     */
	public List<Area> findChildrenAreas(Area area){
	    return dao.findChildrenAreas(area);
    }

    /**
     * 从sys_area获取全部的Area
     *
     * @return
     */
    public List<Area> getAreaListWithNoCache() {
        return dao.findAllList(new Area());
    }

	@Transactional(readOnly = false)
	public void save(Area area) {
		super.save(area);
		UserUtils.removeCache(UserUtils.CACHE_AREA_LIST);
	}
	
	@Transactional(readOnly = false)
	public void delete(Area area) {
		super.delete(area);
		UserUtils.removeCache(UserUtils.CACHE_AREA_LIST);
	}
	
}
