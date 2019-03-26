/**
 *
 */
package com.zb.p2p.customer.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zb.p2p.customer.api.entity.OpenAccountReq;
import com.zb.p2p.customer.api.entity.TxsCusInfo;
import com.zb.p2p.customer.api.entity.card.*;
import com.zb.p2p.customer.common.constant.AppConstant;
import com.zb.p2p.customer.common.enums.AccountPurposeEnum;
import com.zb.p2p.customer.common.enums.AppCodeEnum;
import com.zb.p2p.customer.common.exception.AppException;
import com.zb.p2p.customer.common.model.BaseRes;
import com.zb.p2p.customer.common.model.ExternalBaseRes;
import com.zb.p2p.customer.common.util.CustomerConstants;
import com.zb.p2p.customer.common.util.DateUtil;
import com.zb.p2p.customer.common.util.HttpClientUtils;
import com.zb.p2p.customer.common.util.JsonUtil;
import com.zb.p2p.customer.common.util.UuidUtils;
import com.zb.p2p.customer.dao.CustomerBindcardMapper;
import com.zb.p2p.customer.dao.CustomerInfoMapper;
import com.zb.p2p.customer.dao.UserBindCardLogMapper;
import com.zb.p2p.customer.dao.UserBindCardMapper;
import com.zb.p2p.customer.dao.domain.CustomerBindcard;
import com.zb.p2p.customer.dao.domain.CustomerInfo;
import com.zb.p2p.customer.dao.domain.UserBindCard;
import com.zb.p2p.customer.dao.domain.UserBindCardLog;
import com.zb.p2p.customer.service.BalanceService;
import com.zb.p2p.customer.service.CardService;
import com.zb.p2p.customer.service.InfoService;
import com.zb.p2p.customer.service.OtpService;
import com.zb.p2p.customer.service.bo.CheckSmsCaptchaBO;
import com.zb.p2p.customer.service.bo.HoldTotalAssetsBO;
import com.zb.p2p.customer.service.bo.HoldTotalAssetsReq;
import com.zb.p2p.customer.service.bo.PaymentResponse;
import com.zb.p2p.customer.service.config.ReadOnlyConnection;
import com.zb.p2p.customer.service.rpc.OrderServiceClient;
import com.zb.qjs.common.util.OrderNoGenerator;
import com.zb.qjs.common.util.StringUtil;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 查询绑卡服务
 *
 * @author laoguoliang
 */
@Service
public class CardServiceImpl implements CardService {

    private static final Logger logger = LoggerFactory.getLogger(CardServiceImpl.class);

    @Resource
    private CustomerInfoMapper customerInfoMapper;
    @Resource
    private CustomerBindcardMapper customerBindcardMapper;
    @Resource
    private UserBindCardMapper userBindCardMapper;
    @Resource
    private UserBindCardLogMapper userBindCardLogMapper;
    @Autowired
    RedisService redisService;
    @Resource
    private InfoService infoService;

    @Autowired
    private CardService cardService;
    @Autowired
    private OrderServiceClient orderServiceClient;
    @Autowired
    private BalanceService balanceService;
    @Autowired
    private OtpService otpService;


    @Value("${env.paymentHost}")
    private String paymentHost;//金核请求地址
    @Value("${env.isEBankAccount:false}")
    private String isEBankAccount;//是否开通E账户

    @Override
    public BaseRes<CardBin> cardBin(String cardNo) {
        BaseRes<CardBin> result = new BaseRes<CardBin>();
        String url = paymentHost + "/queryCardBinInfo";//卡bin查询地址
        String jsonReq = "{\"cardNo\":" + cardNo + "}";
        logger.info(url + "参数：" + jsonReq);
        try {

            String response = HttpClientUtils.doPost(url, jsonReq);
            logger.info("卡bin查询返回:" + response);
            PaymentResponse pr = (PaymentResponse) JsonUtil.getObjectByJsonStr(response, PaymentResponse.class);
            if (pr != null) {
                if (AppCodeEnum._0000_SUCCESS.getCode().equals(pr.getCode())) {//成功
                    if (pr.getData() != null) {
                        JSONObject json = (JSONObject) pr.getData();
                        CardBin cardBin = new CardBin();
                        cardBin.setBankCode(json.getString("bankCode"));//银行简称
                        cardBin.setBankName(json.getString("bankName"));//银行名称
                        cardBin.setCardType(json.getString("cardType"));//卡类型
                        result.setData(cardBin);
                    }
                } else {
                    result.failure(pr.getCode(), pr.getMsg());
                }
            }
        } catch (Exception e) {
            logger.error("请求" + url + "异常!参数为" + jsonReq, e);
            result.resetCode(AppCodeEnum._A110_CARD_BIN_FAIL);
            return result;
        }
        return result;
    }

    @Override
    public BaseRes<List<Card>> cardList() {
        // 获取All
        return cardListByBankCode(null);
    }

    public BaseRes<List<Card>> cardListByBankCode(String bankCode) {
        BaseRes<List<Card>> result = new BaseRes<List<Card>>();
        String url = paymentHost + "/queryBankQuota";//银行卡限额查询地址
        String jsonReq = "{\"sourceId\":\"" + CustomerConstants.PAYMENT_SOURCEID_MSD + "\"}";
        logger.info(url + "参数：" + jsonReq);
        String response = null;
        try {
            response = HttpClientUtils.doPost(url, jsonReq);
            logger.info("银行卡限额查询返回:" + response);
            PaymentResponse pr = (PaymentResponse) JsonUtil.getObjectByJsonStr(response, PaymentResponse.class);
            if (pr != null) {
                if (AppCodeEnum._0000_SUCCESS.getCode().equals(pr.getCode())) {//成功
                    if (pr.getData() != null) {
                        JSONArray ja = (JSONArray) pr.getData();
                        List<Card> list = new ArrayList<>(ja.size());
                        final boolean isNeedCondition = StringUtil.isNotBlank(bankCode);
                        for (int i = 0; i < ja.size(); i++) {
                            JSONObject job = ja.getJSONObject(i);
                            // 如果条件参数bankCode不为空则精确匹配返回
                            if (isNeedCondition) {
                                if (!bankCode.equals(job.getString("bankCode"))) {
                                    continue;
                                }
                            }
                            Card card = new Card();
                            card.setBankCode(job.getString("bankCode"));
                            card.setBankName(job.getString("bankName"));
                            card.setCardType(job.getString("cardType"));
                            card.setPayMax(job.getString("payMax"));
                            card.setPayDayMax(job.getString("payDayMax"));
                            list.add(card);
                        }
                        result.setData(list);
                    }
                } else {
                    result.failure(pr.getCode(), pr.getMsg());
                }
            }
        } catch (Exception e) {
            logger.error("请求" + url + "异常!参数为" + jsonReq, e);
            result.resetCode(AppCodeEnum._A111_CARD_LIST_FAIL);
            return result;
        }
        return result;
    }
    
    /*@Override
    @Transactional
    public BaseRes<ConfirmBindCardRes> directBindingCard(ConfirmBindCardReq card, String customerId) {
        BaseRes<ConfirmBindCardRes> result = new BaseRes<ConfirmBindCardRes>();
        String url = paymentHost + "/directBindingCardYST";//银行卡限额查询地址
        JSONObject jsonReq = new JSONObject();
        try {
            String orderNo = OrderNoGenerator.generate(Long.valueOf(customerId));//订单号
            Date orderDate = new Date();
            jsonReq.put("sourceId", CustomerConstants.PAYMENT_SOURCEID_MSD);//外部请求来源
            jsonReq.put("memberId", customerId);//会员号
            jsonReq.put("mobile", card.getMobile());//手机号
            jsonReq.put("cardNo", card.getCardNo());//银行卡号
            jsonReq.put("bankCode", card.getBankCode());//银行编码
            jsonReq.put("idCardType", card.getIdCardType());//身份证类型
            jsonReq.put("idCardNo", card.getIdCardNo().toUpperCase());//证件号码
            jsonReq.put("memberName", card.getMemberName());//客户姓名
            jsonReq.put("orderTime", orderDate);//订单时间
            jsonReq.put("orderNo", orderNo);//订单号
            jsonReq.put("memberType", card.getMemberType());//客户类型
            jsonReq.put("openEBankAccount", isEBankAccount);//0：不开通 1：开通
            logger.info(url + "参数：" + jsonReq.toString());
            
            String response = HttpClientUtils.doPost(url, jsonReq.toString());
            logger.info("金核直接绑卡返回:" + response);
            PaymentResponse pr = (PaymentResponse)JsonUtil.getObjectByJsonStr(response, PaymentResponse.class);
            if (pr != null) {
                if (AppCodeEnum._0000_SUCCESS.getCode().equals(pr.getCode())) {//成功
                    if (pr.getData() != null) {
                        JSONObject json = (JSONObject)pr.getData();
                        UserBindCardLog userBindCardLog = new UserBindCardLog();
                        userBindCardLog.setCustomerId(Long.valueOf(customerId));//客户ID
                        userBindCardLog.setCardNo(card.getCardNo());//银行卡号
                        userBindCardLog.setBankCode(card.getBankCode());//银行简称
                        userBindCardLog.setBankName(card.getBankName());//银行名称
                        userBindCardLog.setCardType(card.getCardType());//卡类型D：借记卡，C：贷记卡
                        userBindCardLog.setStatus(CustomerConstants.TWO);//状态[1-成功2-处理中3-失败]
                        userBindCardLog.setContent(pr.getMsg());//绑卡结果内容
                        userBindCardLog.setIdCardType(card.getIdCardType());//证件类型 01:身份证
                        userBindCardLog.setIdCardNo(card.getIdCardNo().toUpperCase());//证件号
                        userBindCardLog.setMemberName(card.getMemberName());//证件姓名
                        userBindCardLog.setMobile(card.getMobile());//银行卡号对应的银行预留手机号
                        
                        userBindCardLog.setSignId(json.getString("signId"));//签约协议号
                        userBindCardLog.setOutOrderNo(orderNo);//流水号
                        userBindCardLog.setOutOrderTime(orderDate);//请求时间
                        //插入存管绑卡日志表
                        int bindResult = userBindCardLogMapper.insertSelective(userBindCardLog);
                        logger.info("插入存管绑卡日志表,条数：" + bindResult);
                        
                        CustomerInfo customerInfo = new CustomerInfo();
                        customerInfo.setCustomerId(Long.valueOf(customerId));
                        customerInfo.setIsDepositManage(CustomerConstants.TWO);//是否开通银行存管,0-否1-是2-处理中3-失败
                        customerInfo.setEBankAccount(json.getString("eBankAccount"));//E账户账号
                        //更新会员表
                        customerInfoMapper.updateByPrimaryKeySelective(customerInfo);
                        logger.info("更新会员表,条数：" + bindResult);
                    }else {
                        logger.info("请求金核直接绑卡，返回值data为空！");
                        result.resetCode(AppCodeEnum._A108_BIND_CARD_FAIL);
                    }
                }else {
                    result.failure(pr.getCode(), pr.getMsg());
                }
            }else {
                logger.info("请求金核直接绑卡，返回值为空！");
                result.resetCode(AppCodeEnum._A108_BIND_CARD_FAIL);
            }
        } catch (Exception e) {
            logger.error("请求"+url+"异常!参数为"+jsonReq.toString(),e);
            result.resetCode(AppCodeEnum._A108_BIND_CARD_FAIL);
            return result;
        }
        return result;
    }*/

    /**
     * 直接绑卡
     */
    public BaseRes<ConfirmBindCardRes> directBindingCard(ConfirmBindCardReq card, String customerId) {
        BaseRes<ConfirmBindCardRes> result = new BaseRes<ConfirmBindCardRes>();
        String url = paymentHost + "/directBind";//直接绑卡
        JSONObject jsonReq = new JSONObject();
        try {
//            String orderNo = OrderNoGenerator.generate(Long.valueOf(customerId));//订单号
            String orderNo = UuidUtils.getUUID();
            Date orderDate = new Date();
            jsonReq.put("sourceId", CustomerConstants.PAYMENT_SOURCEID_MSD);//外部请求来源    
            jsonReq.put("memberId", customerId);//会员号
            jsonReq.put("mobile", card.getMobile());//手机号
            jsonReq.put("bankCardNo", card.getCardNo());//银行卡号
//            jsonReq.put("bankCode", card.getBankCode());//银行编码
            jsonReq.put("idCardType", card.getIdCardType());//身份证类型
            jsonReq.put("idCardNo", card.getIdCardNo().toUpperCase());//证件号码
            jsonReq.put("idCardName", card.getMemberName());//客户姓名
            jsonReq.put("orderTime", orderDate);//订单时间
            jsonReq.put("orderNo", orderNo);//订单号
//            jsonReq.put("memberType", card.getMemberType());//客户类型
//            jsonReq.put("openEBankAccount", isEBankAccount);//0：不开通 1：开通
            logger.info(url + "参数：" + jsonReq.toString());

            String response = HttpClientUtils.doPost(url, jsonReq.toString());
            logger.info("金核直接绑卡返回:" + response);
            PaymentResponse pr = (PaymentResponse) JsonUtil.getObjectByJsonStr(response, PaymentResponse.class);
            if (pr != null) {
                if (AppCodeEnum._0000_SUCCESS.getCode().equals(pr.getCode())) {//成功
                    if (pr.getData() != null) {
                        JSONObject json = (JSONObject) pr.getData();

                        CustomerBindcard customerBindcard = new CustomerBindcard();
                        customerBindcard.setCustomerId(Long.valueOf(customerId));//客户ID
                        customerBindcard.setBankCardNo(card.getCardNo());//银行卡号
                        customerBindcard.setBankCode(card.getBankCode());//银行简称
                        customerBindcard.setBankName(card.getBankName());//银行名称
                        customerBindcard.setCardType(card.getCardType());//卡类型D：借记卡，C：贷记卡
                        customerBindcard.setStatus(CustomerConstants.BIND_STAUS_1);//状态[0：预绑卡、1：启用 2:解绑]
                        customerBindcard.setIdCardType(card.getIdCardType());//证件类型 01:身份证
                        customerBindcard.setIdCardNo(card.getIdCardNo());//证件号
                        customerBindcard.setIdCardName(card.getMemberName());//证件姓名
                        customerBindcard.setMobile(card.getMobile());//银行绑定手机号
                        customerBindcard.setPreOrderNo(orderNo);//预绑卡订单号
                        customerBindcard.setConfirmOrderNo(orderNo);
                        customerBindcard.setPreOrderTime(orderDate);//预绑卡订单时间
                        customerBindcard.setConfirmOrderTime(orderDate);
                        customerBindcard.setSignId(json.getString("signId"));//签约协议号

                        customerBindcardMapper.insertSelective(customerBindcard);

                        CustomerInfo customerInfo = new CustomerInfo();
                        customerInfo.setCustomerId(Long.valueOf(customerId));//客户ID
                        customerInfo.setIsBindCard(CustomerConstants.IS_BIND_CARD_1);//是否已绑卡,0-否1-是

                        //更新会员表绑卡信息
                        int count2 = customerInfoMapper.updateByPrimaryKeySelective(customerInfo);
                        logger.info("更新会员表绑卡信息:" + count2);

                        //开户
                        CustomerInfo tempCustomerInfo = cardService.getCustomerInfo(customerId);
                        OpenAccountReq openAccountReq = new OpenAccountReq();
                        openAccountReq.setAccountPurpose(AccountPurposeEnum._102.getCode());
                        openAccountReq.setAccountType(CustomerConstants.PAYMENT_ACCOUNT_TYPE_LOGIC);
                        openAccountReq.setMemberType(CustomerConstants.PAYMENT_MEMBER_TYPE_10);
                        openAccountReq.setMemberId(customerId);
                        openAccountReq.setSourceId(AppConstant.SOURCE_ID_MSD);
                        infoService.openAccount(tempCustomerInfo, openAccountReq);

                    } else {
                        logger.info("请求支付直接绑卡，返回值data为空！");
                        result.resetCode(AppCodeEnum._A108_BIND_CARD_FAIL);
                    }
                } else {
                    result.failure(pr.getCode(), pr.getMsg());
                }
            } else {
                logger.info("请求支付直接绑卡，返回值为空！");
                result.resetCode(AppCodeEnum._A108_BIND_CARD_FAIL);
            }
        } catch (Exception e) {
            logger.error("请求" + url + "异常!参数为" + jsonReq.toString(), e);
            result.resetCode(AppCodeEnum._A108_BIND_CARD_FAIL);
            return result;
        }
        return result;
    }

    @Override
    @Transactional
    public BaseRes<Object> updateBindCard(BindCardNotifyReq req) throws Exception {
        BaseRes<Object> result = new BaseRes<Object>();
        Long customerId = Long.valueOf(req.getCustomerId());
        //查询会员信息
        CustomerInfo user = customerInfoMapper.selectByPrimaryKey(customerId);
        if (user != null) {
            //是否开通银行存管,0-否1-是2-处理中3-失败
            Integer isDepositManage = user.getIsDepositManage();
            logger.info("查询会员信息，是否开通银行存管：" + isDepositManage);
            if (CustomerConstants.TWO.equals(isDepositManage) || CustomerConstants.ZERO.equals(isDepositManage) ||
                    CustomerConstants.THREE.equals(isDepositManage)) {//处理中的场合，更新相关表
                if ("1".equals(req.getStatus())) {//1-成功
                    UserBindCardLog userBindCardLog = new UserBindCardLog();
                    userBindCardLog.setCustomerId(customerId);//客户ID
                    userBindCardLog.setStatus(Integer.valueOf(req.getStatus()));//状态[1-成功2-处理中3-失败]
                    userBindCardLog.setOutOrderNo(req.getOrderNo());//请求流水号
                    userBindCardLog.setContent("成功");//绑卡结果内容
                    //更新存管绑卡日志表
                    int logResult = userBindCardLogMapper.updateByOutOrderNo(userBindCardLog);
                    logger.info("更新绑卡日志表:" + logResult);

                    //查询绑卡日志表
                    UserBindCardLog bindCardLog = new UserBindCardLog();
                    bindCardLog.setOutOrderNo(req.getOrderNo());//请求流水号
                    bindCardLog.setCustomerId(customerId);
                    UserBindCardLog log = userBindCardLogMapper.selectCardLogByOutOrderNo(bindCardLog);
                    logger.info("查询绑卡日志表:" + log);

                    if (log != null) {
                        UserBindCard userBindCard = new UserBindCard();
                        userBindCard.setCustomerId(customerId);//客户ID
                        userBindCard.setCardNo(log.getCardNo());//银行卡号
                        userBindCard.setBankCode(log.getBankCode());//银行简称
                        userBindCard.setBankName(log.getBankName());//银行名称
                        userBindCard.setCardType(log.getCardType());//卡类型D：借记卡，C：贷记卡
                        userBindCard.setIdCardType(log.getIdCardType());//证件类型 01:身份证
                        userBindCard.setIdCardNo(log.getIdCardNo());//证件号
                        userBindCard.setMemberName(log.getMemberName());//证件姓名
                        userBindCard.setMobile(log.getMobile());//银行卡号对应的银行预留手机号
                        userBindCard.setSignId(log.getSignId());//签约协议号
                        //插入存管绑卡表
                        int bindcardResult = userBindCardMapper.insertSelective(userBindCard);
                        logger.info("插入绑卡表:" + bindcardResult);

                        CustomerInfo customerInfo = new CustomerInfo();
                        customerInfo.setCustomerId(customerId);//客户ID
                        customerInfo.setIsDepositManage(CustomerConstants.ONE);//是否开通银行存管,0-否1-是2-处理中3-失败
                        customerInfo.setIsReal(CustomerConstants.IS_REAL_1);//是否已实名,0-否1-是
                        customerInfo.setIsBindCard(CustomerConstants.IS_BIND_CARD_1);//是否已绑卡,0-否1-是
                        customerInfo.setRealName(log.getMemberName());//客户真实姓名
                        customerInfo.setIdCardNo(log.getIdCardNo());//证件号码
                        customerInfo.setIdCardType(log.getIdCardType());//证件类型,01-身份证
                        customerInfo.setEBankAccount(req.geteBankAccount());//E账户账号
                        //开户
                        result = openAccount(customerInfo, customerId);
                    } else {
                        logger.info("查询绑卡日志表为空");
                    }
                } else if ("3".equals(req.getStatus())) {//失败
                    UserBindCardLog userBindCardLog = new UserBindCardLog();
                    userBindCardLog.setCustomerId(customerId);//客户ID
                    userBindCardLog.setStatus(Integer.valueOf(req.getStatus()));//状态[1-成功2-处理中3-失败]
                    userBindCardLog.setOutOrderNo(req.getOrderNo());//请求流水号
                    if (req.getContent() == null || req.getContent() == "") {//银行未返回失败原因的场合
                        userBindCardLog.setContent("失败");//绑卡结果内容
                    } else {
                        userBindCardLog.setContent(req.getContent());//绑卡结果内容
                    }
                    //更新存管绑卡日志表
                    int logResult = userBindCardLogMapper.updateByOutOrderNo(userBindCardLog);
                    logger.info("更新绑卡日志表:" + logResult);

                    CustomerInfo customerInfo = new CustomerInfo();
                    customerInfo.setCustomerId(customerId);//客户ID
                    customerInfo.setIsDepositManage(CustomerConstants.THREE);//是否开通银行存管,0-否1-是2-处理中3-失败
                    //更新会员信息表
                    int custResult = customerInfoMapper.updateByPrimaryKeySelective(customerInfo);
                    logger.info("绑卡开户失败，更新会员表:" + custResult);
                } else if ("2".equals(req.getStatus())) {
                    UserBindCardLog userBindCardLog = new UserBindCardLog();
                    userBindCardLog.setCustomerId(customerId);//客户ID
                    userBindCardLog.setStatus(Integer.valueOf(req.getStatus()));//状态[1-成功2-处理中3-失败]
                    userBindCardLog.setOutOrderNo(req.getOrderNo());//请求流水号
                    userBindCardLog.setContent(req.getContent());//绑卡结果内容
                    //更新存管绑卡日志表
                    int logResult = userBindCardLogMapper.updateByOutOrderNo(userBindCardLog);
                    logger.info("绑卡开户处理中，更新绑卡日志表:" + logResult);
                }
            }
        } else {
            logger.info("根据customerId查询会员信息为空！");
        }
        return result;
    }

    @Override
    public BaseRes<PreBind> sendMsgByPreBind(String customerId, BindCard bindCard) {
        BaseRes<PreBind> result = new BaseRes<PreBind>();
        String url = paymentHost + "/preBind";//预绑卡地址
        JSONObject jsonReq = new JSONObject();

        try {
            String orderNo = OrderNoGenerator.generate(Long.valueOf(customerId));//订单号
            Date orderDate = new Date();
            jsonReq.put("sourceId", CustomerConstants.PAYMENT_SOURCEID_P2P);//系统来源
            jsonReq.put("orderNo", orderNo);//订单号
            jsonReq.put("orderTime", orderDate);//订单时间
            jsonReq.put("bankCardNo", bindCard.getBankCardNo());//银行卡号
            jsonReq.put("bankCode", bindCard.getBankCode());//银行编码
            jsonReq.put("idCardType", bindCard.getIdCardType());//证件类型
            jsonReq.put("idCardNo", bindCard.getIdCardNo());//证件号码
            jsonReq.put("idCardName", bindCard.getIdCardName());//证件名
            jsonReq.put("mobile", bindCard.getMobile());//银行绑定手机号
            jsonReq.put("memberId", customerId);//会员id
            logger.info(url + "参数：" + jsonReq.toString());

            String response = HttpClientUtils.doPost(url, jsonReq.toString());
            logger.info("预绑卡返回:" + response);
            PaymentResponse pr = (PaymentResponse) JsonUtil.getObjectByJsonStr(response, PaymentResponse.class);

            if (pr != null) {
                if (AppCodeEnum._0000_SUCCESS.getCode().equals(pr.getCode())) {//成功
                    PreBind preBind = new PreBind();
                    preBind.setOrderNo(orderNo);//订单号
                    result.setData(preBind);

                    CustomerBindcard bind = new CustomerBindcard();
                    bind.setCustomerId(Long.valueOf(customerId));
                    bind.setStatus(CustomerConstants.BIND_STAUS_0);//状态[0：预绑卡、1：启用 2:解绑]
                    CustomerBindcard custBindcard = customerBindcardMapper.selectByPreBindCard(bind);

                    CustomerBindcard card = new CustomerBindcard();
                    card.setCustomerId(Long.valueOf(customerId));//客户ID
                    card.setBankCardNo(bindCard.getBankCardNo());//银行卡号
                    card.setBankCode(bindCard.getBankCode());//银行简称
                    card.setBankName(bindCard.getBankName());//银行名称
                    card.setCardType(bindCard.getCardType());//卡类型D：借记卡，C：贷记卡
                    card.setStatus(CustomerConstants.BIND_STAUS_0);//状态[0：预绑卡、1：启用 2:解绑]
                    card.setIdCardType(bindCard.getIdCardType());//证件类型 01:身份证
                    card.setIdCardNo(bindCard.getIdCardNo());//证件号
                    card.setIdCardName(bindCard.getIdCardName());//证件姓名
                    card.setMobile(bindCard.getMobile());//银行绑定手机号
                    card.setPreOrderNo(orderNo);//预绑卡订单号
                    card.setPreOrderTime(orderDate);//预绑卡订单时间
                    if (custBindcard != null) {//该用户非首次调用预绑卡
                        card.setBindId(custBindcard.getBindId());
                        //更新会员绑卡表,记录绑定的银行卡信息
                        int count = customerBindcardMapper.updateByPrimaryKeySelective(card);
                        logger.info("更新会员绑卡表,记录绑定的银行卡信息:" + count);
                    } else {//该用户首次调用预绑卡
                        //插入会员绑卡表,记录绑定的银行卡信息
                        int count = customerBindcardMapper.insertSelective(card);
                        logger.info("插入会员绑卡表,记录绑定的银行卡信息:" + count);
                    }
                } else {
                    result.failure(pr.getCode(), pr.getMsg());
                }
            }
        } catch (Exception e) {
            logger.error("请求" + url + "异常!参数为" + jsonReq.toString(), e);
            result.resetCode(AppCodeEnum._A109_BIND_CARD_FAIL);
            return result;
        }
        return result;
    }

    @Override
    @Transactional
    public BaseRes<Object> confirmBind(CustomerBindcard bindcardRes, String smsCode, String originalOrderNo, String customerId) {
        BaseRes<Object> result = new BaseRes<Object>();
        String url = paymentHost + "/confirmBind";//确认绑卡地址
        JSONObject jsonReq = new JSONObject();

        try {
            String confirmOrderNo = OrderNoGenerator.generate(Long.valueOf(customerId));//订单号
            Date confirmOrderDate = new Date();
            jsonReq.put("sourceId", CustomerConstants.PAYMENT_SOURCEID_P2P);//系统来源
            jsonReq.put("orderNo", confirmOrderNo);//订单号
            jsonReq.put("orderTime", confirmOrderDate);//订单时间
            jsonReq.put("memberId", customerId);//会员id
            jsonReq.put("originalOrderNo", originalOrderNo);//银行卡号
            jsonReq.put("smsCode", smsCode);//银行编码
            logger.info(url + "参数：" + jsonReq.toString());

            String response = HttpClientUtils.doPost(url, jsonReq.toString());//确认绑卡
            logger.info("确认绑卡返回:" + response);
            PaymentResponse pr = (PaymentResponse) JsonUtil.getObjectByJsonStr(response, PaymentResponse.class);
            if (pr != null) {
                if (AppCodeEnum._0000_SUCCESS.getCode().equals(pr.getCode())) {//确认绑卡成功
                    if (pr.getData() != null) {
                        bindcardRes.setStatus(CustomerConstants.BIND_STAUS_1);//状态[0：预绑卡、1：启用 2:解绑]
                        bindcardRes.setConfirmOrderNo(confirmOrderNo);//确认绑卡订单号
                        bindcardRes.setConfirmOrderTime(confirmOrderDate);//确认预绑卡订单时间
                        JSONObject json = (JSONObject) pr.getData();
                        bindcardRes.setSignId(json.get("signId").toString());//签约协议号
                        //更新绑卡银行卡信息
                        int count1 = customerBindcardMapper.updateByPrimaryKeySelective(bindcardRes);
                        logger.info("更新绑卡银行卡信息:" + count1);

                        CustomerInfo customerInfo = new CustomerInfo();
                        customerInfo.setCustomerId(Long.valueOf(customerId));//客户ID
                        customerInfo.setIsBindCard(CustomerConstants.IS_BIND_CARD_1);//是否已绑卡,0-否1-是
                        //更新会员表绑卡信息
                        int count2 = customerInfoMapper.updateByPrimaryKeySelective(customerInfo);
                        logger.info("更新会员表绑卡信息:" + count2);
                        return result;
                    } else {
                        logger.info("调用金核[确认绑卡]未返回signId");
                    }
                } else {
                    result.failure(pr.getCode(), pr.getMsg());
                    return result;
                }
            } else {
                logger.info("调用金核[确认绑卡]返回值为空");
            }
            result.resetCode(AppCodeEnum._A112_CONFIRM_BIND_FAIL);
        } catch (Exception e) {
            logger.error("请求" + url + "异常!参数为" + jsonReq.toString(), e);
            result.resetCode(AppCodeEnum._A112_CONFIRM_BIND_FAIL);
            return result;
        }
        return result;
    }

    @Override
    public BaseRes<Object> openAccount(CustomerInfo cust, Long customerId) {
        BaseRes<Object> result = new BaseRes<Object>();
        String url = paymentHost + "/openAccount";//开户地址
        JSONObject jsonReq = new JSONObject();
        try {
            //开户操作
            String orderNo = OrderNoGenerator.generate(customerId);//订单号
            Date orderDate = new Date();
            jsonReq.put("sourceId", CustomerConstants.PAYMENT_SOURCEID_MSD);//系统来源
            jsonReq.put("orderNo", orderNo);//订单号
            jsonReq.put("orderTime", orderDate);//订单时间
            jsonReq.put("accountName", cust.getRealName());//账户名称
            jsonReq.put("accountType", CustomerConstants.PAYMENT_ACCOUNT_TYPE_LOGIC);//账户类型：VIR-虚拟账户,ELC-电子账户，logic-逻辑账户
            jsonReq.put("accountPurpose", AccountPurposeEnum._102.getCode());//账户用途(102:出借投资账户)
            jsonReq.put("memberId", customerId);//会员id
            jsonReq.put("memberType", CustomerConstants.PAYMENT_MEMBER_TYPE_10);//会员类型(10-个人,20-机构)
            logger.info(url + "参数：" + jsonReq.toString());

            String resp = HttpClientUtils.doPost(url, jsonReq.toString());//开户
            logger.info("开户返回:" + resp);
            PaymentResponse payRes = (PaymentResponse) JsonUtil.getObjectByJsonStr(resp, PaymentResponse.class);

            if (payRes != null) {
                if (AppCodeEnum._0000_SUCCESS.getCode().equals(payRes.getCode())) {//开户成功
                    if (payRes.getData() != null) {
                        CustomerInfo customerInfo = new CustomerInfo();
                        customerInfo.setCustomerId(customerId);//客户ID
                        customerInfo.setRealName(cust.getRealName());//客户真实姓名
                        customerInfo.setIsReal(CustomerConstants.IS_REAL_1);//是否已实名,0-否1-是
                        customerInfo.setIdCardType(cust.getIdCardType());//证件类型,01-身份证
                        customerInfo.setIdCardNo(cust.getIdCardNo());//证件号码
                        customerInfo.setIsDepositManage(CustomerConstants.ONE);//是否开通银行存管,0-否1-是2-处理中3-失败
                        customerInfo.setIsBindCard(CustomerConstants.IS_BIND_CARD_1);//是否已绑卡,0-否1-是
                        customerInfo.setEBankAccount(cust.getEBankAccount());//E账户账号

                        JSONObject json = (JSONObject) payRes.getData();
                        customerInfo.setAccountNo(json.getString("accountNo"));
                        //更新会员表绑卡信息
                        int count2 = customerInfoMapper.updateByPrimaryKeySelective(customerInfo);
                        logger.info("更新会员表绑卡信息:" + count2);
                        return result;
                    } else {
                        logger.info("调用金核[开户]未返回accountNo");
                    }
                } else {
                    result.failure(payRes.getCode(), payRes.getMsg());
                    return result;
                }
            } else {
                logger.info("调用金核[开户]返回值为空");
            }
            result.resetCode(AppCodeEnum._A113_OPEN_ACCOUNT_FAIL);
        } catch (Exception e) {
            logger.error("请求" + url + "异常!参数为" + jsonReq.toString(), e);
            result.resetCode(AppCodeEnum._A113_OPEN_ACCOUNT_FAIL);
            return result;
        }
        return result;
    }

    @Transactional
    @Override
    public BaseRes<Object> unBind(String bindId, String customerId, UnBindReq req) {
        BaseRes<Object> result = new BaseRes<Object>();
        String url = paymentHost + "/unBind";//解绑卡地址
        JSONObject jsonReq = new JSONObject();
        try {

            //根据会员Id查询会员信息
            CustomerInfo customerInfoTemp = cardService.getCustomerInfo(customerId);
            //根据bindId查询绑卡信息
            CustomerBindcard customerBindcard = customerBindcardMapper.selectByPrimaryKey(Long.valueOf(bindId));


            if (customerInfoTemp != null) {

                if (customerBindcard != null) {
                    if (!customerId.equals(String.valueOf(customerBindcard.getCustomerId()))) {//判断这个会员id是否合法
                        result.resetCode(AppCodeEnum._0001_ILLEGAL_PARAMETER);
                        logger.info("入参customerId和根据bindId查询的会员信息中的customerId不一致");
                        return result;
                    } else if (2 == customerBindcard.getStatus()) { //解绑重复
                        result.resetCode(AppCodeEnum._A124_UNBIND_DUPLICATE);
                        return result;
                    }

                } else {
                    result.resetCode(AppCodeEnum._A124_UNBIND_CARD_NOT_FOUND);
                    logger.info("绑卡信息不存在");
                    return result;
                }

                //短信验证码校验
                CheckSmsCaptchaBO bo = new CheckSmsCaptchaBO();
                bo.setMobile(customerBindcard.getMobile());// 预留手机号
                bo.setOtpBusiCode("05");//05-解绑
                bo.setSmsCaptchaVal(req.getSmsCode());//短信验证码
                if (StringUtils.isBlank(req.getSmsCode()) || !otpService.checkSmsCaptchaForInner(bo)) {
                    result.resetCode(AppCodeEnum._A005_SMS_CAPT_INVALID);
                    logger.info("验证码不正确！" + result.toString());
                    return result;
                }

                //验证资产是否为零(在投)
                HoldTotalAssetsReq holdTotalAssetsReq = new HoldTotalAssetsReq();
                holdTotalAssetsReq.setMemberId(customerId);
                holdTotalAssetsReq.setInterestDate(DateUtil.formatDate(new Date()));
                holdTotalAssetsReq.setCaller("JHHY");

                ExternalBaseRes<HoldTotalAssetsBO> responseEntity = orderServiceClient.holdTotalAssets(holdTotalAssetsReq);
                if (responseEntity != null) {
                    if (responseEntity.getData() != null && (Double.valueOf(responseEntity.getData().getTotalAssets()) > 0)) {
                        result.resetCode(AppCodeEnum._A105_UNBIND_FAIL);
                        logger.info(result.toString());
                        return result;
                    }
                } else {
                    result.resetCode(AppCodeEnum._A104_UNBIND_FAIL);
                    logger.info("调用订单服务getAssetsAmount接口返回值为空！" + result.toString());
                    return result;
                }

                //验证余额是否为零
                BigDecimal zero = new BigDecimal(0);
                try {
                    BigDecimal banlance = infoService.queryBanlanceByAccountNo(customerInfoTemp.getAccountNo());
                    if (banlance != null) {
                        if (banlance.compareTo(zero) != 0) {
                            result.resetCode(AppCodeEnum._A105_UNBIND_FAIL);
                            logger.info(result.toString());
                            return result;
                        }
                    } else {
                        logger.info("查询余额为空！");
                    }
                } catch (AppException e) {
                    logger.info("查询余额:账户不存在或记录不存在");
                }


//              String orderNo = OrderNoGenerator.generate(Long.valueOf(customerId));//订单号

                String orderNo = UuidUtils.getUUID();
                Date orderDate = new Date();
                jsonReq.put("sourceId", CustomerConstants.PAYMENT_SOURCEID_MSD);//系统来源
                jsonReq.put("orderNo", orderNo);//订单号
                jsonReq.put("orderTime", orderDate);//订单时间
//              jsonReq.put("memberId", customerId);//会员id
                jsonReq.put("signId", customerBindcard.getSignId());//签约协议号
                logger.info(url + "参数：" + jsonReq.toString());

                String response = HttpClientUtils.doPost(url, jsonReq.toString());
                logger.info("解绑卡返回:" + response);
                PaymentResponse pr = (PaymentResponse) JsonUtil.getObjectByJsonStr(response, PaymentResponse.class);
                if (pr != null) {
                    if (AppCodeEnum._0000_SUCCESS.getCode().equals(pr.getCode())) {//成功
                        CustomerBindcard bindcard = new CustomerBindcard();
                        bindcard.setBindId(customerBindcard.getBindId());
                        bindcard.setStatus(CustomerConstants.BIND_STAUS_2);//状态[0：预绑卡、1：启用 2:解绑]
                        //更新绑卡表的该卡状态为解绑
                        int count1 = customerBindcardMapper.updateByPrimaryKeySelective(bindcard);
                        logger.info("更新绑卡表的该卡状态为解绑:" + count1);

                        CustomerInfo customerInfo = new CustomerInfo();
                        customerInfo.setCustomerId(Long.valueOf(customerId));//客户ID
                        customerInfo.setIsBindCard(CustomerConstants.IS_BIND_CARD_0);//是否已绑卡,0-否1-是
                        //更新会员表的绑卡状态为未绑卡
                        int count2 = customerInfoMapper.updateByPrimaryKeySelective(customerInfo);
                        logger.info("更新会员表的绑卡状态为未绑卡:" + count2);
                    } else {
                        result.failure(pr.getCode(), pr.getMsg());
                    }
                }
            } else {
                logger.info("根据customerId查询的会员信息为空！");
                result.failure(AppCodeEnum._0001_ILLEGAL_PARAMETER.getCode(), AppCodeEnum._0001_ILLEGAL_PARAMETER.getMessage());
                return result;
            }


        } catch (Exception e) {
            logger.error("请求" + url + "异常!参数为" + jsonReq.toString(), e);
            result.resetCode(AppCodeEnum._A104_UNBIND_FAIL);
            return result;
        }
        return result;
    }

    @ReadOnlyConnection
    @Override
    public CustomerInfo getCustomerInfo(String customerId) {
        // 查询会员信息
        return customerInfoMapper.selectByPrimaryKey(Long.valueOf(customerId));
    }

    @Override
    public CustomerBindcard getCustomerBindcard(String bindId) throws Exception {
        // 查询会员信息
        return customerBindcardMapper.selectByPrimaryKey(Long.valueOf(bindId));
    }

    @ReadOnlyConnection
    @Override
    public CustomerInfo selectByPrimaryIden(String idCardNo) {
        return customerInfoMapper.selectByPrimaryIden(idCardNo);
    }

    @Override
    public CustomerBindcard selectByPreBindCard(CustomerBindcard record) throws Exception {
        return customerBindcardMapper.selectByPreBindCard(record);
    }

    @Override
    public CustomerBindcard selectCustomerCardByCardNo(String cardNo) {
        CustomerBindcard bindcardRes = customerBindcardMapper.selectCardByCardNo(cardNo);
        return bindcardRes;
    }

    @Override
    public CustomerBindcard queryCardByCustomerId(Long customerId) {
        CustomerBindcard bindcardRes = customerBindcardMapper.selectCustUseCard(customerId);
        return bindcardRes;
    }

    @Override
    public UserBindCard selectCardByCardNo(String cardNo) throws Exception {
        //查询绑卡信息
        UserBindCard userBindCard = userBindCardMapper.selectCardByCardNo(cardNo);
        return userBindCard;
    }

    @Override
    public BindCardResultRes bindCardResult(BindCardResultReq req, Long customerId) throws Exception {
        BindCardResultRes res = null;

        CustomerInfo customerInfo = customerInfoMapper.selectByPrimaryKey(customerId);
        if (customerInfo != null) {
            Integer isDepositManage = customerInfo.getIsDepositManage();//是否开通银行存管,0-否1-是2-处理中3-失败
            if (isDepositManage != null) {
                if (CustomerConstants.ZERO.equals(isDepositManage)) {//未开通
                    res = new BindCardResultRes();
                    res.setStatus(isDepositManage.toString());
                    res.setContent("未开通");
                } else if (CustomerConstants.ONE.equals(isDepositManage)) {//开通成功
                    res = new BindCardResultRes();
                    res.setStatus(isDepositManage.toString());
                    res.setContent("成功");
                } else if (CustomerConstants.TWO.equals(isDepositManage)) {//处理中
                    res = new BindCardResultRes();
                    res.setStatus(isDepositManage.toString());
                    res.setContent("处理中");
                } else if (CustomerConstants.THREE.equals(isDepositManage)) {//失败
                    UserBindCardLog log = new UserBindCardLog();
                    log.setCustomerId(customerId);
                    if (req.getOutOrderNo() != null) {
                        log.setOutOrderNo(req.getOutOrderNo());
                    }
                    log.setStatus(CustomerConstants.THREE);
                    //查询绑卡日志表
                    UserBindCardLog userBindCardLog = userBindCardLogMapper.selectCardLogByOutOrderNo(log);
                    if (userBindCardLog != null) {
                        res = new BindCardResultRes();
                        res.setStatus(isDepositManage.toString());
                        res.setContent(userBindCardLog.getContent());
                    }
                }
            }
        }
        return res;
    }

    @Override
    public BindCardHelp bindCardHelp(Long customerId) throws Exception {
        BindCardHelp help = new BindCardHelp();
        CustomerInfo info = customerInfoMapper.selectByPrimaryKey(customerId);
        if (info == null) {
            return help;
        }
        /*if (CustomerConstants.ONE.equals(info.getIsDepositManage())) {//已开通存管
            logger.info("已开通存管");
            return help;
        }else {*/
        if (CustomerConstants.ONE.equals(info.getIsBindCard())) {//已绑卡
            //查询宝付绑卡表
            logger.info("查询宝付绑卡表");
            CustomerBindcard card = customerBindcardMapper.selectCustUseCard(customerId);
            if (card == null) {
                return help;
            }
            help.setName(info.getRealName());//姓名
            help.setBankCardNo(card.getBankCardNo());//银行卡号
            help.setBankMobile(card.getMobile());//银行预留手机号
            help.setIdCardNo(info.getIdCardNo());//身份证号
        }
        if (CustomerConstants.ZERO.equals(info.getIsBindCard())) {//未绑卡
            TxsCusInfo txsCusInfo = new TxsCusInfo();
            logger.info("缓存中查询唐小僧会员信息");
            boolean flag = redisService.exists(AppConstant.P2P_REDIS_KEY_TXS_MEMBERINFO + info.getChannelCustomerId());
            if (flag) {
                String str = redisService.get(AppConstant.P2P_REDIS_KEY_TXS_MEMBERINFO + info.getChannelCustomerId());
                txsCusInfo = (TxsCusInfo) JsonUtil.getObjectByJsonStr(str, TxsCusInfo.class);
            } else {
                help.setName(info.getRealName());//姓名
                help.setIdCardNo(info.getIdCardNo());//身份证号
                help.setIsReal(info.getIsReal().toString());//是否实名
                help.setIsBindCard(info.getIsBindCard().toString());//是否绑卡
                return help;
            }
            help.setBankCardNo(txsCusInfo.getCardNumber());//银行卡号
            help.setBankMobile(txsCusInfo.getPhoneNumber());//银行预留手机号
            help.setName(txsCusInfo.getName());//姓名
            help.setIdCardNo(txsCusInfo.getIdentityNumber());//身份证号
        }
        help.setIsReal(info.getIsReal().toString());//是否实名
        help.setIsBindCard(info.getIsBindCard().toString());//是否绑卡
//        }
        return help;
    }
}
