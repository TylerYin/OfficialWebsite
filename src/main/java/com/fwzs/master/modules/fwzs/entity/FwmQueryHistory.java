/**
 * 
 */
package com.fwzs.master.modules.fwzs.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fwzs.master.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

/**
 * 防伪查询记录表Entity
 * @author ly
 * @version 2017-09-30
 */
public class FwmQueryHistory extends DataEntity<FwmQueryHistory> {
	
	private static final long serialVersionUID = 1L;
	private String qrcode;		// 防伪码
	private Date queryDate;		// 查询日期
	private String queryType;		// 类型
	private String querySource;		// 来源
	private String queryResult;		// 结果
	private String longitude;		// 经度
	private String latitude;		// 维度
    private String province;        // 省份
    private String city;            // 市
    private String town;            // 县区
	private String address;		    // 地址
	
	public FwmQueryHistory() {
		super();
	}

	public FwmQueryHistory(String id){
		super(id);
	}

	@Length(min=0, max=50, message="防伪码长度必须介于 0 和 50 之间")
	public String getQrcode() {
		return qrcode;
	}

	public void setQrcode(String qrcode) {
		this.qrcode = qrcode;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getQueryDate() {
		return queryDate;
	}

	public void setQueryDate(Date queryDate) {
		this.queryDate = queryDate;
	}
	
	@Length(min=0, max=1, message="类型长度必须介于 0 和 1 之间")
	public String getQueryType() {
		return queryType;
	}

	public void setQueryType(String queryType) {
		this.queryType = queryType;
	}
	
	@Length(min=0, max=100, message="来源长度必须介于 0 和 100 之间")
	public String getQuerySource() {
		return querySource;
	}

	public void setQuerySource(String querySource) {
		this.querySource = querySource;
	}
	
	@Length(min=0, max=1, message="结果长度必须介于 0 和 1 之间")
	public String getQueryResult() {
		return queryResult;
	}

	public void setQueryResult(String queryResult) {
		this.queryResult = queryResult;
	}
	
	@Length(min=0, max=20, message="经度长度必须介于 0 和 20 之间")
	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	
	@Length(min=0, max=20, message="维度长度必须介于 0 和 20 之间")
	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	@Length(min=0, max=20, message="地址长度必须介于 0 和 200 之间")
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }
}