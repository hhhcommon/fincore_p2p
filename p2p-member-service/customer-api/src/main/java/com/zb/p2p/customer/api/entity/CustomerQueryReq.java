/**
 * 
 */
package com.zb.p2p.customer.api.entity;

import lombok.Data;

/**
 * 会员请求：封装请求通用字段
 * @author guolitao
 *
 */
@Data
public class CustomerQueryReq {

	private String memberId;
	private String mobile;
}
