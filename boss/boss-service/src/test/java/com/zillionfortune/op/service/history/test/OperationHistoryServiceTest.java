package com.zillionfortune.op.service.history.test;
/*
 * Copyright (c) 2016, 资邦金服（上海）网络科技有限公司. All Rights Reserved.
 *
 *
 *
 */


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.zillionfortune.boss.dal.entity.OperationHistory;
import com.zillionfortune.boss.service.history.OperationHistoryService;

/**
 * ClassName: OperationHistoryServiceTest <br/>
 * Function: 日志_service_单元测试. <br/>
 * Date: 2016年11月23日 上午10:16:37 <br/>
 *
 * @author kaiyun
 * @version 
 * @since JDK 1.7
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-basic.xml")
@ActiveProfiles("dev")
public class OperationHistoryServiceTest {
	
	@Autowired
	OperationHistoryService operationHistoryService;
	
	@Test
	public void test_insert(){
		OperationHistory operationHistory = new OperationHistory();
		operationHistory.setUserId(1);//用户名Id
		operationHistory.setOperationType("1");//操作描述
		operationHistory.setContent("kaiyun content");//操作描述
		operationHistory.setReferId("10000");//业务ID
		operationHistory.setCreateBy("1");
		operationHistory.setModifyBy("1");
		boolean flg = operationHistoryService.insertOperationHistory(operationHistory);
		System.out.println(flg);
	}
	
}
