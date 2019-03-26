package com.zb.p2p.customer.service.bo;

public class MessageCenterResp {

	/**
	 * 200：请求成功 400：请求参数错误, 500：系统错误, 903：没有对应的消息模板 902：无权限访问对应的NodeKey
	 * 904：找不到topic 905：参数reqId不能重复 906：没有关联的[营销类]配置信息 907：无权限访问通道 999：其它异常
	 */

	private String code;
	private String message;
	private String success;
	private String failure;

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

	public String getSuccess() {
		return success;
	}

	public void setSuccess(String success) {
		this.success = success;
	}

	public String getFailure() {
		return failure;
	}

	public void setFailure(String failure) {
		this.failure = failure;
	}

}
