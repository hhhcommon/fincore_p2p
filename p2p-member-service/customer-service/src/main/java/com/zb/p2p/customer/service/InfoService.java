/**
 *
 */
package com.zb.p2p.customer.service;

import com.alibaba.fastjson.JSONObject;
import com.zb.p2p.customer.api.entity.*;
import com.zb.p2p.customer.common.model.BaseRes;
import com.zb.p2p.customer.dao.domain.CustomerInfo;
import com.zb.p2p.customer.dao.domain.MemberBasicInfo;
import com.zb.p2p.customer.dao.domain.OrgCustomerInfo;

import java.math.BigDecimal;
import java.util.List;

/**
 * 查询会员信息
 *
 * @author guolitao
 */
public interface InfoService {

    /**
     * 获取个人会员详情
     *
     * @param customerId
     * @return
     */
    CustomerDetail getPerDetail(Long customerId);

    /**
     * 获取个人会员详情
     *
     * @param req
     * @return
     */
    InfoByChannelResp getInfoByChannel(InfoByChannelReq req);


    /**
     * 获取个人会员列表
     *
     * @param perCustCond
     * @return
     */
    MemberBasicInfo perList(com.zb.p2p.customer.dao.query.req.PerCustCond perCustCond);

    /**
     * 获取个人会员列表
     *
     * @param perCustCond
     * @return
     */
    Page<CustomerDetail> perList(PerCustCond perCustCond);

    /**
     * 获取机构会员详情
     *
     * @param orgId
     * @param channelCode
     * @return
     */
    OrgCustomerDetail getOrgDetail(Long orgId, String channelCode);

    /**
     * 根据会员ID获取会员基本信息
     *
     * @param customerId
     * @return
     */
    CustomerInfo getCustomerInfoById(Long customerId);

    /**
     * 根据手机号获取会员信息
     *
     * @param mobile
     * @return
     */
    CustomerInfo getCustomerInfoByMobile(String mobile);

    /**
     * 获取机构会员基本信息
     *
     * @param orgId
     * @return
     */
    OrgCustomerInfo getOrgMemberInfo(Long orgId);

    /**
     * 根据企业机构会员Id或者channelMemberId获取机构会员基本信息
     *
     * @param orgId
     * @return
     */
    OrgCustomerInfo getOrgMemberInfoByMemberId(String orgId);

    /**
     * 获取机构会员列表
     *
     * @param page
     * @return
     */
    Page<OrgCustomerDetail> orgList(CustCond page);

    /**
     * 根据sourceId获取机构会员账户信息
     *
     * @param sourceId
     * @return
     */
    OrgMemberInfoRes getOrgMemberInfoBySourceId(String sourceId);

    /**
     * 获取会员绑卡信息
     *
     * @param customerId
     * @return
     */
    CustomerCardBin getPerCard(Long customerId);

    /**
     * 更新个人会员信息
     *
     * @param ci
     * @return
     */
    int updatePer(CustomerInfo ci);

    String queryTxsIDByCustomerId(Long customerId);

    /**
     * 查询余额
     *
     * @param customerId
     * @return
     */
    BigDecimal queryBanlanceByCustomerId(long customerId);

    public BigDecimal queryBanlanceByAccountNo(String accountNo);

    /**
     * 查询个人会员可用支付方式
     *
     * @param customerId
     * @return
     */
    List<PayType> queryAvlPayTypes(Long customerId);

    /**
     * 调用支付服务进行开户操作
     *
     * @param customerInfo
     * @return
     */
    BaseRes<OpenAccountRes> openAccount(Object customerInfo, OpenAccountReq req);

    /**
     * 开户请求的业务校验
     *
     * @param req
     */
    void validateOpenAccount(OpenAccountReq req);

    /**
     * 增加个人实名认证
     *
     * @param req
     * @return
     */
    BaseRes<JSONObject> addMemberVerify(MemberVerifyReq req);

    /**
     * 根据customerId 或 mobile 获取个人会员详情
     *
     * @param customerId
     * @return
     */
    BaseRes<CustomerSecretDetail> getCustomerDetail(String customerId, String mobile);

    /**
     * 根据机构代码获取机构会员基本信息
     *
     * @param idCardNo
     * @return
     */
    OrgCustomerInfo getOrgMemberInfoByCardNo(String idCardNo);

    /**
     * 增加机构认证
     *
     * @param req
     * @return
     */
    BaseRes<OrgMemberVerifyRes> addOrgMemberVerify(OrgCustomerInfo customerInfo, OrgMemberVerifyReq req);

    /**
     * 机构注册
     *
     * @param req
     * @return
     */
    BaseRes<RegisterOrgMemberRes> registerOrgMember(RegisterOrgMemberReq req, OrgCustomerInfo currentCust);

    /**
     * 获取机构认证结果
     *
     * @param customerInfo
     * @param req
     * @return
     */
    BaseRes<OrgMemberVerifyRes> getOrgMemberVerifyInfo(OrgCustomerInfo customerInfo, OrgMemberVerifyReq req);

    /**
     * 校验会员ID是否已注册过
     *
     * @param req
     * @param customerInfo
     * @return
     */
    BaseRes<JSONObject> validateVerifyMember(MemberVerifyReq req, CustomerInfo customerInfo);

    /**
     * 校验机构注册请求信息
     *
     * @param req
     */
    void validateRegisterOrgInfo(RegisterOrgMemberReq req);
}
