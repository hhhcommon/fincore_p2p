package com.zb.txs.p2p.business.enums;

import lombok.Getter;

/**
 * houlintao 交易记录status
 */
@Getter
public enum TransactionLogStatusEnum {
    START("START"),//发起
    PROCESSING("PROCESSING"),//处理中
    FAIL("FAIL"),//失败
    SUCCESS("SUCCESS");//成功

    private TransactionLogStatusEnum(String value) {
        this.value = value;
    }

    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    private static final TransactionLogStatusEnum[] states = values();

    public static TransactionLogStatusEnum get(String strValue) {
        for (TransactionLogStatusEnum state : states) {
            if (state.getValue().equals(strValue)) {
                return state;
            }
        }
        return null;
    }
}
