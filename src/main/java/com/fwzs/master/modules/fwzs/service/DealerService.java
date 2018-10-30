package com.fwzs.master.modules.fwzs.service;

import com.fwzs.master.common.config.Global;
import com.fwzs.master.common.persistence.Page;
import com.fwzs.master.common.service.CrudService;
import com.fwzs.master.common.utils.BaiDuPositionUtils;
import com.fwzs.master.common.utils.ExtractPinYinFromHanZiUtils;
import com.fwzs.master.common.utils.StringUtils;
import com.fwzs.master.modules.fwzs.dao.DealerDao;
import com.fwzs.master.modules.fwzs.entity.Dealer;
import com.fwzs.master.modules.fwzs.entity.TreeMenu;
import com.fwzs.master.modules.sys.dao.UserDao;
import com.fwzs.master.modules.sys.entity.Area;
import com.fwzs.master.modules.sys.entity.Role;
import com.fwzs.master.modules.sys.entity.User;
import com.fwzs.master.modules.sys.service.AreaService;
import com.fwzs.master.modules.sys.service.SystemService;
import com.fwzs.master.modules.sys.utils.UserUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author Tyler Yin
 * @create 2017-11-13 14:39
 * @description 经销商Service
 **/
@Service
@Transactional(readOnly = true)
public class DealerService extends CrudService<DealerDao, Dealer> {

    @Autowired
    private UserDao userDao;

    @Autowired
    private AreaService areaService;

    @Autowired
    private SystemService systemService;

    public Dealer get(String id) {
        return super.get(id);
    }

    public List<Dealer> findList(Dealer dealer) {
        return super.findList(dealer);
    }

    public Page<Dealer> findPage(Page<Dealer> page, Dealer dealer) {
        if (UserUtils.isDealer()) {
            dealer.setPage(page);

            Dealer d = UserUtils.findCurrentDealer();
            if (null != d) {
                dealer.setParentIds(d.getId());
            }

            page.setList(dao.findListForDealerRole(dealer));
            return page;
        } else {
            dealer.getSqlMap().put("dsf", dataScopeFilter(dealer.getCurrentUser(), "o", "u"));
            return super.findPage(page, dealer);
        }
    }

    /**
     * 获取当前经销商所有的下级
     *
     * @return
     */
    public List<Dealer> findChildrenDealer() {
        Dealer d = UserUtils.findCurrentDealer();
        Dealer dealer = new Dealer();
        if (null != d) {
            dealer.setParentIds(d.getId());
        }
        return dao.findListForDealerRole(dealer);
    }

    @Transactional(readOnly = false)
    public void save(Dealer dealer) {
        final String address = dealer.getSalesArea().getName() + dealer.getAddress();
        final Map<String, String> position = BaiDuPositionUtils.generatePosition(address);
        if (null != position.get("lng") && null != position.get("lat")) {
            dealer.setLongitude(String.valueOf(position.get("lng")));
            dealer.setLatitude(String.valueOf(position.get("lat")));
        }

        if (StringUtils.isNotBlank(dealer.getName())) {
            dealer.setDealerJianMa(ExtractPinYinFromHanZiUtils.extractPinYinFirstSpell(dealer.getName()));
        }

        if ("1".equals(dealer.getGrade())) {
            dealer.setParentIds("0");
        } else {
            if (null != dealer.getParentDealer() && StringUtils.isNotBlank(dealer.getParentDealer().getId())) {
                Dealer parentDealer = get(dealer.getParentDealer().getId());
                dealer.setParentIds(parentDealer.getParentIds() + "," + parentDealer.getId());
            }
        }

        if (StringUtils.isBlank(dealer.getId())) {
            dealer.preInsert();
            dao.insert(dealer);
        } else {
            dealer.preUpdate();
            dao.update(dealer);
        }

        saveArea(dealer);
    }

    /**
     * 插入或更新经销商的销售区域
     *
     * @param dealer
     */
    private void saveArea(Dealer dealer) {
        Collection<String> unUsedAreas = CollectionUtils.subtract(get(dealer).getAreaIdList(), dealer.getAreaIdList());

        dao.deleteDealerArea(dealer);
        if (CollectionUtils.isNotEmpty(dealer.getAreaIdList())) {
            dao.insertDealerArea(dealer);
        }

        if (CollectionUtils.isNotEmpty(unUsedAreas)) {
            Area area = new Area();
            area.setParentIds(StringUtils.join(unUsedAreas, ","));
            List<Area> areas = areaService.findChildrenAreas(area);
            List<String> areaIds = new ArrayList<>();
            for (Area a : areas) {
                areaIds.add(a.getId());
            }

            // 级联删除下属经销商不能再使用的销售区域
            if (CollectionUtils.isNotEmpty(areaIds)) {
                dao.deleteUnusedSaleArea(dealer.getId(), areaIds);
            }
        }
    }

    /**
     * 根据经销商查找销售区域
     *
     * @param dealer
     * @return
     */
    public List<Area> findAreaByDealer(Dealer dealer) {
        return dao.findAreaByDealer(dealer);
    }

    /**
     * 获取销售区域主键列表
     *
     * @param dealer
     * @return
     */
    public List<String> findAreaIdsByDealer(Dealer dealer) {
        List<String> areaIds = new ArrayList<>();
        for (Area area : findAreaByDealer(dealer)) {
            areaIds.add(area.getId());
        }
        return areaIds;
    }

    /**
     * 根据防伪码获取经销商
     *
     * @param qrCode
     * @return
     */
    public List<Dealer> findDealerByQrcode(String qrCode) {
        return dao.findDealerByQrcode(Dealer.DEL_FLAG_NORMAL, qrCode);
    }

    /**
     * 删除经销商和关联的帐户
     *
     * @param dealer
     */
    @Transactional(readOnly = false)
    public void delete(Dealer dealer) {
        List<User> users = systemService.getByLoginNameAndCompany(dealer.getAccount(), dealer.getCompany());
        if (CollectionUtils.isNotEmpty(users)) {
            User user = users.get(0);
            dao.delete(dealer);
            userDao.delete(user);
        }
    }

    /**
     * 生成当前经销商的树型菜单
     *
     * @param dealer
     * @return
     */
    public Map<String, List<TreeMenu>> findTreeMenuDataListByLevel(final Dealer dealer) {
        if (UserUtils.isDealer()) {
            Dealer currentDealer = UserUtils.findCurrentDealer();
            final List<Dealer> dealerList = findChildrenDealer();
            final List<Dealer> filteredDealers = new ArrayList<>();

            if(StringUtils.isNotBlank(dealer.getGrade())){
                if (currentDealer.getGrade().equals(String.valueOf(Integer.valueOf(dealer.getGrade()) - 1))) {
                    filteredDealers.add(currentDealer);
                } else {
                    for (Dealer d : dealerList) {
                        if (String.valueOf(Integer.valueOf(dealer.getGrade()) - 1).equals(d.getGrade())) {
                            filteredDealers.add(d);
                        }
                    }
                }
            }else{
                filteredDealers.addAll(dealerList);
            }

            return generateTreeMenu(filteredDealers);
        } else {
            dealer.setCompany(dealer.getCurrentUser().getCompany());
            final List<Dealer> dealerList = this.dao.findTreeMenuDataListByLevel(dealer);
            return generateTreeMenu(dealerList);
        }
    }

    /**
     * 获取经销商树型菜单数据
     *
     * @param dealerList
     * @return
     */
    private Map<String, List<TreeMenu>> generateTreeMenu(final List<Dealer> dealerList){
        final List<TreeMenu> dealerTreeList = new ArrayList<>();
        final Map<String, List<TreeMenu>> dealerTreeMap = new HashMap<>();
        if (CollectionUtils.isNotEmpty(dealerList)) {
            for (final Dealer dealer : dealerList) {
                TreeMenu treeMenu = new TreeMenu();
                treeMenu.setId(dealer.getId());
                if (null != dealer.getParentDealer() && StringUtils.isNotEmpty(dealer.getParentDealer().getId())) {
                    treeMenu.setpId(dealer.getParentDealer().getId());
                } else {
                    treeMenu.setpId("0");
                }
                treeMenu.setOpen(true);
                treeMenu.setName(dealer.getName());
                dealerTreeList.add(treeMenu);
            }
        }
        dealerTreeMap.put("dealerTreeMap", dealerTreeList);
        return dealerTreeMap;
    }

    /**
     * 创建经销商帐户并赋予权限
     *
     * @param dealer
     * @return
     */
    @Transactional(readOnly = false)
    public boolean createDealerAccount(Dealer dealer) {
        if (StringUtils.isNotBlank(dealer.getId())) {
            Dealer existDealer = get(dealer.getId());
            User user = systemService.getUserByLoginName(existDealer.getAccount());
            user.setEmail(dealer.getEmail());
            user.setPhone(dealer.getPhone());
            user.setOffice(dealer.getCompany());
            user.setCompany(dealer.getCompany());
            user.setUserType(Global.USER_TYPE_DEALER);
            systemService.updateFullUserInfo(user);
            return true;
        } else {
            User user = new User();
            Role dealerRole;
            if ("3".equals(dealer.getGrade())) {
                dealerRole = systemService.getRoleByEnname("thirdLevelDealer");
            } else {
                dealerRole = systemService.getRoleByEnname("dealer");
            }

            if (null != dealerRole) {
                if (StringUtils.isBlank(dealer.getId()) && StringUtils.isNotBlank(dealer.getPassword())) {
                    dealer.setPassword(SystemService.entryptPassword(dealer.getPassword()));
                }
                dealer.setCompany(UserUtils.getOfficeBelongToUser());

                user.setName(dealer.getName());
                user.setLoginName(dealer.getAccount());
                user.setEmail(dealer.getEmail());
                user.setPhone(dealer.getPhone());
                user.setOffice(dealer.getCompany());
                user.setCompany(dealer.getCompany());
                user.setPassword(dealer.getPassword());
                user.setUserType(Global.USER_TYPE_DEALER);

                List<Role> roleList = new ArrayList<>();
                roleList.add(dealerRole);
                user.setRoleList(roleList);
                systemService.saveUser(user);
                return true;
            }
        }
        return false;
    }
}
