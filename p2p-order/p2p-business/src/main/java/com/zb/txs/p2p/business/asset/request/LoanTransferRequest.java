package com.zb.txs.p2p.business.asset.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoanTransferRequest implements Serializable {
    private String orderNo;             // 订单号      
    private Long orderTime;             // 订单时间    
    private String memberId;            // 会员id         投资人会员id。
    private String instMemberId;        // 会员id         机构会员id
    private BigDecimal tradeAmount;     // 交易金额         转账金额，精确到小数点2位
    private String loanOrderNo;         // 借款订单号      马上贷发起借款的订单号
    private String sourceId;            // 系统标识        MSD-马上贷
}