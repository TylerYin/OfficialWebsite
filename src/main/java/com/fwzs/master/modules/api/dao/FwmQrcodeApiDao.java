/**
 * 
 */
package com.fwzs.master.modules.api.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.fwzs.master.common.persistence.CrudDao;
import com.fwzs.master.common.persistence.annotation.MyBatisDao;
import com.fwzs.master.modules.api.entity.FwmQrcodeVo;
import com.fwzs.master.modules.fwzs.entity.FwmQrcode;

/**
 * 防伪码DAO接口
 * @author yjd
 * @version 2017-10-08
 */
@MyBatisDao
public interface FwmQrcodeApiDao extends CrudDao<FwmQrcodeVo> {
	void updateByBatch(@Param("fwmQrcodeVos") List<FwmQrcodeVo> fwmQrcodeVos);
}