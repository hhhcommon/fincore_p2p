package com.zb.p2p.facade.api.req;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wangwanbin on 2017/9/8.
 */
@Data
public class DailyIncomeReq implements Serializable {
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
     * 批量产品编号
     */
    private List<String> productCodeList;
}
