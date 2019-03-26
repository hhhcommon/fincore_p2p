package com.zb.p2p.customer.api.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "发送短信验证码-请求")
public class SendSmsCaptchaReq {
	@ApiModelProperty(value="手机号")
	private String mobile;
	@ApiModelProperty(value="业务码(01-注册,02-登录,03-激活,04-绑卡,05-解绑,06-充值,07-提现,08-支付)",required=true)
	private String otpBusiCode;
	@ApiModelProperty(value="免图片验证码口令")
	private String freeImgToken;
	@ApiModelProperty(value="图片验证码code")
	private String imgCaptchaCode;
	@ApiModelProperty(value="图片验证码值")
	private String imgCaptchaVal;
	
	@ApiModelProperty(value="")
	private String customerId; //给txs
}
