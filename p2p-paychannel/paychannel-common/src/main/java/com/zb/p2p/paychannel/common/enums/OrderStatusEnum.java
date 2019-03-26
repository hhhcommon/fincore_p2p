package com.zb.p2p.paychannel.common.enums;

public enum OrderStatusEnum {
    WAIT_UNMATCHED("WAIT_UNMATCHED", "待匹配"),
    MATCH_SUCCESS("MATCH_SUCCESS", "匹配成功"),
    PARTLY_MATCH_SUCCESS("PARTLY_MATCH_SUCCESS", "部分匹配成功"),
    MATCH_FAIL("MATCH_FAIL", "匹配失败");
    private String code;
    private String desc;

    OrderStatusEnum(String code, String desc) {
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
