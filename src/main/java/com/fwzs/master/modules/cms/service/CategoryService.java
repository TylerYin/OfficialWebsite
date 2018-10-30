package com.fwzs.master.modules.cms.service;

import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fwzs.master.common.config.Global;
import com.fwzs.master.common.persistence.Page;
import com.fwzs.master.common.service.TreeService;
import com.fwzs.master.modules.cms.dao.CategoryDao;
import com.fwzs.master.modules.cms.entity.Category;
import com.fwzs.master.modules.cms.entity.Site;
import com.fwzs.master.modules.cms.util.CmsUtils;
import com.fwzs.master.modules.sys.entity.Office;
import com.fwzs.master.modules.sys.entity.User;
import com.fwzs.master.modules.sys.utils.UserUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

/**
 * 栏目Service
 *
 * @author ly
 * @version 2013-5-31
 */
@Service
@Transactional(readOnly = true)
public class CategoryService extends TreeService<CategoryDao, Category> {

    public static final String CACHE_CATEGORY_LIST = "categoryList";

    private Category entity = new Category();

    public List<Category> findByUser(boolean isCurrentSite, String module) {
        List<Category> list = (List<Category>) UserUtils.getCache(CACHE_CATEGORY_LIST);
        if (CollectionUtils.isEmpty(list)) {
            User user = UserUtils.getUser();
            Category category = new Category();
            category.setSite(new Site());
            category.setOffice(new Office());
            category.setParent(new Category());
            category.getSqlMap().put("dsf", dataScopeFilter(user, "o", "u"));
            list = dao.findList(category);

            // 将没有父节点的节点，找到父节点
            Set<String> parentIdSet = Sets.newHashSet();
            for (Category e : list) {
                if (e.getParent() != null && StringUtils.isNotBlank(e.getParent().getId())) {
                    boolean isExistParent = false;
                    for (Category e2 : list) {
                        if (e.getParent().getId().equals(e2.getId())) {
                            isExistParent = true;
                            break;
                        }
                    }
                    if (!isExistParent) {
                        parentIdSet.add(e.getParent().getId());
                    }
                }
            }
            UserUtils.putCache(CACHE_CATEGORY_LIST, list);
        }

        if (isCurrentSite) {
            List<Category> categoryList = Lists.newArrayList();
            for (Category e : list) {
                boolean isRootCategory = Category.isRoot(e.getId()) || (e.getSite() != null && e.getSite().getId() != null
                        && e.getSite().getId().equals(Site.getCurrentSiteId()));
                if (isRootCategory) {
                    if (StringUtils.isNotEmpty(module)) {
                        if (module.equals(e.getModule()) || "".equals(e.getModule())) {
                            categoryList.add(e);
                        }
                    } else {
                        categoryList.add(e);
                    }
                }
            }
            return categoryList;
        }
        return list;
    }

    public List<Category> findByParentId(String parentId, String siteId) {
        Category parent = new Category();
        parent.setId(parentId);
        entity.setParent(parent);
        Site site = new Site();
        site.setId(siteId);
        entity.setSite(site);
        return dao.findByParentIdAndSiteId(entity);
    }

    public Page<Category> find(Page<Category> page, Category category) {
        category.setPage(page);
        category.setInMenu(Global.SHOW);
        page.setList(dao.findModule(category));
        return page;
    }

    @Override
    @Transactional(readOnly = false)
    public void save(Category category) {
        category.setSite(new Site(Site.getCurrentSiteId()));
        if (StringUtils.isNotBlank(category.getViewConfig())) {
            category.setViewConfig(StringEscapeUtils.unescapeHtml4(category.getViewConfig()));
        }
        super.save(category);
        UserUtils.removeCache(CACHE_CATEGORY_LIST);
        CmsUtils.removeCache("mainNavList_" + category.getSite().getId());
    }

    @Override
    @Transactional(readOnly = false)
    public void delete(Category category) {
        super.delete(category);
        UserUtils.removeCache(CACHE_CATEGORY_LIST);
        CmsUtils.removeCache("mainNavList_" + category.getSite().getId());
    }

    /**
     * 通过编号获取栏目列表
     */
    public List<Category> findByIds(String ids) {
        List<Category> list = Lists.newArrayList();
        String[] idss = StringUtils.split(ids, ",");
        if (idss.length > 0) {
            for (String id : idss) {
                Category e = dao.get(id);
                if (null != e) {
                    list.add(e);
                }
            }
        }
        return list;
    }
}
