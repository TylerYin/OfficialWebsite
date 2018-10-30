package com.fwzs.master.common.utils;

import java.util.List;

/**
 * 返回json
 * 
 * @author Administrator
 * 
 */
public class BaseBeanJson {

	/**
	 * 请求返回码
	 */
	protected int code = 0;
	/**
	 * 请求返回描述
	 */
	protected String msg;

	public BaseBeanJson(int code, String msg) {
		// TODO Auto-generated constructor stub
		this.code = code;
		this.msg = msg;
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

}
