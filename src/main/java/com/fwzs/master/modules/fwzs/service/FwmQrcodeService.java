/**
 * 
 */
package com.fwzs.master.modules.fwzs.service;

import com.fwzs.master.common.config.Global;
import com.fwzs.master.common.persistence.Page;
import com.fwzs.master.common.service.CrudService;
import com.fwzs.master.modules.fwzs.dao.FwmQrcodeDao;
import com.fwzs.master.modules.fwzs.entity.BsProduct;
import com.fwzs.master.modules.fwzs.entity.FwmFile;
import com.fwzs.master.modules.fwzs.entity.FwmQrcode;
import com.fwzs.master.modules.sys.entity.Office;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 防伪码Service
 * @author yjd
 * @version 2017-10-08
 */
@Service
@Transactional(readOnly = true)
public class FwmQrcodeService extends CrudService<FwmQrcodeDao, FwmQrcode> {

	public FwmQrcode get(String id) {
		return super.get(id);
	}
	
	public List<FwmQrcode> findList(FwmQrcode fwmQrcode) {
		return super.findList(fwmQrcode);
	}
	
	public Page<FwmQrcode> findPage(Page<FwmQrcode> page, FwmQrcode fwmQrcode) {
		fwmQrcode.getSqlMap().put("dsf", dataScopeFilter(fwmQrcode.getCurrentUser(), "o", "u"));
		return super.findPage(page, fwmQrcode);
	}
	
	@Transactional(readOnly = false)
	public void save(FwmQrcode fwmQrcode) {
		super.save(fwmQrcode);
	}
	
	@Transactional(readOnly = false)
	public void delete(FwmQrcode fwmQrcode) {
		super.delete(fwmQrcode);
	}
	@Transactional(readOnly = false)
	public void saveByBatchaPre(List<String> list,BsProduct bsProduct,FwmFile fwmFile) {
		List<FwmQrcode> fwmQrcodes = new ArrayList<>();
		// 分批入库
		int i = 0;
		for (String string : list) {

			FwmQrcode fwmQrcode = new FwmQrcode();
			fwmQrcode.preInsert();
			fwmQrcode.setQrcode(string);
			fwmQrcode.setSelectNum(0l);
			fwmQrcode.setBsProduct(bsProduct);;
			fwmQrcode.setStatus("1");
			fwmQrcode.setFwmFile(fwmFile);
			fwmQrcodes.add(fwmQrcode);
			++i;
			if (i % 2000 == 0) {

			}
		}

		saveByBatch(fwmQrcodes);
	}

	public void saveByBatch(final List<FwmQrcode> fwmQrcodes) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				batchCommit("saveByBatch", Integer.parseInt(Global.getConfig("fwm.bachSize")), fwmQrcodes, logger);
			}
		}).start();
	}

    /**
     * 码文件码和计划关联
     *
     * @return
     */
    @Transactional(readOnly = false)
    public void updatePlanByFileId(String planId, String fileId) {
        dao.updatePlanByFileId(planId, fileId);
    }

	/**
	 * 根据防伪码查询信息
	 *
	 * @return
	 */
	public FwmQrcode getByQrcode(String qrCode) {
		return dao.getByQrcode(qrCode);
	}

	/**
	 * 更新防伪码查询次数
	 * 
	 * @return
	 */
	@Transactional(readOnly = false)
	public void updateSelectNum(String qrCode) {
		dao.updateSelectNum(qrCode);
	}
	
	/**
	 * 批量提交数据
	 * 
	 * @param mybatisSQLId
	 *            SQL语句在Mapper XML文件中的ID
	 * @param commitCountEveryTime
	 *            每次提交的记录数
	 * @param fwmQrcodes
	 *            要提交的数据列表
	 * @param logger
	 *            日志记录器
	 */
	private void batchCommit(String mybatisSQLId, int commitCountEveryTime,
			List<FwmQrcode> fwmQrcodes, org.slf4j.Logger logger) {
		try {
			int commitCount = (int) Math.ceil(fwmQrcodes.size()
					/ (double) commitCountEveryTime);
			List<FwmQrcode> tempList = new ArrayList<FwmQrcode>(
					commitCountEveryTime);
			int start, stop;
			Long startTime = System.currentTimeMillis();
			for (int i = 0; i < commitCount; i++) {
				tempList.clear();
				start = i * commitCountEveryTime;
				stop = Math.min(i * commitCountEveryTime + commitCountEveryTime
						- 1, fwmQrcodes.size() - 1);
				for (int j = start; j <= stop; j++) {
					tempList.add(fwmQrcodes.get(j));
				}
				dao.saveByBatch(tempList);
			}
			Long endTime = System.currentTimeMillis();
			logger.debug("batchCommit耗时：" + (endTime - startTime) + "毫秒");
		} catch (Exception e) {
			logger.error("batchCommit error!", e);

		}
	}

    /**
     * 查询QrCode是否存在
     *
     * @param fwmQrcode
     * @return
     */
    int findQrCodeCount(FwmQrcode fwmQrcode) {
        return dao.findQrCodeCount(fwmQrcode);
    }

    /**
     * 获取手机端防伪追溯界面的企业相关信息
     * @param qrCode
     * @return
     */
    public Office getOfficeInfoByQrcode(String qrCode){
        return dao.getOfficeInfoByQrcode(qrCode);
    }

    /**
     * 获取手机端防伪追溯界面的产品特点等相关信息
     * @param qrCode
     * @return
     */
    public BsProduct getBsProductExtendAttributes(String qrCode) {
        return dao.getBsProductExtendAttributes(qrCode);
    }
}