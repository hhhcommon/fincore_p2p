package com.zb.p2p.paychannel.common.enums;

/**
 * @author
 * @create 2018-05-08 15:55
 */
public enum PaymentPattenEnum {
    DQLC("DQLC", "定期理财"),
    DEBJ("DEBJ", "等额本金"),
    DEBX("DEBX", "等额本息");
    private String code;
    private String desc;

    PaymentPattenEnum(String code, String desc) {
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
