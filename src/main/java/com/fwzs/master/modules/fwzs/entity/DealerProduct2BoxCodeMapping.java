package com.fwzs.master.modules.fwzs.entity;

import com.fwzs.master.common.persistence.DataEntity;

import java.util.Date;

/**
 * @author Tyler Yin
 * @create 2017-11-24 11:11
 * @description 产品和箱码关系映射实体
 **/
public class DealerProduct2BoxCodeMapping extends DataEntity<DealerProduct2BoxCodeMapping> {
    private static final long serialVersionUID = 1L;

    private String boxCode;
    private String boxCodeType;
    private String dealerBound2ProductId;
    private Date scanDate;

    public String getDealerBound2ProductId() {
        return dealerBound2ProductId;
    }

    public void setDealerBound2ProductId(String dealerBound2ProductId) {
        this.dealerBound2ProductId = dealerBound2ProductId;
    }

    public String getBoxCode() {
        return boxCode;
    }

    public void setBoxCode(String boxCode) {
        this.boxCode = boxCode;
    }

    public String getBoxCodeType() {
        return boxCodeType;
    }

    public void setBoxCodeType(String boxCodeType) {
        this.boxCodeType = boxCodeType;
    }

    public Date getScanDate() {
        return scanDate;
    }

    public void setScanDate(Date scanDate) {
        this.scanDate = scanDate;
    }

}
