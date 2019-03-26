package com.zb.p2p.customer.web.controller;

import com.zb.p2p.customer.api.CardAPI;
import com.zb.p2p.customer.api.entity.card.*;
import com.zb.p2p.customer.common.enums.AppCodeEnum;
import com.zb.p2p.customer.common.model.BaseRes;
import com.zb.p2p.customer.common.util.CustomerConstants;
import com.zb.p2p.customer.dao.domain.CustomerBindcard;
import com.zb.p2p.customer.dao.domain.CustomerInfo;
import com.zb.p2p.customer.service.CardService;
import com.zb.p2p.customer.service.OtpService;
import com.zb.p2p.customer.service.bo.CheckSmsCaptchaBO;
import com.zb.p2p.customer.service.impl.RedisService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by laoguoliang
 */
@Api(value = "Card控制器", description = "银行卡")
@Controller
@RequestMapping("/card")
public class CardController implements CardAPI {
    private static final Logger logger = LoggerFactory.getLogger(CardController.class);

    @Autowired
    private CardService cardService;
    @Autowired
    private RedisService redisService;
    @Autowired
    private OtpService otpService;

    @ApiOperation(value = "查询卡bin", httpMethod = "POST")
    @ApiResponse(code = 200, message = "success", response = BaseRes.class)
    @ResponseBody
    @RequestMapping(value = "cardBin", method = RequestMethod.POST)
    public BaseRes<CardBin> cardBin(@RequestBody CardBinReq req, HttpServletRequest request) {
        BaseRes<CardBin> result = new BaseRes<>();
        try {
            //参数校验
            if (StringUtils.isEmpty(req.getCardNo())) {
                result.resetCode(AppCodeEnum._0001_ILLEGAL_PARAMETER);
                return result;
            }
            result = cardService.cardBin(req.getCardNo());
        } catch (Exception e) {
            logger.error("查询卡bin失败", e);
            result.resetCode(AppCodeEnum._A110_CARD_BIN_FAIL);
        }
        return result;
    }

    @ApiOperation(value = "查询支持的银行列表", httpMethod = "POST")
    @ApiResponse(code = 200, message = "success", response = BaseRes.class)
    @ResponseBody
    @RequestMapping(value = "cardList", method = RequestMethod.POST)
    public BaseRes<List<Card>> cardList(@RequestBody CardReq req, HttpServletRequest request) {
        BaseRes<List<Card>> result = new BaseRes<>();
        try {
            result = cardService.cardListByBankCode(req.getBankCode());
        } catch (Exception e) {
            logger.error("查询支持的银行列表失败", e);
            result.resetCode(AppCodeEnum._A111_CARD_LIST_FAIL);
        }
        return result;
    }

    @ApiOperation(value = "确认绑卡", httpMethod = "POST")
    @ApiResponse(code = 200, message = "success", response = BaseRes.class)
    @ResponseBody
    @RequestMapping(value = "confirmBindCard", method = RequestMethod.POST)
    public BaseRes<ConfirmBindCardRes> confirmBindCard(@RequestHeader(name = "customerId", required = false) String customerId,
                                                       @RequestBody ConfirmBindCardReq req) {
        logger.info("确认绑卡接口开始，参数：" + req.toString());
        logger.info("customerId:" + customerId);
        BaseRes<ConfirmBindCardRes> result = new BaseRes<ConfirmBindCardRes>();

        // 对用户进行加锁
        String customerLock = CustomerConstants.LOCK_BIND_CARD + customerId;
        boolean locked = redisService.setIfAbsent(customerLock, customerId, CustomerConstants.LOCK_CUSTOMER_TIMEOUT);
        if (!locked) {
            //已加锁
            result.resetCode(AppCodeEnum._A119_BIND_CARD_FAIL);
            logger.info("重复提交请求！" + result.toString());
            return result;
        }
        try {
            //customerId校验
            if (!StringUtils.isNumeric(customerId)) {
                result.resetCode(AppCodeEnum._0001_ILLEGAL_PARAMETER);
                logger.info("customerId格式错误！" + result.toString());
                return result;
            }
            //参数校验
            if (StringUtils.isBlank(req.getBankCode()) || StringUtils.isBlank(req.getBankName()) ||
                    StringUtils.isBlank(req.getCardNo()) || StringUtils.isBlank(req.getCardType()) ||
                    StringUtils.isBlank(req.getIdCardNo()) || StringUtils.isBlank(req.getIdCardType()) ||
                    StringUtils.isBlank(req.getMemberName()) || StringUtils.isBlank(req.getMobile())) {
                result.resetCode(AppCodeEnum._0001_ILLEGAL_PARAMETER);
                logger.info("请求参数为空！" + result.toString());
                return result;
            }
            if (!"D".equals(req.getCardType())) {
                result.resetCode(AppCodeEnum._0001_ILLEGAL_PARAMETER);
                result.setMessage("网贷出借只支持绑定借记卡");
                return result;
            }
            //短信验证码校验
            CheckSmsCaptchaBO bo = new CheckSmsCaptchaBO();
            bo.setMobile(req.getMobile());//银行预留手机号
            bo.setOtpBusiCode("04");//04-绑卡
            bo.setSmsCaptchaVal(req.getSmsCode());//短信验证码
            if (StringUtils.isBlank(req.getSmsCode()) || !otpService.checkSmsCaptchaForInner(bo)) {
                result.resetCode(AppCodeEnum._A005_SMS_CAPT_INVALID);
                logger.info("验证码不正确！" + result.toString());
                return result;
            }

            //根据卡号查询绑卡信息
            CustomerBindcard userBindCard = cardService.selectCustomerCardByCardNo(req.getCardNo());
            if (userBindCard == null) {//该卡号未绑定过，可正常绑卡
                //查询会员信息
                CustomerInfo custInfo = cardService.getCustomerInfo(customerId);
                if (custInfo != null) {

                    //未实名
                    if (1 != custInfo.getIsReal()) {
                        result.resetCode(AppCodeEnum._A124_BIND_CARD_NO_REALNAME);
                        return result;
                    } else if (1 == custInfo.getIsBindCard()) {
                        //已绑卡
                        result.resetCode(AppCodeEnum._A115_BIND_CARD_FAIL);
                        return result;
                    }

                    //直接绑卡
                    result = cardService.directBindingCard(req, customerId);
                } else {
                    result.resetCode(AppCodeEnum._0001_ILLEGAL_PARAMETER);
                    logger.info("根据customerId查询用户信息为空！" + result.toString());
                    return result;
                }
            } else {//该卡号已经被绑定过的场合
                result.resetCode(AppCodeEnum._A120_BIND_CARD_FAIL);
                logger.info(result.toString());
                return result;
            }
            //CODE转换
            if ("1M22".equals(result.getCode())) {//‘1M22：报文交易要素格式错误’转换
                result.resetCode(AppCodeEnum._A116_BIND_CARD_FAIL);
            } else if ("S010".equals(result.getCode())) {//S010：已在其他账号绑定过
                result.resetCode(AppCodeEnum._A120_BIND_CARD_FAIL);
            }
        } catch (Exception e) {
            logger.error("确认绑卡失败", e);
            result.resetCode(AppCodeEnum._A108_BIND_CARD_FAIL);
        } finally {
            if (redisService.exists(customerLock)) {//解锁
                redisService.del(customerLock);
            }
        }
        logger.info("确认绑卡接口结束，参数：" + result.toString());
        return result;
    }


    @ApiOperation(value = "解绑卡", httpMethod = "POST")
    @ApiResponse(code = 200, message = "success", response = BaseRes.class)
    @ResponseBody
    @RequestMapping(value = "unBind", method = RequestMethod.POST)
    public BaseRes<Object> unBind(@RequestHeader(name = "customerId", required = false) String customerId,
                                  @RequestBody UnBindReq req) {
        logger.info("解绑卡接口开始，参数：" + req.toString());
        logger.info("customerId:" + customerId);
        BaseRes<Object> result = new BaseRes<Object>();

        if (org.apache.commons.lang.StringUtils.isEmpty(customerId)) {
            customerId = req.getCustomerId();
        }

        try {
            //customerId校验
            if (!StringUtils.isNumeric(customerId)) {
                result.resetCode(AppCodeEnum._0001_ILLEGAL_PARAMETER);
                logger.info("customerId格式不正确！" + result.toString());
                return result;
            }
            //参数校验
            if (StringUtils.isEmpty(req.getBindId())) {
                result.resetCode(AppCodeEnum._0001_ILLEGAL_PARAMETER);
                logger.info("请求参数为空！" + result.toString());
                return result;
            }
            result = cardService.unBind(req.getBindId(), customerId, req);

        } catch (Exception e) {
            logger.error("失败", e);
            result.resetCode(AppCodeEnum._A104_UNBIND_FAIL);
        }
        logger.info("结束，" + result.toString());
        return result;
    }


    @ApiOperation(value = "查询绑卡辅助信息", httpMethod = "POST")
    @ApiResponse(code = 200, message = "success", response = BaseRes.class)
    @ResponseBody
    @RequestMapping(value = "bindCardHelp", method = RequestMethod.POST)
    public BaseRes<BindCardHelp> bindCardHelp(@RequestHeader(name = "customerId", required = false) String customerId) {
        logger.info("查询绑卡辅助信息接口开始!");
        logger.info("customerId:" + customerId);
        BaseRes<BindCardHelp> result = new BaseRes<BindCardHelp>();
        try {
            //customerId校验
            if (!StringUtils.isNumeric(customerId)) {
                result.resetCode(AppCodeEnum._0001_ILLEGAL_PARAMETER);
                logger.info("customerId格式不正确！" + result.toString());
                return result;
            }
            BindCardHelp help = cardService.bindCardHelp(Long.valueOf(customerId));
            result.setData(help);
        } catch (Exception e) {
            logger.error("查询绑卡辅助信息失败", e);
            result.resetCode(AppCodeEnum._9999_ERROR);
        }
        return result;
    }

}
