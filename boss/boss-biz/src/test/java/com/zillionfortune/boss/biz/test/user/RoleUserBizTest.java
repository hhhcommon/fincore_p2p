/*
 * Copyright (c) 2016, 资邦金服（上海）网络科技有限公司. All Rights Reserved.
 *
 *
 *
 */
package com.zillionfortune.boss.biz.test.user;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.zillionfortune.boss.biz.user.UserRoleBiz;
import com.zillionfortune.boss.common.dto.BaseWebResponse;

/**
 * ClassName: RoleUserBizTest <br/>
 * Function: 用户角色_单元测试. <br/>
 * Date: 2016年11月23日 上午10:16:37 <br/>
 *
 * @author kaiyun
 * @version 
 * @since JDK 1.7
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-basic.xml")
@ActiveProfiles("dev")
public class RoleUserBizTest {
	
	@Autowired
	UserRoleBiz userRoleBiz;
	
	@Test
	public void test_queryRolesByUserId(){
		BaseWebResponse resp = userRoleBiz.selectRoleByUserId(35);
		System.out.println(JSON.toJSONString(resp));
	}
	
	
}
