package com.zb.p2p.customer.common.enums;
/**
 * 错误码<br/>
 * 前两位表示业务分类，后两位自定义
 * 如：1001, 10表示积分商城  01表示商品已存在
 * @author liujia
 *
 */
public enum AppCodeEnum {
	//公共参数
	_0000_SUCCESS("0000","成功"),
	_9999_ERROR("9999","操作异常"),
	_0001_ILLEGAL_PARAMETER("0001","非法参数"),
	/** md5计算异常 */
	_1111_ERROR("1111","操作异常"),
	_0010_REDIS_LOCK_FAILED("0010","操作异常"),
	//A0-会员基本信息及操作
    _A001_USER_NOT_EXISTS("A001","会员不存在"),
    _A002_USER_EXISTS("A002","手机号已注册"),
    _A003_IMG_CAPT_INVALID("A003","图形验证码校验不通过或图形验证码过期"),
    _A004_SMS_CAPT_SEND_ERROR("A004","短信验证码发送失败，请稍后重试"),
    _A005_SMS_CAPT_INVALID("A005","短信验证码校验不通过"),
    _A006_USER_UNLOGIN("A006","用户未登录或会话过期"),
    _A007_SMS_CAPT_SEND_ERROR("A007","短信验证码发送太频繁，请稍后重试"),
    _A008_CHECK_CODE("A008","CODE校验不通过"),
    _A009_REFUSE("A009","拒绝"),
    _A010_LOGIN_ERR("A010","用户密码错误"),
    _A011_CAPT_PARA_ERR("A011","密码错误次数到达上限，请输入短信验证码"),
	
    //A1-会员绑卡开户
    _A101_USER_NOT_BIND("A101","用户没有绑卡"),
    _A102_USER_ALREADY_BIND("A102","该实名信息已存在，请核实信息。如果疑问，请联系客服400-688-7608"),
    _A103_NAME_NOT_NAME("A103","实名信息与绑卡信息不一致"),
    _A104_UNBIND_FAIL("A104","解绑卡失败，请稍后重试"),
    _A105_UNBIND_FAIL("A105","您有余额或在投资金，不支持银行卡管理操作"),
    _A106_UNBIND_FAIL("A106","发送解绑卡短信失败，请稍后重试"),
    _A107_UNBIND_RESULT_FAIL("A107","查询渠道解绑卡结果失败，请稍后重试"),
    _A108_BIND_CARD_FAIL("A108","会员绑定银行卡失败，请稍后重试"),
    _A109_BIND_CARD_FAIL("A109","会员绑卡发送验证码失败，请稍后重试"),
    _A110_CARD_BIN_FAIL("A110","查询卡bin失败，请稍后重试"),
    _A111_CARD_LIST_FAIL("A111","查询支持的银行列表失败，请稍后重试"),
    _A112_CONFIRM_BIND_FAIL("A112","确认绑卡失败，请稍后重试"),
    _A113_OPEN_ACCOUNT_FAIL("A113","开户失败，请稍后重试"),
    _A114_UNBIND_RESULT_FAIL("A114","该卡未进行过解绑卡"),
    _A115_BIND_CARD_FAIL("A115","您已绑定过银行卡"),
    _A116_BIND_CARD_FAIL("A116","绑卡信息不正确，请确认"),
    _A117_BIND_CARD_FAIL("A117","未满18周岁无法投资"),
    _A118_UNBIND_SUCCESS("A118","暂不支持银行卡管理操作，请联系客服"),
    _A119_BIND_CARD_FAIL("A119","绑卡处理中"),
    _A120_BIND_CARD_FAIL("A120","该银行卡已在其他账号绑定过，请换卡重新尝试"),
    _A121_MQ_CONSUM("A121","回调TXS失败"),
    _A122_BIND_CARD_FAIL("A122","银行受理的开户时间为工作日9:00至17:00,感谢您的支持,我们将努力为您提供最优质服务"),
    _A123_BIND_CARD_NOTIFY_FAIL("A123","绑卡回调失败"),
    _A124_BIND_CARD_NO_REALNAME("A124","未实名"),
    _A124_UNBIND_DUPLICATE("A125","解绑重复"),
    _A124_UNBIND_CARD_NOT_FOUND("A126","解绑信息不存在"),
    _A127_UNBIND_MOBILE_ERROR("A127","该手机号与绑卡预留手机号不一致"),

    
    _A201_MEMBER_VERIFY_FAIL("A201","个人会员实名认证失败"),
    _A202_MEMBER_VERIFY_DUPLICATE("A202","个人会员实名信息已被认证，不能多次认证"),
    _A201_ORG_MEMBER_VERIFY_FAIL("A201","企业或机构会员认证失败"),
    _A203_ORG_MEMBER_QUERY_FAIL("A203","查询企业或机构会员认证结果失败"),
    //A2-风险评测
	
	//A3-活期账户
    _A301_ORC_UPLOAD_FAIL("A301","证件信息上传失败，请稍后重试"),
    _A302_ORC_CERTIFY_FAIL("A302","证件信息校验不通过，请上传正确的身份证"),
    _A303_ACCOUNT_INACTIVE("A303","活期账户尚未激活"),
    _A304_ORC_IMG_INVALID("A304","证件图片格式不正确"),
    _A305_ORC_IMG_TOO_BIG("A305","证件图片过大，不能超过4M"),
    _A306_ORC_CALL_FAILED("A306","网络超时，请稍后重试"),
    //A4-OTP
    _A401_ILLEGAL_CAPTCHA("A401","验证码无效"),
    _A402_SEND_SMS_DIFF_ILLEGAL("A402","短信发送太频繁"),
    _A403_SMS_CAPTCHA_NOT_EXITS("A403","短信验证码不存在"),
    
    
	;
	
	private String code;
	private String message;
	private AppCodeEnum(String code, String message) {
		this.code = code;
		this.message = message;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
}
