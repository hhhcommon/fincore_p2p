package com.zb.p2p.facade.api.resp;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by wangwanbin on 2017/9/8.
 */
@Data
public class DailyIncomeDTO implements Serializable{
    /**
     * 会员ID
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
     * 总收益
     */
    private BigDecimal incomeAmt;
}
