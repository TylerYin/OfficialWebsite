package com.fwzs.master.modules.sys.entity;

import java.io.Serializable;

public class KeyValue implements Serializable{
	private String columnName;
	private String columnComment;
	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	public String getColumnComment() {
		return columnComment;
	}
	public void setColumnComment(String columnComment) {
		this.columnComment = columnComment;
	}

	

}
