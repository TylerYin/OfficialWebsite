package com.fwzs.master.modules.fwzs.dao;

import com.fwzs.master.common.persistence.CrudDao;
import com.fwzs.master.common.persistence.annotation.MyBatisDao;
import com.fwzs.master.modules.fwzs.entity.OutBound;
import com.fwzs.master.modules.fwzs.entity.Qrcode2BoxcodeMapping;

import java.util.List;

/**
 * @author Tyler Yin
 * @create 2017-11-22 10:55
 * @description 产品出库Dao
 **/
@MyBatisDao
public interface OutBoundDao extends CrudDao<OutBound> {
    List<Qrcode2BoxcodeMapping> findQrCodeById(final Qrcode2BoxcodeMapping qrcode2BoxcodeMapping);
    void updateStatus(OutBound outBound);
    List<OutBound> findOutBoundsForPDA(OutBound outBound);
}