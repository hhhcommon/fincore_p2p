/*
* Copyright (c) 2017, 资邦金服(上海)网络科技有限公司. All Rights Reserved.
*
*/

package com.zb.txs.p2p.order.service;

import com.zb.txs.p2p.business.enums.order.MatchStatusEnum;
import com.zb.txs.p2p.business.enums.order.PayStatusEnum;
import com.zb.txs.p2p.order.persistence.model.TradeOrder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Function:   order service with transaction <br/>
 * Date:   2017年09月28日 下午5:21 <br/>
 *
 * @author liguoliang@zillionfortune.com
 */

@Service
@Slf4j
public class OrderServiceTransaction {

    @Autowired
    private OrderServiceNestedTransaction orderServiceNestedTransaction;

    /**
     * 回滚产品库存
     *
     * @param tradeOrder 订单
     */
    public void roolBackProductStock(final TradeOrder tradeOrder) {
        //更新状态
        TradeOrder tradeOrder1 = new TradeOrder();
        tradeOrder1.setId(tradeOrder.getId());
        tradeOrder1.setPayNo(tradeOrder.getPayNo());
        tradeOrder1.setPayTime(new Date());
        tradeOrder1.setPayStatus(PayStatusEnum.PAY_FAIL.getCode());
        tradeOrder1.setPayCode(tradeOrder.getPayCode());
        tradeOrder1.setPayMsg(tradeOrder.getPayMsg());
        orderServiceNestedTransaction.updateTradeOrder(tradeOrder1);
    }

    /**
     * 支付成功后更新产品库存、订单状态
     *
     * @param tradeOrder 订单
     */
    public void updateProductStockWhenPaySuccess(final TradeOrder tradeOrder) {
        //更新状态
        TradeOrder tradeOrder1 = new TradeOrder();
        tradeOrder1.setId(tradeOrder.getId());
        tradeOrder1.setPayStatus(PayStatusEnum.PAY_SUCCESS.getCode());
        tradeOrder1.setMatchStatus(MatchStatusEnum.INIT.getCode());
        tradeOrder1.setPayTime(new Date());
        tradeOrder1.setPayNo(tradeOrder.getPayNo());
        tradeOrder1.setPayCode(tradeOrder.getPayCode());
        tradeOrder1.setPayMsg(tradeOrder.getPayMsg());
        orderServiceNestedTransaction.updateTradeOrder(tradeOrder1);
    }

    /**
     * 更新预约单状态
     *
     * @param tradeOrder 预约单
     */
    public void appointmentProcessWhenPay(TradeOrder tradeOrder) {
        orderServiceNestedTransaction.updateTradeOrder(tradeOrder);
    }

}