package com.zb.p2p.trade.client.dto;

import lombok.Data;

/**
 * <p> 查询支付结果返回Dto </p>
 *
 * @author Vinson
 * @version QueryTradeResultDto.java v1.0 2018/5/21 16:57 Zhengwenquan Exp $
 */
@Data
public class QueryTradeResultDto {

    protected String code;
    protected String msg;

    private String tradeStatus;
    private String tradeCode;
    private String tradeMsg;
    private String payNo;
}
