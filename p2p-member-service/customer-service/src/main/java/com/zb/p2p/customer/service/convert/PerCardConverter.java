/**
 * 
 */
package com.zb.p2p.customer.service.convert;

import com.zb.p2p.customer.api.entity.CustomerCardBin;
import com.zb.p2p.customer.api.entity.card.Card;
import com.zb.p2p.customer.common.util.SensitiveInfoUtils;
import com.zb.p2p.customer.dao.domain.CustomerBindcard;
import com.zb.p2p.customer.dao.domain.UserBindCard;

/**
 * @author guolitao
 *
 */
public class PerCardConverter {

	public static CustomerCardBin convert(CustomerBindcard card,Card limit){
		if(card == null){
			return null;
		}
		CustomerCardBin bankCard = new CustomerCardBin();
		bankCard.setBankCardNo(null == card ? "" : SensitiveInfoUtils.getHiddenData(card.getBankCardNo(), 4, 4) );
		bankCard.setBankCode(card.getBankCode());
		bankCard.setBankName(card.getBankName());
		bankCard.setBindId(card.getBindId());
		bankCard.setCardType(card.getCardType());
		//查询会员基本信息时绑卡信息是否需要？
		if(limit != null){
			/*bankCard.setPayDayMax(limit.getPayDayMax());
			bankCard.setPayMax(limit.getPayMax());*/
			bankCard.setPayDayMax(limit.getPayDayMax());
			bankCard.setPayMax(limit.getPayMax());
		}
		
		bankCard.setSignId(card.getSignId());
		bankCard.setBankMobile(card.getMobile());
		bankCard.setIdCardNo(card.getIdCardNo() );
		bankCard.setIdCardName(card.getIdCardName() );
		return bankCard;
	}
	
	public static CustomerCardBin convert(UserBindCard card,Card limit){
		if(card == null){
			return null;
		}
		CustomerCardBin bankCard = new CustomerCardBin();
		bankCard.setBankCardNo(card.getCardNo());
		bankCard.setBankCode(card.getBankCode());
		bankCard.setBankName(card.getBankName());
		bankCard.setBindId(card.getCardBindId());
		bankCard.setCardType(card.getCardType());
		//查询会员基本信息时绑卡信息是否需要？
		if(limit != null){
			bankCard.setPayDayMax(limit.getPayDayMax());
			bankCard.setPayMax(limit.getPayMax());
		}
		
		bankCard.setSignId(card.getSignId());
		bankCard.setBankMobile(card.getMobile());
		return bankCard;
	}
}
