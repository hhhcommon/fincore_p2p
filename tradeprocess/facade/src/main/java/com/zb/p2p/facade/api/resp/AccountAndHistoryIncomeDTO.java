package com.zb.p2p.facade.api.resp;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Function:查询兑付信息
 * Author: created by liguoliang
 * Date: 2017/9/8 0008 下午 4:29
 * Version: 1.0
 */
@Data
public class AccountAndHistoryIncomeDTO implements Serializable {

    

    //持仓本金
    private BigDecimal investAmount;

    // 累计收益（已兑付）
    private BigDecimal income;

    
}
