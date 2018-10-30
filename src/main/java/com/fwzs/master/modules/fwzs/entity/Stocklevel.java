package com.fwzs.master.modules.fwzs.entity;

import com.fwzs.master.common.persistence.DataEntity;

/**
 * @author Tyler Yin
 * @create 2017-11-16 16:19
 * @description 进出库实体
 **/
public class Stocklevel extends DataEntity<Stocklevel> {
    private static final long serialVersionUID = 1L;

    private String shippingNumber;   //进出库单号
    private long number;             //进出库数量
    private BsProduct bsProduct;     //进出库产品
    private Warehouse warehouse;     //进出库仓库
    private String operationType;    //操作类别(0代表入库；1代表出库)
    private Dealer dealer;           //经销商
    private String boxNumber;        //箱号
    private String leader;           //负责人

    public String getShippingNumber() {
        return shippingNumber;
    }

    public void setShippingNumber(String shippingNumber) {
        this.shippingNumber = shippingNumber;
    }

    public long getNumber() {
        return number;
    }

    public void setNumber(long number) {
        this.number = number;
    }

    public BsProduct getBsProduct() {
        return bsProduct;
    }

    public void setBsProduct(BsProduct bsProduct) {
        this.bsProduct = bsProduct;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    public Dealer getDealer() {
        return dealer;
    }

    public void setDealer(Dealer dealer) {
        this.dealer = dealer;
    }

    public String getBoxNumber() {
        return boxNumber;
    }

    public void setBoxNumber(String boxNumber) {
        this.boxNumber = boxNumber;
    }

    public String getLeader() {
        return leader;
    }

    public void setLeader(String leader) {
        this.leader = leader;
    }
}
