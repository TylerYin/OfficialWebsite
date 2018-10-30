/**
 * 
 */
package com.fwzs.master.modules.fwzs.service;

import com.fwzs.master.common.config.Global;
import com.fwzs.master.common.persistence.Page;
import com.fwzs.master.common.service.CrudService;
import com.fwzs.master.common.utils.StringUtils;
import com.fwzs.master.modules.fwzs.dao.BsProductDao;
import com.fwzs.master.modules.fwzs.entity.BsProduct;
import com.fwzs.master.modules.fwzs.entity.FwmSpec;
import com.fwzs.master.modules.fwzs.entity.ProductSpec;
import com.fwzs.master.modules.sys.utils.UserUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 防伪查询记录表Service
 * @author ly
 * @version 2017-09-30
 */
@Service
@Transactional(readOnly = true)
public class BsProductService extends CrudService<BsProductDao, BsProduct> {

	public BsProduct get(String id) {
		return super.get(id);
	}

    /**
     * 初次加载弹出层产品列表
     *
     * @param bsProduct
     * @return
     */
    public List<BsProduct> findList(BsProduct bsProduct) {
        if (!UserUtils.isSystemManager()) {
            bsProduct.setCompany(UserUtils.getUser().getCompany());
        }

        Page<BsProduct> page = new Page<>();
        if (null != Global.getConfig("page.selectProductPageSize")) {
            page.setPageSize(Integer.valueOf(Global.getConfig("page.selectProductPageSize")));
        } else {
            page.setPageSize(50);
        }
        bsProduct.setPage(page);

        return super.findList(bsProduct);
    }

    /**
     * 选择产品弹出层数据检索
     *
     * @param bsProduct
     * @return
     */
    public List<BsProduct> findProducts(BsProduct bsProduct) {
        if (!UserUtils.isSystemManager()) {
            bsProduct.setCompany(UserUtils.getUser().getCompany());
        }
        return this.dao.findProducts(bsProduct);
    }

    public Page<BsProduct> findPage(Page<BsProduct> page, BsProduct bsProduct) {
		bsProduct.getSqlMap().put("dsf", dataScopeFilter(bsProduct.getCurrentUser(), "o", "u"));
		return super.findPage(page, bsProduct);
	}

    @Transactional(readOnly = false)
    public boolean saveProductForMultiSpec(BsProduct bsProduct) {
        if (CollectionUtils.isNotEmpty(bsProduct.getProductSpecs())) {
            FwmSpec fwmSpec;
            BsProduct product;
            for (ProductSpec productSpec : bsProduct.getProductSpecs()) {
                if (StringUtils.isBlank(productSpec.getProdUnitValue()) || StringUtils.isBlank(productSpec.getPackRate())) {
                    continue;
                }

                product = bsProduct;
                product.setId("");
                product.setProdNo(productSpec.getProdNo());
                product.setProdUnit(productSpec.getProdUnitValue());
                if (StringUtils.isNotBlank(productSpec.getPackRate())) {
                    product.setPackRate(productSpec.getPackRate().replace("：", ":"));
                }

                if (null != productSpec.getProdSpec() && StringUtils.isNotBlank(productSpec.getProdSpec().getId())) {
                    fwmSpec = new FwmSpec();
                    fwmSpec.setId(productSpec.getProdSpec().getId());
                    product.setProdSpec(fwmSpec);
                }
                super.save(product);
            }
            return true;
        }
        return false;
    }

	@Transactional(readOnly = false)
    public void save(BsProduct bsProduct) {
        if (StringUtils.isNotBlank(bsProduct.getPackRate())) {
            bsProduct.setPackRate(bsProduct.getPackRate().replace("：", ":"));
        }
        super.save(bsProduct);
    }

    @Transactional(readOnly = false)
	public void delete(BsProduct bsProduct) {
		super.delete(bsProduct);
	}

    public List<String> duplicateRowId(BsProduct bsProduct) {
        return dao.duplicateRowId(bsProduct);
    }

    /**
     * 更新产品参数，产品特点和产品注意事项等属性
     *
     * @param bsProduct
     */
    @Transactional(readOnly = false)
    public void updateExtendAttributes(BsProduct bsProduct) {
        dao.updateExtendAttributes(bsProduct);
    }

}