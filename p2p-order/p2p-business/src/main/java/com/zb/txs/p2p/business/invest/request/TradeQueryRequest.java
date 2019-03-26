package com.zb.txs.p2p.business.invest.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TradeQueryRequest implements Serializable {
    /**
     * 订单号
     */
    private String orderNo;
    /**
     * 会员ID
     */
    private String memberId;
    /**
     * 收益日期
     */
    private String incomeDate;

    /**
     * 批量产品编号
     */
    private List<String> productCodeList;
}
