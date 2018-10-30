package com.fwzs.master.common.utils;

import java.util.List;

/**
 * 返回json
 * 
 * @author Administrator
 * 
 */
public class BaseBeanListJson {
	/**
	 * 请求返回码
	 */
	protected int code = 0;
	/**
	 * 请求返回描述
	 */
	protected String msg;
	/**
	 * 结果集
	 */
	protected List resultList;
	public BaseBeanListJson(int code ,String msg,List result) {
		// TODO Auto-generated constructor stub
		this.code=code;
		this.msg=msg;
		this.resultList=result;
	}
	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public List getResultList() {
		return resultList;
	}

	public void setResultList(List resultList) {
		this.resultList = resultList;
	}

}
