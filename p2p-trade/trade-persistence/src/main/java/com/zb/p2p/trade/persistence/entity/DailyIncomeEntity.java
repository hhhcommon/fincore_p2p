package com.zb.p2p.trade.persistence.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class DailyIncomeEntity {
    private Long id;

    private String memberId;

    private String refNo;

    private String productCode;

    private String assetNo;

    private BigDecimal interestAmount;

    private Date interestDate;

    private Date createTime;

    private String repayType;

    private Date modifyTime;

    private String modifyBy;

}