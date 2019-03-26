package com.zb.txs.p2p.business.order.response;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 持仓列表页返回
 * Created by liguoliang on 2017/9/26.
 */
@Builder
@Data
public class HoldAssetsResp implements Serializable {
    //网贷总资产
    private String totalAssets;
    //网贷总收益
    private String totalProfit;
    //在投记录
    private String totalActivity;
    //数据集
    private List<HoldAssetsListResp> list;
}