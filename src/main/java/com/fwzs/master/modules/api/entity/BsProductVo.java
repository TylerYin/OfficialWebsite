/**
 * 
 */
package com.fwzs.master.modules.api.entity;

import java.io.Serializable;

import org.hibernate.validator.constraints.Length;

import com.fwzs.master.common.persistence.DataEntity;
import com.fwzs.master.modules.sys.entity.Office;

/**
 * 防伪查询记录表Entity
 * @author ly
 * @version 2017-09-30
 */
public class BsProductVo extends  DataEntity<BsProductVo>{
	
	private static final long serialVersionUID = 1L;
	private String prodNo;		// 产品编号
	private String pesticideName;		// 农药名
	private String officeId;
	private String prodName;		// 产品名称
	private String prodSpec;		// 产品规格
	private String packRate;		// 包装比例
	private String prodUnit;		// 单位
	private String remark;		// 备注
	
	public String getOfficeId() {
		return officeId;
	}
	public void setOfficeId(String officeId) {
		this.officeId = officeId;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getProdNo() {
		return prodNo;
	}
	public void setProdNo(String prodNo) {
		this.prodNo = prodNo;
	}
	public String getPesticideName() {
		return pesticideName;
	}
	public void setPesticideName(String pesticideName) {
		this.pesticideName = pesticideName;
	}
	public String getProdName() {
		return prodName;
	}
	public void setProdName(String prodName) {
		this.prodName = prodName;
	}
	public String getProdSpec() {
		return prodSpec;
	}
	public void setProdSpec(String prodSpec) {
		this.prodSpec = prodSpec;
	}
	public String getPackRate() {
		return packRate;
	}
	public void setPackRate(String packRate) {
		this.packRate = packRate;
	}
	public String getProdUnit() {
		return prodUnit;
	}
	public void setProdUnit(String prodUnit) {
		this.prodUnit = prodUnit;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
	
	

	
}