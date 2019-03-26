/* 
* Copyright (c) 2017, 资邦金服(上海)网络科技有限公司. All Rights Reserved.
*
*/

package com.zb.txs.p2p.business.order.response;

import lombok.Data;

import java.io.Serializable;

/**
 * Function:   预约单验证码回复 <br/>
 * Date:   2017年09月27日 下午3:10 <br/>
 *
 * @author liguoliang@zillionfortune.com
 */

@Data
public class AppointVerifyResp implements Serializable {

    /**
     * 金核验证码预约流水号
     */
    private String orderId;

    /**
     * 会员手机号
     */
    private String phoneNo;
}
