package com.zb.p2p.trade.common.enums;

public enum MatchStatusEnum {
    MATCH_SUCCESS("MATCH_SUCCESS", "预匹配成功"),
    MATCH_FAIL("MATCH_FAIL", "匹配失败");

    private String code;
    private String desc;

    MatchStatusEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static MatchStatusEnum getByCode(String code) {
        if (code == null) {
            return null;
        }

        for (MatchStatusEnum item : MatchStatusEnum.values()) {
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
