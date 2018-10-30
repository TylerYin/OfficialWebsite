/**
 * 
 */
package com.fwzs.master.modules.fwzs.service;

import com.fwzs.master.common.config.Global;
import com.fwzs.master.common.persistence.Page;
import com.fwzs.master.common.service.CrudService;
import com.fwzs.master.common.utils.IdGen;
import com.fwzs.master.modules.fwzs.dao.ScPlanDao;
import com.fwzs.master.modules.fwzs.dao.ScPlanQcDao;
import com.fwzs.master.modules.fwzs.entity.*;
import com.fwzs.master.modules.sys.entity.User;
import com.fwzs.master.modules.sys.utils.UserUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 任务计划Service
 * @author ly
 * @version 2017-09-30
 */
@Service
@Transactional(readOnly = true)
public class ScPlanService extends CrudService<ScPlanDao, ScPlan> {

    @Autowired
    private FwmQrcodeService fwmQrcodeService;

    @Autowired
    private FwmFileService fwmFileService;

    @Autowired
    private ScPlanQcDao scPlanQcDao;

	@Autowired
	private SCPlan2BSProductMappingService scPlan2BSProductMappingService;

	public ScPlan get(String id) {
		return super.get(id);
	}

	public ScPlan getByIdAndDelFlag(String id){
        final Map<String, String> parameterMap = new HashMap<>();
        parameterMap.put("id", id);
        parameterMap.put("delFlag", "0");
        FwmFile fwmFile;
        final ScPlan scPlan = super.dao.getByIdAndDelFlag(parameterMap);
        if (null != scPlan && CollectionUtils.isNotEmpty(scPlan.getBsProductList())) {
            for (BsProduct bsProduct : scPlan.getBsProductList()) {
                if (StringUtils.isNotBlank(bsProduct.getFwmFileId())) {
                    fwmFile = fwmFileService.get(bsProduct.getFwmFileId());
                    if (null != fwmFile) {
                        bsProduct.setFwmFileName(fwmFileService.get(bsProduct.getFwmFileId()).getFileName());
                    }
                }
            }
        }

        setQcNotPassReason(scPlan);
        return scPlan;
    }

    /**
     * 根据生产计划主键查询质检没有通过原因
     *
     * @param scPlan
     */
    private void setQcNotPassReason(ScPlan scPlan) {
        List<ScPlanQc> scPlanQcs = findQcNotPass(scPlan.getId());
        Collections.sort(scPlanQcs);
        if (CollectionUtils.isNotEmpty(scPlanQcs)) {
            scPlan.setQcNotPassReason(scPlanQcs.get(0).getReason());
        }
    }

    public List<ScPlan> findList(ScPlan scPlan) {
		return super.findList(scPlan);
	}
	
	public Page<ScPlan> findPage(Page<ScPlan> page, ScPlan scPlan) {
		scPlan.getSqlMap().put("dsf", dataScopeFilter(scPlan.getCurrentUser(), "o", "u"));
		return super.findPage(page, scPlan);
	}

    public Page<ScPlan> findPlanByStatus(Page<ScPlan> page, ScPlan entity) {
        entity.getSqlMap().put("dsf", dataScopeFilter(entity.getCurrentUser(), "o", "u"));
        entity.setPage(page);
        page.setList(dao.findPlanByStatus(entity));
        return page;
    }

    /**
     * 根据质检人员查询质检列表
     *
     * @param page
     * @param entity
     * @return
     */
    public Page<ScPlan> findPlanByQC(Page<ScPlan> page, ScPlan entity) {
        entity.getSqlMap().put("dsf", dataScopeFilter(entity.getCurrentUser(), "o", "u"));
        entity.setQcBy(UserUtils.getUser());
        entity.setPage(page);
        page.setList(dao.findPlanByQC(entity));
        return page;
    }

    public Page<ScPlan> findPlanByStatusIsNotDraft(Page<ScPlan> page, ScPlan entity) {
		entity.getSqlMap().put("dsf", dataScopeFilter(entity.getCurrentUser(), "o", "u"));
		entity.setPage(page);
		page.setList(dao.findPlanByStatusIsNotDraft(entity));
		return page;
	}

	@Transactional(readOnly = false)
	public int updatePlanStatus(final ScPlan scPlan)
	{
		return super.dao.updatePlanStatus(scPlan);
	}

	@Transactional(readOnly = false)
	public void save(ScPlan scPlan) {
        final List<BsProduct> filteredBsProduct = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(scPlan.getBsProductList())) {
            for (BsProduct product : scPlan.getBsProductList()) {
                if (StringUtils.isNotEmpty(product.getProdNo()) && StringUtils.isNotEmpty(product.getPesticideName())
                        && StringUtils.isNotEmpty(product.getProdName())) {
                    filteredBsProduct.add(product);
                }
            }
        }
        scPlan.setBsProductList(filteredBsProduct);

        updatePlanByFileId(scPlan);

        final boolean isNewRecord = StringUtils.isNotEmpty(scPlan.getId()) ? false : true;
		if (CollectionUtils.isNotEmpty(scPlan.getBsProductList())) {
			final String planId = IdGen.uuid();
			saveScPlan(scPlan, planId, isNewRecord);
            boolean isEdit = false;
            String editPlanId = "";
            for (final BsProduct newBsProduct : scPlan.getBsProductList()) {
				final SCPlan2BSProductMapping scPlan2BSProductMapping = new SCPlan2BSProductMapping();
                if (StringUtils.isNotEmpty(newBsProduct.getMappingId())) {
                    isEdit = true;
                    editPlanId = scPlan.getId();
                    scPlan2BSProductMapping.setPlanId(scPlan.getId());
                    scPlan2BSProductMapping.setId(newBsProduct.getMappingId());
                } else {
                    if (isEdit) {
                        scPlan2BSProductMapping.setPlanId(editPlanId);
                    } else {
                        scPlan2BSProductMapping.setPlanId(planId);
                    }
                }

				scPlan2BSProductMapping.setProdId(newBsProduct.getId());
				scPlan2BSProductMapping.setIndate(newBsProduct.getIndate());
				scPlan2BSProductMapping.setRemark(newBsProduct.getRemark());
				scPlan2BSProductMapping.setBatchNo(newBsProduct.getBatchNo());
				scPlan2BSProductMapping.setPlanNumber(newBsProduct.getPlanNumber());
                scPlan2BSProductMapping.setFwmFileId(newBsProduct.getFwmFileId());

                if (StringUtils.isNotEmpty(newBsProduct.getCheckPlanUrl())) {
                    scPlan2BSProductMapping.setCheckPlanUrl(newBsProduct.getCheckPlanUrl());
                }

                if (null != newBsProduct.getWarehouse() && StringUtils.isNotBlank(newBsProduct.getWarehouse().getId())) {
                    scPlan2BSProductMapping.setWarehouse(newBsProduct.getWarehouse());
                }
                scPlan2BSProductMappingService.save(scPlan2BSProductMapping);
			}
		}
    }

    /**
     * 根据选择的防伪码文件更新防伪码表中的生产计划
     *
     * @param scPlan
     */
    private void updatePlanByFileId(final ScPlan scPlan) {
        final ScPlan plan = get(scPlan.getId());
        if (null != plan) {
            final Map<String, Boolean> fwmFileUpdateFlag = new HashMap<>();
            if (CollectionUtils.isNotEmpty(plan.getBsProductList())) {
                for (BsProduct product : plan.getBsProductList()) {
                    fwmFileUpdateFlag.put(product.getId(), StringUtils.isNotBlank(product.getFwmFileId()) ? false : true);
                }
            }

            // 批处理文件码和批次的关联信息
            if (CollectionUtils.isNotEmpty(scPlan.getBsProductList())) {
                for (BsProduct bsProduct : scPlan.getBsProductList()) {
                    if (StringUtils.isNotBlank(bsProduct.getFwmFileId()) && fwmFileUpdateFlag.get(bsProduct.getId())) {
                        fwmQrcodeService.updatePlanByFileId(scPlan.getId(), bsProduct.getFwmFileId());
                    }
                }
            }
        }
    }

    /**
     * 获取当前企业的所有质检人员
     *
     * @return
     */
    public List<User> findQcUser() {
        List<User> users = UserUtils.findUserByCurrentCompany();
        List<User> qcUsers = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(users)) {
            for (User user : users) {
                if (StringUtils.containsIgnoreCase(user.getLoginName(), "zj")) {
                    qcUsers.add(user);
                }
            }
            return qcUsers;
        }
        return Collections.emptyList();
    }

    private void saveScPlan(final ScPlan scPlan, final String planId, boolean isNewRecord) {
        scPlan.setIsNewRecord(isNewRecord);
        if (isNewRecord) {
            scPlan.setId(planId);
            User user = UserUtils.getUser();
            if (StringUtils.isNotBlank(user.getId())) {
                scPlan.setOperateBy(user.getId());
            }
        } else {
            User user = UserUtils.getUser();
            if (StringUtils.isNotBlank(user.getId())) {
                scPlan.setOperateBy(user.getId());
            }

            // 修改没有通过的质检计划时不需要写入质检没有通过的原因
            if (Global.QUALITY_CONTROL_NOT_PASS.equals(scPlan.getStatus())
                    && Global.COMPLETED.equals(get(scPlan.getId()).getStatus())) {
                scPlan.setQcCount(scPlan.getQcCount() + 1);
                saveQcNotPassReason(scPlan);
            }

            // 修改没有通过的质检计划时重置生产计划的状态
            if (Global.QUALITY_CONTROL_NOT_PASS.equals(get(scPlan.getId()).getStatus())) {
                scPlan.setStatus(Global.COMPLETED);
            }
        }
        super.save(scPlan);
    }

    /**
     * 保存生产计划质检没有通过的原因
     *
     * @param scPlan
     */
    private void saveQcNotPassReason(final ScPlan scPlan) {
        ScPlanQc scPlanQc = new ScPlanQc();
        scPlanQc.setId(IdGen.uuid());
        scPlanQc.setPlanId(scPlan.getId());
        scPlanQc.setReason(scPlan.getQcNotPassReason());
        scPlanQc.setCreateDate(new Date());
        scPlan.setDelFlag("0");
        scPlanQcDao.insertScPlanQc(scPlanQc);
    }

    /**
     * 根据生产计划主键查找质检没有通过列表
     *
     * @param planId
     * @return
     */
    public List<ScPlanQc> findQcNotPass(String planId) {
        if (StringUtils.isNotBlank(planId)) {
            ScPlanQc scPlanQc = new ScPlanQc();
            scPlanQc.setPlanId(planId);
            List<ScPlanQc> scPlanQcs = scPlanQcDao.findQcNotPass(scPlanQc);
            if (CollectionUtils.isNotEmpty(scPlanQcs)) {
                Collections.sort(scPlanQcs);
            }
            return scPlanQcs;
        }
        return Collections.emptyList();
    }

    @Transactional(readOnly = false)
    public void deleteSCPlanAndMapping(final String mappingId, final ScPlan scPlan) {
        SCPlan2BSProductMapping scPlan2BSProductMapping = new SCPlan2BSProductMapping();
        scPlan2BSProductMapping.setId(mappingId);
        scPlan2BSProductMapping = scPlan2BSProductMappingService.get(scPlan2BSProductMapping);

        if (null != scPlan2BSProductMapping) {
            if (StringUtils.isNotBlank(scPlan2BSProductMapping.getFwmFileId())) {
                fwmQrcodeService.updatePlanByFileId(null, scPlan2BSProductMapping.getFwmFileId());
            }
            if (StringUtils.isNotBlank(scPlan2BSProductMapping.getId())) {
                scPlan2BSProductMappingService.delete(scPlan2BSProductMapping);
            }
        }

        scPlan2BSProductMapping = new SCPlan2BSProductMapping();
        scPlan2BSProductMapping.setPlanId(scPlan.getId());
        final List<SCPlan2BSProductMapping> scPlan2BSProductMappings = scPlan2BSProductMappingService.findList(scPlan2BSProductMapping);
        if (CollectionUtils.isEmpty(scPlan2BSProductMappings)) {
            delete(scPlan);
        }
    }

    @Transactional(readOnly = false)
    public void deleteSCPlanAndMapping(final String deleteKyes) {
        String[] deleteKeysArray = deleteKyes.split("&");
        for (String delKey : deleteKeysArray) {
            final ScPlan plan = new ScPlan();
            final String[] delKeys = delKey.split("#");
            final String planId = delKeys[0].trim();
            final String prodId = delKeys[1].trim();
            if (StringUtils.isNotEmpty(planId) && StringUtils.isNotEmpty(prodId)) {
                plan.setId(planId);
                deleteSCPlanAndMapping(prodId, plan);
            }
        }
    }

    @Transactional(readOnly = false)
	public void delete(ScPlan scPlan) {
        if (CollectionUtils.isNotEmpty(scPlan.getBsProductList())) {
            for (BsProduct bsProduct : scPlan.getBsProductList()) {
                if (StringUtils.isNotBlank(bsProduct.getMappingId())) {
                    SCPlan2BSProductMapping scPlan2BSProductMapping = new SCPlan2BSProductMapping();
                    scPlan2BSProductMapping.setId(bsProduct.getMappingId());
                    scPlan2BSProductMappingService.delete(scPlan2BSProductMapping);
                }
            }
        }
		super.delete(scPlan);
	}
	
}