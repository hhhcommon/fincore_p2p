package com.zb.txs.p2p.business.invest.repose;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InvestIncomeResp implements Serializable{
    /**
     * 客户编号
     */
    private String memberId;
    /**
     * 产品编号
     */
    private String productCode;
    /**
     * 收益日期
     */
    private String incomeDate;
    /**
     * 利息
     */
    private BigDecimal incomeAmt;
}
