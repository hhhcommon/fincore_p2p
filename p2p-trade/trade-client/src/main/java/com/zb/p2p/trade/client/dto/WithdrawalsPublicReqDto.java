package com.zb.p2p.trade.client.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p> 对公放款 </p>
 *
 * @author Vinson
 * @version WithdrawalsPublicReqDto.java v1.0 2018/5/14 12:51 Zhengwenquan Exp $
 */
@Data
public class WithdrawalsPublicReqDto implements Serializable {

    private String orderNo;
    private Date orderTime;
    private String sourceId;

    /**
     * 会员id    String  是
     */
    private String memberId;

    /**
     * 账户用途(101-借款用户、102-授权用户、103-资金归集户；104-综合账户户；105-风险备付金户；106-渠道手续费户；)
     */
    private String accountPurpose;

    /**
     * 交易金额
     */
    private BigDecimal tradeAmount;

    /**
     * 开户行省名
     */
    private String branchBankProvince;
    /**
     * 开户行市名
     */
    private String branchBankCity;
    /**
     * 开户行机构名
     */
    private String branchBankInst;
    /**
     * 收款人银行名称
     */
    private String bankName;
    /**
     * 收款人银行卡号
     */
    private String bankAccountNo;
    /**
     * 收款人姓名
     */
    private String memberName;
}
