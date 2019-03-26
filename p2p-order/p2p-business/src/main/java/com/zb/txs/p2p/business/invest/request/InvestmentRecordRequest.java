package com.zb.txs.p2p.business.invest.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class InvestmentRecordRequest implements Serializable {

    private String memberId;
    private Long lastId;
    private Long pageSize;
    private Integer transType;
    private String status;

}
