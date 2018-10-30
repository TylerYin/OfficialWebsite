package com.fwzs.master.modules.cms.service;

import java.util.Date;
import java.util.List;

import com.fwzs.master.modules.cms.constant.ConfigConstant;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fwzs.master.common.persistence.Page;
import com.fwzs.master.common.service.CrudService;
import com.fwzs.master.common.utils.CacheUtils;
import com.fwzs.master.common.utils.StringUtils;
import com.fwzs.master.modules.cms.dao.LinkDao;
import com.fwzs.master.modules.cms.entity.Link;
import com.google.common.collect.Lists;

/**
 * 链接Service
 *
 * @author ly
 * @version 2013-01-15
 */
@Service
@Transactional(readOnly = true)
public class LinkService extends CrudService<LinkDao, Link> {

    @Transactional(readOnly = false)
    public Page<Link> findPage(Page<Link> page, Link link, boolean isDataScopeFilter) {
        // 更新过期的权重，间隔为“6”个小时
        Date updateExpiredWeightDate = (Date) CacheUtils.get("updateExpiredWeightDateByLink");
        boolean isUpdateExpiredWeight = updateExpiredWeightDate == null || (updateExpiredWeightDate != null
                && updateExpiredWeightDate.getTime() < System.currentTimeMillis());
        if (isUpdateExpiredWeight) {
            dao.updateExpiredWeight(link);
            CacheUtils.put("updateExpiredWeightDateByLink", DateUtils.addHours(new Date(), 6));
        }

        if (isDataScopeFilter) {
            link.getSqlMap().put("dsf", dataScopeFilter(link.getCurrentUser(), "o", "u"));
        }

        return super.findPage(page, link);
    }

    @Transactional(readOnly = false)
    public void delete(Link link, String status) {
        if (ConfigConstant.AUDITED_STATUS.equals(status)) {
            dao.delete(link);
        } else {
            if (ConfigConstant.DELETED_STATUS.equals(status)) {
                link.setDelFlag(ConfigConstant.AUDITING_STATUS);
            } else {
                link.setDelFlag(ConfigConstant.AUDITED_STATUS);
            }
            dao.updateStatusAsAuditing(link);
        }
    }

    /**
     * 通过编号获取内容标题
     */
    public List<Object[]> findByIds(String ids) {
        List<Object[]> list = Lists.newArrayList();
        String[] idss = StringUtils.split(ids, ",");
        if (idss.length > 0) {
            List<Link> l = dao.findByIdIn(idss);
            for (Link e : l) {
                list.add(new Object[]{e.getId(), StringUtils.abbr(e.getTitle(), 50)});
            }
        }
        return list;
    }

}
