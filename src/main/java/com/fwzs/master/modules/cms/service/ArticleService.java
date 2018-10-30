package com.fwzs.master.modules.cms.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fwzs.master.modules.cms.constant.ConfigConstant;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fwzs.master.common.config.Global;
import com.fwzs.master.common.persistence.Page;
import com.fwzs.master.common.service.CrudService;
import com.fwzs.master.common.utils.CacheUtils;
import com.fwzs.master.common.utils.StringUtils;
import com.fwzs.master.modules.cms.dao.ArticleDao;
import com.fwzs.master.modules.cms.dao.ArticleDataDao;
import com.fwzs.master.modules.cms.dao.CategoryDao;
import com.fwzs.master.modules.cms.entity.Article;
import com.fwzs.master.modules.cms.entity.ArticleData;
import com.fwzs.master.modules.cms.entity.Category;
import com.fwzs.master.modules.sys.utils.UserUtils;
import com.google.common.collect.Lists;

/**
 * 文章Service
 *
 * @author ly
 * @version 2013-05-15
 */
@Service
@Transactional(readOnly = true)
public class ArticleService extends CrudService<ArticleDao, Article> {

    @Autowired
    private ArticleDataDao articleDataDao;
    @Autowired
    private CategoryDao categoryDao;

    @Transactional(readOnly = false)
    public Page<Article> findPage(Page<Article> page, Article article, boolean isDataScopeFilter) {
        // 更新过期的权重，间隔为“6”个小时
        Date updateExpiredWeightDate = (Date) CacheUtils.get("updateExpiredWeightDateByArticle");
        boolean isUpdateExpiredWeight = updateExpiredWeightDate == null || (updateExpiredWeightDate != null
                && updateExpiredWeightDate.getTime() < System.currentTimeMillis());
        if (isDataScopeFilter) {
            article.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
        }
        if (isUpdateExpiredWeight) {
            dao.updateExpiredWeight(article);
            CacheUtils.put("updateExpiredWeightDateByArticle", DateUtils.addHours(new Date(), 6));
        }

        if (article.getCategory() != null && StringUtils.isNotBlank(article.getCategory().getId()) && !Category.isRoot(article.getCategory().getId())) {
            Category category = categoryDao.get(article.getCategory().getId());
            if (category == null) {
                category = new Category();
            }
            category.setParentIds(category.getId());
            category.setSite(category.getSite());
            article.setCategory(category);
        } else {
            article.setCategory(new Category());
        }
        return super.findPage(page, article);
    }

    @Override
    @Transactional(readOnly = false)
    public void save(Article article) {
        if (article.getArticleData().getContent() != null) {
            article.getArticleData().setContent(StringEscapeUtils.unescapeHtml4(
                    article.getArticleData().getContent()));
        }

        // 如果没有审核权限，则将当前内容改为待审核状态
        if (!UserUtils.getSubject().isPermitted(ConfigConstant.CMS_ARTICLE_AUDIT)) {
            article.setDelFlag(Article.DEL_FLAG_AUDIT);
        }

        // 如果栏目不需要审核，则将该内容设为发布状态
        if (article.getCategory() != null && StringUtils.isNotBlank(article.getCategory().getId())) {
            Category category = categoryDao.get(article.getCategory().getId());
            if (!Global.YES.equals(category.getIsAudit())) {
                article.setDelFlag(Article.DEL_FLAG_NORMAL);
            }
        }
        article.setUpdateBy(UserUtils.getUser());
        article.setUpdateDate(new Date());
        if (StringUtils.isNotBlank(article.getViewConfig())) {
            article.setViewConfig(StringEscapeUtils.unescapeHtml4(article.getViewConfig()));
        }

        ArticleData articleData;
        if (StringUtils.isBlank(article.getId())) {
            article.preInsert();
            articleData = article.getArticleData();
            articleData.setId(article.getId());
            dao.insert(article);
            articleDataDao.insert(articleData);
        } else {
            article.preUpdate();
            articleData = article.getArticleData();
            articleData.setId(article.getId());
            dao.update(article);
            articleDataDao.update(article.getArticleData());
        }
    }

    @Transactional(readOnly = false)
    public void delete(Article article, String status) {
        if (ConfigConstant.AUDITED_STATUS.equals(status)) {
            dao.delete(article);
        } else {
            if (ConfigConstant.DELETED_STATUS.equals(status)) {
                article.setDelFlag(ConfigConstant.AUDITING_STATUS);
            } else {
                article.setDelFlag(ConfigConstant.AUDITED_STATUS);
            }
            dao.updateStatusAsAuditing(article);
        }
    }

    /**
     * 通过编号获取内容标题
     *
     * @return new Object[]{栏目Id,文章Id,文章标题}
     */
    public List<Object[]> findByIds(String ids) {
        if (ids == null) {
            return new ArrayList<>();
        }
        List<Object[]> list = Lists.newArrayList();
        String[] idss = StringUtils.split(ids, ",");
        Article e;
        for (int i = 0; (idss.length - i) > 0; i++) {
            e = dao.get(idss[i]);
            if (null != e) {
                list.add(new Object[]{e.getCategory().getId(), e.getId(), StringUtils.abbr(e.getTitle(), 50)});
            }
        }
        return list;
    }

    /**
     * 点击数加一
     */
    @Transactional(readOnly = false)
    public void updateHitsAddOne(String id) {
        dao.updateHitsAddOne(id);
    }

    /**
     * 更新索引
     */
    public void createIndex() {
        //dao.createIndex();
    }

    /**
     * 全文检索
     * FIXME 暂不提供检索功能
     */
    public Page<Article> search(Page<Article> page, String q, String categoryId, String beginDate, String endDate) {
        // 设置查询条件
//		BooleanQuery query = dao.getFullTextQuery(q, "title","keywords","description","articleData.content");
//
//		// 设置过滤条件
//		List<BooleanClause> bcList = Lists.newArrayList();
//
//		bcList.add(new BooleanClause(new TermQuery(new Term(Article.FIELD_DEL_FLAG, Article.DEL_FLAG_NORMAL)), Occur.MUST));
//		if (StringUtils.isNotBlank(categoryId)){
//			bcList.add(new BooleanClause(new TermQuery(new Term("category.ids", categoryId)), Occur.MUST));
//		}
//
//		if (StringUtils.isNotBlank(beginDate) && StringUtils.isNotBlank(endDate)) {
//			bcList.add(new BooleanClause(new TermRangeQuery("updateDate", beginDate.replaceAll("-", ""),
//					endDate.replaceAll("-", ""), true, true), Occur.MUST));
//		}

        //BooleanQuery queryFilter = dao.getFullTextQuery((BooleanClause[])bcList.toArray(new BooleanClause[bcList.size()]));

//		System.out.println(queryFilter);

        // 设置排序（默认相识度排序）
        //FIXME 暂时不提供lucene检索
        //Sort sort = null;//new Sort(new SortField("updateDate", SortField.DOC, true));
        // 全文检索
        //dao.search(page, query, queryFilter, sort);
        // 关键字高亮
        //dao.keywordsHighlight(query, page.getList(), 30, "title");
        //dao.keywordsHighlight(query, page.getList(), 130, "description","articleData.content");

        return page;
    }

}
