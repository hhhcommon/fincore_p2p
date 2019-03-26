package com.zb.txs.p2p.business.asset.response;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class AssetResponse implements Serializable {

    /**
     * 总在投资产金额
     */
    private BigDecimal totalAssetsMoney;
}
