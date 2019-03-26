package com.zb.p2p.trade.api.req;

import com.zb.p2p.trade.common.model.PageQueryRequest;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by mengkai on 2017/8/31.
 */
@Data
public class LoanContractReq extends PageQueryRequest implements Serializable {

    /**
     * 借款单号
     */
    private String loanNo;
}
