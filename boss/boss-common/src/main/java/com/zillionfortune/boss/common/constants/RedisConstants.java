package com.zillionfortune.boss.common.constants;
/**
 * redis key
 * 
 * @author kaiyun
 * @version  RedisKey.java, v 0.1 2016-7-29 下午2:44:13
 */
public class RedisConstants {
	
	public final static long EXPIRE_TIME = 1800;
    
    /**
     * 获取令牌key值. <br/>
     *
     * @param userId
     * @return
     */
    public final static String getLoginTokenKey(String userId){
    	return "token_" + userId;
    }
    
    /**
     * 获取用户信息key值. <br/>
     *
     * @param userId
     * @return
     */
    public final static String getUserInfoKey(String userId){
    	return "user_info_" + userId;
    }
    
}
