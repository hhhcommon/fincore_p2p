package com.zb.p2p.customer.service.bo;

import com.zb.p2p.customer.common.enums.OtpBusiCodeEnum;

import lombok.Data;


/**
 * 发送短信验证码
 * @author liujia
 *
 */
@Data
public class SendSmsCaptchaBO {
	//手机号
	private String mobile;
	//业务码(01-注册,02-登录,03-激活,04-绑卡,05-解绑,06-充值,07-提现,08-支付)
	private String otpBusiCode;
	//免图片验证码口令
	private String freeImgToken;
	//图片验证码code
	private String imgCaptchaCode;
	//图片验证码值
	private String imgCaptchaVal;
	private Long customerId;

}
