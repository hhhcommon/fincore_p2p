package com.zb.txs.p2p.business.invest.repose;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Getter
@ToString
@Builder
public class InvestmentRecordResp implements Serializable {
    /**
     * 投资产品的数量
     */
    private String total;
    /**
     * 投资记录项
     */
    private List<InvestmentRecordItem> investmentRecordItemList;



}
