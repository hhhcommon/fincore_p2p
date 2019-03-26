/* 
* Copyright (c) 2017, 资邦金服(上海)网络科技有限公司. All Rights Reserved.
*
*/

package com.zb.txs.p2p.business.order.response;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Function:   交易状态 <br/>
 * Date:   2017年09月27日 下午8:36 <br/>
 *
 * @author liguoliang@zillionfortune.com
 */

@Data
@NoArgsConstructor
public class TradeStatusResp implements Serializable {

    /**
     * 交易状态
     */
    private String tradeStatus;
    private String tradeCode;
    private String tradeMsg;

    /**
     * 支付单号
     */
    private String payNo;

}
