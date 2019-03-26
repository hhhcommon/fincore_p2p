package com.zillionfortune.boss.service.redis.impl;

import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Component;

import com.zillionfortune.boss.common.constants.RedisConstants;
import com.zillionfortune.boss.common.exception.BusinessException;
import com.zillionfortune.boss.service.redis.AbstractBaseRedisService;
import com.zillionfortune.boss.service.redis.TokenManager;
import com.zillionfortune.boss.service.redis.model.TokenModel;


/**
 * ClassName: TokenManagerImpl <br/>
 * Function: 对Token进行操作的接口实现. <br/>
 * Date: 2016年11月21日 下午4:00:25 <br/>
 *
 * @author kaiyun
 * @version 
 * @since JDK 1.7
 */
@Component
public class TokenManagerImpl extends AbstractBaseRedisService<String, String> implements TokenManager {
	
	/**
	 * 创建一个token关联上指定session.
	 */
    @Override
    public TokenModel createToken(final String userId) throws BusinessException {
    	//校验参数
        if (StringUtils.isBlank(userId)) {
    		throw new BusinessException("userId为空！");
        }
        
        TokenModel result = redisTemplate.execute(new RedisCallback<TokenModel>() {  
            public TokenModel doInRedis(RedisConnection connection)  
                    throws DataAccessException {  
            	String token = UUID.randomUUID().toString().replace("-", "");
            	TokenModel model = new TokenModel(userId, token);
                RedisSerializer<String> serializer = getRedisSerializer();  
                byte[] key  = serializer.serialize(RedisConstants.getLoginTokenKey(userId));  
                byte[] value = serializer.serialize(userId);  
                boolean bl = connection.setNX(key, value);  
                if(bl){//设置键的有效时间
                	connection.expire(key, RedisConstants.EXPIRE_TIME);
                }
                return model;
            }  
        });  
        return result;
    }

    /**
     * 检查token是否有效
     */
    @Override
    public String getTokenByKey(final String userId) throws BusinessException {
        if (StringUtils.isBlank(userId)) {
    		throw new BusinessException("userId为空！");
        }
        String result = redisTemplate.execute(new RedisCallback<String>() {  
            public String doInRedis(RedisConnection connection)  
                    throws DataAccessException { 
                RedisSerializer<String> serializer = getRedisSerializer();  
                byte[] key  = serializer.serialize(RedisConstants.getLoginTokenKey(userId));  
                //判别键值是否存在，避免无用功
                if (connection.exists(key)) {
                	return serializer.deserialize(connection.get(key));
                }
                return null;
            }  
        });  
        return result; 
    }
    
    /**
     * 校验Token是否存在
     */
    @Override
    public boolean checkToken(final String userId) throws BusinessException {
    	
    	boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {  
            public Boolean doInRedis(RedisConnection connection)  
                    throws DataAccessException {  
                RedisSerializer<String> serializer = getRedisSerializer();  
                byte[] key  = serializer.serialize(RedisConstants.getLoginTokenKey(userId)); 
                if (connection.exists(key)) {
                	//如果验证成功，说明此用户进行了一次有效操作，延长token的过期时间
                	connection.expire(key, RedisConstants.EXPIRE_TIME);
                	return true;
                }
                return false;
            }  
        });  
        return result;  
    }

    /**
     * 根据key查Token
     */
    @Override
    public boolean checkToken(final TokenModel model) throws BusinessException {
    	
    	boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {  
            public Boolean doInRedis(RedisConnection connection)  
                    throws DataAccessException {  
                RedisSerializer<String> serializer = getRedisSerializer();  
                byte[] key  = serializer.serialize(RedisConstants.getLoginTokenKey(model.getUserId())); 
                if (connection.exists(key)) {
                	String token =serializer.deserialize(connection.get(key));
                	if (token == null || !token.equals(model.getToken())) {
                        return false;
                    } 
                	//如果验证成功，说明此用户进行了一次有效操作，延长token的过期时间
                	connection.expire(key, RedisConstants.EXPIRE_TIME);
                	return true;
                }
                return false;
            }  
        });  
        return result;  
    }

    /**
     * TODO 企业_清除token
     */
    @Override
    public long deleteToken(final String sessionId) throws BusinessException {
    	Long result = redisTemplate.execute(new RedisCallback<Long>() {  
	    	public Long doInRedis(RedisConnection connection)  
	                throws DataAccessException {  
	            RedisSerializer<String> serializer = getRedisSerializer();  
	            byte[] key  = serializer.serialize(RedisConstants.getLoginTokenKey(sessionId));
	            long num = connection.del(key);
	            return num;
	        }  
	    }); 
    	return result;
    }
    
    /**
     * TODO 设置键的有效时间（秒）
     */
    @Override
	public boolean expire(final String sessionId, final long seconds) throws BusinessException {
		boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {  
	    	public Boolean doInRedis(RedisConnection connection)  
	                throws DataAccessException {  
	            RedisSerializer<String> serializer = getRedisSerializer();  
	            byte[] key  = serializer.serialize(RedisConstants.getLoginTokenKey(sessionId));
	            if (connection.exists(key)) {
	            	if(seconds!=0){
	            		return connection.expire(key, seconds);
	            	}
	            }
	            return false;
	        }  
	    }); 
    	return result; 
	}
}
