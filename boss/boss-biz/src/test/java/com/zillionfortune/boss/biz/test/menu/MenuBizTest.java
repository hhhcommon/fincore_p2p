/*
 * Copyright (c) 2016, 资邦金服（上海）网络科技有限公司. All Rights Reserved.
 *
 *
 *
 */
package com.zillionfortune.boss.biz.test.menu;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.zillionfortune.boss.biz.menu.MenuBiz;
import com.zillionfortune.boss.biz.menu.dto.MenuAddRequest;
import com.zillionfortune.boss.common.dto.BaseWebResponse;
import com.zillionfortune.boss.dal.entity.User;

/**
 * ClassName: MenuBizTest <br/>
 * Function: 菜单管理单元测试. <br/>
 * Date: 2017年2月22日 下午5:00:11 <br/>
 *
 * @author wangzinan_tech@zillionfortune.com
 * @version 
 * @since JDK 1.7
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-basic.xml")
@ActiveProfiles("dev")
public class MenuBizTest {
	
	@Autowired
	MenuBiz menuBiz;
	
	@Test
	public void test_add1(){
		MenuAddRequest req = new MenuAddRequest();
		req.setCreateBy("15");
		req.setDisplayOrder(55);
		req.setIcon("*******");
		req.setIsValid(1);
		req.setName("王子南测试用");
		req.setRemark("一级菜单（王子南测试用）");
		req.setUrl("wzntest1");
		BaseWebResponse resp = menuBiz.add(req);
		System.out.println(JSON.toJSONString(resp));
	}
	
}
