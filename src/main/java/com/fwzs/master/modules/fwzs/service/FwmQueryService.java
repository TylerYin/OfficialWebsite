/**
 * 
 */
package com.fwzs.master.modules.fwzs.service;

import com.fwzs.master.common.service.CrudService;
import com.fwzs.master.modules.fwzs.dao.FwmQueryDao;
import com.fwzs.master.modules.fwzs.entity.FwmQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 防伪码信息查询Service
 * @author Tyler Yin
 * @version 2017-11-07
 */
@Service
@Transactional(readOnly = true)
public class FwmQueryService extends CrudService<FwmQueryDao, FwmQuery> {
	public List<FwmQuery> getFwmInfoByQrCode(final String qrCode, String user, String delFlag){
		return dao.getFwmInfoByQrCode(qrCode, user, delFlag);
	}

    public List<FwmQuery> getFwmInfo(final String qrCode, String delFlag){
        return dao.getFwmInfo(qrCode, delFlag);
    }
}