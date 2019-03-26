/**
 *
 */
package com.zb.p2p.customer.service.impl;

import com.zb.p2p.customer.api.entity.*;
import com.zb.p2p.customer.common.enums.AppCodeEnum;
import com.zb.p2p.customer.common.enums.FlagEnum;
import com.zb.p2p.customer.common.enums.SourceIdEnum;
import com.zb.p2p.customer.common.exception.AppException;
import com.zb.p2p.customer.common.util.BusinessUtil;
import com.zb.p2p.customer.common.util.MD5Util;
import com.zb.p2p.customer.common.util.StringUtils;
import com.zb.p2p.customer.common.util.UuidUtils;
import com.zb.p2p.customer.dao.CustomerInfoMapper;
import com.zb.p2p.customer.dao.MemberBasicInfoMapper;
import com.zb.p2p.customer.dao.domain.CustomerInfo;
import com.zb.p2p.customer.dao.domain.MemberBasicInfo;
import com.zb.p2p.customer.service.CaptchaService;
import com.zb.p2p.customer.service.DeviceService;
import com.zb.p2p.customer.service.InfoService;
import com.zb.p2p.customer.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.lang.reflect.Member;
import java.util.UUID;

/**
 * 登录服务
 *
 * @author guolitao
 */
@Service
@Slf4j
public class LoginServiceImpl implements LoginService {

    //过期时间
    @Value("${env.loginTokenExpireTime}")
    private Long loginTokenExpireTime;
    //设置token不过期
    @Value("${env.noLiveTime}")
    private Long noLiveTime;
    public static final String PRE_CUST = "token:cust_";
    public static final String PRE_TOKEN = "token:loginToken_";
    public static final String PRE_ERR = "loginError:preErr_";//记录登录密码次数
    @Resource
    private CustomerInfoMapper customerInfoMapper;
    @Resource
    private MemberBasicInfoMapper memberBasicInfoMapper;
    @Resource
    private CaptchaService captchaService;
    @Resource
    private DeviceService deviceService;
    @Resource
    private RedisService redisService;

    @Override
    @Transactional
    public String register(CustomerReq req) {
        MemberBasicInfo cd = memberBasicInfoMapper.selectByUkMobile(req.getMobile());
        MemberBasicInfo info = new MemberBasicInfo();
        info.setMobile(req.getMobile());
        info.setLoginPwd(MD5Util.encrypt(req.getLoginPwd()));
        info.setMemberId(UuidUtils.getUUID());
        if (cd != null) {
            //更新会员密码
            memberBasicInfoMapper.updateByMobile(info);
        } else {
            //保存主会员账户
            memberBasicInfoMapper.insertSelective(info);
            CustomerInfo customerInfo = new CustomerInfo();
            customerInfo.setMemberId(info.getMemberId());
            customerInfo.setMemberType(req.getMemberType());
            //保存子会员账户
            customerInfoMapper.insertSelective(customerInfo);
        }
        return null;
    }

    @Override
    public LoginResp login(CustomerReq req) {
        String errKey = PRE_ERR + req.getMobile();
        String errFlag = FlagEnum.Y.getCode();
        try {
            MemberBasicInfo cd = memberBasicInfoMapper.selectByUkMobile(req.getMobile());
            if (cd != null) {
                String isTXSUser = FlagEnum.N.getCode();
                //判断是TXS用户首次登录
                if (StringUtils.isBlank(cd.getLoginPwd()) && SourceIdEnum.TXS.getCode().equals(cd.getChannelCode())
                        && !StringUtils.isBlank(cd.getChannelCustomerId())) {
                    isTXSUser = FlagEnum.Y.getCode();
                    MemberBasicInfo info = new MemberBasicInfo();
                    info.setMobile(req.getMobile());
                    info.setLoginPwd(MD5Util.encrypt(req.getLoginPwd()));
                    memberBasicInfoMapper.updateByMobile(info);
                }

                //MD5加密判断
                if (FlagEnum.N.getCode().equals(isTXSUser) && !cd.getLoginPwd().equals(MD5Util.encrypt(req.getLoginPwd()))) {
                    log.info("login判断密码,LoginPwd1:{},LoginPwd2:{}", cd.getLoginPwd(), MD5Util.encrypt(req.getLoginPwd()));
                    errFlag = FlagEnum.N.getCode();
                } else {
                    LoginResp lcd = new LoginResp();
                    lcd.setLoginToken(UuidUtils.getUUID());
                    redisService.set(PRE_CUST + lcd.getLoginToken(), cd.getMemberId(), noLiveTime);
                    //防止重复登陆
                    redisService.set(PRE_TOKEN + cd.getMemberId(), lcd.getLoginToken(), noLiveTime);
                    //登录成功，删除缓存
                    if (redisService.exists(errKey)) {
                        long del = this.redisService.del(errKey);
                        log.info("redis-del >> key:{}|ret:{}", errKey, del);
                    }

                    //记录登录设备信息
                    req.setCustomerId(cd.getMemberId());
                    deviceService.insertSelective(req);
                    lcd.setIsCodeKaptcha(FlagEnum.N.getCode());
                    lcd.setIsTXSUser(isTXSUser);
                    lcd.setFlag(FlagEnum.Y.getCode());
                    lcd.setDesc(AppCodeEnum._0000_SUCCESS.getMessage());
                    log.info("login-lcd,lcd:{}", lcd);
                    return lcd;
                }
            }

            if (FlagEnum.N.getCode().equals(errFlag)) {
                log.info("login请求参数,errFlag:{}", errFlag);

                //记录输入错误密码次数
                redisService.setnx(errKey, "0");//不存在则写入，初始值=0
                redisService.increment(errKey, 1L, loginTokenExpireTime);//每次自增+1，并返回自增后的值

                //如果密码错误>=3次，提示前端需要短信验证码
                if (Integer.parseInt(redisService.get(errKey)) >= 3) {
                    LoginResp lcd1 = new LoginResp();
                    lcd1.setIsCodeKaptcha(FlagEnum.Y.getCode());
                    lcd1.setFlag(FlagEnum.N.getCode());
                    lcd1.setDesc(AppCodeEnum._A010_LOGIN_ERR.getMessage());
                    return lcd1;
                }
                throw new AppException(AppCodeEnum._A010_LOGIN_ERR);
            }

        } catch (AppException e) {
            log.error("Error login:", e);
            throw e;
        }
        throw new AppException(AppCodeEnum._A001_USER_NOT_EXISTS);
    }

    @Override
    public LoginCustomerDetail login(String mobile) {
        MemberBasicInfo cd = memberBasicInfoMapper.selectByUkMobile(mobile);
        if (cd != null) {
            LoginCustomerDetail lcd = new LoginCustomerDetail();
            BeanUtils.copyProperties(cd, lcd);
            lcd.setLoginToken(UuidUtils.getUUID());
            //唐小僧导流默认为投资会员户
            CustomerInfo customerInfo = customerInfoMapper.selectByMemberIdAndType(cd.getMemberId(), "INVEST");
            //把customerId转义为摘要信息，不直接暴露给客户端
            lcd.setCustomerId(BusinessUtil.convertToOpenCustomerId(String.valueOf(customerInfo.getCustomerId())));
            redisService.set(PRE_CUST + lcd.getLoginToken(), String.valueOf(customerInfo.getCustomerId()), noLiveTime);
            redisService.set(PRE_TOKEN + String.valueOf(customerInfo.getCustomerId()), lcd.getLoginToken(), noLiveTime);
            return lcd;
        } else {
            throw new AppException(AppCodeEnum._A001_USER_NOT_EXISTS);
        }

    }

    @Override
    public void modifyLoginPwd(CustomerReq req) {
        MemberBasicInfo record = new MemberBasicInfo();
        //查询子账户
        CustomerInfo customerInfo = customerInfoMapper.selectByPrimaryKey(Long.parseLong(req.getCustomerId()));
        record.setMemberId(customerInfo.getMemberId());
        record.setLoginPwd(MD5Util.encrypt(req.getConfirmPwd()));
        //更新主账户
        memberBasicInfoMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public void resetLoginPwd(CustomerReq req) {
        MemberBasicInfo record = new MemberBasicInfo();
        record.setMobile(req.getMobile());
        record.setLoginPwd(MD5Util.encrypt(req.getNewPwd()));
        memberBasicInfoMapper.updateByMobile(record);
    }

    @Override
    public void logout(Long customerId, String loginToken) {
        CustomerInfo ci = customerInfoMapper.selectByPrimaryKey(customerId);
        if (ci != null) {
            MemberBasicInfo memberBasicInfo = memberBasicInfoMapper.selectByPrimaryKey(ci.getMemberId());
            String mobile = memberBasicInfo.getMobile();
            captchaService.clearCaptcha(mobile, String.valueOf(customerId), loginToken);
        }
    }

    @Override
    public String tokenToCustomerId(String loginToken, String memberType) {
        String key = PRE_CUST + loginToken;
        if (!redisService.exists(key)) {
            throw new AppException(AppCodeEnum._A006_USER_UNLOGIN);
        }
        String customerId = redisService.get(key);
        String lastToken = redisService.get(PRE_TOKEN + customerId);
        log.info("lastToken={},curLoginToken={}", lastToken, loginToken);
        //上一个token不等于当前token强制登出
        if (!lastToken.equals(loginToken)) {
            return null;
        }
        //根据登录时的token和会员类型查询子会员id
        CustomerInfo customerInfo = customerInfoMapper.selectByMemberIdAndType(redisService.get(key), memberType);
        return String.valueOf(customerInfo.getCustomerId());
    }

    @Override
    public String customerIdToToken(String customerId) {
        return redisService.get(PRE_TOKEN + customerId);
    }


}
