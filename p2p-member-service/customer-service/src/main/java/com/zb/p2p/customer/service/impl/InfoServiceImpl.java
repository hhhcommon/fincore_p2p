
package com.zb.p2p.customer.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.zb.p2p.customer.api.entity.*;
import com.zb.p2p.customer.api.entity.PayType.PayTypeEnum;
import com.zb.p2p.customer.api.entity.PerCustCond;
import com.zb.p2p.customer.api.entity.card.Card;
import com.zb.p2p.customer.client.domain.*;
import com.zb.p2p.customer.client.msdfinance.MsdFinanceClient;
import com.zb.p2p.customer.client.payment.PaymentClient;
import com.zb.p2p.customer.client.signstamper.SignStamperClient;
import com.zb.p2p.customer.common.constant.AppConstant;
import com.zb.p2p.customer.common.enums.*;
import com.zb.p2p.customer.common.exception.AppException;
import com.zb.p2p.customer.common.model.BaseRes;
import com.zb.p2p.customer.common.model.ExternalBaseRes;
import com.zb.p2p.customer.common.util.CustomerConstants;
import com.zb.p2p.customer.common.util.DateUtil;
import com.zb.p2p.customer.common.util.SensitiveInfoUtils;
import com.zb.p2p.customer.common.util.StringUtils;
import com.zb.p2p.customer.dao.*;
import com.zb.p2p.customer.dao.domain.*;
import com.zb.p2p.customer.dao.domain.page.PerCustPage;
import com.zb.p2p.customer.dao.page.PageData;
import com.zb.p2p.customer.dao.query.req.*;
import com.zb.p2p.customer.service.BankService;
import com.zb.p2p.customer.service.InfoService;
import com.zb.p2p.customer.service.bo.HoldTotalAssetsBO;
import com.zb.p2p.customer.service.bo.HoldTotalAssetsReq;
import com.zb.p2p.customer.service.config.ReadOnlyConnection;
import com.zb.p2p.customer.service.convert.*;
import com.zb.p2p.customer.service.rpc.OrderServiceClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 查询会员服务
 *
 * @author guolitao
 */
@Slf4j
@Service
public class InfoServiceImpl implements InfoService {

    @Resource
    private CustomerInfoMapper customerInfoMapper;

    @Resource
    private MemberBasicInfoMapper memberBasicInfoMapper;

    @Resource
    private CustomerBindcardMapper customerBindcardMapper;
    @Resource
    private OrgBankCardMapper orgBankCardMapper;

    @Resource
    private OrgCustomerInfoMapper orgCustomerInfoMapper;

    @Resource
    private BankService bankService;

    @Autowired
    private PaymentClient paymentClient;
    @Autowired
    private SignStamperClient signStamperClient;

    @Autowired
    private OrderServiceClient orderServiceClient;

    @Autowired
    private InfoService infoService;

    @Override
    public CustomerDetail getPerDetail(Long customerId) {
        CustomerInfo info = customerInfoMapper.selectByPrimaryKey(customerId);
        if (info == null) {
            return null;
        }
        TxsCusInfo txsCusInfo = new TxsCusInfo();

        CustomerBindcard card = customerBindcardMapper.selectCustUseCard(customerId);
        Card limit = null;
        if (card != null && StringUtils.isNotBlank(card.getBankCode()) && StringUtils.isNotBlank(card.getCardType())) {
            limit = bankService.getCardByBankCode(CustomerConstants.PAYMENT_SOURCEID_MSD, card.getBankCode(), card.getCardType());
        }
        return PerCustomerDetailConverter.convert(info, card, limit, txsCusInfo);
    }

    @Override
    public InfoByChannelResp getInfoByChannel(InfoByChannelReq req) {
        CustomerInfo info = customerInfoMapper.selectByAccountId(req.getChannelCustomerId(), req.getChannelCode());
        if (info == null) {
            return null;
        }
        InfoByChannelResp resp = new InfoByChannelResp();
        BeanUtils.copyProperties(info, resp);
        resp.setCustomerId(String.valueOf(info.getCustomerId()));

        return resp;
    }

    @Override
    public MemberBasicInfo perList(com.zb.p2p.customer.dao.query.req.PerCustCond perCustCond) {
        return memberBasicInfoMapper.queryBySelected(perCustCond);
    }

    @Override
    public Page<CustomerDetail> perList(PerCustCond perCustCond) {
        PerCustPage page = new PerCustPage();
        page.setPageNo(perCustCond.getPageNo());
        page.setPageSize(perCustCond.getPageSize());
        if (StringUtils.isNumeric(perCustCond.getCustomerId())) {
            page.setCustomerId(Long.valueOf(perCustCond.getCustomerId()));
        }
        if (StringUtils.isNotBlank(perCustCond.getIdCardNo())) {
            page.setIdCardNo(perCustCond.getIdCardNo());
        }
        if (StringUtils.isNotBlank(perCustCond.getIdCardType())) {
            page.setIdCardType(perCustCond.getIdCardType());
        }
        if (StringUtils.isNotBlank(perCustCond.getMobile())) {
            page.setMobile(perCustCond.getMobile());
        }
        if (StringUtils.isNotBlank(perCustCond.getLoginPwd())) {
            page.setLoginPwd(perCustCond.getLoginPwd());
        }
        List<PageData> pageList = memberBasicInfoMapper.listPage(page);
        return PerCustPageListConverter.convert(page, pageList);
    }

    /* (non-Javadoc)
     * @see com.zb.p2p.customer.service.InfoService#getOrgDetail(java.lang.Long)
     */
    @Override
    public OrgCustomerDetail getOrgDetail(Long orgId, String channelCode) {

        List<OrgBankCard> cardList = orgBankCardMapper.selectByOrgId(orgId);
        if (StringUtils.isNotBlank(channelCode)) {
            List<OrgBankCard> bankCardList = new ArrayList<OrgBankCard>();
            if (cardList != null && !cardList.isEmpty()) {
                for (OrgBankCard orgBc : cardList) {
                    if (channelCode.equals(orgBc.getChannelCode())) {
                        bankCardList.add(orgBc);
                    }
                }
            }
            cardList = bankCardList;
        }
        OrgCustomerInfo orgCi = orgCustomerInfoMapper.selectByPrimaryKey(orgId);
        OrgCustomerDetail pd = OrgCustDetailConverter.convert(orgCi, cardList);
        return pd;
    }

    @ReadOnlyConnection
    @Override
    public CustomerInfo getCustomerInfoById(Long customerId) {
        return customerInfoMapper.selectByPrimaryKey(customerId);
    }

    @ReadOnlyConnection
    @Override
    public CustomerInfo getCustomerInfoByMobile(String mobile) {
        return customerInfoMapper.selectByUkMobile(mobile);
    }

    @ReadOnlyConnection
    @Override
    public OrgCustomerInfo getOrgMemberInfo(Long orgId) {
        return orgCustomerInfoMapper.selectByPrimaryKey(orgId);
    }

    @ReadOnlyConnection
    @Override
    public OrgCustomerInfo getOrgMemberInfoByMemberId(String orgId) {
        OrgCustomerInfo customerInfo = orgCustomerInfoMapper.selectByPrimaryKey(Long.valueOf(orgId));
        if (customerInfo == null) {
            customerInfo = orgCustomerInfoMapper.selectByChannelMemberId(orgId);
        }

        return customerInfo;
    }

    @ReadOnlyConnection
    @Override
    public OrgMemberInfoRes getOrgMemberInfoBySourceId(String sourceId) {
        OrgMemberInfoRes orgMemberInfoRes = null;
        try {
            orgMemberInfoRes = CustomerInfoConvert.convert2OrgMemberInfoRes(orgCustomerInfoMapper.selectBySourceId(sourceId));
        } catch (Exception e) {
            log.error("根据系统标识获取机构会员信息异常：", e);
        }
        return orgMemberInfoRes;
    }

    /* (non-Javadoc)
         * @see com.zb.p2p.customer.service.InfoService#orgList(com.zb.p2p.customer.api.entity.CustCond)
         */
    @Override
    public Page<OrgCustomerDetail> orgList(CustCond custCond) {
        PerCustPage page = new PerCustPage();
        page.setPageNo(custCond.getPageNo());
        page.setPageSize(custCond.getPageSize());
        List<PageData> pageList = orgCustomerInfoMapper.listPageOrgAcc(page);
        return OrgCustPageListConverter.convert(page, pageList);
    }

    @Override
    public CustomerCardBin getPerCard(Long customerId) {
        String bankCode = "";
        String cardType = "";
        CustomerBindcard card = customerBindcardMapper.selectCustUseCard(customerId);
        CustomerInfo customerInfo = customerInfoMapper.selectByPrimaryKey(Long.valueOf(customerId));

        if (card != null) {
            bankCode = card.getBankCode();
            cardType = card.getCardType();
        }
        Card limit = null;

        log.info("bankCode:" + bankCode + ",cardType:" + cardType);
        if (StringUtils.isNotBlank(bankCode) && StringUtils.isNotBlank(cardType)) {
            limit = bankService.getCardByBankCode(CustomerConstants.PAYMENT_SOURCEID_MSD, bankCode, cardType);
        }
        log.info("getCardByBankCode :  {} ", limit);

        CustomerCardBin cardBin = PerCardConverter.convert(card, limit);
        if (cardBin != null) {
            boolean unbindFlag = true;

            //验证资产是否为零(在投)
            HoldTotalAssetsReq holdTotalAssetsReq = new HoldTotalAssetsReq();
            holdTotalAssetsReq.setMemberId(customerId + "");
            holdTotalAssetsReq.setInterestDate(DateUtil.formatDate(new Date()));
            holdTotalAssetsReq.setCaller("JHHY");
            ExternalBaseRes<HoldTotalAssetsBO> responseEntity = orderServiceClient.holdTotalAssets(holdTotalAssetsReq);
            if (responseEntity != null) {
                if (responseEntity.getData() != null && (Double.valueOf(responseEntity.getData().getTotalAssets()) > 0)) {
                    unbindFlag = false;
                }
            }

            if (unbindFlag) {
                BigDecimal zero = new BigDecimal(0);
                //验证余额是否为零
                BigDecimal banlance = infoService.queryBanlanceByAccountNo(customerInfo.getAccountNo());
                if (banlance != null) {
                    if (banlance.compareTo(zero) != 0) {
                        unbindFlag = false;
                    }
                }
            }

            cardBin.setUnbindFlag(unbindFlag);
        }

        return cardBin;
    }

    @Override
    public int updatePer(CustomerInfo ci) {
        if (ci == null || ci.getCustomerId() == null) {
            throw new AppException(AppCodeEnum._0001_ILLEGAL_PARAMETER);
        }
        return customerInfoMapper.updateByPrimaryKeySelective(ci);
    }

    @Override
    public String queryTxsIDByCustomerId(Long customerId) {


        CustomerInfo info = customerInfoMapper.selectByPrimaryKey(customerId);
        if (info != null) {
            return info.getChannelCustomerId();
        }
        return "";
    }

    @Override
    public List<PayType> queryAvlPayTypes(Long customerId) {
        CustomerDetail cd = getPerDetail(customerId);
        List<PayType> payTypes = new ArrayList<>();
        payTypes.add(new PayType(PayTypeEnum.ACCOUNT));
        if (cd != null && cd.getIsActiveEAccount() != null) {
            if (1 == cd.getIsActiveEAccount()) {
                payTypes.add(new PayType(PayTypeEnum.E_ACCOUNT));
            }
        }
        return payTypes;
    }

    @Override
    public BigDecimal queryBanlanceByCustomerId(long customerId) {
        BigDecimal amt = null;
        CustomerDetail cd = this.getPerDetail(customerId);
//        if (cd.getIsDepositManage() != 1) {
//            return null;
//        }

        QueryAccountBalanceReq req = new QueryAccountBalanceReq();
//        req.setMemberId(String.valueOf(customerId));
//        req.setAccountPurpose(AccountPurposeEnum._102.getCode());
//        req.setSourceId(AppConstant.SOURCE_ID_MSD);

        BaseRes<QueryAccountBalanceRes> res = paymentClient.queryAccountBalance(req);

        if (res == null || !AppConstant.RESP_CODE_SUCCESS.equals(res.getCode())) {
            throw AppException.getInstance(res.getCode(), res.getMessage());
        }
        amt = res.getData().getAvailableAmount();
        return amt;
    }

    public BigDecimal queryBanlanceByAccountNo(String accountNo) {
        BigDecimal amt = null;

        QueryAccountBalanceReq req = new QueryAccountBalanceReq();
        req.setAccountNo(accountNo);

        BaseRes<QueryAccountBalanceRes> res = paymentClient.queryAccountBalance(req);

        if (res == null || !AppConstant.RESP_CODE_SUCCESS.equals(res.getCode())) {
            throw AppException.getInstance(res.getCode(), res.getMessage());
        }
        amt = res.getData().getAvailableAmount();
        return amt;
    }

    @Override
    public BaseRes<OpenAccountRes> openAccount(Object customerInfo, OpenAccountReq req) {

        // 转换开户请求为JSonParams
        JSONObject jsonReq = CustomerInfoConvert.convert2OpenAccountJsonObject(customerInfo, req);

        BaseRes<OpenAccountRes> result = new BaseRes<>(AppCodeEnum._A113_OPEN_ACCOUNT_FAIL);
        try {
            //开户操作
            PaymentResponse payRes = paymentClient.openAccount(jsonReq);
            // 结果转换
            if (payRes != null) {
                if (AppCodeEnum._0000_SUCCESS.getCode().equals(payRes.getCode())) {
                    // 支付响应的结果账务账号
                    String accountNo = payRes.getAccountNo();
                    // 开户成功
                    if (StringUtils.isNotBlank(accountNo)) {
                        Long memberId = jsonReq.getLong("memberId");
                        int updateRows = 0;
                        if (customerInfo instanceof CustomerInfo) {
                            CustomerInfo updateCustomerInfo = new CustomerInfo();
                            updateCustomerInfo.setCustomerId(memberId);//客户ID
                            updateCustomerInfo.setAccountNo(accountNo);

                            //更新个人会员表账务账户信息
                            updateRows = customerInfoMapper.updateByPrimaryKeySelective(updateCustomerInfo);
                        } else {
                            OrgCustomerInfo updateCustomerInfo = new OrgCustomerInfo();
                            updateCustomerInfo.setOrgId(memberId);//客户ID
                            // 根据账户用途进行相应落地
                            boolean setBl = CustomerInfoConvert.setAccountNoByAccountPurpose(updateCustomerInfo, req, accountNo);

                            // 更新机构会员
                            if (setBl) {
                                updateRows = orgCustomerInfoMapper.updateByPrimaryKeySelective(updateCustomerInfo);
                            }
                        }
                        // 标记结果行数
                        if (updateRows != 1) {
                            log.error("更新会员表[账户]信息时影响行数不正确，会员类型:{}，UpdateRows:{}",
                                    req.getMemberType(), updateRows);
                        } else {
                            // 成功装配结果返回
                            result.resetCode(AppCodeEnum._0000_SUCCESS);
                            result.setData(new OpenAccountRes(accountNo));
                            return result;
                        }
                    }
                } else {
                    result.failure(payRes.getCode(), payRes.getMsg());
                }
            }
        } catch (Exception e) {
            log.error("请求金核[开户]出现错误：", e);
            result.resetCode(AppCodeEnum._9999_ERROR);
        }
        return result;
    }

    public void validateOpenAccount(OpenAccountReq req) {

        // 个人不能开通机构账户，反之机构不能开通个人账户
        MemberType memberType = MemberType.getByCode(req.getMemberType());
        Assert.notNull(memberType, "参数错误：会员类型传入错误");

        // 判断账户类型(只支持逻辑户)
        Assert.notNull(AccountTypeEnum.getByCode(req.getAccountType()), "参数错误：账户类型传入错误");

        // 判断账户类型(只支持逻辑户)
        Assert.notNull(AccountPurposeEnum.getByCode(req.getAccountPurpose()), "参数错误：账户用途传入错误");

        // 个人会员只支持出借及借款账户
        if (memberType == MemberType.PERSONAL) {
            Assert.isTrue(AppConstant.PERSONAL_ACCOUNT_PURPOSE.contains(req.getAccountPurpose()), "个人会员只支持开通出借及借款账户");
        }

    }

    @Override
    public BaseRes<JSONObject> addMemberVerify(MemberVerifyReq req) {
        // 默认认证失败
        BaseRes<JSONObject> result = new BaseRes<>(AppCodeEnum._A201_MEMBER_VERIFY_FAIL);

        // 认证响应结果
        PaymentResponse response;
        try {
            // 获取认证类型
            VerifyTypeEnum verifyType = VerifyTypeEnum.getByCode(req.getVerifyType());
            switch (verifyType) {
                case XM_SFZ:
                    // 两要素认证
                    response = paymentClient.memberRealNameVerify(CustomerInfoConvert.convert2RealNameAuthReq(req));
                    break;
                default:
                    result.resetCode(AppCodeEnum._0001_ILLEGAL_PARAMETER);
                    result.setMessage("个人实名认证暂不支持该认证类型：" + req.getVerifyType());
                    return result;
            }
            // 检查响应结果
            if (response != null) {
                // 透传认证结果状态
                result.setData(response.getData());
                // 实名认证成功(实名状态码为一致-01)
                if (AppConstant.REALNAME_VALIDATE_CODE_SUCCESS.equals(response.getValidateStatus())) {
                    // 认证一致成功后标记为已认证
                    CustomerInfo updateCustomerInfo = new CustomerInfo();
                    updateCustomerInfo.setCustomerId(Long.valueOf(req.getCustomerId()));//客户ID
                    updateCustomerInfo.setIsReal(CustomerConstants.IS_REAL_1);//是否已实名,0-否1-是
                    updateCustomerInfo.setRealName(req.getMemberName());//客户真实姓名
                    updateCustomerInfo.setIdCardType(IdCardTypeEnum.IDCARD.getCode());// 身份证件
                    updateCustomerInfo.setIdCardNo(req.getCertificateNo());

                    //更新会员表账务账户信息
                    int updateRows = customerInfoMapper.updateByPrimaryKeySelective(updateCustomerInfo);

                    if (updateRows == 1) {
                        result.resetCode(AppCodeEnum._0000_SUCCESS);
                        return result;
                    } else {
                        log.error("更新会员表[实名认证]信息时影响行数不正确，UpdateRows:{}", updateRows);
                    }
                }
            }
        } catch (Exception e) {
            log.error("请求金核[实名认证]出现错误：", e);
            result.resetCode(AppCodeEnum._9999_ERROR);
        }
        return result;
    }

    @Override
    public BaseRes<CustomerSecretDetail> getCustomerDetail(String memberId, String mobile) {
        BaseRes<CustomerSecretDetail> result = new BaseRes<CustomerSecretDetail>(true);
        if (StringUtils.isBlank(memberId) && StringUtils.isBlank(mobile)) {
            result.setCode(AppCodeEnum._0001_ILLEGAL_PARAMETER.getCode());
            result.setMessage("参数错误：memberId或mobile必须有值。");
            return result;
        }
        if (StringUtils.isNotBlank(memberId)) {
            if (!StringUtils.isNumeric(memberId)) {
                result.setCode(AppCodeEnum._0001_ILLEGAL_PARAMETER.getCode());
                result.setMessage(AppCodeEnum._0001_ILLEGAL_PARAMETER.getMessage());
                return result;
            }
        } else {
            CustomerInfo customerInfo = getCustomerInfoByMobile(mobile);
            if (customerInfo == null) {
                result.setCode(AppCodeEnum._A001_USER_NOT_EXISTS.getCode());
                result.setMessage("会员信息不存在。");
                return result;
            }
            memberId = String.valueOf(customerInfo.getCustomerId());
        }

        CustomerInfo info = customerInfoMapper.selectByPrimaryKey(Long.valueOf(memberId));
//        CustomerDetail detailInfo = getPerDetail(Long.valueOf(memberId));
        if (info == null) {
            result.setCode(AppCodeEnum._A001_USER_NOT_EXISTS.getCode());
            result.setMessage("会员信息不存在。");
            return result;
        } else {
            CustomerSecretDetail customerSecretDetail = new CustomerSecretDetail();
            if (info.getIsBindCard() == 1) {
                CustomerBindcard card = customerBindcardMapper.selectCustUseCard(Long.valueOf(memberId));
                customerSecretDetail.setBankCardNo(null == card ? "" : SensitiveInfoUtils.getHiddenData(card.getBankCardNo(), 4, 4));
                customerSecretDetail.setBankMobile(null == card ? "" : SensitiveInfoUtils.getHiddenData(card.getMobile(), 3, 4));
                customerSecretDetail.setBankName(null == card ? "" : card.getBankName());
            }

            //脱敏处理;
            customerSecretDetail.setMobile(SensitiveInfoUtils.getHiddenData(info.getMobile(), 3, 4));
            customerSecretDetail.setIdCardType(info.getIdCardType());
            customerSecretDetail.setIdCardNo(SensitiveInfoUtils.getHiddenData(info.getIdCardNo(), 6, 4));
            customerSecretDetail.setName(info.getRealName());
            customerSecretDetail.setMemberId(memberId);

            customerSecretDetail.setRegisterTime(DateUtil.formatDateTime(info.getRegisterTime()));
            result.setData(customerSecretDetail);
        }
        return result;
    }

    @ReadOnlyConnection
    @Override
    public OrgCustomerInfo getOrgMemberInfoByCardNo(String idCardNo) {
        return orgCustomerInfoMapper.selectByUkIdCardNo(idCardNo);
    }

    @Override
    public BaseRes<OrgMemberVerifyRes> addOrgMemberVerify(OrgCustomerInfo customerInfo, OrgMemberVerifyReq req) {
        BaseRes<OrgMemberVerifyRes> result = new BaseRes<>(AppCodeEnum._A201_ORG_MEMBER_VERIFY_FAIL);
        try {
            // 转换
            CompanyAuthentReq authentReq = CustomerInfoConvert.convert2CompanyAuthReq(customerInfo, req);
            // 调用认证操作
            CompanyAuthentRes response = signStamperClient.orgMemberVerify(authentReq);
            if (response != null) {
                // 认证成功
                if (AppConstant.RESP_CODE_SUCCESS.equals(response.getResultCode())) {
                    // 认证一致成功后标记为已认证
                    OrgCustomerInfo updateCustomerInfo = new OrgCustomerInfo();
                    updateCustomerInfo.setOrgId(customerInfo.getOrgId());// 机构ID
                    updateCustomerInfo.setIsReal(CustomerConstants.IS_REAL_1);//是否已实名,0-否1-是

                    // 标记已认证
                    int updateRows = orgCustomerInfoMapper.updateByPrimaryKeySelective(updateCustomerInfo);

                    if (updateRows == 1) {
                        // 更新成功装配结果
                        result.resetCode(AppCodeEnum._0000_SUCCESS);
                        result.setData(new OrgMemberVerifyRes(response.getAuthenUrl()));
                        return result;
                    } else {
                        log.error("更新会员表[机构认证]信息时影响行数不正确，UpdateRows:{}", updateRows);
                    }
                } else {
                    result.failure(response.getResultCode(), response.getResultMsg());
                }
            }
        } catch (Exception e) {
            log.error("请求金核[机构认证]出现错误：", e);
            result.resetCode(AppCodeEnum._9999_ERROR);
        }
        return result;
    }

    @Override
    public BaseRes<RegisterOrgMemberRes> registerOrgMember(RegisterOrgMemberReq req, OrgCustomerInfo currentCust) {
        BaseRes<RegisterOrgMemberRes> result = new BaseRes<>();

        // 新注册 Or 已注册
        boolean isRegistered = currentCust != null;

        OrgCustomerInfo customerInfo = CustomerInfoConvert.convertRegisterOrgReq2CustomerInfo(req, isRegistered);
        // 落地注册信息到数据库
        if (isRegistered) {
            customerInfo.setOrgId(currentCust.getOrgId());
            int updateRows = orgCustomerInfoMapper.updateByPrimaryKeySelective(customerInfo);
            if (updateRows != 1) {
                log.error("更新会员表[机构注册]信息时影响行数不正确，UpdateRows:{}", updateRows);
                result.resetCode(AppCodeEnum._9999_ERROR);
            } else {
                // 更新落地成功，则修改结果标记为已注册
                result.setData(new RegisterOrgMemberRes(customerInfo.getOrgId(), YesNoEnum.YES.getCode()));
            }
        } else {
            int insertRows = orgCustomerInfoMapper.insert(customerInfo);
            if (insertRows != 1) {
                log.error("新增会员表[机构注册]信息时影响行数不正确，InsertRows:{}", insertRows);
                result.resetCode(AppCodeEnum._9999_ERROR);
            } else {
                // 注册落地成功，则修改结果标记为新注册
                result.setData(new RegisterOrgMemberRes(customerInfo.getOrgId(), YesNoEnum.NO.getCode()));
            }
        }
        return result;
    }

    @Override
    public BaseRes<OrgMemberVerifyRes> getOrgMemberVerifyInfo(OrgCustomerInfo customerInfo, OrgMemberVerifyReq req) {
        BaseRes<OrgMemberVerifyRes> result = new BaseRes<>(AppCodeEnum._A203_ORG_MEMBER_QUERY_FAIL);
        try {
            // 组装参数
            CompanyAuthentReq authentReq = new CompanyAuthentReq();
            authentReq.setName(customerInfo.getOrgName());
            authentReq.setSaleChannel(req.getSaleChannel());

            // 调用查询认证服务
            CompanyAuthentRes response = signStamperClient.getMemberVerifyInfo(authentReq);
            if (response != null) {
                // 机构查询响应成功
                if (AppConstant.RESP_CODE_SUCCESS.equals(response.getResultCode())) {
                    // 装配结果直接返回
                    result.resetCode(AppCodeEnum._0000_SUCCESS);
                    result.setData(new OrgMemberVerifyRes(response.getAuthentResultUrl()));
                    return result;
                } else {
                    result.failure(response.getResultCode(), response.getResultMsg());
                }
            }
        } catch (Exception e) {
            log.error("请求金核[查询机构认证结果]出现错误：", e);
            result.resetCode(AppCodeEnum._9999_ERROR);
        }
        return result;
    }

    @Override
    public BaseRes<JSONObject> validateVerifyMember(MemberVerifyReq req, CustomerInfo customerInfo) {
        BaseRes<JSONObject> result = new BaseRes<>();

        // 如果未找到会员信息，则返回
        if (customerInfo == null) {
            // 如果身份证未被认证过，则检验会员是否已经注册过
            customerInfo = this.getCustomerInfoById(Long.valueOf(req.getCustomerId()));
            if (customerInfo == null) {
                result.setCode(AppCodeEnum._A001_USER_NOT_EXISTS.getCode());
                result.setMessage("会员不存在或手机号还未注册");
            }
        } else if (!req.getCustomerId().equals(customerInfo.getCustomerId())) {
            result.setCode(AppCodeEnum._A202_MEMBER_VERIFY_DUPLICATE.getCode());
            result.setMessage("证件号：[" + req.getCertificateNo() + "]已经被实名认证过，不能再次使用");
        }

        // 是否已经认证过
        if (result.whetherSuccess() && YesNoEnum.YES.getCode() == customerInfo.getIsReal()) {
            result.setCode(AppCodeEnum._A001_USER_NOT_EXISTS.getCode());
            result.setMessage("会员已经实名认证过，请勿重复实名");
        }

        return result;
    }

    @Override
    public void validateRegisterOrgInfo(RegisterOrgMemberReq req) {

        Assert.notNull(OrgCardTypeEnum.getByCode(req.getOrgCardType()), "企业机构的证件类型非法");

        Assert.notNull(IdCardTypeEnum.getByCode(req.getOwnerIdCardType()), "法人证件类型非法");
    }
}
