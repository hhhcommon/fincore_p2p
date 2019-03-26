package com.zb.p2p.trade.common.enums;

public enum WithdrawFlagEnum {
    WITHDRAWING("WITHDRAWING", "提现中"),
    WITHDRAW_SUCCESS("WITHDRAW_SUCCESS", "提现成功"),
    WITHDRAW_FAIL("WITHDRAW_FAIL", "提现失败");

    private String code;
    private String desc;

    WithdrawFlagEnum(String code, String desc) {
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

    public static String getStatusDesc(String code) {
        for (WithdrawFlagEnum item : values()) {
            if (item.getCode().equals(code)) {
                return item.getDesc();
            }
        }
        return null;
    }
}
