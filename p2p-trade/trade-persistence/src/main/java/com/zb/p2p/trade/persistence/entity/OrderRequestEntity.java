package com.zb.p2p.trade.persistence.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class OrderRequestEntity {
    private Long id;

    private String memberId;

    private String extOrderNo;

    private String orderNo;

    private String productCode;

    private String saleChannel;

    private BigDecimal investAmount;

    private BigDecimal matchedAmount;

    private Date orderTime;

    private String status;

    private Integer version;

    /*系统创建时间*/
    private Date createTime;

    private Date modifyTime;

    private String createBy;

    private String modifyBy;

    private String name;

    private String certNo;

    private String telNo;
    
    private BigDecimal totalIncome;

}