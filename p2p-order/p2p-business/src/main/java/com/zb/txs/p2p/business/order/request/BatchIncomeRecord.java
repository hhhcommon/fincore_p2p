package com.zb.txs.p2p.business.order.request;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Function:  批量查询每日收益  <br/>
 * Date:  2017/9/27 17:39 <br/>
 *
 * @author liguoliang@zillionfortune.com
 */
@Data
public class BatchIncomeRecord implements Serializable {

    /**
     * 客户编号
     */
    private String memberId;

    /**
     * 产品编号
     */
    private List<String> productCodes;

    /**
     * 收益日期
     */
    private Date incomeDate;
}
