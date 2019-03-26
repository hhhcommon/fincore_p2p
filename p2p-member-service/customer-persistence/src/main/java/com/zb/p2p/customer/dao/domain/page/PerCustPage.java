/**
 * 
 */
package com.zb.p2p.customer.dao.domain.page;

import com.zb.p2p.customer.dao.page.Page;
import lombok.Data;

/**
 * 个人会员分页查询
 * @author guolitao
 *
 */
@Data
public class PerCustPage extends Page {

	private static final long serialVersionUID = -4520475345351752822L;
	private String channelCode;
	//手机号
	private String mobile;
	//证件号
    private String idCardNo;
    //证件类型，固定填01
    private String idCardType;
    //会员ID
    private Long customerId;
    
    private Long orgId;

    private String loginPwd;

    
}
