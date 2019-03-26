package com.zb.fincore.pms.common.enums;

/**
 * Created by niuzhanjun on 2017/1/11 0011.
 */
public enum PayChannelEnum {

	/** BF - 宝付 */
    BF("BF", "宝付"),
    /** XL - 新浪 */
    XL("XL", "新浪");

    private String code;
    private String desc;

    PayChannelEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    /**
     * 通过code获取enum对象
     *
     * @param code
     * @return
     */
    public static PayChannelEnum getEnumItem(String code) {
        for (PayChannelEnum item : values()) {
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
