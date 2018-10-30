/**
 * 
 */
package com.fwzs.master.modules.fwzs.dao;

import com.fwzs.master.common.persistence.CrudDao;
import com.fwzs.master.common.persistence.annotation.MyBatisDao;
import com.fwzs.master.modules.fwzs.entity.Dealer;
import com.fwzs.master.modules.sys.entity.Area;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 经销商DAO接口
 * @author Tyler Yin
 * @version 2017-11-14
 */
@MyBatisDao
public interface DealerDao extends CrudDao<Dealer> {
    List<Dealer> findTreeMenuDataListByLevel(final Dealer dealer);
    Dealer findByAccountAndCompany(final Dealer dealer);
    List<Dealer> findListForDealerRole(final Dealer dealer);
    void deleteDealerArea(final Dealer dealer);
    void insertDealerArea(final Dealer dealer);
    List<Area> findAreaByDealer(final Dealer dealer);
    List<Dealer> findDealerByQrcode(@Param("delFlag") String delFlag, @Param("qrCode") String qrCode);
    void deleteUnusedSaleArea(@Param("dealerId") String dealerId, @Param("saleAreas") List<String> saleAreas);
}
