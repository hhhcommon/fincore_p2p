package com.zb.p2p.customer.api.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "发送短信验证码-响应")
public class SendSmsCaptchaRes {
	@ApiModelProperty(value="短信验证码code",required=true)
	private String smsCaptchaCode;
}
