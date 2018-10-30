package com.fwzs.master.modules.fwzs.web;

import com.fwzs.master.common.config.Global;
import com.fwzs.master.common.persistence.Page;
import com.fwzs.master.common.utils.*;
import com.fwzs.master.common.web.BaseController;
import com.fwzs.master.modules.fwzs.entity.TreeMenu;
import com.fwzs.master.modules.fwzs.entity.Warehouse;
import com.fwzs.master.modules.fwzs.service.WarehouseService;
import com.fwzs.master.modules.sys.entity.Area;
import com.fwzs.master.modules.sys.entity.User;
import com.fwzs.master.modules.sys.service.AreaService;
import com.fwzs.master.modules.sys.utils.UserUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Tyler Yin
 * @create 2017-11-17 9:25
 * @description 仓库管理
 **/
@Controller
@RequestMapping(value = "${adminPath}/fwzs/warehouse")
public class WarehouseController extends BaseController {
    @Autowired
    private WarehouseService warehouseService;
    @Autowired
    private AreaService areaService;

    @ModelAttribute("warehouse")
    public Warehouse get(@RequestParam(required = false) String id) {
        if (StringUtils.isNotBlank(id)) {
            return warehouseService.get(id);
        } else {
            return new Warehouse();
        }
    }

    @RequiresPermissions("fwzs:dealer:view")
    @RequestMapping(value = {"list", ""})
    public String list(Warehouse warehouse, HttpServletRequest request,
                       HttpServletResponse response, Model model) {
        warehouse.setCurrentUser(UserUtils.getUser());

        final String name = ModelUtils.getValueFromModel(model, "name");
        if (StringUtils.isNotEmpty(name)) {
            warehouse.setName(name);
        }

        final String phone = ModelUtils.getValueFromModel(model, "phone");
        if (StringUtils.isNotEmpty(phone)) {
            warehouse.setPhone(phone);
        }

        final String areaId = ModelUtils.getValueFromModel(model, "areaId");
        final String areaName = ModelUtils.getValueFromModel(model, "areaName");
        if (StringUtils.isNotEmpty(areaId) && StringUtils.isNotEmpty(areaName)) {
            final Area area = new Area();
            area.setId(areaId);
            area.setName(areaName);
            warehouse.setSalesArea(area);
        }

        final Page<Warehouse> page = warehouseService.findPage(new Page<Warehouse>(
                request, response), warehouse);
        model.addAttribute("page", page);
        return "modules/fwzs/warehouseList";
    }

    @RequiresPermissions("fwzs:dealer:view")
    @RequestMapping(value = "form")
    public String form(Warehouse warehouse, Model model) {
        if (null != warehouse) {
            if (StringUtils.isBlank(warehouse.getId())) {
                warehouse.setWarehouseNo(IdGen.genProdFileSerialNum(3, "CK"));
            }
        }
        model.addAttribute("warehouse", warehouse);
        return "modules/fwzs/warehouseForm";
    }

    @RequiresPermissions("fwzs:dealer:edit")
    @RequestMapping(value = "save")
    public String save(Warehouse warehouse, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, warehouse)) {
            return form(warehouse, model);
        }
        warehouseService.save(warehouse);
        addMessage(redirectAttributes, "保存仓库成功");
        return "redirect:" + Global.getAdminPath() + "/fwzs/warehouse/list/?repage";
    }

    @RequiresPermissions("fwzs:dealer:edit")
    @RequestMapping(value = "delete")
    public String delete(Warehouse warehouse, final HttpServletRequest request, RedirectAttributes redirectAttributes) {
        final Map<String, String> map = new HashMap<>();
        final User currentUser = UserUtils.getUser();
        if (null != currentUser.getCompany()) {
            map.put("company", currentUser.getCompany().getId());
        }
        map.put("delFlag", Warehouse.DEL_FLAG_NORMAL);
        map.put("warehouseId", warehouse.getId());
        warehouseService.delete(map);
        addMessage(redirectAttributes, "删除仓库成功");
        redirectAttributes.addFlashAttribute("name", request.getParameter("warehouse.name"));
        redirectAttributes.addFlashAttribute("phone", request.getParameter("warehouse.phone"));
        redirectAttributes.addFlashAttribute("areaId", request.getParameter("warehouse.salesArea.id"));
        redirectAttributes.addFlashAttribute("areaName", request.getParameter("warehouse.salesArea.name"));
        return "redirect:" + Global.getAdminPath() + "/fwzs/warehouse/list/?repage";
    }

    /**
     * 获取区域树型下拉选择菜单数据
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/areaMenuData", method = RequestMethod.GET)
    public Map<String, List<TreeMenu>> getAreaList() {
        return TreeMenuUtils.generateAreaTreeMenu(areaService.findAll());
    }

    /**
     * 获取仓库树型下拉选择菜单数据
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/warehouseMenuData", method = RequestMethod.GET)
    public Map<String, List<TreeMenu>> getWarehouseList(HttpServletRequest request) {
        final Warehouse warehouse = new Warehouse();
        warehouse.setCompany(UserUtils.getUser().getCompany());
        return warehouseService.findTreeMenuDataList(warehouse);
    }
}
