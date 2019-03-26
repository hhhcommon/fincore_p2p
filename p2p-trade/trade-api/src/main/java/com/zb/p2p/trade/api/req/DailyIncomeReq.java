package com.zb.p2p.trade.api.req;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * <p> 每日收益数据请求 </p>
 *
 * @author Vinson
 * @version DailyIncomeReq.java v1.0 2018/6/2 0029 下午 7:43 Zhengwenquan Exp $
 */
@Data
public class DailyIncomeReq {
    /**
     * 会员ID
     */
    @NotNull(message = "会员ID不能为空")
    @Size(min = 1, message = "会员ID不能为空")
    private String memberId;

    /**
     * 产品编号
     */
    private String productCode;

    /**
     * 收益日期 格式为短格式
     */
    private String incomeDate;

}
