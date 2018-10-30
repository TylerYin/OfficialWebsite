package com.fwzs.master.modules.fwzs.entity;

import com.fwzs.master.common.persistence.DataEntity;

/**
 * @author Tyler Yin
 * @create 2017-11-24 11:12
 * @description 出库和产品关系映射实体
 **/
public class DealerBound2ProductMapping extends DataEntity<DealerBound2ProductMapping> {
    private static final long serialVersionUID = 1L;

    private String dealerBoundId;
    private String prodId;
    private String warehouseId;
    private int planNumber;
    private int realNumber;
    private String boxCode;

    public String getDealerBoundId() {
        return dealerBoundId;
    }

    public void setDealerBoundId(String dealerBoundId) {
        this.dealerBoundId = dealerBoundId;
    }

    public String getProdId() {
        return prodId;
    }

    public void setProdId(String prodId) {
        this.prodId = prodId;
    }

    public String getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(String warehouseId) {
        this.warehouseId = warehouseId;
    }

    public int getPlanNumber() {
        return planNumber;
    }

    public void setPlanNumber(int planNumber) {
        this.planNumber = planNumber;
    }

    public int getRealNumber() {
        return realNumber;
    }

    public void setRealNumber(int realNumber) {
        this.realNumber = realNumber;
    }

    public String getBoxCode() {
        return boxCode;
    }

    public void setBoxCode(String boxCode) {
        this.boxCode = boxCode;
    }
}
