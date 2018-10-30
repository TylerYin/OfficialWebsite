package com.fwzs.master.modules.fwzs.entity;

import com.fwzs.master.common.persistence.DataEntity;
import com.fwzs.master.modules.sys.entity.Area;
import com.fwzs.master.modules.sys.entity.Office;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Tyler Yin
 * @create 2017-11-13 14:24
 * @description 经销商Entity
 **/
public class Dealer extends DataEntity<Dealer>{
    private static final long serialVersionUID = 1L;

    private Dealer parentDealer;                                    // 父级经销商
    private List<Dealer> childrenDealer = new ArrayList<>();        // 下级仓库
    private String name;                                            // 经销商名称
    private String dealerNo;                                        // 经销商编号
    private String dealerJianMa;                                    // 经销商简码
    private String address; 	                                    // 经销商地址
    private String phone; 	                                        // 经销商电话
    private String email; 	                                        // 经销商邮箱
    private String fax; 	                                        // 经销商传真
    private Office company; 	                                    // 经销商所属企业
    private Area salesArea; 	                                    // 经销商所在区域
    private String grade; 	                                        // 经销商所级别
    private String dealerInfo; 	                                    // 经销商信息
    private String longitude; 	                                    // 经销商所在地址经度
    private String latitude; 	                                    // 经销商所在地址纬度
    private String remark; 	                                        // 经销商备注

    private String qq; 	                                            // 经销商QQ
    private String wechat; 	                                        // 经销商微信
    private String certificateUrl; 	                                // 经销商证书
    private String account; 	                                    // 经销商帐号
    private String password; 	                                    // 经销商密码

    private List<String> allChildrenDealer = new ArrayList<>();     // 下级仓库

    private String parentIds;                                       // 父级经销商主键
    private String areaNames;                                       // 拥有的区域名称列表
    private List<Area> areaList = Lists.newArrayList();             // 拥有区域列表

    public Dealer getParentDealer() {
        return parentDealer;
    }

    public void setParentDealer(Dealer parentDealer) {
        this.parentDealer = parentDealer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDealerNo() {
        return dealerNo;
    }

    public void setDealerNo(String dealerNo) {
        this.dealerNo = dealerNo;
    }

    public String getDealerJianMa() {
        return dealerJianMa;
    }

    public void setDealerJianMa(String dealerJianMa) {
        this.dealerJianMa = dealerJianMa;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public Office getCompany() {
        return company;
    }

    public void setCompany(Office company) {
        this.company = company;
    }

    public Area getSalesArea() {
        return salesArea;
    }

    public void setSalesArea(Area salesArea) {
        this.salesArea = salesArea;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getDealerInfo() {
        return dealerInfo;
    }

    public void setDealerInfo(String dealerInfo) {
        this.dealerInfo = dealerInfo;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getWechat() {
        return wechat;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getCertificateUrl() {
        return certificateUrl;
    }

    public void setCertificateUrl(String certificateUrl) {
        this.certificateUrl = certificateUrl;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Dealer> getChildrenDealer() {
        return childrenDealer;
    }

    public void setChildrenDealer(List<Dealer> childrenDealer) {
        this.childrenDealer = childrenDealer;
    }

    public List<String> getAllChildrenDealer() {
        return allChildrenDealer;
    }

    public void setAllChildrenDealer(List<String> allChildrenDealer) {
        this.allChildrenDealer = allChildrenDealer;
    }

    public String getParentIds() {
        return parentIds;
    }

    public void setParentIds(String parentIds) {
        this.parentIds = parentIds;
    }

    public List<Area> getAreaList() {
        return areaList;
    }

    public void setAreaList(List<Area> areaList) {
        this.areaList = areaList;
    }

    public List<String> getAreaIdList() {
        List<String> areaIdList = Lists.newArrayList();
        for (Area area : areaList) {
            areaIdList.add(area.getId());
        }
        return areaIdList;
    }

    public void setAreaIdList(List<String> areaIdList) {
        areaList = Lists.newArrayList();
        for (String areaId : areaIdList) {
            Area area = new Area();
            area.setId(areaId);
            areaList.add(area);
        }
    }

    public String getAreaIds() {
        return StringUtils.join(getAreaIdList(), ",");
    }

    public void setAreaIds(String areaIds) {
        areaList = Lists.newArrayList();
        if (areaIds != null){
            String[] ids = StringUtils.split(areaIds, ",");
            setAreaIdList(Lists.newArrayList(ids));
        }
    }

    public String getAreaNames() {
        return areaNames;
    }

    public void setAreaNames(String areaNames) {
        this.areaNames = areaNames;
    }
}
