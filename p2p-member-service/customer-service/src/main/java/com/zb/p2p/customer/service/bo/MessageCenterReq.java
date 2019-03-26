package com.zb.p2p.customer.service.bo;

import java.io.Serializable;

public class MessageCenterReq implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5181833520187342765L;
	private String reqId;
	private String appKey;
	private String nodeKey;
	private String reqExt;
	private MsgBodyInfo[] body;
	private String msgMode;
	private String bizCode;
	public String getReqId() {
		return reqId;
	}
	public void setReqId(String reqId) {
		this.reqId = reqId;
	}
	public String getAppKey() {
		return appKey;
	}
	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}
	public String getNodeKey() {
		return nodeKey;
	}
	public void setNodeKey(String nodeKey) {
		this.nodeKey = nodeKey;
	}
	public String getReqExt() {
		return reqExt;
	}
	public void setReqExt(String reqExt) {
		this.reqExt = reqExt;
	}
	public MsgBodyInfo[] getBody() {
		return body;
	}
	public void setBody(MsgBodyInfo[] body) {
		this.body = body;
	}
	public String getMsgMode() {
		return msgMode;
	}
	public void setMsgMode(String msgMode) {
		this.msgMode = msgMode;
	}
	public String getBizCode() {
		return bizCode;
	}
	public void setBizCode(String bizCode) {
		this.bizCode = bizCode;
	}
	
	
	

}
