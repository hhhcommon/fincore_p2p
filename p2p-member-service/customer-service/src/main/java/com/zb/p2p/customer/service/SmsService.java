package com.zb.p2p.customer.service;

/**
 * 短信服务
 * @author guolitao
 *
 */
public interface SmsService {

	/**
	 * 发送短信
	 * @param mobile
	 * @param content
	 * @return
	 */
	boolean sendSms(String mobile,String content);
	/**
	 * 校验手机号
	 * @param mobile
	 * @return
	 */
	boolean isValidMobile(String mobile);
}
