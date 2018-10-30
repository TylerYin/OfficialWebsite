package com.fwzs.master.modules.cms.entity;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import com.fwzs.master.common.config.Global;
import com.fwzs.master.common.persistence.DataEntity;

/**
 * 文章Entity
 *
 * @author ly
 * @version 2013-01-15
 */
public class ArticleData extends DataEntity<ArticleData> {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private String id;

    /**
     * 内容
     */
    private String content;

    /**
     * 来源
     */
    private String copyfrom;

    /**
     * 相关文章
     */
    private String relation;

    /**
     * 是否允许评论
     */
    private String allowComment;

    private Article article;

    public ArticleData() {
        super();
        this.allowComment = Global.YES;
    }

    public ArticleData(String id) {
        this();
        this.id = id;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @NotBlank
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Length(min = 0, max = 255)
    public String getCopyfrom() {
        return copyfrom;
    }

    public void setCopyfrom(String copyfrom) {
        this.copyfrom = copyfrom;
    }

    @Length(min = 0, max = 255)
    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    @Length(min = 1, max = 1)
    public String getAllowComment() {
        return allowComment;
    }

    public void setAllowComment(String allowComment) {
        this.allowComment = allowComment;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

}