package com.fwzs.master.modules.cms.service;

import com.fwzs.master.modules.cms.constant.ConfigConstant;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fwzs.master.common.persistence.Page;
import com.fwzs.master.common.service.CrudService;
import com.fwzs.master.modules.cms.dao.CommentDao;
import com.fwzs.master.modules.cms.entity.Comment;

/**
 * 评论Service
 *
 * @author ly
 * @version 2013-01-15
 */
@Service
@Transactional(readOnly = true)
public class CommentService extends CrudService<CommentDao, Comment> {
    @Override
    public Page<Comment> findPage(Page<Comment> page, Comment comment) {
        comment.getSqlMap().put("dsf", dataScopeFilter(comment.getCurrentUser(), "o", "u"));
        return super.findPage(page, comment);
    }

    @Transactional(readOnly = false)
    public void delete(Comment comment, String status) {
        if (ConfigConstant.AUDITED_STATUS.equals(status)) {
            dao.delete(comment);
        } else {
            if (ConfigConstant.DELETED_STATUS.equals(status)) {
                comment.setDelFlag(ConfigConstant.AUDITING_STATUS);
            } else {
                comment.setDelFlag(ConfigConstant.AUDITED_STATUS);
            }
            dao.updateStatusAsAuditing(comment);
        }
    }
}
