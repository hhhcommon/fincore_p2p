package com.zb.txs.p2p.business.invest.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InvestIncomeRequest implements Serializable{
    /**
     * 产品编码
     */
    private String productCode;
    /**
     * 会员ID
     */
    private String memberId;
    /**
     * 收益日期
     */
    private String incomeDate;
}
