package com.zb.txs.p2p.order.persistence.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class TradeOrder {
    private Long id;

    private Long registerId;

    private String memberId;

    private String productCode;

    private String saleChannel;

    private BigDecimal investAmount;

    private BigDecimal matchAmount;

    private String matchStatus;

    private Date matchTime;

    private String tradeNo;

    private String payStatus;

    private String payCode;

    private String payMsg;

    private Date payTime;

    private String payNo;

    private String refundStatus;

    private Date refundTime;

    private String refundNo;

    private String cashStatus;

    private Date cashTime;

    private BigDecimal cashAmount;

    private String hisFlag;

    private String productCategory;

    private String categoryName;

    private Integer version;

    private Date createTime;

    private Date modifyTime;

    private String createBy;

    private String modifyBy;

    /**
     * 是否售罄标识
     * 售罄标识  Y:是;N:否
     */
    private String soldOutFlag;


    private String transType;
    private Date startDate;
    private Date endDate;

}