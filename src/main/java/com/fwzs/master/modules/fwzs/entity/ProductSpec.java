package com.fwzs.master.modules.fwzs.entity;

/**
 * @author Tyler Yin
 * @create 2017-12-14 15:29
 * @description 产品规格
 **/
public class ProductSpec {
    private String prodNo;
    private FwmSpec prodSpec;
    private String packRate;
    private String prodUnit;
    private String prodUnitValue;

    public String getProdNo() {
        return prodNo;
    }

    public void setProdNo(String prodNo) {
        this.prodNo = prodNo;
    }

    public FwmSpec getProdSpec() {
        return prodSpec;
    }

    public void setProdSpec(FwmSpec prodSpec) {
        this.prodSpec = prodSpec;
    }

    public String getPackRate() {
        return packRate;
    }

    public void setPackRate(String packRate) {
        this.packRate = packRate;
    }

    public String getProdUnit() {
        return prodUnit;
    }

    public void setProdUnit(String prodUnit) {
        this.prodUnit = prodUnit;
    }

    public String getProdUnitValue() {
        return prodUnitValue;
    }

    public void setProdUnitValue(String prodUnitValue) {
        this.prodUnitValue = prodUnitValue;
    }
}
