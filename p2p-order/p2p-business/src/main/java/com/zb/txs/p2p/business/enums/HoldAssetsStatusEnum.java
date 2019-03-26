package com.zb.txs.p2p.business.enums;

import lombok.Getter;

/**
 * 用户持仓状态
 * Created by liguoliang on 2017/9/26.
 */
@Getter
public enum HoldAssetsStatusEnum {
    //匹配中
    MATCHING("MATCHING")
    //支付中
    , PAYING("PAYING"),
    //持有中
    HOLDING("HOLDING"),
    //回款中
    CASHING("CASHING"),
    //已回款
    CASH_SUCCESS("CASH_SUCCESS"),
    //投资失败
    INVEST_FAIL("INVEST_FAIL"),
    //匹配失败
    MATCH_FAIL("MATCH_FAIL"),

    //支付失败
    PAY_FAIL("PAY_FAIL"),
    //成交已还款
    DONE_REPAYMENT("DONE_REPAYMENT"),
    //未成交已还款
    UNDONE_REPAYMENT("UNDONE_REPAYMENT");


    String value;
    private static final HoldAssetsStatusEnum[] states = values();

    public static HoldAssetsStatusEnum get(String strValue) {
        for (HoldAssetsStatusEnum state : states) {
            if (state.getValue().equals(strValue)) {
                return state;
            }
        }
        return null;
    }

    HoldAssetsStatusEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
