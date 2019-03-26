package com.zb.p2p.trade.persistence.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Function:查询兑付信息
 * Author: created by liguoliang
 * Date: 2017/9/8 0008 下午 4:29
 * Version: 1.0
 */
@Data
public class CashRecordDto {

    // 产品编号
    private String productCode;

    // 资产编号
    private String assetNo;

    // 借款会员ID
    private String loanMemberId;

    // 还款日（预期回款日期）
    private Date expectDate;

    // 实际兑付日期
    private Date cashDate;

    //渠道
    private String saleChannel;

    //兑付本金
    private BigDecimal cashAmount;

    //兑付利息
    private BigDecimal cashIncome;

    //兑付状态
    private String status;
}
