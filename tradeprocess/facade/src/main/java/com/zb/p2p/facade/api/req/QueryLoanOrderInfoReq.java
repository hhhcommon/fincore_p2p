package com.zb.p2p.facade.api.req;

import lombok.Data;

import java.io.Serializable;

/**
 * 查询借款详情
 * @author
 * @create 2018-01-10 15:39
 */
@Data
public class QueryLoanOrderInfoReq implements Serializable{

    private String loanNo;
}
