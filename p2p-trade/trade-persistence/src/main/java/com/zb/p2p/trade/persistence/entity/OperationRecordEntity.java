package com.zb.p2p.trade.persistence.entity;

import lombok.Data;

import java.util.Date;

@Data
public class OperationRecordEntity {
    private Long id;

    private String operationType;

    private String referId;

    private Date createTime;

    private Date modifyTime;

    private String createBy;

    private String modifyBy;

}