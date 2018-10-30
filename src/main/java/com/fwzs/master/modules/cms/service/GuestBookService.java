package com.fwzs.master.modules.cms.service;

import com.fwzs.master.modules.cms.constant.ConfigConstant;
import com.fwzs.master.modules.cms.entity.GuestBook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fwzs.master.common.persistence.Page;
import com.fwzs.master.common.service.CrudService;
import com.fwzs.master.modules.cms.dao.GuestBookDao;

/**
 * 留言Service
 *
 * @author ly
 * @version 2013-01-15
 */
@Service
@Transactional(readOnly = true)
public class GuestBookService extends CrudService<GuestBookDao, GuestBook> {
    @Override
    public GuestBook get(String id) {
        return dao.get(id);
    }

    @Override
    public Page<GuestBook> findPage(Page<GuestBook> page, GuestBook guestBook) {
        guestBook.getSqlMap().put("dsf", dataScopeFilter(guestBook.getCurrentUser(), "o", "u"));
        guestBook.setPage(page);
        page.setList(dao.findList(guestBook));
        return page;
    }

    @Transactional(readOnly = false)
    public void delete(GuestBook guestBook, String status) {
        if (ConfigConstant.AUDITED_STATUS.equals(status)) {
            dao.delete(guestBook);
        } else {
            if (ConfigConstant.DELETED_STATUS.equals(status)) {
                guestBook.setDelFlag(ConfigConstant.AUDITING_STATUS);
            } else {
                guestBook.setDelFlag(ConfigConstant.AUDITED_STATUS);
            }
            dao.updateStatusAsAuditing(guestBook);
        }
    }

    /**
     * 更新索引
     */
    public void createIndex() {
        //dao.createIndex();
    }

    /**
     * 全文检索
     */
    public Page<GuestBook> search(Page<GuestBook> page, String q, String beginDate, String endDate) {
        // 设置查询条件
//		BooleanQuery query = dao.getFullTextQuery(q, "name","content","reContent");
//		
//		// 设置过滤条件
//		List<BooleanClause> bcList = Lists.newArrayList();
//
//		bcList.add(new BooleanClause(new TermQuery(new Term(GuestBook.FIELD_DEL_FLAG, GuestBook.DEL_FLAG_NORMAL)), Occur.MUST));
//		
//		if (StringUtils.isNotBlank(beginDate) && StringUtils.isNotBlank(endDate)) {   
//			bcList.add(new BooleanClause(new TermRangeQuery("createDate", beginDate.replaceAll("-", ""),
//					endDate.replaceAll("-", ""), true, true), Occur.MUST));
//		}
//
//		bcList.add(new BooleanClause(new TermQuery(new Term("type", "1")), Occur.SHOULD));
//		bcList.add(new BooleanClause(new TermQuery(new Term("type", "2")), Occur.SHOULD));
//		bcList.add(new BooleanClause(new TermQuery(new Term("type", "3")), Occur.SHOULD));
//		bcList.add(new BooleanClause(new TermQuery(new Term("type", "4")), Occur.SHOULD));
//		
//		BooleanQuery queryFilter = dao.getFullTextQuery((BooleanClause[])bcList.toArray(new BooleanClause[bcList.size()]));
//
//		System.out.println(queryFilter);
//		
//		// 设置排序（默认相识度排序）
//		Sort sort = null;//new Sort(new SortField("updateDate", SortField.DOC, true));
//		// 全文检索
//		dao.search(page, query, queryFilter, sort);
//		// 关键字高亮
//		dao.keywordsHighlight(query, page.getList(), 30, "name");
//		dao.keywordsHighlight(query, page.getList(), 1300, "content");
//		dao.keywordsHighlight(query, page.getList(), 1300, "reContent");

        return page;
    }
}
