package com.fwzs.master.modules.fwzs.service;

import com.fwzs.master.common.config.Global;
import com.fwzs.master.common.persistence.Page;
import com.fwzs.master.common.service.CrudService;
import com.fwzs.master.modules.fwzs.dao.PdaUserDao;
import com.fwzs.master.modules.fwzs.entity.Dealer;
import com.fwzs.master.modules.fwzs.entity.PdaUser;
import com.fwzs.master.modules.sys.entity.Office;
import com.fwzs.master.modules.sys.service.SystemService;
import com.fwzs.master.modules.sys.utils.UserUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Tyler Yin
 * @create 2018-02-01 9:18
 * @description PDA User Service
 **/
@Service
@Transactional(readOnly = true)
public class PdaUserService extends CrudService<PdaUserDao, PdaUser> {

    public Page<PdaUser> findPage(Page<PdaUser> page, PdaUser pdaUser) {
        pdaUser.getSqlMap().put("dsf", dataScopeFilter(pdaUser.getCurrentUser(), "o", "u"));
        return super.findPage(page, pdaUser);
    }

    public List<PdaUser> findPdaByUser(PdaUser pdaUser) {
        pdaUser.setLoginFlag(Global.LOGIN_FLAG_ENABLE);
        return dao.findPdaByUser(pdaUser);
    }

    @Transactional(readOnly = false)
    public void save(PdaUser pdaUser) {
        if (UserUtils.isDealer()) {
            pdaUser.setDealer(UserUtils.findCurrentDealer());
        }

        pdaUser.setCompany(UserUtils.getUser().getCompany());
        pdaUser.setPassword(SystemService.entryptPassword(pdaUser.getPassword()));
        super.save(pdaUser);
    }

    /**
     * 根据登录用户名和所属公司查询帐户是否合法。
     * 若数据库中已经存在，则表明该帐户非法，否则表明该帐户有效。
     *
     * @param loginName
     * @param company
     * @return
     */
    public boolean isUserAccountValidate(String loginName, Office company) {
        PdaUser pdaUser = new PdaUser();
        pdaUser.setLoginName(loginName);
        pdaUser.setCompany(company);
        return CollectionUtils.isNotEmpty(dao.getByLoginNameAndCompany(pdaUser)) ? true : false;
    }

    /**
     * 根据企业和经销商获取PDA用户信息
     *
     * @param companyId
     * @param dealerId
     * @return
     */
    public List<PdaUser> findPdaInfor(final String companyId, final String dealerId) {
        PdaUser pdaUser = new PdaUser();
        pdaUser.setLoginFlag(Global.LOGIN_FLAG_ENABLE);
        pdaUser.setDelFlag(PdaUser.DEL_FLAG_NORMAL);
        if (StringUtils.isNotBlank(companyId)) {
            Office company = new Office();
            company.setId(companyId);
            pdaUser.setCompany(company);
        }

        if (StringUtils.isNotBlank(dealerId)) {
            Dealer dealer = new Dealer();
            dealer.setId(dealerId);
            pdaUser.setDealer(dealer);
        }
        return dao.findPdaInfor(pdaUser);
    }
}
