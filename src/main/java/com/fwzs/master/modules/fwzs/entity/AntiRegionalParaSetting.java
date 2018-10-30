package com.fwzs.master.modules.fwzs.entity;

import com.fwzs.master.common.persistence.DataEntity;
import com.fwzs.master.modules.sys.entity.Office;

/**
 * @author Tyler Yin
 * @create 2018-03-15 14:07
 * @description Anti Regional Parameter Setting
 **/
public class AntiRegionalParaSetting extends DataEntity<AntiRegionalParaSetting> {
    private static final long serialVersionUID = 1L;

    private Dealer dealer;                       // 经销商
    private Office company;                      // 企业
    private boolean systemManager;               // 是否是系统管理员
    private Integer antiRegionalThreshold;       // 是否窜货阈值数量

    public Office getCompany() {
        return company;
    }

    public void setCompany(Office company) {
        this.company = company;
    }

    public Dealer getDealer() {
        return dealer;
    }

    public void setDealer(Dealer dealer) {
        this.dealer = dealer;
    }

    public boolean getSystemManager() {
        return systemManager;
    }

    public void setSystemManager(boolean systemManager) {
        this.systemManager = systemManager;
    }

    public Integer getAntiRegionalThreshold() {
        return antiRegionalThreshold;
    }

    public void setAntiRegionalThreshold(Integer antiRegionalThreshold) {
        this.antiRegionalThreshold = antiRegionalThreshold;
    }
}
