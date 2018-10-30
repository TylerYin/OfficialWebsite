/**
 * 
 */
package com.fwzs.master.modules.fwzs.dao;

import com.fwzs.master.common.persistence.CrudDao;
import com.fwzs.master.common.persistence.annotation.MyBatisDao;
import com.fwzs.master.modules.fwzs.entity.BsProduct;
import com.fwzs.master.modules.fwzs.entity.FwmQrcode;
import com.fwzs.master.modules.sys.entity.Office;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 防伪码DAO接口
 * @author yjd
 * @version 2017-10-08
 */
@MyBatisDao
public interface FwmQrcodeDao extends CrudDao<FwmQrcode> {
	void saveByBatch(@Param("fwmQrcodes") List<FwmQrcode> fwmQrcodes);
	int updateStatus(@Param("type") Integer type, @Param("id") String id);
    int updatePlanByFileId(@Param("planId") String planId, @Param("fileId") String fileId);
	int updateSelectNum(@Param("qrCode") String qrCode);
	FwmQrcode getByQrcode(@Param("qrCode") String qrCode);
    int findQrCodeCount(FwmQrcode fwmQrcode);
    Office getOfficeInfoByQrcode(@Param("qrCode") String qrCode);
    BsProduct getBsProductExtendAttributes(@Param("qrCode") String qrCode);
}