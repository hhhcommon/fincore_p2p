/**
 * 
 */
package com.zb.p2p.customer.api;

import com.zb.p2p.customer.api.entity.CustomerCardBin;
import com.zb.p2p.customer.api.entity.CustomerDetail;
import com.zb.p2p.customer.api.entity.ThirdCustReq;
import com.zb.p2p.customer.api.entity.currency.CustomerAcountDetail;
import com.zb.p2p.customer.common.model.BaseRes;

/**
 * 提供给第三方的接口，唐小僧是第三方
 * @author guolitao
 *
 */
public interface ThirdAPI {

	/**
	 * 查询会员绑卡信息
	 * @param req
	 * @return
	 */
	BaseRes<CustomerCardBin> bankCardBindInfo(ThirdCustReq req);
	
	BaseRes<CustomerDetail> perDetailInfo(ThirdCustReq req);
	/**
	 * 查询会员账户信息
	 * @param req
	 * @return
	 */
	BaseRes<CustomerAcountDetail> queryCustAccount(ThirdCustReq req);

	
}
