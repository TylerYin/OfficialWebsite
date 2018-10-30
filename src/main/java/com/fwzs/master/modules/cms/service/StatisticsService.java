package com.fwzs.master.modules.cms.service;

import java.util.List;
import java.util.Map;

import com.fwzs.master.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fwzs.master.common.service.BaseService;
import com.fwzs.master.modules.cms.dao.ArticleDao;
import com.fwzs.master.modules.cms.entity.Category;
import com.fwzs.master.modules.cms.entity.Site;
import com.fwzs.master.modules.sys.entity.Office;

/**
 * 统计Service
 *
 * @author ly
 * @version 2013-05-21
 */
@Service
@Transactional(readOnly = true)
public class StatisticsService extends BaseService {

    @Autowired
    private ArticleDao articleDao;

    public List<Category> article(Map<String, Object> paramMap) {
        Category category = new Category();

        Site site = new Site();
        site.setId(Site.getCurrentSiteId());
        category.setSite(site);

        String categoryId = (String) paramMap.get("categoryId");
        if (categoryId != null && !("".equals(categoryId))) {
            category.setId(categoryId);
            category.setParentIds(categoryId);
        }

        String officeId = (String) (paramMap.get("officeId"));
        Office office = new Office();
        if (officeId != null && !("".equals(officeId))) {
            office.setId(officeId);
            category.setOffice(office);
        } else {
            category.setOffice(office);
        }
        category.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
        return articleDao.findStatistics(category);
    }
}
