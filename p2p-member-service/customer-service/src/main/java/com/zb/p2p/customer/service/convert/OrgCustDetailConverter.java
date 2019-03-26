/**
 * 
 */
package com.zb.p2p.customer.service.convert;

import java.util.ArrayList;
import java.util.List;

import com.zb.p2p.customer.api.entity.OrgCustomerDetail;
import com.zb.p2p.customer.api.entity.card.BankCard;
import com.zb.p2p.customer.dao.domain.OrgBankCard;
import com.zb.p2p.customer.dao.domain.OrgCustomerInfo;

/**
 * @author guolitao
 *
 */
public class OrgCustDetailConverter {

	public static OrgCustomerDetail convert(OrgCustomerInfo orgCi,List<OrgBankCard> cardList){
		OrgCustomerDetail cd = new OrgCustomerDetail();
		//基本信息
		cd.setIdCardNo(orgCi.getIdCardNo());
		cd.setIdCardType(orgCi.getIdCardType());
		cd.setOrgId(""+orgCi.getOrgId());
		cd.setOrgName(orgCi.getOrgName());
		cd.setOwnerIdCardNo(orgCi.getOwnerIdCardNo());
		cd.setOwnerIdCardType(orgCi.getOwnerIdCardType());
		cd.setOwnerName(orgCi.getOwnerName());
		cd.setTelephone(orgCi.getTelephone());
		
		List<BankCard> bankCardList = new ArrayList<>();
		cd.setBankCardList(bankCardList);
		if(cardList != null && !cardList.isEmpty()){
			BankCard bc = null;
			for(OrgBankCard orgBankCard : cardList){
				bc = new BankCard();
				bc.setBankCardNo(orgBankCard.getBankCardNo());
				bc.setBankMobile(orgBankCard.getMobile());
				bc.setBankName(orgBankCard.getBankName());
				bc.setBankCode(orgBankCard.getBankCode());
				bc.setBranch(orgBankCard.getBankBranchName());
				bc.setCity(orgBankCard.getBankCity());
				bc.setProvince(orgBankCard.getBankProvince());
				bc.setBankCardName(orgBankCard.getBankCardName());
				bc.setChannelCode(orgBankCard.getChannelCode());
				bankCardList.add(bc);
			}
		}		
		return cd;
	}
}
