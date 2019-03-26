package com.zb.p2p.facade.api.resp.contract;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by mengkai on 2017/8/31.
 */
@Data
public class InvestInfoDTO implements Serializable {
    /**
     * 投资人id  预约单号 投资人联系方式 身份证
     */
    private String investMemberId, reservationNo, investTelNo, investIdentityCard;
    /**
     * 投资人姓名
     */
    private String investMemberName;
    /**
     * 投资金额
     */
    private BigDecimal investAmount;

}
