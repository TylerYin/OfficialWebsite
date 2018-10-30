/**
 * 
 */
package com.fwzs.master.modules.fwzs.service;

import com.fwzs.master.common.persistence.Page;
import com.fwzs.master.common.service.CrudService;
import com.fwzs.master.modules.fwzs.dao.ScLinesDao;
import com.fwzs.master.modules.fwzs.entity.ScLines;
import com.fwzs.master.modules.sys.utils.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

/**
 * 生产线Service
 * @author ly
 * @version 2017-09-30
 */
@Service
@Transactional(readOnly = true)
public class ScLinesService extends CrudService<ScLinesDao, ScLines> {

	public ScLines get(String id) {
		return super.get(id);
	}
	
	public List<ScLines> findList(ScLines scLines) {
		scLines.getSqlMap().put("dsf", dataScopeFilter(scLines.getCurrentUser(), "o", "u"));
		return super.findList(scLines);
	}

    /**
     * 查询当前用户所属公司所拥有的所有生产线
     *
     * @param scLines
     * @return
     */
    public List<ScLines> findListForScPlan(ScLines scLines) {
        scLines.setCreateBy(UserUtils.getUser());
        return this.dao.findListForScPlan(scLines);
    }
	
	public Page<ScLines> findPage(Page<ScLines> page, ScLines scLines) {
		scLines.getSqlMap().put("dsf", dataScopeFilter(scLines.getCurrentUser(), "o", "u"));
		return super.findPage(page, scLines);
	}
	
	public int getRowCountByLineNo(final HashMap searchCondition){
		return super.dao.getRowCountByLineNo(searchCondition);
	}
	
	@Transactional(readOnly = false)
	public void save(ScLines scLines) {
		super.save(scLines);
	}
	
	@Transactional(readOnly = false)
	public void delete(ScLines scLines) {
		super.delete(scLines);
	}
	
}