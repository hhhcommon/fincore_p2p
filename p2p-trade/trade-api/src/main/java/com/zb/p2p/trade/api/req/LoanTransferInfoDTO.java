package com.zb.p2p.trade.api.req;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class LoanTransferInfoDTO implements Serializable {
	private static final long serialVersionUID = -5928802087384884057L;

	private String memberId; //投资人会员id
	private BigDecimal loanTransferAmount; //转账金额，单位元，精确到小数点2位
	private String investOrderNo; // 该订单号对应得金额大于等于loanTransferAmount
}
