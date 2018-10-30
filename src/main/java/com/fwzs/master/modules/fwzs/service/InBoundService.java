package com.fwzs.master.modules.fwzs.service;

import com.fwzs.master.common.config.Global;
import com.fwzs.master.common.persistence.Page;
import com.fwzs.master.common.service.CrudService;
import com.fwzs.master.common.utils.DateUtils;
import com.fwzs.master.common.utils.StringUtils;
import com.fwzs.master.modules.fwzs.dao.InBoundDao;
import com.fwzs.master.modules.fwzs.entity.*;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Tyler Yin
 * @create 2017-11-29 14:22
 * @description 入库Service
 **/
@Service
@Transactional(readOnly = true)
public class InBoundService extends CrudService<InBoundDao, InBound> {

    @Autowired
    private ScPlanService scPlanService;

    @Autowired
    private BsProductService bsProductService;

    @Autowired
    private WarehouseService warehouseService;

    @Autowired
    private RealtimeStockLevelService realtimeStockLevelService;

    public InBound get(String id) {
        return super.get(id);
    }

    public List<InBound> findList(InBound inBound) {
        inBound.getSqlMap().put("dsf", dataScopeFilter(inBound.getCurrentUser(), "o", "u"));
        return super.findList(inBound);
    }

    public Page<InBound> findPage(Page<InBound> page, InBound inBound) {
        inBound.getSqlMap().put("dsf", dataScopeFilter(inBound.getCurrentUser(), "o", "u"));

        if (inBound.getBeginDate() == null){
            inBound.setBeginDate(DateUtils.setDays(DateUtils.parseDate(DateUtils.getDate()), 1));
        }
        if (inBound.getEndDate() == null){
            inBound.setEndDate(DateUtils.addMonths(inBound.getBeginDate(), 1));
        }

       Page<InBound> result = super.findPage(page, inBound);
       organizeInBoundData(result);
       return result;
    }

    /**
     * 按照生产计划任务号重新组织数据
     *
     * @param result
     */
    private void organizeInBoundData(Page<InBound> result) {
        final List<InBound> inBounds = result.getList();
        final Set<String> planNos = new HashSet<>();
        if (CollectionUtils.isNotEmpty(inBounds)) {
            for (InBound bound : inBounds) {
                if (null != bound.getScPlan()) {
                    if (planNos.contains(bound.getScPlan().getPlanNo())) {
                        bound.setMergeScPlanRowCount(0);
                        continue;
                    }

                    int mergeRowCount = 0;
                    for (InBound inBound : inBounds) {
                        if (null != inBound.getScPlan()
                                && inBound.getScPlan().getPlanNo().equals(bound.getScPlan().getPlanNo())) {
                            mergeRowCount++;
                        }
                    }

                    planNos.add(bound.getScPlan().getPlanNo());
                    bound.setMergeScPlanRowCount(mergeRowCount);
                }
            }
        }
    }

    /**
     * 根据入库查询防伪码
     *
     * @param page
     * @param qrcode2BoxcodeMapping
     * @return
     */
    public Page<Qrcode2BoxcodeMapping> findBoxcodePageByInBoundId(Page<Qrcode2BoxcodeMapping> page, Qrcode2BoxcodeMapping qrcode2BoxcodeMapping) {
        qrcode2BoxcodeMapping.setPage(page);
        page.setList(dao.findQrCodeById(qrcode2BoxcodeMapping));
        return page;
    }

    /**
     * 获取导出Excel需要的数据集
     *
     * @param inBound
     * @return
     */
    public List<Qrcode2BoxcodeMapping> findBoxcodePageByInBoundId(final InBound inBound) {
        final Qrcode2BoxcodeMapping qrcode2BoxcodeMapping = new Qrcode2BoxcodeMapping();
        if (null != inBound.getScPlan() && StringUtils.isNotBlank(inBound.getScPlan().getId())) {
            qrcode2BoxcodeMapping.setPlanId(inBound.getScPlan().getId());
        }

        if (null != inBound.getBsProduct() && StringUtils.isNotBlank(inBound.getBsProduct().getId())) {
            qrcode2BoxcodeMapping.setProdId(inBound.getBsProduct().getId());
        }
        return dao.findQrCodeById(qrcode2BoxcodeMapping);
    }

    @Transactional(readOnly = false)
    public void delete(InBound inBound) {
        super.delete(inBound);
    }

    /**
     * PDA调用该接口，保存入库相关数据
     *
     * @param userId
     * @param planId
     * @param inBoundNumber
     * @return
     */
    @Transactional(readOnly = false)
    public PdaBound saveInBoundData(final String userId, final String planId, int inBoundNumber) {
        PdaBound pdaBound = isParameterValid(userId, planId, inBoundNumber);
        if (StringUtils.isBlank(pdaBound.getStatus())) {
            InBound inBound;
            ScPlan plan = scPlanService.get(planId);
            int boundNumber = inBoundNumber / plan.getBsProductList().size();
            for (BsProduct product : plan.getBsProductList()) {
                inBound = new InBound();
                inBound.setOperator(userId);
                inBound.setScPlan(plan);
                inBound.setBsProduct(product);
                inBound.setWarehouse(product.getWarehouse());
                inBound.setInBoundNumber(boundNumber);
                inBound.setInBoundDate(new Date());

                save(inBound);
                updateStock(product.getId(), product.getWarehouse().getId(), boundNumber);
            }
            pdaBound.setStatus(Global.INBOUND_SUCCESS);
        }
        return pdaBound;
    }

    /**
     * 检查PDA传入的参数是否合法
     *
     * @param planId
     * @param inBoundNumber
     * @return
     */
    private PdaBound isParameterValid(final String userId, final String planId, int inBoundNumber) {
        PdaBound pdaBound = new PdaBound();
        if (StringUtils.isNotBlank(userId) && StringUtils.isNotBlank(planId) && inBoundNumber > 0) {
            ScPlan scPlan = scPlanService.get(planId);
            if (null == scPlan || StringUtils.isBlank(scPlan.getId())
                    && CollectionUtils.isEmpty(scPlan.getBsProductList())) {
                pdaBound.setStatus(Global.BOUND_PLANNOTEXIST);
            }
        } else if (StringUtils.isBlank(userId)) {
            pdaBound.setStatus(Global.BOUND_INVALID_PERSON);
        } else if (StringUtils.isBlank(planId)) {
            pdaBound.setStatus(Global.BOUND_PLANNOTEXIST);
        } else {
            pdaBound.setStatus(Global.BOUND_INVALID_NUMBER);
        }
        return pdaBound;
    }

    @Transactional(readOnly = false)
    public void save(InBound inBound) {
        super.save(inBound);
    }

    /**
     * 根据PDA传递过来的入库数据更新实时库存表的库存记录
     *
     * @param prodId
     * @param warehouseId
     * @param inBoundNumber
     * @return
     */
    public void updateStock(final String prodId, final String warehouseId, int inBoundNumber) {
        RealtimeStockLevel realtimeStock = new RealtimeStockLevel();
        BsProduct bsProduct = bsProductService.get(prodId);
        realtimeStock.setBsProduct(bsProduct);

        Warehouse warehouse = new Warehouse();
        warehouse.setId(warehouseId);
        realtimeStock.setWarehouse(warehouse);
        realtimeStock = realtimeStockLevelService.findStock(realtimeStock);

        if (null != realtimeStock) {
            //更新已经有的库存记录
            realtimeStock.setBsProduct(bsProduct);
            realtimeStock.setCompany(bsProduct.getCompany());
            realtimeStock.setStockLevel(realtimeStock.getStockLevel() + inBoundNumber);
            realtimeStockLevelService.save(realtimeStock);
        } else {
            //插入新的库存记录
            RealtimeStockLevel newStock = new RealtimeStockLevel();
            newStock.setWarehouse(warehouse);
            newStock.setBsProduct(bsProduct);
            newStock.setStockLevel(inBoundNumber);
            newStock.setCompany(bsProduct.getCompany());
            realtimeStockLevelService.save(newStock);
        }
    }
}
