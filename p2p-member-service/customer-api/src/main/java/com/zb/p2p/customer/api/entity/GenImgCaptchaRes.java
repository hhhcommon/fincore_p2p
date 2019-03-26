package com.zb.p2p.customer.api.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "生成图片验证码-响应")
public class GenImgCaptchaRes {
	@ApiModelProperty(value="免图片验证码口令")
	private String freeImgToken;
	@ApiModelProperty(value="图片验证码code")
	private String imgCaptchaCode;
	@ApiModelProperty(value="图片验证码url")
	private String imgCaptchaUrl;
}
