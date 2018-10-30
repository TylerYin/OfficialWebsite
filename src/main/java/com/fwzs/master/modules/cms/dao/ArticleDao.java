package com.fwzs.master.modules.cms.dao;

import java.util.List;

import com.fwzs.master.common.persistence.CrudDao;
import com.fwzs.master.common.persistence.annotation.MyBatisDao;
import com.fwzs.master.modules.cms.entity.Article;
import com.fwzs.master.modules.cms.entity.Category;

/**
 * 文章DAO接口
 *
 * @author ly
 * @version 2013-8-23
 */
@MyBatisDao
public interface ArticleDao extends CrudDao<Article> {

    /**
     * 查找文章
     *
     * @param ids
     * @return
     */
    List<Article> findByIdIn(String[] ids);

    /**
     * 更新访问次数
     *
     * @param id
     * @return
     */
    int updateHitsAddOne(String id);

    /**
     * 更新过期权重
     *
     * @param article
     * @return
     */
    int updateExpiredWeight(Article article);

    /**
     * 获取统计数据
     *
     * @param category
     * @return
     */
    List<Category> findStatistics(Category category);
}
