package com.zb.p2p.customer.common.util;

public class CacheKeyUtil {
	private final static String SEPRATOR = ":";

	private final static String PROFIX_OTP_IMG_CAPTCHA_CODE = "OTP_IMG_CAPTCHA_CODE";
	private final static String PROFIX_OTP_IMG_CAPTCHA_VAL = "OTP_IMG_CAPTCHA_VAL";
	private final static String PROFIX_OTP_SMS_CAPTCHA_VAL = "OTP_SMS_CAPTCHA_VAL";
	private final static String PROFIX_OTP_SMS_ERROR_COUNT = "OTP_SMS_ERROR_COUNT";
	private final static String PROFIX_OTP_FREE_IMG_TOKEN = "OTP_FREE_IMG_TOKEN";
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
	
	public static String keyForOtpImgCaptchaCode(String otpBusiCode, String mobile) {
		return join(PROFIX_OTP_IMG_CAPTCHA_CODE,otpBusiCode,mobile);
	}
	
	public static String keyForOtpImgCaptchaVal(String imgCaptchaCode) {
		return join(PROFIX_OTP_IMG_CAPTCHA_VAL,imgCaptchaCode);
	}
	
	public static String keyForOtpSmsCaptchaVal(String otpBusiCode, String mobile) {
		return join(PROFIX_OTP_SMS_CAPTCHA_VAL,otpBusiCode,mobile);
	}
	
	public static String keyForOtpSmsErrorCount(String otpBusiCode, String mobile) {
		return join(PROFIX_OTP_SMS_ERROR_COUNT,otpBusiCode,mobile);
	}
	
	public static String keyForOtpFreeImgToken(String otpBusiCode, String mobile) {
		return join(PROFIX_OTP_FREE_IMG_TOKEN,otpBusiCode,mobile);
	}
	
	public static String keyForOtpSmsCaptchaLock(String otpBusiCode, String mobile) {
		return join(PROFIX_OTP_SMS_CAPTCHA_LOCK,otpBusiCode,mobile);
	}
	
	public static String keyForOtpCheckImgCaptchaLock(String otpBusiCode, String mobile) {
		return join(PROFIX_OTP_CHECK_IMG_CAPTCHA_LOCK,otpBusiCode,mobile);
	}
	
	public static String keyForOtpCheckSmsCaptchaLock(String otpBusiCode, String mobile) {
		return join(PROFIX_OTP_CHECK_SMS_CAPTCHA_LOCK,otpBusiCode,mobile);
	}
}
