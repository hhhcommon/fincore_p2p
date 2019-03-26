/* 
* Copyright (c) 2017, 资邦金服(上海)网络科技有限公司. All Rights Reserved.
*
*/

package com.zb.txs.p2p.business.order.request;

import lombok.Data;

import java.io.Serializable;

/**
 * Function:   确认投资 <br/>
 * Date:   2017年09月29日 下午1:58 <br/>
 *
 * @author liguoliang@zillionfortune.com
 */

@Data
public class ConfirmInvest implements Serializable {

    /**
     * 订单号
     */
    private String orderNo;

    /**
     * 订单时间
     */
    private Long orderTime;

    /**
     * 会员id
     */
    private String memberId;

    /**
     * 原交易订单号
     */
    private String originalOrderNo;

    /**
     * 短信验证码
     */
    private String smsCode;

    /**
     * 系统标识
     */
    private String sourceId;
}
