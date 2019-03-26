package com.zb.p2p.paychannel.common.enums;
/**
 * 错误码<br/>
 *
 *
 * @author
 *
 */
public enum AppCodeEnum {
    RESPONSE_SUCCESS("0000", "响应成功"),
    RESPONSE_FAIL("9999", "响应失败"),
    RESPONSE_NOT_FUND("9000", "未找到信息"),
    RESPONSE_SERIAL_REPEAT("9001", "申请单号重复"),
    RESPONSE_PARAM_FAIL("9003", "参数校验失败"),
    RESPONSE_PARAM_PROCESSING("3T05", "处理中"),
    _0001_ILLEGAL_PARAMETER("0001","非法参数"),
    _0010_REDIS_LOCK_FAILED("0010","操作异常"),;


	private String code;
	private String message;
	private AppCodeEnum(String code, String message) {
		this.code = code;
		this.message = message;
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
	
}
