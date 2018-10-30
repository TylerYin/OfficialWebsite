package com.fwzs.master.modules.fwzs.service;

import com.fwzs.master.common.config.Global;
import com.fwzs.master.common.persistence.Page;
import com.fwzs.master.common.service.CrudService;
import com.fwzs.master.common.utils.CaculateStockUtils;
import com.fwzs.master.modules.fwzs.dao.DealerProduct2BoxCodeMappingDao;
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
 * @description DealerProduct2BoxCodeMapping Service
 **/
@Service
@Transactional(readOnly = true)
public class DealerBoundProduct2BoxCodeMappingService extends CrudService<DealerProduct2BoxCodeMappingDao, DealerProduct2BoxCodeMapping> {

    private final static Logger logger = LoggerFactory.getLogger(DealerBoundProduct2BoxCodeMappingService.class);

    @Autowired
    private DealerBound2ProductMappingService dealerBound2ProductMappingService;

    @Autowired
    private RealtimeStockLevelService realtimeStockLevelService;

    public DealerProduct2BoxCodeMapping get(String id) {
        return super.get(id);
    }

    public List<DealerProduct2BoxCodeMapping> findList(DealerProduct2BoxCodeMapping dealerProduct2BoxCodeMapping) {
        return super.findList(dealerProduct2BoxCodeMapping);
    }

    public Page<DealerProduct2BoxCodeMapping> findPage(Page<DealerProduct2BoxCodeMapping> page, DealerProduct2BoxCodeMapping dealerProduct2BoxCodeMapping) {
        dealerProduct2BoxCodeMapping.getSqlMap().put("dsf", dataScopeFilter(dealerProduct2BoxCodeMapping.getCurrentUser(), "o", "u"));
        return super.findPage(page, dealerProduct2BoxCodeMapping);
    }

    /**
     * 保存从PAD传递过来的箱码等相关数据
     *
     * @param dealerBound2ProductId
     * @param boxCodeList
     * @param boxCodeType 0表示产品码，1表示箱码，2表示垛码
     * @param scanDate
     */
    //TODO
    //1.传入过来的码需要检验，但是如果对于传入过来的数据量比较大，逐条从数据库中检验是不合适的，所以最好的办法是在码中约定几位用来做校验
    //2.后续如果数据量比较大的话，会改成批量插入的方式
    @Transactional(readOnly = false)
    public PdaBound saveBoxCode(final String dealerBound2ProductId, final List<String> boxCodeList,
                                final String boxCodeType, final Date scanDate) {
        PdaBound pdaBound = updateStock(dealerBound2ProductId, boxCodeList, boxCodeType);
        if (Global.BOUND_SUCCESS.equals(pdaBound.getStatus())) {
            for (String boxCode : boxCodeList) {
                DealerProduct2BoxCodeMapping dealerProduct2BoxCodeMapping = new DealerProduct2BoxCodeMapping();
                dealerProduct2BoxCodeMapping.setBoxCode(boxCode);
                dealerProduct2BoxCodeMapping.setScanDate(scanDate);
                dealerProduct2BoxCodeMapping.setBoxCodeType(boxCodeType);
                dealerProduct2BoxCodeMapping.setDealerBound2ProductId(dealerBound2ProductId);
                save(dealerProduct2BoxCodeMapping);
            }
        }
        return pdaBound;
    }

    /**
     * 更新实时库存记录
     * 先查找库中有无记录
     *
     * @param dealerBound2ProductId
     * @param boxCodeList
     */
    private PdaBound updateStock(final String dealerBound2ProductId, final List<String> boxCodeList, final String boxCodeType) {
        PdaBound pdaBound = new PdaBound();
        pdaBound.setStatus(Global.BOUND_SUCCESS);
        final DealerBound dealerBound = this.dao.getDealerBound2Product(dealerBound2ProductId);
        if (null != dealerBound && null != dealerBound.getBsProduct() && null != dealerBound.getOutDealer()) {
            final String packRate = dealerBound.getBsProduct().getPackRate();
            if (CaculateStockUtils.checkPackRateAndBoxCodeTypeIsValid(packRate, boxCodeType)) {
                int stock = CaculateStockUtils.caculateSendAmunts(packRate, boxCodeList.size(), boxCodeType);
                updateRealNumber(dealerBound2ProductId, stock);
            } else {
                pdaBound.setStatus(Global.BOUND_INVALIDPACKAGERATE);
                logger.error("无效的产品包装比例!");
            }
        } else {
            pdaBound.setStatus(Global.BOUND_PLANNOTEXIST);
            logger.error("经销商出库计划不存在!");
        }
        return pdaBound;
    }

    /**
     * 更新实际出库数量
     *
     * @param dealerBound2ProductId
     * @param stock
     */
    private void updateRealNumber(final String dealerBound2ProductId, int stock) {
        DealerBound2ProductMapping dealerBound2ProductMapping = new DealerBound2ProductMapping();
        dealerBound2ProductMapping.setId(dealerBound2ProductId);
        dealerBound2ProductMapping = dealerBound2ProductMappingService.get(dealerBound2ProductMapping);
        if (null != dealerBound2ProductMapping) {
            dealerBound2ProductMapping.setRealNumber(dealerBound2ProductMapping.getRealNumber() + stock);
            dealerBound2ProductMappingService.updateRealNumber(dealerBound2ProductMapping);
        }
    }

    @Transactional(readOnly = false)
    public void save(DealerProduct2BoxCodeMapping dealerProduct2BoxCodeMapping) {
        super.save(dealerProduct2BoxCodeMapping);
    }

    @Transactional(readOnly = false)
    public void delete(DealerProduct2BoxCodeMapping dealerProduct2BoxCodeMapping) {
        super.delete(dealerProduct2BoxCodeMapping);
    }

    /**
     * 根据出库计划和产品的关联关系表删除
     *
     * @param dealerProduct2BoxCodeMapping
     */
    @Transactional(readOnly = false)
    public void deleteByDealerBound2ProductMappingId(DealerProduct2BoxCodeMapping dealerProduct2BoxCodeMapping) {
        this.dao.deleteByDealerBound2ProductMappingId(dealerProduct2BoxCodeMapping);
    }
}