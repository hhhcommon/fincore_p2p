/**
 * 
 */
package com.zb.p2p.customer.service;

/**
 * 验证码服务：生成和校验，可用于短信验证码和图形验证码
 * 功能代码一来用来标识验证码属于哪个功能，二来和key组装成实际的redis key，格式为funcCode_key，避免各功能的key相互冲突
 * @author guolitao
 *
 */
public interface CaptchaService {

	//常量定义
	public static final String FUNC_SESSION = "session";//当前会话
	public static final String FUNC_SESSION_RESULT = "session_result";//当前会话
	public static final String FUNC_REGISTER = "register";//注册
	public static final String FUNC_SMS_SEND_REGISTER = "sms_send_register";//客户端短信发送
	public static final String FUNC_LOGIN = "login";//登录
	public static final String FUNC_SMS_SEND_LOGIN = "sms_send_login";//客户端短信发送
	public static final String FUNC_RESET = "reset";//重置密码
	public static final String FUNC_SMS_SEND_RESET = "sms_send_reset";//客户端短信发送
	public static final String FUNC_UNBIND_CARD = "unbindcard";//绑卡
	public static final String FUNC_TXS_ACTIVE = "txs_active";

	/**
	 * 生成SMS验证码
	 * @param funcCode 功能代码
	 * @param key 业务key
	 * @param expireTime 过期时间,单位是秒
	 * @return
	 */
	String generate(String funcCode,String key,Long expireTime);

	/**
	 * 保存MS验证码
	 * @return
	 */
	boolean saveCaptchaVal(String redisKey,String redisVal,Long expireTime);
	
	/**
	 * 是否允许发送短信
	 * @param funcCode
	 * @param key
	 * @param expireTime
	 * @return
	 */
	boolean isAllowSmsSend(String funcCode,String key,Long expireTime);
	
	/**
	 * 总是生成新的SMS验证码：对应强制刷新验证码功能
	 * @param funcCode
	 * @param key
	 * @param expireTime
	 * @param alwaysNew
	 * @return
	 */
	String generate(String funcCode,String key,Long expireTime,boolean alwaysNew);
	/**
	 * 图形验证码，总是生成新的验证码
	 * @param funcCode
	 * @param key
	 * @param expireTime
	 * @return
	 */
	String generateImg(String funcCode,String key,Long expireTime);
	/**
	 * 校验验证码
	 * @param funcCode 功能代码，参数常量定义
	 * @param key 业务key
	 * @param value 验证码值
	 * @return
	 */
	boolean validate(String funcCode,String key,String value);	
	
	/**
	 * 清除手机登录注册缓存
	 * @param mobile
	 */
	void clearCaptcha(String mobile,String customerId, String loginToken);
}
