package com.fwzs.master.modules.fwzs.entity;

import com.fwzs.master.common.persistence.DataEntity;

/**
 * 防伪码查询Entity
 * @author Tyler Yin
 * @version 2017-11-07
 */
public class FwmQuery extends DataEntity<FwmQuery> {
    private String qrCode;                      //防伪码
    private String status;                      //状态
    private String selectNum;                   //查询次数
    private String prodNo;                      //产品编号
    private String pesticideName;               //农药名称
    private String prodName;                    //产品名称
    private String specDesc;                    //产品规格
    private String packRate;                    //包装比例
    private String regCrop;                     //登记公司
    private String corpName;                    //生产企业
    private String fileName;                    //生码文件
    private String qcStatus;                    //质检情况
    private String batchNo;                     //生产批号
    private String qcBy;                        //质检人员
    private String prodDesc;                    //产品描述
    private Integer antiRegionalThreshold;      //企业窜货阈值

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSelectNum() {
        return selectNum;
    }

    public void setSelectNum(String selectNum) {
        this.selectNum = selectNum;
    }

    public String getProdNo() {
        return prodNo;
    }

    public void setProdNo(String prodNo) {
        this.prodNo = prodNo;
    }

    public String getPesticideName() {
        return pesticideName;
    }

    public void setPesticideName(String pesticideName) {
        this.pesticideName = pesticideName;
    }

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

    public String getSpecDesc() {
        return specDesc;
    }

    public void setSpecDesc(String specDesc) {
        this.specDesc = specDesc;
    }

    public String getPackRate() {
        return packRate;
    }

    public void setPackRate(String packRate) {
        this.packRate = packRate;
    }

    public String getRegCrop() {
        return regCrop;
    }

    public void setRegCrop(String regCrop) {
        this.regCrop = regCrop;
    }

    public String getCorpName() {
        return corpName;
    }

    public void setCorpName(String corpName) {
        this.corpName = corpName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getQcStatus() {
        return qcStatus;
    }

    public void setQcStatus(String qcStatus) {
        this.qcStatus = qcStatus;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getQcBy() {
        return qcBy;
    }

    public void setQcBy(String qcBy) {
        this.qcBy = qcBy;
    }

    public String getProdDesc() {
        return prodDesc;
    }

    public void setProdDesc(String prodDesc) {
        this.prodDesc = prodDesc;
    }

    public Integer getAntiRegionalThreshold() {
        return antiRegionalThreshold;
    }

    public void setAntiRegionalThreshold(Integer antiRegionalThreshold) {
        this.antiRegionalThreshold = antiRegionalThreshold;
    }
}
