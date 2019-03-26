/*
* Copyright (c) 2017, 资邦金服(上海)网络科技有限公司. All Rights Reserved.
*
*/

package com.zb.txs.p2p.order.service;

import com.zb.txs.p2p.order.persistence.mapper.TradeOrderMapper;
import com.zb.txs.p2p.order.persistence.model.TradeOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Function:   order service with nested transaction <br/>
 * Date:   2017年09月28日 下午5:21 <br/>
 *
 * @author liguoliang@zillionfortune.com
 */

@Service
public class OrderServiceNestedTransaction {

    @Autowired
    private TradeOrderMapper tradeOrderMapper;

    /**
     * 更新订单状态
     *
     * @param tradeOrder
     */
    public void updateTradeOrder(TradeOrder tradeOrder) {
        tradeOrderMapper.updateByPrimaryKeySelective(tradeOrder);
    }
}