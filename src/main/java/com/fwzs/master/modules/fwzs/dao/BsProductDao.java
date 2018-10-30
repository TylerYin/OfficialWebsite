/**
 * 
 */
package com.fwzs.master.modules.fwzs.dao;

import com.fwzs.master.common.persistence.CrudDao;
import com.fwzs.master.common.persistence.annotation.MyBatisDao;
import com.fwzs.master.modules.fwzs.entity.BsProduct;

import java.util.List;

/**
 * 防伪查询记录表DAO接口
 * @author ly
 * @version 2017-09-30
 */
@MyBatisDao
public interface BsProductDao extends CrudDao<BsProduct> {
    List<BsProduct> findProducts(BsProduct bsProduct);
    List<String> duplicateRowId(BsProduct bsProduct);
    void updateExtendAttributes(BsProduct bsProduct);
}