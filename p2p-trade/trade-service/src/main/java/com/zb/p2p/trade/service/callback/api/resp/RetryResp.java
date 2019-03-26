package com.zb.p2p.trade.service.callback.api.resp;

import lombok.Data;

import java.io.Serializable;

/**
 * Function:
 * Author: created by liguoliang
 * Date: 2017/10/13 0013 下午 5:30
 * Version: 1.0
 */
@Data
public class RetryResp implements Serializable {
    private boolean result;

    private String respParam;
}
