package com.zb.txs.p2p.business.asset.request;

import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class AssetRequest implements Serializable {
    /**
     * 产品ID
     */
    private String productID;

    /**
     * 账户ID
     */
    private String memberID;
}
