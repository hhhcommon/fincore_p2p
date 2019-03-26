package com.zb.txs.p2p.business.asset.response;

import lombok.*;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoanTransferResp implements Serializable {
    /**
     * 交易状态
     */
    private String tradeStatus;
    /**
     * 支付单号
     */
    private String payNo;

}