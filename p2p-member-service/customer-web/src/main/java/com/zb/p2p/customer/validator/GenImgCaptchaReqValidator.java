package com.zb.p2p.customer.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zb.p2p.customer.api.entity.GenImgCaptchaReq;
import com.zb.p2p.customer.service.config.OtpConfig;
@Component
public class GenImgCaptchaReqValidator extends BaseValidator<GenImgCaptchaReq>{

	@Autowired
	private OtpConfig otpConfig;
	@Override
	public void validate(GenImgCaptchaReq obj) {
		this.required(obj.getOtpBusiCode());
		this.checkMobileForOtp(obj.getOtpBusiCode(), obj.getMobile(), otpConfig);
	}

}
