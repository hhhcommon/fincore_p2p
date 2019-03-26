package com.zb.p2p.customer.dao.domain;

import lombok.Data;

import java.util.Date;

/**
 * 会员主体
 */
@Data
public class MemberBasicInfo {
    private String memberId;

    private String mobile;

    private String realName;

    private Integer isReal;

    private String idCardType;

    private String idCardNo;

    private String loginPwd;

    private Date registerTime;

    private String channelCustomerId;

    private String channelCode;

    private Date createTime;

    private Date updateTime;

}