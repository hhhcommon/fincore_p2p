/* 
* Copyright (c) 2017, 资邦金服(上海)网络科技有限公司. All Rights Reserved.
*
*/

package com.zb.txs.p2p.business.order.request;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Function:   预投资 <br/>
 * Date:   2017年09月27日 下午8:32 <br/>
 *
 * @author liguoliang@zillionfortune.com
 */

@Data
public class AppointInvest implements Serializable {

    /**
     * 订单流水号（前端正式下单时需要携带）
     */
    private String orderNo;

    /**
     * 订单时间
     */
    private Long orderTime;

    /*   * 签约协议号
     */
    private String signId;

    /**
     * 交易金额
     */
    private BigDecimal tradeAmount;

    /**
     * 会员id
     */
    private String memberId;

    /**
     * 系统标识
     */
    private String sourceId;
}
