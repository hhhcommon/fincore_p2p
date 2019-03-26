package com.zb.txs.p2p.order.persistence.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RefoundTradeRequest {

    //原交易订单号
    private String orderNo;
    //会员id
    private String memberId;
    //10-预约投资，20-放款（放款转账、放款代付，放款代付重试），30-还款（正常还款、逾期还款、备付金还款、正常代还款，逾期代还款），40-退款（包含退款重试）、50-兑付（包含兑付重试），60-尾差，70-补账，80-对公充值代扣
    private String busiType;
    //sourceId
    private String sourceId;
}
