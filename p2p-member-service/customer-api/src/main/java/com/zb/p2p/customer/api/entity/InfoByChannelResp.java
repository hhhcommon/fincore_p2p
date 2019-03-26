/**
 * 
 */
package com.zb.p2p.customer.api.entity;

import lombok.Data;

/**
 * @author liguoliang
 *
 */
@Data
public class InfoByChannelResp {
	private String customerId;

	private String mobile;

	private String realName;

	private String idCardType;

	private String idCardNo;

	private String channelCustomerId;

	private String channelCode;

	private String accountNo;
}
