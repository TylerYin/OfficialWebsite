package com.fwzs.master.modules.fwzs.entity;

import com.fwzs.master.common.persistence.DataEntity;
import com.fwzs.master.modules.sys.entity.Office;

import java.util.Date;

/**
 * @author Tyler Yin
 * @create 2018-03-15 14:25
 * @description AntiRegional Dumping
 **/
public class AntiRegionalDumping extends DataEntity<AntiRegionalDumping> {
    private Long antiCount;                         // 窜货数量
    private String qrCode;                          // 防伪码
    private Dealer dealer;                          // 经销商
    private BsProduct bsProduct;                    // 产品
    private Office company;                         // 产品所属公司
    private int queryTimes;                         // 查询次数
    private FwmQueryHistory fwmQueryHistory;        // 历史查询记录主键

    private Date beginDate;                         // 开始日期
    private Date endDate;                           // 结束日期
    private boolean systemManager = true;           // 是否是越级用户

    public Long getAntiCount() {
        return antiCount;
    }

    public void setAntiCount(Long antiCount) {
        this.antiCount = antiCount;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public Dealer getDealer() {
        return dealer;
    }

    public void setDealer(Dealer dealer) {
        this.dealer = dealer;
    }

    public BsProduct getBsProduct() {
        return bsProduct;
    }

    public void setBsProduct(BsProduct bsProduct) {
        this.bsProduct = bsProduct;
    }

    public Office getCompany() {
        return company;
    }

    public void setCompany(Office company) {
        this.company = company;
    }

    public int getQueryTimes() {
        return queryTimes;
    }

    public void setQueryTimes(int queryTimes) {
        this.queryTimes = queryTimes;
    }

    public FwmQueryHistory getFwmQueryHistory() {
        return fwmQueryHistory;
    }

    public void setFwmQueryHistory(FwmQueryHistory fwmQueryHistory) {
        this.fwmQueryHistory = fwmQueryHistory;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public boolean isSystemManager() {
        return systemManager;
    }

    public void setSystemManager(boolean systemManager) {
        this.systemManager = systemManager;
    }
}
