package com.zb.p2p.paychannel.common.enums;

public enum MatchRecordStatusEnum {
    MATCH_SUCCESS("MATCH_SUCCESS", "预匹配成功"),
    MATCH_FAIL("MATCH_FAIL", "匹配失败");

    private String code;
    private String desc;

    MatchRecordStatusEnum(String code, String desc) {
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
