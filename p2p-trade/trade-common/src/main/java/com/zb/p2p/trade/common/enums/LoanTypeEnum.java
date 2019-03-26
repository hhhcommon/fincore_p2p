package com.zb.p2p.trade.common.enums;

/**
 * Created by liguoliang on 2017/4/7.
 */
public enum LoanTypeEnum {
    PERSONAL("PERSONAL", "个人借款"),
    COMPANY("COMPANY", "企业借款");

    private String code;
    private String desc;

    LoanTypeEnum(String code, String desc) {
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
