package com.zb.p2p.trade.client.payment;

import com.alibaba.fastjson.JSON;
import com.zb.p2p.match.common.util.DateUtil;
import com.zb.p2p.paychannel.api.PayChannelAPI;
import com.zb.p2p.paychannel.api.common.BusinessTypeEnum;
import com.zb.p2p.paychannel.api.common.ChannelTypeEnum;
import com.zb.p2p.paychannel.api.common.CommonResp;
import com.zb.p2p.paychannel.api.req.FeeTransferReq;
import com.zb.p2p.paychannel.api.req.HonourRequest;
import com.zb.p2p.paychannel.api.req.LoanRequest;
import com.zb.p2p.paychannel.api.req.QueryTradeReq;
import com.zb.p2p.paychannel.api.resp.TradeRespDTO;
import com.zb.p2p.trade.client.dto.QueryTradeResultDto;
import com.zb.p2p.trade.common.constant.GlobalVar;
import com.zb.p2p.trade.common.enums.PayChannelEnum;
import com.zb.p2p.trade.persistence.entity.CreditorInfoEntity;
import com.zb.p2p.trade.persistence.entity.LoanRequestEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * <p> 支付客户端封装Service </p>
 *
 * @author Vinson
 * @version PaymentClientService.java v1.0 2018/4/25 15:19 Zhengwenquan Exp $
 */
@Slf4j
@Service
public class PaymentClientService {

    @Autowired
    private PayChannelAPI payChannelAPI;


    /**
     * 封装查询支付交易结果
     * @param orderNo
     * @return
     */
    public TradeRespDTO queryTradeResult(String orderNo, String memberId) {
        //查询资产状态
        QueryTradeReq queryTradeStatus = new QueryTradeReq();
        queryTradeStatus.setOrderNo(orderNo);
        queryTradeStatus.setOrderTime(DateUtil.getLongDateString(new Date()));
//        queryTradeStatus.setSourceId(SourceIdEnum.TXS.getCode());
        try {
            log.info("queryTradeStatus请求的参数为:{}", queryTradeStatus);
            CommonResp<TradeRespDTO> resp = payChannelAPI.queryTrade(queryTradeStatus);
            log.info("queryTradeStatus返回的结果为:{}", JSON.toJSONString(resp));
            //确认有可能支付链路失败，查看是否支付
            if (resp != null && resp.getData() != null) {
                return resp.getData();
            }
        } catch (Exception e) {
            log.error("调用查询支付结果请求异常：", e);
        }

        return null;
    }

    /**
     * 放款到余额
     * @param creditorInfoEntity
     * @param loanRequestEntity
     * @param loanTradeSerialNumber
     * @return
     */
    public CommonResp<TradeRespDTO> loanToBalance(CreditorInfoEntity creditorInfoEntity,
                                                  LoanRequestEntity loanRequestEntity,
                                                  String loanTradeSerialNumber){
        try {
            LoanRequest loanToBalanceDTO = new LoanRequest();
            loanToBalanceDTO.setOrderNo(loanTradeSerialNumber );// 交易流水号，唯一
            loanToBalanceDTO.setOrderTime(com.zb.p2p.trade.common.util.DateUtil.format(new Date(), com.zb.p2p.trade.common.util.DateUtil.NEW_FORMAT) );
            loanToBalanceDTO.setLoanerMemberId(creditorInfoEntity.getLoanMemberId() );
            loanToBalanceDTO.setInvestorMemberId(creditorInfoEntity.getMemberId() );
            loanToBalanceDTO.setAmount(creditorInfoEntity.getInvestAmount().toString() );
            loanToBalanceDTO.setLoanOrderNo(loanRequestEntity.getLoanNo() );
            loanToBalanceDTO.setInvestOrderNo(creditorInfoEntity.getOrderNo() );
            loanToBalanceDTO.setChannelType(PayChannelEnum.BAOFU.getCode().equals(loanRequestEntity.getPayChannel())? ChannelTypeEnum.BAOFOO.getCode():ChannelTypeEnum.SINA.getCode() );
            loanToBalanceDTO.setBusinessType(BusinessTypeEnum.LOAN.getCode() );
            if (PayChannelEnum.BAOFU.getCode().equals(loanRequestEntity.getPayChannel()) ) {
                loanToBalanceDTO.setBranchBankProvince(loanRequestEntity.getBranchBankProvince() );
                loanToBalanceDTO.setBranchBankCity(loanRequestEntity.getBranchBankCity() );
                loanToBalanceDTO.setBranchBankInst(loanRequestEntity.getBranchBankInst() );
                loanToBalanceDTO.setBankName(loanRequestEntity.getBankName() );
                loanToBalanceDTO.setBankAccountNo(loanRequestEntity.getBankAccountNo() );
                loanToBalanceDTO.setMemberName(loanRequestEntity.getMemberName() );
            }
            loanToBalanceDTO.setNotifyUrl(GlobalVar.PAY_CALL_BACK_URL);
            log.info("放款请求的参数为:{}", loanToBalanceDTO);
            CommonResp<TradeRespDTO> resp = payChannelAPI.loan(loanToBalanceDTO);
            log.info("放款返回的结果为:{}", JSON.toJSONString(resp));
            //确认有可能支付链路失败，查看是否支付
            if (resp != null && resp.getData() != null) {
                return resp;
            }
        } catch (Exception e) {
            log.error("调用放款支付请求异常：", e);
        }

        return null;
    }

    /**
     * 手续费划转
     * @param request
     * @return
     */
    public TradeRespDTO feeTransfer(FeeTransferReq request){
        try {
            log.info("feeTransfer请求的参数为:{}", request);
            CommonResp<TradeRespDTO> resp = payChannelAPI.feeTransfer(request);
            log.info("feeTransfer返回的结果为:{}", JSON.toJSONString(resp));
            //确认有可能支付链路失败，查看是否支付
            if (resp != null && resp.getData() != null) {
                return resp.getData();
            }
        } catch (Exception e) {
            log.error("调用手续费支付请求异常：", e);
        }

        return null;
    }

    /**
     * 兑付支付
     * @param request
     * @return
     */
    public TradeRespDTO cashPay(HonourRequest request){
        try {
            log.info("cashPay请求的参数为:{}", request);
            CommonResp<TradeRespDTO> resp = payChannelAPI.honour(request);
            log.info("cashPay返回的结果为:{}", JSON.toJSONString(resp));
            //确认有可能支付链路失败，查看是否支付
            if (resp != null && resp.getData() != null) {
                return resp.getData();
            }
        } catch (Exception e) {
            log.error("调用兑付支付请求异常：", e);
        }

        return null;
    }
}
