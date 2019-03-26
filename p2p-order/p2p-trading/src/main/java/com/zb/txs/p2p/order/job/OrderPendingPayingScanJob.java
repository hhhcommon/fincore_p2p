/*
* Copyright (c) 2017, 资邦金服(上海)网络科技有限公司. All Rights Reserved.
*
*/

package com.zb.txs.p2p.order.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.zb.txs.p2p.order.httpclient.OrderClient;
import com.zb.txs.p2p.order.service.OrderService;
import com.zb.txs.p2p.order.service.OrderServiceRetry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Function:   待预约支付状态持续1分钟以上：1、实际支付，但后续事务回滚 2、判断是否占了库存但没有支付 <br/>
 * Date:   2017年10月16日 上午10:45 <br/>
 *
 * @author liguoliang@zillionfortune.com
 */

@Slf4j
public class OrderPendingPayingScanJob implements SimpleJob {

    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderClient orderClient;
//    @Autowired
//    private ProductStockLogMapper stockLogMapper;
    @Autowired
    private OrderServiceRetry orderServiceRetry;

    /**
     * 查询数据不存在
     */
    private static final String ORDER_NOT_EXITS = "S001";

    @Override
    public void execute(ShardingContext shardingContext) {
//        System.out.println("OrderPendingPayingScanJob.execute==================");
//
//        AppointmentExample appointmentExample = new AppointmentExample();
//        AppointmentExample.Criteria criteria = appointmentExample.createCriteria();
//        criteria.andStatusEqualTo(States.PENDING_APPOINT_PAYMENT);
//        criteria.andModifiedLessThanOrEqualTo(new DateTime().minusHours(1).toDate());
//        criteria.andModifiedGreaterThanOrEqualTo(new DateTime().minusHours(2).toDate());
//        appointmentMapper.selectByExample(appointmentExample)
//                .forEach(appointment -> {
//                    //判断是否已经支付
//                    QueryTradeStatus queryTradeStatus = new QueryTradeStatus();
//                    queryTradeStatus.setBusiType("10");
//                    queryTradeStatus.setMemberId(String.valueOf(appointment.getMemberId()));
//                    queryTradeStatus.setOrderNo(String.valueOf(appointment.getTradeRegisterNo()));
//                    queryTradeStatus.setSourceId(OrderService.PAY_SOURCEID);
//                    try {
//                        ProductAndBaseResp productAndBaseResp = productFeignClient.getProductAndBase(String.valueOf(appointment.getProductId()));
//                        Result<TradeStatusResp> tradeStatusRespResult = orderClient.queryTradeStatus(queryTradeStatus).execute().body();
//                        if (ORDER_NOT_EXITS.equals(tradeStatusRespResult.getCode().getCode())) {
//                            //判断是否占了库存
//                            ProductStockLogExample productStockLogExample = new ProductStockLogExample();
//                            ProductStockLogExample.Criteria productCriteria = productStockLogExample.createCriteria();
//                            productCriteria.andAppointmentIdEqualTo(appointment.getId());
//                            productCriteria.andProductIdEqualTo(appointment.getProductId());
//                            productCriteria.andDirectionEqualTo(Byte.valueOf("1"));
//                            if (stockLogMapper.selectByExample(productStockLogExample).size() > 0) {
//                                //回滚库存
//                                log.error("appointment:{}补偿确认失败", appointment.getId());
////                                orderService.appointmentTradeFailure(appointment, productAndBaseResp, "补偿确认支付失败", false);
//                            }
//                        } else {
//                            //实际支付，但事务回滚，补偿后续操作
//                            TradeStatusEnum tradeStatusEnum = orderServiceRetry.checkReponseState(tradeStatusRespResult);
//                            if (TradeStatusEnum.S.equals(tradeStatusEnum)) {
////                                orderService.appointmentAfterPaySuccess(appointment, productAndBaseResp, false);
//                            } else if (TradeStatusEnum.F.equals(tradeStatusEnum)) {
////                                orderService.appointmentTradeFailure(appointment, productAndBaseResp, tradeStatusRespResult.getMsg(), false);
//                            }
//                        }
//                    } catch (Exception e) {
//                        log.error("OrderPayingScanJob error:", e);
//                    }
//                });
    }
}
