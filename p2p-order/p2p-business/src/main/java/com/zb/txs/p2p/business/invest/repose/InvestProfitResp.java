package com.zb.txs.p2p.business.invest.repose;

import lombok.Data;

import java.io.Serializable;

@Data
public class InvestProfitResp implements Serializable {
    /**
     * 网贷总资产（总本金）
     */
    private String totalAssets;
    /**
     * 收益
     */
    private String interestProfit;
}