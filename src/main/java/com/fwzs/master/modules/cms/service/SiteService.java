package com.fwzs.master.modules.cms.service;

import com.fwzs.master.modules.sys.utils.UserUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fwzs.master.common.persistence.Page;
import com.fwzs.master.common.service.CrudService;
import com.fwzs.master.modules.cms.dao.SiteDao;
import com.fwzs.master.modules.cms.entity.Site;
import com.fwzs.master.modules.cms.util.CmsUtils;

/**
 * 站点Service
 *
 * @author ly
 * @version 2013-01-15
 */
@Service
@Transactional(readOnly = true)
public class SiteService extends CrudService<SiteDao, Site> {
    @Override
    public Page<Site> findPage(Page<Site> page, Site site) {
        site.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
        return super.findPage(page, site);
    }

    @Override
    @Transactional(readOnly = false)
    public void save(Site site) {
        if (site.getCopyright() != null) {
            site.setCopyright(StringEscapeUtils.unescapeHtml4(site.getCopyright()));
        }
        super.save(site);
        CmsUtils.removeCache("site_" + site.getId());
        CmsUtils.removeCache("siteList");
    }

    @Transactional(readOnly = false)
    public void delete(Site site, Boolean isRe) {
        site.setDelFlag(isRe != null && isRe ? Site.DEL_FLAG_NORMAL : Site.DEL_FLAG_DELETE);
        super.delete(site);
        CmsUtils.removeCache("site_" + site.getId());
        CmsUtils.removeCache("siteList");
    }
}
