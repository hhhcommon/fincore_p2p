package com.zillionfortune.boss.service.redis.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.zillionfortune.boss.common.constants.RedisConstants;
import com.zillionfortune.boss.common.exception.BusinessException;
import com.zillionfortune.boss.common.utils.JsonUtils;
import com.zillionfortune.boss.service.redis.AbstractBaseRedisService;
import com.zillionfortune.boss.service.redis.UserRedisService;
import com.zillionfortune.boss.service.redis.model.RedisModel;
  
  
/**
 * ClassName: UserRedisServiceImpl <br/>
 * Function: redis_用户信息service操作_接口实现. <br/>
 * Date: 2016年12月1日 下午3:45:47 <br/>
 *
 * @author kaiyun@zillionfortune.com
 * @version 
 * @since JDK 1.7
 */
@Service
public class UserRedisServiceImpl extends AbstractBaseRedisService<String, RedisModel> implements UserRedisService {  
	
//	@Value("${qylc_redis_key_expire_time}")
//	private long qylc_redis_key_expire_time ;
	
	/**
	 * 新增
	 */
	@Override
    public boolean add(final RedisModel redisModel) throws BusinessException {  
    	
        boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {  
            public Boolean doInRedis(RedisConnection connection)  
                    throws DataAccessException {  
                RedisSerializer<String> serializer = getRedisSerializer();  
                byte[] key  = serializer.serialize(RedisConstants.getUserInfoKey(redisModel.getId()+""));  
                byte[] name = serializer.serialize(JsonUtils.object2Json(redisModel));  
                boolean bl = connection.setNX(key, name); 
                if(bl){//设置键的有效时间
                	connection.expire(key, RedisConstants.EXPIRE_TIME);
                }
                return bl;  
            }  
        });  
        return result;  
    }  
    
    /**
     * 批量新增 使用pipeline方式  
     */
    @Override
    public boolean add(final List<RedisModel> redisModelList) throws BusinessException {  
        Assert.notEmpty(redisModelList);  
        boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {  
            public Boolean doInRedis(RedisConnection connection)  
                    throws DataAccessException {  
                RedisSerializer<String> serializer = getRedisSerializer();  
                for (RedisModel user : redisModelList) {  
                    byte[] key  = serializer.serialize(RedisConstants.getUserInfoKey(user.getId()+""));  
                    byte[] name = serializer.serialize(JsonUtils.object2Json(user));  
                    connection.setNX(key, name);  
                }  
                return true;  
            }  
        }, false, true);  
        return result;  
    }  
      
    /**
     * 删除
     */
    public void delete(String userId) throws BusinessException {  
        List<String> list = new ArrayList<String>();  
        list.add(RedisConstants.getUserInfoKey(userId));  
        delete(list);  
    }  
  
    /**
     * 删除多个 
     */
    public void delete(List<String> keys) throws BusinessException {  
        redisTemplate.delete(keys);  
    }  
  
    /**
     * 修改
     */
    public boolean update(final RedisModel redisModel) throws BusinessException {  
    	//校验参数
        String key = redisModel.getId()+"";
        if (get(key) == null) {  
            throw new NullPointerException("数据行不存在, key = " + key);  
        }  
        
        boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {  
            public Boolean doInRedis(RedisConnection connection)  
                    throws DataAccessException {  
                RedisSerializer<String> serializer = getRedisSerializer();  
                byte[] key  = serializer.serialize(RedisConstants.getUserInfoKey(redisModel.getId()+""));  
                byte[] name = serializer.serialize(JsonUtils.object2Json(redisModel));  
                connection.set(key, name);  
                //设置键的有效时间
                connection.expire(key, RedisConstants.EXPIRE_TIME);
                return true;  
            }  
        });  
        return result;  
    }  
  
    /**
     * 通过key获取 
     */
    public RedisModel get(final String userId) throws BusinessException {  
    	RedisModel result = redisTemplate.execute(new RedisCallback<RedisModel>() {  
            public RedisModel doInRedis(RedisConnection connection)  
                    throws DataAccessException {  
                RedisSerializer<String> serializer = getRedisSerializer();  
                byte[] key = serializer.serialize(RedisConstants.getUserInfoKey(userId));  
                byte[] value = connection.get(key);  
                if (value == null) {  
                    return null;  
                }  
                String objStr = serializer.deserialize(value);  
                return JsonUtils.json2Object(objStr, RedisModel.class);
            }  
        });  
        return result;  
    }
    
    /**
     * 设置过期时间 （秒）
     */
    public boolean expire(final String userId, final long seconds) throws BusinessException {  
    	boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {  
            public Boolean doInRedis(RedisConnection connection)  
                    throws DataAccessException {  
                RedisSerializer<String> serializer = getRedisSerializer();  
                byte[] key = serializer.serialize(RedisConstants.getUserInfoKey(userId));  
                return connection.expire(key, seconds);
            }  
        });  
        return result;  
    }

    /**
     * 校验key是否存在
     */
	public boolean check(final String userId) throws BusinessException {
    	
		boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {  
            public Boolean doInRedis(RedisConnection connection)  
                    throws DataAccessException {  
                RedisSerializer<String> serializer = getRedisSerializer();  
                byte[] key = serializer.serialize(RedisConstants.getUserInfoKey(userId)); 
                byte[] value = connection.get(key);
                if (value == null) {  
                    return false;  
                } 
                //若键存在，则延长键的有效时间
                connection.expire(key, RedisConstants.EXPIRE_TIME);
                return true;
            }  
        });  
        return result;  
	}

}  
