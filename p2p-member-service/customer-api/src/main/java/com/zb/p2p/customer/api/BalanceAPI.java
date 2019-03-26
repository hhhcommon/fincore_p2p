/**
 * 
 */
package com.zb.p2p.customer.api;

import com.zb.p2p.customer.api.entity.CustomerIdReq;
import com.zb.p2p.customer.api.entity.currency.CustomerAcountDetail;
import com.zb.p2p.customer.api.entity.currency.CustomerEaccountBalance;
import com.zb.p2p.customer.common.model.BaseRes;

/**
 * @author guolitao
 *
 */
public interface BalanceAPI {

	/**
	 * 查询用户账户余额
	 * @param req
	 * @param customerId
	 * @return
	 */
	BaseRes<CustomerEaccountBalance> queryCustomerBalance(CustomerIdReq req, String customerId);
	
	/**
	 * 查询余额
	 * @param req
	 * @param customerId
	 * @return
	 */
	BaseRes<CustomerAcountDetail> queryCustAccount(CustomerIdReq req,String customerId);
	
	/**
	 * 查询活期开关
	 * @return
	 */
//	BaseRes<Integer> queryBalanceFlag();
}
