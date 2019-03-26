/*
 * Copyright (c) 2017, 资邦金服(上海)网络科技有限公司. All Rights Reserved.
 *
 */

package com.zb.p2p.trade.api.req;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * Date:   2018年04月20日 下午9:48 <br/>
 *
 * @author liguoliang@zillionfortune.com
 */

@Data
public class PayFundRecordReq implements Serializable {

    /** 订单号 */
    private String orderNo;
    /** 订单时间  yyyy-MM-dd HH:mm:ss */
    private String orderTime;
    /** 借款人会员ID */
    private String loanerMemberId;
    /** 出借人会员ID */
    private String investorMemberId;
    /** 金额 */
    private String amount;
    /** 借款订单号 */
    private String loanOrderNo;
    /** 投资订单号 */
    private String investOrderNo;
    /** 渠道类型 */
    private String channelType;
    /** 业务类型 */
    private String businessType;
    /** 开户行省名， 宝付必填 */
    private String branchBankProvince;
    /** 开户行市名， 宝付必填 */
    private String branchBankCity;
    /** 开户行机构名， 宝付必填 */
    private String branchBankInst;
    /** 收款人银行名称， 宝付必填 */
    private String bankName;
    /** 收款人银行卡号， 宝付必填 */
    private String bankAccountNo;
    /** 收款人姓名， 宝付必填 */
    private String memberName;
    /** 异步通知url */
    private String notifyUrl;

}
