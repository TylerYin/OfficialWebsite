package com.fwzs.master.modules.fwzs.service;

import com.fwzs.master.common.persistence.Page;
import com.fwzs.master.common.service.CrudService;
import com.fwzs.master.modules.fwzs.dao.OutBound2ProductMappingDao;
import com.fwzs.master.modules.fwzs.entity.OutBound2ProductMapping;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Tyler Yin
 * @create 2017-11-24 13:38
 * @description OutBound2ProductMapping Service
 **/
@Service
@Transactional(readOnly = true)
public class OutBound2ProductMappingService extends CrudService<OutBound2ProductMappingDao, OutBound2ProductMapping> {
    public OutBound2ProductMapping get(String id) {
        return super.get(id);
    }

    public List<OutBound2ProductMapping> findList(OutBound2ProductMapping outBound2ProductMapping) {
        return super.findList(outBound2ProductMapping);
    }

    public Page<OutBound2ProductMapping> findPage(Page<OutBound2ProductMapping> page, OutBound2ProductMapping outBound2ProductMapping) {
        outBound2ProductMapping.getSqlMap().put("dsf", dataScopeFilter(outBound2ProductMapping.getCurrentUser(), "o", "u"));
        return super.findPage(page, outBound2ProductMapping);
    }

    @Transactional(readOnly = false)
    public void save(OutBound2ProductMapping outBound2ProductMapping) {
        super.save(outBound2ProductMapping);
    }

    /**
     * 更新已出库数量
     *
     * @param outBound2ProductMapping
     */
    @Transactional(readOnly = false)
    public void updateRealNumber(OutBound2ProductMapping outBound2ProductMapping){
        this.dao.updateRealNumber(outBound2ProductMapping);
    }

    /**
     * 根据出库计划删除其关联产品
     *
     * @param outBoundIdList
     */
    public void deleteByOutBoundIds(List<String> outBoundIdList, String delFlag){
        this.dao.deleteByOutBoundIds(outBoundIdList, delFlag);
    }

    /**
     * 根据出库计划查询相关产品
     *
     * @param outBoundIdList
     * @return
     */
    public List<OutBound2ProductMapping> getMappingsByOutBoundIds(List<String> outBoundIdList){
        return this.dao.getMappingsByOutBoundIds(outBoundIdList);
    }

    @Transactional(readOnly = false)
    public void delete(OutBound2ProductMapping outBound2ProductMapping) {
        super.delete(outBound2ProductMapping);
    }

    /**
     * 查询已经出库的箱码
     *
     * @param outBound2ProductMapping
     * @return
     */
    public int findOutBoundBoxCount(OutBound2ProductMapping outBound2ProductMapping) {
        return dao.findOutBoundBoxCount(outBound2ProductMapping);
    }

}
