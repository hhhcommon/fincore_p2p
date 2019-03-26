package com.zb.p2p.customer.service.bo;

public class UserInfoResponseContent {

	
	private String name; //姓名
	private String cardNumber; //银行卡号
	private String cardType; //卡类型
	private String bankCode; //银行编码
	private String bankName; //开户行
	private String phoneNumber;//预留手机号
//	private String questionnaireResult	;
	private String identityNumber; //身份证号
	private String responseTime; //
	private String txsPhoneNumber;//唐小僧电话号码
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCardNumber() {
		return cardNumber;
	}
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}
	public String getCardType() {
		return cardType;
	}
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
	public String getBankCode() {
		return bankCode;
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
//	public String getQuestionnaireResult() {
//		return questionnaireResult;
//	}
//	public void setQuestionnaireResult(String questionnaireResult) {
//		this.questionnaireResult = questionnaireResult;
//	}
	public String getIdentityNumber() {
		return identityNumber;
	}
	public void setIdentityNumber(String identityNumber) {
		this.identityNumber = identityNumber;
	}
	public String getResponseTime() {
		return responseTime;
	}
	public void setResponseTime(String responseTime) {
		this.responseTime = responseTime;
	}
	public String getTxsPhoneNumber() {
		return txsPhoneNumber;
	}
	public void setTxsPhoneNumber(String txsPhoneNumber) {
		this.txsPhoneNumber = txsPhoneNumber;
	}
	
	


}
