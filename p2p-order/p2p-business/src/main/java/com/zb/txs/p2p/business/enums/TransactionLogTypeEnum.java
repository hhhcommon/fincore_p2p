package com.zb.txs.p2p.business.enums;


import lombok.Getter;

/**
 * houlintao 交易记录类型
 */
@Getter
public enum TransactionLogTypeEnum {
    //退款（日切）
    REFOUND("退款", 0),
    //预约投资
    RESERVATION("预约投资", 3),
    //兑付（投资到期偿还本金和利息）
    REPAYMENT("兑付", 4);

    private String name;
    private Integer value;

    TransactionLogTypeEnum(String name, Integer value) {
        this.name = name;
        this.value = value;
    }
}
