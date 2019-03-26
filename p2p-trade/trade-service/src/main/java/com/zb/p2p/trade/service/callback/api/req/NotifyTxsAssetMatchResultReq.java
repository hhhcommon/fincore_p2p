package com.zb.p2p.trade.service.callback.api.req;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 资产匹配结果请求对象
 *
 * @author
 * @create 2017-09-19 10:54
 */
@Data
public class NotifyTxsAssetMatchResultReq implements Serializable {

    private String extOrderNo;

    private String tradeNo;

    /**
     * 匹配金额
     */
    private BigDecimal amount;

    private String type;

    private String status;

    public enum Status {
        MATCH, CASH
    }

}
