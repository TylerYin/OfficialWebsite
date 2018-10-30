package com.fwzs.master.modules.fwzs.entity;

import com.fwzs.master.common.persistence.DataEntity;

import java.util.Date;

/**
 * @author Tyler Yin
 * @create 2018-01-19 9:08
 * @description 防伪码追溯Entity
 **/
public class FwmTrace extends DataEntity<FwmTrace> {

    private static final long serialVersionUID = 1L;

    private String qrCode;                        //防伪码
    private String prodName;                      //产品名称
    private String prodSpec;                      //产品规格
    private String batchNo;                       //产品批号
    private String companyName;                   //公司名称
    private String warehouseName;                 //仓库名称
    private String warehouseAddress;              //仓库地址
    private String salesArea;                     //仓库所属区域
    private String boxCode;                       //箱码
    private String bigBoxCode;                    //垛码
    private String planNo;                        //生产计划号
    private Date inboundDate;                     //企业入库日期
    private String outboundNo;                    //企业出库计划单号
    private Date outboundPlanDate;                //企业出库计划日期
    private Date outboundDate;                    //企业发货日期
    private Date receiveDate;                     //经销商收货日期
    private String inDealerNo;                    //收货经销商编号
    private String inDealerName;                  //收货经销商名称
    private String inDealerArea;                  //收货经销商所属区域
    private String inDealerAddress;               //收货经销商地址
    private String inDealerPhone;                 //收货经销商联系电话
    private String outDealerNo;                   //发货经销商编号
    private String outDealerName;                 //发货经销商名称
    private String outDealerArea;                 //发货经销商所属区域
    private String outDealerAddress;              //发货经销商地址
    private String outDealerPhone;                //发货经销商联系电话

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

    public String getProdSpec() {
        return prodSpec;
    }

    public void setProdSpec(String prodSpec) {
        this.prodSpec = prodSpec;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public String getWarehouseAddress() {
        return warehouseAddress;
    }

    public void setWarehouseAddress(String warehouseAddress) {
        this.warehouseAddress = warehouseAddress;
    }

    public String getSalesArea() {
        return salesArea;
    }

    public void setSalesArea(String salesArea) {
        this.salesArea = salesArea;
    }

    public String getBoxCode() {
        return boxCode;
    }

    public void setBoxCode(String boxCode) {
        this.boxCode = boxCode;
    }

    public String getBigBoxCode() {
        return bigBoxCode;
    }

    public void setBigBoxCode(String bigBoxCode) {
        this.bigBoxCode = bigBoxCode;
    }

    public String getPlanNo() {
        return planNo;
    }

    public void setPlanNo(String planNo) {
        this.planNo = planNo;
    }

    public Date getInboundDate() {
        return inboundDate;
    }

    public void setInboundDate(Date inboundDate) {
        this.inboundDate = inboundDate;
    }

    public String getOutboundNo() {
        return outboundNo;
    }

    public void setOutboundNo(String outboundNo) {
        this.outboundNo = outboundNo;
    }

    public Date getOutboundPlanDate() {
        return outboundPlanDate;
    }

    public void setOutboundPlanDate(Date outboundPlanDate) {
        this.outboundPlanDate = outboundPlanDate;
    }

    public Date getOutboundDate() {
        return outboundDate;
    }

    public void setOutboundDate(Date outboundDate) {
        this.outboundDate = outboundDate;
    }

    public Date getReceiveDate() {
        return receiveDate;
    }

    public void setReceiveDate(Date receiveDate) {
        this.receiveDate = receiveDate;
    }

    public String getInDealerNo() {
        return inDealerNo;
    }

    public void setInDealerNo(String inDealerNo) {
        this.inDealerNo = inDealerNo;
    }

    public String getInDealerName() {
        return inDealerName;
    }

    public void setInDealerName(String inDealerName) {
        this.inDealerName = inDealerName;
    }

    public String getInDealerAddress() {
        return inDealerAddress;
    }

    public void setInDealerAddress(String inDealerAddress) {
        this.inDealerAddress = inDealerAddress;
    }

    public String getInDealerPhone() {
        return inDealerPhone;
    }

    public void setInDealerPhone(String inDealerPhone) {
        this.inDealerPhone = inDealerPhone;
    }

    public String getOutDealerNo() {
        return outDealerNo;
    }

    public void setOutDealerNo(String outDealerNo) {
        this.outDealerNo = outDealerNo;
    }

    public String getOutDealerName() {
        return outDealerName;
    }

    public void setOutDealerName(String outDealerName) {
        this.outDealerName = outDealerName;
    }

    public String getOutDealerAddress() {
        return outDealerAddress;
    }

    public void setOutDealerAddress(String outDealerAddress) {
        this.outDealerAddress = outDealerAddress;
    }

    public String getOutDealerPhone() {
        return outDealerPhone;
    }

    public void setOutDealerPhone(String outDealerPhone) {
        this.outDealerPhone = outDealerPhone;
    }

    public String getInDealerArea() {
        return inDealerArea;
    }

    public void setInDealerArea(String inDealerArea) {
        this.inDealerArea = inDealerArea;
    }

    public String getOutDealerArea() {
        return outDealerArea;
    }

    public void setOutDealerArea(String outDealerArea) {
        this.outDealerArea = outDealerArea;
    }
}