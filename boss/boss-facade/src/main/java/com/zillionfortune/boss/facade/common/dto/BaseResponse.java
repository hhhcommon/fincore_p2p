package com.zillionfortune.boss.facade.common.dto;

import java.io.Serializable;

public class BaseResponse implements Serializable{

	private static final long serialVersionUID = 1L;
	
	/** 响应状态编码  */
    protected String respCode;

    /** 业务处理结果编码  */
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
