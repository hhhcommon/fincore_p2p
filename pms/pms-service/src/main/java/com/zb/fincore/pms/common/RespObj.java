package com.zb.fincore.pms.common;

import java.io.Serializable;

/**
 * Created by wangwanbin on 2017/1/9.
 */
public class RespObj implements Serializable {
	/** 响应码 */
	private String code;
	/** 响应消息 */
	private String message;
	
	private boolean isinglobalmaintenance; 
	/** 响应数据 */
	private Object data;
	
	private String reason;
	
	public final static String SUCCESS="200"; 

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	
	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isIsinglobalmaintenance() {
		return isinglobalmaintenance;
	}

	public void setIsinglobalmaintenance(boolean isinglobalmaintenance) {
		this.isinglobalmaintenance = isinglobalmaintenance;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

}
