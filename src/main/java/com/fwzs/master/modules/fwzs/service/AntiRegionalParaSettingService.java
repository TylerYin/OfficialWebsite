package com.fwzs.master.modules.fwzs.service;

import com.fwzs.master.common.persistence.Page;
import com.fwzs.master.common.service.CrudService;
import com.fwzs.master.modules.fwzs.dao.AntiRegionalParaSettingDao;
import com.fwzs.master.modules.fwzs.entity.AntiRegionalParaSetting;
import com.fwzs.master.modules.sys.utils.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Tyler Yin
 * @create 2018-03-13 9:48
 * @description AntiRegionalDumping Service
 **/
@Service
@Transactional(readOnly = true)
public class AntiRegionalParaSettingService extends CrudService<AntiRegionalParaSettingDao, AntiRegionalParaSetting> {

    public AntiRegionalParaSetting get(String id) {
        return super.get(id);
    }

    public List<AntiRegionalParaSetting> findList(AntiRegionalParaSetting antiRegionalParaSetting, boolean isSystemManager) {
        antiRegionalParaSetting.setSystemManager(isSystemManager);
        if (!isSystemManager) {
            antiRegionalParaSetting.setCompany(UserUtils.getUser().getCompany());
            if (UserUtils.isDealer()) {
                antiRegionalParaSetting.setDealer(UserUtils.findCurrentDealer());
            }
        }
        return super.findList(antiRegionalParaSetting);
    }

    public Page<AntiRegionalParaSetting> findPage(Page<AntiRegionalParaSetting> page, AntiRegionalParaSetting antiRegionalParaSetting) {
        antiRegionalParaSetting.getSqlMap().put("dsf", dataScopeFilter(antiRegionalParaSetting.getCurrentUser(), "o", "u"));
        return super.findPage(page, antiRegionalParaSetting);
    }

    @Transactional(readOnly = false)
    public void save(AntiRegionalParaSetting antiRegionalParaSetting) {
        antiRegionalParaSetting.setCompany(UserUtils.getUser().getCompany());
        if (UserUtils.isDealer()) {
            antiRegionalParaSetting.setDealer(UserUtils.findCurrentDealer());
        }
        super.save(antiRegionalParaSetting);
    }

    @Transactional(readOnly = false)
    public void delete(AntiRegionalParaSetting antiRegionalParaSetting) {
        super.delete(antiRegionalParaSetting);
    }
}
