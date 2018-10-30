package com.fwzs.master.modules.fwzs.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fwzs.master.common.persistence.DataEntity;
import com.fwzs.master.common.supcan.annotation.treelist.cols.SupCol;
import com.fwzs.master.common.utils.excel.annotation.ExcelField;
import com.fwzs.master.modules.sys.entity.Office;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

/**
 * @author Tyler Yin
 * @create 2018-02-01 9:09
 * @description PDA用户
 **/
public class PdaUser extends DataEntity<PdaUser> {
    private static final long serialVersionUID = 1L;

    private String no;                      // 工号
    private String name;                    // 姓名
    private String mobile;                  // 手机
    private Office company;                 // 归属公司
    private Dealer dealer;                  // 归属经销商
    private String loginName;               // 登录名
    private String password;                // 密码
    private String oldLoginName;            // 原登录名
    private String newPassword;             // 新密码
    private String loginFlag;               // 是否允许登录

    @SupCol(isUnique = "true", isHide = "true")
    @ExcelField(title = "ID", type = 1, align = 2, sort = 1)
    public String getId() {
        return id;
    }

    @JsonIgnore
    public Office getCompany() {
        return company;
    }

    public void setCompany(Office company) {
        this.company = company;
    }

    @JsonIgnore
    public Dealer getDealer() {
        return dealer;
    }

    public void setDealer(Dealer dealer) {
        this.dealer = dealer;
    }

    @Length(min = 1, max = 100, message = "登录名长度必须介于 1 和 100 之间")
    @ExcelField(title = "登录名", align = 2, sort = 30)
    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    @Length(min = 1, max = 100, message = "密码长度必须介于 1 和 100 之间")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Length(min = 1, max = 100, message = "姓名长度必须介于 1 和 100 之间")
    @ExcelField(title = "姓名", align = 2, sort = 40)
    public String getName() {
        return name;
    }

    @Length(min = 1, max = 100, message = "工号长度必须介于 1 和 100 之间")
    @ExcelField(title = "工号", align = 2, sort = 45)
    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Length(min = 0, max = 200, message = "手机长度必须介于 1 和 200 之间")
    @ExcelField(title = "手机", align = 2, sort = 70)
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @ExcelField(title = "备注", align = 1, sort = 900)
    public String getRemarks() {
        return remarks;
    }

    @ExcelField(title = "创建时间", type = 0, align = 1, sort = 90)
    public Date getCreateDate() {
        return createDate;
    }

    public String getOldLoginName() {
        return oldLoginName;
    }

    public void setOldLoginName(String oldLoginName) {
        this.oldLoginName = oldLoginName;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public boolean isAdmin() {
        return isAdmin(this.id);
    }

    public static boolean isAdmin(String id) {
        return id != null && "1".equals(id);
    }

    public String getLoginFlag() {
        return loginFlag;
    }

    public void setLoginFlag(String loginFlag) {
        this.loginFlag = loginFlag;
    }

    @Override
    public String toString() {
        return id;
    }
}
