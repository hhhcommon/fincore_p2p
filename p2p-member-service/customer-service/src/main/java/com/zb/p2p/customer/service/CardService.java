/**
 * 
 */
package com.zb.p2p.customer.service;

import java.util.List;

import com.zb.p2p.customer.api.entity.card.BindCard;
import com.zb.p2p.customer.api.entity.card.BindCardHelp;
import com.zb.p2p.customer.api.entity.card.BindCardNotifyReq;
import com.zb.p2p.customer.api.entity.card.BindCardResultReq;
import com.zb.p2p.customer.api.entity.card.BindCardResultRes;
import com.zb.p2p.customer.api.entity.card.Card;
import com.zb.p2p.customer.api.entity.card.CardBin;
import com.zb.p2p.customer.api.entity.card.ConfirmBindCardReq;
import com.zb.p2p.customer.api.entity.card.ConfirmBindCardRes;
import com.zb.p2p.customer.api.entity.card.PreBind;
import com.zb.p2p.customer.api.entity.card.UnBindReq;
import com.zb.p2p.customer.common.model.BaseRes;
import com.zb.p2p.customer.dao.domain.CustomerBindcard;
import com.zb.p2p.customer.dao.domain.CustomerInfo;
import com.zb.p2p.customer.dao.domain.UserBindCard;

/**
 * 查询绑卡信息
 * @author laoguoliang
 *
 */
public interface CardService {
	
	 public CustomerBindcard selectCustomerCardByCardNo(String cardNo) ;

    CustomerBindcard queryCardByCustomerId(Long customerId) ;


    /**
     * 查询卡bin
     * @return BaseRes<CardBin>
     */
    BaseRes<CardBin> cardBin(String cardNo);
    
    /**
     * 查询支持的银行列表
     * @return BaseRes<List<Card>>
     */
    BaseRes<List<Card>> cardList();

    /**
     * 查询支持的银行列表
     * @return BaseRes<List<Card>>
     */
    BaseRes<List<Card>> cardListByBankCode(String bankCode);
    
    /**
     * 会员绑卡发送验证码
     * @param bindCard
     * @param customerId
     * @return BaseRes<PreBind>
     */
    BaseRes<PreBind> sendMsgByPreBind(String customerId, BindCard bindCard);
    
    /**
     * 确认绑卡
     * @param bindcardRes
     * @param smsCode
     * @param orderNo
     * @param customerId
     * @return BaseRes<Object>
     */
    BaseRes<Object> confirmBind(CustomerBindcard bindcardRes, String smsCode, String orderNo, String customerId);
    
    /**
     * 开户
     * @param smsCode
     * @param orderNo
     * @return BaseRes<Object>
     */
    BaseRes<Object> openAccount(CustomerInfo cust, Long customerId);
    
    /**
     * 解绑卡
     * @param bindId
     * @param customerId
     * @return BaseRes<UnBind>
     */
    BaseRes<Object> unBind(String bindId, String customerId,UnBindReq req);
    
    /**
     * 查询会员信息
     * @param customerId
     * @return CustomerInfo
     */
    CustomerInfo getCustomerInfo(String customerId);
    
    /**
     * 查询绑卡信息
     * @param bindId
     * @return CustomerBindcard
     */
    CustomerBindcard getCustomerBindcard(String bindId) throws Exception;
    
    /**
     * 根据身份证查询会员信息
     * @param bindId
     * @return CustomerBindcard
     */
    CustomerInfo selectByPrimaryIden(String idCardNo);
    
    /**
     * 查询绑卡信息
     * @param bindId
     * @return CustomerBindcard
     */
    CustomerBindcard selectByPreBindCard(CustomerBindcard record) throws Exception;
    
    /**
     * 直接绑卡
     * @param ConfirmBindCardReq
     * @param customerId
     * @return ConfirmBindCardRes
     */
    BaseRes<ConfirmBindCardRes> directBindingCard(ConfirmBindCardReq card, String customerId) throws Exception;
    
    /**
     * 根据卡号查询绑卡信息
     * @param cardNo
     * @return UserBindCard
     */
    UserBindCard selectCardByCardNo(String cardNo) throws Exception;
    
    /**
     * 根据卡号查询绑卡信息
     * @param cardNo
     * @return UserBindCard
     */
    BaseRes<Object> updateBindCard(BindCardNotifyReq req) throws Exception;
    
    /**
     * 查询绑卡结果
     * @param req
     * @param customerId
     * @return UserBindCard
     */
    BindCardResultRes bindCardResult(BindCardResultReq req, Long customerId) throws Exception;
    
    /**
     * 查询绑卡辅助信息
     * @param req
     * @param customerId
     * @return BindCardHelp
     */
    BindCardHelp bindCardHelp(Long customerId) throws Exception;
}
