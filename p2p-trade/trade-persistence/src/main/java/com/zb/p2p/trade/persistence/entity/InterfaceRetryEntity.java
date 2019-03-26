package com.zb.p2p.trade.persistence.entity;

import lombok.Data;
import java.util.Date;

@Data
public class InterfaceRetryEntity {
    private Long id;

    private String businessType;

    private String businessNo;

    private String responseParam;

    private String status;

    private Integer retryTimes;

    private String memo;

    private String productCode;

    private Date createTime;

    private Date modifyTime;

    private String createBy;

    private String modifyBy;

    private String requestParam;

}