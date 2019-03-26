package com.zb.p2p.paychannel.dao.domain;

import lombok.Data;
import java.util.Date;

/**
 * 接口重试entity
 */
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