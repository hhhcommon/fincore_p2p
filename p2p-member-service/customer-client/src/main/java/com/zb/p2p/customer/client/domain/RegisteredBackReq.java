package com.zb.p2p.customer.client.domain;

public class RegisteredBackReq {


	private String txsAccountId;
	private String p2pAccountId;
	private String mobile;
	private String operationTime;
	private String requestTime;
	public String getTxsAccountId() {
		return txsAccountId;
	}
	public void setTxsAccountId(String txsAccountId) {
		this.txsAccountId = txsAccountId;
	}

	 
	public String getP2pAccountId() {
		return p2pAccountId;
	}
	public void setP2pAccountId(String p2pAccountId) {
		this.p2pAccountId = p2pAccountId;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getOperationTime() {
		return operationTime;
	}
	public void setOperationTime(String operationTime) {
		this.operationTime = operationTime;
	}
	public String getRequestTime() {
		return requestTime;
	}
	public void setRequestTime(String requestTime) {
		this.requestTime = requestTime;
	}
	
	
	
	
	
}
