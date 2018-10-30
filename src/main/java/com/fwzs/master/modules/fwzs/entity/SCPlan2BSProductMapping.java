package com.fwzs.master.modules.fwzs.entity;

import com.fwzs.master.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

/**
 * 防伪查询记录表VO
 * @author Tyler
 * @version 2017-10-31
 */
public class SCPlan2BSProductMapping extends DataEntity<SCPlan2BSProductMapping> {
    private static final long serialVersionUID = 1L;

    private String prodId;		    // 产品id
    private String planId;		    // 生产线Id
    private String batchNo;		    // 生产批号
    private Date indate;		    // 有效期
    private String checkPlanUrl;    // 检验报告单
    private String planNumber;		// 计划生产量
    private String realNumber;		// 实际生产量
    private Warehouse warehouse;    // 仓库
    private String fwmFileId;		// 防伪码文件
    private String remark;		    // 备注

    public String getProdId() {
        return prodId;
    }

    public void setProdId(String prodId) {
        this.prodId = prodId;
    }

    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    @Length(min=0, max=150, message="检验报告单长度必须介于 0 和 150 之间")
    public String getCheckPlanUrl() {
        return checkPlanUrl;
    }

    public void setCheckPlanUrl(String checkPlanUrl) {
        this.checkPlanUrl = checkPlanUrl;
    }

    public Date getIndate() {
        return indate;
    }

    public void setIndate(Date indate) {
        this.indate = indate;
    }

    public String getPlanNumber() {
        return planNumber;
    }

    public void setPlanNumber(String planNumber) {
        this.planNumber = planNumber;
    }

    public String getRealNumber() {
        return realNumber;
    }

    public void setRealNumber(String realNumber) {
        this.realNumber = realNumber;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getFwmFileId() {
        return fwmFileId;
    }

    public void setFwmFileId(String fwmFileId) {
        this.fwmFileId = fwmFileId;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }
}
