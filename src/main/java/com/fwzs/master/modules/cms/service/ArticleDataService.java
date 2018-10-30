package com.fwzs.master.modules.cms.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fwzs.master.common.service.CrudService;
import com.fwzs.master.modules.cms.dao.ArticleDataDao;
import com.fwzs.master.modules.cms.entity.ArticleData;

/**
 * 站点Service
 *
 * @author ly
 * @version 2013-01-15
 */
@Service
@Transactional(readOnly = true)
public class ArticleDataService extends CrudService<ArticleDataDao, ArticleData> {

}
