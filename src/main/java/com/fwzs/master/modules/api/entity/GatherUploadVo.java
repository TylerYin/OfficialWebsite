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
 * 
 * @author ly
 * @version 2017-09-30
 */
public class GatherUploadVo implements Serializable {

	private static final long serialVersionUID = 1L;

	private String planId;
	private String realNumber;
	private String status;
	private String qcBy;
	private String operateBy;
	private String key;

	public String getPlanId() {
		return planId;
	}

	public void setPlanId(String planId) {
		this.planId = planId;
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

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

}