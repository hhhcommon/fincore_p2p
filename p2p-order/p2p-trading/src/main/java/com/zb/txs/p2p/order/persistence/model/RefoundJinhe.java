package com.zb.txs.p2p.order.persistence.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RefoundJinhe {
    //订单号
    private String orderNo;
    //订单时间
    private Long orderTime;
    //会员id
    private String memberId;
    //机构会员id
    private String instMemberId;
    //签约协议号
    private String signId;
    //交易金额
    private BigDecimal tradeAmount;
    //01-未匹配资产退款，02-逾期费退款
    private String tradeType;
    //系统标识
    private String sourceId;
    //原交易订单号
    private String originalOrderNo;//支付交易流水号
}
