package com.zillionfortune.op.redis;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.zillionfortune.boss.common.constants.RedisConstants;
import com.zillionfortune.boss.common.exception.BusinessException;
import com.zillionfortune.boss.common.utils.JsonUtils;
import com.zillionfortune.boss.service.redis.UserRedisService;
import com.zillionfortune.boss.service.redis.model.RedisModel;
  
  
/**  
 * 测试 
 * @author kaiyun 
 * @version <b>1.0</b>  
 */     
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-basic.xml")
@ActiveProfiles("dev")
public class UserRedisTest {  
    
	@Autowired
	UserRedisService userRedisService ; 
      
    /** 
     * 新增 
     * <br>------------------------------<br> 
     */  
    @Test  
    public void testAddUser() {  
    	RedisModel user = new RedisModel();  
    	user.setId(1);
    	user.setName("kaiyun");
    	user.setRealName("kaiyun");
    	user.setEmail("122@qq.com");
    	user.setMobile("137777777");
    	user.setCreateBy(1);
    	user.setCreateTime(new Date());
    	user.setModifyBy(1);
    	user.setModifyTime(new Date());
        boolean result = false;
		try {
			result = userRedisService.add(user);
		} catch (BusinessException e) {
			e.printStackTrace();
		}  
        Assert.assertTrue(result);  
    }  
    
    /** 
     * 批量新增 普通方式 
     * <br>------------------------------<br> 
     */  
    @Test  
    public void testAddUsers1() {  
        List<RedisModel> list = new ArrayList<RedisModel>();  
        for (int i = 1; i <= 3; i++) {  
        	RedisModel user = new RedisModel();  
        	user.setId(i);
        	user.setName("kaiyun"+i);
        	user.setRealName("kaiyun"+i);
        	user.setEmail("122@qq.com");
        	user.setMobile("137777777");
        	user.setCreateBy(1);
        	user.setCreateTime(new Date());
        	user.setModifyBy(1);
        	user.setModifyTime(new Date());
            list.add(user);  
        }  
        long begin = System.currentTimeMillis();  
        boolean result = false;
        try {
        	 for (RedisModel user : list) {  
        		 result = userRedisService.add(user);  
             }  
		} catch (Exception e) {
			e.printStackTrace();
		}
        System.out.println(System.currentTimeMillis() -  begin);  
        Assert.assertTrue(result);  
    }  
      
    /** 
     * 批量新增 pipeline方式 
     * <br>------------------------------<br> 
     */  
    @Test  
    public void testAddUsers2() {  
        List<RedisModel> list = new ArrayList<RedisModel>();  
        for (int i = 1; i <= 3; i++) {  
        	RedisModel user = new RedisModel();  
        	user.setId(i);
        	user.setName("kaiyun"+i);
        	user.setRealName("kaiyun"+i);
        	user.setEmail("122@qq.com");
        	user.setMobile("137777777");
        	user.setCreateBy(1);
        	user.setCreateTime(new Date());
        	user.setModifyBy(1);
        	user.setModifyTime(new Date());
            list.add(user);  
        }  
        long begin = System.currentTimeMillis();  
        boolean result = false;
		try {
			result = userRedisService.add(list);
		} catch (BusinessException e) {
			e.printStackTrace();
		}  
        System.out.println(System.currentTimeMillis() - begin);  
        Assert.assertTrue(result);  
    }  
    
    /** 
     * 校验key是否存在 
     * <br>------------------------------<br> 
     */  
    @Test
    public void testCheck(){
    	String userId = "1";
    	boolean result = false;
		try {
			result = userRedisService.check(userId);
		} catch (BusinessException e) {
			e.printStackTrace();
		}
    	Assert.assertTrue(result); 
    }
    
    /** 
     * 获取 
     * <br>------------------------------<br> 
     */  
    @Test  
    public void testGetUser() {  
        String userId = "1";  
        RedisModel user = null;
		try {
			user = userRedisService.get(userId);
		} catch (BusinessException e) {
			e.printStackTrace();
		}  
        Assert.assertNotNull(user);  
        System.out.println(JsonUtils.object2Json(user));
    }  
      
    /** 
     * 修改 
     * <br>------------------------------<br> 
     */  
    @Test  
    public void testUpdate() {  
    	RedisModel user = new RedisModel();  
    	user.setId(1);
    	user.setName("kaiyun");
    	user.setRealName("kaiyun");
    	user.setEmail("122@qq.com");
    	user.setMobile("137777777");
    	user.setCreateBy(1);
    	user.setCreateTime(new Date());
    	user.setModifyBy(1);
    	user.setModifyTime(new Date());
        boolean result = false;
		try {
			result = userRedisService.update(user);
		} catch (BusinessException e) {
			e.printStackTrace();
		}  
        Assert.assertTrue(result);  
    }  
    
    /** 
     * 设置键的有效时期（秒） 
     * <br>------------------------------<br> 
     */  
    @Test
    public void testExpire(){
    	String userId = "1";
    	long seconds = RedisConstants.EXPIRE_TIME;
    	boolean result = false;
		try {
			result = userRedisService.expire(userId, seconds);
		} catch (BusinessException e) {
			e.printStackTrace();
		}
    	Assert.assertTrue(result); 
    }
      
    /** 
     * 通过key删除单个 
     * <br>------------------------------<br> 
     */  
    @Test  
    public void testDelete() {  
        String key = "1";  
        try {
        	userRedisService.delete(key);
		} catch (BusinessException e) {
			e.printStackTrace();
		}  
    }  
      
    /** 
     * 批量删除 
     * <br>------------------------------<br> 
     */  
    @Test  
    public void testDeletes() {  
        List<String> list = new ArrayList<String>();  
        for (int i = 1; i <= 3; i++) {  
            list.add(RedisConstants.getUserInfoKey("user_info_") + i);  
        }  
        try {
        	userRedisService.delete(list);
		} catch (BusinessException e) {
			e.printStackTrace();
		}  
    }

	public void setUserRedisService(UserRedisService userRedisService) {
		this.userRedisService = userRedisService;
	}  
      
}  