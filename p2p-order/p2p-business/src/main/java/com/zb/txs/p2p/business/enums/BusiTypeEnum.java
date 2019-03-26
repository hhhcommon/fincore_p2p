package com.zb.txs.p2p.business.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public enum BusiTypeEnum {
    INVESTMENT("10", "预约投资"),
    LOAN("20", "放款（放款转账、放款代付，放款代付重试）"),
    REFUND("30", "还款（正常还款、逾期还款、备付金还款、正常代还款，逾期代还款）"),
    DRAWBACK("40", "退款（包含退款重试）"),
    CASH("50", "兑付（包含兑付重试）"),
    DIFFERENCE("60", "尾差"),
    ADD_AMOUNT("70", "补账"),
    TO_PUBLIC("80", "对公充值代扣"),;

    private final String code;
    private final String desc;
}
