package com.zb.txs.p2p.business.invest.repose;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by liguoliang on 2017/9/1.
 */
@Data
public class OrderMatchResp implements Serializable {

    private Long count;

    private List<OrderMatchInfo> orderMatchInfoDTOList;

}
