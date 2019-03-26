package com.zb.txs.p2p.order.persistence.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.zb.txs.p2p.order.state.enums.States;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Appointment {
    private Long id;

    private Long accountId;

    private Long memberId;

    private String productCode;

    private Long productId;

    private String productTitle;

    private Integer duration;

    private BigDecimal amount;

    private BigDecimal matchAmount;

    private States status = States.INIT_APPOINT;

    //预约确认支付流水号
    private String tradeRegisterNo;

    //天鼋交易系统回调的预约申请单号
    private String finCoreRegisterNo;

    private Date paySuccessTime;

    private Date created;

    private String createdBy;

    private Date modified;

    private String modifiedBy;
}