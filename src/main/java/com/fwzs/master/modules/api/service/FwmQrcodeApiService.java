/**
 * 
 */
package com.fwzs.master.modules.api.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fwzs.master.common.config.Global;
import com.fwzs.master.common.persistence.Page;
import com.fwzs.master.common.service.CrudService;
import com.fwzs.master.modules.api.dao.FwmQrcodeApiDao;
import com.fwzs.master.modules.api.entity.FwmQrcodeVo;
import com.fwzs.master.modules.fwzs.entity.BsProduct;
import com.fwzs.master.modules.fwzs.entity.FwmFile;
import com.fwzs.master.modules.fwzs.entity.FwmQrcode;
import com.fwzs.master.modules.fwzs.dao.FwmQrcodeDao;

/**
 * 防伪码Service
 * @author yjd
 * @version 2017-10-08
 */
@Service
@Transactional(readOnly = true)
public class FwmQrcodeApiService extends CrudService<FwmQrcodeApiDao, FwmQrcodeVo> {

	public FwmQrcodeVo get(String id) {
		return super.get(id);
	}
	
	
	
	@Transactional(readOnly = false)
	public void updateByBatch(final List<FwmQrcodeVo> fwmQrcodeVos) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				batchCommit("saveByBatch", Integer.parseInt(Global.getConfig("fwm.bachSize")), fwmQrcodeVos, logger);
			}
		}).start();

		//
	}
	/**
	 * 批量提交数据
	 * 
	 * @param sqlSessionFactory
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
			List<FwmQrcodeVo> fwmQrcodeVos, org.slf4j.Logger logger) {
		try {
			int commitCount = (int) Math.ceil(fwmQrcodeVos.size()
					/ (double) commitCountEveryTime);
			List<FwmQrcodeVo> tempList = new ArrayList<FwmQrcodeVo>(
					commitCountEveryTime);
			int start, stop;
			Long startTime = System.currentTimeMillis();
			for (int i = 0; i < commitCount; i++) {
				tempList.clear();
				start = i * commitCountEveryTime;
				stop = Math.min(i * commitCountEveryTime + commitCountEveryTime
						- 1, fwmQrcodeVos.size() - 1);
				for (int j = start; j <= stop; j++) {
					tempList.add(fwmQrcodeVos.get(j));
				}
				dao.updateByBatch(tempList);
			}
			Long endTime = System.currentTimeMillis();
			logger.debug("batchCommit耗时：" + (endTime - startTime) + "毫秒");
			System.out.println("batchCommit耗时：" + (endTime - startTime) + "毫秒");
		} catch (Exception e) {
			logger.error("batchCommit error!", e);

		}
	}
}