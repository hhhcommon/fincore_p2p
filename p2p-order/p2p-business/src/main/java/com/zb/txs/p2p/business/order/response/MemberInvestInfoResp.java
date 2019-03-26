package com.zb.txs.p2p.business.order.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * Function:    <br/>
 * Date:  2017/9/27 17:56 <br/>
 *
 * @author liguoliang@zillionfortune.com
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberInvestInfoResp implements Serializable {

    /**
     * 用户总资产
     */
    private BigDecimal totalAmt;

    /**
     * 用户总利息
     */
    private BigDecimal incomeAmt;


    private List<MemberCatagoryInvestInfoResp> memberCatagoryInvestInfoList;
}
