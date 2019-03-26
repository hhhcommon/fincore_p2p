package com.zillionfortune.boss.web.controller.ams;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-basic.xml")
@ActiveProfiles("dev")
public class RedisTest {
	
	 @Autowired
	 protected RedisTemplate<String, String> redisTemplate;
	 
	
    @Test
    public void Test_redis_string(){
    	redisTemplate.opsForValue().set("ky2", "ky_val_2");
    	String result = redisTemplate.opsForValue().get("ky2");
    	System.err.println("redis string result:------------" + result );
    }
    
    //@Test
    public void Test_redis_list(){
    	redisTemplate.opsForHash().put("h_key1", "username", "kaiyun");
    }
    
    //@Test
    public void Test_redis_set(){
    	redisTemplate.opsForSet().add("s_key1", "s_val1","s_val2");
    }
    
    //@Test
    public void Test_redis_zset(){
    	redisTemplate.opsForZSet().add("zs_key1", "zs_val1", 2);
    }

}
