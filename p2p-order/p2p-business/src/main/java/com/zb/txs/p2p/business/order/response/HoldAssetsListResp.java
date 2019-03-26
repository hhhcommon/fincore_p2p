package com.zb.txs.p2p.business.order.response;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by changbangcu 2017/9/26.
 */
@Builder
@Data
public class HoldAssetsListResp implements Serializable {

    //持仓记录Id
    private String orderId;
    //productId
    private String productId;
    //productCode
    private String productCode;
    //产品名称
    private String productTitle;
    //预约总金额
    private String investAmount;
    //匹配总金额
    private String matchAmount;
    //待匹配金额
    private String waitMatchAmount;
    //状态
    private String status;
    //到期日
    private String expireDay;
    //到期收益
    private String expireProfit;
    //剩余持有天数
    private String surplusDay;
    //匹配截止日
    private String limitMatchDay;
    //投资日
    private String investmentDay;
    private String hisFlag;
}
