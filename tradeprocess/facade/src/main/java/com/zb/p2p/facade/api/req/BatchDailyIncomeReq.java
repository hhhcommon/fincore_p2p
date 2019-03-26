package com.zb.p2p.facade.api.req;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wangwanbin on 2017/9/8.
 */
@Data
public class BatchDailyIncomeReq implements Serializable {
    /**
     * 会员ID
     */
    private String memberId;

    /**
     * 产品编号列表
     */
    private List<String> productCodes;

    /**
     * 收益日期
     */
    private String incomeDate;
}
