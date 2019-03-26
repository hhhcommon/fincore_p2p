package com.zb.p2p.customer.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zb.p2p.customer.api.entity.CheckSmsCaptchaReq;
import com.zb.p2p.customer.service.config.OtpConfig;
@Component
public class CheckSmsCaptchaReqValidator extends BaseValidator<CheckSmsCaptchaReq>{
	@Autowired
	private OtpConfig otpConfig;
	@Override
	public void validate(CheckSmsCaptchaReq obj) {
		this.required(obj.getOtpBusiCode(),obj.getSmsCaptchaVal());
		this.checkMobileForOtp(obj.getOtpBusiCode(), obj.getMobile(), otpConfig);

	}

}
