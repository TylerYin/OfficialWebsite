package com.fwzs.master.modules.cms.entity;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;

import com.fwzs.master.common.persistence.DataEntity;
import com.fwzs.master.common.utils.IdGen;
import com.fwzs.master.modules.sys.entity.User;

/**
 * 留言Entity
 *
 * @author ly
 * @version 2013-05-15
 */
public class GuestBook extends DataEntity<GuestBook> {

    private static final long serialVersionUID = 1L;

    /**
     * 留言分类（咨询、建议、投诉、其它）
     */
    private String type;

    /**
     * 留言内容
     */
    private String content;

    /**
     * 姓名
     */
    private String name;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 电话
     */
    private String phone;

    /**
     * 单位
     */
    private String workunit;

    /**
     * 留言IP
     */
    private String ip;

    /**
     * 留言时间
     */
    private Date createDate;

    /**
     * 回复人
     */
    private User reUser;

    /**
     * 回复时间
     */
    private Date reDate;

    /**
     * 回复内容
     */
    private String reContent;

    /**
     * 删除标记删除标记（0：正常；1：删除；2：审核）
     */
    private String delFlag;

    public GuestBook() {
        this.delFlag = DEL_FLAG_NORMAL;
    }

    public GuestBook(String id) {
        this();
        this.id = id;
    }

    public void prePersist() {
        this.id = IdGen.uuid();
        this.createDate = new Date();
    }

    @Length(min = 1, max = 100)
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Length(min = 1, max = 2000)
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Length(min = 1, max = 100)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Email
    @Length(min = 0, max = 100)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Length(min = 0, max = 100)
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Length(min = 0, max = 100)
    public String getWorkunit() {
        return workunit;
    }

    public void setWorkunit(String workunit) {
        this.workunit = workunit;
    }

    @Length(min = 1, max = 100)
    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    @Override
    @NotNull
    public Date getCreateDate() {
        return createDate;
    }

    @Override
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public User getReUser() {
        return reUser;
    }

    public void setReUser(User reUser) {
        this.reUser = reUser;
    }

    public String getReContent() {
        return reContent;
    }

    public void setReContent(String reContent) {
        this.reContent = reContent;
    }

    public Date getReDate() {
        return reDate;
    }

    public void setReDate(Date reDate) {
        this.reDate = reDate;
    }

    @Override
    @Length(min = 1, max = 1)
    public String getDelFlag() {
        return delFlag;
    }

    @Override
    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

}


