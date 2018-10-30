/**
 * 
 */
package com.fwzs.master.modules.sys.web;

import com.fwzs.master.common.config.Global;
import com.fwzs.master.common.utils.IdGen;
import com.fwzs.master.common.utils.StringUtils;
import com.fwzs.master.common.web.BaseController;
import com.fwzs.master.modules.fwzs.entity.Dealer;
import com.fwzs.master.modules.fwzs.service.DealerService;
import com.fwzs.master.modules.sys.entity.Area;
import com.fwzs.master.modules.sys.service.AreaService;
import com.fwzs.master.modules.sys.utils.UserUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * 区域Controller
 * @author ly
 * @version 2013-5-15
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/area")
public class AreaController extends BaseController {

	@Autowired
	private AreaService areaService;

	@Autowired
    private DealerService dealerService;
	
	@ModelAttribute("area")
	public Area get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return areaService.get(id);
		}else{
			return new Area();
		}
	}

	@RequiresPermissions("sys:area:view")
	@RequestMapping(value = {"list", ""})
	public String list(Area area, Model model) {
		model.addAttribute("list", areaService.findAboveCityLevelArea());
		return "modules/sys/areaList";
	}

    /**
     * 根据上级行政区域编码获取所有的直接下级行政区域
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("getChildArea")
    public List<Area> getChildArea(HttpServletRequest request) {
        String parentId = request.getParameter("parentId");
        if (StringUtils.isNotBlank(parentId)) {
            return areaService.getChildArea(parentId);
        } else {
            return Collections.emptyList();
        }
    }

	@RequiresPermissions("sys:area:view")
	@RequestMapping(value = "form")
	public String form(Area area, Model model) {
		if (area.getParent()==null||area.getParent().getId()==null){
			area.setParent(UserUtils.getUser().getOffice().getArea());
		}
		area.setParent(areaService.get(area.getParent().getId()));
//		// 自动获取排序号
//		if (StringUtils.isBlank(area.getId())){
//			int size = 0;
//			List<Area> list = areaService.findAll();
//			for (int i=0; i<list.size(); i++){
//				Area e = list.get(i);
//				if (e.getParent()!=null && e.getParent().getId()!=null
//						&& e.getParent().getId().equals(area.getParent().getId())){
//					size++;
//				}
//			}
//			area.setCode(area.getParent().getCode() + StringUtils.leftPad(String.valueOf(size > 0 ? size : 1), 4, "0"));
//		}
		model.addAttribute("area", area);
		return "modules/sys/areaForm";
	}
	
	@RequiresPermissions("sys:area:edit")
	@RequestMapping(value = "save")
	public String save(Area area, Model model, RedirectAttributes redirectAttributes) {
		if(Global.isDemoMode()){
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:" + adminPath + "/sys/area";
		}
		if (!beanValidator(model, area)){
			return form(area, model);
		}
		areaService.save(area);
		addMessage(redirectAttributes, "保存区域'" + area.getName() + "'成功");
		return "redirect:" + adminPath + "/sys/area/";
	}
	
	@RequiresPermissions("sys:area:edit")
	@RequestMapping(value = "delete")
	public String delete(Area area, RedirectAttributes redirectAttributes) {
		if(Global.isDemoMode()){
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:" + adminPath + "/sys/area";
		}
//		if (Area.isRoot(id)){
//			addMessage(redirectAttributes, "删除区域失败, 不允许删除顶级区域或编号为空");
//		}else{
			areaService.delete(area);
			addMessage(redirectAttributes, "删除区域成功");
//		}
		return "redirect:" + adminPath + "/sys/area/";
	}

    @RequiresPermissions("user")
    @ResponseBody
    @RequestMapping(value = "treeData")
    public List<Map<String, Object>> treeData(@RequestParam(required = false) String extId,
        @RequestParam(required = false) String parentDealerId, HttpServletResponse response) {
        List<Map<String, Object>> mapList = Lists.newArrayList();

        List<Area> list = areaService.findAll();
        if (StringUtils.isNotBlank(parentDealerId)) {
            list = filterAreaByDealer(parentDealerId, list);
        }

        for (int i = 0; i < list.size(); i++) {
            Area e = list.get(i);
            if (StringUtils.isBlank(extId) || (extId != null && !extId.equals(e.getId()) && e.getParentIds().indexOf("," + extId + ",") == -1)) {
                Map<String, Object> map = Maps.newHashMap();
                map.put("id", e.getId());
                map.put("pId", e.getParentId());
                map.put("name", e.getName());
                mapList.add(map);
            }
        }
        return mapList;
    }

    /**
     * 过滤掉已不属于经销商的销售区域
     *
     * @param dealerId
     * @param areas
     */
    private List<Area> filterAreaByDealer(String dealerId, List<Area> areas) {
        List<Area> filteredAreas = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(areas) && StringUtils.isNotBlank(dealerId)) {
            Dealer dealer = new Dealer();
            dealer.setId(dealerId);

            List<Area> dealerAreas = dealerService.findAreaByDealer(dealer);
            if (CollectionUtils.isNotEmpty(dealerAreas)) {
                for (Area dealerArea : dealerAreas) {
                    List<Area> childrenAreas = findChildrenArea(areas, dealerArea);
                    if (Integer.valueOf(dealerArea.getType()) < Integer.valueOf(Global.AREA_TYPE_TOWN)
                            && CollectionUtils.isNotEmpty(childrenAreas)
                            && !isHaveChildren(dealerAreas, childrenAreas)) {
                        filteredAreas.add(dealerArea);
                        filteredAreas.addAll(childrenAreas);
                    } else {
                        filteredAreas.add(dealerArea);
                    }
                }
            }
        }

        List<String> filteredAreaIds = new ArrayList<>();
        for(Area area : filteredAreas){
            filteredAreaIds.add(area.getId());
        }

        List<Area> newAreas = new ArrayList<>();
        for (Area area : areas) {
            if (filteredAreaIds.contains(area.getId())) {
                newAreas.add(area);
            }
        }

        return newAreas;
    }

    /**
     * 获取当前区域的所有下属区域
     *
     * @param areas
     * @param parentArea
     * @return
     */
    private List<Area> findChildrenArea(List<Area> areas, Area parentArea) {
        List<Area> childrenAreas = new ArrayList<>();
        for (Area area : areas) {
            if (area.getParentIds().contains(parentArea.getId())) {
                childrenAreas.add(area);
            }
        }
        return childrenAreas;
    }

    /**
     * 判断当前区域是否包含子区域
     *
     * @param dealerAreas
     * @param childrenAreas
     * @return
     */
    private boolean isHaveChildren(List<Area> dealerAreas, List<Area> childrenAreas) {
        for (Area area : childrenAreas) {
            if (dealerAreas.contains(area)) {
                return true;
            }
        }
        return false;
    }
//	public static void main(String[] args) {
//		long start=new Date().getTime();
//		System.out.println(start);
//		HashSet<String> set=new HashSet<>();
//		ArrayList<String> list=new ArrayList<>();
//		Random ne=new Random();
//		int x;
//		String lsh;
// 		for (int i = 0; i < 100000; i++) {
// 			long middle=new Date().getTime();
// 			x=ne.nextInt(9999-1000+1)+1000;
// 			lsh=String.format("%08d", i);
//			System.out.println(middle+""+x);
//			set.add(middle+lsh);
//			list.add(middle+lsh);
////			System.out.println(String.format("%05d", i));
//		}
//		long end=new Date().getTime();
//		System.out.println(end);
//		System.out.println(end-start);
//		System.out.println(set.size());
//		System.out.println(list.size());
//		
//	}
	public static void main(String[] args) {
		HashMap<String,Long> map=new HashMap<>();
		HashMap<String,String> map2=new HashMap<>();
		for (int i = 0; i < 5000000; i++) {
			long cc=System.nanoTime();
			map.put(String.valueOf(cc).substring(8)+IdGen.getFixLenthString(16), System.currentTimeMillis());
			System.out.println(String.valueOf(cc).substring(8)+IdGen.getFixLenthString(16));
			map2.put(String.valueOf(cc).substring(0), String.valueOf(cc).substring(8));
			
		}
		System.out.println(map.size());
		System.out.println(map2.size());
	}
}
