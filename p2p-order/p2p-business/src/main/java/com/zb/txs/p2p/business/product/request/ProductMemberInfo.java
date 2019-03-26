/* 
* Copyright (c) 2017, 资邦金服(上海)网络科技有限公司. All Rights Reserved.
*
*/

package com.zb.txs.p2p.business.product.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * Function:   确认投资页信息 <br/>
 * Date:   2017年09月20日 下午8:00 <br/>
 *
 * @author liguoliang@zillionfortune.com
 */

@Setter
@Getter
@Builder
@ToString
public class ProductMemberInfo implements Serializable {

    /**
     * 剩余金额
     */
    private String remainAmount;

    /**
     * 银行名称
     */
    private String bankName;

    /**
     * 银行尾号
     */
    private String cardTailNumber;

    /**
     * 银行单笔限额
     */
    private String bankSingleLimit;

    /**
     * 银行每日限额
     */
    private String bankDayLimit;
}
