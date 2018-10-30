package com.fwzs.master.modules.fwzs.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Tyler Yin
 * @create 2018-01-30 9:29
 * @description PDA出库实体类
 **/
public class PdaBound implements Serializable {

    private static final long serialVersionUID = 1L;

    private int boundNumber;                                         //出库单品数量
    private String status;                                           //状态
    private List<String> qrCodes = new ArrayList<>();                //单品码集合
    private List<String> invalidCodes = new ArrayList<>();           //无效的码集合
    private List<String> duplicateCodes = new ArrayList<>();       //重复出库的码集合

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getBoundNumber() {
        return boundNumber;
    }

    public void setBoundNumber(int boundNumber) {
        this.boundNumber = boundNumber;
    }

    public List<String> getQrCodes() {
        return qrCodes;
    }

    public void setQrCodes(List<String> qrCodes) {
        this.qrCodes = qrCodes;
    }

    public List<String> getInvalidCodes() {
        return invalidCodes;
    }

    public void setInvalidCodes(List<String> invalidCodes) {
        this.invalidCodes = invalidCodes;
    }

    public List<String> getDuplicateCodes() {
        return duplicateCodes;
    }

    public void setDuplicateCodes(List<String> duplicateCodes) {
        this.duplicateCodes = duplicateCodes;
    }
}
