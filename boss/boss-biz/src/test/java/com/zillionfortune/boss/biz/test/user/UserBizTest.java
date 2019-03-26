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
import com.zillionfortune.boss.biz.user.UserBiz;
import com.zillionfortune.boss.biz.user.dto.UserAddRequest;
import com.zillionfortune.boss.biz.user.dto.UserDeleteRequest;
import com.zillionfortune.boss.biz.user.dto.UserModifyPasswordRequest;
import com.zillionfortune.boss.biz.user.dto.UserModifyRequest;
import com.zillionfortune.boss.biz.user.dto.UserQueryListByPageRequest;
import com.zillionfortune.boss.biz.user.dto.UserQueryListRequest;
import com.zillionfortune.boss.biz.user.dto.UserResetPasswordRequest;
import com.zillionfortune.boss.common.dto.BaseWebResponse;

/**
 * ClassName: UserBizTest <br/>
 * Function: 用户管理_单元测试. <br/>
 * Date: 2016年11月23日 上午10:16:37 <br/>
 *
 * @author kaiyun
 * @version 
 * @since JDK 1.7
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-basic.xml")
@ActiveProfiles("dev")
public class UserBizTest {
	
	@Autowired
	UserBiz userBiz;
	
	@Test
	public void test_user_insert(){
		UserAddRequest req = new UserAddRequest();
		req.setEmail("1229@qq.com");
		req.setMobile("13816947328");
		req.setUserName("kaiyun_test13");
		req.setPassword("123456");
		req.setRealName("kaiyun");
		req.setCreateBy("1");
		req.setModifyBy("1");
		BaseWebResponse resp = userBiz.insert(req);
		System.out.println(JSON.toJSONString(resp));
	}
	
	@Test
	public void test_user_update(){
		UserModifyRequest req = new UserModifyRequest();
		req.setUserId("32");
		req.setEmail("2222@qq.com");
		BaseWebResponse resp = userBiz.update(req);
		System.out.println(JSON.toJSONString(resp));
	}
	
	@Test
	public void test_user_resetPassword(){
		UserResetPasswordRequest req = new UserResetPasswordRequest();
		req.setUserId("32");
		BaseWebResponse resp = userBiz.resetPassword(req);
		System.out.println(JSON.toJSONString(resp));
	}
	
	@Test
	public void test_user_modifyPassword(){
		UserModifyPasswordRequest req = new UserModifyPasswordRequest();
		req.setUserId("32");
		req.setOriginalPwd("123456");
		req.setNewPwd("111111");
		req.setRepeatPwd("111111");
		BaseWebResponse resp = userBiz.modifyPassword(req);
		System.out.println(JSON.toJSONString(resp));
	}
	
	@Test
	public void test_user_delete_wl(){
		Integer id = 32;
		BaseWebResponse resp = userBiz.deleteByPrimaryKey(id);
		System.out.println(JSON.toJSONString(resp));
	}
	
	@Test
	public void test_user_delete_lj(){
		UserDeleteRequest req = new UserDeleteRequest();
		req.setUserId("32");
		BaseWebResponse resp = userBiz.deleteByPrimaryKey(req);
		System.out.println(JSON.toJSONString(resp));
	}
	
	@Test
	public void test_user_queryList(){
		UserQueryListRequest req = new UserQueryListRequest();
		BaseWebResponse resp = userBiz.queryList(req);
		System.out.println(JSON.toJSONString(resp));
	}
	
	@Test
	public void test_user_queryListByPage(){
		UserQueryListByPageRequest req = new UserQueryListByPageRequest();
		req.setPageNo(1);
		req.setPageSize(2);
		req.setMobile("");
		req.setEmail("");
		req.setUserName("");
		BaseWebResponse resp = userBiz.queryListByPage(req);
		System.out.println(JSON.toJSONString(resp));
	}
	
	
}
