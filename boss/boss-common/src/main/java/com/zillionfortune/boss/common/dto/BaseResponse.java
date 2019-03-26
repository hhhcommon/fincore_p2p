/*
 * Copyright (c) ${year}, 资邦金服（上海）网络科技有限公司. All Rights Reserved.
 *
 */
package com.zillionfortune.boss.common.dto;

import java.io.Serializable;

/**
 * ClassName: BaseResponse <br/>
 * Function: 相应对象基础类封装. <br/>
 * Date: 2016年11月8日 上午9:48:40 <br/>
 *
 * @author Administrators
 * @version 
 * @since JDK 1.7
 */
public class BaseResponse implements Serializable {

    private static final long serialVersionUID = 92562941371458897L;
 
    /**
     * 响应状态编码
     * 必输
     */
    protected String respCode;

    /** 业务处理结果编码 */
    protected String resultCode;
    
    /** 业务处理结果描述 */
    protected String resultMsg;
    
    public BaseResponse() {
		
	}

	public BaseResponse(String respCode) {
		super();
		this.respCode = respCode;
	}


	public BaseResponse(String respCode, String resultMsg) {
		super();
		this.respCode = respCode;
		this.resultMsg = resultMsg;
	}

	public BaseResponse(String respCode, String resultCode,String resultMsg) {
		super();
		this.respCode = respCode;
		this.resultCode = resultCode;
		this.resultMsg = resultMsg;
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
	
}
