package com.zb.p2p.customer.validator;

import org.apache.commons.lang.StringUtils;

import com.zb.p2p.customer.common.enums.AppCodeEnum;
import com.zb.p2p.customer.common.enums.MobileTypeEnum;
import com.zb.p2p.customer.common.exception.AppException;
import com.zb.p2p.customer.service.config.OtpConfig;


public abstract class BaseValidator<T> {
	
	
	public abstract void validate(T obj);
	
	protected void required(String ... array){
		
		for(String a : array){
			if(StringUtils.isEmpty(a)){
				throw AppException.getInstance(AppCodeEnum._0001_ILLEGAL_PARAMETER);
			}
		}
		
	}
	
	
	protected void checkMobileForOtp(String otpBusiCode,String inputMobile,OtpConfig otpConfig) {
		String mobileType = otpConfig.getSmsMessageMap().get(otpBusiCode).getMobileType();
		if(StringUtils.isEmpty(mobileType)) {
			throw AppException.getInstance(AppCodeEnum._0001_ILLEGAL_PARAMETER);
		}
		if(StringUtils.equals(mobileType, MobileTypeEnum._90_CUSTOMER_INPUT.getCode()) && StringUtils.isEmpty(inputMobile)) {
			throw AppException.getInstance(AppCodeEnum._0001_ILLEGAL_PARAMETER);

		}
	}
}
