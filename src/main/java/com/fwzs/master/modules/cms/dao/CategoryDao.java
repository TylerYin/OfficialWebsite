package com.fwzs.master.modules.cms.dao;

import java.util.List;
import java.util.Map;

import com.fwzs.master.common.persistence.TreeDao;
import com.fwzs.master.common.persistence.annotation.MyBatisDao;
import com.fwzs.master.modules.cms.entity.Category;

/**
 * 栏目DAO接口
 * @author ly
 * @version 2013-8-23
 */
@MyBatisDao
public interface CategoryDao extends TreeDao<Category> {

    /**
     * 查找栏目
     * @param category
     * @return
     */
    List<Category> findModule(Category category);
	
//	public List<Category> findByParentIdsLike(Category category);
//	{
//		return find("from Category where parentIds like :p1", new Parameter(parentIds));
//	}

    /**
     * 查找栏目
     * @param module
     * @return
     */
	List<Category> findByModule(String module);
//	{
//		return find("from Category where delFlag=:p1 and (module='' or module=:p2) order by site.id, sort", 
//				new Parameter(Category.DEL_FLAG_NORMAL, module));
//	}

    /**
     * 查找栏目
     * @param parentId
     * @param isMenu
     * @return
     */
	List<Category> findByParentId(String parentId, String isMenu);
//	{
//		return find("from Category where delFlag=:p1 and parent.id=:p2 and inMenu=:p3 order by site.id, sort", 
//				new Parameter(Category.DEL_FLAG_NORMAL, parentId, isMenu));
//	}

    /**
     * 根据父节点和站点ID查找栏目
     * @param entity
     * @return
     */
	List<Category> findByParentIdAndSiteId(Category entity);

    /**
     * 查找统计数据
     * @param sql
     * @return
     */
	List<Map<String, Object>> findStats(String sql);
//	{
//		return find("from Category where delFlag=:p1 and parent.id=:p2 and site.id=:p3 order by site.id, sort", 
//				new Parameter(Category.DEL_FLAG_NORMAL, parentId, siteId));
//	}
	
	//List<Category> findByIdIn(String[] ids);
//	{
//		return find("from Category where id in (:p1)", new Parameter(new Object[]{ids}));
//	}
	//List<Category> find(Category category);

//	@Query("select distinct c from Category c, Role r, User u where c in elements (r.categoryList) and r in elements (u.roleList)" +
//			" and c.delFlag='" + Category.DEL_FLAG_NORMAL + "' and r.delFlag='" + Role.DEL_FLAG_NORMAL + 
//			"' and u.delFlag='" + User.DEL_FLAG_NORMAL + "' and u.id=?1 or (c.user.id=?1 and c.delFlag='" + Category.DEL_FLAG_NORMAL +
//			"') order by c.site.id, c.sort")
//List<Category> findByUserId(Long userId);
	
}
