/**
 *
 */
package com.zb.p2p.customer.service;

import com.zb.p2p.customer.api.entity.CustomerReq;
import com.zb.p2p.customer.api.entity.LoginCustomerDetail;
import com.zb.p2p.customer.api.entity.LoginResp;

/**
 * 登录/注册服务
 *
 * @author guolitao
 */
public interface LoginService {

    /**
     * 注册服务,注册成功返回信息，不成功则以APPException返回
     *
     * @param req
     * @return
     */
    String register(CustomerReq req);

    /**
     * 登录服务，登录成功返回信息，不成功则以APPException返回
     *
     * @param req
     * @return
     */
    LoginResp login(CustomerReq req);

    /**
     * 登录服务，登录成功返回信息，不成功则以APPException返回
     * token
     *
     * @param moible
     * @return
     */
    LoginCustomerDetail login(String moible);

    /**
     * 修改登录密码
     *
     * @param req
     */
    void modifyLoginPwd(CustomerReq req);

    /**
     * 重置登录密码
     *
     * @param req
     */
    void resetLoginPwd(CustomerReq req);

    /**
     * 登出：清理缓存
     *
     * @param customerId
     */
    void logout(Long customerId, String loginToken);

    /**
     * 根据token和会员类型 取customerId
     *
     * @param loginToken
     * @param memberType
     * @return
     */
    String tokenToCustomerId(String loginToken, String memberType);

    /**
     * 根据id取token
     *
     * @param customerId
     * @return
     */
    String customerIdToToken(String customerId);

}
