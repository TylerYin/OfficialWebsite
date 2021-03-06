package com.fwzs.master.modules.cms.entity;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.fwzs.master.modules.cms.constant.ConfigConstant;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Length;

import com.fwzs.master.common.persistence.DataEntity;
import com.fwzs.master.modules.cms.util.CmsUtils;
import com.fwzs.master.modules.sys.entity.User;
import com.google.common.collect.Lists;

/**
 * 文章Entity
 *
 * @author ly
 * @version 2013-05-15
 */
public class Article extends DataEntity<Article> {

    public static final String DEFAULT_TEMPLATE = "frontViewArticle";

    private static final long serialVersionUID = 1L;

    /**
     * 分类编号
     */
    private Category category;

    /**
     * 标题
     */
    private String title;

    /**
     * 外部链接
     */
    private String link;

    /**
     * 标题颜色（red：红色；green：绿色；blue：蓝色；yellow：黄色；orange：橙色）
     */
    private String color;

    /**
     * 文章图片
     */
    private String image;

    /**
     * 关键字
     */
    private String keywords;

    /**
     * 描述、摘要
     */
    private String description;

    /**
     * 权重，越大越靠前
     */
    private Integer weight;

    /**
     * 权重期限，超过期限，将weight设置为0
     */
    private Date weightDate;

    /**
     * 点击数
     */
    private Integer hits;

    /**
     * 推荐位，多选（1：首页焦点图；2：栏目页文章推荐；）
     */
    private String posid;

    /**
     * 自定义内容视图
     */
    private String customContentView;

    /**
     * 视图参数
     */
    private String viewConfig;

    /**
     * 文章副表
     */
    private ArticleData articleData;

    /**
     * 开始时间
     */
    private Date beginDate;

    /**
     * 结束时间
     */
    private Date endDate;

    private User user;

    public Article() {
        super();
        this.weight = 0;
        this.hits = 0;
        this.posid = "";
    }

    public Article(String id) {
        this();
        this.id = id;
    }

    public Article(Category category) {
        this();
        this.category = category;
    }

    public void prePersist() {
        articleData.setId(this.id);
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Length(min = 0, max = 255)
    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Length(min = 0, max = 50)
    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Length(min = 0, max = 255)
    public String getImage() {
        return image;
    }

    /**
     * CmsUtils.formatImageSrcToDb(image);
     *
     * @param image
     */
    public void setImage(String image) {
        this.image = image;
    }

    @Length(min = 0, max = 255)
    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    @Length(min = 0, max = 255)
    public String getDescription() {
        return description;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @NotNull
    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Date getWeightDate() {
        return weightDate;
    }

    public void setWeightDate(Date weightDate) {
        this.weightDate = weightDate;
    }

    public Integer getHits() {
        return hits;
    }

    public void setHits(Integer hits) {
        this.hits = hits;
    }

    @Length(min = 0, max = 10)
    public String getPosid() {
        return posid;
    }

    public void setPosid(String posid) {
        this.posid = posid;
    }

    public String getCustomContentView() {
        return customContentView;
    }

    public void setCustomContentView(String customContentView) {
        this.customContentView = customContentView;
    }

    public String getViewConfig() {
        return viewConfig;
    }

    public void setViewConfig(String viewConfig) {
        this.viewConfig = viewConfig;
    }

    public ArticleData getArticleData() {
        return articleData;
    }

    public void setArticleData(ArticleData articleData) {
        this.articleData = articleData;
    }

    public List<String> getPosidList() {
        List<String> list = Lists.newArrayList();
        if (posid != null) {
            for (String s : StringUtils.split(posid, ConfigConstant.DELIMITER_COMMA)) {
                list.add(s);
            }
        }
        return list;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setPosidList(List<String> list) {
        posid = "," + StringUtils.join(list, ",") + ",";
    }

    public String getUrl() {
        return CmsUtils.getUrlDynamic(this);
    }

    public String getImageSrc() {
        return CmsUtils.formatImageSrcToWeb(this.image);
    }

}


