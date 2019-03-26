/* 
* Copyright (c) 2017, 资邦金服(上海)网络科技有限公司. All Rights Reserved.
*
*/

package com.zb.txs.p2p.business.product.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * Function:   日切通知 <br/>
 * Date:   2017年09月20日 下午9:37 <br/>
 *
 * @author liguoliang@zillionfortune.com
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductCutDayRecord implements Serializable {

    /**
     * 流水号
     */
    private String serialNo;

    /**
     * 产品IDs
     */
    private List<String> productCodes;
}
