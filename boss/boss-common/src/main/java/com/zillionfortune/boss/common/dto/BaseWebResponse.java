/*
 * Copyright (c) ${year}, 资邦金服（上海）网络科技有限公司. All Rights Reserved.
 *
 */
package com.zillionfortune.boss.common.dto;

import java.io.Serializable;

import com.zillionfortune.boss.common.enums.ResultCode;

/**
 * ClassName: BaseWebResponse <br/>
 * Function: 相应对象基础类封装. <br/>
 * Date: 2016年11月8日 上午9:48:40 <br/>
 *
 * @author Administrators
 * @version 
 * @since JDK 1.7
 */
public class BaseWebResponse extends BaseResponse implements Serializable {

    private static final long serialVersionUID = 92562941371458897L;
 
    protected Object data;

    public BaseWebResponse() {
		
	}

	public BaseWebResponse(String respCode) {
		super(respCode);
	}


	public BaseWebResponse(String respCode, String resultMsg) {
		super(respCode,resultMsg);
	}

	public BaseWebResponse(String respCode, String resultCode,String resultMsg) {
		super(respCode,resultCode,resultMsg);
	}

	public String getRespCode() {
		return respCode;
	}

	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getResultMsg() {
		return resultMsg;
	}

	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
	
}
