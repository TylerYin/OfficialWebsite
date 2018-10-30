package com.fwzs.master.modules.fwzs.entity;

import com.fwzs.master.common.persistence.DataEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Tyler Yin
 * @create 2017-12-04 10:22
 * @description 经销商产品出库Entity
 **/
public class DealerBound extends DataEntity<DealerBound>{
    private static final long serialVersionUID = 1L;

    private String boundNo;                                    //出库单号
    private Dealer outDealer;                                  //收货经销商
    private boolean indirectSelling;                           //是否直销
    private Dealer inDealer;                                   //出库经销商
    private List<BsProduct> bsProductList = new ArrayList<>(); //产品集合
    private Date beginDate;		                               // 开始日期
    private Date endDate;		                               // 结束日期

    private BsProduct bsProduct;                               //查询使用的产品对象
    private Dealer dealer;                                     //查询使用的经销商
    private String status;                                     //发货状态
    private PdaUser pdaUser;                                   //出库员
    private Date boundDate;                                    //发货时间
    private Date receiveDate;                                  //收货时间
    private String shipNo;                                     //物流单号
    private String shipName;                                   //物流名称

    public BsProduct getBsProduct() {
        return bsProduct;
    }

    public void setBsProduct(BsProduct bsProduct) {
        this.bsProduct = bsProduct;
    }

    public Dealer getOutDealer() {
        return outDealer;
    }

    public void setOutDealer(Dealer outDealer) {
        this.outDealer = outDealer;
    }

    public String getBoundNo() {
        return boundNo;
    }

    public void setBoundNo(String boundNo) {
        this.boundNo = boundNo;
    }

    public List<BsProduct> getBsProductList() {
        return bsProductList;
    }

    public void setBsProductList(List<BsProduct> bsProductList) {
        this.bsProductList = bsProductList;
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

    public Dealer getDealer() {
        return dealer;
    }

    public void setDealer(Dealer dealer) {
        this.dealer = dealer;
    }

    public boolean isIndirectSelling() {
        return indirectSelling;
    }

    public void setIndirectSelling(boolean indirectSelling) {
        this.indirectSelling = indirectSelling;
    }

    public Dealer getInDealer() {
        return inDealer;
    }

    public void setInDealer(Dealer inDealer) {
        this.inDealer = inDealer;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public PdaUser getPdaUser() {
        return pdaUser;
    }

    public void setPdaUser(PdaUser pdaUser) {
        this.pdaUser = pdaUser;
    }

    public Date getBoundDate() {
        return boundDate;
    }

    public void setBoundDate(Date boundDate) {
        this.boundDate = boundDate;
    }

    public Date getReceiveDate() {
        return receiveDate;
    }

    public void setReceiveDate(Date receiveDate) {
        this.receiveDate = receiveDate;
    }

    public String getShipNo() {
        return shipNo;
    }

    public void setShipNo(String shipNo) {
        this.shipNo = shipNo;
    }

    public String getShipName() {
        return shipName;
    }

    public void setShipName(String shipName) {
        this.shipName = shipName;
    }
}
