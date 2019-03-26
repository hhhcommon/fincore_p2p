/**
 * 
 */
package com.zb.p2p.customer.service;

import com.zb.p2p.customer.api.entity.currency.CustomerAcountDetail;
import com.zb.p2p.customer.api.entity.currency.CustomerEaccountBalance;

/**
 * 活期账户相关接口
 * @author guolitao
 *
 */
public interface BalanceService {
	/**
	 * 查询会员活期账户余额
	 * @param customerId
	 * @return
	 */
	CustomerEaccountBalance queryCustomerBalance(Long customerId);
	/**
	 * 查询会员账户详情
	 * @param customerId
	 * @return
	 */
	CustomerAcountDetail queryCustomerAcountDetail(Long customerId);
	
	/**
	 * 查询活期开关
	 * @return
	 */
//	Integer queryBalanceFlag();
}
