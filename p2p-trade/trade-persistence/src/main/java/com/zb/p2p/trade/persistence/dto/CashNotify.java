package com.zb.p2p.trade.persistence.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * Created by limingxin on 2018/1/9.
 */
@Data
public class CashNotify {

    String loanNo;//借款单号
    // 当前资产编号
    String assetNo;
    String memberId;//投资人id
    BigDecimal cashAmount, cashIncome;//兑付金额 兑付利息
    BigDecimal totalLoanCharge;//兑付手续费
    String extOrderNo;//外部订单编号
    String cashCommand;//=1可发提现指令，=0继续等待
    String productCode;
}
