package com.zb.txs.p2p.business.asset.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderInvestRequest implements Serializable {
    private String reservationNo;               // 交易系统的预约单号 天鼋预约单号
    private String extReservationNo;            // 唐小僧预约单号
    private String extOrderNo;                  // 唐小僧订单号
    private String assetCode;                   // 资产code
    private Date orderTime;                   // 唐小僧下单时间 格式：yyyy-MM-dd HH:mm:ss
    private String memberId;                    // 会员id
    private String productCode;                 // 产品代码
    private String saleChannel;                 // 交易渠道  ZD:资鼎
    private BigDecimal assetAmount;                 // 借款总金额 资产的总金额
    private BigDecimal matchedAmount;               // 已匹配金额
}