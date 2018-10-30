package com.fwzs.master.modules.cms.entity;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.fwzs.master.common.persistence.DataEntity;
import com.fwzs.master.modules.sys.entity.User;

/**
 * 评论Entity
 *
 * @author ly
 * @version 2013-05-15
 */
public class Comment extends DataEntity<Comment> {

    private static final long serialVersionUID = 1L;

    /**
     * 分类编号
     */
    private Category category;

    /**
     * 归属分类内容的编号（Article.id、Photo.id、Download.id）
     */
    private String contentId;

    /**
     * 归属分类内容的标题（Article.title、Photo.title、Download.title）
     */
    private String title;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 评论姓名
     */
    private String name;

    /**
     * 评论IP
     */
    private String ip;

    /**
     * 评论时间
     */
    private Date createDate;

    /**
     * 审核人
     */
    private User auditUser;

    /**
     * 审核时间
     */
    private Date auditDate;

    /**
     * 删除标记删除标记（0：正常；1：删除；2：审核）
     */
    private String delFlag;

    public Comment() {
        super();
        this.delFlag = DEL_FLAG_NORMAL;
    }

    public Comment(String id) {
        this();
        this.id = id;
    }

    public Comment(Category category) {
        this();
        this.category = category;
    }

    @NotNull
    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @NotNull
    public String getContentId() {
        return contentId;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
    }

    @Length(min = 1, max = 255)
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Length(min = 1, max = 255)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Length(min = 1, max = 100)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getAuditUser() {
        return auditUser;
    }

    public void setAuditUser(User auditUser) {
        this.auditUser = auditUser;
    }

    public Date getAuditDate() {
        return auditDate;
    }

    public void setAuditDate(Date auditDate) {
        this.auditDate = auditDate;
    }

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