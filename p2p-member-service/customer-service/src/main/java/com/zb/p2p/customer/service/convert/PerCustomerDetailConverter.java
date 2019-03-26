/**
 * 
 */
package com.zb.p2p.customer.service.convert;

import com.zb.p2p.customer.api.entity.CustomerCardBin;
import com.zb.p2p.customer.api.entity.CustomerDetail;
import com.zb.p2p.customer.api.entity.TxsCusInfo;
import com.zb.p2p.customer.api.entity.card.Card;
import com.zb.p2p.customer.common.util.DateUtil;
import com.zb.p2p.customer.common.util.StringUtils;
import com.zb.p2p.customer.dao.domain.CustomerBindcard;
import com.zb.p2p.customer.dao.domain.CustomerInfo;
import com.zb.p2p.customer.dao.domain.UserBindCard;
import com.zb.p2p.customer.service.bo.UserInfoResponseContent;

/**
 * 个人会员信息转换
 * @author guolitao
 *
 */
public class PerCustomerDetailConverter {
	
	
	public static TxsCusInfo txsRespCovert(UserInfoResponseContent content){
		
		TxsCusInfo tu=new TxsCusInfo();
		tu.setBankCode(content.getBankCode());
		tu.setBankName(content.getBankName());
		tu.setCardNumber(content.getCardNumber());
		tu.setCardType(content.getCardType());
//		tu.setIdentityNumber(content.getc);
//		tu.setName(name);
	    return tu;
		
	}

	public static CustomerDetail convert(CustomerInfo ci,CustomerBindcard card,Card limit,TxsCusInfo txsInfo){
		if(ci == null){
			return null;
		}
		CustomerDetail cd = new CustomerDetail();
		//基本信息
		cd.setIdCardNo(ci.getIdCardNo());
		cd.setIdCardType(ci.getIdCardType());
		cd.setIsBindCard(ci.getIsBindCard());
		cd.setCustomerId(ci.getCustomerId().toString());
		cd.setIsRealName(ci.getIsReal());
		cd.setMobile(ci.getMobile());
		cd.setName(ci.getRealName());
		cd.setRegisterTime(DateUtil.formatDateTime(ci.getRegisterTime()));
		cd.setIsOpenAccount(StringUtils.isBlank(ci.getAccountNo()) ? "0":"1");
		cd.setIsActiveEAccount(ci.getIsActiveEAccount());
		cd.setIsDepositManage(ci.getIsDepositManage());
		if(2 == ci.getBuyFreshProductStatus()){
			//新手标锁定，可购
			cd.setCanFresh(1);//1---可购
		}else if(1 == ci.getBuyFixedProductStatus() || 1== ci.getBuyFreshProductStatus()){
			cd.setCanFresh(0);//0---不可购
		}else{
			cd.setCanFresh(1);//1---可购
		}
			
		cd.setBuyFreshProductStatus(ci.getBuyFreshProductStatus());
		cd.setBuyFixedProductStatus(ci.getBuyFixedProductStatus());
		if(card != null){
			CustomerCardBin bankCard = new CustomerCardBin();
			bankCard.setBankCardNo(card.getBankCardNo());
			bankCard.setBankCode(card.getBankCode());
			bankCard.setBankName(card.getBankName());
			bankCard.setBindId(card.getBindId());
			bankCard.setCardType(card.getCardType());

			if(limit != null){
				bankCard.setPayDayMax(limit.getPayDayMax());
				bankCard.setPayMax(limit.getPayMax());
			}
			
			bankCard.setSignId(card.getSignId());
			bankCard.setBankMobile(card.getMobile());
			cd.setBankCard(bankCard);
		}
		//新增txs银行卡信息
		cd.setTxsCusInfo(txsInfo);
		return cd;
	}
	
	public static CustomerDetail convert(CustomerInfo ci,UserBindCard card,Card limit,TxsCusInfo txsInfo){
		if(ci == null){
			return null;
		}
		CustomerDetail cd = new CustomerDetail();
		//基本信息
		cd.setIdCardNo(ci.getIdCardNo());
		cd.setIdCardType(ci.getIdCardType());
		cd.setIsBindCard(ci.getIsBindCard());
		cd.setCustomerId(ci.getCustomerId().toString());
		cd.setIsRealName(ci.getIsReal());
		cd.setMobile(ci.getMobile());
		cd.setName(ci.getRealName());
		cd.setRegisterTime(DateUtil.formatDateTime(ci.getRegisterTime()));
		cd.setIsOpenAccount(StringUtils.isBlank(ci.getAccountNo()) ? "0":"1");
		cd.setIsActiveEAccount(ci.getIsActiveEAccount());
		cd.setIsDepositManage(ci.getIsDepositManage());
		if(2 == ci.getBuyFreshProductStatus()){
			//新手标锁定，可购
			cd.setCanFresh(1);//1---可购
		}else if(1 == ci.getBuyFixedProductStatus() || 1== ci.getBuyFreshProductStatus()){
			cd.setCanFresh(0);//0---不可购
		}else{
			cd.setCanFresh(1);//1---可购
		}
		
		cd.setBuyFreshProductStatus(ci.getBuyFreshProductStatus());
		cd.setBuyFixedProductStatus(ci.getBuyFixedProductStatus());
		if(card != null){
			CustomerCardBin bankCard = new CustomerCardBin();
			bankCard.setBankCardNo(card.getCardNo());
			bankCard.setBankCode(card.getBankCode());
			bankCard.setBankName(card.getBankName());
			bankCard.setBindId(card.getCardBindId());
			bankCard.setCardType(card.getCardType());
			
			if(limit != null){
				bankCard.setPayDayMax(limit.getPayDayMax());
				bankCard.setPayMax(limit.getPayMax());
			}
			
			bankCard.setSignId(card.getSignId());
			bankCard.setBankMobile(card.getMobile());
			cd.setBankCard(bankCard);
		}
		//新增txs银行卡信息
		cd.setTxsCusInfo(txsInfo);
		return cd;
	}
}
