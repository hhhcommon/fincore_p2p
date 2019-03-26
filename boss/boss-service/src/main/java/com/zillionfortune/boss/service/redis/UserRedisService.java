package com.zillionfortune.boss.service.redis;

import java.util.List;

import com.zillionfortune.boss.common.exception.BusinessException;
import com.zillionfortune.boss.service.redis.model.RedisModel;

/**
 * ClassName: EnterpriseUserRedisService <br/>
 * Function: redis_企业用户信息service操作_接口. <br/>
 * Date: 2016年12月1日 下午3:45:13 <br/>
 *
 * @author kaiyun@zillionfortune.com
 * @version @param <E>
 * @since JDK 1.7
 */
public interface UserRedisService {  
      
    /** 
     * 新增 
     * <br>------------------------------<br> 
     * @param redisModel 用户信息model类
     * @return boolean 
     */
    boolean add(RedisModel redisModel) throws BusinessException;  
    
    /** 
     * 批量新增 使用pipeline方式 
     * <br>------------------------------<br> 
     * @param redisModelList 用户信息model列表
     * @return boolean
     */  
    boolean add(List<RedisModel> redisModelList) throws BusinessException;  
      
    /** 
     * 删除 
     * <br>------------------------------<br> 
     * @param userId 
     */  
    void delete(String userId) throws BusinessException;  
      
    /** 
     * 删除多个 
     * <br>------------------------------<br> 
     * @param userIdList 
     */  
    void delete(List<String> userIdList) throws BusinessException;  
      
    /** 
     * 修改 
     * <br>------------------------------<br> 
     * @param redisModel 用户信息model类
     * @return boolean
     */  
    boolean update(RedisModel redisModel) throws BusinessException;  
  
    /** 
     * 通过key获取 
     * <br>------------------------------<br> 
     * @param userId 
     * @return redisModel 
     */  
    RedisModel get(String userId) throws BusinessException;  
    
    /**  
     * 设置过期时间 （秒）
     * <br>------------------------------<br> 
     * @param userId 
     * @param seconds 
     * @return boolean
     */  
    boolean expire(String userId, long seconds) throws BusinessException;
    
    /**  
     * 校验key是否存在
     * <br>------------------------------<br> 
     * @param userId 
     * @return 
     */ 
    boolean check(String userId) throws BusinessException;
    
}  
