package com.zb.txs.p2p.business.invest.repose;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TradeQueryResp implements Serializable {
    /**
     * 总本金
     */
    private BigDecimal investAmount;
    /**
     * 已兑付利息
     */
    private BigDecimal income;

}
