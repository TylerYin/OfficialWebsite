/**
 * 
 */
package com.fwzs.master.modules.fwzs.entity;

import org.hibernate.validator.constraints.Length;

import com.fwzs.master.common.persistence.DataEntity;

/**
 * 防伪码Entity
 * @author yjd
 * @version 2017-10-08
 */
public class FwmQrcode extends DataEntity<FwmQrcode> {
	
	private static final long serialVersionUID = 1L;
	private String qrcode;		// 防伪码
	private FwmBoxCode fwmBoxCode;		// 箱码
	private ScPlan scPlan;		// 任务号
	private String status;		// 状态
	private Long selectNum;		// 查询数
	private BsProduct bsProduct;		// 产品id
	private FwmFile fwmFile;		// 防伪码文件id
	
	public FwmQrcode() {
		super();
	}

	public FwmQrcode(String id){
		super(id);
	}

	@Length(min=0, max=50, message="防伪码长度必须介于 0 和 50 之间")
	public String getQrcode() {
		return qrcode;
	}

	public void setQrcode(String qrcode) {
		this.qrcode = qrcode;
	}
	
	
	
	public FwmBoxCode getFwmBoxCode() {
		return fwmBoxCode;
	}

	public void setFwmBoxCode(FwmBoxCode fwmBoxCode) {
		this.fwmBoxCode = fwmBoxCode;
	}

	public ScPlan getScPlan() {
		return scPlan;
	}

	public void setScPlan(ScPlan scPlan) {
		this.scPlan = scPlan;
	}

	@Length(min=0, max=1, message="状态长度必须介于 0 和 1 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public Long getSelectNum() {
		return selectNum;
	}

	public void setSelectNum(Long selectNum) {
		this.selectNum = selectNum;
	}

	public BsProduct getBsProduct() {
		return bsProduct;
	}

	public void setBsProduct(BsProduct bsProduct) {
		this.bsProduct = bsProduct;
	}

	public FwmFile getFwmFile() {
		return fwmFile;
	}

	public void setFwmFile(FwmFile fwmFile) {
		this.fwmFile = fwmFile;
	}
	
	
	
}