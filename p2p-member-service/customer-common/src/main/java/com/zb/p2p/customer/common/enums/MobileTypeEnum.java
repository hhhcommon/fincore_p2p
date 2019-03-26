package com.zb.p2p.customer.common.enums;
/**
 * 手机号类型
 * @author liujia
 *
 */
public enum MobileTypeEnum {
	/* 注册手机号 */
	_01_REGISTE("01","注册手机号"),
	/* 绑卡预留手机号 */
	_02_BINDCARD("02","绑卡预留手机号"),
	/* 会员录入手机号 */
	_90_CUSTOMER_INPUT("90","会员录入手机号"),
	;
	
	private String code;
	private String desc;
	
	private MobileTypeEnum(String code, String desc) {
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
	
	public static MobileTypeEnum getByCode(String code) {
		MobileTypeEnum[] values = MobileTypeEnum.values();
		for(MobileTypeEnum val : values){
			if(val.getCode().equals(code)) {
				return val;
			}
		}
		return null;
	}
	
	public static void main(String[] args) {
		MobileTypeEnum[] values = MobileTypeEnum.values();
		for(MobileTypeEnum val : values){
			String s = val.getCode() +"-"+val.getDesc();
			System.out.print(s+",");
		}
	}
}
