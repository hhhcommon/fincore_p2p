package com.zb.p2p.facade.api.resp;

import lombok.Data;

/**
 * 兑付失败请求（转账 提现）
 * Created by limingxin on 2018/1/12.
 */
@Data
public class FailCashResp {
    String errorOrderNo, errorCode, errorMsg;
}
