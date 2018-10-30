package com.fwzs.master.modules.fwzs.entity;

import com.fwzs.master.common.persistence.DataEntity;
/**
 * 防伪码关联bean
 * @author ly
 *
 */
public class FwmRelate extends DataEntity<FwmRelate> {
	private static final long serialVersionUID = 1L;
	private FwmBoxCode fwmBoxCode; // 箱码
	private FwmQrcode fwmQrcode;
	private FwmBigboxCode bigboxCode;
	private ScPlan scPlan;
	
	public FwmBoxCode getFwmBoxCode() {
		return fwmBoxCode;
	}
	public void setFwmBoxCode(FwmBoxCode fwmBoxCode) {
		this.fwmBoxCode = fwmBoxCode;
	}
	public FwmQrcode getFwmQrcode() {
		return fwmQrcode;
	}
	public void setFwmQrcode(FwmQrcode fwmQrcode) {
		this.fwmQrcode = fwmQrcode;
	}
	public FwmBigboxCode getBigboxCode() {
		return bigboxCode;
	}
	public void setBigboxCode(FwmBigboxCode bigboxCode) {
		this.bigboxCode = bigboxCode;
	}
	public ScPlan getScPlan() {
		return scPlan;
	}
	public void setScPlan(ScPlan scPlan) {
		this.scPlan = scPlan;
	}
	
}
