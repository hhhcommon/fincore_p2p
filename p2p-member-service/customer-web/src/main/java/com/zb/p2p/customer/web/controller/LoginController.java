/**
 *
 */
package com.zb.p2p.customer.web.controller;

import com.google.code.kaptcha.Producer;
import com.zb.p2p.customer.api.entity.*;
import com.zb.p2p.customer.api.entity.PerCustCond;
import com.zb.p2p.customer.client.message.api.ValicodeMessageReq;
import com.zb.p2p.customer.common.enums.AppCodeEnum;
import com.zb.p2p.customer.common.enums.FlagEnum;
import com.zb.p2p.customer.common.enums.SendSMSEnum;
import com.zb.p2p.customer.common.enums.SourceIdEnum;
import com.zb.p2p.customer.common.exception.AppException;
import com.zb.p2p.customer.common.model.BaseRes;
import com.zb.p2p.customer.common.util.MD5Util;
import com.zb.p2p.customer.common.util.StringUtils;
import com.zb.p2p.customer.common.util.UuidUtils;
import com.zb.p2p.customer.dao.domain.MemberBasicInfo;
import com.zb.p2p.customer.dao.domain.OrgCustomerInfo;
import com.zb.p2p.customer.dao.query.req.*;
import com.zb.p2p.customer.service.*;
import com.zb.p2p.customer.service.config.ReadOnlyConnection;
import com.zb.p2p.customer.service.impl.LoginServiceImpl;
import com.zb.p2p.customer.service.impl.RedisService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sun.rmi.runtime.Log;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static com.zb.p2p.customer.service.CaptchaService.*;

/**
 * 会员登录相关
 *
 * @author guolitao
 */
@Api(value = "会员登录控制器", description = "会员登录")
@Controller
@RequestMapping("/login")
@Slf4j
public class LoginController {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    private static final String IMG_VALID_FLAG = "1";//图片验证码校验通过标记
    private static final String SMS_TYPE_LOGIN = "1";//图片验证码校验通过标记
    private static final String SMS_TYPE_REGISTER = "2";//图片验证码校验通过标记
    @Value("${env.imgCaptchaExpireTime}")
    private Long imgCaptchaExpireTime;
    @Value("${env.imgCaptchaResultExpireTime}")
    private Long imgCaptchaResultExpireTime;
    @Value("${env.smsCaptchaExpireTime}")
    private Long smsCaptchaExpireTime;
    @Value("${env.smsSendExpireTime}")
    private Long smsSendExpireTime;
    @Value("${env.isSmsCaptcha}")
    private Integer isSmsCaptcha;
    @Resource
    private RedisService redisService;
    @Resource
    private CaptchaService captchaService;
    @Resource(name = "imgCaptchaProducer")
    private Producer imgCaptchaProducer;
    @Resource
    private LoginService loginService;
    @Resource
    private InfoService infoService;
    @Resource
    private SmsService smsService;
    @Autowired
    private MessageService messageService;
    @Autowired
    private ParamsDetector paramsDetector;


    @ReadOnlyConnection
    @ApiOperation(value = "手机号是否已注册", httpMethod = "POST", produces = "application/json")
    @ApiResponse(code = 200, message = "success", response = BaseRes.class)
    @ResponseBody
    @RequestMapping(value = "/isRegister", method = RequestMethod.POST, produces = "application/json")
    public BaseRes<Flag> isRegister(@RequestBody CustomerReq req) {
        log.info("isRegister请求参数,req:{}", req);

        BaseRes<Flag> result = new BaseRes<Flag>(true);
        String mobile = req.getMobile();
        if (StringUtils.isBlank(mobile) || !smsService.isValidMobile(mobile)) {
            result.setMessage("手机号缺失或格式错误");
            result.setCode(AppCodeEnum._0001_ILLEGAL_PARAMETER.getCode());
        } else {
            com.zb.p2p.customer.dao.query.req.PerCustCond perCustCond = new com.zb.p2p.customer.dao.query.req.PerCustCond();
            perCustCond.setMobile(mobile);
            MemberBasicInfo pcd = infoService.perList(perCustCond);
            Flag flag = null;
            //获取是否TXS用户标识
            String isTXSUser = FlagEnum.N.getCode();
            if (pcd != null) {
                if (SourceIdEnum.TXS.getCode().equals(pcd.getChannelCode()) && !StringUtils.isBlank(pcd.getChannelCustomerId())) {
                    isTXSUser = FlagEnum.Y.getCode();
                }
                flag = new Flag(FlagEnum.Y.getCode(), "已注册", isTXSUser);
            } else {
                flag = new Flag(FlagEnum.N.getCode(), "未注册", isTXSUser);
            }
            result.setData(flag);
        }
        log.info("isRegister返回结果,result:{}", result);
        return result;
    }

    @ApiOperation(value = "图形验证码", httpMethod = "POST", produces = "application/json")
    @ApiResponse(code = 200, message = "success")
    @ResponseBody
    @RequestMapping(value = "/imgCaptcha", method = RequestMethod.POST, produces = "application/json")
    public BaseRes<ImgCaptchaResp> getKaptchaImage(@RequestBody CustomerReq req, HttpServletRequest request) throws IOException {
        log.info("getKaptchaImage请求参数,req:{}", req);
        BaseRes<ImgCaptchaResp> resultResp = new BaseRes<ImgCaptchaResp>(true);

        String clientId = req.getClientId();
        if (StringUtils.isBlank(clientId)) {
            resultResp.setCode(AppCodeEnum._0001_ILLEGAL_PARAMETER.getCode());
            resultResp.setMessage("缺少客户端标识");
            return resultResp;
        }
        try {
            String capText = captchaService.generateImg(CaptchaService.FUNC_SESSION, clientId, imgCaptchaExpireTime);
            logger.info("clientId【" + clientId + "】获得图形验证码为" + capText);
            //清空校验结果缓存
            String redisKey = CaptchaService.FUNC_SESSION_RESULT + "_" + clientId;
            if (redisService.exists(redisKey)) {
                //删除key
                redisService.del(redisKey);
            }
            BufferedImage bi = imgCaptchaProducer.createImage(capText);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            boolean flag = ImageIO.write(bi, "jpg", out);
            byte[] bytes = out.toByteArray();
            ImgCaptchaResp msg = new ImgCaptchaResp(bytes);
            resultResp.setData(msg);
        } catch (Exception e) {
            logger.error("getKaptchaImage图形验证码未成功", e);
            resultResp.setCode(AppCodeEnum._9999_ERROR.getCode());
            resultResp.setMessage(AppCodeEnum._9999_ERROR.getMessage());
        }
        log.info("getKaptchaImage返回结果,resultResp:{}", resultResp);

        return resultResp;
    }

    @ApiOperation(value = "图形验证码校验", httpMethod = "POST", produces = "application/json")
    @ApiResponse(code = 200, message = "success", response = BaseRes.class)
    @ResponseBody
    @RequestMapping(value = "/validImgKaptcha", method = RequestMethod.POST, produces = "application/json")
    public BaseRes<Flag> validImgKaptcha(@RequestBody CustomerReq req, HttpServletRequest request) {
        log.info("validImgKaptcha请求参数,req:{}", req);

        BaseRes<Flag> result = new BaseRes<Flag>(true);
        String codeKaptcha = req.getCodeKaptcha();
        String clientId = req.getClientId();
        if (StringUtils.isBlank(clientId) || StringUtils.isBlank(codeKaptcha)) {
            result.setCode(AppCodeEnum._0001_ILLEGAL_PARAMETER.getCode());
            result.setMessage("缺少客户端标识或验证码");
            return result;
        }
        boolean isValid = captchaService.validate(CaptchaService.FUNC_SESSION, clientId, codeKaptcha);
        String redisKey = CaptchaService.FUNC_SESSION_RESULT + "_" + clientId;
        if (isValid) {
            result.setData(new Flag(FlagEnum.Y.getCode(), "校验成功", FlagEnum.N.getCode()));
            //校验通过就增加校验通过的标记，以便后续后台校验
            redisService.set(redisKey, IMG_VALID_FLAG, imgCaptchaResultExpireTime);
        } else {
            result.setCode(AppCodeEnum._A003_IMG_CAPT_INVALID.getCode());
            result.setMessage(AppCodeEnum._A003_IMG_CAPT_INVALID.getMessage());
            if (redisService.exists(redisKey)) {
                //删除key
                redisService.del(redisKey);
            }
        }
        log.info("validImgKaptcha返回结果,result:{}", result);

        return result;
    }

    @ApiOperation(value = "短信验证码(登录或注册)", httpMethod = "POST", produces = "application/json")
    @ApiResponse(code = 200, message = "success", response = BaseRes.class)
    @ResponseBody
    @RequestMapping(value = "/smsCaptcha", method = RequestMethod.POST, produces = "application/json")
    public BaseRes<KaptchaSMSMsg> getKaptchaSMSMsg(@RequestBody CustomerReq req, HttpServletRequest request) {
        log.info("getKaptchaSMSMsg请求参数,req:{}", req);

        BaseRes<KaptchaSMSMsg> result = new BaseRes<KaptchaSMSMsg>(true);
        String mobile = req.getMobile();
        String type = req.getType();
        String clientId = req.getClientId();

        BaseRes<KaptchaSMSMsg> resultPara = paramsDetector.smsCaptchaDetector(req);
        if (resultPara != null) {
            return resultPara;
        } else {
            String funcCode = FUNC_LOGIN;
            String funcName = "登录";
            String funcSmsType = FUNC_SMS_SEND_LOGIN;
            String businessType = SendSMSEnum.FINANCE_LOGIN.getCode();
            if (type.equals(FUNC_REGISTER)) {
                //校验图形验证码结果
                if (!checkImgCaptValid(clientId)) {
                    result.setCode(AppCodeEnum._A003_IMG_CAPT_INVALID.getCode());
                    result.setMessage(AppCodeEnum._A003_IMG_CAPT_INVALID.getMessage());
                    return result;
                }
                funcCode = FUNC_REGISTER;
                funcName = "注册";
                funcSmsType = FUNC_SMS_SEND_REGISTER;
                businessType = SendSMSEnum.FINANCE_REGISTE.getCode();
            } else if (type.equals(FUNC_RESET)) {
                funcCode = FUNC_RESET;
                funcName = "重置登录密码";
                funcSmsType = FUNC_SMS_SEND_RESET;
                businessType = SendSMSEnum.FINANCE_BACK.getCode();
            }
            try {
                if (!captchaService.isAllowSmsSend(funcSmsType, clientId, smsSendExpireTime)) {
                    result.setCode(AppCodeEnum._A007_SMS_CAPT_SEND_ERROR.getCode());
                    result.setMessage(AppCodeEnum._A007_SMS_CAPT_SEND_ERROR.getMessage());
                    return result;
                }
                //发送短信验证码
                ValicodeMessageReq validateMessageReq = new ValicodeMessageReq();
                validateMessageReq.setBusinessType(businessType);
                validateMessageReq.setMobile(mobile);

                String smsCaptchaVal = null;
                if (isSmsCaptcha != 1) {
                    smsCaptchaVal = messageService.sendValidateCode(validateMessageReq);
                } else {
                    smsCaptchaVal = "888888";
                }
                log.info("smsCaptchaVal:{},mobile:{}", smsCaptchaVal, mobile);
                //保存短信验证码，下次验证
                boolean saveResult = captchaService.saveCaptchaVal(funcCode + "_" + mobile, smsCaptchaVal, smsCaptchaExpireTime);
                if (!saveResult) {
                    throw AppException.getInstance(AppCodeEnum._9999_ERROR);
                }

//				KaptchaSMSMsg msg = new KaptchaSMSMsg(mobile);
//				result.setData(msg);
            } catch (Exception e) {
                logger.error("smsCaptcha发送短信不成功", e);
                result.setCode(AppCodeEnum._A004_SMS_CAPT_SEND_ERROR.getCode());
                result.setMessage(AppCodeEnum._A004_SMS_CAPT_SEND_ERROR.getMessage());
            }
        }
        log.info("getKaptchaSMSMsg返回结果:{}", result);

        return result;
    }

    /**
     * 校验图形验证码通过标志
     *
     * @param clientId
     * @return
     */
    private boolean checkImgCaptValid(String clientId) {
        //校验图形验证码结果
        String redisKey = CaptchaService.FUNC_SESSION_RESULT + "_" + clientId;
        String imgVaild = redisService.get(redisKey);
        if (!IMG_VALID_FLAG.equals(imgVaild)) {
            return false;
        }
        return true;
    }

    @ApiOperation(value = "注册", httpMethod = "POST", produces = "application/json")
    @ApiResponse(code = 200, message = "success", response = BaseRes.class)
    @ResponseBody
    @RequestMapping(value = "/register", method = RequestMethod.POST, produces = "application/json")
    public BaseRes<LoginCustomerDetail> register(@RequestBody CustomerReq req) {
        log.info("register请求参数,req:{}", req);

        BaseRes<LoginCustomerDetail> result = new BaseRes<LoginCustomerDetail>(true);
        String funcCode = FUNC_REGISTER;
        String mobile = req.getMobile();
        String codeKaptcha = req.getCodeKaptcha();
        String clientId = req.getClientId();
        try {

            BaseRes<LoginCustomerDetail> resultPara = paramsDetector.registerDetector(req);
            if (resultPara != null) {
                return resultPara;
            }

            //校验图形验证码结果
            if (!checkImgCaptValid(clientId)) {
                result.setCode(AppCodeEnum._A003_IMG_CAPT_INVALID.getCode());
                result.setMessage(AppCodeEnum._A003_IMG_CAPT_INVALID.getMessage());
                return result;
            }
            //校验短信验证码
            boolean isValid = captchaService.validate(funcCode, mobile, codeKaptcha);
            if (isValid) {
                loginService.register(req);
//    			LoginCustomerDetail lcd = loginService.login(mobile);
//    			result.setData(lcd);
            } else {
                result.setCode(AppCodeEnum._A005_SMS_CAPT_INVALID.getCode());
                result.setMessage(AppCodeEnum._A005_SMS_CAPT_INVALID.getMessage());
            }
        } catch (AppException e) {
            result.setCode(e.getCode());
            result.setMessage(e.getMessage());
            return result;
        }
        log.info("register返回结果,result:{}", result);
        return result;
    }

    @ReadOnlyConnection
    @ApiOperation(value = "登录", httpMethod = "POST", produces = "application/json")
    @ApiResponse(code = 200, message = "success", response = BaseRes.class)
    @ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = "application/json")
    public BaseRes<LoginResp> login(@RequestBody CustomerReq req) {
        log.info("login请求参数,req:{}", req);

        BaseRes<LoginResp> result = new BaseRes<>(true);
        LoginResp resp = new LoginResp();
        resp.setIsCodeKaptcha(FlagEnum.N.getCode());
        resp.setFlag(FlagEnum.N.getCode());
        resp.setIsTXSUser(FlagEnum.N.getCode());

        String funcCode = FUNC_LOGIN;
        //校验参数
        String mobile = req.getMobile();
        String codeKaptcha = req.getCodeKaptcha();
        BaseRes<LoginResp> resultPara = paramsDetector.loginDetector(req);
        if (resultPara != null) {
            return resultPara;
        }
        try {
            boolean isValid = true;
            String errKey = LoginServiceImpl.PRE_ERR + mobile;
            if (redisService.exists(errKey)) {
                int errValue = Integer.parseInt(redisService.get(errKey));
                //密码输入错误次数达到3次需输入验证码
                if (errValue >= 3) {
                    if (StringUtils.isBlank(codeKaptcha) || StringUtils.isBlank(req.getClientId())) {
                        result.setCode(AppCodeEnum._0000_SUCCESS.getCode());
                        result.setMessage(AppCodeEnum._A011_CAPT_PARA_ERR.getMessage());
                        resp.setIsCodeKaptcha(FlagEnum.Y.getCode());
                        resp.setDesc(AppCodeEnum._A011_CAPT_PARA_ERR.getMessage());
                        result.setData(resp);
                        log.info("login密码错误返回结果,result:{}", result);
                        return result;
                    } else {
                        //校验短信验证码
                        isValid = captchaService.validate(funcCode, mobile, codeKaptcha);
                    }
                }
            }
            if (isValid) {
                //登录操作
                LoginResp loginResp = loginService.login(req);
                if (FlagEnum.Y.getCode().equals(loginResp.getIsCodeKaptcha())) {
                    result.setCode(AppCodeEnum._0000_SUCCESS.getCode());
                    result.setMessage(AppCodeEnum._A010_LOGIN_ERR.getMessage());
                }
                result.setData(loginResp);
            } else {
                result.setCode(AppCodeEnum._0000_SUCCESS.getCode());
                result.setMessage(AppCodeEnum._A005_SMS_CAPT_INVALID.getMessage());
                resp.setIsCodeKaptcha(FlagEnum.Y.getCode());
                resp.setDesc(AppCodeEnum._A005_SMS_CAPT_INVALID.getMessage());
                result.setData(resp);
            }
        } catch (AppException e) {
            result.setCode(AppCodeEnum._0000_SUCCESS.getCode());
            result.setMessage(e.getMessage());
            resp.setDesc(e.getMessage());
            result.setData(resp);
        }
        log.info("login返回结果,result:{}", result);

        return result;

    }

    @ApiOperation(value = "修改登录密码", httpMethod = "POST", produces = "application/json")
    @ApiResponse(code = 200, message = "success", response = BaseRes.class)
    @ResponseBody
    @RequestMapping(value = "/modifyLoginPwd", method = RequestMethod.POST, produces = "application/json")
    public BaseRes<Object> modifyLoginPwd(@RequestHeader(name = "customerId", required = false) String cId, @RequestBody CustomerReq req) {
        log.info("modifyLoginPwd请求参数,cId:{};req{}", cId, req);

        BaseRes<Object> result = new BaseRes<Object>(true);
        String custId = cId;
        BaseRes<Object> resultPara = paramsDetector.modifyLoginPwdDetector(req, custId);
        if (resultPara != null) {
            return resultPara;
        }

        try {
            //校验原密码是否正确
            com.zb.p2p.customer.dao.query.req.PerCustCond perCustCond = new com.zb.p2p.customer.dao.query.req.PerCustCond();
            perCustCond.setCustomerId(Long.parseLong(custId));
            MemberBasicInfo pcd = infoService.perList(perCustCond);
            if (pcd != null) {
                //MD5加密判断
                if (!pcd.getLoginPwd().equals(MD5Util.encrypt(req.getOriginPwd()))) {
                    log.info("modifyLoginPwd判断密码,LoginPwd:{},originPwd:{}", pcd.getLoginPwd(), MD5Util.encrypt(req.getOriginPwd()));
                    result.setCode(AppCodeEnum._A010_LOGIN_ERR.getCode());
                    result.setMessage(AppCodeEnum._A010_LOGIN_ERR.getMessage());
                    return result;
                }
            } else {
                result.setCode(AppCodeEnum._A001_USER_NOT_EXISTS.getCode());
                result.setMessage(AppCodeEnum._A001_USER_NOT_EXISTS.getMessage());
                return result;
            }

            //修改密码
            req.setCustomerId(custId);
            loginService.modifyLoginPwd(req);
        } catch (Exception e) {
            result.setCode(AppCodeEnum._9999_ERROR.getCode());
            result.setMessage(AppCodeEnum._9999_ERROR.getMessage());
            logger.info("修改登录密码异常", e);
            return result;
        }
        result.setMessage("修改登录密码成功");
        log.info("modifyLoginPwd返回结果,result:{}", result);

        return result;
    }

    @ApiOperation(value = "重置登录密码", httpMethod = "POST", produces = "application/json")
    @ApiResponse(code = 200, message = "success", response = BaseRes.class)
    @ResponseBody
    @RequestMapping(value = "/resetLoginPwd", method = RequestMethod.POST, produces = "application/json")
    public BaseRes<Object> resetLoginPwd(@RequestBody CustomerReq req) {
        log.info("resetLoginPwd请求参数,req{}", req);

        BaseRes<Object> result = new BaseRes<Object>(true);
        String mobile = req.getMobile();
        String codeKaptcha = req.getCodeKaptcha();

        BaseRes<Object> resultPara = paramsDetector.resetLoginPwdDetector(req);
        if (resultPara != null) {
            return resultPara;
        }
        try {
            boolean isValid = captchaService.validate(CaptchaService.FUNC_RESET, mobile, codeKaptcha);
            if (isValid) {
                loginService.resetLoginPwd(req);
            } else {
                result.setCode(AppCodeEnum._A005_SMS_CAPT_INVALID.getCode());
                result.setMessage(AppCodeEnum._A005_SMS_CAPT_INVALID.getMessage());
                return result;
            }
        } catch (Exception e) {
            result.setCode(AppCodeEnum._9999_ERROR.getCode());
            result.setMessage(AppCodeEnum._9999_ERROR.getMessage());
            logger.info("重置登录密码异常", e);
            return result;
        }
        result.setMessage("重置登录密码成功");
        log.info("resetLoginPwd返回结果,result{}", result);

        return result;
    }

    @ReadOnlyConnection
    @ApiOperation(value = "登出", httpMethod = "POST", produces = "application/json")
    @ApiResponse(code = 200, message = "success", response = BaseRes.class)
    @ResponseBody
    @RequestMapping(value = "/logout", method = RequestMethod.POST, produces = "application/json")
    public BaseRes<Object> logout(@RequestHeader(name = "customerId", required = false) String cId) {
        log.info("logout请求参数,cId:{}", cId);

        BaseRes<Object> result = new BaseRes<Object>(true);
        if (!StringUtils.isNumeric(cId)) {
            result.setCode(AppCodeEnum._0001_ILLEGAL_PARAMETER.getCode());
            result.setMessage("客户ID不能为空");
            return result;
        }
        try {
            Long customerId = Long.valueOf(cId);
            String loginToken = loginService.customerIdToToken(cId);
            loginService.logout(customerId, loginToken);
        } catch (Exception e) {
            //跳过
            logger.warn("登出操作异常", e);
        }

        result.setMessage("登出成功");

        log.info("logout返回结果,result{}", result);
        return result;
    }

    @ReadOnlyConnection
    @ApiOperation(value = "根据token获得登录ID", httpMethod = "POST", produces = "application/json")
    @ApiResponse(code = 200, message = "success", response = BaseRes.class)
    @ResponseBody
    @RequestMapping(value = "/getCustomerId", method = RequestMethod.POST, produces = "application/json")
    public BaseRes<String> getCustomerId(@RequestBody CustomerReq req) {
        log.info("getCustomerId请求参数,req{}", req);

        BaseRes<String> result = new BaseRes<String>();
        String loginToken = req.getLoginToken();
        if (StringUtils.isBlank(loginToken)) {
            result.setMessage("参数错误：token缺失");
            result.setCode(AppCodeEnum._0001_ILLEGAL_PARAMETER.getCode());
        } else if (StringUtils.isBlank(req.getMemberType())) {
            result.setMessage("参数错误：会员类型缺失");
            result.setCode(AppCodeEnum._0001_ILLEGAL_PARAMETER.getCode());
        } else {
            try {
                String customerId = loginService.tokenToCustomerId(loginToken, req.getMemberType());
                result.setData(customerId);
            } catch (AppException e) {
                result.setCode(e.getCode());
                result.setMessage(e.getMessage());
            }
        }
        log.info("getCustomerId返回结果,result{}", result);

        return result;
    }

    @ApiOperation(value = "生成客户端标识", httpMethod = "POST", produces = "application/json")
    @ApiResponse(code = 200, message = "success", response = BaseRes.class)
    @ResponseBody
    @RequestMapping(value = "/getClientId", method = RequestMethod.POST, produces = "application/json")
    public BaseRes<String> getClientId() {
        BaseRes<String> result = new BaseRes<String>();
        result.setData(UuidUtils.getUUID());
        return result;
    }

    @ApiOperation(value = "机构注册", httpMethod = "POST", produces = "application/json")
    @ApiResponse(code = 200, message = "success", response = BaseRes.class)
    @ResponseBody
    @RequestMapping(value = "/registerOrg", method = RequestMethod.POST, produces = "application/json")
    public BaseRes<RegisterOrgMemberRes> registerOrgMember(@RequestBody @Valid RegisterOrgMemberReq req) {
        // 校验
        infoService.validateRegisterOrgInfo(req);

        // 根据机构代码获取
        OrgCustomerInfo currentCustomerInfo = infoService.getOrgMemberInfoByCardNo(req.getOrgCardNo());
        // 注册操作
        BaseRes<RegisterOrgMemberRes> result = infoService.registerOrgMember(req, currentCustomerInfo);

        return result;
    }
}
