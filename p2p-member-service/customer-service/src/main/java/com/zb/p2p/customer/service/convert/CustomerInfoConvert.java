package com.zb.p2p.customer.service.convert;

import com.alibaba.fastjson.JSONObject;
import com.zb.fincore.common.utils.BeanUtils;
import com.zb.p2p.customer.api.entity.*;
import com.zb.p2p.customer.client.domain.CompanyAuthentReq;
import com.zb.p2p.customer.client.domain.RealNameAuthReq;
import com.zb.p2p.customer.client.domain.SyncCorpInfoRes;
import com.zb.p2p.customer.client.domain.TelephoneAuthReq;
import com.zb.p2p.customer.common.constant.AppConstant;
import com.zb.p2p.customer.common.enums.AccountPurposeEnum;
import com.zb.p2p.customer.common.enums.IdCardTypeEnum;
import com.zb.p2p.customer.common.enums.OrgCardTypeEnum;
import com.zb.p2p.customer.common.util.CustomerConstants;
import com.zb.p2p.customer.common.util.DateUtil;
import com.zb.p2p.customer.common.util.StringUtils;
import com.zb.p2p.customer.dao.domain.CustomerInfo;
import com.zb.p2p.customer.dao.domain.OrgCustomerInfo;
import com.zb.qjs.common.util.OrderNoGenerator;

import java.util.Date;

/**
 * <p> 会员信息转换 </p>
 *
 * @author Vinson
 * @version CustomerInfoConvert.java v1.0 2018/3/7 18:30 Zhengwenquan Exp $
 */
public class CustomerInfoConvert {

    private static final String BUSINESS_LICENSE = "BUSINESSLICENSE";
    private static final String CREDIT_CODE = "CREDITCODE";
    private static final String ORGANIZATION_CODE = "ORGANIZATIONCODE";
    private static final String ID_CARD = "IDCARD";

    public static JSONObject convert2OpenAccountJsonObject(Object from, OpenAccountReq req) {

        // common args
        JSONObject to = convert2OpenAccountJsonObject(req);
        // self args
        if (from instanceof CustomerInfo) {
            CustomerInfo customerInfo = (CustomerInfo) from;
            to.put("orderNo", OrderNoGenerator.generate(customerInfo.getCustomerId()));//订单号
            to.put("accountName", customerInfo.getRealName());//账户名称
            to.put("memberId", customerInfo.getCustomerId());//会员id
        } else if (from instanceof OrgCustomerInfo) {
            OrgCustomerInfo orgCustomerInfo = (OrgCustomerInfo) from;
            to.put("orderNo", OrderNoGenerator.generate(orgCustomerInfo.getOrgId()));//订单号
            to.put("accountName", orgCustomerInfo.getOrgName());//账户名称
            to.put("memberId", orgCustomerInfo.getOrgId());//会员id
        }

        return to;
    }

    /**
     * 调用支付开户请求的公共参数
     *
     * @param req
     * @return
     */
    private static JSONObject convert2OpenAccountJsonObject(OpenAccountReq req) {
        JSONObject to = new JSONObject();
        to.put("sourceId", req.getSourceId());//系统来源
        to.put("orderTime", DateUtil.getLongDateString(new Date()));//订单时间
        to.put("accountType", req.getAccountType());//账户类型：VIR-虚拟账户,ELC-电子账户，logic-逻辑账户
        to.put("accountPurpose", req.getAccountPurpose());//账户用途(101:投资账户，201：融资账户，202：平台手续费账户 )
        to.put("memberType", req.getMemberType());//会员类型(10-个人,20-机构)

        return to;
    }

    /**
     * 机构信息转换为机构认证请求
     *
     * @param customerInfo
     * @param req
     * @return
     */
    public static CompanyAuthentReq convert2CompanyAuthReq(OrgCustomerInfo customerInfo, OrgMemberVerifyReq req) {
        CompanyAuthentReq to = new CompanyAuthentReq();
        to.setName(customerInfo.getOrgName());
        to.setRegisterNo(customerInfo.getIdCardNo());
        to.setSaleChannel(req.getSaleChannel());
        to.setSuccessUrl(req.getSuccessUrl());
        to.setAuthentType(req.getAuthentType());

        return to;
    }

    /**
     * 机构注册请求转换为机构会员信息
     *
     * @param req
     * @return
     */
    public static OrgCustomerInfo convertRegisterOrgReq2CustomerInfo(RegisterOrgMemberReq req, boolean isRegistered) {
        OrgCustomerInfo to = new OrgCustomerInfo();

        // 已注册，则只更新法人联系电话
        if (!isRegistered) {
            to.setOrgName(req.getOrgName());
            to.setIdCardType(req.getOrgCardType());
            to.setIdCardNo(req.getOrgCardNo());
            to.setOwnerName(req.getOwnerName());
            to.setOwnerIdCardType(req.getOwnerIdCardType());
            to.setOwnerIdCardNo(req.getOwnerIdCardNo());
            to.setChannelCode(req.getSaleChannel());
            to.setIsReal(CustomerConstants.IS_REAL_0);
        }
        to.setTelephone(req.getTelephone());

        return to;
    }

    /**
     * 根据个人实名认证请求转换为手机号认证请求
     *
     * @param from
     * @return
     */
    public static TelephoneAuthReq convert2PhoneAuthReq(MemberVerifyReq from, CustomerInfo customerInfo) {
        TelephoneAuthReq to = new TelephoneAuthReq();
        to.setPhoneNo(customerInfo.getMobile());
        to.setName(from.getMemberName());
        to.setCertNo(from.getCertificateNo());

        return to;
    }

    /**
     * 个人实名认证请求转换为姓名身份证认证请求
     *
     * @param from
     * @return
     */
    public static RealNameAuthReq convert2RealNameAuthReq(MemberVerifyReq from) {
        RealNameAuthReq to = new RealNameAuthReq();
        to.setRealName(from.getMemberName());
        to.setIdCardNo(from.getCertificateNo());
        to.setOrderNo(OrderNoGenerator.generate(Long.valueOf(from.getCustomerId())));
        to.setOrderTime(DateUtil.getLongDateString(new Date()));//订单时间
        to.setSourceId(CustomerConstants.PAYMENT_SOURCEID_MSD);

        return to;
    }

    /**
     * 转换为外部显示的机构会员信息
     *
     * @param from
     * @return
     * @throws Exception
     */
    public static OrgMemberInfoRes convert2OrgMemberInfoRes(OrgCustomerInfo from) throws Exception {
        if(from == null) {
            return null;
        }
        OrgMemberInfoRes to = new OrgMemberInfoRes();
        // 优先取机构的渠道会员ID
        if (StringUtils.isNotBlank(from.getChannelMemberId())) {
            to.setMemberId(Long.valueOf(from.getChannelMemberId()));
        }else {
            to.setMemberId(from.getOrgId());
        }

        // set
        BeanUtils.copyNotNullProperties(from, to);

        return to;
    }

    /**
     * 根据账户用途设置账户账号
     * @param to
     * @param req
     * @param accountNo
     */
    public static boolean setAccountNoByAccountPurpose(OrgCustomerInfo to, OpenAccountReq req, String accountNo) {

        AccountPurposeEnum accountPurpose = AccountPurposeEnum.getByCode(req.getAccountPurpose());
        switch (accountPurpose) {
            case _101:
                // 借款
                to.setAccountNo(accountNo);
                break;
            case _201:
                to.setFundCollectAccountNo(accountNo);
                break;
            case _202:
                to.setGeneralAccountNo(accountNo);
                break;
            case _203:
                to.setRiskReserveAccountNo(accountNo);
                break;
            case _204:
                to.setChannelFeeAccountNo(accountNo);
                break;
            case _205:
                to.setAuthRepayAccountNo(accountNo);
                break;
            case _206:
                to.setRepayAdminAccountNo(accountNo);
                break;
            case _207:
                to.setSecurityAccountNo(accountNo);
                break;
            default:
                return false;

        }
        return true;
    }

    /**
     * 转换企业同步结果为企业机构会员信息
     * @param req
     * @return
     */
    public static OrgCustomerInfo convertSyncCorpInfo2CustomerInfo(SyncCorpInfoRes req) {
        OrgCustomerInfo to = new OrgCustomerInfo();

        // 会员Id以会员的orgId为准
//        to.setOrgId(req.getId());
        to.setOrgName(req.getCorpName());
        to.setChannelMemberId(String.valueOf(req.getMemberId()));
        if (BUSINESS_LICENSE.equals(req.getCorpIdType())) {
            to.setIdCardType(OrgCardTypeEnum.YING_YE_ZHI_ZHAO.getCode());
        }else if (CREDIT_CODE.equals(req.getCorpIdType())){
            to.setIdCardType(OrgCardTypeEnum.XIN_YONG_DAI_MA.getCode());
        }else if (ORGANIZATION_CODE.equals(req.getCorpIdType())) {
            to.setIdCardType(OrgCardTypeEnum.JI_GOU_DAI_MA.getCode());
        }
        to.setIdCardNo(req.getCorpIdNo());
        to.setOwnerName(req.getIdentityName());
        if (IdCardTypeEnum.IDCARD.name().equals(req.getIdentityType())) {
            to.setOwnerIdCardType(IdCardTypeEnum.IDCARD.getCode());
        }
        to.setOwnerIdCardNo(req.getIdentityNo());
        to.setChannelCode(AppConstant.CHANNEL_CODE_SYNC_TXS);
        // 企业默认借款账户
        if (StringUtils.isNotBlank(req.getPayUserId())) {
            to.setIsReal(CustomerConstants.IS_REAL_1);
            to.setAccountNo(req.getPayUserId());
        }else {
            to.setIsReal(CustomerConstants.IS_REAL_0);
        }
        to.setTelephone(req.getMobile());

        // 会员类型
        to.setSourceId(req.getMemberType());
        to.setCreateTime(req.getCreated());
        to.setUpdateTime(req.getModified());

        return to;
    }
}
