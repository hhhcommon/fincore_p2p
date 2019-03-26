package com.zb.p2p.customer.service.impl;

import com.zb.p2p.customer.api.entity.*;
import com.zb.p2p.customer.client.domain.RegisteredBackReq;
import com.zb.p2p.customer.client.domain.TxsResponse;
import com.zb.p2p.customer.client.txs.TxsClient;
import com.zb.p2p.customer.common.constant.AppConstant;
import com.zb.p2p.customer.common.enums.AppCodeEnum;
import com.zb.p2p.customer.common.model.BaseRes;
import com.zb.p2p.customer.common.util.DateUtil;
import com.zb.p2p.customer.common.util.JsonUtil;
import com.zb.p2p.customer.common.util.UuidUtils;
import com.zb.p2p.customer.dao.CustomerInfoMapper;
import com.zb.p2p.customer.dao.MemberBasicInfoMapper;
import com.zb.p2p.customer.dao.NotifyMapper;
import com.zb.p2p.customer.dao.domain.CustomerInfo;
import com.zb.p2p.customer.dao.domain.MemberBasicInfo;
import com.zb.p2p.customer.dao.domain.NotifyInfo;
import com.zb.p2p.customer.service.LoginService;
import com.zb.p2p.customer.service.URLDispenserService;
import com.zb.p2p.customer.service.bo.UserInfoResponseContent;
import com.zb.qjs.common.client.MQProducer;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;


@Service
public class URLDispenserServiceImpl implements URLDispenserService {
    private static final Logger logger = LoggerFactory.getLogger(URLDispenserServiceImpl.class);

    @Autowired
    RedisService redisService;
    @Autowired
    CustomerInfoMapper customerInfoMapper;
    @Autowired
    MemberBasicInfoMapper memberBasicInfoMapper;
    @Autowired
    LoginService loginService;
    @Autowired
    NotifyMapper notifyMapper;

    @Value("${env.dispatchUrl}")
    private String dispatchUrl;//

    @Value("${env.smsCaptchaExpireTime}")
    private Long smsCaptchaExpireTime;

    @Value("${ons.ali.topic}")
    private String topic;//

    @Autowired
    private TxsClient txsClient;

    private final static String MEMBER_TYPE = "INVEST";

    @Override
    public URLDispenserResp urlDispenser(URLDispenserRequest request) {
        //生成code
        String code = AppConstant.P2P_REDIS_KEY_CHECKCODE + UuidUtils.getUUID();
        String requestJson = JsonUtil.convertObjToStr(request);
        logger.info("urlDispenser 进来      uuid-code:{}     ,    request: {}  ,requestJson:{} , request.getSourceParam(): {} ", code, request, requestJson, request.getSourceParam());
        //放入缓存
        logger.info("放入缓存：" + code);
        redisService.set(code, requestJson, 3000);
        logger.info("放入缓存成功有效期300秒：" + code);
        String reUrl = dispatchUrl.replace("@version", String.valueOf(System.currentTimeMillis()));
        String url = reUrl + "?checkcode=" + code;
        URLDispenserResp resp = new URLDispenserResp();
        resp.setUrl(url);
        return resp;
    }

    @Override
    public BaseRes<CheckCodeRes> checkCode(CheckCodeRequest req) {

        logger.info("校验code： " + req.getCode());
        BaseRes<CheckCodeRes> baseR = new BaseRes<>();
        CheckCodeRes res = new CheckCodeRes();

        Boolean fla = redisService.exists(req.getCode());
        String result = null;
        if (!fla) {
            baseR.setCode(AppCodeEnum._A008_CHECK_CODE.getCode());
            baseR.setMessage(AppCodeEnum._A008_CHECK_CODE.getMessage());
            return baseR;

        } else {
            result = redisService.get(req.getCode());
        }

        logger.info("checkCode 拿到的  result：" + result);

        String token = AppConstant.P2P_REDIS_KEY_AUTHREGISTER + UuidUtils.getUUID();//生成新的 token

        URLDispenserRequest obj = (URLDispenserRequest) JsonUtil.getFastJsonObject(result, URLDispenserRequest.class);
        res.setSourceParam(obj.getSourceParam());
        logger.info("obj.getSourceParam():" + obj.getSourceParam());

        String txsAccountId = obj.getTxsAccountId();

        String requestTime = DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss");

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("txsAccountId", txsAccountId);
        jsonObject.put("requestTime", requestTime);


        logger.info("查询TXs用户信息jsonObject:====" + jsonObject);
        TxsResponse userInfoResponse = txsClient.queryMemberInfo(jsonObject);
        logger.info("查询TXs用户信息response:" + userInfoResponse.getCode() + " data:" + userInfoResponse.getData() + "---");

        if ("0000".equals(userInfoResponse.getCode())) {

            TxsResponse userInfoResponse2 = (TxsResponse) JsonUtil.getObjectByJsonStr(JsonUtil.printStrFromObj(userInfoResponse), TxsResponse.class);
            UserInfoResponseContent content = (UserInfoResponseContent) JsonUtil.getObjectByJsonStr(((com.alibaba.fastjson.JSONObject) userInfoResponse2.getData()).toJSONString(), UserInfoResponseContent.class);

            //缓存txs信息
            String txsStr = JsonUtil.printStrFromObj(content);
            logger.info("缓存txs信息" + txsStr);
            redisService.set(AppConstant.P2P_REDIS_KEY_TXS_MEMBERINFO + txsAccountId, txsStr, 86400);//一天
            logger.info("缓存txs信息完成" + txsStr);

            //检查txs客户是否注册 绑卡等信息
            MemberBasicInfo cinf_id = memberBasicInfoMapper.selectByTxsAccountId(txsAccountId, null);
            MemberBasicInfo cinf_mobile = memberBasicInfoMapper.selectByPrimaryMobile(content.getTxsPhoneNumber(), null);
            MemberBasicInfo cinf_idCard = memberBasicInfoMapper.selectByPrimaryIden(content.getIdentityNumber());

            if (cinf_id != null) {//老用户 已关联
                logger.info("===老用户===通过TXS  id 查到数据");
                CustomerInfo customerInfo = customerInfoMapper.selectByMemberIdAndType(cinf_id.getMemberId(), MEMBER_TYPE);
                if (customerInfo.getIsBindCard() != 1) {
                    res.setNextStep("4");//跳转绑卡开户
                } else {
                    res.setNextStep("3");//其他
                }

                res.setSourcePage(obj.getSourcePage());

                LoginCustomerDetail detail = loginService.login(cinf_id.getMobile());//获取loginToken

                logger.info("老用户已关联 获取token登录 setLogintoken：" + detail.getLoginToken());

                res.setToken(detail.getLoginToken());
                res.setCustomerId(detail.getCustomerId());

                baseR.setCode(AppCodeEnum._0000_SUCCESS.getCode());
                baseR.setMessage(AppCodeEnum._0000_SUCCESS.getMessage());
                baseR.setData(res);

                redisService.set(token, result, 3000);
                logger.info("校验code：" + token + " result:" + result);
                redisService.del(req.getCode());
                return baseR;
            } else {//未关联的用户
                if (cinf_idCard != null) {//身份证号存在  不管手机号是否相同 都走授权
                    logger.info("===身份证相同==授权");

                    res.setNextStep("1");
                    res.setToken(token);

                    logger.info("===身份证相同== 注册手机号为：" + cinf_idCard.getMobile());

                    res.setSourcePage(obj.getSourcePage());
                    baseR.setCode(AppCodeEnum._0000_SUCCESS.getCode());
                    baseR.setMessage(AppCodeEnum._0000_SUCCESS.getMessage());
                    baseR.setData(res);

                    obj.setMobile(cinf_idCard.getMobile());
                    String jihuoStr = JsonUtil.printStrFromObj(obj);

                    redisService.set(token, jihuoStr, 3000);
                    logger.info("校验code：" + token + " jihuoStr:" + jihuoStr);
                    redisService.del(req.getCode());

                    return baseR;
                } else {//身份证不存在
                    if (cinf_mobile == null) {//手机号也不存在   走 授权
                        logger.info("===全新用户===手机号身份证都没查到");
                        res.setNextStep("1");
                        res.setToken(token);
                        res.setSourcePage(obj.getSourcePage());
                        baseR.setCode(AppCodeEnum._0000_SUCCESS.getCode());
                        baseR.setMessage(AppCodeEnum._0000_SUCCESS.getMessage());
                        baseR.setData(res);

                        redisService.set(token, result, 3000);
                        logger.info("校验code：" + token + " result:" + result);
                        redisService.del(req.getCode());

                        return baseR;

                    } else {//手机号存在

                        if (cinf_mobile.getIsReal() == 0) {//该手机未实名  直接把  txs的实名带过来  授权
                            logger.info("===手机号已注册但是未实名===");
                            res.setNextStep("1");
                            res.setToken(token);
                            res.setSourcePage(obj.getSourcePage());
                            baseR.setCode(AppCodeEnum._0000_SUCCESS.getCode());
                            baseR.setMessage(AppCodeEnum._0000_SUCCESS.getMessage());
                            baseR.setData(res);

                            redisService.set(token, result, 3000);
                            logger.info("校验code：" + token + " result:" + result);
                            redisService.del(req.getCode());

                            return baseR;

                        } else {//该手机实名了 拒绝
                            logger.info("手机相同身份证不同 拒绝服务");
                            baseR.setCode(AppCodeEnum._A009_REFUSE.getCode());
                            baseR.setMessage(AppCodeEnum._A009_REFUSE.getMessage());
                            return baseR;
                        }
                    }
                }
            }
        } else {
            logger.info("查TXS用户信息失败");
            baseR.setCode(AppCodeEnum._A009_REFUSE.getCode());
            baseR.setMessage(AppCodeEnum._A009_REFUSE.getMessage());
            return baseR;
        }
    }

    @Transactional
    @Override
    public AuthregisterRes authregister(AuthregisterReq req) {
        logger.info("============authregister 开始授权注册==========");
        AuthregisterRes responseResult = new AuthregisterRes();
        String result = null;
        logger.info("authregister：content:" + req.getToken());
        Boolean fla = redisService.exists(req.getToken());
        logger.info("查询token--" + fla);
        if (fla) {
            result = redisService.get(req.getToken());
            logger.info("查询结果--" + result);
        } else {
            logger.info("查询token不存在--");
            return null;
        }

        URLDispenserRequest obj = (URLDispenserRequest) JsonUtil.getFastJsonObject(result, URLDispenserRequest.class);

        String txsAccountId = obj.getTxsAccountId();
        String requestTime = DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss");

        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("txsAccountId", txsAccountId);
            jsonObject.put("requestTime", requestTime);
            TxsResponse res = txsClient.queryMemberInfo(jsonObject);


            if ("0000".equals(res.getCode())) {
                if (res.getData() != null && !"".equals(res.getData())) {
                    TxsResponse userInfoResponse2 = (TxsResponse) JsonUtil.getObjectByJsonStr(JsonUtil.printStrFromObj(res), TxsResponse.class);
                    UserInfoResponseContent content = (UserInfoResponseContent) JsonUtil.getObjectByJsonStr(((com.alibaba.fastjson.JSONObject) userInfoResponse2.getData()).toJSONString(), UserInfoResponseContent.class);

                    //检查txs客户是否注册 绑卡等信息
                    MemberBasicInfo cinf_id = memberBasicInfoMapper.selectByTxsAccountId(txsAccountId, null);
                    MemberBasicInfo cinf_mobile = memberBasicInfoMapper.selectByPrimaryMobile(content.getTxsPhoneNumber(), null);
                    MemberBasicInfo cinf_idCard = memberBasicInfoMapper.selectByPrimaryIden(content.getIdentityNumber());

                    if (null != cinf_id) {
                        return null;
                    } else if (cinf_mobile == null && cinf_idCard == null) {//手机号 和身份都不存在 注册
                        MemberBasicInfo record = new MemberBasicInfo();
                        record.setMemberId(UuidUtils.getUUID());
                        record.setMobile(content.getTxsPhoneNumber());
                        record.setRealName(content.getName());
                        record.setIsReal(1);
                        record.setIdCardType("01");
                        record.setIdCardNo(content.getIdentityNumber());
                        record.setRegisterTime(new Date());
                        record.setChannelCustomerId(txsAccountId);
                        record.setCreateTime(new Date());
                        record.setChannelCode("TXS");
                        //保存会员主体
                        memberBasicInfoMapper.insertSelective(record);
                        CustomerInfo customerInfo = new CustomerInfo();
                        customerInfo.setMemberId(record.getMemberId());
                        customerInfo.setMemberType(MEMBER_TYPE);
                        //保存子会员
                        customerInfoMapper.insertSelective(customerInfo);

                        //回调唐小僧
                        RegisteredBackReq registeredBackReq = new RegisteredBackReq();

                        //放入回调表
                        NotifyInfo notifyInfo = new NotifyInfo();
                        notifyInfo.setCustomerId(customerInfo.getCustomerId());
                        notifyInfo.setNotifyKey(txsAccountId);
                        notifyInfo.setNotifyCategory(1);//回调Txs 1
                        notifyInfo.setCreateTime(new Date());
                        logger.info("====开始插入回调txs信息完成===");
                        notifyMapper.insert(notifyInfo);
                        logger.info("====插入回调txs信息完成===");

                        registeredBackReq.setP2pAccountId(String.valueOf(customerInfo.getCustomerId()));
                        registeredBackReq.setMobile(record.getMobile());
                        registeredBackReq.setOperationTime(DateUtil.format(record.getRegisterTime(), "yyyy-MM-dd HH:mm:ss"));
                        registeredBackReq.setRequestTime(DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
                        registeredBackReq.setTxsAccountId(txsAccountId);
                        String backRequestStr = JsonUtil.printStrFromObj(registeredBackReq);
                        logger.info("============回调txs消息放入队列============= 内容：" + backRequestStr);

                        try {
                            MQProducer.send(backRequestStr, topic);
                            logger.info("============回调txs消息放入队列完成=============");
                        } catch (Exception e) {
                            logger.error("放入mq异常：参数backRequestStr：" + backRequestStr, e);
                        }

                        LoginCustomerDetail detail = loginService.login(content.getTxsPhoneNumber());//获取loginToken
                        logger.info("全新用户insert结束 insert setLogintoken：" + detail.getLoginToken());
                        responseResult.setLogintoken(detail.getLoginToken());
                        responseResult.setNextPage("1");
                        responseResult.setCustomerId(detail.getCustomerId());
                    } else {
                        MemberBasicInfo record1 = new MemberBasicInfo();
                        record1.setChannelCustomerId(txsAccountId);
                        MemberBasicInfo memberBasicInfo;
                        if (cinf_idCard != null) {//身份证存在
                            memberBasicInfo = cinf_idCard;
                        } else {//手机号存在
                            memberBasicInfo = cinf_mobile;
                        }
                        CustomerInfo customerInfo = customerInfoMapper.selectByMemberIdAndType(memberBasicInfo.getMemberId(), MEMBER_TYPE);
                        long customerId = customerInfo.getCustomerId();
                        String mobile = memberBasicInfo.getMobile();
                        String registerTime = DateUtil.format(memberBasicInfo.getRegisterTime(), "yyyy-MM-dd HH:mm:ss");
                        record1.setMemberId(memberBasicInfo.getMemberId());
                        record1.setIdCardNo(content.getIdentityNumber());
                        record1.setRealName(content.getName());
                        record1.setIsReal(1);
                        memberBasicInfoMapper.updateByPrimaryKeySelective(record1);

                        RegisteredBackReq registeredBackReq = new RegisteredBackReq();

                        //放入回调表
                        NotifyInfo notifyInfo = new NotifyInfo();
                        notifyInfo.setCustomerId(customerId);
                        notifyInfo.setNotifyKey(txsAccountId);
                        notifyInfo.setNotifyCategory(1);//回调Txs 1
                        notifyInfo.setCreateTime(new Date());
                        logger.info("====开始插入回调txs信息完成===");
                        notifyMapper.insert(notifyInfo);
                        logger.info("====插入回调txs信息完成===");
                        //放入回调表

                        registeredBackReq.setP2pAccountId(String.valueOf(customerId));
                        registeredBackReq.setMobile(mobile);
                        registeredBackReq.setOperationTime(registerTime);
                        registeredBackReq.setRequestTime(DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
                        registeredBackReq.setTxsAccountId(txsAccountId);
                        String backRequestStr = JsonUtil.printStrFromObj(registeredBackReq);
                        logger.info("============回调txs消息放入队列============= 内容：" + backRequestStr);

                        try {
                            MQProducer.send(backRequestStr, topic);
                            logger.info("============回调txs消息放入队列完成=============");
                        } catch (Exception e) {
                            logger.error("放入mq异常：参数backRequestStr：" + backRequestStr, e);
                        }
                        CustomerInfo localCustomerInfo = customerInfoMapper.selectByPrimaryKey(customerId);

                        LoginCustomerDetail detail = loginService.login(localCustomerInfo.getMobile());
                        logger.info("已注册未绑卡 更新  setLogintoken：" + detail.getLoginToken());
                        responseResult.setLogintoken(detail.getLoginToken());


                        if (localCustomerInfo != null && localCustomerInfo.getIsBindCard() == 1) {
                            responseResult.setNextPage("3"); //其他页
                        } else {
                            responseResult.setNextPage("1");  //绑卡
                        }
                        responseResult.setCustomerId(detail.getCustomerId());
                    }
                }
            }
        } catch (Exception e) {
            logger.error("authregister is error!", e);
            throw e;
        }

        logger.info("============authregister 授权注册完成==========");
        return responseResult;
    }
}
