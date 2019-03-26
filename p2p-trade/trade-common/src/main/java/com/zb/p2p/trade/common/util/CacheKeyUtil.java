package com.zb.p2p.trade.common.util;

public class CacheKeyUtil {
	private final static String SEPRATOR = ":";

	private final static String PROFIX_OTP_SMS_ERROR_COUNT = "OTP_SMS_ERROR_COUNT";
	/** 短信发送间隔60秒lock */
	private final static String PROFIX_OTP_SMS_CAPTCHA_LOCK = "OTP_SMS_CAPTCHA_LOCK";
	/** 图片验证码检验lock */
	private final static String PROFIX_OTP_CHECK_IMG_CAPTCHA_LOCK = "OTP_CHECK_IMG_CAPTCHA_LOCK";
	/** 短信验证码检验lock */
	private static final String PROFIX_OTP_CHECK_SMS_CAPTCHA_LOCK = "OTP_CHECK_IMG_CAPTCHA_LOCK";
	

	private static String join(String ... strs) {
		StringBuilder builder = new StringBuilder();
		for(int i=0;i<strs.length;i++) {
			builder.append(strs[i]);
			if(i<strs.length - 1) {
				builder.append(SEPRATOR);
			}
		}
		return builder.toString();
	}
	
	public static String keyForOtpCheckSmsCaptchaLock(String otpBusiCode, String mobile) {
		return join(PROFIX_OTP_CHECK_SMS_CAPTCHA_LOCK,otpBusiCode,mobile);
	}
}
