package com.zb.txs.p2p.order.controller;


import com.zb.txs.p2p.business.order.request.NotifyOrder;
import com.zb.txs.p2p.business.order.request.OrderCallBackReq;
import com.zb.txs.p2p.business.order.response.CommonResponse;
import com.zb.txs.p2p.business.product.request.ProductCutDayRecord;
import com.zb.txs.p2p.order.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/p2p/trading/callBack")
public class CallbackController {

    private final OrderService orderService;

    @Autowired
    public CallbackController(OrderService orderService) {
        this.orderService = orderService;
    }

    /**
     * 订单支付回调
     *
     * @param orderCallBackReq 订单支付回调
     * @return
     */
    @PostMapping("/payNotifyOrder")
    public CommonResponse<Object> payNotifyOrder(@RequestBody OrderCallBackReq orderCallBackReq) {
        try {
            return orderService.payNotifyOrder(orderCallBackReq);
        } catch (Exception e) {
            log.error("系统异常:", e);
            return CommonResponse.failure();
        }
    }

    /**
     * 交易通知
     */
    @PostMapping("/tradeNotifyOrder")
    public CommonResponse<Object> notifyOrder(@RequestBody List<NotifyOrder> notifyOrder) {
        log.info("开始调用 交易通知(tradeNotifyOrder), 入参为 : {}", notifyOrder);
        try {
            orderService.notifyOrder(notifyOrder);
            return CommonResponse.success(null);
        } catch (Exception e) {
            log.error("调用 notifyOrder 出错 : {}", notifyOrder.toString(), e);
            return CommonResponse.failure();
        }
    }

    /**
     * 日切售罄产品(天鼋产品调用)
     *
     * @param productCodes
     * @return
     */
    @PostMapping("/soldSutProduct")
    public CommonResponse<Object> soldOutProductNotify(@RequestBody List<String> productCodes) {
        log.info("开始调用 日切售罄产品(soldOutProductNotify), 入参为 : {}", productCodes);
        try {
            ProductCutDayRecord productCutDayRecord = new ProductCutDayRecord();
            productCutDayRecord.setProductCodes(productCodes);
            return orderService.soldOutProductNotify(productCutDayRecord);
        } catch (Exception e) {
            log.error("调用 soldOutProductNotify 出错 : {}", productCodes.toString(), e);
            return CommonResponse.failure();
        }
    }

}
