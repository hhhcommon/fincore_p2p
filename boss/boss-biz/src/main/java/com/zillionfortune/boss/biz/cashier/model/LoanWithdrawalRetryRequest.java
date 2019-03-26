package com.zillionfortune.boss.biz.cashier.model;

public class LoanWithdrawalRetryRequest {
	private String orderNo;
	private String orderTime;
	private String signId;
	private String branchBankProvince;
	private String branchBankCity;
	private String branchBankInst;
	private String bankName;
	private String bankAccountNo;
	private String memberName;
	private String memberId;
	private String tradeType;
	private String originalOrderNo;
	private String sourceId;
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getOrderTime() {
		return orderTime;
	}
	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}
	public String getSignId() {
		return signId;
	}
	public void setSignId(String signId) {
		this.signId = signId;
	}
	public String getBranchBankProvince() {
		return branchBankProvince;
	}
	public void setBranchBankProvince(String branchBankProvince) {
		this.branchBankProvince = branchBankProvince;
	}
	public String getBranchBankCity() {
		return branchBankCity;
	}
	public void setBranchBankCity(String branchBankCity) {
		this.branchBankCity = branchBankCity;
	}
	public String getBranchBankInst() {
		return branchBankInst;
	}
	public void setBranchBankInst(String branchBankInst) {
		this.branchBankInst = branchBankInst;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getBankAccountNo() {
		return bankAccountNo;
	}
	public void setBankAccountNo(String bankAccountNo) {
		this.bankAccountNo = bankAccountNo;
	}
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public String getTradeType() {
		return tradeType;
	}
	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}
	public String getOriginalOrderNo() {
		return originalOrderNo;
	}
	public void setOriginalOrderNo(String originalOrderNo) {
		this.originalOrderNo = originalOrderNo;
	}
	public String getSourceId() {
		return sourceId;
	}
	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}
	
}
