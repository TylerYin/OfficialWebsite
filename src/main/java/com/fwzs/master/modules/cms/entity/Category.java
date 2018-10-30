package com.fwzs.master.modules.cms.entity;

import java.util.Date;
import java.util.List;

import com.fwzs.master.modules.cms.constant.ConfigConstant;
import org.hibernate.validator.constraints.Length;

import com.fwzs.master.common.config.Global;
import com.fwzs.master.common.persistence.TreeEntity;
import com.fwzs.master.modules.cms.util.CmsUtils;
import com.fwzs.master.modules.sys.entity.Office;
import com.google.common.collect.Lists;

/**
 * 栏目Entity
 *
 * @author ly
 * @version 2013-05-15
 */
public class Category extends TreeEntity<Category> {

    public static final String DEFAULT_TEMPLATE = "frontList";

    private static final long serialVersionUID = 1L;

    /**
     * 归属站点
     */
    private Site site;

    /**
     * 归属部门
     */
    private Office office;

    /**
     * 父级菜单
     */
    private Category parent;

    /**
     * 所有父级编号
     */
    private String parentIds;

    /**
     * 排序（升序）
     */
    private Integer sort;

    /**
     * 栏目名称
     */
    private String name;

    /**
     * 栏目模型（article：文章；picture：图片；download：下载；link：链接；special：专题）
     */
    private String module;

    /**
     * 栏目图片
     */
    private String image;

    /**
     * 链接
     */
    private String href;

    /**
     * 目标（ _blank、_self、_parent、_top）
     */
    private String target;

    /**
     * 描述，填写有助于搜索引擎优化
     */
    private String description;

    /**
     * 关键字，填写有助于搜索引擎优化
     */
    private String keywords;

    /**
     * 是否在导航中显示（1：显示；0：不显示）
     */
    private String inMenu;

    /**
     * 是否在分类页中显示列表（1：显示；0：不显示）
     */
    private String inList;

    /**
     * 展现方式（0:有子栏目显示栏目列表，无子栏目显示内容列表;1：首栏目内容列表；2：栏目第一条内容）
     */
    private String showModes;

    /**
     * 是否允许评论
     */
    private String allowComment;

    /**
     * 是否需要审核
     */
    private String isAudit;

    /**
     * 自定义列表视图
     */
    private String customListView;

    /**
     * 自定义内容视图
     */
    private String customContentView;

    /**
     * 视图参数
     */
    private String viewConfig;

    /**
     * 开始时间
     */
    private Date beginDate;

    /**
     * 结束时间
     */
    private Date endDate;

    /**
     * 信息量
     */
    private String cnt;

    /**
     * 点击量
     */
    private String hits;

    /**
     * 拥有子分类列表
     */
    private List<Category> childList = Lists.newArrayList();

    public Category() {
        super();
        this.module = "";
        this.sort = 30;
        this.inMenu = Global.HIDE;
        this.inList = Global.SHOW;
        this.showModes = "0";
        this.allowComment = Global.NO;
        this.delFlag = DEL_FLAG_NORMAL;
        this.isAudit = Global.NO;
    }

    public Category(String id) {
        this();
        this.id = id;
    }

    public Category(String id, Site site) {
        this();
        this.id = id;
        this.setSite(site);
    }

    public Site getSite() {
        return site;
    }

    public String getHits() {
        return hits;
    }

    public void setHits(String hits) {
        this.hits = hits;
    }

    public void setSite(Site site) {
        this.site = site;
    }

    public Office getOffice() {
        return office;
    }

    public void setOffice(Office office) {
        this.office = office;
    }

    @Length(min = 0, max = 20)
    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    @Length(min = 0, max = 255)
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Length(min = 0, max = 255)
    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    @Length(min = 0, max = 20)
    public String getTarget() {
        return target;
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

    public void setTarget(String target) {
        this.target = target;
    }

    @Length(min = 0, max = 255)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Length(min = 0, max = 255)
    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    @Length(min = 1, max = 1)
    public String getInMenu() {
        return inMenu;
    }

    public void setInMenu(String inMenu) {
        this.inMenu = inMenu;
    }

    @Length(min = 1, max = 1)
    public String getInList() {
        return inList;
    }

    public void setInList(String inList) {
        this.inList = inList;
    }

    @Length(min = 1, max = 1)
    public String getShowModes() {
        return showModes;
    }

    public void setShowModes(String showModes) {
        this.showModes = showModes;
    }

    @Length(min = 1, max = 1)
    public String getAllowComment() {
        return allowComment;
    }

    public void setAllowComment(String allowComment) {
        this.allowComment = allowComment;
    }

    @Length(min = 1, max = 1)
    public String getIsAudit() {
        return isAudit;
    }

    public void setIsAudit(String isAudit) {
        this.isAudit = isAudit;
    }

    public String getCustomListView() {
        return customListView;
    }

    public void setCustomListView(String customListView) {
        this.customListView = customListView;
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

    public List<Category> getChildList() {
        return childList;
    }

    public void setChildList(List<Category> childList) {
        this.childList = childList;
    }

    public String getCnt() {
        return cnt;
    }

    public void setCnt(String cnt) {
        this.cnt = cnt;
    }

    public static void sortList(List<Category> list, List<Category> sourcelist, String parentId) {
        for (int i = 0; i < sourcelist.size(); i++) {
            Category e = sourcelist.get(i);
            if (e.getParent() != null && e.getParent().getId() != null
                    && e.getParent().getId().equals(parentId)) {
                list.add(e);

                // 判断是否还有子节点, 有则继续获取子节点
                for (int j = 0; j < sourcelist.size(); j++) {
                    Category child = sourcelist.get(j);
                    if (child.getParent() != null && child.getParent().getId() != null
                            && child.getParent().getId().equals(e.getId())) {
                        sortList(list, sourcelist, e.getId());
                        break;
                    }
                }
            }
        }
    }

    public String getIds() {
        return (this.getParentIds() != null ? this.getParentIds().replaceAll(",", " ") : "")
                + (this.getId() != null ? this.getId() : "");
    }

    public boolean isRoot() {
        return isRoot(this.id);
    }

    public static boolean isRoot(String id) {
        return id != null && ConfigConstant.DEFAULT_SITE_ID.equals(id);
    }

    public String getUrl() {
        return CmsUtils.getUrlDynamic(this);
    }

    @Override
    public Category getParent() {
        return parent;
    }

    @Override
    public void setParent(Category parent) {
        this.parent = parent;
    }
}