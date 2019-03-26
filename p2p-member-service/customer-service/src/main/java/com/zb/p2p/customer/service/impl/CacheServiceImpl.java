package com.zb.p2p.customer.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.druid.util.StringUtils;
import com.zb.p2p.customer.common.util.CacheKeyUtil;
import com.zb.p2p.customer.common.util.JsonUtil;
import com.zb.p2p.customer.service.CacheService;
import com.zb.p2p.customer.service.LockService;
import com.zb.p2p.customer.service.bo.SmsCaptchaBO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CacheServiceImpl implements CacheService {
	
	@Autowired
	private RedisService redisService;
	
	@Override
	public boolean saveImgCaptcha(String otpBusiCode, String mobile, String imgCaptchaCode, String imgCaptchaVal,int timeout) {
		boolean result = false;
		
		String keyForOtpImgCaptchaCode = CacheKeyUtil.keyForOtpImgCaptchaCode(otpBusiCode, mobile);
		long r1 = this.redisService.set(keyForOtpImgCaptchaCode, imgCaptchaCode, timeout);
		log.info("redis-set >> key:{}|val:{}|timeout:{}|ret:{}",keyForOtpImgCaptchaCode, imgCaptchaCode, timeout, r1);
		if(r1 != 1) {
			return result;
		}
		
		String keyForOtpImgCaptchaVal = CacheKeyUtil.keyForOtpImgCaptchaVal(imgCaptchaCode);
		long r2 = this.redisService.set(keyForOtpImgCaptchaVal, imgCaptchaVal, timeout);
		log.info("redis-set >> key:{}|val:{}|timeout:{}|ret:{}",keyForOtpImgCaptchaVal, imgCaptchaCode, timeout, r2);

		if(r2 == 1) {
			result = true;
		}
		return result;
	}

	@Override
	public String findImgCaptcha(String otpBusiCode, String mobile, String imgCaptchaCode) {
		String result = null;
		
		String keyForOtpImgCaptchaCode = CacheKeyUtil.keyForOtpImgCaptchaCode(otpBusiCode, mobile);
		String dbImgCaptchaCode = this.redisService.get(keyForOtpImgCaptchaCode);
		log.info("redis-get >> key:{}|val:{}",keyForOtpImgCaptchaCode, imgCaptchaCode);

		if(StringUtils.equals(imgCaptchaCode, dbImgCaptchaCode)) {
			String keyForOtpImgCaptchaVal = CacheKeyUtil.keyForOtpImgCaptchaVal(imgCaptchaCode);
			result = this.redisService.get(keyForOtpImgCaptchaVal);
			log.info("redis-get >> key:{}|val:{}",keyForOtpImgCaptchaVal, result);

		}
		
		return result;
	}
	
	@Override
	public String findImgCaptcha(String imgCaptchaCode) {
		String result = null;

		String keyForOtpImgCaptchaVal = CacheKeyUtil.keyForOtpImgCaptchaVal(imgCaptchaCode);
		result = this.redisService.get(keyForOtpImgCaptchaVal);
		log.info("redis-get >> key:{}|val:{}",keyForOtpImgCaptchaVal, result);

		return result;
	}
	
	@Override
	public boolean delImgCaptcha(String otpBusiCode, String mobile, String imgCaptchaCode) {

		String keyForOtpImgCaptchaCode = CacheKeyUtil.keyForOtpImgCaptchaCode(otpBusiCode, mobile);
		String keyForOtpImgCaptchaVal = CacheKeyUtil.keyForOtpImgCaptchaVal(imgCaptchaCode);

	    long del = this.redisService.del(keyForOtpImgCaptchaCode,keyForOtpImgCaptchaVal);
		log.info("redis-del >> key1:{}|key2:{}|ret:{}",keyForOtpImgCaptchaCode,keyForOtpImgCaptchaVal, del);

		
		return del > 0;
	}

	@Override
	public boolean saveFreeImgToken(String otpBusiCode, String mobile, String freeImgToken,int timeout) {
		String keyForOtpFreeImgToken = CacheKeyUtil.keyForOtpFreeImgToken(otpBusiCode, mobile);
		long r = this.redisService.set(keyForOtpFreeImgToken, freeImgToken, timeout);
		log.info("redis-set >> key:{}|val:{}|timeout:{}|ret:{}",keyForOtpFreeImgToken, freeImgToken, timeout,r);

		return r == 1;
	}

	@Override
	public String findFreeImgToken(String otpBusiCode, String mobile) {
		String keyForOtpFreeImgToken = CacheKeyUtil.keyForOtpFreeImgToken(otpBusiCode, mobile);
		String val = this.redisService.get(keyForOtpFreeImgToken);
		log.info("redis-get >> key:{}|val:{}",keyForOtpFreeImgToken, val);

		return val;
	}
	
	@Override
	public boolean delFreeImgToken(String otpBusiCode, String mobile) {
		String keyForOtpFreeImgToken = CacheKeyUtil.keyForOtpFreeImgToken(otpBusiCode, mobile);
		long del = this.redisService.del(keyForOtpFreeImgToken);
		return del == 1;
	}

	@Override
	public boolean saveSmsCaptcha(String otpBusiCode, String mobile, SmsCaptchaBO smsCaptcha,int timeout) {
		String keyForOtpSmsCaptchaVal = CacheKeyUtil.keyForOtpSmsCaptchaVal(otpBusiCode, mobile);
		
		String json = JsonUtil.convertObjToStr(smsCaptcha);
		long r = this.redisService.set(keyForOtpSmsCaptchaVal, json, timeout);
		log.info("redis-set >> key:{}|val:{}|timeout:{}|ret:{}",keyForOtpSmsCaptchaVal, json, timeout,r);

		return r == 1;
	}

	@Override
	public SmsCaptchaBO findSmsCaptcha(String otpBusiCode, String mobile) {
		SmsCaptchaBO smsCaptchaBO = null;
		String keyForOtpSmsCaptchaVal = CacheKeyUtil.keyForOtpSmsCaptchaVal(otpBusiCode, mobile);
		String val = this.redisService.get(keyForOtpSmsCaptchaVal);
		if(!StringUtils.isEmpty(val)) {
			smsCaptchaBO = (SmsCaptchaBO) JsonUtil.getObjectByJsonStr(val,SmsCaptchaBO.class);
		}
		log.info("redis-get >> key:{}|val:{}",keyForOtpSmsCaptchaVal, val);

		return smsCaptchaBO;
	}
	
	@Override
	public boolean delSmsCaptcha(String otpBusiCode, String mobile) {
		String keyForOtpSmsCaptchaVal = CacheKeyUtil.keyForOtpSmsCaptchaVal(otpBusiCode, mobile);
		long del = this.redisService.del(keyForOtpSmsCaptchaVal);
		log.info("redis-del >> key:{}|ret:{}",keyForOtpSmsCaptchaVal, del);

		return del == 1;
	}

	@Override
	public boolean saveSmsErrorCount(String otpBusiCode, String mobile,long timeout) {
		String keyForOtpSmsErrorCount = CacheKeyUtil.keyForOtpSmsErrorCount(otpBusiCode, mobile);
		long increment = this.redisService.increment(keyForOtpSmsErrorCount, 1L, timeout);
		log.info("redis-incr >> key:{}|timeout:{}|ret:{}",keyForOtpSmsErrorCount, timeout,increment);

		return increment == 1;
	}

	@Override
	public int findSmsErrorCount(String otpBusiCode, String mobile) {
		int result = 0;
		String keyForOtpSmsErrorCount = CacheKeyUtil.keyForOtpSmsErrorCount(otpBusiCode, mobile);
		String val = this.redisService.get(keyForOtpSmsErrorCount);
		log.info("redis-get >> key:{}|val:{}",keyForOtpSmsErrorCount, val);

		if(!StringUtils.isEmpty(val)) {
			result = Integer.valueOf(val);
		}
		return result;
	}

	@Override
	public boolean delSmsErrorCount(String otpBusiCode, String mobile) {
		String keyForOtpSmsErrorCount = CacheKeyUtil.keyForOtpSmsErrorCount(otpBusiCode, mobile);

		long r = this.redisService.del(keyForOtpSmsErrorCount);
		log.info("redis-del >> key:{}|ret:{}",keyForOtpSmsErrorCount, r);

		return r == 1;
	}

	@Override
	public boolean saveSmsCaptchaLock(String otpBusiCode, String mobile, int timeout) {
		String keyForOtpSmsCaptchaLock = CacheKeyUtil.keyForOtpSmsCaptchaLock(otpBusiCode, mobile);
		long r = this.redisService.set(keyForOtpSmsCaptchaLock, "lock", timeout);
		log.info("redis-set >> key:{}|val:{}|timeout:{}|ret:{}",keyForOtpSmsCaptchaLock, "lock", timeout,r);

		return r == 1;
	}
	@Override
	public boolean hasSmsCaptchaLock(String otpBusiCode, String mobile) {
		String keyForOtpSmsCaptchaLock = CacheKeyUtil.keyForOtpSmsCaptchaLock(otpBusiCode, mobile);
		boolean ret = this.redisService.exists(keyForOtpSmsCaptchaLock);
		return ret;
	}
	
	@Override
	public boolean delSmsCaptchaLock(String otpBusiCode, String mobile) {
		String keyForOtpSmsCaptchaLock = CacheKeyUtil.keyForOtpSmsCaptchaLock(otpBusiCode, mobile);
		long ret = this.redisService.del(keyForOtpSmsCaptchaLock);
		log.info("redis-del >> key:{}|ret:{}",keyForOtpSmsCaptchaLock, ret);

		return ret == 1;
	}

	@Override
	public <T> void lock(String lockKey,T t, LockService<T> lockService) {
		this.redisService.lock(lockKey, "lock", t,lockService);
	}

}
