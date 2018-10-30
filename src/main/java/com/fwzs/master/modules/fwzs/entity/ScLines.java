/**
 * 
 */
package com.fwzs.master.modules.fwzs.entity;

import org.hibernate.validator.constraints.Length;

import com.fwzs.master.common.persistence.DataEntity;

/**
 * 生产线Entity
 * @author ly
 * @version 2017-09-30
 */
public class ScLines extends DataEntity<ScLines> {
	
	private static final long serialVersionUID = 1L;
	private String lineNo;		// 生产线号
	private String lineName;		// 生产线名称
	
	public ScLines() {
		super();
	}

	public ScLines(String id){
		super(id);
	}

	@Length(min=0, max=20, message="生产线号长度必须介于 0 和 20 之间")
	public String getLineNo() {
		return lineNo;
	}

	public void setLineNo(String lineNo) {
		this.lineNo = lineNo;
	}
	
	@Length(min=0, max=100, message="生产线名称长度必须介于 0 和 100 之间")
	public String getLineName() {
		return lineName;
	}

	public void setLineName(String lineName) {
		this.lineName = lineName;
	}
	
}