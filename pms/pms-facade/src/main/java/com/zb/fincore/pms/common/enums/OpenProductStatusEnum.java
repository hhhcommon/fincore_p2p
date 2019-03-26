package com.zb.fincore.pms.common.enums;

/**
 * Created by kaiyun on 2017/5/4 0011.
 */
public enum OpenProductStatusEnum {

	/**
	 * INIT:初始化
	 */
	INIT("INIT", "初始化"),
	/**
	 * SUCCESS:成功
	 */
	SUCCESS("SUCCESS", "成功"),
	/**
	 * FAIL:失败
	 */
	FAIL("FAIL", "失败")
	;

    private String code;
    private String desc;

    OpenProductStatusEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    /**
     * 通过code获取enum对象
     *
     * @param code
     * @return
     */
    public static OpenProductStatusEnum getEnumItem(String code) {
        for (OpenProductStatusEnum item : values()) {
            if (item.getCode().equals(code)) {
                return item;
            }
        }
        return null;
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
}
