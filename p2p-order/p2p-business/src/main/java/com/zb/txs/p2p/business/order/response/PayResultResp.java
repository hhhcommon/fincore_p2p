package com.zb.txs.p2p.business.order.response;

import lombok.Data;

import java.io.Serializable;

/**
 * 前端查询支付状态接口
 * Created by liguoliang on 2017/9/26.
 */
@Data
public class PayResultResp implements Serializable {
    private String orderId;
    private String payStatus;
    private String payCode;
    private String payMsg;
    private String payNo;

}