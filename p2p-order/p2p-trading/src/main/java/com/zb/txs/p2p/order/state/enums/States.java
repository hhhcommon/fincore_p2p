package com.zb.txs.p2p.order.state.enums;

/**
 * Function:   状态枚举 <br/>
 * Date:   2017年09月24日 下午3:45 <br/>
 *
 * @author liguoliang@zillionfortune.com
 */

public enum States {
    //初始化
    INIT_APPOINT("INIT_APPOINT"),
    //待预约支付
    PENDING_APPOINT_PAYMENT("PENDING_APPOINT_PAYMENT"),
    //预约支付失败
    APPOINT_PAY_FAILURE("APPOINT_PAY_FAILURE"),
    //预约支付处理中
    APPOINT_PAY_PROCESSING("APPOINT_PAY_PROCESSING"),
    //待确认预约
    PENDING_CONFIRM_APPOINT("PENDING_CONFIRM_APPOINT"),
    //预约确认失败
    APPOINT_CONFIRM_FAILURE("APPOINT_CONFIRM_FAILURE"),
    //匹配中
    MATCHING("MATCHING"),
    //已成交
    DEAL_DONE("DEAL_DONE");

    String value;
    private static final States[] states = values();

    public static States get(String strValue) {
        for (States state : states) {
            if (state.getValue().equals(strValue)) {
                return state;
            }
        }
        return null;
    }

    States(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}