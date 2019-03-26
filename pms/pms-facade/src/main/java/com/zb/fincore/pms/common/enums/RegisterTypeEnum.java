package com.zb.fincore.pms.common.enums;

/**
 * Created by kaiyun on 2017/5/4 0011.
 */
public enum RegisterTypeEnum {

	/**
	 * AUTO:自动注册
	 */
	AUTO("AUTO", "自动注册"),
	/**
	 * UNAUTO:人工注册
	 */
	UNAUTO("UNAUTO", "人工注册");

    private String code;
    private String desc;

    RegisterTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    /**
     * 通过code获取enum对象
     *
     * @param code
     * @return
     */
    public static RegisterTypeEnum getEnumItem(String code) {
        for (RegisterTypeEnum item : values()) {
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
