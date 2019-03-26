package com.zb.txs.p2p.business.order.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Function:    <br/>
 * Date:  2017/9/27 17:56 <br/>
 *
 * @author liguoliang@zillionfortune.com
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberCatagoryInvestInfoResp implements Serializable {

    /**
     * 客户编号
     */
    private String productCategory;

    /**
     * 产品编号
     */
    private String categoryName;

    /**
     * 分类总资产
     */
    private BigDecimal catagoryTotalAmt;

    /**
     * 分类总利息
     */
    private BigDecimal catagoryIncomeAmt;
}
