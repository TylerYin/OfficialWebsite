/**
 * 
 */
package com.fwzs.master.modules.fwzs.entity;

import org.hibernate.validator.constraints.Length;

import com.fwzs.master.common.persistence.DataEntity;

/**
 * 追溯展示设置Entity
 * @author ly
 * @version 2017-09-30
 */
public class FwmRetrospectSet extends DataEntity<FwmRetrospectSet> {
	
	private static final long serialVersionUID = 1L;
	private String prodId;		// 产品id
	private String property;		// 展示属性
	
	public FwmRetrospectSet() {
		super();
	}

	public FwmRetrospectSet(String id){
		super(id);
	}

	@Length(min=0, max=64, message="产品id长度必须介于 0 和 64 之间")
	public String getProdId() {
		return prodId;
	}

	public void setProdId(String prodId) {
		this.prodId = prodId;
	}
	
	@Length(min=0, max=200, message="展示属性长度必须介于 0 和 200 之间")
	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}
	
}