package com.fwzs.master.modules.fwzs.entity;

import com.fwzs.master.common.persistence.DataEntity;

/**
 * @author Tyler Yin
 * @create 2017-11-24 18:06
 * @description 防伪码和箱码关系映射实体
 **/
public class Qrcode2BoxcodeMapping extends DataEntity<Qrcode2BoxcodeMapping> {

    private static final long serialVersionUID = 1L;

    private String boundId;
    private String planId;
    private String prodId;

    private String qrCode;
    private String boxCode;
    private String bigBoxCode;

    public String getProdId() {
        return prodId;
    }

    public void setProdId(String prodId) {
        this.prodId = prodId;
    }

    public String getBoundId() {
        return boundId;
    }

    public void setBoundId(String boundId) {
        this.boundId = boundId;
    }

    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
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

}