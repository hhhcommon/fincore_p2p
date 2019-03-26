package com.zb.p2p.paychannel.validator;

import com.zb.p2p.paychannel.common.enums.AppCodeEnum;
import com.zb.p2p.paychannel.common.exception.AppException;
import org.apache.commons.lang.StringUtils;



public abstract class BaseValidator<T> {
	
	
	public abstract void validate(T obj);
	
	protected void required(String ... array){
		
		for(String a : array){
			if(StringUtils.isEmpty(a)){
				throw AppException.getInstance(AppCodeEnum._0001_ILLEGAL_PARAMETER);
			}
		}
		
	}
}
