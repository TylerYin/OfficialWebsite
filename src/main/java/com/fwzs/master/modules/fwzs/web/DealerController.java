package com.fwzs.master.modules.fwzs.web;

import com.fwzs.master.common.config.Global;
import com.fwzs.master.common.persistence.Page;
import com.fwzs.master.common.utils.IdGen;
import com.fwzs.master.common.utils.ModelUtils;
import com.fwzs.master.common.utils.StringUtils;
import com.fwzs.master.common.utils.TreeMenuUtils;
import com.fwzs.master.common.web.BaseController;
import com.fwzs.master.modules.fwzs.entity.Dealer;
import com.fwzs.master.modules.fwzs.entity.TreeMenu;
import com.fwzs.master.modules.fwzs.service.DealerService;
import com.fwzs.master.modules.sys.entity.Area;
import com.fwzs.master.modules.sys.entity.Office;
import com.fwzs.master.modules.sys.entity.User;
import com.fwzs.master.modules.sys.service.AreaService;
import com.fwzs.master.modules.sys.service.SystemService;
import com.fwzs.master.modules.sys.utils.UserUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Tyler Yin
 * @create 2017-11-13 14:04
 * @description 经销商管理
 **/
@Controller
@RequestMapping(value = "${adminPath}/fwzs/dealer")
public class DealerController extends BaseController {
    @Autowired
    private DealerService dealerService;
    @Autowired
    private AreaService areaService;
    @Autowired
    private SystemService systemService;

    @ModelAttribute("dealer")
    public Dealer get(@RequestParam(required=false) String id) {
        if (StringUtils.isNotBlank(id)){
            return dealerService.get(id);
        }else{
            return new Dealer();
        }
    }

    @RequiresPermissions("fwzs:dealer:view")
    @RequestMapping(value = {"list", ""})
    public String list(Dealer dealer, HttpServletRequest request,
                       HttpServletResponse response, Model model) {
        dealer.setCurrentUser(UserUtils.getUser());

        final String phone = ModelUtils.getValueFromModel(model, "phone");
        if (StringUtils.isNotEmpty(phone)) {
            dealer.setPhone(phone);
        }

        final String name = ModelUtils.getValueFromModel(model, "name");
        if (StringUtils.isNotEmpty(name)) {
            dealer.setName(name);
        }

        final String areaId = ModelUtils.getValueFromModel(model, "areaId");
        final String areaName = ModelUtils.getValueFromModel(model, "areaName");
        if (StringUtils.isNotEmpty(areaId) && StringUtils.isNotEmpty(areaName)) {
            final Area area = new Area();
            area.setId(areaId);
            area.setName(areaName);
            dealer.setSalesArea(area);
        }

        final String companyName = ModelUtils.getValueFromModel(model, "company");
        if (StringUtils.isNotEmpty(companyName)) {
            final Office company = new Office();
            company.setName(companyName);
            dealer.setCompany(company);
        }

        Page<Dealer> page = dealerService.findPage(new Page<Dealer>(
                request, response), dealer);
        model.addAttribute("page", page);
        return "modules/fwzs/dealerList";
    }

    @RequiresPermissions("fwzs:dealer:view")
    @RequestMapping(value = "form")
    public String form(Dealer dealer, Model model) {
        if (null != dealer) {
            if (StringUtils.isBlank(dealer.getId())) {
                dealer.setDealerNo(IdGen.genProdFileSerialNum(3, "JXS"));
            }
        }

        boolean currentUserIsDealer = UserUtils.isDealer();
        model.addAttribute("currentUserIsDealer", currentUserIsDealer);
        if (currentUserIsDealer) {
            Dealer currentDealer = UserUtils.findCurrentDealer();
            model.addAttribute("currentDealer", currentDealer);
        }

        if (CollectionUtils.isNotEmpty(dealer.getAreaList())) {
            List<String> areaNames = new ArrayList<>();
            for (Area area : dealer.getAreaList()) {
                if (Global.AREA_TYPE_COUNTRY.equals(area.getId())) {
                    continue;
                }
                areaNames.add(areaService.get(area).getName());
            }
            dealer.setAreaNames(StringUtils.join(areaNames, ","));
        }

        model.addAttribute("dealer", dealer);
        return "modules/fwzs/dealerForm";
    }

    /**
     * 根据经销商获取销售区域
     *
     * @param dealerId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "getSalesAreaIds")
    public List<String> findAreaByDealer(String dealerId) {
        Dealer dealer = new Dealer();
        dealer.setId(dealerId);
        return dealerService.findAreaIdsByDealer(dealer);
    }

    @RequiresPermissions("fwzs:dealer:edit")
    @RequestMapping(value = "save")
    public String save(Dealer dealer, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, dealer)) {
            return form(dealer, model);
        }

        boolean isCreateDealerSuccess = dealerService.createDealerAccount(dealer);
        if (isCreateDealerSuccess) {
            dealerService.save(dealer);
            addMessage(redirectAttributes, "保存经销商成功");
        } else {
            addMessage(redirectAttributes, "保存经销商失败");
        }
        return "redirect:" + Global.getAdminPath() + "/fwzs/dealer/list/?repage";
    }

    @RequiresPermissions("fwzs:dealer:edit")
    @RequestMapping(value = "delete")
    public String delete(Dealer dealer, final HttpServletRequest request, RedirectAttributes redirectAttributes) {
        final Map<String, String> map = new HashMap<>();
        final User currentUser = UserUtils.getUser();
        if (null != currentUser.getCompany()) {
            map.put("company", currentUser.getCompany().getId());
        }

        dealerService.delete(dealer);
        addMessage(redirectAttributes, "删除经销商成功");

        redirectAttributes.addFlashAttribute("name", request.getParameter("dealer.name"));
        redirectAttributes.addFlashAttribute("phone", request.getParameter("dealer.phone"));
        redirectAttributes.addFlashAttribute("areaId", request.getParameter("dealer.salesArea.id"));
        redirectAttributes.addFlashAttribute("areaName", request.getParameter("dealer.salesArea.name"));
        redirectAttributes.addFlashAttribute("company", request.getParameter("dealer.company.name"));

        return "redirect:" + Global.getAdminPath() + "/fwzs/dealer/list/?repage";
    }

    /**
     * 获取区域树型下拉选择菜单数据
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/areaMenuData", method = RequestMethod.GET)
    public Map<String, List<TreeMenu>> getAreaList() {
        return TreeMenuUtils.generateAreaTreeMenu(areaService.findAll());
    }

    /**
     * 获取经销商树型下拉选择菜单数据
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/dealerMenuData", method = RequestMethod.GET)
    public Map<String, List<TreeMenu>> getDealerList(HttpServletRequest request) {
        final Dealer dealer = new Dealer();
        final String level = request.getParameter("level");
        if (StringUtils.isNotBlank(level)) {
            dealer.setGrade(level);
        }
        return dealerService.findTreeMenuDataListByLevel(dealer);
    }

    /**
     * 根据登录用户名和所属公司查询帐户是否合法。
     * 若数据库中已经存在，则表明该帐户非法，否则表明该帐户有效。
     *
     * @param account
     * @param originAccount
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/isUserAccountValidate", method = RequestMethod.GET)
    public boolean isUserAccountValidate(@RequestParam("account") String account, @RequestParam("originAccount") String originAccount) {
        if (originAccount != null && originAccount.equals(account))
            return true;
        return !systemService.isUserAccountExist(account, UserUtils.getUser().getCompany());
    }
}
