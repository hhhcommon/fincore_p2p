/* 
* Copyright (c) 2017, 资邦金服(上海)网络科技有限公司. All Rights Reserved.
*
*/

package com.zb.txs.p2p.order.state.entity;


import com.zb.txs.p2p.order.state.enums.Events;
import com.zb.txs.p2p.order.state.enums.States;
import lombok.Data;

import java.io.Serializable;

/**
 * Function:   状态实体 <br/>
 * Date:   2017年09月24日 下午3:45 <br/>
 *
 * @author liguoliang@zillionfortune.com
 */

@Data
public class StateEntity<E> implements Serializable {

    private String name;

    //关联实体
    private E e;

    // 上一个状态
    private States sourceState;

    // 下一个状态
    private States targetState;

    // 状态变迁所受哪个事件驱动的
    private Events event;
}
