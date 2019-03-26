package com.zb.txs.p2p.business.order.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentSuccess {

    private String orderId;

    private String orderTime;

    private String status;


    public enum OrderRespEnum{
        /**
         * 支付并且下单成功
         */
        ORDER_SUCCESS,
        /**
         * 支付进行中
         */
        PAY_PROCESSING,
        /**
         * 下单登记失败
         */
        ORDER_FAILURE
    }
}
