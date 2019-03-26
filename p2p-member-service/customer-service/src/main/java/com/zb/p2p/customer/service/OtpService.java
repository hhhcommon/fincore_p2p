package com.zb.p2p.customer.service;

import com.zb.p2p.customer.service.bo.CheckSmsCaptchaBO;
import com.zb.p2p.customer.service.bo.GenImgCaptchaBO;
import com.zb.p2p.customer.service.bo.ImgCaptchaBO;
import com.zb.p2p.customer.service.bo.SendSmsCaptchaBO;

/**
 * 一次性口令接口
 * @author liujia
 *
 */
public interface OtpService {
	/**
	 * 生成图片验证码
	 * @param genImgCaptchaBO
	 * @return
	 */
	ImgCaptchaBO genImgCaptcha(GenImgCaptchaBO genImgCaptchaBO);
	
	/**
	 * 发送手机短信验证码
	 * @param sendSmsCaptchaBO
	 */
	void sendSmsCaptcha(SendSmsCaptchaBO sendSmsCaptchaBO);
	
	/**
	 * 校验手机短信验证码
	 * @param smsCaptchaBO
	 */
	void checkSmsCaptcha(CheckSmsCaptchaBO smsCaptchaBO);
	/**
	 * 校验手机短信验证码
	 * @param checkSmsCaptchaBO
	 * @return
	 */
	boolean checkSmsCaptchaForInner(CheckSmsCaptchaBO checkSmsCaptchaBO);
	
	/**
	 * 查询图片验证码
	 * @param imgCaptchaCode 图片验证码code
	 * @return
	 */
	String findImgCaptcha(String imgCaptchaCode);
	
}
