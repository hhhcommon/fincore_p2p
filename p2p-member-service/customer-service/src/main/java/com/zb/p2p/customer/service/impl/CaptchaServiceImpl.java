/**
 * 
 */
package com.zb.p2p.customer.service.impl;

import com.google.code.kaptcha.Producer;
import com.zb.p2p.customer.common.enums.AppCodeEnum;
import com.zb.p2p.customer.common.exception.AppException;
import com.zb.p2p.customer.common.util.StringUtils;
import com.zb.p2p.customer.service.CaptchaService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 验证码服务
 * @author guolitao
 *
 */
@Service
public class CaptchaServiceImpl implements CaptchaService {
	
	@Resource
	private RedisService redisService;
	@Value("${env.isFixImgCap}")
	private Integer isFixImgCap;
	@Value("${env.isFixSmsCap}")
	private Integer isFixSmsCap;
	
	@Resource(name="captchaProducer")
    private Producer captchaProducer; 
	@Resource(name="imgCaptchaProducer")
    private Producer imgCaptchaProducer; 

	/* (non-Javadoc)
	 * @see com.zb.p2p.customer.service.CaptchaService#generate(java.lang.String, java.lang.String, java.lang.Long)
	 */
	@Override
	public String generate(String funcCode, String key, Long expireTime) {
		//校验
		return generate(funcCode, key, expireTime,false);
	}

	@Override
	public boolean saveCaptchaVal(String redisKey,String redisVal, Long expireTime) {
		//校验
		if(StringUtils.isBlank(redisKey)){
			throw new AppException(AppCodeEnum._0001_ILLEGAL_PARAMETER);
		}
		long r = this.redisService.set(redisKey, redisVal, expireTime);

		return r == 1;
	}

	/* (non-Javadoc)
	 * @see com.zb.p2p.customer.service.CaptchaService#validate(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public boolean validate(String funcCode, String key, String value) {
		//校验
		if(StringUtils.isBlank(funcCode) || StringUtils.isBlank(key) || StringUtils.isBlank(value)){
			throw new AppException(AppCodeEnum._0001_ILLEGAL_PARAMETER);
		}
		String redisKey = funcCode+"_"+key;
		if(redisService.exists(redisKey)){
			String text = redisService.get(redisKey);
			return value.equalsIgnoreCase(text);
		}
		return false;
	}

	@Override
	public void clearCaptcha(String mobile,String customerId, String loginToken) {
		
		redisService.del(CaptchaService.FUNC_LOGIN+"_"+mobile,CaptchaService.FUNC_REGISTER+"_"+mobile,LoginServiceImpl.PRE_CUST+loginToken,LoginServiceImpl.PRE_TOKEN+customerId);
	}

	@Override
	public String generate(String funcCode, String key, Long expireTime, boolean alwaysNew) {
		//校验
		if(StringUtils.isBlank(funcCode) || StringUtils.isBlank(key)){
			throw new AppException(AppCodeEnum._0001_ILLEGAL_PARAMETER);
		}
		String text = null;
		String redisKey = funcCode+"_"+key;
		if(!alwaysNew && redisService.exists(redisKey)){
			text = redisService.get(redisKey);
		}else{
			if(isFixSmsCap != 1){
				text = captchaProducer.createText();
			}else{
				text = "123456";
			}
			redisService.set(redisKey, text, expireTime);
		}
		return text;
	}

	@Override
	public String generateImg(String funcCode, String key, Long expireTime) {
		//校验
		if(StringUtils.isBlank(funcCode) || StringUtils.isBlank(key)){
			throw new AppException(AppCodeEnum._0001_ILLEGAL_PARAMETER);
		}
		String text = null;
		String redisKey = funcCode+"_"+key;
		if(isFixImgCap != 1){
			text = imgCaptchaProducer.createText();
		}else{
			text = "1234";
		}
		redisService.set(redisKey, text, expireTime);
		return text;
	}

	@Override
	public boolean isAllowSmsSend(String funcCode, String key, Long expireTime) {
		String redisKey = funcCode+"_"+key;
		if(redisService.exists(redisKey)){
			return false;
		}else{
			redisService.set(redisKey, "1", expireTime);
		}
		return true;
	}

}
