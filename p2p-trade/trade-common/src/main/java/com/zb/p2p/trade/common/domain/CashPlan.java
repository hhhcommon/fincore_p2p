package com.zb.p2p.trade.common.domain;

import com.zb.p2p.trade.common.enums.CashOverdueEnum;
import com.zb.p2p.trade.common.enums.CashStatusEnum;
import com.zb.p2p.trade.common.enums.RepaymentTypeEnum;
import com.zb.p2p.trade.common.enums.YesNoEnum;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class CashPlan {

    private Long id;

    //请求流水号,投资订单号
    private String reqNo;
    private String extOrderNo;

    //产品编号
    private String productCode;

    //投资用户ID 借款用户id
    private String memberId, loanMemberId;

    //渠道
    private String saleChannel;

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
    // 期号
    private int stageSeq;

    //兑付状态
    private CashStatusEnum status;

    // 还款状态,还款类型
    private CashOverdueEnum cashStatus;
    private RepaymentTypeEnum repayType;

    // 债权Id，原为匹配记录编号
    private String creditorId;

    private Integer version;

    private Date createTime;

    private Date modifyTime;

    // 兑付模板Id
    private Long billPlanId;

    // 资产编号，原始资产编号
    private String assetNo;
    private String orgAssetNo;

    // 是否锁定
    private boolean lockTag = false;
    // 当期待结利息
    private BigDecimal payingInterest;
    // 支付渠道
    private String payChannel;

}