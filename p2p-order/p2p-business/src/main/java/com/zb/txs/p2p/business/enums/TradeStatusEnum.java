package com.zb.txs.p2p.business.enums;

import lombok.Getter;

/**
 * 支付接口回调状态枚举
 */
@Getter
public enum TradeStatusEnum {

    S("S"),//成功
    F("F"),//失败
    P("P");//处理中

    private TradeStatusEnum(String value) {
        this.value = value;
    }

    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    private static final TradeStatusEnum[] states = values();

    public static TradeStatusEnum get(String strValue) {
        for (TradeStatusEnum state : states) {
            if (state.getValue().equals(strValue)) {
                return state;
            }
        }
        return null;
    }
}
