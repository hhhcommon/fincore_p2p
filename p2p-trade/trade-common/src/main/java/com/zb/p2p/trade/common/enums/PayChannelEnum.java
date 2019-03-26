package com.zb.p2p.trade.common.enums;

/**
 * 支付渠道
 * @author Vinson
 *
 */
public enum PayChannelEnum {

    SINA("XL", "1", "新浪"),

    BAOFU("BF", "2", "宝付");

    private String code;
    private String argus;
    private String desc;

    PayChannelEnum(String code, String argus, String desc) {
        this.code = code;
        this.argus = argus;
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

    public String getArgus() {
        return argus;
    }

    public static PayChannelEnum getByCode(String code) {
        if (code == null) {
            return null;
        }

        for (PayChannelEnum item : PayChannelEnum.values()) {
            if (item.getCode().equals(code)) {
                return item;
            }
        }

        return null;
    }

}
