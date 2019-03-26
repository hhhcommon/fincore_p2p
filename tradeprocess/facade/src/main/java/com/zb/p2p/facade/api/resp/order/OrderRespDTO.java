package com.zb.p2p.facade.api.resp.order;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by limingxin on 2017/9/1.
 */
@Data
public class OrderRespDTO implements Serializable {
    /*外部订单号,交易系统订单号*/
    String extOrderNo, orderNo;
    /*交易系统下单时间*/
    Date orderTime;
}
