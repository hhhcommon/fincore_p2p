package com.zb.p2p.customer.service.impl;

import com.zb.p2p.customer.api.entity.CustomerDetail;
import com.zb.p2p.customer.client.message.api.ValicodeMessageReq;
import com.zb.p2p.customer.common.enums.AppCodeEnum;
import com.zb.p2p.customer.common.enums.MobileTypeEnum;
import com.zb.p2p.customer.common.exception.AppException;
import com.zb.p2p.customer.common.util.*;
import com.zb.p2p.customer.service.*;
import com.zb.p2p.customer.service.bo.*;
import com.zb.p2p.customer.service.config.OtpConfig;
import com.zb.p2p.customer.service.config.OtpConfig.CaptchaConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

//import com.zb.p2p.customer.service.MessageCenterService;

@Slf4j
@Service
public class OtpServiceImpl implements OtpService {
    @Autowired
    private OtpConfig otpConfig;
    @Autowired
    private CacheService cacheService;
    @Autowired
    private InfoService infoService;
   /* @Autowired
    private MessageCenterService messageCenterService;*/
    @Value("${env.isFixImgCap}")
    private Integer isFixImgCap;
    @Value("${env.isFixSmsCap}")
    private Integer isFixSmsCap;
    
    @Autowired
    private MessageService messageService;

    public final static String FIX_IMG_CAP_VAL = "1234";
    public final static String FIX_SMS_CAP_VAL = "123456";


    @Override
    public ImgCaptchaBO genImgCaptcha(GenImgCaptchaBO genImgCaptchaBO) {
        ImgCaptchaBO imgCaptchaBO = new ImgCaptchaBO();
        // 确定手机号
        final String mobile = this.findMobile(genImgCaptchaBO.getOtpBusiCode(), genImgCaptchaBO.getMobile());
        final String otpBusiCode = genImgCaptchaBO.getOtpBusiCode();
        // 免图口令or图片验证码
        boolean freeImgCaptchaModel = this.isFreeImgCaptchaModel(otpBusiCode, mobile);
        if (freeImgCaptchaModel) {
            this.genOtpFreeImgToken(imgCaptchaBO, mobile, otpBusiCode);
        } else {
            this.genOtpImgCaptcha(imgCaptchaBO, mobile, otpBusiCode);
        }
        return imgCaptchaBO;
    }

    /**
     * 是否为免图模式
     *
     * @param genImgCaptchaBO
     * @return
     */
    private boolean isFreeImgCaptchaModel(String otpBusiCode, String mobile) {
        boolean result = false;
        int findSmsErrorCount = this.cacheService.findSmsErrorCount(otpBusiCode, mobile);

        if (findSmsErrorCount < 3) {
            result = true;
        }
        return result;
    }

    private void genOtpFreeImgToken(ImgCaptchaBO imgCaptchaBO, String mobile, String otpBusiCode) {
        String freeImgToken = UuidUtils.getUUID();
        boolean result = this.cacheService.saveFreeImgToken(otpBusiCode, mobile, freeImgToken, this.otpConfig.getFreeImgToken().getTimeoutSecond());
        log.info("save freeImgToken is {}", result);
        if (!result) {
            throw AppException.getInstance(AppCodeEnum._9999_ERROR);
        }
        imgCaptchaBO.setFreeImgToken(freeImgToken);
    }

    private void genOtpImgCaptcha(ImgCaptchaBO imgCaptchaBO, String mobile, String otpBusiCode) {
        String val = null;
        if (this.isFixImgCap != null && this.isFixImgCap == 1) {
            val = FIX_IMG_CAP_VAL;
        } else {
            val = RandomUtil.createRadom(this.otpConfig.getImgCaptcha().getLength(), this.otpConfig.getImgCaptcha().getType());

        }
        String code = UuidUtils.getUUID();
        imgCaptchaBO.setImgCaptchaCode(code);
        imgCaptchaBO.setImgCaptchaUrl(this.otpConfig.getImgCaptcha().getPreUrl() + "/" + code);

        boolean result = this.cacheService.saveImgCaptcha(otpBusiCode, mobile, code, val, this.otpConfig.getImgCaptcha().getTimeoutSecond());
        log.info("save imgCaptcha is {}", result);

        if (!result) {
            throw AppException.getInstance(AppCodeEnum._9999_ERROR);
        }
    }

    /**
     * 根据业务码确定手机号
     *
     * @param genImgCaptchaBO
     * @return
     */
    private String findMobile(String otpBusiCode, String inputMobile) {
        String mobile = null;

        String mobileType = this.otpConfig.getSmsMessageMap().get(otpBusiCode).getMobileType();
        MobileTypeEnum mobileTypeEnum = MobileTypeEnum.getByCode(mobileType);
        //手机号： 注册手机号、银行卡预留手机号、用户录入手机号
        switch (mobileTypeEnum) {
            case _01_REGISTE:
                mobile = this.findRegisteMobile();
                break;
            case _02_BINDCARD:
                mobile = this.findBindCardMobile();
                break;
            case _90_CUSTOMER_INPUT:
                mobile = inputMobile;
                break;
        }

        return mobile;
    }

    /**
     * 查询注册手机号
     *
     * @return
     */
    private String findRegisteMobile() {
        CustomerDetail customerDetail = this.infoService.getPerDetail(AppContext.getInstance().getCustomerId());
        String mobile = customerDetail.getMobile();
        return mobile;
    }

    /**
     * 查询绑卡手机号
     *
     * @return
     */
    private String findBindCardMobile() {
        Long customerId = AppContext.getInstance().getCustomerId();
        CustomerDetail perDetail = this.infoService.getPerDetail(customerId);
        String bankMobile = perDetail.getBankCard().getBankMobile();
        return bankMobile;
    }

    @Override
    public void sendSmsCaptcha(final SendSmsCaptchaBO sendSmsCaptchaBO) {

        String otpBusiCode = sendSmsCaptchaBO.getOtpBusiCode();
//        String mobile = this.findMobile(otpBusiCode, sendSmsCaptchaBO.getMobile());
        String mobile = sendSmsCaptchaBO.getMobile();
        sendSmsCaptchaBO.setMobile(mobile);
        String keyForOtpCheckImgCaptchaLock = CacheKeyUtil.keyForOtpCheckImgCaptchaLock(otpBusiCode, mobile);

        this.cacheService.lock(keyForOtpCheckImgCaptchaLock, this, new LockService<OtpServiceImpl>() {

            @Override
            public void execute(OtpServiceImpl t) {
                t.execSendSmsCaptcha(sendSmsCaptchaBO);

            }
        });
    }

    /**
     * 发送短信
     *
     * @param sendSmsCaptchaBO
     */
    private void execSendSmsCaptcha(SendSmsCaptchaBO sendSmsCaptchaBO) {
        String otpBusiCode = sendSmsCaptchaBO.getOtpBusiCode();
        String mobile = sendSmsCaptchaBO.getMobile();

        // 校验
        boolean hasSmsCaptchaLock = this.cacheService.hasSmsCaptchaLock(otpBusiCode, mobile);
        if (hasSmsCaptchaLock) {
            throw AppException.getInstance(AppCodeEnum._A402_SEND_SMS_DIFF_ILLEGAL);
        }

        //图片验证码
//        boolean checkResult = this.checkForSendSmsCaptcha(sendSmsCaptchaBO);
//        if (!checkResult) {
//            throw AppException.getInstance(AppCodeEnum._A401_ILLEGAL_CAPTCHA);
//        }

        // 发送短信
        CaptchaConfig smsCaptcha = this.otpConfig.getSmsCaptcha();

        /*boolean sendSameSms = false;//是否发送相同的短信验证码
        SmsCaptchaBO dbSmsCaptcha = this.cacheService.findSmsCaptcha(otpBusiCode, mobile);
        if (dbSmsCaptcha != null) {
            long diff = dbSmsCaptcha.getCreateTime() - DateUtil.getTimestamp();
            if (this.otpConfig.getSendSameSmsInSecond() * 1000 < diff) {
                sendSameSms = true;
            }
        }*/
        
       /* // 生成短信验证码
        String smsCaptchaVal = null;
        if (this.isFixSmsCap != null && this.isFixSmsCap == 1) {
            smsCaptchaVal = FIX_SMS_CAP_VAL;
        } else {
            smsCaptchaVal = sendSameSms ? dbSmsCaptcha.getSmsCaptchaVal() : RandomUtil.createRadom(smsCaptcha.getLength(), smsCaptcha.getType());
        }*/

        
        
        /*
         * // 调用message-center 接口
        String content = this.otpConfig.getSmsMessageMap().get(otpBusiCode).getMessage().replaceAll(OtpConfig.SMS_PARAM_SMS_CAPTCHA_VAL, smsCaptchaVal);
        log.info("s3SendSms >> {}-{}", mobile, content);
        SmsReq smsReq = new SmsReq();
        smsReq.setBizCode("OTP_" + otpBusiCode);
        smsReq.setContent(content);
        smsReq.setMsgMode("206");
        smsReq.setPhone(mobile);
        //smsReq.
        BaseRes<MessageCenterResp> sendSms = this.messageCenterService.sendSms(smsReq);
        if (!StringUtils.equals(sendSms.getCode(), AppCodeEnum._0000_SUCCESS.getCode())) {
            log.error("call meesage-center send is failed.code:{},msg:{}", sendSms.getCode(), sendSms.getMessage());
            throw AppException.getInstance(AppCodeEnum._9999_ERROR);
        }*/
        
        String businessType = "";
          
        if("04".equals(otpBusiCode)){
        	businessType = "finance_bindcard";
        }else if("05".equals(otpBusiCode)){
        	businessType = "finance_unbind";
        }else{
        	 throw AppException.getInstance(AppCodeEnum._0001_ILLEGAL_PARAMETER);
        }
        
        ValicodeMessageReq validateMessageReq = new ValicodeMessageReq() ;
        validateMessageReq.setBusinessType(businessType);
        validateMessageReq.setMobile(mobile);
        
        String smsCaptchaVal = "";
        if(isFixSmsCap != null && isFixSmsCap == 1 ){
        	smsCaptchaVal = "123456";
        }else {
        	smsCaptchaVal = messageService.sendValidateCode(validateMessageReq);
        }
          
        SmsCaptchaBO sc = new SmsCaptchaBO();
        sc.setCreateTime(DateUtil.getTimestamp());
        sc.setSmsCaptchaVal(smsCaptchaVal);
        boolean result = this.cacheService.saveSmsCaptcha(otpBusiCode, mobile, sc, smsCaptcha.getTimeoutSecond());
        log.info("save smsCaptcha is {}", result);
        if (!result) {
            throw AppException.getInstance(AppCodeEnum._9999_ERROR);
        }
        this.cacheService.saveSmsErrorCount(otpBusiCode, mobile, DateUtil.getSecondsLeftNow());
        
        // 加60秒锁
        this.cacheService.saveSmsCaptchaLock(otpBusiCode, mobile, this.otpConfig.getMinSendSmsDiffSecond());
    }

    /**
     * 发短信前置校验
     *
     * @param sendSmsCaptchaBO
     * @return
     */
    private boolean checkForSendSmsCaptcha(SendSmsCaptchaBO sendSmsCaptchaBO) {
        boolean result = false;

        String freeImgToken = sendSmsCaptchaBO.getFreeImgToken();
        String otpBusiCode = sendSmsCaptchaBO.getOtpBusiCode();
        String mobile = this.findMobile(otpBusiCode, sendSmsCaptchaBO.getMobile());
        String imgCaptchaCode = sendSmsCaptchaBO.getImgCaptchaCode();

        if (StringUtils.isNotEmpty(freeImgToken)) {
            // 校验免图口令
            String dbFreeImgToken = this.cacheService.findFreeImgToken(otpBusiCode, mobile);
            if (StringUtils.equals(freeImgToken, dbFreeImgToken)) {
                boolean delFreeImgToken = this.cacheService.delFreeImgToken(otpBusiCode, mobile);
                result = delFreeImgToken;

            }
        } else {
            // 校验图片验证码
            String dbImgCaptchaVal = this.cacheService.findImgCaptcha(otpBusiCode, mobile, imgCaptchaCode);
            if (StringUtils.equals(sendSmsCaptchaBO.getImgCaptchaVal(), dbImgCaptchaVal)) {
                boolean delImgCaptcha = this.cacheService.delImgCaptcha(otpBusiCode, mobile, imgCaptchaCode);
                result = delImgCaptcha;
            }
        }

        return result;
    }

    @Override
    public void checkSmsCaptcha(CheckSmsCaptchaBO checkSmsCaptchaBO) {
        String otpBusiCode = checkSmsCaptchaBO.getOtpBusiCode();
//        String mobile = this.findMobile(otpBusiCode, checkSmsCaptchaBO.getMobile());
        String mobile = checkSmsCaptchaBO.getMobile();
        checkSmsCaptchaBO.setMobile(mobile);
        String keyForOtpCheckSmsCaptchaLock = CacheKeyUtil.keyForOtpCheckSmsCaptchaLock(otpBusiCode, mobile);
        this.cacheService.lock(keyForOtpCheckSmsCaptchaLock, this, new LockService<OtpServiceImpl>() {

            @Override
            public void execute(OtpServiceImpl t) {
                t.execCheckSmsCaptcha(checkSmsCaptchaBO);
            }
        });
    }

    /**
     * 校验短信验证码
     *
     * @param checkSmsCaptchaBO
     */
    private void execCheckSmsCaptcha(CheckSmsCaptchaBO checkSmsCaptchaBO) {
        boolean result = false;
        String otpBusiCode = checkSmsCaptchaBO.getOtpBusiCode();
//        String mobile = this.findMobile(otpBusiCode, checkSmsCaptchaBO.getMobile());
        String mobile = checkSmsCaptchaBO.getMobile();
        String smsCaptchaVal = checkSmsCaptchaBO.getSmsCaptchaVal();
        // 校验短信验证码
        SmsCaptchaBO smsCaptchaBO = this.cacheService.findSmsCaptcha(otpBusiCode, mobile);

        if (smsCaptchaBO == null) {
            log.info("checkSmsCaptcha >> not found smsCaptchaVal in redis! otpBusiCode:{} mobile:{},result:{}",otpBusiCode, mobile, result);
            throw AppException.getInstance(AppCodeEnum._A403_SMS_CAPTCHA_NOT_EXITS);
        }
        String dbSmsCaptchaVal = smsCaptchaBO.getSmsCaptchaVal();
        log.info("checkSmsCaptcha >>mobile:{}, input:{},db:{}", mobile, smsCaptchaVal, dbSmsCaptchaVal);
        if (StringUtils.equals(smsCaptchaVal, dbSmsCaptchaVal)) {

            this.cacheService.delSmsCaptcha(otpBusiCode, mobile);
            this.cacheService.delSmsErrorCount(otpBusiCode, mobile);
            this.cacheService.delSmsCaptchaLock(otpBusiCode, mobile);
            result = true;
        }
        log.info("checkSmsCaptcha >>mobile:{},result:{}", mobile, result);
        if (!result) {
            throw AppException.getInstance(AppCodeEnum._A401_ILLEGAL_CAPTCHA);
        }

    }

    @Override
    public boolean checkSmsCaptchaForInner(CheckSmsCaptchaBO checkSmsCaptchaBO) {
        boolean result = false;
        try {
            this.checkSmsCaptcha(checkSmsCaptchaBO);
            result = true;
        } catch (Exception e) {
            log.error("checkSmsCaptcha >> error!", e);
            result = false;
        }
        return result;

    }

    @Override
    public String findImgCaptcha(String imgCaptchaCode) {
        String dbImgCaptchaVal = this.cacheService.findImgCaptcha(imgCaptchaCode);

        return dbImgCaptchaVal;
    }


}
