package com.zb.p2p.customer.service.bo;

import java.io.Serializable;

public class UserInfoRequest implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4583404757988271661L;
	private String clientNo;
	private String txnCode;
	private String content;

	public String getClientNo() {
		return clientNo;
	}
	public void setClientNo(String clientNo) {
		this.clientNo = clientNo;
	}
	public String getTxnCode() {
		return txnCode;
	}
	public void setTxnCode(String txnCode) {
		this.txnCode = txnCode;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}

	
	
}
