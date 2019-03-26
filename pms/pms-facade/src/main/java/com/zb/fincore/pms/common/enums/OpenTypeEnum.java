package com.zb.fincore.pms.common.enums;

/**
 * Created by kaiyun on 2017/5/4 0011.
 */
public enum OpenTypeEnum {

	/**
	 * IN:对内
	 */
	IN("IN", "对内"),
	/**
	 * OUT:对外
	 */
	OUT("OUT", "对外")
	;

    private String code;
    private String desc;

    OpenTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    /**
     * 通过code获取enum对象
     *
     * @param code
     * @return
     */
    public static OpenTypeEnum getEnumItem(String code) {
        for (OpenTypeEnum item : values()) {
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
