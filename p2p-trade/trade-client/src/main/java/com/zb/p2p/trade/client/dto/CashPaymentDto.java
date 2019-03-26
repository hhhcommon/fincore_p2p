package com.zb.p2p.trade.client.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p> 兑付支付请求Header </p>
 *
 * @author Vinson
 * @version CashPaymentDto.java v1.0 2018/5/9 19:16 Zhengwenquan Exp $
 */
@Data
public class CashPaymentDto {

    private String orderNo;
    private String orderTime;
    private String channelType;
    private String businessType;

    private String batchNo;
    private int totalCount;
    private BigDecimal tradeAmount;
    private List<CashPaymentSubReqDto> cashDetailList;
}
