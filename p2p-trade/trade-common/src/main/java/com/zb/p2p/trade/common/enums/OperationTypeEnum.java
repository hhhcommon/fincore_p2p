package com.zb.p2p.trade.common.enums;

public enum OperationTypeEnum {
    LOAN_APPLY("LOAN_APPLY", "借款申请"),
    LOAN_APPLY_EXCEPTION("LOAN_APPLY_EXCEPTION", "借款申请异常"),

    RESERVATION_INVEST_APPLY("RESERVATION_INVEST_APPLY", "预约投资申请"),
    RESERVATION_INVEST_APPLY_EXCEPTION("RESERVATION_INVEST_APPLY_EXCEPTION", "预约投资申请异常"),

    INVEST_APPLY("INVEST_APPLY", "投资申请"),
    INVEST_APPLY_EXCEPTION("INVEST_APPLY_EXCEPTION", "投资申请异常"),

    RECHARGE_APPLY("RECHARGE_APPLY", "充值申请"),
    WITHDRAWALS_APPLY("WITHDRAWALS_APPLY", "提现申请"),

    CASH_REQUEST("CASH_REQUEST", "兑付请求"),
    ASSET_MATCH_NOTIFY_TXS("ASSET_MATCH_NOTIFY_TXS", "资产匹配通知唐小僧"),
    CASH_REQUEST_EXCEPTION("CASH_REQUEST_EXCEPTION", "兑付请求异常"),

    DAILY_CUT("DAILY_CUT", "日切");

    private String code;
    private String desc;

    OperationTypeEnum(String code, String desc) {
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
