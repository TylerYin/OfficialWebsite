/**
 * 
 */
package com.fwzs.master.modules.api.entity;


import java.io.Serializable;
import java.util.Date;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fwzs.master.common.persistence.DataEntity;

/**
 * 任务计划Entity
 * @author ly
 * @version 2017-09-30
 */
public class ScPlanVo extends DataEntity<ScPlanVo> {
	
	private static final long serialVersionUID = 1L;
	
	private String planNo;		// 计划号
	private String  productId;		// 产品id
	private String prodNo  ;//产品no
	private String batchNo;		// 批号
	private String madeDate;		// 生产日期
	private String planNumber;		// 计划生产数量
	private String  scLinesId;		// 流水线id
	private String  scLinesName;		// 流水线Name
	private String lineNo;
	private String officeId;
	private String indate;		// 有效期
	
	private String realNumber;
	private String status;
	private String qcBy;
	private String operateBy;
	
	
	public String getProdNo() {
		return prodNo;
	}
	public void setProdNo(String prodNo) {
		this.prodNo = prodNo;
	}
	public String getRealNumber() {
		return realNumber;
	}
	public void setRealNumber(String realNumber) {
		this.realNumber = realNumber;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getQcBy() {
		return qcBy;
	}
	public void setQcBy(String qcBy) {
		this.qcBy = qcBy;
	}
	public String getOperateBy() {
		return operateBy;
	}
	public void setOperateBy(String operateBy) {
		this.operateBy = operateBy;
	}
	public String getLineNo() {
		return lineNo;
	}
	public void setLineNo(String lineNo) {
		this.lineNo = lineNo;
	}
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
	public String getPlanNo() {
		return planNo;
	}
	public void setPlanNo(String planNo) {
		this.planNo = planNo;
	}

	public String getBatchNo() {
		return batchNo;
	}
	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}
	
	public String getPlanNumber() {
		return planNumber;
	}
	public void setPlanNumber(String planNumber) {
		this.planNumber = planNumber;
	}
	
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getScLinesId() {
		return scLinesId;
	}
	public void setScLinesId(String scLinesId) {
		this.scLinesId = scLinesId;
	}
	public String getScLinesName() {
		return scLinesName;
	}
	public void setScLinesName(String scLinesName) {
		this.scLinesName = scLinesName;
	}
	public String getMadeDate() {
		return madeDate;
	}
	public void setMadeDate(String madeDate) {
		this.madeDate = madeDate;
	}
	public String getIndate() {
		return indate;
	}
	public void setIndate(String indate) {
		this.indate = indate;
	}
	
	
	
	
}