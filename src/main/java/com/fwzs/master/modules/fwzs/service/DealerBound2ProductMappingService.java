package com.fwzs.master.modules.fwzs.service;

import com.fwzs.master.common.persistence.Page;
import com.fwzs.master.common.service.CrudService;
import com.fwzs.master.modules.fwzs.dao.DealerBound2ProductMappingDao;
import com.fwzs.master.modules.fwzs.entity.DealerBound2ProductMapping;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Tyler Yin
 * @create 2017-11-24 13:38
 * @description DealerBound2ProductMapping Service
 **/
@Service
@Transactional(readOnly = true)
public class DealerBound2ProductMappingService extends CrudService<DealerBound2ProductMappingDao, DealerBound2ProductMapping> {
    public DealerBound2ProductMapping get(String id) {
        return super.get(id);
    }

    public List<DealerBound2ProductMapping> findList(DealerBound2ProductMapping dealerBound2ProductMapping) {
        return super.findList(dealerBound2ProductMapping);
    }

    public Page<DealerBound2ProductMapping> findPage(Page<DealerBound2ProductMapping> page, DealerBound2ProductMapping dealerBound2ProductMapping) {
        dealerBound2ProductMapping.getSqlMap().put("dsf", dataScopeFilter(dealerBound2ProductMapping.getCurrentUser(), "o", "u"));
        return super.findPage(page, dealerBound2ProductMapping);
    }

    @Transactional(readOnly = false)
    public void save(DealerBound2ProductMapping dealerBound2ProductMapping) {
        super.save(dealerBound2ProductMapping);
    }

    /**
     * 更新已出库数量
     *
     * @param dealerBound2ProductMapping
     */
    @Transactional(readOnly = false)
    public void updateRealNumber(DealerBound2ProductMapping dealerBound2ProductMapping){
        this.dao.updateRealNumber(dealerBound2ProductMapping);
    }

    /**
     * 根据出库计划删除其关联产品
     *
     * @param dealerBoundIdList
     */
    public void deleteByDealerBoundIds(List<String> dealerBoundIdList, String delFlag){
        this.dao.deleteByDealerBoundIds(dealerBoundIdList, delFlag);
    }

    /**
     * 根据出库计划查询相关产品
     *
     * @param
     * @return
     */
    public List<DealerBound2ProductMapping> getMappingsByDealerBoundIds(List<String> dealerBoundIdList){
        return this.dao.getMappingsByDealerBoundIds(dealerBoundIdList);
    }

    @Transactional(readOnly = false)
    public void delete(DealerBound2ProductMapping dealerBound2ProductMapping) {
        super.delete(dealerBound2ProductMapping);
    }

    public int findOutBoundBoxCount(DealerBound2ProductMapping dealerBound2ProductMapping) {
        return dao.findOutBoundBoxCount(dealerBound2ProductMapping);
    }

}
