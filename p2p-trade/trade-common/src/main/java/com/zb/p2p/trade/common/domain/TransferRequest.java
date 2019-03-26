package com.zb.p2p.trade.common.domain;

import com.zb.p2p.trade.common.enums.TransferExitStatusEnum;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class TransferRequest {
    private Long id;

    // 退出请求号
    private String requestNo;

    private String memberId;

    private String productCode;

    // 退出订单号(首投订单号)
    private String orderNo;

    // 退出本金
    private BigDecimal principle;

    private String promiseInterest;

    // 实际总利息
    private BigDecimal actualInterest;

    private TransferExitStatusEnum status;

    private Date requestTime;

    private Date finishedTime;

    private String memo;

    private Integer version;

    private Date createTime;

    private Date modifyTime;

}