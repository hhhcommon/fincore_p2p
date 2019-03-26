package com.zb.p2p.paychannel.common.enums;

public enum LoanTypeEnum {
    PERSONAL("PERSONAL", "个人"),
    COMPANY("COMPANY", "企业");

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
