/*
 * Copyright (c) 2016, 资邦金服（上海）网络科技有限公司. All Rights Reserved.
 *
 *
 *
 */
package com.zillionfortune.boss.biz.test.power;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.zillionfortune.boss.biz.power.PowerBiz;
import com.zillionfortune.boss.biz.power.dto.PowerAddRequest;
import com.zillionfortune.boss.biz.power.dto.PowerModifyRequest;
import com.zillionfortune.boss.biz.power.dto.PowerQueryByPageRequest;
import com.zillionfortune.boss.biz.role.RolePowerBiz;
import com.zillionfortune.boss.biz.role.dto.RolePowerAddRequest;
import com.zillionfortune.boss.common.dto.BaseWebResponse;
import com.zillionfortune.boss.common.utils.PageBean;

/**
 * ClassName: PowerBizTest <br/>
 * Function: 权限测试. <br/>
 * Date: 2017年2月23日 上午10:43:48 <br/>
 *
 * @author zhengrunlong@zillionfortune.com
 * @version 
 * @since JDK 1.7
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-basic.xml")
@ActiveProfiles("dev")
public class PowerBizTest {
	
	@Autowired
	private PowerBiz powerBiz;
	
	@Autowired
	private RolePowerBiz rolePowerBiz;
	
	@Test
	public void testAdd(){
		PowerAddRequest req = new PowerAddRequest();
		req.setCreateBy("2");
		req.setMenuId(1);
		req.setName("用户管理-查询");
		req.setOperationCode("Select");
		req.setRemark("用户管理查询模块");
		req.setRequestUrl("http://192.168.0.1");
		
		/*BaseWebResponse resp = powerBiz.add(req);
		
		System.out.println(JSON.toJSONString(resp));*/
	}
	
	@Test
	public void testModify(){
		PowerModifyRequest req = new PowerModifyRequest();
		req.setPowerId(1);
		req.setMenuId(11);
		req.setName("用户管理-新增(修改)");
		req.setOperationCode("new（modify）");
		req.setRemark("用户管理新增模块（modify）");
		req.setRequestUrl("http://192.168.0.1（modify）");
		req.setModifyBy("2");
		
		/*BaseWebResponse resp = powerBiz.modify(req);
		
		System.out.println(JSON.toJSONString(resp));*/
	}
	
	@Test
	public void testDelete(){

		/*BaseWebResponse resp = powerBiz.delete(1, 222);
		
		System.out.println(JSON.toJSONString(resp));*/
	}
	
	@Test
	public void testQuery(){

		/*BaseWebResponse resp = powerBiz.query(11);
		
		System.out.println(JSON.toJSONString(resp));*/
	}
	
	@Test
	public void testQueryByPage(){

		PowerQueryByPageRequest req = new PowerQueryByPageRequest();
		PageBean pageBean = new PageBean(1, 5);
		req.setPageSize(5);
    	req.setPageStart(pageBean.getPageStart());
    	req.setMenuId(1);
    	req.setName("用户");
		
		/*BaseWebResponse resp = powerBiz.queryByPage(req);
		
		System.out.println(JSON.toJSONString(resp));*/
	}
	
	@Test
	public void testRolePowerAdd(){

		RolePowerAddRequest req = new RolePowerAddRequest();
		req.setCreateBy("55");
		req.setRoleId(1);
		List<Integer> powerIds = new ArrayList<Integer>();
		powerIds.add(1);
		powerIds.add(2);
		powerIds.add(3);
		req.setPowerIds(powerIds);
		
		BaseWebResponse resp = rolePowerBiz.add(req);
		
		System.out.println(JSON.toJSONString(resp));
	}
	
	@Test
	public void testRolePowerQuery(){

		/*BaseWebResponse resp = rolePowerBiz.query(55);
		
		System.out.println(JSON.toJSONString(resp));*/
	}
	
	@Test
	public void testQueryPower(){

		BaseWebResponse resp = rolePowerBiz.queryPower(1, 1);
		
		System.out.println(JSON.toJSONString(resp));
	}
	
}
