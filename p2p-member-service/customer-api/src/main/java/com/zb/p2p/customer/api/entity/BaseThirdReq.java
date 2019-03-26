/**
 * 
 */
package com.zb.p2p.customer.api.entity;

/**
 * 默认第三方查询参数
 * @author guolitao
 *
 */
public class BaseThirdReq {
	private String clientNo;//	客户编号	string	Y	固定输入TXS
	private String clientSeqNo;//	客户端流水号	string	Y	 
	public String getClientNo() {
		return clientNo;
	}
	public void setClientNo(String clientNo) {
		this.clientNo = clientNo;
	}
	public String getClientSeqNo() {
		return clientSeqNo;
	}
	public void setClientSeqNo(String clientSeqNo) {
		this.clientSeqNo = clientSeqNo;
	}
	
}
