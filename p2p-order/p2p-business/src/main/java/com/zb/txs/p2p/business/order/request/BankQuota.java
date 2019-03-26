/* 
* Copyright (c) 2017, 资邦金服(上海)网络科技有限公司. All Rights Reserved.
*
*/

package com.zb.txs.p2p.business.order.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Function:   银行限额查询 <br/>
 * Date:   2017年10月10日 下午2:20 <br/>
 *
 * @author liguoliang@zillionfortune.com
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BankQuota implements Serializable {

    private String bankCode;
    private String sourceId;
}
