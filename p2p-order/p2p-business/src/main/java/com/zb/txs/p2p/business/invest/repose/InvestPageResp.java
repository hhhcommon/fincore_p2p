package com.zb.txs.p2p.business.invest.repose;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class InvestPageResp implements Serializable {
    private String orderId;

    private String memberId;

    private String transType;

    private BigDecimal amount;

    private String status;

    private Date transTime;

    private String hisFlag;

    private String investName;

}
