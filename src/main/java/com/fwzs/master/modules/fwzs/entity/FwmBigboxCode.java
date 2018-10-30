/**
 * 
 */
package com.fwzs.master.modules.fwzs.entity;

import org.hibernate.validator.constraints.Length;

import com.fwzs.master.common.persistence.DataEntity;

/**
 * 剁码Entity
 * @author ly
 * @version 2017-10-09
 */
public class FwmBigboxCode extends DataEntity<FwmBigboxCode> {
	
	private static final long serialVersionUID = 1L;
	private String bigBoxCode;		// big_box_code
	private String planId;		// plan_id
	private String status;		// status
	
	public FwmBigboxCode() {
		super();
	}

	public FwmBigboxCode(String id){
		super(id);
	}

	@Length(min=0, max=50, message="big_box_code长度必须介于 0 和 50 之间")
	public String getBigBoxCode() {
		return bigBoxCode;
	}

	public void setBigBoxCode(String bigBoxCode) {
		this.bigBoxCode = bigBoxCode;
	}
	
	@Length(min=0, max=64, message="plan_id长度必须介于 0 和 64 之间")
	public String getPlanId() {
		return planId;
	}

	public void setPlanId(String planId) {
		this.planId = planId;
	}
	
	@Length(min=0, max=1, message="status长度必须介于 0 和 1 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}