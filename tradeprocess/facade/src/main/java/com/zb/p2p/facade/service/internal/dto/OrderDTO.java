package com.zb.p2p.facade.service.internal.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * created by fangyang on 2017/9/6 10:08.
 * email:fangyang@zillionfortune.com
 * version:1.0
 * describe:
 */
@Data
public class OrderDTO {
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

    private Date createTime;

    private Date modifyTime;

    private String createBy;

    private String modifyBy;

    private String name;

    private String certNo;

    private String telNo;
}
