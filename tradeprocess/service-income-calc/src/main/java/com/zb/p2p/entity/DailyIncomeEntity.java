package com.zb.p2p.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class DailyIncomeEntity {
    private Long id;

    private String memberId;

    private String refNo;

    private String productCode;

    private BigDecimal interestAmount;

    private Date interestDate;

    private Date createTime;

    private String createBy;

    private Date modifyTime;

    private String modifyBy;

}