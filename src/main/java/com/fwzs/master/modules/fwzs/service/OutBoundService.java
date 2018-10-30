package com.fwzs.master.modules.fwzs.service;

import com.fwzs.master.common.config.Global;
import com.fwzs.master.common.persistence.Page;
import com.fwzs.master.common.service.CrudService;
import com.fwzs.master.common.utils.DateUtils;
import com.fwzs.master.common.utils.IdGen;
import com.fwzs.master.modules.fwzs.dao.OutBoundDao;
import com.fwzs.master.modules.fwzs.entity.*;
import com.fwzs.master.modules.sys.utils.UserUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author Tyler Yin
 * @create 2017-11-22 16:34
 * @description 出库Service
 **/
@Service
@Transactional(readOnly = true)
public class OutBoundService extends CrudService<OutBoundDao, OutBound> {

    private final static Logger logger = LoggerFactory.getLogger(OutBoundService.class);

    @Autowired
    private OutBound2ProductMappingService outBound2ProductMappingService;

    @Autowired
    private OutProduct2BoxCodeMappingService outProduct2BoxCodeMappingService;

    @Autowired
    private RealtimeStockLevelService realtimeStockLevelService;

    @Autowired
    private PdaUserService pdaUserService;

    @Autowired
    private FwmQrcodeService fwmQrcodeService;

    @Autowired
    private FwmBoxCodeService fwmBoxCodeService;

    public OutBound get(String id) {
        return super.get(id);
    }

    public List<OutBound> findList(OutBound outBound) {
        outBound.getSqlMap().put("dsf", dataScopeFilter(outBound.getCurrentUser(), "o", "u"));
        return super.findList(outBound);
    }

    public Page<OutBound> findPage(Page<OutBound> page, OutBound outBound) {
        outBound.getSqlMap().put("dsf", dataScopeFilter(outBound.getCurrentUser(), "o", "u"));

        if (outBound.getBeginDate() == null){
            outBound.setBeginDate(DateUtils.setDays(DateUtils.parseDate(DateUtils.getDate()), 1));
        }
        if (outBound.getEndDate() == null){
            outBound.setEndDate(DateUtils.addMonths(outBound.getBeginDate(), 1));
        }

        Page<OutBound> outBoundPage = super.findPage(page, outBound);
        return outBoundPage;
    }

    @Transactional(readOnly = false)
    public String saveOutBound(OutBound outBound) {
        final List<BsProduct> filteredBsProduct = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(outBound.getBsProductList())) {
            for (BsProduct product : outBound.getBsProductList()) {
                if (StringUtils.isNotEmpty(product.getProdName()) && StringUtils.isNotEmpty(product.getId())
                        && StringUtils.isNotEmpty(product.getPlanNumber())
                        && StringUtils.isNotEmpty(product.getWarehouse().getId())
                        && StringUtils.isNotEmpty(product.getProdName())) {
                    filteredBsProduct.add(product);
                }
            }
        }

        String status = checkPlanNumberIsValid(filteredBsProduct);
        if (Global.BOUND_SUCCESS.equals(status)) {
            final String outBoundId = IdGen.uuid();
            saveOutBound(outBound, outBoundId);
            saveOutBound2ProductMapping(outBoundId, filteredBsProduct);
            updateRealtimeStock(filteredBsProduct);
            return Global.BOUND_SUCCESS;
        }
        return status;
    }

    /**
     * 检查计划出库数量是否满足当前库存
     *
     * @param filteredBsProduct
     * @return
     */
    private String checkPlanNumberIsValid(final List<BsProduct> filteredBsProduct) {
        for (BsProduct product : filteredBsProduct) {
            RealtimeStockLevel realtimeStock = new RealtimeStockLevel();
            realtimeStock.setBsProduct(product);
            realtimeStock.setWarehouse(product.getWarehouse());
            realtimeStock = realtimeStockLevelService.findStock(realtimeStock);

            if (null != realtimeStock) {
                long planNumber = Long.parseLong(product.getPlanNumber());
                if (realtimeStock.getStockLevel() < planNumber) {
                    return Global.BOUND_OUTOFSTOCK;
                }
            } else {
                return Global.BOUND_STOCKNOTEXIST;
            }
        }
        return Global.BOUND_SUCCESS;
    }

    /**
     * 更新实时库存
     *
     * @param filteredBsProduct
     * @return
     */
    private String updateRealtimeStock(final List<BsProduct> filteredBsProduct) {
        for (BsProduct product : filteredBsProduct) {
            RealtimeStockLevel realtimeStock = new RealtimeStockLevel();
            realtimeStock.setBsProduct(product);
            realtimeStock.setWarehouse(product.getWarehouse());
            realtimeStock = realtimeStockLevelService.findStock(realtimeStock);

            long planNumber = Long.parseLong(product.getPlanNumber());
            realtimeStock.setStockLevel(realtimeStock.getStockLevel() - planNumber);
            realtimeStockLevelService.save(realtimeStock);
        }
        return Global.BOUND_SUCCESS;
    }

    /**
     * 保存出库单
     *
     * @param outBound
     * @param outBoundId
     */
    private void saveOutBound(final OutBound outBound, final String outBoundId) {
        outBound.setIsNewRecord(true);
        outBound.setId(outBoundId);
        outBound.setStatus(Global.BOUNDING);
        super.save(outBound);
    }

    /**
     * 保存出库单和产品映射关系
     *
     * @param outBoundId
     * @param filteredBsProduct
     */
    private void saveOutBound2ProductMapping(final String outBoundId, final List<BsProduct> filteredBsProduct) {
        String outBound2ProductMappingId;
        OutBound2ProductMapping outBound2ProductMapping;
        for (BsProduct product : filteredBsProduct) {
            outBound2ProductMappingId = IdGen.uuid();
            outBound2ProductMapping = new OutBound2ProductMapping();
            outBound2ProductMapping.setIsNewRecord(true);
            outBound2ProductMapping.setOutBoundId(outBoundId);
            outBound2ProductMapping.setId(outBound2ProductMappingId);
            outBound2ProductMapping.setProdId(product.getId());
            outBound2ProductMapping.setPlanNumber(Integer.valueOf(product.getPlanNumber()));
            outBound2ProductMapping.setWarehouseId(product.getWarehouse().getId());
            outBound2ProductMapping.setRemarks(product.getRemarks());
            outBound2ProductMappingService.save(outBound2ProductMapping);
        }
    }

    /**
     * 更新出库状态
     *
     * @param outBound
     */
    @Transactional(readOnly = false)
    public void updateStatus(final OutBound outBound) {
        if (Global.SHIPPING.equals(outBound.getStatus())) {
            outBound.setBoundDate(new Date());
        } else {
            outBound.setReceiveDate(new Date());
        }
        this.dao.updateStatus(outBound);
    }

    /**
     * 验证产品是否允许出库
     *
     * @param outBound
     * @return
     */
    @Transactional(readOnly = false)
    public boolean validateRealNumber(final OutBound outBound) {
        OutBound bound = get(outBound);
        if (CollectionUtils.isNotEmpty(bound.getBsProductList())) {
            String validateNumber = bound.getBsProductList().get(0).getPlanNumber();
            for (BsProduct bsProduct : bound.getBsProductList()) {
                if (!bsProduct.getPlanNumber().equals(validateNumber) || !bsProduct.getRealNumber().equals(validateNumber)) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    /**
     * 根据出库查询防伪码
     *
     * @param page
     * @param qrcode2BoxcodeMapping
     * @return
     */
    public Page<Qrcode2BoxcodeMapping> findBoxcodePageByOutboundId(Page<Qrcode2BoxcodeMapping> page, Qrcode2BoxcodeMapping qrcode2BoxcodeMapping) {
        qrcode2BoxcodeMapping.setPage(page);
        page.setList(dao.findQrCodeById(qrcode2BoxcodeMapping));
        return page;
    }

    /**
     * 获取导出Excel需要的数据集
     *
     * @param outBound
     * @return
     */
    public List<Qrcode2BoxcodeMapping> findBoxcodePageByOutboundId(final OutBound outBound) {
        final Qrcode2BoxcodeMapping qrcode2BoxcodeMapping = new Qrcode2BoxcodeMapping();
        qrcode2BoxcodeMapping.setBoundId(outBound.getId());
        if (null != outBound.getBsProduct()) {
            qrcode2BoxcodeMapping.setProdId(outBound.getBsProduct().getId());
        }
        return dao.findQrCodeById(qrcode2BoxcodeMapping);
    }

    @Transactional(readOnly = false)
    public void delete(OutBound outBound) {
        super.delete(outBound);
    }

    /**
     * 删除计划
     * @param outBoundIds
     */
    @Transactional(readOnly = false)
    public boolean deleteOutBound(String outBoundIds) {
        boolean isDeleteSuccessful = true;
        if (StringUtils.isNotBlank(outBoundIds)) {
            final List<String> outBoundIdList = Arrays.asList(outBoundIds.split(","));

            //删除出库计划
            OutBound outBound;
            for (String id : outBoundIdList) {
                outBound = new OutBound();
                outBound.setId(id);
                delete(outBound);
            }

            //删除出库计划所关联的产品
            outBound2ProductMappingService.deleteByOutBoundIds(outBoundIdList, OutBound.DEL_FLAG_DELETE);

            //删除产品所关联的箱码表
            final List<OutBound2ProductMapping> mappings = outBound2ProductMappingService.getMappingsByOutBoundIds(outBoundIdList);
            if (CollectionUtils.isNotEmpty(mappings)) {
                OutProduct2BoxCodeMapping outProduct2BoxCodeMapping;
                for (OutBound2ProductMapping outBound2ProductMapping : mappings) {
                    outProduct2BoxCodeMapping = new OutProduct2BoxCodeMapping();
                    outProduct2BoxCodeMapping.setOutBound2ProductId(outBound2ProductMapping.getId());
                    outProduct2BoxCodeMappingService.deleteByOutBound2ProductMappingId(outProduct2BoxCodeMapping);

                    //恢复库存
                    OutBound2ProductMapping bound2ProductMapping = outBound2ProductMappingService.get(outBound2ProductMapping.getId());
                    if (null != bound2ProductMapping && bound2ProductMapping.getPlanNumber() > 0) {
                        RealtimeStockLevel realtimeStock = new RealtimeStockLevel();
                        final BsProduct product = new BsProduct();
                        product.setId(outBound2ProductMapping.getProdId());
                        realtimeStock.setBsProduct(product);
                        final Warehouse warehouse = new Warehouse();
                        warehouse.setId(outBound2ProductMapping.getWarehouseId());
                        realtimeStock.setWarehouse(warehouse);
                        realtimeStock = realtimeStockLevelService.findStock(realtimeStock);
                        if (null != realtimeStock) {
                            //恢复实时库存
                            realtimeStock.setStockLevel(realtimeStock.getStockLevel() + bound2ProductMapping.getPlanNumber());
                            realtimeStock.setBsProduct(product);
                            realtimeStock.setWarehouse(warehouse);
                            realtimeStock.setCompany(UserUtils.getUser().getCompany());
                            realtimeStockLevelService.save(realtimeStock);

                            //恢复出库计划相应产品的库存
                            OutBound2ProductMapping bound2Product = outBound2ProductMappingService.get(bound2ProductMapping);
                            bound2Product.setRealNumber(0);
                            outBound2ProductMappingService.save(bound2Product);
                        } else {
                            isDeleteSuccessful = false;
                            logger.error("库存记录不存在，恢复库存失败");
                        }
                    } else {
                        isDeleteSuccessful = true;
                        logger.error("没有找到需要恢复的记录！");
                    }
                }
            }
        } else {
            isDeleteSuccessful = false;
        }
        return isDeleteSuccessful;
    }

    /**
     * 更新出库计划和实时库存记录
     *
     * @param outBound2ProductId
     * @param boxCodeList
     * @param boxCodeType
     * @param scanDate
     * @return
     */
    //TODO 传入过来的码需要检验，但是如果对于传入过来的数据量比较大，逐条从数据库中检验是不合适的，所以最好的办法是在码中约定几位用来做校验
    @Transactional(readOnly = false)
    public PdaBound saveBoxCode(final String outBound2ProductId, final List<String> boxCodeList,
                                final String boxCodeType, final Date scanDate) {
        PdaBound pdaBound = isParameterValid(outBound2ProductId, boxCodeList, boxCodeType, scanDate);
        if (StringUtils.isBlank(pdaBound.getStatus())) {
            return outProduct2BoxCodeMappingService.saveBoxCode(outBound2ProductId, boxCodeList, boxCodeType, scanDate);
        }
        return pdaBound;
    }

    /**
     * 检查PDA传入的参数是否合法
     *
     * @param outBound2ProductId
     * @param boxCodeList
     * @param boxCodeType
     * @param scanDate
     * @return
     */
    private PdaBound isParameterValid(final String outBound2ProductId, final List<String> boxCodeList,
                                      final String boxCodeType, final Date scanDate) {
        PdaBound pdaBound = new PdaBound();
        if (StringUtils.isBlank(outBound2ProductId)) {
            pdaBound.setStatus(Global.BOUND_PLANNOTEXIST);
        } else if (StringUtils.isBlank(boxCodeType) || !(Global.CODE_TYPE_SKU.equals(boxCodeType)
                || Global.CODE_TYPE_BOX.equals(boxCodeType) || Global.CODE_TYPE_BIGBOX.equals(boxCodeType))) {
            pdaBound.setStatus(Global.BOUND_INVALID_BOXCODE_TYPE);
        } else if (null == scanDate) {
            pdaBound.setStatus(Global.BOUND_INVALID_SCANDATE);
        } else {
            OutBound2ProductMapping outBound2ProductMapping = outBound2ProductMappingService.get(outBound2ProductId);
            if (null == outBound2ProductMapping
                    || StringUtils.isBlank(outBound2ProductMapping.getId())) {
                pdaBound.setStatus(Global.BOUND_PLANNOTEXIST);
                return pdaBound;
            }

            if (CollectionUtils.isNotEmpty(boxCodeList)) {
                //判断扫码集合是不是包含无效码
                FwmQrcode fwmQrcode;
                FwmBoxCode fwmBoxCode;
                for (String code : boxCodeList) {
                    if (Global.CODE_TYPE_SKU.equals(boxCodeType)) {
                        fwmQrcode = new FwmQrcode();
                        fwmQrcode.setQrcode(code);
                        fwmQrcode.setDelFlag(FwmQrcode.DEL_FLAG_NORMAL);
                        if (fwmQrcodeService.findQrCodeCount(fwmQrcode) == 0) {
                            pdaBound.getInvalidCodes().add(code);
                        }
                    } else if (Global.CODE_TYPE_BOX.equals(boxCodeType)) {
                        fwmBoxCode = new FwmBoxCode();
                        fwmBoxCode.setBoxCode(code);
                        fwmBoxCode.setStatus(Global.CODE_TYPE_BOX);
                        fwmBoxCode.setDelFlag(FwmBoxCode.DEL_FLAG_NORMAL);
                        if (fwmBoxCodeService.findBoxCodeCount(fwmBoxCode) == 0) {
                            pdaBound.getInvalidCodes().add(code);
                        }
                    } else {
                        fwmBoxCode = new FwmBoxCode();
                        fwmBoxCode.setBigboxCode(code);
                        fwmBoxCode.setStatus(Global.CODE_TYPE_BIGBOX);
                        fwmBoxCode.setDelFlag(FwmBoxCode.DEL_FLAG_NORMAL);
                        if (fwmBoxCodeService.findBoxCodeCount(fwmBoxCode) == 0) {
                            pdaBound.getInvalidCodes().add(code);
                        }
                    }
                }

                if (CollectionUtils.isNotEmpty(pdaBound.getInvalidCodes())) {
                    pdaBound.setStatus(Global.BOUND_INVALID_BOXCODES);
                    return pdaBound;
                }

                //判断扫码集合是不是包含重复出库
                for (String code : boxCodeList) {
                    outBound2ProductMapping = new OutBound2ProductMapping();
                    outBound2ProductMapping.setBoxCode(code);
                    outBound2ProductMapping.setDelFlag(OutBound2ProductMapping.DEL_FLAG_NORMAL);
                    if (outBound2ProductMappingService.findOutBoundBoxCount(outBound2ProductMapping) > 0) {
                        pdaBound.getDuplicateCodes().add(code);
                    }
                }

                if (CollectionUtils.isNotEmpty(pdaBound.getDuplicateCodes())) {
                    pdaBound.setStatus(Global.BOUND_DUPLICATE_BOXCODES);
                    return pdaBound;
                }
            } else {
                pdaBound.setStatus(Global.BOUND_BOXCODES_EMPTY);
            }
        }
        return pdaBound;
    }

    /**
     * 根据产品和仓库获取最新库存
     * 若找到则返回最新库存，若没有找到则返回0
     *
     * @param prodId
     * @param warehouseId
     * @return
     */
    public long getLatestStockLevel(final String prodId, final String warehouseId) {
        return realtimeStockLevelService.getLatestStockLevel(prodId, warehouseId, null);
    }

    /**
     * 根据用户Id获取待发货的出库计划
     *
     * @param userId
     * @return
     */
    public List<OutBound> findOutBoundsForPDA(String userId) {
        if (StringUtils.isNotBlank(userId)) {
            PdaUser user = pdaUserService.get(userId);
            if (null != user && null != user.getCompany() && StringUtils.isNotBlank(user.getCompany().getId())) {
                OutBound outBound = new OutBound();
                outBound.setPdaUser(user);
                outBound.setStatus(Global.BOUNDING);
                outBound.setCompanyId(user.getCompany().getId());
                return this.dao.findOutBoundsForPDA(outBound);
            }
        }
        return Collections.emptyList();
    }
}
