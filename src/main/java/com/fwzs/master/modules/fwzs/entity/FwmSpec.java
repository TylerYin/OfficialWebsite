/**
 * 
 */
package com.fwzs.master.modules.fwzs.entity;

import org.hibernate.validator.constraints.Length;

import com.fwzs.master.common.persistence.DataEntity;

/**
 * 产品规格Entity
 * @author ly
 * @version 2017-09-30
 */
public class FwmSpec extends DataEntity<FwmSpec> {
	
	private static final long serialVersionUID = 1L;
	private String specCode;		// 规格码
	private String specDesc;		// 规格说明
	private String status;		// status
	
	public FwmSpec() {
		super();
	}

	public FwmSpec(String id){
		super(id);
	}

	@Length(min=1, max=64, message="规格码长度必须介于 1 和 64 之间")
	public String getSpecCode() {
		return specCode;
	}

	public void setSpecCode(String specCode) {
		this.specCode = specCode;
	}
	
	@Length(min=1, max=100, message="规格说明长度必须介于 1 和 100 之间")
	public String getSpecDesc() {
		return specDesc;
	}

	public void setSpecDesc(String specDesc) {
		this.specDesc = specDesc;
	}
	
	@Length(min=0, max=1, message="status长度必须介于 0 和 1 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}