package com.fwzs.master.modules.fwzs.entity;

import com.fwzs.master.common.persistence.DataEntity;

/**
 * @author Tyler Yin
 * @create 2017-11-24 11:11
 * @description 产品和箱码关系映射实体
 **/
public class InProduct2BoxCodeMapping extends DataEntity<InProduct2BoxCodeMapping> {
    private static final long serialVersionUID = 1L;

    private String boxCode;
    private String boxCodeType;
    private String inBound2ProductId;

    public String getInBound2ProductId() {
        return inBound2ProductId;
    }

    public void setInBound2ProductId(String inBound2ProductId) {
        this.inBound2ProductId = inBound2ProductId;
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
}
