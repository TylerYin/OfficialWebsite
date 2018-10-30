package com.fwzs.master.modules.fwzs.entity;

import com.fwzs.master.common.persistence.DataEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Tyler Yin
 * @create 2017-01-10 15:22
 * @description 经销商入库Entity
 **/
public class DealerInBound extends DataEntity<DealerInBound>{
    private static final long serialVersionUID = 1L;

    private String boundNo;                                    //出库单号
    private String status;                                     //是否已出库，0表示未出库，1表示已出库
    private String boundId;                                    //企业出库计划主键
    private String dealerBoundId;                              //经销商出库计划主键
    private String bound2ProductId;                            //出库计划和产品映射关系表主键
    private List<BsProduct> bsProductList = new ArrayList<>(); //产品集合
    private PdaUser pdaUser;                                   //出库员
    private Dealer dealer;                                     //经销商
    private String shipper;                                    //发货方
    private String shipNo;                                     //物流单号
    private String shipName;                                   //物流名称
    private Date createDate;                                   //下单日期
    private Date boundDate;                                    //发货日期
    private Date receiveDate;                                  //收货日期
    private Date beginDate;		                               //开始日期
    private Date endDate;		                               //结束日期
    private BsProduct bsProduct;                               //查询使用的产品对象

    public String getBoundNo() {
        return boundNo;
    }

    public void setBoundNo(String boundNo) {
        this.boundNo = boundNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBoundId() {
        return boundId;
    }

    public void setBoundId(String boundId) {
        this.boundId = boundId;
    }

    public String getDealerBoundId() {
        return dealerBoundId;
    }

    public void setDealerBoundId(String dealerBoundId) {
        this.dealerBoundId = dealerBoundId;
    }

    public String getBound2ProductId() {
        return bound2ProductId;
    }

    public void setBound2ProductId(String bound2ProductId) {
        this.bound2ProductId = bound2ProductId;
    }

    public List<BsProduct> getBsProductList() {
        return bsProductList;
    }

    public void setBsProductList(List<BsProduct> bsProductList) {
        this.bsProductList = bsProductList;
    }

    public PdaUser getPdaUser() {
        return pdaUser;
    }

    public void setPdaUser(PdaUser pdaUser) {
        this.pdaUser = pdaUser;
    }

    public BsProduct getBsProduct() {
        return bsProduct;
    }

    public void setBsProduct(BsProduct bsProduct) {
        this.bsProduct = bsProduct;
    }

    public Dealer getDealer() {
        return dealer;
    }

    public void setDealer(Dealer dealer) {
        this.dealer = dealer;
    }

    public String getShipper() {
        return shipper;
    }

    public void setShipper(String shipper) {
        this.shipper = shipper;
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

    public Date getBoundDate() {
        return boundDate;
    }

    public void setBoundDate(Date boundDate) {
        this.boundDate = boundDate;
    }

    @Override
    public Date getCreateDate() {
        return createDate;
    }

    @Override
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getReceiveDate() {
        return receiveDate;
    }

    public void setReceiveDate(Date receiveDate) {
        this.receiveDate = receiveDate;
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
}
