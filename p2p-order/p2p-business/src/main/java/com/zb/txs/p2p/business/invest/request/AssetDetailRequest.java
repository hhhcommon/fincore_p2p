package com.zb.txs.p2p.business.invest.request;

import lombok.Data;

import java.io.Serializable;

/**
 * Function:
 * Author: created by liguoliang
 * Date: 2018/1/22 0022 下午 8:19
 * Version: 1.0
 */
@Data
public class AssetDetailRequest implements Serializable {
    /**
     * 订单记录ID
     */
    private String orderId;

    /**
     * 订单记录ID
     */
    private String extOrderNo;
}
