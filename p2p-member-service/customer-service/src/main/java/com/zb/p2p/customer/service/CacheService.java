package com.zb.p2p.customer.service;

import com.zb.p2p.customer.service.bo.SmsCaptchaBO;

/**
 * 缓存服务
 * @author liujia
 *
 */
public interface CacheService {
	/**
	 * 保存图片验证码
	 * @param otpBusiCode
	 * @param mobile
	 * @param imgCaptchaCode
	 * @param imgCaptchaVal
	 * @param timeout
	 * @return
	 */
	boolean saveImgCaptcha(String otpBusiCode,String mobile,String imgCaptchaCode,String imgCaptchaVal,int timeout);
	/**
	 * 查询图片验证码
	 * @param otpBusiCode
	 * @param mobile
	 * @param imgCaptchaCode
	 * @return
	 */
	String findImgCaptcha(String otpBusiCode,String mobile,String imgCaptchaCode);
	/**
	 * 查询图片验证码
	 * @param otpBusiCode
	 * @param mobile
	 * @param imgCaptchaCode
	 * @return
	 */
	String findImgCaptcha(String imgCaptchaCode);
	/**
	 * 删除图片验证码
	 * @param otpBusiCode
	 * @param mobile
	 * @param imgCaptchaCode
	 * @return
	 */
	boolean delImgCaptcha(String otpBusiCode,String mobile,String imgCaptchaCode);
	/**
	 * 保存免图口令
	 * @param otpBusiCode
	 * @param mobile
	 * @param freeImgToken
	 * @param timeout
	 * @return
	 */
	boolean saveFreeImgToken(String otpBusiCode,String mobile,String freeImgToken,int timeout);
	/**
	 * 查询免图口令
	 * @param otpBusiCode
	 * @param mobile
	 * @return
	 */
	String findFreeImgToken(String otpBusiCode,String mobile);
	/**
	 * 删除免图口令
	 * @param otpBusiCode
	 * @param mobile
	 * @return
	 */
	boolean delFreeImgToken(String otpBusiCode,String mobile);
	/**
	 * 保存短信验证码
	 * @param otpBusiCode
	 * @param mobile
	 * @param smsCaptchaVal
	 * @param timeout
	 * @return
	 */
	boolean saveSmsCaptcha(String otpBusiCode,String mobile,SmsCaptchaBO smsCaptcha,int timeout);
	/**
	 * 查询短信验证码
	 * @param otpBusiCode
	 * @param mobile
	 * @return
	 */
	SmsCaptchaBO findSmsCaptcha(String otpBusiCode,String mobile);
	/**
	 * 删除短信验证码
	 * @param otpBusiCode
	 * @param mobile
	 * @return
	 */
	boolean delSmsCaptcha(String otpBusiCode,String mobile);
	/**
	 * 保存短信验证码错误次数
	 * @param otpBusiCode
	 * @param mobile
	 * @param timeout
	 * @return
	 */
	boolean saveSmsErrorCount(String otpBusiCode,String mobile,long timeout);
	/**
	 * 查询短信验证码错误次数
	 * @param otpBusiCode
	 * @param mobile
	 * @return
	 */
	int findSmsErrorCount(String otpBusiCode,String mobile);
	/**
	 * 查询短信验证码错误次数
	 * @param otpBusiCode
	 * @param mobile
	 * @return
	 */
	boolean delSmsErrorCount(String otpBusiCode,String mobile);
	
	/**
	 * 保存短信验证码锁（）
	 * @param otpBusiCode
	 * @param mobile
	 * @param smsCaptchaVal
	 * @param timeout
	 * @return
	 */
	boolean saveSmsCaptchaLock(String otpBusiCode,String mobile,int timeout);
	/**
	 * 保存短信验证码锁（）
	 * @param otpBusiCode
	 * @param mobile
	 * @return
	 */
	public boolean hasSmsCaptchaLock(String otpBusiCode, String mobile);
	/**
	 * 删除短信验证码锁（）
	 * @param otpBusiCode
	 * @param mobile
	 * @return
	 */
	public boolean delSmsCaptchaLock(String otpBusiCode, String mobile);
	/**
	 * 校验图片验证码锁
	 * @param otpBusiCode
	 * @param mobile
	 * @param lockService
	 * @return
	 */
	public <T> void lock(String lockKey,T t,LockService<T> lockService);
	
}
