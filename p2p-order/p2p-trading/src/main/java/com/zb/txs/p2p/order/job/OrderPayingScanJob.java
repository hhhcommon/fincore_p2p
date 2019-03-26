/*
* Copyright (c) 2017, 资邦金服(上海)网络科技有限公司. All Rights Reserved.
*
*/

package com.zb.txs.p2p.order.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.zb.txs.foundation.response.Result;
import com.zb.txs.p2p.business.enums.TradeStatusEnum;
import com.zb.txs.p2p.business.enums.order.PayStatusEnum;
import com.zb.txs.p2p.business.order.request.QueryTradeStatus;
import com.zb.txs.p2p.business.order.response.TradeStatusResp;
import com.zb.txs.p2p.order.httpclient.OrderClient;
import com.zb.txs.p2p.order.persistence.mapper.TradeOrderMapper;
import com.zb.txs.p2p.order.persistence.model.TradeOrderExample;
import com.zb.txs.p2p.order.service.OrderService;
import com.zb.txs.p2p.order.service.OrderServiceRetry;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Function:   预约单、订单状态超过一小时仍在进行中确认状态 <br/>
 * Date:   2017年10月16日 上午10:45 <br/>
 *
 * @author liguoliang@zillionfortune.com
 */

@Slf4j
public class OrderPayingScanJob implements SimpleJob {

    @Autowired
    private TradeOrderMapper tradeOrderMapper;
    @Autowired
    private OrderServiceRetry orderServiceRetry;
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderClient orderClient;

    /**
     * 查询数据不存在
     */
    private static final String ORDER_NOT_EXITS = "S001";

    @Override
    public void execute(ShardingContext shardingContext) {
        log.info("OrderPayingScanJob Start:");
        //查询状态"支付处理中"的订单
        TradeOrderExample example = new TradeOrderExample();
        TradeOrderExample.Criteria criteria = example.createCriteria();
        criteria.andPayStatusEqualTo(PayStatusEnum.PAYING.getCode());
        criteria.andModifyTimeLessThanOrEqualTo(new DateTime().minusHours(1).toDate());
        tradeOrderMapper.selectByExample(example)
                .forEach(tradeOrder -> {
                    QueryTradeStatus queryTradeStatus = new QueryTradeStatus();
                    queryTradeStatus.setBusiType("10");
                    queryTradeStatus.setMemberId(tradeOrder.getMemberId());
                    queryTradeStatus.setOrderNo(String.valueOf(tradeOrder.getRegisterId()));
                    queryTradeStatus.setSourceId(OrderService.PAY_SOURCEID);
                    try {
                        Result<TradeStatusResp> tradeStatusRespResult = orderClient.queryTradeStatus(queryTradeStatus).execute().body();
                        if (tradeStatusRespResult == null || tradeStatusRespResult.getData() == null || tradeStatusRespResult.getCode().getCode() == null) {
                            log.error("调用支付失败queryTradeStatus");
                            throw new Exception("调用支付queryTradeStatus失败");
                        }

                        //确认有可能支付链路失败，查看是否支付
                        if (ORDER_NOT_EXITS.equals(tradeStatusRespResult.getCode().getCode())) {
                            //请求没发过去，直接认为失败
                            tradeOrder.setPayCode(ORDER_NOT_EXITS);
                            tradeOrder.setPayMsg("信息不存在");
                            log.info("tradeOrder请求参数为:{}", tradeOrder);
                            orderService.appointmentTradeFailure(tradeOrder, tradeStatusRespResult.getMsg());
                        } else {
                            TradeStatusEnum tradeStatusEnum = orderServiceRetry.checkReponseState(tradeStatusRespResult);
                            tradeOrder.setPayCode(tradeStatusRespResult.getData().getTradeCode());
                            tradeOrder.setPayMsg(tradeStatusRespResult.getData().getTradeMsg());
                            tradeOrder.setPayNo(tradeStatusRespResult.getData().getPayNo());
                            log.info("tradeOrder参数为:{}", tradeOrder);
                            if (TradeStatusEnum.S.equals(tradeStatusEnum)) {
                                orderService.appointmentAfterPaySuccess(tradeOrder);
                            } else if (TradeStatusEnum.F.equals(tradeStatusEnum)) {
                                orderService.appointmentTradeFailure(tradeOrder, tradeStatusRespResult.getMsg());
                            }
                        }
                    } catch (Exception e) {
                        log.error("OrderPayingScanJob error:", e);
                    }
                });

        log.info("OrderPayingScanJob End:");
    }
}
