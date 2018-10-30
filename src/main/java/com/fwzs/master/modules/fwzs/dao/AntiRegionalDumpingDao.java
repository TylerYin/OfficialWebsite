package com.fwzs.master.modules.fwzs.dao;

import com.fwzs.master.common.persistence.CrudDao;
import com.fwzs.master.common.persistence.annotation.MyBatisDao;
import com.fwzs.master.modules.fwzs.entity.AntiRegionalDumping;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Tyler Yin
 * @create 2018-03-13 9:44
 * @description AntiRegionalDumping dao
 **/
@MyBatisDao
public interface AntiRegionalDumpingDao extends CrudDao<AntiRegionalDumping> {
    List<AntiRegionalDumping> findDetailList(AntiRegionalDumping antiRegionalDumping);
    int getAntiRegionlByQrCode(@Param("qrCode") String qrCode, @Param("dealerId") String dealerId);
}