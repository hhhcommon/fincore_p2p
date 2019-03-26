package com.zb.p2p.trade.common.queue.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <p> 匹配结果消息 </p>
 *
 * @author Vinson
 * @version MatchMqResult.java v1.0 2018/4/24 15:49 Zhengwenquan Exp $
 */
@Data
public class MatchMqResult {

    /**
     * 匹配完成的资产编号（原始/转让资产）
     */
    private String assetCode;

    /**
     * 匹配数量（匹配记录数）
     */
    private int matchCounts;

    /**
     * 资产状态（目前暂时只有完全匹配）
     */
    private String assetStatus;

    /**
     * 下架时间（完全匹配的时间）
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date downShelveTime;

    /**
     * 匹配总金额（匹配记录汇总总金额）
     */
    private BigDecimal matchAmount;
}
