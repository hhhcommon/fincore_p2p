/* 
* Copyright (c) 2017, 资邦金服(上海)网络科技有限公司. All Rights Reserved.
*
*/

package com.zb.txs.p2p.order.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.zb.txs.foundation.monads.Try;
import com.zb.txs.p2p.business.order.response.CardsResponse;
import com.zb.txs.p2p.order.persistence.mapper.TradeOrderMapper;
import com.zb.txs.p2p.order.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Function:   支付成功但下单失败进行重试扫描 <br/>
 * Date:   2018年1月16日 下午2:31 <br/>
 *
 * @author liguoliang@zillionfortune.com
 */

@Slf4j
public class OrderFailureScanJob implements SimpleJob {

    @Autowired
    private TradeOrderMapper tradeOrderMapper;
    @Autowired
    private OrderService orderService;

    @Override
    public void execute(ShardingContext shardingContext) {
        log.info("OrderFailureScanJob Start:");
        //查询状态"预约确认失败"和"待确认预约"持续1分钟以上(可能是链路问题，金核服务端做了幂等，故再次调用下单拿到结果)的预约单,只查询近1小时的数据
        tradeOrderMapper.queryOrderFailure(new DateTime().minusHours(1).toDate())
                .forEach(tradeOrder -> {
                    try {
                        //拿到会员绑卡信息
                        String memberId = String.valueOf(tradeOrder.getMemberId());
                        Try<CardsResponse> cardsResponseTry = orderService.getMembersCard(memberId);
                        if (cardsResponseTry.isSuccess()) {
                            CardsResponse cardInfo = cardsResponseTry.successValue();
                            //调用金核投资下单
                            orderService.orderReservation(tradeOrder, orderService.appointReservation(cardInfo, tradeOrder));
                        }
                    } catch (Exception e) {
                        log.error("OrderPayingScanJob error:", e);
                    }
                });
        log.info("OrderFailureScanJob End:");
    }
}
