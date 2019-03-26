package com.zb.p2p.customer.common.enums;
/**
 * OTP业务编码
 * @author liujia
 *
 */
public enum OtpBusiCodeEnum {
	/* 注册 */
	_01_REGISTE("01","注册"),
	/* 登录 */
	_02_LOGIN("02","登录"),
	/* 激活 */
	_03_ACTIVATE("03","激活"),
	/* 绑卡 */
	_04_BINDCARD("04","绑卡"),
	/* 解绑 */
	_05_UNBINDCARD("05","解绑"),
	/* 充值 */
	_06_RECHARGE("06","充值"),
	/* 提现 */
	_07_WITHDRAW("07","提现"),
	/* 活期申购 */
	_08_CURRENT_APPLY("08","活期申购"),
	/* 活期赎回 */
	_09_CURRENT_REDEEM("09","活期赎回"),
	/* 定期申购 */
	_10_FIXED_APPLY("10","定期申购"),
	/* 申请转让 */
	_11_TRANSFER_APPLY("11","申请转让"),
	/* 购买转让 */
	_12_TRANSFER_BUY("12","购买转让")
	
	;
	
	private String code;
	private String desc;
	
	private OtpBusiCodeEnum(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	
	public static void main(String[] args) {
		OtpBusiCodeEnum[] values = OtpBusiCodeEnum.values();
		for(OtpBusiCodeEnum val : values){
			String s = val.getCode() +"-"+val.getDesc();
			System.out.print(s+",");
		}
	}
}
