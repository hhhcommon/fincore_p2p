/**
 *
 */
package com.zb.p2p.customer.api;

import com.zb.p2p.customer.api.entity.*;
import com.zb.p2p.customer.common.model.BaseRes;
import feign.slf4j.Slf4jLogger;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


/**
 * 查询会员信息API
 *
 * @author guolitao
 */
@FeignClient(name = "p2p-customer-service", configuration = Slf4jLogger.class)
public interface InfoAPI {

    /**
     * 会员绑卡信息
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/info/bankCardBindInfo", method = RequestMethod.POST)
    BaseRes<CustomerCardBin> bankCardBindInfo(CustomerReq req);

    /**
     * 个人会员详情
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/info/perDetailInfo", method = RequestMethod.POST)
    BaseRes<CustomerDetail> perDetailInfo(CustomerReq req);

    /**
     * 根据渠道客户ID查询会员信息
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/info/getInfoByChannel", method = RequestMethod.POST)
    BaseRes<InfoByChannelResp> getInfoByChannel(InfoByChannelReq req);

//	/**
//	 * 实名信息
//	 * @param customerId
//	 * @return
//	 */
//	@RequestMapping(value = "/info/realNameInfo", method = RequestMethod.POST)
//	BaseRes<RealNameInfo> realNameInfo(CustomerReq req,String customerId);
//	/**
//	 * 机构会员详情
//	 * @param req
//	 * @return
//	 */
//	@RequestMapping(value = "/info/orgDetailInfo", method = RequestMethod.POST)
//	BaseRes<OrgCustomerDetail> orgDetailInfo(OrgDtlReq req) ;
//	/**
//	 * 个人会员列表
//	 * @param perCustCond
//	 * @return
//	 */
//	@RequestMapping(value = "/info/perlist", method = RequestMethod.POST)
//	BaseRes<Page<CustomerDetail>> perlist(PerCustCond perCustCond);
//
//	/**
//	 * 机构会员列表
//	 * @param custCond
//	 * @return
//	 */
//	@RequestMapping(value = "/info/orgList", method = RequestMethod.POST)
//	BaseRes<Page<OrgCustomerDetail>> orgList(CustCond custCond);
//
//	/**
//	 * 同步会员投资概要接口，目前只更新是否投资新手标和是否投资定期
//	 * @param req
//	 * @return
//	 */
//	@RequestMapping(value = "/info/synCustInvestSummary", method = RequestMethod.POST)
//	BaseRes<Object> synCustInvestSummary(CustInvestSummaryReq req);
//
//
//	/**
//	 * 实名信息
//	 * @param customerId
//	 * @return
//	 */
//	@RequestMapping(value = "/info/queryTxsIdByCustomerId", method = RequestMethod.POST)
//	BaseRes<String> queryTxsIdByCustomerId(String customerId);
//
//
//	/**
//	 * 证件信息扫描
//	 * @param imgReq
//	 * @param customerId
//	 * @return
//	 */
//	@RequestMapping(value = "/info/certificateScanning", method = RequestMethod.POST)
//	BaseRes<ORCInfo> certificateScanning(ImgReq imgReq,String customerId);
//
//
//	/**
//	 * 查询可用的支付方式
//	 * @param customerId
//	 * @return
//	 */
//	@RequestMapping(value = "/info/queryAvlPayTypes", method = RequestMethod.POST)
//	BaseRes<List<PayType>> queryAvlPayTypes(String customerId);
//
//	/**
//	 * 个人会员实名认证
//	 * @param req
//	 * @return
//	 */
//	@RequestMapping(value = "/info/verifyMember", method = RequestMethod.POST)
//	BaseRes<String> verifyMember(MemberVerifyReq req);
//
//	/**
//	 * 个人/机构开户
//	 * @param req
//	 * @return
//	 */
//	@RequestMapping(value = "/info/openAccount", method = RequestMethod.POST)
//	BaseRes<OpenAccountRes> openAccount(OpenAccountReq req);
//
//	/**
//	 * 机构认证
//	 * @param req
//	 * @return
//	 */
//	@RequestMapping(value = "/info/verifyOrgMember", method = RequestMethod.POST)
//	BaseRes<OrgMemberVerifyRes> verifyOrgMember(OrgMemberVerifyReq req);

}
