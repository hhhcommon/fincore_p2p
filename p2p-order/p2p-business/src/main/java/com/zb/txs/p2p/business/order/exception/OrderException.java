/* 
* Copyright (c) 2017, 资邦金服(上海)网络科技有限公司. All Rights Reserved.
*
*/

package com.zb.txs.p2p.business.order.exception;

import lombok.Builder;
import lombok.Getter;

/**
 * Function:   功能描述 <br/>
 * Date:   2017年09月30日 下午4:23 <br/>
 *
 * @author liguoliang@zillionfortune.com
 */

@Builder
@Getter
public class OrderException extends RuntimeException {

    private String code;
    private String message;
}
