package com.fwzs.master.modules.fwzs.entity;

import com.fwzs.master.common.persistence.DataEntity;

import java.util.*;

/**
 * @author Tyler Yin
 * @create 2017-11-29 14:44
 * @description 入库和产品关系映射实体
 **/
public class InBound extends DataEntity<InBound> {
    private static final long serialVersionUID = 1L;

    private ScPlan scPlan;
    private BsProduct bsProduct;
    private Warehouse warehouse;
    private int inBoundNumber;
    private Date inBoundDate;
    private String operator;                                   // 操作人
    private Date beginDate;		                               // 开始日期
    private Date endDate;		                               // 结束日期

    private int mergeScPlanRowCount;

    public ScPlan getScPlan() {
        return scPlan;
    }

    public void setScPlan(ScPlan scPlan) {
        this.scPlan = scPlan;
    }

    public BsProduct getBsProduct() {
        return bsProduct;
    }

    public void setBsProduct(BsProduct bsProduct) {
        this.bsProduct = bsProduct;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    public int getInBoundNumber() {
        return inBoundNumber;
    }

    public void setInBoundNumber(int inBoundNumber) {
        this.inBoundNumber = inBoundNumber;
    }

    public Date getInBoundDate() {
        return inBoundDate;
    }

    public void setInBoundDate(Date inBoundDate) {
        this.inBoundDate = inBoundDate;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
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

    public int getMergeScPlanRowCount() {
        return mergeScPlanRowCount;
    }

    public void setMergeScPlanRowCount(int mergeScPlanRowCount) {
        this.mergeScPlanRowCount = mergeScPlanRowCount;
    }
}
