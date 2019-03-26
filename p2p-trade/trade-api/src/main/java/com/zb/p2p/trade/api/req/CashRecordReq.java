package com.zb.p2p.trade.api.req;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class CashRecordReq {

    // 最近几天
    private int latestDays = 7;
    // 资产编号
    private String assetNo;
    //会员ID
    private String memberId;

    // 开始位置
    private int startIndex;
    // 结束位置
    private int endIndex;
}