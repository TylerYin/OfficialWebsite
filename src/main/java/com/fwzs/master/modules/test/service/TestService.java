/**
 * 
 */
package com.fwzs.master.modules.test.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fwzs.master.common.service.CrudService;
import com.fwzs.master.modules.test.dao.TestDao;
import com.fwzs.master.modules.test.entity.Test;

/**
 * 测试Service
 * @author ly
 * @version 2013-10-17
 */
@Service
@Transactional(readOnly = true)
public class TestService extends CrudService<TestDao, Test> {

}
