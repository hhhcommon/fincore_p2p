package com.zb.txs.p2p.business.order.request;

import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NotifyOrder {

    @NonNull
    private String extOrderNo;
    private BigDecimal amount;
    private String tradeNo;
    @NonNull
    private String type;
    @NonNull
    private String status;

}