/* 
* Copyright (c) 2017, 资邦金服(上海)网络科技有限公司. All Rights Reserved.
*
*/

package com.zb.txs.p2p.business.order.request;

import lombok.Data;

import java.io.Serializable;

/**
 * Function:   预约订单 <br/>
 * Date:   2017年09月28日 下午1:49 <br/>
 *
 * @author liguoliang@zillionfortune.com
 */

@Data
public class AppointOrder implements Serializable {

    private Long id;

    /**
     * 会员id
     */
    private String memberId;

    /**
     * 验证码流水号
     */
    private String orderId;

    private Long registerId;//登记订单流水号

    /**
     * 手机验证码
     */
    private String verificationCode;

    /**
     * 产品id
     */
    private String productCode;

    /**
     * 购买金额
     */
    private String amount;

    /**
     * 是否售罄标识
     * 售罄标识  Y:是;N:否
     */
    private String soldOutFlag;

    private String productCategory;
}
