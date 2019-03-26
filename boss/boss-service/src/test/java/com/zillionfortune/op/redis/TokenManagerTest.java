package com.zillionfortune.op.redis;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.zillionfortune.boss.common.constants.RedisConstants;
import com.zillionfortune.boss.common.exception.BusinessException;
import com.zillionfortune.boss.service.redis.TokenManager;
import com.zillionfortune.boss.service.redis.model.TokenModel;

/**
 * ClassName: RedisTokenManagerEnterpriseTest <br/>
 * Function: 企业_通过Redis存储和验证token的实现类_测试. <br/>
 * Date: 2016年11月21日 下午4:43:44 <br/>
 *
 * @author kaiyun
 * @version 
 * @since JDK 1.7
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-basic.xml")
@ActiveProfiles("dev")
public class TokenManagerTest {
	
	@Autowired
	TokenManager redis;
	
	@Test  
	public void testCreateToken(){
		TokenModel tokenModel = null;
		try {
			tokenModel = redis.createToken("1111");
		} catch (BusinessException e) {
			e.printStackTrace();
		}
		System.out.println("-------------------testCreateToken.resp = " + "\t" + tokenModel.getUserId() + "\t" + tokenModel.getToken() );
	}
	
	@Test
	public void tesCheckToken(){
		String sessionId = "1111";//key
		String token;
		boolean result;
		try {
			token = redis.getTokenByKey(sessionId);//value
			TokenModel model = new TokenModel(sessionId,token);
			result = redis.checkToken(model);
			Assert.assertTrue(result); 
		} catch (BusinessException e1) {
			e1.printStackTrace();
		}
	}
	
	@Test
	public void testTokenExpire(){
		String sessionId = "1111";//key
		try {
			boolean result = redis.expire(sessionId, RedisConstants.EXPIRE_TIME);
			Assert.assertTrue(result);  
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testDeleteToken(){
		String sessionId = "1111";
		try {
			long num = redis.deleteToken(sessionId);
			System.out.println("-------------------testDeleteToken.resp = " + "\t" + num);
		} catch (BusinessException e) {
			e.printStackTrace();
		}
	}
	

}
