package com.zillionfortune.op.service.user.test;
/*
 * Copyright (c) 2016, 资邦金服（上海）网络科技有限公司. All Rights Reserved.
 *
 *
 *
 */


import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.zillionfortune.boss.dal.entity.User;
import com.zillionfortune.boss.facade.operation.OperationServiceFacade;
import com.zillionfortune.boss.facade.operation.dto.OperationUserMenuRequest;
import com.zillionfortune.boss.service.user.UserService;

/**
 * ClassName: UserBizTest <br/>
 * Function: 用户管理_service_单元测试. <br/>
 * Date: 2016年11月23日 上午10:16:37 <br/>
 *
 * @author kaiyun
 * @version 
 * @since JDK 1.7
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-basic.xml")
@ActiveProfiles("dev")
public class UserServiceTest {
	
	@Autowired
	UserService userService;
	
	@Autowired
	OperationServiceFacade operationServiceFacade;
	
	@Test
	public void test_queryList(){
		User user = new User();
		String userName = "kaiyun_test1";
		user = userService.selectByUserName(userName);
		System.out.println(JSON.toJSONString(user));
	}
	
	@Test
	public void test_queryListByPage(){
		User user = new User();
		user.setPageStart(1);
		user.setPageSize(2);
		List<User> userList = userService.queryListByPage(user);
		System.out.println(JSON.toJSONString(userList));
	}
	
	@Test
	public void test_queryUserMenu(){
		OperationUserMenuRequest req = new OperationUserMenuRequest();
		req.setId(3);;
		System.out.println(JSON.toJSONString(operationServiceFacade.operationUserMenu(req)));
	}
	
}
