package com.zb.p2p.customer.service.bo;

import lombok.Data;

/**
 * 图片验证码
 * @author liujia
 *
 */
@Data
public class ImgCaptchaBO {
	//免图片验证码口令
	private String freeImgToken;
	//图片验证码code
	private String imgCaptchaCode;
	//图片验证码url
	private String imgCaptchaUrl;
}
