/**
 * 
 */
package com.fwzs.master.modules.fwzs.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fwzs.master.common.persistence.DataEntity;
import com.fwzs.master.modules.sys.entity.User;
import org.hibernate.validator.constraints.Length;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 任务计划Entity
 * @author ly
 * @version 2017-09-30
 */
public class ScPlan extends DataEntity<ScPlan> {
	
	private static final long serialVersionUID = 1L;
	private String planNo;		                                    // 计划号
	private String batchNo;		                                    // 批号
	private BsProduct bsProduct;
	private List<BsProduct> bsProductList = new ArrayList<>();		// 产品id
	private Date madeDate;		                                    // 生产日期
	private ScLines scLines;		                                // 流水线id
	private String remark;		                                    // 备注
	private String status;		                                    // 1草稿2确认3审核(未生产)4生产中5完工6已审核
	private String operateBy;		                                // 操作员
	private User qcBy;		                                        // 质检员
	private int qcCount;		                                    // 质检次数
	private String qcNotPassReason;		                            // 质检没有通过原因
	private List<String> planList = new ArrayList<>();
	private List<String> statusList = new ArrayList<>();
	private List<ScPlanQc> qcList = new ArrayList<>();

	public BsProduct getBsProduct() {
		return bsProduct;
	}

	public void setBsProduct(BsProduct bsProduct) {
		this.bsProduct = bsProduct;
	}

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public List<BsProduct> getBsProductList() {
		return bsProductList;
	}

	public void setBsProductList(List<BsProduct> bsProductList) {
		this.bsProductList = bsProductList;
	}

	public ScLines getScLines() {
		return scLines;
	}

	public void setScLines(ScLines scLines) {
		this.scLines = scLines;
	}

	public ScPlan() {
		super();
	}

	public ScPlan(String id){
		super(id);
	}

	@Length(min=0, max=50, message="计划号长度必须介于 0 和 50 之间")
	public String getPlanNo() {
		return planNo;
	}

	public void setPlanNo(String planNo) {
		this.planNo = planNo;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getMadeDate() {
		return madeDate;
	}

	public void setMadeDate(Date madeDate) {
		this.madeDate = madeDate;
	}

	@Length(min=0, max=500, message="备注长度必须介于 0 和 500 之间")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@Length(min=0, max=1, message="1草稿2确认3审核(未生产)4生产中5完工长度必须介于 0 和 1 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@Length(min=0, max=100, message="操作员长度必须介于 0 和 100 之间")
	public String getOperateBy() {
		return operateBy;
	}

	public void setOperateBy(String operateBy) {
		this.operateBy = operateBy;
	}

    public User getQcBy() {
        return qcBy;
    }

    public void setQcBy(User qcBy) {
        this.qcBy = qcBy;
    }

    public int getQcCount() {
        return qcCount;
    }

    public void setQcCount(int qcCount) {
        this.qcCount = qcCount;
    }

    public String getQcNotPassReason() {
        return qcNotPassReason;
    }

    public void setQcNotPassReason(String qcNotPassReason) {
        this.qcNotPassReason = qcNotPassReason;
    }

    public List<String> getPlanList() {
		return planList;
	}

	public void setPlanList(List<String> planList) {
		this.planList = planList;
	}

	public List<String> getStatusList() {
		return statusList;
	}

	public void setStatusList(List<String> statusList) {
		this.statusList = statusList;
	}

    public List<ScPlanQc> getQcList() {
        return qcList;
    }

    public void setQcList(List<ScPlanQc> qcList) {
        this.qcList = qcList;
    }
}