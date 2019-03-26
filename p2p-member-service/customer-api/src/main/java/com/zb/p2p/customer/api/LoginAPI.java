package com.zb.p2p.customer.api;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zb.p2p.customer.api.entity.*;
import com.zb.p2p.customer.common.model.BaseRes;

/**
 * 登录API
 * @author guolitao
 *
 */
public interface LoginAPI {

	/**
	 * 获取客户端标识
	 * @return
	 */
	BaseRes<String> getClientId();
	/**
	 * 返回customerId
	 * @param loginToken
	 * @return
	 */
	BaseRes<String> getCustomerId(CustomerReq req);
	/**
	 * 手机号是否注册
	 * @param mobile
	 * @return
	 */
	BaseRes<Flag> isRegister(CustomerReq req);
	/**
	 * 获取图形验证码
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	Object getKaptchaImage(String clientId,HttpServletRequest request,HttpServletResponse response) throws IOException;	
	/**
	 * 验证图形验证码
	 * @param codeKaptcha
	 * @param request
	 * @return
	 */
	BaseRes<Flag> validImgKaptcha(CustomerReq req, HttpServletRequest request);
	/**
	 * 获取短信验证码，type 1:登录2:注册
	 * @param mobile
	 * @param type
	 * @param request
	 * @return
	 */
	BaseRes<KaptchaSMSMsg> getKaptchaSMSMsg(CustomerReq req,HttpServletRequest request);
	/**
	 * 注册
	 * @param mobile
	 * @param codeKaptcha
	 * @return
	 */
	BaseRes<LoginCustomerDetail> register(CustomerReq req);
	/**
	 * 登录
	 * @param mobile
	 * @param codeKaptcha
	 * @return
	 */
	BaseRes<LoginCustomerDetail> login(CustomerReq req);
	/**
	 * 登出
	 * @param customerId
	 * @param request
	 * @return
	 */
	BaseRes<Object> logout(String custId, HttpServletRequest request);

	/**
	 * 机构注册
	 * @param req
	 * @return
	 */
	BaseRes<RegisterOrgMemberRes> registerOrgMember(RegisterOrgMemberReq req);
}
