package com.zb.txs.p2p.business.invest.repose;

import lombok.Data;

import java.io.Serializable;

@Data
public class InvestmentRecordItem implements Serializable {
    private Long orderId;
    private String productCode;//天鼋产品ID
    private String productTitle;//产品名称
    private Integer transType;//交易类型
    private String investAmount;//投资金额
    private String expireProfit;//到期收益
    private String investTime;//投资时间
    private String cashTime;//兑付时间
    private String status;//状态
}
