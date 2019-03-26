package com.zb.p2p.trade.api.req;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by wangwanbin on 2017/9/8.
 */
@Data
public class InvestOrderIncomeReq {
    /**
     * 外部投资订单号
     */
    @NotNull(message = "投资订单号不能为空")
    @Size(min = 1, message = "投资订单号不能为空")
    private String extOrderNo;

}
