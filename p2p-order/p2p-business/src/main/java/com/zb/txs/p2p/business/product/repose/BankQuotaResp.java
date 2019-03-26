/* 
* Copyright (c) 2017, 资邦金服(上海)网络科技有限公司. All Rights Reserved.
*
*/

package com.zb.txs.p2p.business.product.repose;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Function:   银行卡限额查询 <br/>
 * Date:   2017年09月25日 下午7:54 <br/>
 *
 * @author liguoliang@zillionfortune.com
 */

@Data
@NoArgsConstructor
public class BankQuotaResp implements Serializable {
    //银行代码
    private String bankCode;
    //银行名称
    private String bankName;
    //卡类型
    private String cardType;
    //单笔限额
    private String payMax;
    //当日限额
    private String payDayMax;
    //当月限额
    private String payMonthMax;
}
