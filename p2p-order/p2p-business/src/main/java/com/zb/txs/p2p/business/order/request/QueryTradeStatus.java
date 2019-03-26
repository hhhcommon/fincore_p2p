/* 
* Copyright (c) 2017, 资邦金服(上海)网络科技有限公司. All Rights Reserved.
*
*/

package com.zb.txs.p2p.business.order.request;

import lombok.Data;

import java.io.Serializable;

/**
 * Function:   交易状态查询 <br/>
 * Date:   2017年09月29日 下午6:15 <br/>
 *
 * @author liguoliang@zillionfortune.com
 */

@Data
public class QueryTradeStatus implements Serializable {

    private String orderNo;

    private String memberId;

    private String busiType;

    private String sourceId;
}
