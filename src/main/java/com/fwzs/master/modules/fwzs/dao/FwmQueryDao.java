/**
 * 
 */
package com.fwzs.master.modules.fwzs.dao;

import com.fwzs.master.common.persistence.CrudDao;
import com.fwzs.master.common.persistence.annotation.MyBatisDao;
import com.fwzs.master.modules.fwzs.entity.FwmQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 防伪码信息查询DAO接口
 * @author Tyler Yin
 * @version 2017-11-07
 */
@MyBatisDao
public interface FwmQueryDao extends CrudDao<FwmQuery> {
	List<FwmQuery> getFwmInfoByQrCode(@Param("qrCode") String qrCode, @Param("user") String user, @Param("delFlag") String delFlag);
	List<FwmQuery> getFwmInfo(@Param("qrCode") String qrCode, @Param("delFlag") String delFlag);
}