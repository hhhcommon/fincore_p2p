package com.zb.txs.p2p.business.order.response;

import lombok.Data;

import java.io.Serializable;

/**
 * 前端查询出借人数接口
 * Created by liguoliang on 2017/9/26.
 */
@Data
public class LoanCountResp implements Serializable {
    private String investCount;

}