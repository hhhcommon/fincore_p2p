package com.zb.p2p.facade.api.req;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by limingxin on 2017/8/31.
 */
@Data
public class BatchLoanReq implements Serializable {

    private List<LoanReq> loanReqList;
}
