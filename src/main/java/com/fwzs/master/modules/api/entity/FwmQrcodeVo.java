/**
 * 
 */
package com.fwzs.master.modules.api.entity;

import org.hibernate.validator.constraints.Length;

import com.fwzs.master.common.persistence.DataEntity;

/**
 * 防伪码Entity
 * @author yjd
 * @version 2017-10-08
 */
public class FwmQrcodeVo extends DataEntity<FwmQrcodeVo> {
	
	private static final long serialVersionUID = 1L;
	private String qrcode;		// 防伪码
	private String fwmBoxCode;		// 箱码
	private String scPlan;		// 任务号
	public String getQrcode() {
		return qrcode;
	}
	public void setQrcode(String qrcode) {
		this.qrcode = qrcode;
	}
	public String getFwmBoxCode() {
		return fwmBoxCode;
	}
	public void setFwmBoxCode(String fwmBoxCode) {
		this.fwmBoxCode = fwmBoxCode;
	}
	public String getScPlan() {
		return scPlan;
	}
	public void setScPlan(String scPlan) {
		this.scPlan = scPlan;
	}

	

	
	
}