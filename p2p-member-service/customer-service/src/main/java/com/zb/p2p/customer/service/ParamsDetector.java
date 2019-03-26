package com.zb.p2p.customer.service;

import com.zb.p2p.customer.api.entity.CustomerReq;
import com.zb.p2p.customer.api.entity.KaptchaSMSMsg;
import com.zb.p2p.customer.api.entity.LoginCustomerDetail;
import com.zb.p2p.customer.api.entity.LoginResp;
import com.zb.p2p.customer.common.enums.AppCodeEnum;
import com.zb.p2p.customer.common.enums.FlagEnum;
import com.zb.p2p.customer.common.model.BaseRes;
import com.zb.p2p.customer.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static com.zb.p2p.customer.service.CaptchaService.FUNC_REGISTER;

/**
 * Function:  参数校验  <br/>
 * Date:  2017/10/19 14:15 <br/>
 *
 * @author liguoliang@zillionfortune.com
 */

@Slf4j
@Service
public class ParamsDetector {
    @Autowired
    private SmsService smsService;

    /**
     * smsCaptcha 参数校验
     *
     * @param req
     * @return
     */
    public BaseRes<KaptchaSMSMsg> smsCaptchaDetector(CustomerReq req) {
        log.info("smsCaptchaDetector,Request：{}", Objects.isNull(req) ? "null" : req.toString());
        BaseRes<KaptchaSMSMsg> result = new BaseRes<KaptchaSMSMsg>(true);
        //校验参数
        String mobile = req.getMobile();
        String type = req.getType();
        String clientId = req.getClientId();

        if(StringUtils.isBlank(mobile) || !smsService.isValidMobile(mobile) ){
            result.setCode(AppCodeEnum._0001_ILLEGAL_PARAMETER.getCode());
            result.setMessage("手机号为空或格式错误");
            return result;
        }else if(StringUtils.isBlank(clientId)){
            result.setCode(AppCodeEnum._0001_ILLEGAL_PARAMETER.getCode());
            result.setMessage("客户端标识不能为空");
            return result;
        }else if(!FUNC_REGISTER.equals(type)&&!CaptchaService.FUNC_LOGIN.equals(type)&&!CaptchaService.FUNC_RESET.equals(type)){
            result.setCode(AppCodeEnum._0001_ILLEGAL_PARAMETER.getCode());
            result.setMessage("类型标识输入有误");
            return result;
        }
        return null;
    }

    /**
     * register 参数校验
     *
     * @param req
     * @return
     */
    public BaseRes<LoginCustomerDetail> registerDetector(CustomerReq req) {
        log.info("registerDetector,Request：{}", Objects.isNull(req) ? "null" : req.toString());
        BaseRes<LoginCustomerDetail> result = new BaseRes<LoginCustomerDetail>(true);
        //校验参数
        String mobile = req.getMobile();
        String codeKaptcha = req.getCodeKaptcha();
        String clientId = req.getClientId();

        if(StringUtils.isBlank(mobile) || !smsService.isValidMobile(mobile) ){
            result.setCode(AppCodeEnum._0001_ILLEGAL_PARAMETER.getCode());
            result.setMessage("手机号为空或格式错误");
            return result;
        }else if(StringUtils.isBlank(clientId)){
            result.setCode(AppCodeEnum._0001_ILLEGAL_PARAMETER.getCode());
            result.setMessage("客户端标识不能为空");
            return result;
        }else if(StringUtils.isBlank(codeKaptcha)){
            result.setCode(AppCodeEnum._0001_ILLEGAL_PARAMETER.getCode());
            result.setMessage("短信验证码不能为空");
            return result;
        }else if(StringUtils.isBlank(req.getLoginPwd())){
            result.setCode(AppCodeEnum._0001_ILLEGAL_PARAMETER.getCode());
            result.setMessage("登录密码不能为空");
            return result;
        }
        return null;
    }

    /**
     * login 参数校验
     *
     * @param req
     * @return
     */
    public BaseRes<LoginResp> loginDetector(CustomerReq req) {
        log.info("loginDetector,Request：{}", Objects.isNull(req) ? "null" : req.toString());
        BaseRes<LoginResp> result = new BaseRes<>(true);
        LoginResp resp = new LoginResp();
        resp.setIsCodeKaptcha(FlagEnum.N.getCode());
        resp.setFlag(FlagEnum.N.getCode());
        resp.setIsTXSUser(FlagEnum.N.getCode());

        //校验参数
        String mobile = req.getMobile();

        if(StringUtils.isBlank(mobile) || !smsService.isValidMobile(mobile)){
            result.setCode(AppCodeEnum._0000_SUCCESS.getCode());
            result.setMessage("手机号为空或格式错误");
            resp.setDesc("手机号为空或格式错误");
            result.setData(resp);
            return result;
        }else if(StringUtils.isBlank(req.getLoginPwd())){
            result.setCode(AppCodeEnum._0000_SUCCESS.getCode());
            result.setMessage("登录密码为空");
            resp.setDesc("登录密码不能为空");
            result.setData(resp);
            return result;
        }else if(StringUtils.isBlank(req.getSerialNo())){
            result.setCode(AppCodeEnum._0000_SUCCESS.getCode());
            result.setMessage("设备序列号为空");
            resp.setDesc("设备序列号不能为空");
            result.setData(resp);
            return result;
        }else if(StringUtils.isBlank(req.getName())){
            result.setCode(AppCodeEnum._0000_SUCCESS.getCode());
            result.setMessage("设备名称为空");
            resp.setDesc("设备名称不能为空");
            result.setData(resp);
            return result;
        }else if(StringUtils.isBlank(req.getModel())){
            result.setCode(AppCodeEnum._0000_SUCCESS.getCode());
            result.setMessage("设备型号为空");
            resp.setDesc("设备型号不能为空");
            result.setData(resp);
            return result;
        }
        return null;
    }

    /**
     * modifyLoginPwd 参数校验
     *
     * @param req
     * @return
     */
    public BaseRes<Object> modifyLoginPwdDetector(CustomerReq req,String custId) {
        log.info("modifyLoginPwdDetector,Request：{}", Objects.isNull(req) ? "null" : req.toString());
        BaseRes<Object> result = new BaseRes<>(true);
        //校验参数

        if(!StringUtils.isNumeric(custId)){
            result.setCode(AppCodeEnum._0001_ILLEGAL_PARAMETER.getCode());
            result.setMessage("客户ID格式错误");
            return result;
        }else if(StringUtils.isBlank(req.getOriginPwd())){
            result.setCode(AppCodeEnum._0001_ILLEGAL_PARAMETER.getCode());
            result.setMessage("原密码不能为空");
            return result;
        }else if(StringUtils.isBlank(req.getNewPwd())){
            result.setCode(AppCodeEnum._0001_ILLEGAL_PARAMETER.getCode());
            result.setMessage("新密码不能为空");
            return result;
        }else if(StringUtils.isBlank(req.getConfirmPwd())){
            result.setCode(AppCodeEnum._0001_ILLEGAL_PARAMETER.getCode());
            result.setMessage("确认密码不能为空");
            return result;
        }else if(!req.getNewPwd().equals(req.getConfirmPwd()) ){
            result.setCode(AppCodeEnum._0001_ILLEGAL_PARAMETER.getCode());
            result.setMessage("新密码和确认密码输入不一致");
            return result;
        }else if(req.getOriginPwd().equals(req.getConfirmPwd()) ){
            result.setCode(AppCodeEnum._0001_ILLEGAL_PARAMETER.getCode());
            result.setMessage("新密码不能与原密码一致");
            return result;
        }
        return null;
    }

    /**
     * resetLoginPwd 参数校验
     *
     * @param req
     * @return
     */
    public BaseRes<Object> resetLoginPwdDetector(CustomerReq req) {
        log.info("resetLoginPwdDetector,Request：{}", Objects.isNull(req) ? "null" : req.toString());
        BaseRes<Object> result = new BaseRes<>(true);
        //校验参数
        String mobile = req.getMobile();

        if(StringUtils.isBlank(mobile) || !smsService.isValidMobile(mobile)){
            result.setCode(AppCodeEnum._0001_ILLEGAL_PARAMETER.getCode());
            result.setMessage("手机号为空或格式错误");
            return result;
        }else if(StringUtils.isBlank(req.getCodeKaptcha())){
            result.setCode(AppCodeEnum._0001_ILLEGAL_PARAMETER.getCode());
            result.setMessage("验证码不能为空");
            return result;
        }else if(StringUtils.isBlank(req.getClientId())){
            result.setCode(AppCodeEnum._0001_ILLEGAL_PARAMETER.getCode());
            result.setMessage("客户端标识不能为空");
            return result;
        }else if(StringUtils.isBlank(req.getNewPwd())){
            result.setCode(AppCodeEnum._0001_ILLEGAL_PARAMETER.getCode());
            result.setMessage("新密码不能为空");
            return result;
        }
        return null;
    }

}
