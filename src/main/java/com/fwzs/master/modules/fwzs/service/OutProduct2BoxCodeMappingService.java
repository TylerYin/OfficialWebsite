package com.fwzs.master.modules.fwzs.service;

import com.fwzs.master.common.config.Global;
import com.fwzs.master.common.persistence.Page;
import com.fwzs.master.common.service.CrudService;
import com.fwzs.master.common.utils.CaculateStockUtils;
import com.fwzs.master.modules.fwzs.dao.OutProduct2BoxCodeMappingDao;
import com.fwzs.master.modules.fwzs.entity.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author Tyler Yin
 * @create 2017-11-24 13:38
 * @description OutProduct2BoxCodeMapping Service
 **/
@Service
@Transactional(readOnly = true)
public class OutProduct2BoxCodeMappingService extends CrudService<OutProduct2BoxCodeMappingDao, OutProduct2BoxCodeMapping> {

    private final static Logger logger = LoggerFactory.getLogger(OutProduct2BoxCodeMappingService.class);

    @Autowired
    private RealtimeStockLevelService realtimeStockLevelService;

    @Autowired
    private OutBound2ProductMappingService outBound2ProductMappingService;

    public OutProduct2BoxCodeMapping get(String id) {
        return super.get(id);
    }

    public List<OutProduct2BoxCodeMapping> findList(OutProduct2BoxCodeMapping outProduct2BoxCodeMapping) {
        return super.findList(outProduct2BoxCodeMapping);
    }

    public Page<OutProduct2BoxCodeMapping> findPage(Page<OutProduct2BoxCodeMapping> page, OutProduct2BoxCodeMapping outProduct2BoxCodeMapping) {
        outProduct2BoxCodeMapping.getSqlMap().put("dsf", dataScopeFilter(outProduct2BoxCodeMapping.getCurrentUser(), "o", "u"));
        return super.findPage(page, outProduct2BoxCodeMapping);
    }

    /**
     * 保存从PAD传递过来的箱码等相关数据
     *
     * @param outBound2ProductId
     * @param boxCodeList
     * @param boxCodeType 0表示产品码，1表示箱码，2表示垛码
     * @param scanDate
     */
    @Transactional(readOnly = false)
    //TODO
    //1.传入过来的码需要检验，但是如果对于传入过来的数据量比较大，逐条从数据库中检验是不合适的，所以最好的办法是在码中约定几位用来做校验
    //2.后续如果数据量比较大的话，会改成批量插入的方式
    public PdaBound saveBoxCode(final String outBound2ProductId, final List<String> boxCodeList, final String boxCodeType, final Date scanDate) {
        PdaBound pdaBound = updateStock(outBound2ProductId, boxCodeList, boxCodeType);
        if (Global.BOUND_SUCCESS.equals(pdaBound.getStatus())) {
            for (String boxCode : boxCodeList) {
                OutProduct2BoxCodeMapping outProduct2BoxCodeMapping = new OutProduct2BoxCodeMapping();
                outProduct2BoxCodeMapping.setBoxCode(boxCode);
                outProduct2BoxCodeMapping.setScanDate(scanDate);
                outProduct2BoxCodeMapping.setBoxCodeType(boxCodeType);
                outProduct2BoxCodeMapping.setOutBound2ProductId(outBound2ProductId);
                save(outProduct2BoxCodeMapping);
            }
        }
        return pdaBound;
    }

    /**
     * 更新实时库存记录
     * 先查找库中有无记录
     *
     * @param outBound2ProductId
     * @param boxCodeList
     */
    private PdaBound updateStock(final String outBound2ProductId, final List<String> boxCodeList, final String boxCodeType) {
        PdaBound pdaBound = new PdaBound();
        pdaBound.setStatus(Global.BOUND_SUCCESS);
        final OutBound outBound = this.dao.getOutBound2Product(outBound2ProductId);
        if (null != outBound && null != outBound.getBsProduct() && null != outBound.getBsProduct().getWarehouse()) {
            final String packRate = outBound.getBsProduct().getPackRate();
            if (CaculateStockUtils.checkPackRateAndBoxCodeTypeIsValid(packRate, boxCodeType)) {
                int stock = CaculateStockUtils.caculateSendAmunts(packRate, boxCodeList.size(), boxCodeType);
                updateRealNumber(outBound2ProductId, stock);
            } else {
                pdaBound.setStatus(Global.BOUND_INVALIDPACKAGERATE);
                logger.error("无效的产品包装比例！");
            }
        } else {
            pdaBound.setStatus(Global.BOUND_PLANNOTEXIST);
            logger.error("企业出库计划不存在！");
        }
        return pdaBound;
    }

    /**
     * 更新实际出库数量
     *
     * @param outBound2ProductId
     * @param stock
     */
    private void updateRealNumber(final String outBound2ProductId, int stock) {
        OutBound2ProductMapping outBound2ProductMapping = new OutBound2ProductMapping();
        outBound2ProductMapping.setId(outBound2ProductId);
        outBound2ProductMapping = outBound2ProductMappingService.get(outBound2ProductMapping);
        if (null != outBound2ProductMapping) {
            outBound2ProductMapping.setRealNumber(outBound2ProductMapping.getRealNumber() + stock);
            outBound2ProductMappingService.updateRealNumber(outBound2ProductMapping);
        }
    }

    @Transactional(readOnly = false)
    public void save(OutProduct2BoxCodeMapping outProduct2BoxCodeMapping) {
        super.save(outProduct2BoxCodeMapping);
    }

    @Transactional(readOnly = false)
    public void delete(OutProduct2BoxCodeMapping outProduct2BoxCodeMapping) {
        super.delete(outProduct2BoxCodeMapping);
    }

    /**
     * 根据出库计划和产品的关联关系表删除
     *
     * @param outProduct2BoxCodeMapping
     */
    @Transactional(readOnly = false)
    public void deleteByOutBound2ProductMappingId(OutProduct2BoxCodeMapping outProduct2BoxCodeMapping) {
        this.dao.deleteByOutBound2ProductMappingId(outProduct2BoxCodeMapping);
    }

    /**
     * 根据出库计划和产品关联关系表的主键查询箱码集合
     *
     * @param outBound2ProductId
     * @return
     */
    public int getCountByOutBound2ProductId(final String outBound2ProductId){
        return this.dao.getCountByOutBound2ProductId(outBound2ProductId);
    }
}