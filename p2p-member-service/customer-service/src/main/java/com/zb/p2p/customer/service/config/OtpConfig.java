package com.zb.p2p.customer.service.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import lombok.Data;

@Data
@ConfigurationProperties(prefix="env.otp")
@Component
public class OtpConfig {
	//public final static String FIND_MOBILE_MODEL_FROM_SYSTEM = "1";
	public final static String SMS_PARAM_SMS_CAPTCHA_VAL = "#smsCaptchaVal#";
	/** 手机号获取方式 */
	private Map<String, SmsMessageConfig> smsMessageMap = new HashMap<>();
	/** 图片验证码配置 */
	private CaptchaConfig imgCaptcha = new CaptchaConfig();
	/** 手机验证码配置 */
	private CaptchaConfig smsCaptcha = new CaptchaConfig();
	
	/** 免图口令配置 */
	private CaptchaConfig freeImgToken = new CaptchaConfig();
	
	/** 每日最大免口令次数 */
	private Integer maxFreeTimeInOneDay;
	
	private Integer minSendSmsDiffSecond;
	
	private Integer sendSameSmsInSecond;
	
	
	@Component
	public class CaptchaConfig{
		/** 长度 */
		private Integer length;
		/** 随机数的类型： '0':表示仅获得数字随机数；'1'：表示仅获得字符随机数； '2'：表示获得数字字符混合随机数 */
		private Integer type;
		/** 超时时间（s） */
		private Integer timeoutSecond;
		/** 图片验证码前缀 */
		private String preUrl;
		public CaptchaConfig() {
			super();
			// TODO Auto-generated constructor stub
		}
		public Integer getLength() {
			return length;
		}
		public void setLength(Integer length) {
			this.length = length;
		}
		public Integer getType() {
			return type;
		}
		public void setType(Integer type) {
			this.type = type;
		}
		public Integer getTimeoutSecond() {
			return timeoutSecond;
		}
		public void setTimeoutSecond(Integer timeoutSecond) {
			this.timeoutSecond = timeoutSecond;
		}
		public String getPreUrl() {
			return preUrl;
		}
		public void setPreUrl(String preUrl) {
			this.preUrl = preUrl;
		}
		
	}
	
}


