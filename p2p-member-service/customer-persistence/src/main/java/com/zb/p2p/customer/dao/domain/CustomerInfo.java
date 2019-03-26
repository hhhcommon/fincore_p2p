package com.zb.p2p.customer.dao.domain;

import lombok.Data;

import java.util.Date;

/**
 * 子会员信息
 */
@Data
public class CustomerInfo {
    private Long customerId;

    private String memberId;

    private String memberType;

    private String mobile;

    private String realName;

    private Integer isReal;

    private String idCardType;

    private String idCardNo;

    private String loginPwd;

    private Integer isActiveEAccount;

    private Integer isBindCard;

    private Integer buyFixedProductStatus;

    private Integer buyFreshProductStatus;

    private Date registerTime;

    private String channelCustomerId;

    private String channelCode;

    private String accountNo;

    private Integer isDepositManage;

    private String eBankAccount;

    private Date createTime;

    private Date updateTime;

}