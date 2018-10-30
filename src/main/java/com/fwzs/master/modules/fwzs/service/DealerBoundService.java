package com.fwzs.master.modules.fwzs.service;

import com.fwzs.master.common.config.Global;
import com.fwzs.master.common.persistence.Page;
import com.fwzs.master.common.service.CrudService;
import com.fwzs.master.common.utils.DateUtils;
import com.fwzs.master.common.utils.IdGen;
import com.fwzs.master.modules.fwzs.dao.DealerBoundDao;
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
public class DealerBoundService extends CrudService<DealerBoundDao, DealerBound> {

    private final static Logger logger = LoggerFactory.getLogger(DealerBoundService.class);

    @Autowired
    private PdaUserService pdaUserService;

    @Autowired
    private OutBoundService outBoundService;

    @Autowired
    private FwmQrcodeService fwmQrcodeService;

    @Autowired
    private FwmBoxCodeService fwmBoxCodeService;

    @Autowired
    private RealtimeStockLevelService realtimeStockLevelService;

    @Autowired
    private DealerBound2ProductMappingService dealerBound2ProductMappingService;

    @Autowired
    private DealerBoundProduct2BoxCodeMappingService dealerBoundProduct2BoxCodeMappingService;

    public DealerBound get(String id) {
        return super.get(id);
    }

    public List<DealerBound> findList(DealerBound dealerBound) {
        dealerBound.getSqlMap().put("dsf", dataScopeFilter(dealerBound.getCurrentUser(), "o", "u"));
        return super.findList(dealerBound);
    }

    public Page<DealerBound> findPage(Page<DealerBound> page, DealerBound dealerBound) {
        dealerBound.getSqlMap().put("dsf", dataScopeFilter(dealerBound.getCurrentUser(), "o", "u"));

        if (dealerBound.getBeginDate() == null) {
            dealerBound.setBeginDate(DateUtils.setDays(DateUtils.parseDate(DateUtils.getDate()), 1));
        }
        if (dealerBound.getEndDate() == null) {
            dealerBound.setEndDate(DateUtils.addMonths(dealerBound.getBeginDate(), 1));
        }

        Page<DealerBound> dealerBoundPage = super.findPage(page, dealerBound);
        return dealerBoundPage;
    }

    @Transactional(readOnly = false)
    public String saveDealerOutBound(DealerBound dealerBound) {
        final List<BsProduct> filteredBsProduct = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(dealerBound.getBsProductList())) {
            for (BsProduct product : dealerBound.getBsProductList()) {
                if (StringUtils.isNotEmpty(product.getProdName()) && StringUtils.isNotEmpty(product.getId())
                        && StringUtils.isNotEmpty(product.getPlanNumber())
                        && StringUtils.isNotEmpty(product.getProdName())) {
                    filteredBsProduct.add(product);
                }
            }
        }

        String status = checkPlanNumberIsValid(filteredBsProduct);
        if (Global.BOUND_SUCCESS.equals(status)) {
            final String boundId = IdGen.uuid();
            saveDealerBound(dealerBound, boundId);
            saveDealerBound2ProductMapping(boundId, filteredBsProduct);
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
            realtimeStock.setDealer(UserUtils.findCurrentDealer());
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
            realtimeStock.setDealer(UserUtils.findCurrentDealer());
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
     * @param dealerBound
     * @param boundId
     */
    private void saveDealerBound(final DealerBound dealerBound, final String boundId) {
        if (UserUtils.isDealer()) {
            dealerBound.setInDealer(UserUtils.findCurrentDealer());
        }
        dealerBound.setIsNewRecord(true);
        dealerBound.setId(boundId);
        dealerBound.setStatus(Global.BOUNDING);
        super.save(dealerBound);
    }

    /**
     * 保存出库单和产品映射关系
     *
     * @param boundId
     * @param filteredBsProduct
     */
    private void saveDealerBound2ProductMapping(final String boundId, final List<BsProduct> filteredBsProduct) {
        DealerBound2ProductMapping dealerBound2ProductMapping;
        for (BsProduct product : filteredBsProduct) {
            dealerBound2ProductMapping = new DealerBound2ProductMapping();
            dealerBound2ProductMapping.setIsNewRecord(true);
            dealerBound2ProductMapping.setDealerBoundId(boundId);
            dealerBound2ProductMapping.setId(IdGen.uuid());
            dealerBound2ProductMapping.setProdId(product.getId());
            dealerBound2ProductMapping.setPlanNumber(Integer.valueOf(product.getPlanNumber()));
            dealerBound2ProductMapping.setRemarks(product.getRemarks());
            dealerBound2ProductMappingService.save(dealerBound2ProductMapping);
        }
    }

    /**
     * 根据出库查询防伪码
     *
     * @param page
     * @param qrcode2BoxcodeMapping
     * @return
     */
    public Page<Qrcode2BoxcodeMapping> findBoxcodePageByDealerBoundId(Page<Qrcode2BoxcodeMapping> page, Qrcode2BoxcodeMapping qrcode2BoxcodeMapping) {
        qrcode2BoxcodeMapping.setPage(page);
        page.setList(dao.findQrCodeById(qrcode2BoxcodeMapping));
        return page;
    }

    /**
     * 获取导出Excel需要的数据集
     *
     * @param dealerBound
     * @return
     */
    public List<Qrcode2BoxcodeMapping> findBoxcodePageByDealerBoundId(final DealerBound dealerBound) {
        final Qrcode2BoxcodeMapping qrcode2BoxcodeMapping = new Qrcode2BoxcodeMapping();
        qrcode2BoxcodeMapping.setBoundId(dealerBound.getId());
        if (null != dealerBound.getBsProduct()) {
            qrcode2BoxcodeMapping.setProdId(dealerBound.getBsProduct().getId());
        }
        return dao.findQrCodeById(qrcode2BoxcodeMapping);
    }

    @Transactional(readOnly = false)
    public void delete(DealerBound dealerBound) {
        super.delete(dealerBound);
    }

    /**
     * 删除计划
     *
     * @param dealerBoundIds
     */
    @Transactional(readOnly = false)
    public boolean deleteDealerBound(String dealerBoundIds) {
        boolean isDeleteSuccessful = true;
        if (StringUtils.isNotBlank(dealerBoundIds)) {
            final List<String> dealerBoundIdList = Arrays.asList(dealerBoundIds.split(","));

            //删除出库计划
            DealerBound dealerBound;
            for (String id : dealerBoundIdList) {
                dealerBound = new DealerBound();
                dealerBound.setId(id);
                delete(dealerBound);
            }

            //删除出库计划所关联的产品
            dealerBound2ProductMappingService.deleteByDealerBoundIds(dealerBoundIdList, DealerBound.DEL_FLAG_DELETE);

            //删除产品所关联的箱码表
            final List<DealerBound2ProductMapping> mappings = dealerBound2ProductMappingService.getMappingsByDealerBoundIds(dealerBoundIdList);
            if (CollectionUtils.isNotEmpty(mappings)) {
                DealerProduct2BoxCodeMapping dealerProduct2BoxCodeMapping;
                for (DealerBound2ProductMapping dealerBound2ProductMapping : mappings) {
                    dealerProduct2BoxCodeMapping = new DealerProduct2BoxCodeMapping();
                    dealerProduct2BoxCodeMapping.setDealerBound2ProductId(dealerBound2ProductMapping.getId());
                    dealerBoundProduct2BoxCodeMappingService.deleteByDealerBound2ProductMappingId(dealerProduct2BoxCodeMapping);

                    //恢复库存
                    DealerBound2ProductMapping bound2ProductMapping = dealerBound2ProductMappingService.get(dealerBound2ProductMapping.getId());
                    if (null != bound2ProductMapping && bound2ProductMapping.getPlanNumber() > 0) {
                        RealtimeStockLevel realtimeStock = new RealtimeStockLevel();
                        final BsProduct product = new BsProduct();
                        product.setId(dealerBound2ProductMapping.getProdId());
                        realtimeStock.setBsProduct(product);
                        realtimeStock.setDealer(UserUtils.findCurrentDealer());
                        realtimeStock = realtimeStockLevelService.findStock(realtimeStock);
                        if (null != realtimeStock) {
                            //恢复实时库存
                            realtimeStock.setStockLevel(realtimeStock.getStockLevel() + bound2ProductMapping.getPlanNumber());
                            realtimeStock.setBsProduct(product);
                            realtimeStock.setCompany(UserUtils.getUser().getCompany());
                            realtimeStock.setDealer(UserUtils.findCurrentDealer());
                            realtimeStockLevelService.save(realtimeStock);

                            //恢复出库计划相应产品的库存
                            DealerBound2ProductMapping bound2Product = dealerBound2ProductMappingService.get(bound2ProductMapping);
                            bound2Product.setRealNumber(0);
                            dealerBound2ProductMappingService.save(bound2Product);
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
     * @param dealerBound2ProductId
     * @param boxCodeList
     * @param boxCodeType
     * @param scanDate
     * @return
     */
    //TODO 传入过来的码需要检验，但是如果对于传入过来的数据量比较大，逐条从数据库中检验是不合适的，所以最好的办法是在码中约定几位用来做校验
    @Transactional(readOnly = false)
    public PdaBound saveBoxCode(final String dealerBound2ProductId, final List<String> boxCodeList, final String boxCodeType, final Date scanDate) {
        PdaBound pdaBound = isParameterValid(dealerBound2ProductId, boxCodeList, boxCodeType, scanDate);
        if (StringUtils.isBlank(pdaBound.getStatus())) {
            return dealerBoundProduct2BoxCodeMappingService.saveBoxCode(dealerBound2ProductId, boxCodeList, boxCodeType, scanDate);
        }
        return pdaBound;
    }

    /**
     * 检查PDA传入的参数是否合法
     *
     * @param dealerBound2ProductId
     * @param boxCodeList
     * @param boxCodeType
     * @param scanDate
     * @return
     */
    private PdaBound isParameterValid(final String dealerBound2ProductId, final List<String> boxCodeList,
                                     final String boxCodeType, final Date scanDate) {
        PdaBound pdaBound = new PdaBound();
        if (StringUtils.isBlank(dealerBound2ProductId)) {
            pdaBound.setStatus(Global.BOUND_PLANNOTEXIST);
        } else if (StringUtils.isBlank(boxCodeType) || !(Global.CODE_TYPE_SKU.equals(boxCodeType)
                || Global.CODE_TYPE_BOX.equals(boxCodeType) || Global.CODE_TYPE_BIGBOX.equals(boxCodeType))) {
            pdaBound.setStatus(Global.BOUND_INVALID_BOXCODE_TYPE);
        } else if (null == scanDate) {
            pdaBound.setStatus(Global.BOUND_INVALID_SCANDATE);
        } else {
            DealerBound2ProductMapping dealerBound2ProductMapping = dealerBound2ProductMappingService.get(dealerBound2ProductId);
            if (null == dealerBound2ProductMapping
                    || StringUtils.isBlank(dealerBound2ProductMapping.getId())) {
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
                    dealerBound2ProductMapping = new DealerBound2ProductMapping();
                    dealerBound2ProductMapping.setBoxCode(code);
                    dealerBound2ProductMapping.setDealerBoundId(dealerBound2ProductId);
                    dealerBound2ProductMapping.setDelFlag(DealerBound2ProductMapping.DEL_FLAG_NORMAL);
                    if (dealerBound2ProductMappingService.findOutBoundBoxCount(dealerBound2ProductMapping) > 0) {
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
     * 更新发货单状态为已发货
     *
     * @param dealerBound
     */
    @Transactional(readOnly = false)
    public void updateStatus(final DealerBound dealerBound) {
        if (Global.SHIPPING.equals(dealerBound.getStatus())) {
            dealerBound.setBoundDate(new Date());
        } else {
            dealerBound.setReceiveDate(new Date());
        }
        dao.updateStatus(dealerBound);
    }

    /**
     * 验证产品是否允许出库
     *
     * @param dealerBound
     * @return
     */
    @Transactional(readOnly = false)
    public boolean validateRealNumber(final DealerBound dealerBound) {
        DealerBound bound = get(dealerBound);
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
     * 获取经销商收货列表
     *
     * @param page
     * @param dealerInBound
     * @return
     */
    public Page<DealerInBound> findDealerInBoundPage(Page<DealerInBound> page, DealerInBound dealerInBound) {
        if (dealerInBound.getBeginDate() == null) {
            dealerInBound.setBeginDate(DateUtils.setDays(DateUtils.parseDate(DateUtils.getDate()), 1));
        }
        if (dealerInBound.getEndDate() == null) {
            dealerInBound.setEndDate(DateUtils.addMonths(dealerInBound.getBeginDate(), 1));
        }

        Dealer currentDealer = UserUtils.findCurrentDealer();
        if (null != currentDealer) {
            dealerInBound.setDealer(UserUtils.findCurrentDealer());
        } else {
            if (!UserUtils.isSystemManager()) {
                currentDealer = new Dealer();
                currentDealer.setCompany(UserUtils.getUser().getCompany());
                dealerInBound.setDealer(currentDealer);
            }
        }

        dealerInBound.setPage(page);
        page.setList(dao.findInBoundList(dealerInBound));
        return page;
    }

    /**
     * 更新经销商入库状态并更新库存数据
     *
     * @param dealerInBound
     */
    @Transactional(readOnly = false)
    public void updateInBoundStatus(final DealerInBound dealerInBound) {
        if (StringUtils.isNotBlank(dealerInBound.getDealerBoundId())) {
            DealerBound dealerBound = new DealerBound();
            dealerBound.setId(dealerInBound.getDealerBoundId());
            dealerBound.setStatus(Global.RECEIVED);
            updateStatus(dealerBound);

            dealerBound = get(dealerInBound.getDealerBoundId());
            for (BsProduct bsProduct : dealerBound.getBsProductList()) {
                updateInBoundStock(bsProduct.getId(), dealerBound.getOutDealer().getId(), Integer.valueOf(bsProduct.getRealNumber()));
            }
        }

        if (StringUtils.isNotBlank(dealerInBound.getBoundId())) {
            OutBound outBound = new OutBound();
            outBound.setId(dealerInBound.getBoundId());
            outBound.setStatus(Global.RECEIVED);
            outBoundService.updateStatus(outBound);

            outBound = outBoundService.get(dealerInBound.getBoundId());
            for (BsProduct bsProduct : outBound.getBsProductList()) {
                updateInBoundStock(bsProduct.getId(), outBound.getDealer().getId(), Integer.valueOf(bsProduct.getRealNumber()));
            }
        }
    }

    /**
     * 更新经销商库存
     *
     * @param prodId
     * @param dealerId
     * @param inBoundNumber
     * @return
     */
    public boolean updateInBoundStock(final String prodId, final String dealerId, int inBoundNumber) {
        RealtimeStockLevel realtimeStock = new RealtimeStockLevel();
        BsProduct bsProduct = new BsProduct();
        bsProduct.setId(prodId);
        realtimeStock.setBsProduct(bsProduct);

        Dealer dealer = null;
        if (StringUtils.isNotBlank(dealerId)) {
            dealer = new Dealer();
            dealer.setId(dealerId);
            realtimeStock.setDealer(dealer);
        }

        realtimeStock = realtimeStockLevelService.findStock(realtimeStock);
        if (null != realtimeStock) {
            //更新已经有的库存记录
            realtimeStock.setCompany(UserUtils.getUser().getCompany());
            realtimeStock.setBsProduct(bsProduct);
            realtimeStock.setDealer(dealer);
            realtimeStock.setStockLevel(realtimeStock.getStockLevel() + inBoundNumber);
            realtimeStockLevelService.save(realtimeStock);
        } else {
            //插入新的库存记录
            RealtimeStockLevel newStock = new RealtimeStockLevel();
            newStock.setCompany(UserUtils.getUser().getCompany());
            newStock.setBsProduct(bsProduct);
            newStock.setDealer(dealer);
            newStock.setStockLevel(inBoundNumber);
            realtimeStockLevelService.save(newStock);
        }
        return true;
    }

    /**
     * 根据产品和经销商获取最新库存
     * 若找到则返回最新库存，若没有找到则返回0
     *
     * @param prodId
     * @return
     */
    public long getLatestStockLevel(final String prodId) {
        return realtimeStockLevelService.getLatestStockLevel(prodId, null, UserUtils.findCurrentDealer().getId());
    }

    /**
     * 根据经销商Id获取待发货的出库计划
     *
     * @param userId
     * @return
     */
    public List<DealerBound> findOutBoundsForPDA(String userId) {
        if (StringUtils.isNotBlank(userId)) {
            PdaUser user = pdaUserService.get(userId);
            if (null != user && null != user.getDealer() && StringUtils.isNotBlank(user.getDealer().getId())) {
                DealerBound dealerBound = new DealerBound();
                dealerBound.setPdaUser(user);
                dealerBound.setStatus(Global.BOUNDING);
                dealerBound.setInDealer(user.getDealer());
                return this.dao.findDealerBoundsForPDA(dealerBound);
            }
        }
        return Collections.emptyList();
    }
}
