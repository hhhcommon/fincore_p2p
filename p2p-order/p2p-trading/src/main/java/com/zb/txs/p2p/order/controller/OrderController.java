package com.zb.txs.p2p.order.controller;


import com.google.common.base.Preconditions;
import com.zb.fincore.pms.facade.product.dto.req.QueryProductInfoRequest;
import com.zb.fincore.pms.facade.product.dto.req.QueryProductListRequestForP2P;
import com.zb.fincore.pms.facade.product.dto.resp.QueryProductInfoResponse;
import com.zb.fincore.pms.facade.product.model.ProductModel;
import com.zb.txs.foundation.monads.Try;
import com.zb.txs.p2p.business.enums.order.ResponseCodeEnum;
import com.zb.txs.p2p.business.invest.repose.InvestDetailResp;
import com.zb.txs.p2p.business.invest.repose.InvestmentRecordResp;
import com.zb.txs.p2p.business.invest.repose.OrderMatchResp;
import com.zb.txs.p2p.business.invest.request.*;
import com.zb.txs.p2p.business.order.request.AppointOrder;
import com.zb.txs.p2p.business.order.request.AppointVerify;
import com.zb.txs.p2p.business.order.response.*;
import com.zb.txs.p2p.business.order.response.pms.GenericQueryListResponse;
import com.zb.txs.p2p.business.product.request.ProductMemberInfo;
import com.zb.txs.p2p.investment.service.InvestmentService;
import com.zb.txs.p2p.order.httpclient.AmsClientConf;
import com.zb.txs.p2p.order.persistence.model.TradeOrder;
import com.zb.txs.p2p.order.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@Slf4j
@RestController
@RequestMapping("/p2p/trading/order")
public class OrderController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private InvestmentService investmentService;
    @Autowired
    private AmsClientConf.AmsClient amsClient;

    /**
     * 前端确认投资页接口
     *
     * @param productCode   p2p产品Id
     * @param memberId 会员信息
     * @return 前端产品当前可购额度、用户银行卡信息
     */
    @RequestMapping(value = "/{productCode}", method = RequestMethod.GET, produces = "application/json")
    public CommonResponse<ProductMemberInfo> confirmAppointPageInfo(@RequestHeader(name="customerId",required = false)String memberId, @PathVariable String productCode) {
        try {
            if (StringUtils.isEmpty(memberId)) {
                return CommonResponse.build(ResponseCodeEnum.RESPONSE_PARAM_FAIL.getCode(), "获取会员信息失败", null);
            }
            if (ObjectUtils.isEmpty(productCode)) {
                return CommonResponse.build(ResponseCodeEnum.RESPONSE_PARAM_FAIL.getCode(), "接口参数验证失败", null);
            }
            log.info("confirmAppointPageInfo请求参数,productCode:{};memberId{}", productCode, memberId);
            Try<ProductMemberInfo> tryResult = orderService.confirmAppointPageInfo(productCode, memberId);
            log.info("confirmAppointPageInfo返回结果,tryResult:{}", tryResult.successValue());
            if (tryResult.isSuccess()) {
                return CommonResponse.success(tryResult.successValue());
            }

            return CommonResponse.build(ResponseCodeEnum.RESPONSE_FAIL.getCode(), tryResult.failureException().getMessage().split(": ")[1], null);
        } catch (Exception e) {
            log.error("系统异常:", e);
            return CommonResponse.failure();
        }
    }

    /**
     * 订单验证码
     *
     * @param appointVerify 购买金额
     * @param memberId 会员信息
     * @return 金核验证码流水号和手机号
     */
    @PostMapping("/verify")
    public CommonResponse<AppointVerifyResp> verifyAppoint(@RequestBody AppointVerify appointVerify, @RequestHeader(name="customerId",required = false)String memberId) {
        try {
            if (StringUtils.isEmpty(memberId)) {
                return CommonResponse.build(ResponseCodeEnum.RESPONSE_PARAM_FAIL.getCode(), "获取会员信息失败", null);
            }
            if (Objects.isNull(appointVerify)) {
                return CommonResponse.build(ResponseCodeEnum.RESPONSE_PARAM_FAIL.getCode(), "接口参数验证失败", null);
            }
            appointVerify.setMemberId(memberId);

            log.info("verifyAppoint请求参数,appointVerify:{}", appointVerify);
            Try<AppointVerifyResp> tryResult = orderService.verifyAppoint(appointVerify);
            log.info("verifyAppoint返回结果,tryResult:{}", tryResult.successValue());
            if (tryResult.isSuccess()) {
                return CommonResponse.success(tryResult.successValue());
            }
            return CommonResponse.build(ResponseCodeEnum.RESPONSE_FAIL.getCode(), tryResult.failureException().getMessage().split(": ")[1], null);
        } catch (Exception e) {
            log.error("系统异常:", e);
            return CommonResponse.failure();
        }
    }

    /**
     * 订单
     *
     * @param appointOrder  金核验证码预约流水号、购买金额、手机验证码
     * @param memberId 会员信息
     * @return 下单成功、验证失败
     */
    @PostMapping("/order")
    public CommonResponse<AppointmentSuccess> orderAppoint(@RequestBody AppointOrder appointOrder, @RequestHeader(name="customerId",required = false)String memberId) {
        try {
            if (StringUtils.isEmpty(memberId)) {
                return CommonResponse.build(ResponseCodeEnum.RESPONSE_PARAM_FAIL.getCode(), "获取会员信息失败", null);
            }
            if (Objects.isNull(appointOrder)) {
                return CommonResponse.build(ResponseCodeEnum.RESPONSE_PARAM_FAIL.getCode(), "接口参数验证失败", null);
            }
            appointOrder.setMemberId(memberId);
            log.info("orderAppoint请求参数,appointOrder:{}", appointOrder);

            return orderService.orderAppoint(appointOrder);
        } catch (Exception e) {
            log.error("系统异常:", e);
            return CommonResponse.failure();
        }
    }

    /**
     * 查询用户的持仓列表
     *
     * @param req
     * @param memberId 会员信息
     * @return
     */
    @RequestMapping(value = "/holdassetslist", method = RequestMethod.POST)
    public CommonResponse<HoldAssetsResp> getHoldAssetsList(HttpServletRequest request, @RequestBody InvestmentRecordRequest req, @RequestHeader(name="customerId",required = false)String memberId) {
        try {
            request.setAttribute("cat-page-uri", "/p2p/order/trading/holdassetslist");
            if (StringUtils.isEmpty(memberId)) {
                return CommonResponse.build(ResponseCodeEnum.RESPONSE_PARAM_FAIL.getCode(), "获取会员信息失败", null);
            }
            if (ObjectUtils.isEmpty(req.getLastId())) {
                return CommonResponse.build(ResponseCodeEnum.RESPONSE_PARAM_FAIL.getCode(), "接口参数验证失败", null);
            }
            log.info("getHoldAssetsList请求参数,lastId:{};memberId{}", req.getLastId(), memberId);

            req.setMemberId(memberId);
            req.setLastId(req.getLastId());
            return orderService.getHoldAssetsList(req);
        } catch (Exception e) {
            log.error("系统异常:", e);
            return CommonResponse.failure();
        }
    }

    /**
     * 交易记录查询MSD
     *
     * @param investmentRecordRequest
     * @param memberId 会员信息
     * @return
     */
    @RequestMapping(value = "/orderList", method = RequestMethod.POST)
    public CommonResponse<InvestmentRecordResp> orderList(@RequestBody InvestmentRecordRequest investmentRecordRequest, @RequestHeader(name="customerId",required = false)String memberId) {
        try {
            if (StringUtils.isEmpty(memberId)) {
                return CommonResponse.build(ResponseCodeEnum.RESPONSE_PARAM_FAIL.getCode(), "获取会员信息失败", null);
            }
            investmentRecordRequest.setMemberId(memberId);
            log.info("orderList请求参数,investmentRecordRequest{}", investmentRecordRequest);

            return investmentService.getInvestmentRecord(investmentRecordRequest);
        } catch (Exception e) {
            log.error("系统异常:", e);
            return CommonResponse.failure();
        }
    }

    /**
     * 前端查询投资详情接口
     *
     * @param investmentRequest
     * @return 投资详情
     */
    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    public CommonResponse<InvestDetailResp> getInvestDetail(@RequestBody InvestmentRequest investmentRequest, @RequestHeader(name="customerId",required = false)String memberId) {
        try {
            Preconditions.checkNotNull(investmentRequest, "请求实体investmentRequest为空");
            Preconditions.checkNotNull(investmentRequest.getProductCode(), "产品编码为空");
            Preconditions.checkNotNull(investmentRequest.getOrderId(), "订单ID为空");
            Preconditions.checkNotNull(memberId, "用户ID为空");
        } catch (NullPointerException e) {
            log.warn("请求参数错误: {}", e.getMessage());
            return CommonResponse.build(ResponseCodeEnum.RESPONSE_PARAM_FAIL.getCode(), e.getMessage(), null);
        }

        try {
            investmentRequest.setMemberId(memberId);
            log.info("getInvestDetail请求的参数,investmentRequest:{}", investmentRequest);

            Try<InvestDetailResp> tryResult = investmentService.getInvestmentDetail(investmentRequest);
            log.info("getInvestDetail返回的参数,InvestDetailResp:{}", tryResult.successValue());

            if (tryResult.isSuccess()) {
                return CommonResponse.success(tryResult.successValue());
            }
            return CommonResponse.build(ResponseCodeEnum.RESPONSE_FAIL.getCode(), tryResult.failureException().getMessage(), null);
        } catch (Exception e) {
            log.error("系统异常:", e);
            return CommonResponse.failure();
        }
    }

    /**
     * 前端查询资产详情接口
     *
     * @param assetDetailRequest
     * @return 资产详情
     */
    @RequestMapping(value = "/assetDetail", method = RequestMethod.POST)
    public CommonResponse<OrderMatchResp> getAssetDetail(@RequestBody AssetDetailRequest assetDetailRequest, @RequestHeader(name="customerId",required = false)String memberId) {
        try {
            Preconditions.checkNotNull(assetDetailRequest, "请求实体investmentRequest为空");
            Preconditions.checkNotNull(assetDetailRequest.getOrderId(), "订单ID为空");
            Preconditions.checkNotNull(memberId, "用户ID为空");
        } catch (NullPointerException e) {
            log.warn("请求参数错误: {}", e.getMessage());
            return CommonResponse.build(ResponseCodeEnum.RESPONSE_PARAM_FAIL.getCode(), e.getMessage(), null);
        }

        try {
            log.info("getAssetDetail请求参数,assetDetailRequest:{}", assetDetailRequest);

            return orderService.getAssetDetail(assetDetailRequest);

        } catch (Exception e) {
            log.error("系统异常:", e);
            return CommonResponse.failure();
        }
    }


    /**
     * 前端查询出借人数接口
     *
     * @param investmentRequest
     * @return 投资详情
     */
    @RequestMapping(value = "/loanCount", method = RequestMethod.POST)
    public CommonResponse<LoanCountResp> getLoanCount(@RequestBody InvestmentRequest investmentRequest) {
        try {
            Preconditions.checkNotNull(investmentRequest, "请求实体investmentRequest为空");
            Preconditions.checkNotNull(investmentRequest.getProductCode(), "产品编码为空");
        } catch (NullPointerException e) {
            log.warn("请求参数错误: {}", e.getMessage());
            return CommonResponse.build(ResponseCodeEnum.RESPONSE_PARAM_FAIL.getCode(), e.getMessage(), null);
        }
        log.info("loanCount请求参数,investmentRequest:{}", investmentRequest);

        return orderService.getLoanCount(investmentRequest.getProductCode());

    }

    /**
     * 前端查询支付状态接口
     *
     * @param investmentRequest
     * @return 支付状态
     */
    @RequestMapping(value = "/getPayResult", method = RequestMethod.POST)
    public CommonResponse<PayResultResp> getPayResult(@RequestBody InvestmentRequest investmentRequest, @RequestHeader(name="customerId",required = false)String memberId) {
        if (Objects.isNull(investmentRequest) || StringUtils.isEmpty(investmentRequest.getOrderId())) {
            return CommonResponse.build(ResponseCodeEnum.RESPONSE_PARAM_FAIL.getCode(), "订单编号为空", null);
        }
        if (StringUtils.isEmpty(memberId)) {
            return CommonResponse.build(ResponseCodeEnum.RESPONSE_PARAM_FAIL.getCode(), "会员编号为空", null);
        }

        try {
            log.info("getPayResult请求参数,investmentRequest:{}", investmentRequest);

            TradeOrder recordPara = new TradeOrder();
            recordPara.setRegisterId(Long.parseLong(investmentRequest.getOrderId()));
            recordPara.setMemberId(memberId);
            return orderService.getPayResult(recordPara);
        } catch (Exception e) {
            log.error("系统异常:", e);
            return CommonResponse.failure();
        }
    }

    /**
     * 查询产品资产关系
     *
     * @param productLoanRelationReq
     * @param memberId
     * @return
     */
    @RequestMapping(value = "/queryProductLoanRelationList", method = RequestMethod.POST)
    public ProxyResp queryProductLoanRelationList(@RequestBody ProductLoanRelationReq productLoanRelationReq, @RequestHeader(name="customerId",required = false)String memberId) {
        try {
//            Preconditions.checkNotNull(memberId, "用户ID为空");
//            log.info("queryProductLoanRelationList请求参数,productLoanRelationReq:{}", productLoanRelationReq);

            return amsClient.queryProductLoanRelationList(productLoanRelationReq);
        } catch (NullPointerException e) {
            log.warn("请求参数错误: {}", e.getMessage());
            return ProxyResp.builder().respCode(ResponseCodeEnum.RESPONSE_PARAM_FAIL.getCode()).respCode("参数校验失败").build();
        } catch (Exception e) {
            log.error("查询资管系统错误", e);
            return ProxyResp.builder().respCode("9999").respMsg("查询错误").build();
        }
    }

    /**
     * 查询在售/售罄产品列表（第一页）缓存接口
     *
     * @param req
     */
    @RequestMapping(value = "/queryCacheProductListForP2PApp", method = RequestMethod.POST)
    public GenericQueryListResponse<ProductModel> queryCacheProductListForP2PApp(@RequestBody QueryProductListRequestForP2P req) {
        try {
            Preconditions.checkNotNull(req, "请求实体QueryProductListRequestForP2P为空");
        } catch (NullPointerException e) {
            log.warn("请求参数错误: {}", e.getMessage());
            return new GenericQueryListResponse(ResponseCodeEnum.RESPONSE_PARAM_FAIL.getCode(), e.getMessage());
        }
        log.info("queryCacheProductListForP2PApp请求参数,req:{}", req);

        return orderService.queryCacheProductListForP2PApp(req);

    }

    /**
     * 产品详情
     *
     * @param req
     */
    @RequestMapping(value = "/queryProductInfo", method = RequestMethod.POST)
    public QueryProductInfoResponse queryProductInfo(@RequestBody QueryProductInfoRequest req) {
        try {
            Preconditions.checkNotNull(req, "请求实体QueryProductInfoRequest为空");
        } catch (NullPointerException e) {
            log.warn("请求参数错误: {}", e.getMessage());
            return new QueryProductInfoResponse(ResponseCodeEnum.RESPONSE_PARAM_FAIL.getCode(), e.getMessage(), null);
        }
        log.info("queryProductInfo请求参数,req:{}", req);

        return orderService.queryProductInfo(req);

    }

    /**
     * 在售/售罄产品列表查询（供唐小僧用）
     *
     * @param req
     */
    @RequestMapping(value = "/queryProductListForP2PApp", method = RequestMethod.POST)
    public GenericQueryListResponse<ProductModel> queryProductListForP2PApp(@RequestBody QueryProductListRequestForP2P req) {
        try {
            Preconditions.checkNotNull(req, "请求实体QueryProductListRequestForP2P为空");
//            Preconditions.checkNotNull(memberId, "用户ID为空");
        } catch (NullPointerException e) {
            log.warn("请求参数错误: {}", e.getMessage());
            return new GenericQueryListResponse(ResponseCodeEnum.RESPONSE_PARAM_FAIL.getCode(), e.getMessage());
        }
        log.info("queryProductListForP2PApp请求参数,req:{}", req);

        return orderService.queryProductListForP2PApp(req);

    }

    /**
     * 前端查询用户分类资产信息
     *
     * @param memberId 会员信息
     * @return 同类产品的资产总额，利息
     */
    @RequestMapping(value = "/queryMemberCategoryInvestInfo", method = RequestMethod.GET, produces = "application/json")
    public CommonResponse<MemberInvestInfoResp> queryMemberCategoryInvestInfo(@RequestHeader(name="customerId")String memberId) {
        try {
            if (StringUtils.isEmpty(memberId)) {
                return CommonResponse.build(ResponseCodeEnum.RESPONSE_PARAM_FAIL.getCode(), "获取会员信息失败", null);
            }
            return orderService.queryMemberCategoryInvestInfo(memberId);
        } catch (Exception e) {
            log.error("系统异常:", e);
            return CommonResponse.failure();
        }
    }


    /**
     * 独立理财段查询投资记录接口（投资人数点击进去）
     *
     * @param investPageReq
     */
    @RequestMapping(value = "/investRecord", method = RequestMethod.POST)
    public CommonResponse<PageQueryResp> investRecord(@RequestBody InvestPageReq investPageReq) {
        try {
            Preconditions.checkNotNull(investPageReq, "请求实体InvestPageReq为空");
            Preconditions.checkNotNull(investPageReq.getProductCode(), "产品编号为空");
            Preconditions.checkNotNull(investPageReq.getPageNo(), "页码为空");
        } catch (NullPointerException e) {
            log.warn("请求参数错误: {}", e.getMessage());
            return CommonResponse.build(ResponseCodeEnum.RESPONSE_PARAM_FAIL.getCode(), e.getMessage(), null);
        }

        try {
            log.info("investRecord请求参数,investPageReq:{}", investPageReq);

            return orderService.investRecord(investPageReq);
        } catch (Exception e) {
            log.error("系统异常:", e);
            return CommonResponse.failure();
        }
    }

}
