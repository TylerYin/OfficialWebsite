package com.fwzs.master.modules.fwzs.service;

import com.fwzs.master.common.persistence.Page;
import com.fwzs.master.common.service.CrudService;
import com.fwzs.master.modules.fwzs.dao.AntiRegionalDumpingDao;
import com.fwzs.master.modules.fwzs.entity.AntiRegionalDumping;
import com.fwzs.master.modules.sys.utils.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;

/**
 * @author Tyler Yin
 * @create 2018-03-13 9:48
 * @description AntiRegionalDumping Service
 **/
@Service
@Transactional(readOnly = true)
public class AntiRegionalDumpingService extends CrudService<AntiRegionalDumpingDao, AntiRegionalDumping> {
    public AntiRegionalDumping get(String id) {
        return super.get(id);
    }

    public Page<AntiRegionalDumping> findDetailPage(Page<AntiRegionalDumping> page, AntiRegionalDumping antiRegionalDumping) {
        if (!UserUtils.isSystemManager()) {
            antiRegionalDumping.setSystemManager(false);
            antiRegionalDumping.setCompany(UserUtils.getUser().getCompany());
        }

        antiRegionalDumping.setPage(page);
        page.setList(dao.findDetailList(antiRegionalDumping));
        return page;
    }

    public int getAntiRegionlByQrCode(String qrCode, String dealerId){
        return dao.getAntiRegionlByQrCode(qrCode, dealerId);
    }

    public Page<AntiRegionalDumping> findPage(Page<AntiRegionalDumping> page, AntiRegionalDumping antiRegionalDumping) {
        if (antiRegionalDumping.getBeginDate() == null) {
            Calendar now = Calendar.getInstance();
            now.add(Calendar.MONTH, -3);
            antiRegionalDumping.setBeginDate(now.getTime());
        }

        if (antiRegionalDumping.getEndDate() == null) {
            antiRegionalDumping.setEndDate(Calendar.getInstance().getTime());
        }

        if (!UserUtils.isSystemManager()) {
            antiRegionalDumping.setSystemManager(false);
            antiRegionalDumping.setCompany(UserUtils.getUser().getCompany());
            if (UserUtils.isDealer()) {
                antiRegionalDumping.setDealer(UserUtils.findCurrentDealer());
            }
        }

        return super.findPage(page, antiRegionalDumping);
    }

    @Transactional(readOnly = false)
    public void delete(AntiRegionalDumping antiRegionalDumping) {
        super.delete(antiRegionalDumping);
    }

}
