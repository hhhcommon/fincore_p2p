package com.zb.p2p.customer.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zb.p2p.customer.api.entity.SendSmsCaptchaReq;
import com.zb.p2p.customer.service.config.OtpConfig;
@Component
public class SendSmsCaptchaReqValidator extends BaseValidator<SendSmsCaptchaReq>{
	@Autowired
	private OtpConfig otpConfig;
	@Override
	public void validate(SendSmsCaptchaReq obj) {
		this.required(obj.getOtpBusiCode());
		this.checkMobileForOtp(obj.getOtpBusiCode(), obj.getMobile(), otpConfig);

	}

}
