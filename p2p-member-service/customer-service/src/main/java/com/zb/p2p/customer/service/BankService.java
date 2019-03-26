/**
 * 
 */
package com.zb.p2p.customer.service;

import java.util.List;

import com.zb.p2p.customer.api.entity.card.Card;

/**
 * 银行服务：获取银行静态信息，可缓存
 * @author guolitao
 *
 */
public interface BankService {

	/**
	 * 根据银行代码获取限额信息
	 * @param bankCode
	 * @return
	 */
	Card getCardByBankCode(final String channelCode,String bankCode,String cardType);
	
	/**
	 * 查询所有银行的限额信息
	 * @return
	 */
	List<Card> listCard(final String channelCode);
}
