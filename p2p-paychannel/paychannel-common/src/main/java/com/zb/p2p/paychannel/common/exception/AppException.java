package com.zb.p2p.paychannel.common.exception;

import com.zb.p2p.paychannel.common.enums.AppCodeEnum;

public class AppException extends RuntimeException{

	private static final long serialVersionUID = -5068507568724461060L;

	private String code;
	private String message;
	
	private AppCodeEnum appCodeEnum;
	
	public static AppException getInstance(AppCodeEnum appCodeEnum){
		return new AppException(appCodeEnum);
	}
	public static AppException getInstance(String code,String message){
		return new AppException(code, message);
	}
	
	private AppException(String code,String message) {
		super(message);
		this.code = code;
		this.message = message;
		
	}
	public AppException(AppCodeEnum appCodeEnum) {
		super(appCodeEnum.getMessage());
		this.code = appCodeEnum.getCode();
		this.message = appCodeEnum.getMessage();
		this.appCodeEnum = appCodeEnum;
	}

	public AppException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public AppException(String message, Throwable cause) {
		super(message, cause);
	}

	public AppException(String message) {
		super(message);
	}

	public AppException(Throwable cause) {
		super(cause);
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public AppCodeEnum getAppCodeEnum() {
		return appCodeEnum;
	}

	public void setAppCodeEnum(AppCodeEnum appCodeEnum) {
		this.appCodeEnum = appCodeEnum;
	}
	
	

}
