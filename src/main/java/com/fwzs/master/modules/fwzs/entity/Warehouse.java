package com.fwzs.master.modules.fwzs.entity;

import com.fwzs.master.common.persistence.DataEntity;
import com.fwzs.master.modules.sys.entity.Area;
import com.fwzs.master.modules.sys.entity.Office;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Tyler Yin
 * @create 2017-11-16 16:18
 * @description 仓库实体类
 **/
public class Warehouse extends DataEntity<Warehouse> {
    private static final long serialVersionUID = 1L;

    private Warehouse parentWarehouse;                               // 上级仓库
    private List<Warehouse> childrenWarehouse = new ArrayList<>();   // 下级仓库
    private String name;                                             // 仓库名称
    private String warehouseNo;                                      // 仓库编号
    private String warehouseJianMa;                                  // 拼音简码
    private Area salesArea; 	                                     // 仓库所在区域
    private Office company; 	                                     // 仓库所属公司
    private String leader; 	                                         // 仓库负责人
    private String grade; 	                                         // 仓库级别
    private String address; 	                                     // 仓库地址
    private String phone; 	                                         // 仓库电话
    private String size; 	                                         // 仓库面积
    private String volume; 	                                         // 仓库容量
    private String longitude; 	                                     // 仓库所在地址经度
    private String latitude; 	                                     // 仓库所在地址纬度
    private String warehouseInfo; 	                                 // 仓库描述信息
    private String remark; 	                                         // 仓库备注
    private List<String> allChildrenWarehouse = new ArrayList<>();   // 下级仓库

    public Warehouse getParentWarehouse() {
        return parentWarehouse;
    }

    public void setParentWarehouse(Warehouse parentWarehouse) {
        this.parentWarehouse = parentWarehouse;
    }

    public List<Warehouse> getChildrenWarehouse() {
        return childrenWarehouse;
    }

    public void setChildrenWarehouse(List<Warehouse> childrenWarehouse) {
        this.childrenWarehouse = childrenWarehouse;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWarehouseNo() {
        return warehouseNo;
    }

    public void setWarehouseNo(String warehouseNo) {
        this.warehouseNo = warehouseNo;
    }

    public String getWarehouseJianMa() {
        return warehouseJianMa;
    }

    public void setWarehouseJianMa(String warehouseJianMa) {
        this.warehouseJianMa = warehouseJianMa;
    }

    public Area getSalesArea() {
        return salesArea;
    }

    public void setSalesArea(Area salesArea) {
        this.salesArea = salesArea;
    }

    public Office getCompany() {
        return company;
    }

    public void setCompany(Office company) {
        this.company = company;
    }

    public String getLeader() {
        return leader;
    }

    public void setLeader(String leader) {
        this.leader = leader;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
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

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getWarehouseInfo() {
        return warehouseInfo;
    }

    public void setWarehouseInfo(String warehouseInfo) {
        this.warehouseInfo = warehouseInfo;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<String> getAllChildrenWarehouse() {
        return allChildrenWarehouse;
    }

    public void setAllChildrenWarehouse(List<String> allChildrenWarehouse) {
        this.allChildrenWarehouse = allChildrenWarehouse;
    }
}
