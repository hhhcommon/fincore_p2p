package com.zb.txs.p2p.business.order.request;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * Function:  查询每日收益(单个查询)  <br/>
 * Date:  2017/9/27 17:55 <br/>
 *
 * @author liguoliang@zillionfortune.com
 */
@Builder
@Data
public class IncomeRecord implements Serializable {

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
}