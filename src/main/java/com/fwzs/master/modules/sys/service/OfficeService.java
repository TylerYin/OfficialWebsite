/**
 * 
 */
package com.fwzs.master.modules.sys.service;

import com.fwzs.master.common.config.Global;
import com.fwzs.master.common.service.TreeService;
import com.fwzs.master.modules.sys.dao.OfficeDao;
import com.fwzs.master.modules.sys.entity.Office;
import com.fwzs.master.modules.sys.entity.Role;
import com.fwzs.master.modules.sys.entity.User;
import com.fwzs.master.modules.sys.utils.UserUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 机构Service
 * @author ly
 * @version 2014-05-16
 */
@Service
@Transactional(readOnly = true)
public class OfficeService extends TreeService<OfficeDao, Office> {

    @Autowired
    private SystemService systemService;

	public List<Office> findAll(){
		return UserUtils.getOfficeList();
	}

	public List<Office> findList(Boolean isAll){
		if (isAll != null && isAll){
			return UserUtils.getOfficeAllList();
		}else{
			return UserUtils.getOfficeList();
		}
	}
	
	@Transactional(readOnly = true)
	public List<Office> findList(Office office){
        if (office != null) {
            return dao.findByParentIdsLike(office);
        }
        return new ArrayList<>();
    }
	
	@Transactional(readOnly = false)
	public void save(Office office) {
        if (StringUtils.isNotBlank(office.getSummary())) {
            office.setSummary(StringEscapeUtils.unescapeHtml4(office.getSummary()));
        }

		super.save(office);
		UserUtils.removeCache(UserUtils.CACHE_OFFICE_LIST);
	}

	@Transactional(readOnly = false)
	public void delete(Office office) {
		super.delete(office);

        //删除公司或者机构下属的所有用户
        if (Global.OFFICE_TYPE_COMPANY.equals(office.getType())) {
            User user = new User();
            user.setCompany(office);
            systemService.deleteUserByCompany(user);

            //删除公司下属的角色
            Role role = new Role();
            role.setOffice(office);
            systemService.deleteRoleByCompany(role);
            UserUtils.removeCache(UserUtils.CACHE_ROLE_LIST);
        } else {
            List<User> users = systemService.findUserByOfficeId(office.getId());
            for (User user : users) {
                systemService.deleteUser(user);
            }
        }

        UserUtils.removeCache(UserUtils.CACHE_OFFICE_LIST);
    }
}
