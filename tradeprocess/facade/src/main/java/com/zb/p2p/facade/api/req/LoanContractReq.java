package com.zb.p2p.facade.api.req;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by mengkai on 2017/8/31.
 */
@Data
public class LoanContractReq implements Serializable {
    /**
     * 借款人Id
     */
    private String memberId;

    /**
     * 资产编码
     */
    private String assetCode;

    /**
     * 借款单号
     */
    private String loanNo;
}
