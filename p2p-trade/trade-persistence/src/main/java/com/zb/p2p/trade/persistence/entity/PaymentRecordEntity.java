package com.zb.p2p.trade.persistence.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by limingxin on 2018/1/11.
 */
@Data
public class PaymentRecordEntity {
    private Long id;
    private String refId, tradeType, payCode, payMsg, modifyBy;
    private String memberId;
    private BigDecimal amount;
    /* 业务流水号 */
    private String tradeSerialNo;
    private String orgTradeSerialNo;
    private String payNo;
    private String tradeStatus, tradeMsg;
    private String payChannel;

    private Integer version;
    private String memo;
    private Date startTime, endTime, createTime, modifyTime;
}
