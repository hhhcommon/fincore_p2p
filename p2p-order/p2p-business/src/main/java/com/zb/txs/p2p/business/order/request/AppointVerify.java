/* 
* Copyright (c) 2017, 资邦金服(上海)网络科技有限公司. All Rights Reserved.
*
*/

package com.zb.txs.p2p.business.order.request;

import lombok.Data;

import java.io.Serializable;

/**
 * Function:   预约单验证码 <br/>
 * Date:   2017年09月27日 下午3:06 <br/>
 *
 * @author liguoliang@zillionfortune.com
 */

@Data
public class AppointVerify implements Serializable {

    /**
     * 会员Id
     */
    private String memberId;

    /**
     * 产品id
     */
    private String productCode;

    /**
     * 购买金额
     */
    private String amount;

}
