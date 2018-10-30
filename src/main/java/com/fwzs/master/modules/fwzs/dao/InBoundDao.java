package com.fwzs.master.modules.fwzs.dao;

import com.fwzs.master.common.persistence.CrudDao;
import com.fwzs.master.common.persistence.annotation.MyBatisDao;
import com.fwzs.master.modules.fwzs.entity.InBound;
import com.fwzs.master.modules.fwzs.entity.Qrcode2BoxcodeMapping;

import java.util.List;

/**
 * @author Tyler Yin
 * @create 2017-11-22 10:55
 * @description 产品入库Dao
 **/
@MyBatisDao
public interface InBoundDao extends CrudDao<InBound> {
    List<Qrcode2BoxcodeMapping> findQrCodeById(final Qrcode2BoxcodeMapping qrcode2BoxcodeMapping);
    void updateStatus(InBound inBound);
}