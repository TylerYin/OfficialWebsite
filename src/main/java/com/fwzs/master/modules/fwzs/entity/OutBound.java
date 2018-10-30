package com.fwzs.master.modules.fwzs.entity;

import com.fwzs.master.common.persistence.DataEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Tyler Yin
 * @create 2017-11-22 10:45
 * @description 产品出库Entity
 **/
public class OutBound extends DataEntity<OutBound>{
    private static final long serialVersionUID = 1L;

    private String outBoundNo;                                 //出库单号
    private Dealer dealer;                                     //经销商
    private boolean indirectSelling;                           //是否直销
    private Dealer firstDealer;                                //一级经销商
    private Dealer secondDealer;                               //二级经销商
    private List<BsProduct> bsProductList = new ArrayList<>(); //产品集合
    private Date beginDate;		                               // 开始日期
    private Date endDate;		                               // 结束日期
    private BsProduct bsProduct;                               //查询使用的产品对象
    private String status;                                     //发货状态
    private String companyId;                                  //公司ID
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

    public Dealer getDealer() {
        return dealer;
    }

    public void setDealer(Dealer dealer) {
        this.dealer = dealer;
    }

    public String getOutBoundNo() {
        return outBoundNo;
    }

    public void setOutBoundNo(String outBoundNo) {
        this.outBoundNo = outBoundNo;
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

    public boolean isIndirectSelling() {
        return indirectSelling;
    }

    public void setIndirectSelling(boolean indirectSelling) {
        this.indirectSelling = indirectSelling;
    }

    public Dealer getFirstDealer() {
        return firstDealer;
    }

    public void setFirstDealer(Dealer firstDealer) {
        this.firstDealer = firstDealer;
    }

    public Dealer getSecondDealer() {
        return secondDealer;
    }

    public void setSecondDealer(Dealer secondDealer) {
        this.secondDealer = secondDealer;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
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
