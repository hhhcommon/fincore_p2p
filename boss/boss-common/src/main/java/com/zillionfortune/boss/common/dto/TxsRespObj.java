package com.zillionfortune.boss.common.dto;

import java.io.Serializable;

/**
 * Created by wangwanbin on 2017/1/9.
 */
public class TxsRespObj implements Serializable {
	/** 响应码 */
	private String code;
	/** 响应消息 */
	private String message;
	
	/** 响应数据 */
	private Object data;
	
	
	public final static String SUCCESS="200"; 

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

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

}
