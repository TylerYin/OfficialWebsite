/**
 * 
 */
package com.fwzs.master.modules.fwzs.entity;

import org.hibernate.validator.constraints.Length;

import com.fwzs.master.common.persistence.DataEntity;

/**
 * 箱码Entity
 * @author ly
 * @version 2017-10-09
 */
public class FwmBoxCode extends DataEntity<FwmBoxCode> {
	
	private static final long serialVersionUID = 1L;
	private String boxCode;		// box_code
	private String planId;		// plan_id
	private String status;		// status
	private String bigboxCode;		// bigbox_code
	
	public FwmBoxCode() {
		super();
	}

	public FwmBoxCode(String id){
		super(id);
	}

	@Length(min=0, max=50, message="box_code长度必须介于 0 和 50 之间")
	public String getBoxCode() {
		return boxCode;
	}

	public void setBoxCode(String boxCode) {
		this.boxCode = boxCode;
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
	
	@Length(min=0, max=50, message="bigbox_code长度必须介于 0 和 50 之间")
	public String getBigboxCode() {
		return bigboxCode;
	}

	public void setBigboxCode(String bigboxCode) {
		this.bigboxCode = bigboxCode;
	}
	
}