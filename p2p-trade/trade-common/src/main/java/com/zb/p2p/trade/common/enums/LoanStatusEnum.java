package com.zb.p2p.trade.common.enums;

public enum LoanStatusEnum {
    LOAN_UNMATCHED("LOAN_UNMATCHED", "借款待匹配"),
    PARTLY_LOAN_SUCCESS("PARTLY_LOAN_SUCCESS", "部分借款成功"),
    LOAN_SUCCESS("LOAN_SUCCESS", "借款成功"),
    LOAN_FAILED("LOAN_FAILED", "借款失败");

    private String code;
    private String desc;

    LoanStatusEnum(String code, String desc) {
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

    public static LoanStatusEnum getByCode(String code) {
        if (code == null) {
            return null;
        }

        for (LoanStatusEnum item : LoanStatusEnum.values()) {
            if (item.getCode().equals(code)) {
                return item;
            }
        }

        return null;
    }
}
