package com.fwzs.master.modules.fwzs.dao;

import com.fwzs.master.common.persistence.CrudDao;
import com.fwzs.master.common.persistence.annotation.MyBatisDao;
import com.fwzs.master.modules.fwzs.entity.DealerBound;
import com.fwzs.master.modules.fwzs.entity.DealerInBound;
import com.fwzs.master.modules.fwzs.entity.Qrcode2BoxcodeMapping;

import java.util.List;

/**
 * @author Tyler Yin
 * @create 2017-11-22 10:55
 * @description 产品出库Dao
 **/
@MyBatisDao
public interface DealerBoundDao extends CrudDao<DealerBound> {
    List<Qrcode2BoxcodeMapping> findQrCodeById(final Qrcode2BoxcodeMapping qrcode2BoxcodeMapping);
    List<DealerInBound> findInBoundList(final DealerInBound dealerInBound);
    void updateStatus(DealerBound dealerBound);
    List<DealerBound> findDealerBoundsForPDA(DealerBound dealerBound);
}