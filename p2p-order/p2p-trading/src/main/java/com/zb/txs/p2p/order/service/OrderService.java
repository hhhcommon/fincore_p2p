package com.zb.txs.p2p.order.service;

import com.alibaba.fastjson.JSON;
import com.zb.fincore.common.utils.NumberUtils;
import com.zb.fincore.pms.common.dto.BaseResponse;
import com.zb.fincore.pms.common.enums.ChangeProductStockTypeEnum;
import com.zb.fincore.pms.common.enums.P2PProductCollectStatusEnum;
import com.zb.fincore.pms.facade.product.dto.req.*;
import com.zb.fincore.pms.facade.product.dto.resp.ChangeProductStockResponse;
import com.zb.fincore.pms.facade.product.dto.resp.FreezeProductStockResponse;
import com.zb.fincore.pms.facade.product.dto.resp.QueryProductInfoResponse;
import com.zb.fincore.pms.facade.product.dto.resp.UnFreezeProductStockResponse;
import com.zb.fincore.pms.facade.product.model.ProductModel;
import com.zb.fincore.pms.facade.product.model.ProductPeriodModel;
import com.zb.fincore.pms.facade.product.model.ProductProfitModel;
import com.zb.fincore.pms.facade.product.model.ProductStockModel;
import com.zb.p2p.customer.api.InfoAPI;
import com.zb.p2p.customer.api.entity.CustomerCardBin;
import com.zb.p2p.customer.api.entity.CustomerDetail;
import com.zb.p2p.customer.api.entity.CustomerReq;
import com.zb.p2p.customer.common.model.BaseRes;
import com.zb.txs.foundation.legacy.DateUtil;
import com.zb.txs.foundation.monads.Try;
import com.zb.txs.foundation.response.Result;
import com.zb.txs.members.common.utils.SensitiveInfoUtils;
import com.zb.txs.p2p.business.enums.HoldAssetsStatusEnum;
import com.zb.txs.p2p.business.enums.P2PSourceIdEnum;
import com.zb.txs.p2p.business.enums.TradeStatusEnum;
import com.zb.txs.p2p.business.enums.order.*;
import com.zb.txs.p2p.business.invest.repose.InvestPageResp;
import com.zb.txs.p2p.business.invest.repose.OrderMatchResp;
import com.zb.txs.p2p.business.invest.repose.TradeQueryResp;
import com.zb.txs.p2p.business.invest.request.AssetDetailRequest;
import com.zb.txs.p2p.business.invest.request.InvestPageReq;
import com.zb.txs.p2p.business.invest.request.InvestmentRecordRequest;
import com.zb.txs.p2p.business.invest.request.TradeQueryRequest;
import com.zb.txs.p2p.business.order.exception.OrderException;
import com.zb.txs.p2p.business.order.request.*;
import com.zb.txs.p2p.business.order.response.*;
import com.zb.txs.p2p.business.order.response.pms.GenericQueryListResponse;
import com.zb.txs.p2p.business.product.repose.BankQuotaResp;
import com.zb.txs.p2p.business.product.request.ProductCutDayRecord;
import com.zb.txs.p2p.business.product.request.ProductMemberInfo;
import com.zb.txs.p2p.code.CodeEnum;
import com.zb.txs.p2p.investment.httpclient.InvestmentClient;
import com.zb.txs.p2p.investment.util.BeanUtils;
import com.zb.txs.p2p.investment.util.Page;
import com.zb.txs.p2p.order.httpclient.*;
import com.zb.txs.p2p.order.persistence.mapper.OperationRecordMapper;
import com.zb.txs.p2p.order.persistence.mapper.TradeOrderMapper;
import com.zb.txs.p2p.order.persistence.model.OperationRecord;
import com.zb.txs.p2p.order.persistence.model.TradeOrder;
import com.zb.txs.p2p.util.DesensitizeUtil;
import com.zb.txs.p2p.util.DistributedSerialNoService;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import retrofit2.Call;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.SocketTimeoutException;
import java.util.*;

/**
 * Created by liguoliang on 2017/9/22.
 */
@Service
@Slf4j
public class OrderService {
    @Autowired
    private TradeOrderMapper tradeOrderMapper;
    @Autowired
    private OperationRecordMapper operationRecordMapper;
    @Autowired
    private OrderClient orderClient;
    @Autowired
    private TradeprocessClient tradeprocessClient;
    @Autowired
    private OrderServiceTransaction orderServiceTransaction;
    @Autowired
    private OrderServiceRetry orderServiceRetry;
    @Autowired
    private TxsProductClient txsProductClient;
    @Autowired
    private InvestmentClient investmentClient;
    @Autowired
    private PmsClient pmsClient;
    @Autowired
//    private MemberClient memberClient;
    private InfoAPI memberClient;

    @Autowired
    private DistributedSerialNoService distributedSerialNoService;

    public static final String PAY_SOURCEID = "MSD";
    public static final String SUCCESS = "SUCCESS";
    public static final String FAIL = "FAIL";
    public static final String ORDER_TYPE = "ORDER";

    /**
     * 前端确认投资页接口
     *
     * @param productCode p2p产品Id
     * @param memberId  会员Id
     * @return 前端产品当前可购额度、用户银行卡信息
     */
    public Try<ProductMemberInfo> confirmAppointPageInfo(final String productCode, final String memberId) {
        return getStockAmount(productCode)
                .flatMap(stockAmount -> getMembersCard(memberId)
                        .flatMap(card -> getCardLimit(card)
                                .map(bank -> buildFrom(stockAmount, card, bank))));

    }

    /**
     * 拿到天鼋产品剩余份额
     */
    private Try<BigDecimal> getStockAmount(final String productCode) {
        try {
            QueryProductInfoRequest req = new QueryProductInfoRequest();
            req.setProductCode(productCode);
            QueryProductInfoResponse pmsResp = queryProductInfo(req);
            ProductModel productModel = pmsResp.getProductModel();

            ProductStockModel productStockModel = productModel.getProductStockModel();
            BigDecimal stockAmount = productStockModel.getStockAmount();
            if (stockAmount == null) {
                return Try.failure("产品剩余份额未找到");
            }
            return Try.success(stockAmount);
        } catch (Throwable t) {
            log.error("getStockAmount error", t);
            return Try.failure("获取产品剩余份额异常");
        }

    }

    /**
     * 拿到会员绑卡信息
     */
    public Try<CardsResponse> getMembersCard(final String memberId) {
        try {
            CustomerReq req = new CustomerReq();
            req.setCustomerId(memberId);
            log.info("bankCardBindInfo请求参数,bankCardBindInfo:{}", JSON.toJSONString(req));
            final BaseRes<CustomerCardBin> result = memberClient.bankCardBindInfo(req);
            log.info("bankCardBindInfo返回的结果,bankCardBindInfo:{}", JSON.toJSONString(result));
            if (result == null || !ResponseCodeEnum.RESPONSE_SUCCESS.getCode().equals(result.getCode())) {
                return Try.failure(result.getMessage());
            }

            CardsResponse cardsResponse = new CardsResponse();
            CustomerCardBin customerCardBin = result.getData();
            cardsResponse.setBankCode(customerCardBin.getBankCode());
            cardsResponse.setCardNo(customerCardBin.getBankCardNo());
            cardsResponse.setPhoneNo(customerCardBin.getBankMobile());
            cardsResponse.setBankName(customerCardBin.getBankName());
            if (StringUtils.isEmpty(cardsResponse.getBankCode())) {
                return Try.failure("获取银行编码异常");
            }
            String cardNo = cardsResponse.getCardNo();
            if (StringUtils.isEmpty(cardNo) || cardNo.length() < 4) {
                return Try.failure("获取银行卡号异常");
            }
            if (StringUtils.isEmpty(cardsResponse.getPhoneNo())) {
                return Try.failure("获取手机号异常");
            }
            cardsResponse.setCardNo(cardNo.substring(cardNo.length() - 4));
            cardsResponse.setSignId(customerCardBin.getSignId());
            cardsResponse.setIdCardNo(customerCardBin.getIdCardNo());
            cardsResponse.setIdCardName(customerCardBin.getIdCardName());
//            CardsResponse cardsResponse = new CardsResponse();
//            cardsResponse.setCardNo("1122");
            return Try.success(cardsResponse);
        } catch (Throwable t) {
            log.error("get cardResponse error", t);
            return Try.failure("获取会员绑卡信息异常");
        }
    }

    /**
     * 个人信息查询
     */
    public CustomerDetail getMembersInfo(final String memberId) {
        CustomerDetail customerDetail = null;
        try {
            CustomerReq req = new CustomerReq();
            req.setCustomerId(memberId);
            log.info("perDetailInfo请求参数,perDetailInfo:{}", JSON.toJSONString(req));
            final BaseRes<CustomerDetail> result = memberClient.perDetailInfo(req);
            log.info("perDetailInfo返回的结果,perDetailInfo:{}", JSON.toJSONString(result));
            if (result == null || !ResponseCodeEnum.RESPONSE_SUCCESS.getCode().equals(result.getCode())) {
                log.error("perDetailInfo code exception:" + result.getCode());
                throw new RuntimeException("获取会员绑卡信息异常");
            }

            customerDetail = result.getData();
        } catch (Throwable t) {
            log.error("get getMembersInfo error", t);
        }
        return customerDetail;
    }



    /**
     * 拿到银行卡限额
     */
    private Try<BankQuotaResp> getCardLimit(final CardsResponse cardsResponse) {
        try {
            BankQuota bankQuota = BankQuota.builder().bankCode(cardsResponse.getBankCode()).sourceId(PAY_SOURCEID).build();
            final Result<List<BankQuotaResp>> result = orderClient.queryBankQuota(bankQuota).execute().body();
            final Result.Code code = result.getCode();
            if (CodeEnum.RESPONSE_SUCCESS.equals(code)) {
                List<BankQuotaResp> bankQuotaRespList = result.getData();
                if (!CollectionUtils.isEmpty(bankQuotaRespList)) {
                    BankQuotaResp resultResponse = result.getData().get(0);
                    return Try.success(resultResponse);
                }
                return Try.failure("获取银行卡限额为空");
            }
            log.error("queryBankQuota code exception:" + code);
        } catch (Throwable e) {
            log.error("getCardLimit error", e);
        }
        return Try.failure("获取银行卡限额异常");
    }

    /**
     * 组合信息返回给前台
     *
     * @param remaining 剩余金额
     * @param card      银行卡信息
     * @param bank      银行卡限额
     */
    private ProductMemberInfo buildFrom(final BigDecimal remaining, final CardsResponse card, final BankQuotaResp bank) {
        return ProductMemberInfo.builder()
                .bankName(card.getBankName())
                .cardTailNumber(card.getCardNo())
                .bankDayLimit(bank.getPayDayMax())
                .bankSingleLimit(bank.getPayMax())
                .remainAmount(remaining.toString()).build();
    }

    /**
     * 预约订单验证码
     *
     * @param appointVerify 会员id、账户id和购买金额
     * @return 金核验证码预约流水号和手机号
     */
    public Try<AppointVerifyResp> verifyAppoint(final AppointVerify appointVerify) {
        try {
            final String memberId = appointVerify.getMemberId();
            final String productCode = appointVerify.getProductCode();
            BigDecimal tradeAmount = new BigDecimal(appointVerify.getAmount());
            if (ObjectUtils.isEmpty(tradeAmount) || tradeAmount.compareTo(BigDecimal.ZERO) <= 0
                    || ObjectUtils.isEmpty(productCode) ) {
                return Try.failure("接口参数验证失败");
            }

            //获取产品信息
            QueryProductInfoRequest req = new QueryProductInfoRequest();
            req.setProductCode(productCode);
            QueryProductInfoResponse pmsResp = queryProductInfo(req);
            ProductModel productModel = pmsResp.getProductModel();
            ProductPeriodModel productPeriodModel = productModel.getProductPeriodModel();
            ProductProfitModel productProfitModel = productModel.getProductProfitModel();
            ProductStockModel productStockModel = productModel.getProductStockModel();

            //判断产品剩余份额
            BigDecimal stockAmount = productStockModel.getStockAmount();
            log.info("verifyAppoint剩余库存:productCode:{},tradeAmount:{},stockAmount:{}", productCode, tradeAmount, stockAmount);

            if (stockAmount.compareTo(tradeAmount) < 0) {
                log.info("stockAmount not enough,stockAmount:{},amountBigDecimal:{}", stockAmount, tradeAmount);
                return Try.failure("产品剩余份额不足");
            }
            //如果出现尾标(产品剩余可出借金额低于起投金额)，购买的金额需为产品剩余可出借金额
            BigDecimal amountMax = productProfitModel.getMaxInvestAmount();
            BigDecimal amountMin = productProfitModel.getMinInvestAmount();
            if (stockAmount.compareTo(amountMin) < 0) {
                if (tradeAmount.compareTo(stockAmount) != 0) {
                    log.info("出现尾标:memberId:{},tradeAmount:{},stockAmount:{}", appointVerify.getMemberId(), tradeAmount, stockAmount);
                    return Try.failure("购买金额应该为剩余金额");
                }
            } else {
                //校验步长
                Integer step = productProfitModel.getIncreaseAmount().intValue();
                if ((tradeAmount.intValue() - amountMin.intValue()) % step != 0) {
                    log.info("校验步长:memberId:{},tradeAmount:{}", appointVerify.getMemberId(), tradeAmount);
                    return Try.failure("购买金额减去起投金额应该是" + step + "的倍数");
                }

                //判断起投金额、限额
                if (tradeAmount.compareTo(amountMax) > 0 || tradeAmount.compareTo(amountMin) < 0) {
                    return Try.failure("订单金额应该不低于" + amountMin + "，并且不高于" + amountMax);
                }
            }

            final BigDecimal formateAmount = new BigDecimal(tradeAmount.toString());
            final String orderId = String.valueOf(distributedSerialNoService.getLongSeqNo());
            return getMembersCard(memberId)
                    .flatMap(cardsResponse -> appointInvest(formateAmount, memberId, cardsResponse.getSignId(), orderId)
                            .map(appointInvestResp -> buildAppointVerifyRespFrom(cardsResponse.getPhoneNo(), orderId)));
        } catch (Throwable t) {
            log.error("verifyAppoint error", t);
            return Try.failure("获取验证码异常，请稍后重试");
        }
    }

    /**
     * 金核预投资发送短信
     */
    private Try<TradeStatusResp> appointInvest(final BigDecimal tradeAmount, final String memberId, final String signId, final String orderId) {
        try {
            AppointInvest appointInvest = new AppointInvest();
            appointInvest.setMemberId(memberId);
            appointInvest.setOrderNo(orderId);
            appointInvest.setOrderTime(System.currentTimeMillis());
            appointInvest.setSignId(signId);
            appointInvest.setTradeAmount(tradeAmount);
            appointInvest.setSourceId(PAY_SOURCEID);

            final Result<TradeStatusResp> result = orderClient.appointInvest(appointInvest).execute().body();

            TradeStatusEnum tradeStatusEnum = orderServiceRetry.checkReponseState(result);
            if (TradeStatusEnum.S.equals(tradeStatusEnum) || TradeStatusEnum.P.equals(tradeStatusEnum)) {
                TradeStatusResp appointInvestResp = result.getData();
                return Try.success(appointInvestResp);
            }
            return Try.failure(StringUtils.hasText(result.getMsg()) ? result.getMsg() : "获取验证码异常，请稍后重试");
        } catch (Throwable t) {
            throw new RuntimeException("appointInvest error");
        }
    }

    /**
     * 组合给前台点击获取验证码页面数据
     */
    private AppointVerifyResp buildAppointVerifyRespFrom(String phoneNo, String orderId) {
        AppointVerifyResp appointVerifyResp = new AppointVerifyResp();
        appointVerifyResp.setOrderId(orderId);
        appointVerifyResp.setPhoneNo(SensitiveInfoUtils.mobilePhone(phoneNo));
        return appointVerifyResp;
    }

    /**
     * 订单
     *
     * @param appointOrder 会员id、金核验证码预约流水号、购买金额、手机验证码
     * @return 下单成功、系统错误、库存不足、下单支付失败、下单登记失败、交易处理中
     */
    public CommonResponse<AppointmentSuccess> orderAppoint(final AppointOrder appointOrder) {
        try {
            final String orderId = appointOrder.getOrderId();
            final String memberId = appointOrder.getMemberId();
            final String productCode = appointOrder.getProductCode();
            final String verificationCode = appointOrder.getVerificationCode();
            final String productCategory = appointOrder.getProductCategory();

            final BigDecimal tradeAmount = new BigDecimal(appointOrder.getAmount());
            //判断请求参数是否完整
            if (ObjectUtils.isEmpty(verificationCode) || ObjectUtils.isEmpty(orderId)
                    || ObjectUtils.isEmpty(memberId) || ObjectUtils.isEmpty(productCode)
                    || tradeAmount.compareTo(BigDecimal.ZERO) <= 0
                    || (!ObjectUtils.isEmpty(productCategory) && !ProductCategoryEnum.validateType(productCategory)) ) {
                return CommonResponse.build(ResponseCodeEnum.RESPONSE_PARAM_FAIL.getCode(), "下单提交参数错误", null);
            }

            //第一步：防重校验
            appointOrder.setRegisterId(distributedSerialNoService.getLongSeqNo());//新增登记订单流水号 modify by 20180120
            if (!SUCCESS.equals(saveOperationRecord(appointOrder))) {
                return CommonResponse.build(ResponseCodeEnum.RESPONSE_PARAM_FAIL.getCode(), "订单重复", null);
            }
            //第二步：下单产品前置检查
            Try<Void> productCheck = checkProductPreOrder(productCode, tradeAmount);
            if (productCheck.isSuccess()) {
                //第三步：插入投资记录
                if (!SUCCESS.equals(saveTradeRecord(appointOrder))) {
                    return CommonResponse.build(ResponseCodeEnum.RESPONSE_FAIL.getCode(), "下单登记失败", null);
                }
                //第四步：冻结产品库存
                freezeProductStock(productCode, tradeAmount, String.valueOf(appointOrder.getRegisterId()));
                //第五步：确认投资支付
                return confirmInvest(appointOrder);
            }
            return CommonResponse.build(ResponseCodeEnum.RESPONSE_PARAM_FAIL.getCode(), productCheck.failureException().getMessage().split(": ")[1], null);
        } catch (Throwable t) {
            log.error("orderAppoint error", t);
            return CommonResponse.build(ResponseCodeEnum.RESPONSE_FAIL.getCode(), "下单登记异常", null);
        }
    }

    /**
     * 下单前校验产品
     *
     * @param productCode       产品编号
     * @param tradeAmount        购买金额
     * @return
     */
    private Try<Void> checkProductPreOrder(String productCode, BigDecimal tradeAmount) {
        try {
            //获取产品信息
            QueryProductInfoRequest req = new QueryProductInfoRequest();
            req.setProductCode(productCode);
            QueryProductInfoResponse pmsResp = queryProductInfo(req);
            ProductModel productModel = pmsResp.getProductModel();
            ProductStockModel productStockModel = productModel.getProductStockModel();

            //产品募集状态(10:待募集，11:募集期，12:募集完成)
            int collectStatus = productModel.getCollectStatus();
            // 产品销售状态(10:待部署, 11:已部署，12:上线, 13:下线, 14:归档)
            int saleStatus = productModel.getSaleStatus();
            if (P2PProductCollectStatusEnum.PRODUCT_COLLECT_STATUS_COLLECTING.getCode() != collectStatus
                    || P2PProductCollectStatusEnum.PRODUCT_COLLECT_STATUS_RAISE_COMPLETE.getCode() != saleStatus) {
                return Try.failure("产品已售罄");
            }

            //判断产品份额是否足够
            BigDecimal stockAmount = productStockModel.getStockAmount();
            log.info("checkProductPreOrder剩余库存:productCode:{},stockAmount:{}", productCode, stockAmount);

            if (stockAmount.compareTo(tradeAmount) < 0) {
                log.info("stockAmount not enough,stockAmount:{},tradeAmount:{}", stockAmount, tradeAmount);
                return Try.failure("产品剩余份额不足");
            }
            return Try.success(null);
        } catch (Exception e) {
            log.error("checkProductPreOrder error", e);
            return Try.failure("校验产品信息异常");
        }
    }

    /**
     * 入库order
     */
    public String saveTradeRecord(final AppointOrder appointOrder) {
        //生成trade_order
        TradeOrder record = new TradeOrder();
        record.setId(Long.parseLong(appointOrder.getOrderId()));
        record.setRegisterId(appointOrder.getRegisterId());
        record.setMemberId(appointOrder.getMemberId());
        record.setProductCode(appointOrder.getProductCode());
        record.setInvestAmount(new BigDecimal(appointOrder.getAmount()));
        record.setMatchStatus(MatchStatusEnum.INIT.getCode());
        record.setProductCategory(appointOrder.getProductCategory());
        record.setCategoryName(ProductCategoryEnum.getTypeDesc(appointOrder.getProductCategory()));
        //入库记录表
        int insertResult = insertTradeOrder(record);
        if (insertResult == 1) {
            return SUCCESS;
        } else {
            return FAIL;
        }
    }

    public String saveOperationRecord(final AppointOrder appointOrder) {
        //入库操作记录表
        OperationRecord operationRecord = new OperationRecord();
        operationRecord.setMemberId(appointOrder.getMemberId());
        operationRecord.setReferId(appointOrder.getOrderId());
        operationRecord.setOperationType(ORDER_TYPE);
        int operationResult = operationRecordMapper.insertSelective(operationRecord);
        if (operationResult == 1) {
            return SUCCESS;
        } else {
            return FAIL;
        }
    }

    /**
     * 确认投资支付
     *
     * @param appointOrder
     * @return
     */
    private CommonResponse<AppointmentSuccess> confirmInvest(final AppointOrder appointOrder) {
        ConfirmInvest confirmInvest = new ConfirmInvest();
        confirmInvest.setMemberId(appointOrder.getMemberId());
        confirmInvest.setOrderNo(appointOrder.getRegisterId().toString());
        confirmInvest.setOrderTime(System.currentTimeMillis());
        confirmInvest.setOriginalOrderNo(appointOrder.getOrderId());
        confirmInvest.setSmsCode(appointOrder.getVerificationCode());
        confirmInvest.setSourceId(PAY_SOURCEID);
        TradeOrder tradeOrder = new TradeOrder();
        tradeOrder.setId(Long.parseLong(appointOrder.getOrderId()));
        tradeOrder.setRegisterId(appointOrder.getRegisterId());
        tradeOrder.setMemberId(appointOrder.getMemberId());
        tradeOrder.setProductCode(appointOrder.getProductCode());
        tradeOrder.setInvestAmount(new BigDecimal(appointOrder.getAmount()));
        try {
            //金核支付
            Result<TradeStatusResp> appointInvestRespResult = orderClient.confirmInvest(confirmInvest).execute().body();
            if (appointInvestRespResult != null) {
                TradeStatusEnum tradeStatusEnum = orderServiceRetry.checkReponseState(appointInvestRespResult);
                tradeOrder.setPayNo(appointInvestRespResult.getData().getPayNo());
                tradeOrder.setPayCode(appointInvestRespResult.getCode().getCode());
                tradeOrder.setPayMsg(appointInvestRespResult.getMsg());
//            if (TradeStatusEnum.S.equals(tradeStatusEnum)) {
//                return appointmentAfterPaySuccess(tradeOrder, productAndBaseResp, false);
//            }
                if (TradeStatusEnum.P.equals(tradeStatusEnum)) {
                    return appointmentTradeProcessing(tradeOrder);
                } else {
                    appointmentTradeFailure(tradeOrder, appointInvestRespResult.getMsg());
                }
            } else {
                appointmentTradeFailure(tradeOrder, appointInvestRespResult.getMsg());
            }
        } catch (SocketTimeoutException se) {
            //网络超时，返回支付中状态
            log.error("pay time out", se);
            appointmentTradeProcessing(tradeOrder);
        } catch (OrderException o) {
            log.error("OrderException", o);
            return CommonResponse.build(o.getCode(), o.getMessage(), null);
        } catch (Throwable t) {
            log.error("confirmInvest exception", t);
            return CommonResponse.build(CodeEnum.RESPONSE_FAIL.getCode(), "确认投资支付异常", null);
        }
        return null;
    }

    /**
     * 预约单支付成功后，后续金核下单
     */
    public CommonResponse<AppointmentSuccess> appointmentAfterPaySuccess(TradeOrder tradeOrder) {
        //进行库存确认操作和预约单更新
        //占用产品库存
        changeProductStock(tradeOrder.getProductCode(), tradeOrder.getInvestAmount(), String.valueOf(tradeOrder.getRegisterId()));
        //预约单更新
        orderServiceTransaction.updateProductStockWhenPaySuccess(tradeOrder);

        //拿到会员绑卡信息
        Try<CardsResponse> cardsResponseTry = getMembersCard(tradeOrder.getMemberId());
        if (cardsResponseTry.isSuccess()) {
            CardsResponse cardInfo = cardsResponseTry.successValue();
            //调用金核预约申请
            return orderReservation(tradeOrder, appointReservation(cardInfo, tradeOrder));
        }
        log.error("get memberInfo exception,appointment is" + tradeOrder.getId());
        return CommonResponse.success(new AppointmentSuccess(tradeOrder.getId().toString(), new DateTime().toString("yyyy-MM-dd"), AppointmentSuccess.OrderRespEnum.ORDER_FAILURE.name()));
    }

    public AppointReservation appointReservation(CardsResponse cardInfo, TradeOrder tradeOrder) {
        AppointReservation appointReservation = new AppointReservation();
        appointReservation.setCertNo(cardInfo.getIdCardNo());
        appointReservation.setName(cardInfo.getIdCardName());
        appointReservation.setExtOrderNo(String.valueOf(tradeOrder.getId()));
        appointReservation.setMemberId(tradeOrder.getMemberId());
        appointReservation.setProductCode(tradeOrder.getProductCode());
        appointReservation.setSaleChannel(P2PSourceIdEnum.MSD.name());
        appointReservation.setOrderTime(new Date());
        appointReservation.setInvestAmount(tradeOrder.getInvestAmount().setScale(2, BigDecimal.ROUND_DOWN));
        appointReservation.setTelNo(cardInfo.getPhoneNo());
        return appointReservation;
    }

    /**
     * 预约单支付处理中
     */
    private CommonResponse<AppointmentSuccess> appointmentTradeProcessing(TradeOrder tradeOrder) {
        //支付处理中，状态改为"支付处理中"
        TradeOrder tradeOrder3 = new TradeOrder();
        tradeOrder3.setId(tradeOrder.getId());
        tradeOrder3.setPayNo(tradeOrder.getPayNo());
        tradeOrder3.setPayStatus(PayStatusEnum.PAYING.getCode());
        tradeOrder3.setPayCode(tradeOrder.getPayCode());
        tradeOrder3.setPayMsg(tradeOrder.getPayMsg());
        //更新订单状态记录
        orderServiceTransaction.appointmentProcessWhenPay(tradeOrder3);
        return CommonResponse.success(new AppointmentSuccess(tradeOrder.getRegisterId().toString(), new DateTime().toString("yyyy-MM-dd"), AppointmentSuccess.OrderRespEnum.PAY_PROCESSING.name()));
    }

    /**
     * 预约单支付失败
     */
    public void appointmentTradeFailure(TradeOrder tradeOrder, String msg) throws OrderException {
        log.info("失败库存释放:tradeOrder:{},msg:{}",tradeOrder,msg);

        orderServiceTransaction.roolBackProductStock(tradeOrder);
        //解冻库存
        unFreezeProductStock(tradeOrder.getProductCode(), String.valueOf(tradeOrder.getRegisterId()));
        //TODO 库存回滚失败，通知出来
        log.error("confirmInvest status error, product stock will roolback:registerId:{},msg:{}",tradeOrder.getRegisterId(),msg);
        throw OrderException.builder().code(CodeEnum.PAY_FAIL.getCode()).message(msg == null ? "订单支付失败" : msg).build();
    }

    /**
     * 处理金核产品申请回复
     *
     * @param tradeOrder
     * @param appointReservation
     */
    public CommonResponse<AppointmentSuccess> orderReservation(TradeOrder tradeOrder, AppointReservation appointReservation) {
        try {
            Result<AppointReservationResp> reservationRespResult = tradeprocessClient.orderReservation(appointReservation).execute().body();
            if (reservationRespResult != null) {
                String code = reservationRespResult.getCode().getCode();
                if (CodeEnum.RESPONSE_SUCCESS.getCode().equals(code)) {
                    TradeOrder tradeOrder2 = new TradeOrder();
                    tradeOrder2.setId(tradeOrder.getId());
                    //金核确认预约，状态改为"匹配中"
                    tradeOrder2.setMatchStatus(MatchStatusEnum.MATCHING.getCode());
                    tradeOrderMapper.updateByPrimaryKeySelective(tradeOrder2);

                    //获取产品信息
                    QueryProductInfoRequest req = new QueryProductInfoRequest();
                    req.setProductCode(tradeOrder.getProductCode());
                    QueryProductInfoResponse pmsResp = queryProductInfo(req);
                    ProductModel productModel = pmsResp.getProductModel();
                    ProductStockModel productStockModel = productModel.getProductStockModel();
                    //下单产品前置检查
                    BigDecimal stockAmount = productStockModel.getStockAmount();
                    log.info("orderReservation剩余库存:productCode:{},stockAmount:{}", tradeOrder.getProductCode(), stockAmount);
                    if (stockAmount.compareTo(new BigDecimal(0)) == 0 &&
                            productStockModel.getSaleAmount().compareTo(productModel.getTotalAmount()) == 0) {
                        //通知产品售罄
                        noticeProductStockSellout(tradeOrder.getProductCode());
                        List<String> productCodes = new ArrayList<>();
                        productCodes.add(tradeOrder.getProductCode());
                        ProductCutDayRecord productReq = new ProductCutDayRecord();
                        productReq.setSerialNo(tradeOrder.getId().toString());
                        productReq.setProductCodes(productCodes);
                        callProductServiceSoldOut(productReq);
                    }
                    log.info("下单登记成功:{}", tradeOrder.getId().toString());
                    return CommonResponse.success(null);
                }
            }

            throw new RuntimeException("下单登记失败:{}" + reservationRespResult);
            //金核确认预约失败，状态改为"预约确认失败"
            //TODO 监控这状态报警出来   后续重试
//            tradeOrder.setMatchStatus("MATCH_FAIL");
//            updateAppointmentState(tradeOrder.getId(), "MATCH_FAIL", "INIT");
//            throw new RuntimeException("orderReservation error code :" + code);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            //返回给前端200，后续job去重试
            TradeOrder tradeOrder2 = new TradeOrder();
            tradeOrder2.setId(tradeOrder.getId());
            //金核确认预约，状态改为"匹配中"
            tradeOrder2.setMatchStatus(MatchStatusEnum.ORDER_FAIL.getCode());
            tradeOrderMapper.updateByPrimaryKeySelective(tradeOrder2);
            log.info("支付成功但下单登记失败,后续job重试,id:{}", tradeOrder.getId());
            return CommonResponse.success(new AppointmentSuccess(tradeOrder.getId().toString(), new DateTime().toString("yyyy-MM-dd"), AppointmentSuccess.OrderRespEnum.ORDER_FAILURE.name()));
        }
    }

    /**
     * 调用天鼋产品服务售罄产品
     *
     * @param productCode
     */
    public void noticeProductStockSellout(String productCode) {
        try {
            NoticePmsReq noticePmsReq = new NoticePmsReq();
            noticePmsReq.setProductCode(productCode);
            log.info("调用天鼋产品售罄服务请求的参数:{}", noticePmsReq.toString());
            BaseResponse noticeProductResult = pmsClient.noticeProductStockSellout(noticePmsReq).execute().body();
            if (!CodeEnum.RESPONSE_SUCCESS.getCode().equals(noticeProductResult.getRespCode())) {
                log.error("noticeProductStockSellout-productCode:{}更新产品状态失败，result:{}", productCode, noticeProductResult.toString());
            }
        } catch (Exception ex) {
            log.error("noticeProductStockSellout售罄产品Code:{}出错：{}", productCode, ex);
        }
    }

    /**
     * 调用TXS产品服务售罄产品
     *
     * @param req
     */
    public void callProductServiceSoldOut(ProductCutDayRecord req) {
        try {
            log.info("调用TXS产品售罄服务请求的参数:{}", req);
            Result<Void> result = txsProductClient.soldOutProduct(req).execute().body();
            if (!result.success()) {
                log.error("callProductServiceSoldOut更新产品状态失败，result:{}", result.toString());
            }
        } catch (Exception ex) {
            log.error("callProductServiceSoldOut售罄产品出错：{}", ex);
        }
    }

    /**
     * 插入订单记录表
     *
     * @param record 预约记录对象
     * @return 成功或失败
     */
    public int insertTradeOrder(TradeOrder record) {
        int result = 0;
        try {
            result = tradeOrderMapper.insertSelective(record);
        } catch (Throwable t) {
            log.error("insertTradeOrder insert faild", t);
        }
        return result;
    }

    /**
     * 更新记录状态
     *
     * @param appointmentId 记录对象id
     * @param targetState   目标状态
     * @param currentState  当前状态
     * @return 成功或失败
     */
    public int updateAppointmentState(Long appointmentId, String targetState, String currentState) {
        int result = 0;
        try {
            result = tradeOrderMapper.updateMatchStatus(appointmentId, targetState, currentState);
        } catch (Throwable t) {
            log.error("tradeOrder update state faild", t);
        }
        return result;
    }

    /**
     * 查询用户的持仓列表
     *
     * @param reqPara
     * @return
     */
    public CommonResponse<HoldAssetsResp> getHoldAssetsList(InvestmentRecordRequest reqPara) {
        log.info("根查询用户的持仓列表信息,！req:{}", reqPara);
        String memberIdStr = reqPara.getMemberId();
        Long lastId = reqPara.getLastId();
        if (StringUtils.isEmpty(memberIdStr)) {
            HoldAssetsResp holdAssetsResp = HoldAssetsResp.builder()
                    .totalAssets("0")
                    .totalActivity("0")
                    .totalProfit("0")
                    .list(new ArrayList<>()).build();
            return CommonResponse.success(holdAssetsResp);
        }

        if (lastId == null || lastId <= 0) {
            reqPara.setLastId(Long.MAX_VALUE);
        }
        Long pageSize = reqPara.getPageSize();
        if (pageSize == null || pageSize <= 0) {
            reqPara.setPageSize(Long.parseLong("10"));
        }

        Long memberId = Long.valueOf(memberIdStr);
        try {
            HoldAssetsResp holdAssetsResp = HoldAssetsResp.builder()
                    .totalActivity("0")
                    .totalAssets("0")
                    .totalProfit("0").build();
            List<TradeOrder> list = tradeOrderMapper.selectOrderListByLastId(reqPara);
            if (list == null || list.size() <= 0) {
                holdAssetsResp.setList(new ArrayList<>());
                return CommonResponse.success(holdAssetsResp);
            }
            holdAssetsResp.setTotalActivity(list.size()+"");
            List<HoldAssetsListResp> respList = new ArrayList<>();
            //查询总本金和已兑付收益
            TradeQueryRequest tradeQueryRequest = new TradeQueryRequest();
            tradeQueryRequest.setMemberId(memberIdStr);
            Call<Result<TradeQueryResp>> call = investmentClient.queryAccountAndHistoryIncome(tradeQueryRequest);
            Result<TradeQueryResp> tradeQueryResp = call.execute().body();
            if (tradeQueryResp == null || !ResponseCodeEnum.RESPONSE_SUCCESS.getCode().equals(tradeQueryResp.getCode().getCode()) || tradeQueryResp.getData() == null) {
                log.error("查询交易总本金和已兑付收益接口错误，请求参数：{}", tradeQueryRequest);
                holdAssetsResp.setTotalAssets("0");
                holdAssetsResp.setTotalProfit("0");
            } else {
                holdAssetsResp.setTotalAssets(tradeQueryResp.getData().getInvestAmount() == null ? "0" : tradeQueryResp.getData().getInvestAmount().toString());
                holdAssetsResp.setTotalProfit(tradeQueryResp.getData().getIncome() == null ? "0" : tradeQueryResp.getData().getIncome().toString());
            }

            for (TradeOrder item : list) {
                //查询产品相关信息
                //获取产品信息
                QueryProductInfoRequest req = new QueryProductInfoRequest();
                req.setProductCode(item.getProductCode());
                QueryProductInfoResponse pmsResp = queryProductInfo(req);
                ProductModel productModel = pmsResp.getProductModel();
                ProductPeriodModel productPeriodModel = productModel.getProductPeriodModel();

                //查询预期收益
                String profit = "0";
                TradeQueryRequest tradeQueryProfit = TradeQueryRequest.builder().orderNo(item.getId().toString()).build();
                Call<Result<TradeQueryResp>> callProfit = investmentClient.queryOrderIncome(tradeQueryProfit);
                try {
                    Result<TradeQueryResp> tradeQueryRespProfit = callProfit.execute().body();
                    if (tradeQueryRespProfit == null || !tradeQueryRespProfit.getCode().getCode().equals(ResponseCodeEnum.RESPONSE_SUCCESS.getCode()) || tradeQueryRespProfit.getData() == null) {
                        log.error("查询交易预期收益接口错误，请求参数：{}", tradeQueryRequest);
                    } else {
                        profit = tradeQueryRespProfit.getData().getIncome() == null ? "0" : tradeQueryRespProfit.getData().getIncome().toString();
                    }
                } catch (IOException e) {
                    log.error("查询交易预期收益接口错误:", e);
                }
                HoldAssetsListResp model = HoldAssetsListResp.builder()
                        .orderId(item.getId().toString())
                        .productCode(item.getProductCode())
                        .productTitle(productModel.getProductName())
                        .investAmount(item.getInvestAmount() == null ? "" : item.getInvestAmount().toString())
                        .matchAmount(item.getMatchAmount() == null ? "" : item.getMatchAmount().toString())
                        .expireProfit(profit)
                        .expireDay(DateUtil.dateToString(productPeriodModel.getExpectExpireTime(), "yyyy-MM-dd"))
                        .limitMatchDay(DateUtil.dateToString(item.getCreateTime(), "yyyy-MM-dd"))
                        .surplusDay(DateUtil.getMargin(new Date(), productPeriodModel.getExpectClearTime())<0 ? 0+"" : DateUtil.getMargin(new Date(), productPeriodModel.getExpectClearTime())+"")
                        .investmentDay(DateUtil.dateToString(item.getCreateTime(), "yyyy-MM-dd"))
                        .hisFlag(item.getHisFlag())
                        .status(HoldAssetsStatusEnum.MATCHING.getValue()).build();

                if (CashStatusEnum.CASH_SUCCESS.getCode().equals(item.getCashStatus())) {
                    model.setStatus(HoldAssetsStatusEnum.CASH_SUCCESS.getValue());
                }else if (CashStatusEnum.CASHING.getCode().equals(item.getCashStatus())){
//                } else if (DateUtil.dateToString(new Date(), "yyyyMMdd").compareTo(DateUtil.dateToString(productPeriodModel.getExpectExpireTime(), "yyyyMMdd")) > 0
//                        || DateUtil.dateToString(new Date(), "yyyyMMdd").compareTo(DateUtil.dateToString(productPeriodModel.getExpectExpireTime(), "yyyyMMdd")) == 0) {
                    //回款中
                    model.setStatus(HoldAssetsStatusEnum.CASHING.getValue());
                } else if (item.getMatchAmount().compareTo(BigDecimal.ZERO) == 1
                        && MatchStatusEnum.MATCH_SUCCESS.getCode().equals(item.getMatchStatus())) {
                    //持有中 匹配金额>0&&收益=0&&已经日切
                    model.setStatus(HoldAssetsStatusEnum.HOLDING.getValue());
                } else if (PayStatusEnum.PAY_FAIL.getCode().equals(item.getPayStatus())) {
                    //支付失败
                    model.setStatus(HoldAssetsStatusEnum.PAY_FAIL.getValue());
                } else if (PayStatusEnum.PAYING.getCode().equals(item.getPayStatus())) {
                    //支付中 支付中的金额>0&&没有日切
                    model.setStatus(HoldAssetsStatusEnum.PAYING.getValue());
                } else if (MatchStatusEnum.MATCH_FAIL.getCode().equals(item.getMatchStatus())) {
                    //极端异常情况
                    model.setStatus(HoldAssetsStatusEnum.MATCH_FAIL.getValue());
                } else if (MatchStatusEnum.INVEST_FAIL.getCode().equals(item.getMatchStatus())) {
                    //极端异常情况
                    model.setStatus(HoldAssetsStatusEnum.INVEST_FAIL.getValue());
                }
                respList.add(model);
            }
            holdAssetsResp.setList(respList);

            log.info("getHoldAssetsList返回结果,holdAssetsResp:{}", holdAssetsResp);

            return CommonResponse.success(holdAssetsResp);
        } catch (Exception ex) {
            log.error("查询用户的持仓列表信息异常，memberId：{},ex:{}", memberId, ex);
            return CommonResponse.failure();
        }
    }


    /**
     * 前端查询出借人数接口
     *
     * @param productCode
     * @return
     */
    public CommonResponse<LoanCountResp> getLoanCount(String productCode) {
        log.info("查询出借人数接口,！productCode:{}", productCode);
        try {
            int investCount = tradeOrderMapper.contByMatchStatus(productCode);

            LoanCountResp loanCountResp = new LoanCountResp();
            loanCountResp.setInvestCount(investCount + "");
            log.info("getLoanCount返回结果,loanCountResp:{}", loanCountResp);
            return CommonResponse.success(loanCountResp);
        } catch (Exception ex) {
            log.error("查询出借人数接口异常，productCode：{},ex:{}", productCode, ex);
            return CommonResponse.failure();
        }
    }

    /**
     * 前端查询支付状态接口
     *
     * @param recordPara
     * @return
     */
    public CommonResponse<PayResultResp> getPayResult(TradeOrder recordPara) {
        log.info("查询支付状态接口,！recordPara:{}", recordPara);
        try {
            TradeOrder orderResp = tradeOrderMapper.selectByOrderId(recordPara);

            PayResultResp payResultResp = new PayResultResp();
            if (orderResp != null) {
                payResultResp.setOrderId(orderResp.getRegisterId().toString());
                payResultResp.setPayCode(orderResp.getPayCode());
                payResultResp.setPayMsg(orderResp.getPayMsg());
                payResultResp.setPayNo(orderResp.getPayNo());
                payResultResp.setPayStatus(orderResp.getPayStatus());

                log.info("getPayResult返回结果,payResultResp:{}", payResultResp);
                return CommonResponse.success(payResultResp);
            } else {
                return CommonResponse.build(ResponseCodeEnum.RESPONSE_NOT_FUND.getCode(), ResponseCodeEnum.RESPONSE_NOT_FUND.getDesc(), null);
            }
        } catch (Exception ex) {
            log.error("查询支付状态接口异常，recordPara：{},ex:{}", recordPara, ex);
            return CommonResponse.failure();
        }
    }

    /**
     * 订单支付回调
     *
     * @param orderCallBackReq
     * @return
     */
    public CommonResponse<Object> payNotifyOrder(OrderCallBackReq orderCallBackReq) {
        CommonResponse<Object> respPar = OrderServiceParamsDetector.verifyOrderNotify(orderCallBackReq);
        if (!ResponseCodeEnum.RESPONSE_SUCCESS.getCode().equals(respPar.getCode())) {
            return respPar;
        }
        try {
            log.info("payNotifyOrder请求参数,orderCallBackReq:{}", orderCallBackReq);

            TradeOrder recordPara = new TradeOrder();
            recordPara.setId(Long.parseLong(orderCallBackReq.getOriginalOrderNo()));
            recordPara.setMemberId(orderCallBackReq.getMemberId());
//            recordPara.setInvestAmount(new BigDecimal(orderCallBackReq.getInvestAmount()));
            TradeOrder tradeOrderResult = tradeOrderMapper.selectByIdAndMemberIdAndAmount(recordPara);
            if (tradeOrderResult == null) {
                log.error("订单数据不存在。parameter:{}", orderCallBackReq.toString());
                return CommonResponse.build(ResponseCodeEnum.RESPONSE_NOT_FUND.getCode(), "订单数据不存在", null);
            }

            tradeOrderResult.setPayNo(orderCallBackReq.getPayNo());
            tradeOrderResult.setPayCode(orderCallBackReq.getPayCode());
            tradeOrderResult.setPayMsg(orderCallBackReq.getPayMsg());
            if (TradeStatusEnum.S.getValue().equals(orderCallBackReq.getPayStatus())) {
                appointmentAfterPaySuccess(tradeOrderResult);
            } else if (TradeStatusEnum.F.getValue().equals(orderCallBackReq.getPayStatus())) {
                appointmentTradeFailure(tradeOrderResult, "订单支付失败");
            } else {
                log.info("支付状态异常。");
                return CommonResponse.build(ResponseCodeEnum.RESPONSE_FAIL.getCode(), "订单支付回调异常,支付状态错误。", null);
            }

            return CommonResponse.success(null);
        } catch (Exception ex) {
            log.error("订单支付回调异常，ex:{},parameter:{}",
                    ex,
                    Objects.isNull(orderCallBackReq) ? "null" : orderCallBackReq.toString());
            return CommonResponse.build(ResponseCodeEnum.RESPONSE_FAIL.getCode(), "订单支付回调异常，请重试。", null);
        }
    }

    /**
     * 匹配通知
     */
    public void notifyOrder(List<NotifyOrder> notifyOrders) throws Exception {
        //更新状态
        for (NotifyOrder notifyOrder : notifyOrders) {
            if (OrderTypeEnum.MATCH.getCode().equals(notifyOrder.getType())) {
                TradeOrder tradeOrder1 = new TradeOrder();
                tradeOrder1.setId(Long.parseLong(notifyOrder.getExtOrderNo()));
                tradeOrder1.setTradeNo(notifyOrder.getTradeNo());
                tradeOrder1.setMatchAmount(notifyOrder.getAmount());
                tradeOrder1.setMatchStatus(notifyOrder.getStatus());
                tradeOrder1.setMatchTime(new Date());
                tradeOrderMapper.updateByPrimaryKeySelective(tradeOrder1);
            } else if (OrderTypeEnum.CASH.getCode().equals(notifyOrder.getType())) {
                TradeOrder tradeOrder2 = new TradeOrder();
                tradeOrder2.setId(Long.parseLong(notifyOrder.getExtOrderNo()));
                tradeOrder2.setCashStatus(notifyOrder.getStatus());
                tradeOrder2.setCashTime(new Date());
                tradeOrder2.setCashAmount(notifyOrder.getAmount());
                tradeOrderMapper.updateByPrimaryKeySelective(tradeOrder2);
            }
        }
    }

    /**
     * 日切售罄产品(天鼋产品调用)
     */
    public CommonResponse<Object> soldOutProductNotify(ProductCutDayRecord productCutDayRecord) {
        try {
            //验证参数
            CommonResponse<Object> respPar = OrderServiceParamsDetector.validateParameters(productCutDayRecord);
            if (!ResponseCodeEnum.RESPONSE_SUCCESS.getCode().equals(respPar.getCode())) {
                return respPar;
            }
            productCutDayRecord.setSerialNo(String.valueOf(distributedSerialNoService.getLongSeqNo()));

            callProductServiceSoldOut(productCutDayRecord);
//            for (String productCode : productCutDayRecord.getProductCodes()) {
//                callProductServiceSoldOut(productCode);
//            }

        } catch (Exception ex) {
            log.error("日切更新产品参数:{}状态出错：{}", productCutDayRecord.toString(), ex);
            return CommonResponse.build(ResponseCodeEnum.RESPONSE_FAIL.getCode(), "日切回调产品售罄处理出错，请重试。", null);
        }
        return CommonResponse.success(null);
    }

    public GenericQueryListResponse<ProductModel> queryCacheProductListForP2PApp(QueryProductListRequestForP2P req) {
        try {
            Call<GenericQueryListResponse<ProductModel>> call = pmsClient.queryCacheProductListForP2PApp(req);
            GenericQueryListResponse<ProductModel> pmsResp = call.execute().body();
            if (pmsResp == null || !ResponseCodeEnum.RESPONSE_SUCCESS.getCode().equals(pmsResp.getCode())) {
                log.error("查询pms:queryCacheProductListForP2PApp接口错误，请求参数：{}", req.toString());
                throw new RuntimeException("查询pms接口错误");
            }

            return pmsResp;
        } catch (Exception t) {
            log.error("queryCacheProductListForP2PApp错误", t);
            return new GenericQueryListResponse(ResponseCodeEnum.RESPONSE_FAIL.getCode(), ResponseCodeEnum.RESPONSE_FAIL.getDesc());
        }
    }

    public QueryProductInfoResponse queryProductInfo(QueryProductInfoRequest req) {
        try {
            log.info("queryProductInfo请求参数,queryProductInfo:{}", JSON.toJSONString(req));
            Call<QueryProductInfoResponse> call = pmsClient.queryProductInfo(req);
            QueryProductInfoResponse pmsResp = call.execute().body();
            log.info("queryProductInfo返回的结果,queryProductInfo:{}", JSON.toJSONString(pmsResp));

            if (pmsResp == null || !ResponseCodeEnum.RESPONSE_SUCCESS.getCode().equals(pmsResp.getRespCode())) {
                throw new RuntimeException("查询pms产品详情接口错误");
            }

            return pmsResp;
        } catch (Exception t) {
            log.error("queryProductInfo错误", t);
            return new QueryProductInfoResponse(ResponseCodeEnum.RESPONSE_FAIL.getCode(), ResponseCodeEnum.RESPONSE_FAIL.getDesc());
        }
    }

    public GenericQueryListResponse<ProductModel> queryProductListForP2PApp(QueryProductListRequestForP2P req) {
        try {
            Call<GenericQueryListResponse<ProductModel>> call = pmsClient.queryProductListForP2PApp(req);
            GenericQueryListResponse<ProductModel> pmsResp = call.execute().body();
            if (pmsResp == null || !ResponseCodeEnum.RESPONSE_SUCCESS.getCode().equals(pmsResp.getCode())) {
                log.error("查询pms:queryProductListForP2PApp接口错误，请求参数：{}", req.toString());
                throw new RuntimeException("查询pms接口错误");
            }

            return pmsResp;
        } catch (Exception t) {
            log.error("queryProductListForP2PApp错误", t);
            return new GenericQueryListResponse(ResponseCodeEnum.RESPONSE_FAIL.getCode(), ResponseCodeEnum.RESPONSE_FAIL.getDesc());
        }
    }

    public CommonResponse<OrderMatchResp> getAssetDetail(AssetDetailRequest assetDetailRequest) {
        try {
            assetDetailRequest.setExtOrderNo(assetDetailRequest.getOrderId());
            Call<Result<OrderMatchResp>> call = investmentClient.queryOrderMatchInfo(assetDetailRequest);
            Result<OrderMatchResp> assetDetailResp = call.execute().body();
            if (assetDetailResp == null || assetDetailResp.getData() == null ||
                    !ResponseCodeEnum.RESPONSE_SUCCESS.getCode().equals(assetDetailResp.getCode().getCode())) {
                log.error("根据订单编号查资产详情接口错误，请求参数：{}", assetDetailRequest);
                throw new RuntimeException("根据订单编号查资产详情接口返回异常");
            }

            return CommonResponse.success(assetDetailResp.getData());
        } catch (Exception t) {
            log.error("getAssetDetail错误", t);
            return CommonResponse.failure();
        }
    }


    /**
     * 调用产品冻结接口
     *
     * @return
     */
    private void freezeProductStock(String productCode, BigDecimal amount, String orderId) {
        try {
            FreezeProductStockRequest req = new FreezeProductStockRequest();
            req.setProductCode(productCode);
            req.setChangeAmount(amount);
            req.setRefNo(orderId);
            log.info("调用pms接口freezeProductStock，请求参数{}", JSON.toJSONString(req));
            Call<FreezeProductStockResponse> call = pmsClient.freezeProductStock(req);
            FreezeProductStockResponse pmsResp = call.execute().body();
            log.info("调用pms接口freezeProductStock,响应结果{}", JSON.toJSONString(pmsResp));

            if (pmsResp == null || !ResponseCodeEnum.RESPONSE_SUCCESS.getCode().equals(pmsResp.getRespCode())) {
                log.error("调用pms接口freezeProductStock接口错误，请求参数：{}", req.toString());
                throw new RuntimeException("调用pms接口freezeProductStock接口错误");
            }
        } catch (Exception e) {
            log.error("调用产品冻结库存接口失败", e);
            throw new RuntimeException("调用产品冻结库存接口失败");
        }
    }

    /**
     * 调用产品解冻接口
     *
     * @return
     */
    private void unFreezeProductStock(String productCode, String orderId) {
        try {
            UnFreezeProductStockRequest req = new UnFreezeProductStockRequest();
            req.setProductCode(productCode);
            req.setRefNo(orderId);
            log.info("调用pms接口unFreezeProductStock，请求参数{}", JSON.toJSONString(req));
            Call<UnFreezeProductStockResponse> call = pmsClient.unFreezeProductStock(req);
            UnFreezeProductStockResponse pmsResp = call.execute().body();
            log.info("调用pms接口unFreezeProductStock,响应结果{}", JSON.toJSONString(pmsResp));

            if (pmsResp == null || !ResponseCodeEnum.RESPONSE_SUCCESS.getCode().equals(pmsResp.getRespCode())) {
                log.error("调用pms接口unFreezeProductStock接口错误，请求参数：{}", req.toString());
                throw new RuntimeException("调用pms接口unFreezeProductStock接口错误");
            }
        } catch (Exception e) {
            log.error("调用产品解冻库存接口失败", e);
            throw new RuntimeException("调用产品解冻库存接口失败");
        }
    }

    /**
     * 占用产品库存接口
     *
     * @return
     */

    private void changeProductStock(String productCode, BigDecimal amount, String orderId) {
        try {
            ChangeProductStockForP2PRequest req = new ChangeProductStockForP2PRequest();
            req.setRefNo(orderId);
            req.setChangeAmount(amount);
            req.setProductCode(productCode);
            req.setChangeType(ChangeProductStockTypeEnum.OCCUPY.getCode());
            log.info("调用pms接口changeProductStock，请求参数{}", JSON.toJSONString(req));
            Call<ChangeProductStockResponse> call = pmsClient.changeProductStock(req);
            ChangeProductStockResponse pmsResp = call.execute().body();
            log.info("调用pms接口changeProductStock,响应结果{}", JSON.toJSONString(pmsResp));

            if (pmsResp == null || !ResponseCodeEnum.RESPONSE_SUCCESS.getCode().equals(pmsResp.getRespCode())) {
                log.error("调用pms接口changeProductStock接口错误，请求参数：{}", req.toString());
                throw new RuntimeException("调用pms接口changeProductStock接口错误");
            }
        } catch (Exception e) {
            log.error("调用产品占用库存接口失败", e);
            throw new RuntimeException("调用产品占用库存接口失败");
        }
    }

    /**
     * 前端查询用户分类资产信息
     *
     * @param memberId
     * @return
     */
    public CommonResponse<MemberInvestInfoResp> queryMemberCategoryInvestInfo(String memberId) {
        log.info("查询用户分类资产信息接口,memberId:{}", memberId);
        try {
            MemberInvestInfoResp memberInvestInfoResp = new MemberInvestInfoResp();
            List<MemberCatagoryInvestInfoResp> memberCatagoryInvestInfoResps = new ArrayList<>();
            BigDecimal totalAmt = null;
            BigDecimal incomeAmt = null;

            Map<String, Object> params = new HashMap<>();
            params.put("memberId", memberId);
            params.put("matchStatus", MatchStatusEnum.MATCH_SUCCESS.getCode());
            List<TradeOrder> orderList = tradeOrderMapper.queryOrderListByParams(params);

            Map<String, List<String>> memCategoryStatisticMap = new HashMap<>();
            for(TradeOrder tradeOrder : orderList){
                String productCategory = tradeOrder.getProductCategory();
                if(org.apache.commons.lang3.StringUtils.isEmpty(productCategory)){
                    continue;
                }
                if(null == memCategoryStatisticMap.get(productCategory)){
                    List<String> productCodes = new ArrayList<>();
                    memCategoryStatisticMap.put(productCategory, productCodes);
                }
                memCategoryStatisticMap.get(productCategory).add(tradeOrder.getProductCode());
            }

            for(String productCategory : memCategoryStatisticMap.keySet()){
                MemberCatagoryInvestInfoResp memberCatagoryInvestInfoResp = new MemberCatagoryInvestInfoResp();
                memberCatagoryInvestInfoResp.setProductCategory(productCategory);
                memberCatagoryInvestInfoResp.setCategoryName(ProductCategoryEnum.convertFromCode(productCategory).getDesc());

                TradeQueryRequest tradeQueryRequest = new TradeQueryRequest();
                tradeQueryRequest.setMemberId(memberId);
                tradeQueryRequest.setProductCodeList(memCategoryStatisticMap.get(productCategory));
                //调用交易系统获取数据:昨日收益
                Call<Result<TradeQueryResp>> call = investmentClient.queryAccountAndHistoryIncome(tradeQueryRequest);
                Result<TradeQueryResp> tradeQueryRespResponseEntity = call.execute().body();
                if (tradeQueryRespResponseEntity == null || tradeQueryRespResponseEntity.getData() == null ||
                        !ResponseCodeEnum.RESPONSE_SUCCESS.getCode().equals(tradeQueryRespResponseEntity.getCode().getCode())) {
                    log.error("查询分类资产收益接口错误，请求参数：{}", tradeQueryRequest);
                    throw new RuntimeException("查询分类资产收益接口返回异常");
                } else {
                    memberCatagoryInvestInfoResp.setCatagoryIncomeAmt(tradeQueryRespResponseEntity.getData().getIncome() == null ? BigDecimal.ZERO : tradeQueryRespResponseEntity.getData().getIncome());
                    memberCatagoryInvestInfoResp.setCatagoryTotalAmt(tradeQueryRespResponseEntity.getData().getInvestAmount() == null ? BigDecimal.ZERO : tradeQueryRespResponseEntity.getData().getInvestAmount());
                    totalAmt = NumberUtils.sum(totalAmt, memberCatagoryInvestInfoResp.getCatagoryTotalAmt());
                    incomeAmt = NumberUtils.sum(incomeAmt, memberCatagoryInvestInfoResp.getCatagoryIncomeAmt());
                }

                memberCatagoryInvestInfoResps.add(memberCatagoryInvestInfoResp);
                memberInvestInfoResp.setMemberCatagoryInvestInfoList(memberCatagoryInvestInfoResps);
            }
            memberInvestInfoResp.setTotalAmt(totalAmt);
            memberInvestInfoResp.setIncomeAmt(incomeAmt);
            log.info("查询用户分类资产信息接口返回结果:{}", memberInvestInfoResp);
            return CommonResponse.success(memberInvestInfoResp);
        } catch (Exception ex) {
            log.error("查询支付状态接口异常，ex:{}", ex);
            return CommonResponse.failure();
        }
    }

    public CommonResponse<PageQueryResp> investRecord(InvestPageReq investPageReq) {
        try {
            CommonResponse response = CommonResponse.build(ResponseCodeEnum.RESPONSE_SUCCESS.getCode(), ResponseCodeEnum.RESPONSE_SUCCESS.getDesc(), null);

            Page page = new Page();
            BeanUtils.copy(investPageReq, page);
            TradeOrder record = new TradeOrder();
            record.setProductCode(investPageReq.getProductCode());

            //列表总数
            int totalCount = tradeOrderMapper.countByProductPage(record, page);
            //组装返回值
            PageQueryResp pageQueryResp = new PageQueryResp();
            if (totalCount == 0) {
                pageQueryResp.setPageSize(page.getPageSize());
                pageQueryResp.setPageNo(page.getPageNo());
                response.setCode(ResponseCodeEnum.RESPONSE_NOT_FUND.getCode());
                response.setMsg(ResponseCodeEnum.RESPONSE_NOT_FUND.getDesc());
                response.setData(pageQueryResp);
                return response;
            }
            List<InvestPageResp> dataList = new ArrayList();
            List<TradeOrder> resultList = tradeOrderMapper.findByProductPage(record, page);
            for (TradeOrder result : resultList) {
                InvestPageResp dataResp = new InvestPageResp();
                dataResp.setOrderId(result.getId().toString());
                //获取会员名称
                CustomerDetail resultResp = getMembersInfo(result.getMemberId());
                if(resultResp!=null){
                    dataResp.setInvestName(DesensitizeUtil.chineseName(resultResp.getName()));
                }
                dataResp.setAmount(result.getInvestAmount());
                dataResp.setTransTime(result.getCreateTime());

                dataList.add(dataResp);
            }

            pageQueryResp.setPageNo(page.getPageNo());
            pageQueryResp.setPageSize(page.getPageSize());
            pageQueryResp.setTotalCount(totalCount);
            pageQueryResp.setDataList(dataList);
            log.info("investRecord返回结果,pageQueryResp:{}", pageQueryResp);

            response.setData(pageQueryResp);
            return response;
        } catch (Exception e) {
            log.error("investRecord错误", e);
            return CommonResponse.build(ResponseCodeEnum.RESPONSE_FAIL.getCode(), ResponseCodeEnum.RESPONSE_FAIL.getDesc(), null);
        }
    }

}
