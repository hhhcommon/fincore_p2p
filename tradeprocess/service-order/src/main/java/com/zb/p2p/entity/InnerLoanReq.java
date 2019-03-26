package com.zb.p2p.entity;

import com.zb.p2p.facade.api.req.OrderMatchReq;

import lombok.Data;

@Data
public class InnerLoanReq {
   private LoanRequestEntity loanRequest;
   private OrderMatchReq orderReq;
}