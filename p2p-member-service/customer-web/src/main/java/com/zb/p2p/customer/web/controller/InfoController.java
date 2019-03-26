/**
 *
 */
package com.zb.p2p.customer.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.zb.p2p.customer.api.entity.*;
import com.zb.p2p.customer.api.entity.PerCustCond;
import com.zb.p2p.customer.api.entity.orc.ImgReq;
import com.zb.p2p.customer.api.entity.orc.ORCInfo;
import com.zb.p2p.customer.common.enums.AppCodeEnum;
import com.zb.p2p.customer.common.enums.MemberType;
import com.zb.p2p.customer.common.exception.AppException;
import com.zb.p2p.customer.common.model.BaseRes;
import com.zb.p2p.customer.common.util.CustomerConstants;
import com.zb.p2p.customer.common.util.MoneyUtil;
import com.zb.p2p.customer.common.util.StringUtils;
import com.zb.p2p.customer.dao.domain.CustomerInfo;
import com.zb.p2p.customer.dao.domain.MemberBasicInfo;
import com.zb.p2p.customer.dao.domain.OrgCustomerInfo;
import com.zb.p2p.customer.dao.query.req.*;
import com.zb.p2p.customer.service.CardService;
import com.zb.p2p.customer.service.DeviceService;
import com.zb.p2p.customer.service.InfoService;
import com.zb.p2p.customer.service.OCRFileService;
import com.zb.p2p.customer.service.config.ReadOnlyConnection;
import com.zb.p2p.customer.web.config.JsonResponse;
import com.zb.qjs.common.util.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

/**
 * 会员登录相关
 *
 * @author guolitao
 */
@Api(value = "会员信息查询控制器", description = "会员信息查询")
@Controller
@RequestMapping("/info")
@Slf4j
public class InfoController {
    //private static final Logger logger = LoggerFactory.getLogger(InfoController.class);
    @Resource
    private InfoService infoService;
    @Resource
    private DeviceService deviceService;
    @Resource
    private OCRFileService ocrFileService;
    @Resource
    private CardService cardService;

    @ReadOnlyConnection
    @ApiOperation(value = "会员实名情况查询", httpMethod = "POST", produces = "application/json")
    @ApiResponse(code = 200, message = "success", response = BaseRes.class)
    @ResponseBody
    @RequestMapping(value = "/realNameInfo", method = RequestMethod.POST, produces = "application/json")
    public BaseRes<RealNameInfo> realNameInfo(@RequestBody CustomerReq req, @RequestHeader(name = "customerId", required = false) String custId) {

        BaseRes<RealNameInfo> result = new BaseRes<>(true);
        String customerId = custId == null ? req.getCustomerId() : custId;
        if (StringUtils.isNumeric(customerId)) {
            com.zb.p2p.customer.dao.query.req.PerCustCond perCustCond = new com.zb.p2p.customer.dao.query.req.PerCustCond();
            perCustCond.setCustomerId(Long.parseLong(customerId));
            MemberBasicInfo detailInfo = infoService.perList(perCustCond);
            if (detailInfo == null) {
                result.setCode(AppCodeEnum._A001_USER_NOT_EXISTS.getCode());
                result.setMessage("会员【" + customerId + "】不存在");
            } else {
                RealNameInfo realNameInfo = new RealNameInfo();
                realNameInfo.setIsRealName(detailInfo.getIsReal());
                result.setData(realNameInfo);
            }
        } else {
            result.setCode(AppCodeEnum._0001_ILLEGAL_PARAMETER.getCode());
            result.setMessage(AppCodeEnum._0001_ILLEGAL_PARAMETER.getMessage());
        }
        return result;
    }

    @ReadOnlyConnection
    @ApiOperation(value = "会员绑卡查询", httpMethod = "POST", produces = "application/json")
    @ApiResponse(code = 200, message = "success", response = BaseRes.class)
    @ResponseBody
    @RequestMapping(value = "/bankCardBindInfo", method = RequestMethod.POST, produces = "application/json")
    public BaseRes<CustomerCardBin> bankCardBindInfo(@RequestBody CustomerReq req, @RequestHeader(name = "customerId", required = false) String custId) {
        log.info("会员绑卡查询========================");
        BaseRes<CustomerCardBin> result = new BaseRes<CustomerCardBin>(true);
        String customerId = custId == null ? req.getCustomerId() : custId;
        if (StringUtils.isNumeric(customerId)) {
            CustomerCardBin detailInfo = infoService.getPerCard(Long.valueOf(customerId));
            if (detailInfo == null) {
                result.setCode(AppCodeEnum._A101_USER_NOT_BIND.getCode());//没有绑卡
                result.setMessage("会员【" + customerId + "】没有绑卡");
            } else {
                result.setData(detailInfo);
            }
        } else {
            result.setCode(AppCodeEnum._0001_ILLEGAL_PARAMETER.getCode());
            result.setMessage(AppCodeEnum._0001_ILLEGAL_PARAMETER.getMessage());
        }
        return result;
    }

    @ReadOnlyConnection
    @ApiOperation(value = "机构会员详情查询", httpMethod = "POST", produces = "application/json")
    @ApiResponse(code = 200, message = "success", response = BaseRes.class)
    @ResponseBody
    @RequestMapping(value = "/orgDetailInfo", method = RequestMethod.POST, produces = "application/json")
    public BaseRes<OrgCustomerDetail> orgDetailInfo(@RequestBody OrgDtlReq req) {
        BaseRes<OrgCustomerDetail> result = new BaseRes<OrgCustomerDetail>(true);
        String orgId = req.getOrgId();
        if (StringUtils.isNumeric(orgId)) {
            OrgCustomerDetail detailInfo = infoService.getOrgDetail(Long.valueOf(orgId), req.getChannelCode());
            if (detailInfo == null) {
                result.setCode(AppCodeEnum._A001_USER_NOT_EXISTS.getCode());
                result.setMessage("会员【" + orgId + "】不存在");
            } else {
                result.setData(detailInfo);
            }
        } else {
            result.setCode(AppCodeEnum._0001_ILLEGAL_PARAMETER.getCode());
            result.setMessage(AppCodeEnum._0001_ILLEGAL_PARAMETER.getMessage());
        }
        return result;
    }

    @ReadOnlyConnection
    @ApiOperation(value = "个人会员列表查询", httpMethod = "POST", produces = "application/json")
    @ApiResponse(code = 200, message = "success", response = BaseRes.class)
    @ResponseBody
    @RequestMapping(value = "/perList", method = RequestMethod.POST, produces = "application/json")
    public BaseRes<Page<CustomerDetail>> perlist(@RequestBody PerCustCond perCustCond) {

        BaseRes<Page<CustomerDetail>> result = new BaseRes<Page<CustomerDetail>>(true);
        if (perCustCond == null || !perCustCond.isValid()) {
            result.setCode(AppCodeEnum._0001_ILLEGAL_PARAMETER.getCode());
            result.setMessage("参数错误：缺少页号和页大小");
        } else {
            //参数补全：赋默认值
            if (StringUtils.isNotBlank(perCustCond.getIdCardNo()) && StringUtils.isBlank(perCustCond.getIdCardType())) {
                //如果填了身份证号未填证件号类型则默认为身份证号-01
                perCustCond.setIdCardType("01");//默认身份证号
            }
            Page<CustomerDetail> pd = infoService.perList(perCustCond);
            result.setData(pd);
        }
        return result;
    }

    @ReadOnlyConnection
    @ApiOperation(value = "个人会员详情查询", httpMethod = "POST", produces = "application/json")
    @ApiResponse(code = 200, message = "success", response = BaseRes.class)
    @ResponseBody
    @RequestMapping(value = "/perDetailInfo", method = RequestMethod.POST, produces = "application/json")
    public BaseRes<CustomerDetail> perDetailInfo(@RequestBody CustomerReq req, @RequestHeader(name = "customerId", required = false) String custId) {
        log.info("perDetailInfo请求参数,req:{};custId{}", req, custId);

        BaseRes<CustomerDetail> result = new BaseRes<CustomerDetail>(true);
        String customerId = custId == null ? req.getCustomerId() : custId;
        if (StringUtils.isNumeric(customerId)) {
            CustomerDetail detailInfo = infoService.getPerDetail(Long.valueOf(customerId));
            if (detailInfo == null) {
                result.setCode(AppCodeEnum._A001_USER_NOT_EXISTS.getCode());
                result.setMessage("会员信息不存在");
            } else {
                result.setData(detailInfo);
            }
        } else {
            result.setCode(AppCodeEnum._0001_ILLEGAL_PARAMETER.getCode());
            result.setMessage(AppCodeEnum._0001_ILLEGAL_PARAMETER.getMessage());
        }
        log.info("perDetailInfo返回结果,result:{}", result);

        return result;
    }

    @ReadOnlyConnection
    @ApiOperation(value = "根据渠道客户ID查询会员信息", httpMethod = "POST", produces = "application/json")
    @ApiResponse(code = 200, message = "success", response = BaseRes.class)
    @ResponseBody
    @RequestMapping(value = "/getInfoByChannel", method = RequestMethod.POST, produces = "application/json")
    public BaseRes<InfoByChannelResp> getInfoByChannel(@RequestBody InfoByChannelReq req) {
        log.info("getInfoByChannel请求参数,req:{}", req);

        BaseRes<InfoByChannelResp> result = new BaseRes<>(true);
        if (!StringUtils.isBlank(req.getChannelCustomerId()) && !StringUtils.isBlank(req.getChannelCode())) {
            InfoByChannelResp detailInfo = infoService.getInfoByChannel(req);
            if (detailInfo == null) {
                result.setCode(AppCodeEnum._A001_USER_NOT_EXISTS.getCode());
                result.setMessage("会员信息不存在");
            } else {
                result.setData(detailInfo);
            }
        } else {
            result.setCode(AppCodeEnum._0001_ILLEGAL_PARAMETER.getCode());
            result.setMessage(AppCodeEnum._0001_ILLEGAL_PARAMETER.getMessage());
        }
        log.info("getInfoByChannel返回结果,result:{}", result);

        return result;
    }

    @ReadOnlyConnection
    @ApiOperation(value = "查询登录设备", httpMethod = "POST", produces = "application/json")
    @ApiResponse(code = 200, message = "success", response = BaseRes.class)
    @ResponseBody
    @RequestMapping(value = "/queryLoginDevice", method = RequestMethod.POST, produces = "application/json")
    public BaseRes<LoginDeviceDetail> queryLoginDevice(@RequestHeader(name = "customerId", required = false) String custId) {
        log.info("queryLoginDevice请求参数,custId{}", custId);

        BaseRes<LoginDeviceDetail> result = new BaseRes<LoginDeviceDetail>(true);
        String customerId = custId;
        if (StringUtils.isNumeric(customerId)) {
            LoginDeviceDetail detailInfo = deviceService.getLoginDevice(Long.valueOf(customerId));
            if (detailInfo == null) {
                result.setCode(AppCodeEnum._A001_USER_NOT_EXISTS.getCode());
                result.setMessage("会员登录设备信息不存在");
            } else {
                result.setData(detailInfo);
            }
        } else {
            result.setCode(AppCodeEnum._0001_ILLEGAL_PARAMETER.getCode());
            result.setMessage(AppCodeEnum._0001_ILLEGAL_PARAMETER.getMessage());
        }
        log.info("queryLoginDevice返回结果,result{}", result);

        return result;
    }

    @ReadOnlyConnection
    @ApiOperation(value = "机构会员列表查询", httpMethod = "POST", produces = "application/json")
    @ApiResponse(code = 200, message = "success", response = BaseRes.class)
    @ResponseBody
    @RequestMapping(value = "/orgList", method = RequestMethod.POST, produces = "application/json")
    public BaseRes<Page<OrgCustomerDetail>> orgList(@RequestBody CustCond custCond) {
        BaseRes<Page<OrgCustomerDetail>> result = new BaseRes<Page<OrgCustomerDetail>>(true);
        if (custCond == null || !custCond.isValid()) {
            result.setCode(AppCodeEnum._0001_ILLEGAL_PARAMETER.getCode());
            result.setMessage("参数错误：缺少页号和页大小");
        } else {
            Page<OrgCustomerDetail> pd = infoService.orgList(custCond);
            result.setData(pd);
        }
        return result;
    }

    @ApiOperation(value = "同步会员投资概要", httpMethod = "POST", produces = "application/json")
    @ApiResponse(code = 200, message = "success", response = BaseRes.class)
    @ResponseBody
    @RequestMapping(value = "/synCustInvestSummary", method = RequestMethod.POST, produces = "application/json")
    public BaseRes<Object> synCustInvestSummary(@RequestBody CustInvestSummaryReq req) {
        BaseRes<Object> result = new BaseRes<>();
        String customerId = req.getCustomerId();
        String isFresh = req.getBuyFreshProductStatus();
        String isInvestFix = req.getBuyFixedProductStatus();
        if (!StringUtils.isNumeric(customerId)) {
            result.setCode(AppCodeEnum._0001_ILLEGAL_PARAMETER.getCode());
            result.setMessage("参数错误：customerId传参不正确");
        } else if (!StringUtils.isNumeric(isFresh) && !StringUtils.isNumeric(isInvestFix)) {
            result.setCode(AppCodeEnum._0001_ILLEGAL_PARAMETER.getCode());
            result.setMessage("参数错误：是否投资新手标和是否投资定期不能均为空");
        } else {
            CustomerInfo ci = new CustomerInfo();
            ci.setCustomerId(Long.valueOf(customerId));
            if (StringUtils.isNumeric(isFresh)) {
                ci.setBuyFreshProductStatus(Integer.valueOf(isFresh));
            }
            if (StringUtils.isNumeric(isInvestFix)) {
                ci.setBuyFixedProductStatus(Integer.valueOf(isInvestFix));
            }
            infoService.updatePer(ci);
        }
        return result;
    }

    @ReadOnlyConnection
    @ApiOperation(value = "根据会员id查询txs会员Id", httpMethod = "POST", produces = "application/json")
    @ApiResponse(code = 200, message = "success", response = BaseRes.class)
    @ResponseBody
    @RequestMapping(value = "/queryTxsIdByCustomerId", method = RequestMethod.POST, produces = "application/json")
    public BaseRes<String> queryTxsIdByCustomerId(@RequestBody String customerId) {
        // TODO Auto-generated method stub
        BaseRes<String> result = new BaseRes<String>(true);
        if (customerId == null) {
            result.setCode(AppCodeEnum._0001_ILLEGAL_PARAMETER.getCode());
            result.setMessage(AppCodeEnum._0001_ILLEGAL_PARAMETER.getMessage());
            return result;
        }
        if (StringUtils.isNumeric(customerId)) {
            Long cusId = Long.parseLong(customerId);
            String txsId = infoService.queryTxsIDByCustomerId(cusId);
            result.setData(txsId);
        } else {
            result.setCode(AppCodeEnum._0001_ILLEGAL_PARAMETER.getCode());
            result.setMessage(AppCodeEnum._0001_ILLEGAL_PARAMETER.getMessage());
        }

        return result;
    }

    @JsonResponse
    @ResponseBody
    @ApiOperation(value = "余额信息", notes = "余额信息")
    @PostMapping(value = "/balanceInfo")
    public BaseRes<BalanceInfoRes> info(@RequestHeader("customerId") Long customerId) {
        BaseRes<BalanceInfoRes> res = new BaseRes<>(false);
        BigDecimal amt = this.infoService.queryBanlanceByCustomerId(customerId);
        BalanceInfoRes bi = new BalanceInfoRes();
        if (amt != null) {
            bi.setUsableAmount(MoneyUtil.convertToStandardMoneyStr(amt));
        }
        res.setData(bi);
        res.success();
        return res;
    }

    //    @ApiOperation(value = "证件信息扫描，用于活期账户开户", httpMethod = "POST", produces = "application/json")
//    @ApiResponse(code = 200, message = "success", response = BaseRes.class)
//    @ResponseBody
//    @RequestMapping(value = "/certificateScanning", method = RequestMethod.POST, produces = "application/json")

    public BaseRes<ORCInfo> certificateScanning(@RequestBody ImgReq imgReq, @RequestHeader(name = "customerId", required = false) String customerId) {
        String frontFile = imgReq.getFrontFile();
        String backFile = imgReq.getBackFile();
        BaseRes<ORCInfo> result = new BaseRes<ORCInfo>();
        if (StringUtils.isBlank(frontFile) || StringUtils.isBlank(backFile) || customerId == null || !StringUtils.isNumeric(customerId)) {
            result.setCode(AppCodeEnum._0001_ILLEGAL_PARAMETER.getCode());
            result.setMessage(AppCodeEnum._0001_ILLEGAL_PARAMETER.getMessage());
            return result;
        }
        try {
            ORCInfo orcInfo = ocrFileService.certificateScanning(frontFile, backFile, customerId);
            result.setData(orcInfo);
        } catch (AppException e) {
            result.setCode(e.getCode());
            result.setMessage(e.getMessage());
        }
        return result;
    }

    @ApiOperation(value = "查询会员可用支付方式", httpMethod = "POST", produces = "application/json", notes = "支付方式  1 银行卡支付 2余额支付 3活期支付")
    @ApiResponse(code = 200, message = "success", response = BaseRes.class)
    @ResponseBody
    @RequestMapping(value = "/queryAvlPayTypes", method = RequestMethod.POST, produces = "application/json")
    public BaseRes<List<PayType>> queryAvlPayTypes(@RequestHeader(name = "customerId", required = true) String customerId) {
        BaseRes<List<PayType>> result = new BaseRes<List<PayType>>(true);
        if (customerId == null) {
            result.setCode(AppCodeEnum._0001_ILLEGAL_PARAMETER.getCode());
            result.setMessage(AppCodeEnum._0001_ILLEGAL_PARAMETER.getMessage());
            return result;
        }
        if (StringUtils.isNumeric(customerId)) {
            Long cusId = Long.parseLong(customerId);
            List<PayType> payTypes = infoService.queryAvlPayTypes(cusId);
            result.setData(payTypes);
        } else {
            result.setCode(AppCodeEnum._0001_ILLEGAL_PARAMETER.getCode());
            result.setMessage(AppCodeEnum._0001_ILLEGAL_PARAMETER.getMessage());
        }
        return result;
    }

    @ApiOperation(value = "个人实名认证", httpMethod = "POST", produces = "application/json")
    @ApiResponse(code = 200, message = "success", response = BaseRes.class)
    @ResponseBody
    @RequestMapping(value = "/verifyMember", method = RequestMethod.POST, produces = "application/json")
    public BaseRes<JSONObject> verifyMember(@RequestHeader(name = "customerId") String customerId,
                                            @RequestBody @Valid MemberVerifyReq req) {
        BaseRes<JSONObject> result = new BaseRes<>();

        if (!StringUtils.isNumeric(customerId)) {
            result.setCode(AppCodeEnum._0001_ILLEGAL_PARAMETER.getCode());
            result.setMessage("参数非法：会员ID无效");
            return result;
        }

        // 装配参数
        req.setCustomerId(customerId);

        // 根据证件号判断会员是否存在
        CustomerInfo customerInfo = cardService.selectByPrimaryIden(req.getCertificateNo());

        // 校验手机号及身份证件
        result = infoService.validateVerifyMember(req, customerInfo);

        // 校验通过
        if (result.whetherSuccess()) {
            // 实名认证操作
            result = infoService.addMemberVerify(req);
        }
        return result;
    }

    @ApiOperation(value = "个人/机构会员开户", httpMethod = "POST", produces = "application/json")
    @ApiResponse(code = 200, message = "success", response = BaseRes.class)
    @ResponseBody
    @RequestMapping(value = "/openAccount", method = RequestMethod.POST, produces = "application/json")
    public BaseRes<OpenAccountRes> openAccount(@RequestBody @Valid OpenAccountReq req) {
        BaseRes<OpenAccountRes> result = new BaseRes<>();

        Object customerInfo = null;

        // 校验
        infoService.validateOpenAccount(req);

        // 根据会员类型获取会员信息
        switch (MemberType.getByCode(req.getMemberType())) {
            case PERSONAL:
                customerInfo = cardService.getCustomerInfo(req.getMemberId());
                break;
            case ORGANIZATION:
                customerInfo = infoService.getOrgMemberInfoByMemberId(req.getMemberId());
                break;
        }

        // 如果未找到会员信息，则返回
        if (customerInfo == null) {
            result.resetCode(AppCodeEnum._A001_USER_NOT_EXISTS);
            return result;
        }

        //开户
        result = infoService.openAccount(customerInfo, req);
        return result;
    }

    @ReadOnlyConnection
    @ApiOperation(value = "个人会员详情查询(脱敏)", httpMethod = "POST", produces = "application/json")
    @ApiResponse(code = 200, message = "success", response = BaseRes.class)
    @ResponseBody
    @RequestMapping(value = "/perDetailInfoSecret", method = RequestMethod.POST, produces = "application/json")
    public BaseRes<CustomerSecretDetail> perDetailInfo(@RequestBody CustomerQueryReq customerQueryReq) {
        log.info("perDetailInfoSecret请求参数,memberId:{};mobile{}", customerQueryReq.getMemberId(), customerQueryReq.getMobile());
        return infoService.getCustomerDetail(customerQueryReq.getMemberId(), customerQueryReq.getMobile());
    }

    @ApiOperation(value = "机构认证", httpMethod = "POST", produces = "application/json")
    @ApiResponse(code = 200, message = "success", response = BaseRes.class)
    @ResponseBody
    @RequestMapping(value = "/verifyOrgMember", method = RequestMethod.POST, produces = "application/json")
    public BaseRes<OrgMemberVerifyRes> verifyOrgMember(@RequestBody @Valid OrgMemberVerifyReq req) {
        BaseRes<OrgMemberVerifyRes> result = new BaseRes<>();

        if (!NumberUtils.isDigits(req.getOrgId()) || StringUtil.isBlank(req.getAuthentType())) {
            result.setCode(AppCodeEnum._A001_USER_NOT_EXISTS.getCode());
            result.setMessage("企业/机构会员ID或认证类型参数非法");
            return result;
        }

        // 根据手机号判断会员是否存在
        OrgCustomerInfo customerInfo = infoService.getOrgMemberInfoByMemberId(req.getOrgId());

        // 如果未找到会员信息，则返回
        if (customerInfo == null) {
            result.setCode(AppCodeEnum._A001_USER_NOT_EXISTS.getCode());
            result.setMessage("企业/机构会员不存在");
            return result;
        }
        // 可以重复认证
        // 机构认证操作
        result = infoService.addOrgMemberVerify(customerInfo, req);
        return result;
    }

    @ApiOperation(value = "查询机构认证结果", httpMethod = "POST", produces = "application/json")
    @ApiResponse(code = 200, message = "success", response = BaseRes.class)
    @ResponseBody
    @RequestMapping(value = "/orgMemberVerifyInfo", method = RequestMethod.POST, produces = "application/json")
    public BaseRes<OrgMemberVerifyRes> orgMemberVerifyInfo(@RequestBody @Valid OrgMemberVerifyReq req) {
        BaseRes<OrgMemberVerifyRes> result = new BaseRes<>();

        if (!NumberUtils.isDigits(req.getOrgId())) {
            result.setCode(AppCodeEnum._A001_USER_NOT_EXISTS.getCode());
            result.setMessage("企业/机构会员ID参数非法");
            return result;
        }

        // 根据机构会员Id判断会员是否存在
        OrgCustomerInfo customerInfo = infoService.getOrgMemberInfoByMemberId(req.getOrgId());

        // 如果未找到会员信息，则返回
        if (customerInfo == null) {
            result.setCode(AppCodeEnum._A001_USER_NOT_EXISTS.getCode());
            result.setMessage("企业/机构会员不存在");
            return result;
        } else if (CustomerConstants.IS_REAL_0 == customerInfo.getIsReal()) {
            // 若未认证过则直接返回
            result.setCode(AppCodeEnum._A203_ORG_MEMBER_QUERY_FAIL.getCode());
            result.setMessage("企业/机构会员还未认证过");
            return result;
        }
        // 机构认证操作
        result = infoService.getOrgMemberVerifyInfo(customerInfo, req);
        return result;
    }

    @ApiOperation(value = "查询机构会员信息", httpMethod = "POST", produces = "application/json")
    @ApiResponse(code = 200, message = "success", response = BaseRes.class)
    @ResponseBody
    @RequestMapping(value = "/orgMemberInfo", method = RequestMethod.POST, produces = "application/json")
    public BaseRes<OrgMemberInfoRes> orgMemberInfo(@RequestBody @Valid OrgMemberInfoReq req) {
        BaseRes<OrgMemberInfoRes> result = new BaseRes<>();

        // 根据系统标识判断会员是否存在
        OrgMemberInfoRes memberInfoRes = infoService.getOrgMemberInfoBySourceId(req.getSourceId());

        // 如果未找到会员信息，则返回
        if (memberInfoRes == null) {
            result.setCode(AppCodeEnum._A001_USER_NOT_EXISTS.getCode());
            result.setMessage("企业/机构会员信息不存在");
        } else {
            result.setData(memberInfoRes);
        }
        return result;
    }
}
