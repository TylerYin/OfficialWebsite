package com.fwzs.master.modules.fwzs.service;

import com.fwzs.master.common.persistence.Page;
import com.fwzs.master.common.service.CrudService;
import com.fwzs.master.common.utils.StringUtils;
import com.fwzs.master.modules.fwzs.dao.RealtimeStockLevelDao;
import com.fwzs.master.modules.fwzs.entity.BsProduct;
import com.fwzs.master.modules.fwzs.entity.Dealer;
import com.fwzs.master.modules.fwzs.entity.RealtimeStockLevel;
import com.fwzs.master.modules.fwzs.entity.Warehouse;
import com.fwzs.master.modules.sys.utils.UserUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Tyler Yin
 * @create 2017-11-20 14:02
 * @description 实时库存查询Service
 **/
@Service
@Transactional(readOnly = true)
public class RealtimeStockLevelService extends CrudService<RealtimeStockLevelDao, RealtimeStockLevel> {

    public RealtimeStockLevel get(String id) {
        return super.get(id);
    }

    public List<RealtimeStockLevel> findList(RealtimeStockLevel realtimeStockLevel) {
        return super.findList(realtimeStockLevel);
    }

    /**
     * 获取企业库存数据
     *
     * @param page
     * @param realtimeStockLevel
     * @return
     */
    public Page<RealtimeStockLevel> findEnterpriseList(Page<RealtimeStockLevel> page, RealtimeStockLevel realtimeStockLevel) {
        if (!UserUtils.isSystemManager()) {
            realtimeStockLevel.setCompany(UserUtils.getUser().getCompany());
        }
        realtimeStockLevel.setPage(page);
        page.setList(dao.findEnterpriseList(realtimeStockLevel));
        caculateBoxNumber(page);
        return page;
    }

    /**
     * 获取经销商库存数据
     *
     * @param page
     * @param realtimeStockLevel
     * @return
     */
    public Page<RealtimeStockLevel> findDealerList(Page<RealtimeStockLevel> page, RealtimeStockLevel realtimeStockLevel) {
        if (!UserUtils.isSystemManager()) {
            realtimeStockLevel.setCompany(UserUtils.getUser().getCompany());
        }
        realtimeStockLevel.setPage(page);

        if (UserUtils.isDealer()) {
            Dealer dealer = UserUtils.findCurrentDealer();
            if (dealer != null) {
                realtimeStockLevel.setDealer(dealer);
                page.setList(dao.findDealerList(realtimeStockLevel));
            }
        }else{
            page.setList(dao.findDealerForCompanyList(realtimeStockLevel));
        }

        caculateBoxNumber(page);
        return page;
    }

    /**
     * 计算二级包装码和三级包装码的数量
     *
     * @param page
     */
    //TODO
    // 目前是根据总的单品码数据来计算箱码和垛码的数量，这样其实是不合理的，
    // 后续需要和采集端约定采集端在调用平台接口的时候，需要提供箱码，垛码等数量
    private void caculateBoxNumber(Page<RealtimeStockLevel> page) {
        if (CollectionUtils.isNotEmpty(page.getList())) {
            for (RealtimeStockLevel stock : page.getList()) {
                if (stock.getStockLevel() > 0 && null != stock.getBsProduct()
                        && StringUtils.isNotBlank(stock.getBsProduct().getPackRate())
                        && stock.getBsProduct().getPackRate().contains(":")) {
                    String packRate = stock.getBsProduct().getPackRate();

                    //目前只支持两种包装规格，如1:10，或者1:10:100
                    String packRateArray[] = packRate.split(":");
                    int pack = caculatePackRate(packRateArray);
                    if (2 == packRateArray.length) {
                        stock.setBoxNum(stock.getStockLevel() / pack);
                    } else {
                        stock.setBoxNum(stock.getStockLevel() / Integer.valueOf(packRateArray[2]));
                        stock.setBigBoxNum(stock.getStockLevel() / pack);
                    }
                } else {
                    stock.setBoxNum(0);
                    stock.setBigBoxNum(0);
                }
            }
        }
    }

    /**
     * 计算包装比例
     *
     * @param packRateArray
     * @return
     */
    private int caculatePackRate(String packRateArray[]) {
        int sum = 1;
        for (String ration : packRateArray) {
            sum *= Integer.valueOf(ration);
        }
        return sum;
    }

    public RealtimeStockLevel findStock(final RealtimeStockLevel realtimeStockLevel){
        return dao.findStock(realtimeStockLevel);
    }

    @Transactional(readOnly = false)
    public void save(RealtimeStockLevel realtimeStockLevel) {
        super.save(realtimeStockLevel);
    }

    @Transactional(readOnly = false)
    public void delete(RealtimeStockLevel realtimeStockLevel) {
        super.delete(realtimeStockLevel);
    }

    /**
     * 根据产品和仓库获取最新库存
     * 若找到则返回最新库存，若没有找到则返回0
     *
     * @param prodId
     * @param warehouseId
     * @return
     */
    public long getLatestStockLevel(final String prodId, final String warehouseId, final String dealerId) {
        final BsProduct bsProduct = new BsProduct();
        bsProduct.setId(prodId);
        final Warehouse warehouse = new Warehouse();
        warehouse.setId(warehouseId);
        final Dealer dealer = new Dealer();
        dealer.setId(dealerId);

        RealtimeStockLevel realtimeStockLevel = new RealtimeStockLevel();
        realtimeStockLevel.setBsProduct(bsProduct);
        realtimeStockLevel.setWarehouse(warehouse);
        realtimeStockLevel.setDealer(dealer);

        realtimeStockLevel = this.findStock(realtimeStockLevel);
        if (null != realtimeStockLevel && realtimeStockLevel.getStockLevel() > 0) {
            return realtimeStockLevel.getStockLevel();
        }
        return 0;
    }
}
