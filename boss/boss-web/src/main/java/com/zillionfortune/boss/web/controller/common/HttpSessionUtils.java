/*
 * Copyright (c) 2017, 资邦金服（上海）网络科技有限公司. All Rights Reserved.
 *
 *
 *
 */
package com.zillionfortune.boss.web.controller.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.zillionfortune.boss.common.constants.CommonConstants;
import com.zillionfortune.boss.common.exception.BusinessException;
import com.zillionfortune.boss.common.utils.Endecrypt;
import com.zillionfortune.boss.service.redis.UserRedisService;
import com.zillionfortune.boss.service.redis.model.RedisModel;

/**
 * ClassName: HttpSessionUtils <br/>
 * Function: httpSession操作工具类. <br/>
 * Date: 2017年2月22日 上午9:45:42 <br/>
 *
 * @author pengting
 * @version 
 * @since JDK 1.7
 */
@Component
public class HttpSessionUtils {
	
	@Autowired
	private UserRedisService userRedisService;
	
	/**
	 * getCuruser:获取当前登录用户对象. <br/>
	 *
	 * @param session
	 * @return
	 * @throws BusinessException 
	 */
	public RedisModel getCuruser() throws BusinessException {
		ServletRequestAttributes attrs =(ServletRequestAttributes) RequestContextHolder.getRequestAttributes(); 
		String sessionId = attrs.getRequest().getHeader("sessionId");
		String userId = new Endecrypt().get3DESDecrypt(sessionId, CommonConstants.ENDECRYPT_KEY);
		return userRedisService.get(userId);
	}
	
	
}
