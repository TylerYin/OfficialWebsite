package com.fwzs.master.modules.fwzs.entity;

import com.fwzs.master.common.persistence.DataEntity;
import com.fwzs.master.modules.sys.entity.Office;

import java.util.Date;

/**
 * @author Tyler Yin
 * @create 2018-03-06 16:12
 * @description 防伪码统计
 **/
public class FwmFileStatistics extends DataEntity<FwmFileStatistics> {

    private Office company;       //公司名称
    private String productName;   //产品名称
    private Long codeCount;       //生码数量

    private BsProduct bsProduct;  // 产品
    private Date beginDate;		  // 开始日期
    private Date endDate;		  // 结束日期

    public Office getCompany() {
        return company;
    }

    public void setCompany(Office company) {
        this.company = company;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Long getCodeCount() {
        return codeCount;
    }

    public void setCodeCount(Long codeCount) {
        this.codeCount = codeCount;
    }

    public BsProduct getBsProduct() {
        return bsProduct;
    }

    public void setBsProduct(BsProduct bsProduct) {
        this.bsProduct = bsProduct;
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
