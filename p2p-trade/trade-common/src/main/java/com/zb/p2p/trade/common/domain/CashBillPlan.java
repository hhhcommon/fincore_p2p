package com.zb.p2p.trade.common.domain;

import com.zb.p2p.trade.common.enums.CashOverdueEnum;
import com.zb.p2p.trade.common.enums.CashStatusEnum;
import com.zb.p2p.trade.common.enums.RepaymentTypeEnum;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class CashBillPlan {

    private Long id;

    //产品编号
    private String productCode;

    // 借款用户id
    private String loanMemberId;

    //渠道
    private String saleChannel;

    // 期号
    private Integer stageSeq;

    // 应收本金
    private BigDecimal expectPrinciple;
    // 应收利息
    private BigDecimal expectInterest;

    // 实际兑付本金
    private BigDecimal cashAmount;
    // 实际兑付利息
    private BigDecimal cashIncome;

    // 预期兑付日期
    private Date expectDate;
    //兑付日期
    private Date cashDate;

    //兑付状态
    private CashStatusEnum status;

    // 还款状态,还款类型
    private CashOverdueEnum cashStatus;
    private RepaymentTypeEnum repayType;

    private Integer version;

    private Date createTime;

    private Date modifyTime;

    // 资产编号，原始资产编号
    private String assetNo;
    private String orgAssetNo;

    // 是否锁定
    private boolean lockTag = false;

    // 支付通道
    private String payChannel;

}