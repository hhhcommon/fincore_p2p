package com.zb.txs.p2p.investment.service;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.zb.fincore.pms.facade.product.dto.req.QueryProductInfoRequest;
import com.zb.fincore.pms.facade.product.dto.resp.QueryProductInfoResponse;
import com.zb.fincore.pms.facade.product.model.ProductModel;
import com.zb.fincore.pms.facade.product.model.ProductPeriodModel;
import com.zb.fincore.pms.facade.product.model.ProductProfitModel;
import com.zb.txs.foundation.legacy.DateUtil;
import com.zb.txs.foundation.monads.Try;
import com.zb.txs.foundation.response.Result;
import com.zb.txs.p2p.business.enums.CalculateInvestTypeEnum;
import com.zb.txs.p2p.business.enums.InvestmentStatus;
import com.zb.txs.p2p.business.enums.order.CashStatusEnum;
import com.zb.txs.p2p.business.enums.order.MatchStatusEnum;
import com.zb.txs.p2p.business.enums.order.ResponseCodeEnum;
import com.zb.txs.p2p.business.invest.repose.*;
import com.zb.txs.p2p.business.invest.request.InvestPageReq;
import com.zb.txs.p2p.business.invest.request.InvestmentRecordRequest;
import com.zb.txs.p2p.business.invest.request.InvestmentRequest;
import com.zb.txs.p2p.business.invest.request.TradeQueryRequest;
import com.zb.txs.p2p.business.order.response.CardsResponse;
import com.zb.txs.p2p.business.order.response.CommonResponse;
import com.zb.txs.p2p.business.order.response.PageQueryResp;
import com.zb.txs.p2p.investment.httpclient.InvestmentClient;
import com.zb.txs.p2p.investment.util.BeanUtils;
import com.zb.txs.p2p.investment.util.Page;
import com.zb.txs.p2p.order.httpclient.PmsClient;
import com.zb.txs.p2p.order.persistence.mapper.TradeOrderMapper;
import com.zb.txs.p2p.order.persistence.model.Appointment;
import com.zb.txs.p2p.order.persistence.model.TradeOrder;
import com.zb.txs.p2p.order.service.OrderService;
import com.zb.txs.p2p.order.state.enums.States;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import retrofit2.Call;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class InvestmentService {

    private final InvestmentClient investmentClient;
    private final PmsClient pmsClient;
    private final TradeOrderMapper tradeOrderMapper;
    private final OrderService orderService;

    private static final String MSDTZ = "MSDTZ";

    @Autowired
    public InvestmentService(InvestmentClient investmentClient,PmsClient pmsClient,
                             TradeOrderMapper tradeOrderMapper, OrderService orderService) {
        this.investmentClient = investmentClient;
        this.tradeOrderMapper = tradeOrderMapper;
        this.pmsClient = pmsClient;
        this.orderService = orderService;
    }

    public CommonResponse<InvestProfitResp> getInvestProfit(InvestmentRequest investmentRequest) {
        try {
            InvestProfitResp investProfitResp = new InvestProfitResp();
            //调用交易系统获取数据:昨日收益
            TradeQueryRequest tradeQueryRequest = new TradeQueryRequest();
            tradeQueryRequest.setIncomeDate(investmentRequest.getInterestDate());
            tradeQueryRequest.setMemberId(investmentRequest.getMemberId());
            Call<Result<TradeQueryResp>> call = investmentClient.queryYesterdayIncome(tradeQueryRequest);
            Result<TradeQueryResp> tradeQueryRespResponseEntity = call.execute().body();
            if (tradeQueryRespResponseEntity == null || tradeQueryRespResponseEntity.getData() == null ||
                    !ResponseCodeEnum.RESPONSE_SUCCESS.getCode().equals(tradeQueryRespResponseEntity.getCode().getCode())) {
                log.error("查询交易昨日收益接口错误，请求参数：{}", tradeQueryRequest);
                throw new RuntimeException("查询交易昨日收益接口返回异常");
            } else {
                investProfitResp.setInterestProfit(tradeQueryRespResponseEntity.getData().getIncome() == null ? "0" : tradeQueryRespResponseEntity.getData().getIncome().toString());
            }

            Call<Result<TradeQueryResp>> call1 = investmentClient.queryAccountAndHistoryIncome(tradeQueryRequest);
            Result<TradeQueryResp> tradeQueryResp1 = call1.execute().body();
            if (tradeQueryResp1 == null || tradeQueryResp1.getData() == null ||
                    !ResponseCodeEnum.RESPONSE_SUCCESS.getCode().equals(tradeQueryResp1.getCode().getCode())) {
                log.error("查询交易总本金和已兑付收益接口错误，请求参数：{}", tradeQueryRequest);
                throw new RuntimeException("查询交易总本金和已兑付收益接口返回异常");
            } else {
                investProfitResp.setTotalAssets(tradeQueryResp1.getData().getInvestAmount() == null ? "0" : tradeQueryResp1.getData().getInvestAmount().toString());
            }

            log.info("getInvestProfit返回结果,investProfitResp:{}", investProfitResp);

            return CommonResponse.success(investProfitResp);
        } catch (Exception t) {
            log.error("getInvestProfit错误", t);
            return CommonResponse.failure();
        }
    }


    public Try<InvestDetailResp> getInvestmentDetail(InvestmentRequest investmentRequest) {
        QueryProductInfoRequest req = new QueryProductInfoRequest();
        req.setProductCode(investmentRequest.getProductCode());

        return getProductInfo(req)
                .flatMap(pmsResp -> getTradeOrders(investmentRequest.getOrderId())
                        .flatMap(tradeOrders -> getMatureProfit(tradeOrders.getId().toString())
                                .flatMap(matureProfit -> orderService.getMembersCard(investmentRequest.getMemberId())
                                        .map(cardsResponse -> convertToInvestmentDetailResp(pmsResp, matureProfit, tradeOrders, cardsResponse)))));
    }

    private Try<QueryProductInfoResponse> getProductInfo(QueryProductInfoRequest req){
        try{
            log.info("queryProductInfo请求参数,queryProductInfo:{}", JSON.toJSONString(req));
            Call<QueryProductInfoResponse> call = pmsClient.queryProductInfo(req);
            QueryProductInfoResponse pmsResp = call.execute().body();
            log.info("queryProductInfo返回的结果,queryProductInfo:{}", JSON.toJSONString(pmsResp));

            if (pmsResp == null || !ResponseCodeEnum.RESPONSE_SUCCESS.getCode().equals(pmsResp.getRespCode())) {
                throw new RuntimeException("查询pms产品详情接口错误");
            }

            return Try.success(pmsResp);
        }catch (Exception e){
            log.error("getProductInfo错误", e);
            return Try.failure("getProductInfo错误");
        }
    }

    private Try<TradeOrder> getTradeOrders(final String orderId) {
        try{
            TradeOrder tradeOrders = tradeOrderMapper.selectByPrimaryKey(Long.parseLong(orderId));
            Preconditions.checkNotNull(
                    tradeOrders, "查询的tradeOrders为空, orderId:" + orderId);
            return Try.success(tradeOrders);
        }catch (NullPointerException e){
            log.error("getTradeOrders错误:", e);
            return Try.failure("查询的tradeOrders为空, orderId:" + orderId);
        }catch (DataAccessException dataAccessException){
            log.error("getTradeOrders错误,数据库访问错误");
            return Try.failure("getTradeOrders错误,数据库访问错误");
        }catch(Throwable t){
            log.error("getTradeOrders错误", t);
            return Try.failure("getTradeOrders错误");
        }
    }

    private Try<BigDecimal> getMatureProfit(final String orderNo) {
        TradeQueryRequest tradeQueryRequest = TradeQueryRequest.builder().orderNo(orderNo).build();
        Call<Result<TradeQueryResp>> call = investmentClient.queryOrderIncome(tradeQueryRequest);
        try{
            Result<TradeQueryResp> tradeQueryRespResponseEntity = call.execute().body();
            if (tradeQueryRespResponseEntity == null || tradeQueryRespResponseEntity.getData() == null ||
                    !tradeQueryRespResponseEntity.getCode().getCode().equals(ResponseCodeEnum.RESPONSE_SUCCESS.getCode())) {
                log.error("查询交易预期收益接口错误，请求参数：{}", tradeQueryRequest);
                return Try.success(BigDecimal.ZERO);
            }else{
                return Try.success(tradeQueryRespResponseEntity.getData().getIncome() == null ? BigDecimal.ZERO : tradeQueryRespResponseEntity.getData().getIncome());
            }
        }catch (IOException e){
            log.error("查询交易预期收益接口错误:", e);
            return Try.success(BigDecimal.ZERO);
        }
//        return Try.success(BigDecimal.ZERO);
    }

//    private Try<CardsResponse> getMemberBankCard(final String memberID){
//        try{
//            Result<List<CardsResponse>> cards = membersClient.getMemberCardInfo(new CardsRequest(MSDTZ,memberID));
//            if(cards==null || !cards.getCode().equals(Result.CodeManager.SUCCESS)){
//                log.error("查询用户绑卡接口错误，请求参数：sourceCode:{} memberId{}", MSDTZ, memberID);
//                return Try.failure("查询用户绑卡接口错误");
//            }else{
//                if(cards.getData().size()<=0){
//                    log.info("用户绑卡信息不存在，请求参数：sourceCode:{} memberId{}", MSDTZ, memberID);
//                    return Try.success(null);
//                }
//                if(cards.getData().get(0).getCardNo()==null || cards.getData().get(0).getCardNo().length()<4){
//                    log.info("用户绑卡信息错误，请求参数：sourceCode:{} memberId{}", MSDTZ, memberID);
//                    return Try.success(null);
//                }
//                return Try.success(cards.getData().get(0));
//            }
//        }catch (Exception e){
//            log.error("查询绑卡信息接口错误", e);
//            return Try.failure("查询绑卡信息接口错误");
//        }
////        return Try.success(new CardsResponse());
//    }

    private InvestDetailResp convertToInvestmentDetailResp(QueryProductInfoResponse pmsResp, BigDecimal matureProfit,
                                                           TradeOrder tradeOrders, CardsResponse bankCard) {

        ProductModel productModel = pmsResp.getProductModel();
        ProductPeriodModel productPeriodModel = productModel.getProductPeriodModel();
        ProductProfitModel productProfitModel = productModel.getProductProfitModel();

        return InvestDetailResp.builder()
                .rate(new BigDecimal(productProfitModel.getMinYieldRate().add(productProfitModel.getAddYieldRate()).floatValue() * 100).toString())
                .interestStartTime(productPeriodModel.getValueTime())
                //到期回款时间
                .paymentTime(productPeriodModel.getExpectClearTime())
                .matchAmount(tradeOrders.getMatchAmount().toString())
                .productTitle(productModel.getProductName())
                .investAmount(tradeOrders.getInvestAmount().toString())
                .expireProfit(matureProfit.toString())
//                .status(getStatus(tradeOrders))
                .amountInvested(tradeOrders.getMatchAmount().toString())
                .allPaymentAmount(tradeOrders.getMatchAmount().add(matureProfit).toString())
                .payType(CalculateInvestTypeEnum.getDesc(productProfitModel.getCalculateInvestType()))
                .investTime(tradeOrders.getCreateTime())
                //兑付时间
                .paidTime(tradeOrders.getCashTime() != null ? DateUtil.format(tradeOrders.getCashTime(), "yyyy-MM-dd HH:mm") : "")
                .holdTime(getHoldTime(productPeriodModel))
                .orderId(tradeOrders.getId().toString())
                .notes(getNotes(tradeOrders.getCashStatus(), productPeriodModel.getExpectExpireTime()))
                .payTo(bankCard==null? "投资银行卡":bankCard.getBankName()+"尾号"+bankCard.getCardNo().substring(bankCard.getCardNo().length()-4))
                .build();
    }

    private String getNotes(String cashStatus, Date endprofittime) {
        if (CashStatusEnum.CASH_SUCCESS.getCode().equals(cashStatus)) {
            return "回款已成功提交，具体到账时间请以银行为准";
        } else if (DateUtil.dateToString(new Date(), "yyyyMMdd").compareTo(DateUtil.dateToString(endprofittime, "yyyyMMdd")) > 0
                || DateUtil.dateToString(new Date(), "yyyyMMdd").compareTo(DateUtil.dateToString(endprofittime, "yyyyMMdd")) == 0) {
            //回款中
            return "已向银行发起回款请求，请耐心等待";
        }

        return "";

//        if(refundDate==null){
//            return "";
//        }
//
//        if(bankCard == null){
//            return "注：未匹配"
//                    +sumUnMatchAmount.setScale(0,BigDecimal.ROUND_DOWN)
//                    +"元已于"
//                    +DateUtil.dateToString(refundDate,"MM-dd")
//                    +"返还至您的投资银行卡";
//        }
//
//        return "注：未匹配"
//                +sumUnMatchAmount.setScale(0,BigDecimal.ROUND_DOWN)
//                +"元已于"
//                +DateUtil.dateToString(refundDate,"MM-dd")
//                +"返还至尾号"+bankCard.getCardNo().substring(bankCard.getCardNo().length()-4)
//                +"的"+bankCard.getBankName()
//                +"卡";
    }

    private Integer getStatus(TradeOrder tradeOrders) {
        if ("MATCHING".equals(tradeOrders.getPayStatus())) {
            return InvestmentStatus.MATCHING.getCode();
        }

        if ("PAYING".equals(tradeOrders.getPayStatus())) {
            return InvestmentStatus.PAYING.getCode();
        }

        if (tradeOrders.getMatchAmount().compareTo(BigDecimal.ZERO) > 0 && "CASH_SUCCESS".equals(tradeOrders.getCashStatus())) {
            return InvestmentStatus.PAID.getCode();
        }

        return InvestmentStatus.INVESTING.getCode();
    }

    private String getHoldTime(ProductPeriodModel productPeriodModel){
        Date startProfitTime = productPeriodModel.getValueTime()!=null? productPeriodModel.getValueTime():new Date();
        Date paymentTime = productPeriodModel.getExpectClearTime()!=null?productPeriodModel.getExpectClearTime():new Date();
        Date now = new Date();
        int duration = DateUtil.getDifferDays(startProfitTime,paymentTime);
//        int alreadyHoldDay = DateUtil.getDifferDays(startProfitTime,now)+1;
        int alreadyHoldDay = DateUtil.getDifferDays(startProfitTime, now);
        if(duration<alreadyHoldDay){
            alreadyHoldDay = duration;
        }
        return alreadyHoldDay+"/"+duration;
    }

    public CommonResponse<InvestmentRecordResp> getInvestmentRecord(InvestmentRecordRequest investmentRecordRequest) {
        try{
            Long lastId = investmentRecordRequest.getLastId();
            if (lastId == null || lastId <= 0) {
                lastId = Long.MAX_VALUE;
            }
            Long pageSize = investmentRecordRequest.getPageSize();
            if (pageSize == null || pageSize <= 0) {
                pageSize = Long.parseLong("10");
            }

            List<InvestmentRecordItem> resultList = Lists.newArrayList();
            //执行查询过程
            if (investmentRecordRequest.getTransType() == 0) {
                List<TradeOrder> tradeOrders = tradeOrderMapper.selectOrderListByLastIdSort(investmentRecordRequest.getMemberId(), lastId, pageSize);
                if (tradeOrders != null && !tradeOrders.isEmpty()) {
                    for (TradeOrder tradeOrder : tradeOrders) {
                        InvestmentRecordItem resultItem = new InvestmentRecordItem();
                        resultItem.setOrderId(tradeOrder.getId());

                        //获取产品信息
                        QueryProductInfoRequest req = new QueryProductInfoRequest();
                        req.setProductCode(tradeOrder.getProductCode());
                        Try<QueryProductInfoResponse> productResp = getProductInfo(req);
                        QueryProductInfoResponse pmsResp = null;
                        if (productResp.isSuccess()) {
                            pmsResp = productResp.successValue();
                        } else {
                            return CommonResponse.failure();
                        }
                        ProductModel productModel = pmsResp.getProductModel();

                        resultItem.setProductTitle(productModel != null ? productModel.getProductName() : "");
                        resultItem.setTransType(3);
                        resultItem.setInvestAmount(tradeOrder.getInvestAmount() == null ? "" : tradeOrder.getInvestAmount().toString());
                        resultItem.setExpireProfit(null);
                        resultItem.setInvestTime(tradeOrder.getCreateTime() != null ? DateUtil.dateToString(tradeOrder.getCreateTime(), "yyyy-MM-dd HH:mm") : "");
                        resultItem.setCashTime(null);
                        resultItem.setStatus(tradeOrder.getPayStatus());
                        resultList.add(resultItem);
                        if (MatchStatusEnum.MATCH_SUCCESS.getCode().equals(tradeOrder.getMatchStatus()) && CashStatusEnum.CASH_SUCCESS.getCode().equals(tradeOrder.getCashStatus())) {
                            InvestmentRecordItem resultItem1 = new InvestmentRecordItem();
                            resultItem1.setOrderId(tradeOrder.getId());
                            resultItem1.setProductCode(tradeOrder.getProductCode());
                            resultItem1.setProductTitle(productModel != null ? productModel.getProductName() : "");
                            resultItem1.setTransType(4);
                            resultItem1.setInvestAmount(tradeOrder.getMatchAmount() == null ? "" : tradeOrder.getMatchAmount().toString());
                            resultItem1.setExpireProfit(getMatureProfit(tradeOrder.getId().toString()).successValue().toString());//取交易查询的收益
                            resultItem1.setInvestTime(null);
                            resultItem1.setCashTime(tradeOrder.getCashTime() != null ? DateUtil.dateToString(tradeOrder.getCashTime(), "yyyy-MM-dd HH:mm") : "");
                            resultItem1.setStatus(tradeOrder.getCashStatus());
                            resultList.add(resultItem1);
                        }
                    }
                }
            } else if (investmentRecordRequest.getTransType() == 3) {
                List<TradeOrder> tradeOrders = tradeOrderMapper.selectOrderListPaySort(investmentRecordRequest.getMemberId(), lastId, pageSize);
                if (tradeOrders != null && !tradeOrders.isEmpty()) {
                    for (TradeOrder tradeOrder : tradeOrders) {
                        InvestmentRecordItem resultItem = new InvestmentRecordItem();
                        resultItem.setOrderId(tradeOrder.getId());
                        resultItem.setProductCode(tradeOrder.getProductCode());

                        //获取产品信息
                        QueryProductInfoRequest req = new QueryProductInfoRequest();
                        req.setProductCode(tradeOrder.getProductCode());
                        Try<QueryProductInfoResponse> productResp = getProductInfo(req);
                        QueryProductInfoResponse pmsResp = null;
                        if (productResp.isSuccess()) {
                            pmsResp = productResp.successValue();
                        } else {
                            return CommonResponse.failure();
                        }
                        ProductModel productModel = pmsResp.getProductModel();

                        resultItem.setProductTitle(productModel != null ? productModel.getProductName() : "");
                        resultItem.setTransType(3);
                        resultItem.setInvestAmount(tradeOrder.getInvestAmount() == null ? "" : tradeOrder.getInvestAmount().toString());
                        resultItem.setExpireProfit(null);
                        resultItem.setInvestTime(tradeOrder.getCreateTime() != null ? DateUtil.dateToString(tradeOrder.getCreateTime(), "yyyy-MM-dd HH:mm") : "");
                        resultItem.setCashTime(null);
                        resultItem.setStatus(tradeOrder.getPayStatus());
                        resultList.add(resultItem);
                    }
                }
            } else if (investmentRecordRequest.getTransType() == 4) {
                List<TradeOrder> tradeOrders = tradeOrderMapper.selectOrderListCashSort(investmentRecordRequest.getMemberId(), lastId, pageSize);
                if (tradeOrders != null && !tradeOrders.isEmpty()) {
                    for (TradeOrder tradeOrder : tradeOrders) {
                        InvestmentRecordItem resultItem = new InvestmentRecordItem();
                        resultItem.setOrderId(tradeOrder.getId());
                        resultItem.setProductCode(tradeOrder.getProductCode());

                        //获取产品信息
                        QueryProductInfoRequest req = new QueryProductInfoRequest();
                        req.setProductCode(tradeOrder.getProductCode());
                        Try<QueryProductInfoResponse> productResp = getProductInfo(req);
                        QueryProductInfoResponse pmsResp = null;
                        if (productResp.isSuccess()) {
                            pmsResp = productResp.successValue();
                        } else {
                            return CommonResponse.failure();
                        }
                        ProductModel productModel = pmsResp.getProductModel();

                        resultItem.setProductTitle(productModel != null ? productModel.getProductName() : "");
                        resultItem.setTransType(4);
                        resultItem.setInvestAmount(tradeOrder.getInvestAmount() == null ? "" : tradeOrder.getInvestAmount().toString());
                        resultItem.setExpireProfit(getMatureProfit(tradeOrder.getId().toString()).successValue().toString());//取交易查询的收益
                        resultItem.setInvestTime(null);
                        resultItem.setCashTime(tradeOrder.getCashTime() != null ? DateUtil.dateToString(tradeOrder.getCashTime(), "yyyy-MM-dd HH:mm") : "");
                        resultItem.setStatus(tradeOrder.getCashStatus());
                        resultList.add(resultItem);
                    }
                }
            }
            InvestmentRecordResp investmentRecordResp = InvestmentRecordResp.builder()
                    .total(resultList.size() + "")
                    .investmentRecordItemList(resultList)
                    .build();

            log.info("getInvestmentRecord返回结果,investmentRecordResp:{}", investmentRecordResp);
            return CommonResponse.success(investmentRecordResp);

        }catch (Exception e){
            log.error("getInvestmentRecord错误", e);
            return CommonResponse.failure();
        }

    }

    public CommonResponse<PageQueryResp> listPageRecord(InvestPageReq investPageReq) {
        try {
            CommonResponse response = CommonResponse.build(ResponseCodeEnum.RESPONSE_SUCCESS.getCode(), ResponseCodeEnum.RESPONSE_SUCCESS.getDesc(), null);

            String tranType = investPageReq.getTransType();
            Page page = new Page();
            BeanUtils.copy(investPageReq, page);
            TradeOrder record = new TradeOrder();
            record.setTransType(investPageReq.getTransType());
            record.setMemberId(investPageReq.getMemberId());
            record.setStartDate(investPageReq.getStartDate());

            Calendar c = Calendar.getInstance();
            c.setTime(investPageReq.getEndDate());
            c.add(Calendar.DAY_OF_MONTH, 1);// 今天+1天
            Date tomorrow = c.getTime();
            record.setEndDate(tomorrow);
            //列表总数
            int totalCount = tradeOrderMapper.contByDatePage(record, page);
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
            List<TradeOrder> resultList = tradeOrderMapper.findByDatePage(record, page);
            for (TradeOrder result : resultList) {
                InvestPageResp dataResp = new InvestPageResp();
                dataResp.setMemberId(result.getMemberId());
                dataResp.setOrderId(result.getId().toString());
                dataResp.setTransType(tranType);
                dataResp.setHisFlag(result.getHisFlag());
                if ("0".equals(tranType)) {
                    dataResp.setAmount(result.getInvestAmount());
                    dataResp.setStatus(result.getPayStatus());
                    dataResp.setTransTime(result.getCreateTime());
                } else if ("1".equals(tranType)) {
                    dataResp.setAmount(result.getCashAmount());
                    dataResp.setStatus(result.getCashStatus());
                    dataResp.setTransTime(result.getCashTime());
                }
                dataList.add(dataResp);
            }

            pageQueryResp.setPageNo(page.getPageNo());
            pageQueryResp.setPageSize(page.getPageSize());
            pageQueryResp.setTotalCount(totalCount);
            pageQueryResp.setDataList(dataList);
            log.info("listPageRecord返回结果,pageQueryResp:{}", pageQueryResp);

            response.setData(pageQueryResp);
            return response;
        } catch (Exception e) {
            log.error("listPageRecord错误", e);
            return CommonResponse.build(ResponseCodeEnum.RESPONSE_FAIL.getCode(), ResponseCodeEnum.RESPONSE_FAIL.getDesc(), null);
        }

    }

    private boolean isAppointmentPaySucceed(Appointment appointment){
        return appointment.getStatus().equals(States.DEAL_DONE)
                ||appointment.getStatus().equals(States.MATCHING)
                ||appointment.getStatus().equals(States.PENDING_CONFIRM_APPOINT)
                ||appointment.getStatus().equals(States.PENDING_CONFIRM_APPOINT)
                ||appointment.getStatus().equals(States.APPOINT_CONFIRM_FAILURE)
                ;
    }

}
