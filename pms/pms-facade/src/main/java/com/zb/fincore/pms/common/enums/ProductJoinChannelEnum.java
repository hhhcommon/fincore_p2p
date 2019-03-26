package com.zb.fincore.pms.common.enums;

/**
 * Created by zhangxin on 2017/5/4 0011.
 */
public enum ProductJoinChannelEnum {

    ZB_LADDER("01", "资邦阶梯"),
    ZB_PERIODIC("02", "资邦定期");

    private String code;
    private String desc;

    ProductJoinChannelEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    /**
     * 通过code获取enum对象
     *
     * @param code
     * @return
     */
    public static ProductJoinChannelEnum getEnumItem(String code) {
        for (ProductJoinChannelEnum item : values()) {
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
