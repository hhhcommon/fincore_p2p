package com.zb.txs.p2p.business.invest.request;

import lombok.Data;

import java.math.BigDecimal;

/**
 * Created by limingxin on 2018/1/19.
 */
@Data
public class ProductLoanRelationReq {
    private String productCode, loanOrderNo, financeSubjectName;
    private int leftCollectionDays, pageNo, pageSize;
    private BigDecimal loanStartAmount, loanEndAmount;
}
