package com.zb.p2p.customer.dao.domain;

import lombok.Data;

import java.util.Date;

@Data
public class OrgCustomerInfo {
    private Long orgId;

    private String orgName;

    private String channelMemberId;

    private String idCardType;

    private String idCardNo;

    private String ownerName;

    private String ownerIdCardType;

    private String ownerIdCardNo;

    private String telephone;

    private String channelCode;

    private Integer isReal;

    private String sourceId;

    private String accountNo;

    private String securityAccountNo;

    private String generalAccountNo;

    private String riskReserveAccountNo;

    private String authRepayAccountNo;

    private String channelFeeAccountNo;

    private String fundCollectAccountNo;

    private String repayAdminAccountNo;

    private Date createTime;

    private Date updateTime;

}