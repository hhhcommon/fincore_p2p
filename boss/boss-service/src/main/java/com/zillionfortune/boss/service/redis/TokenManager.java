package com.zillionfortune.boss.service.redis;

import com.zillionfortune.boss.common.exception.BusinessException;
import com.zillionfortune.boss.service.redis.model.TokenModel;

/**
 * ClassName: TokenManager <br/>
 * Function: (Redis)对Token进行操作的接口. <br/>
 * Date: 2016年11月21日 下午4:00:07 <br/>
 *
 * @author kaiyun
 * @version 
 * @since JDK 1.7
 */
public interface TokenManager {
	

	/**
	 * createToken:创建一个token关联上指定用户. <br/>
	 *
	 * @param userId 
	 * @return TokenModel
	 */
    public TokenModel createToken(String userId) throws BusinessException ;
    
    /**
     * checkToken:检查token是否存在. <br/>
     *
     * @param userId 
     * @return boolean
     */
    public boolean checkToken(String userId) throws BusinessException ;

    /**
     * checkToken:检查token是否有效. <br/>
     *
     * @param model token
     * @return boolean
     */
    public boolean checkToken(TokenModel model) throws BusinessException ;

    /**
     * getToken:根据key查Token. <br/>
     *
     * @param userId 
     * @return token
     */
    public String getTokenByKey(String userId) throws BusinessException ;

    /**
     * deleteToken:清除token. <br/>
     *
     * @param userId 
     * @return 删除key的数量
     */
    public long deleteToken(String userId) throws BusinessException ;
    
    /**  
     * expire:设置键的有效时间（秒） <br/>
     * 
     * @param userId 
     * @param seconds 有效时间（单位：秒）
     * @return boolean
     */ 
    boolean expire(String userId, long seconds) throws BusinessException;

}
