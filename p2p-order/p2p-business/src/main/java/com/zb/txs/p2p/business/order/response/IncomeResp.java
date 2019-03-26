package com.zb.txs.p2p.business.order.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Function:  查询每日收益(单个查询)  <br/>
 * Date:  2017/9/27 17:56 <br/>
 *
 * @author liguoliang@zillionfortune.com
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class IncomeResp implements Serializable {

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
