package com.fwzs.master.modules.fwzs.entity;

import com.fwzs.master.common.persistence.DataEntity;
import com.fwzs.master.modules.sys.entity.Office;

/**
 * @author Tyler Yin
 * @create 2017-11-16 16:20
 * @description 实时库存实体类
 **/
public class RealtimeStockLevel extends DataEntity<RealtimeStockLevel> {
    private static final long serialVersionUID = 1L;

    private Warehouse warehouse;     //仓库
    private BsProduct bsProduct;     //产品
    private String outBoundNo;       //出库单号
    private Office company;          //公司
    private Dealer dealer;           //经销商
    private long stockLevel;         //实时库存，相当于一级单品码数量
    private long boxNum;             //二级单品码数量,相当于箱码数量
    private long bigBoxNum;          //三级单品码数量,相当于垛码数量

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    public BsProduct getBsProduct() {
        return bsProduct;
    }

    public void setBsProduct(BsProduct bsProduct) {
        this.bsProduct = bsProduct;
    }

    public String getOutBoundNo() {
        return outBoundNo;
    }

    public void setOutBoundNo(String outBoundNo) {
        this.outBoundNo = outBoundNo;
    }

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

    public long getStockLevel() {
        return stockLevel;
    }

    public void setStockLevel(long stockLevel) {
        this.stockLevel = stockLevel;
    }

    public long getBoxNum() {
        return boxNum;
    }

    public void setBoxNum(long boxNum) {
        this.boxNum = boxNum;
    }

    public long getBigBoxNum() {
        return bigBoxNum;
    }

    public void setBigBoxNum(long bigBoxNum) {
        this.bigBoxNum = bigBoxNum;
    }
}
