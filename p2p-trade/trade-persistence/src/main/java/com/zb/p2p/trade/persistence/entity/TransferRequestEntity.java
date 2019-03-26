package com.zb.p2p.trade.persistence.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class TransferRequestEntity {
    private Long id;

    private String requestNo;

    private String memberId;

    private String productCode;

    private String orderNo;

    private BigDecimal principle;

    private String promiseInterest;

    private BigDecimal actualInterest;

    private String status;

    private Date requestTime;

    private Date finishedTime;

    private String memo;

    private Integer version;

    private Date createTime;

    private Date modifyTime;

}