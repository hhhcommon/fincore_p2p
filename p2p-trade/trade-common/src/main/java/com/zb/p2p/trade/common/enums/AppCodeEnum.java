package com.zb.p2p.trade.common.enums;
/**
 * 错误码<br/>
 * 前两位表示业务分类，后两位自定义
 * 如：1001, 10表示积分商城  01表示商品已存在
 * @author liujia
 *
 */
public enum AppCodeEnum {
	//公共参数
	_0000_SUCCESS("0000","成功"),
	_9999_ERROR("9999","操作异常"),
	_0001_ILLEGAL_PARAMETER("0001","非法参数"),
	/** md5计算异常 */
	_1111_ERROR("1111","操作异常"),
	_0010_REDIS_LOCK_FAILED("0010","操作异常"),

    _4001_SOCKET_CALL_FAILED("4001","网络超时，请稍后重试"),

	BUZ_SERIAL_REPEAT("5001", "业务流水号重复")

	;
	
	private String code;
	private String message;
	AppCodeEnum(String code, String message) {
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
