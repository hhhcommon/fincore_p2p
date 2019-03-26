package com.zb.p2p.paychannel.common.enums;

/**
 * @author
 * @create 2018-05-08 15:55
 */
public enum OrderTypeEnum {
    YS("YS", "原始订单"),
    FT("FT", "复投订单"),
    BF("BF", "部分匹配订单"),
    FTBF("FTBF", "复投部分匹配订单");
    private String code;
    private String desc;

    OrderTypeEnum(String code, String desc) {
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
}
