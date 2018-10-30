package com.fwzs.master.modules.fwzs.entity;

import com.fwzs.master.common.persistence.DataEntity;

/**
 * @author Tyler Yin
 * @create 2018-01-23 15:06
 * @description 生产计划质检没有通过实体
 **/
public class ScPlanQc extends DataEntity<ScPlanQc> implements Comparable<ScPlanQc>{
    private String id;
    private String planId;
    private String reason;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Override
    public int compareTo(ScPlanQc o) {
        if (null != o && null != this) {
            return o.getCreateDate().compareTo(this.createDate);
        } else if (null == this) {
            return 1;
        } else {
            return -1;
        }
    }
}
