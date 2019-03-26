package com.zb.p2p.customer.service.bo;

import com.zb.p2p.customer.common.enums.OtpBusiCodeEnum;

import lombok.Data;

@Data
public class GenImgCaptchaBO {
	//手机号
	private String mobile;
	//业务码(01-注册,02-登录,03-激活,04-绑卡,05-解绑,06-充值,07-提现,08-支付
	private String otpBusiCode;
	private Long customerId;
}
