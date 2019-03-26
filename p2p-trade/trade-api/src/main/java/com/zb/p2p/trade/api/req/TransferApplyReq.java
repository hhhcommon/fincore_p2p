/*
 * Copyright (c) 2017, 资邦金服(上海)网络科技有限公司. All Rights Reserved.
 *
 */

package com.zb.p2p.trade.api.req;

import lombok.Data;

import java.io.Serializable;

/**
 * Date:   2018年04月20日 下午9:48 <br/>
 *
 * @author liguoliang@zillionfortune.com
 */

@Data
public class TransferApplyReq implements Serializable {
    private String productCode;
    private String requestNo;
    private String orderNo;
    private String memberId;
    private String requestTime;

}
