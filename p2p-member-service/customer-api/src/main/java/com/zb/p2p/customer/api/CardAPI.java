package com.zb.p2p.customer.api;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.zb.p2p.customer.api.entity.card.*;
import com.zb.p2p.customer.common.model.BaseRes;

public interface CardAPI {

    BaseRes<CardBin> cardBin(CardBinReq req, HttpServletRequest request);

    BaseRes<List<Card>> cardList(CardReq req, HttpServletRequest request);

//    public BaseRes<PreBind> sendMsgByPreBind(BindCard req, HttpServletRequest request);
//    
//    public BaseRes<Object> bindCard(BindCardReq req, HttpServletRequest request);

    BaseRes<Object> unBind(String customerId, UnBindReq req);

//    public BaseRes<Object> sendMsgByUnBind(PreUnBindReq req, HttpServletRequest request);

//    public BaseRes<UnBind> channelUnBind(ChannelUnBindReq req);

//    public BaseRes<UnBind> channelSelectUnBindResult(ChannelUnBindResultReq req, HttpServletRequest request);

    //    public BaseRes<Object> channelSendMsgByUnBind(@RequestBody ChannelPreUnBindReq req, HttpServletRequest request);
    BaseRes<ConfirmBindCardRes> confirmBindCard(String customerId, ConfirmBindCardReq req);

    // public BaseRes<BindCardResultRes> bindCardResult(BindCardResultReq req, String customerId);

//    public BaseRes<Object> bindCardNotify(BindCardNotifyReq req);

//    public BaseRes<BindCardHelp> bindCardHelp(String customerId);

//    public BaseRes<CheckBindCard> checkBindCard();
}
