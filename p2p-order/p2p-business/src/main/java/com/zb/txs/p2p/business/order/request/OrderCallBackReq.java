/*
 * Copyright (c) 2017, 资邦金服(上海)网络科技有限公司. All Rights Reserved.
 *
 */

package com.zb.txs.p2p.business.order.request;

import lombok.Data;

import java.io.Serializable;

/**
 * Function:   订单支付回调 <br/>
 * Date:   2017年09月20日 下午9:48 <br/>
 *
 * @author liguoliang@zillionfortune.com
 */

@Data
public class OrderCallBackReq implements Serializable {

    /**
     * 订单流水号
     */
    private String orderId;

    /**
     * 短信验证码流水号
     */
    private String originalOrderNo;

    /**
     * 用户Id
     */
    private String memberId;

    /**
     * 下单金额
     */
    private String investAmount;

    /**
     * 支付流水号
     */
    private String payNo;

    private String payStatus;
    private String payCode;
    private String payMsg;

}
