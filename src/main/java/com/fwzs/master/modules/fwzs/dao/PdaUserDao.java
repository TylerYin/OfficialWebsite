package com.fwzs.master.modules.fwzs.dao;

import com.fwzs.master.common.persistence.CrudDao;
import com.fwzs.master.common.persistence.annotation.MyBatisDao;
import com.fwzs.master.modules.fwzs.entity.PdaUser;

import java.util.List;

/**
 * @author Tyler Yin
 * @create 2018-02-01 9:17
 * @description PDA User Dao
 **/
@MyBatisDao
public interface PdaUserDao extends CrudDao<PdaUser> {
    List<PdaUser> getByLoginNameAndCompany(PdaUser pdaUser);
    List<PdaUser> findPdaByUser(PdaUser pdaUser);
    List<PdaUser> findPdaInfor(PdaUser pdaUser);
}