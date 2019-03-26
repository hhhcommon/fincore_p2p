package com.zb.p2p.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class CashRecordEntity {
    private Long id;

    //请求流水号,外部订单号
    private String reqNo, extOrderNo;

    //产品编号
    private String productCode;

    //持仓单编号
    private String accountNo;

    //投资用户ID 借款用户id
    private String memberId, loanMemberId;

    //渠道
    private String saleChannel;

    //兑付总本金
    private BigDecimal cashAmount;

    //兑付总利息
    private BigDecimal cashIncome;

    //兑付日期
    private Date cashDate;

    //兑付状态：INIT:待处理；CASHING:兑付中；CASH_SUCCESS:兑付成功；CASH_FAIL:兑付失败；
    private String status;

    //兑付类型,回款类型，关联编号
    private String cashStatus, repayType, refNo;

    private Integer version;

    private Date createTime;

    private String createBy;

    private Date modifyTime;

    private String modifyBy;

    //其他信息
    private BigDecimal totalAmount;//计算后的总金额
    //产品信息
    private BigDecimal yield;//年化收益率
    private Integer productDays;//产品天数
    private Integer interestDays;//计息天数(默认365)
}