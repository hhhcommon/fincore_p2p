package com.zillionfortune.boss.common.dto;

import java.io.Serializable;

/**
 * Created by wangwanbin on 2017/1/9.
 */
public class TradeRespObj implements Serializable {
	/** 响应码 */
	private String code;
	/** 响应消息 */
	private String msg;
	
	/** 响应数据 */
	private Object data;
	
	
	public final static String SUCCESS="0000"; 

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

}
