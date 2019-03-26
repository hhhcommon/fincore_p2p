package com.zb.p2p.trade.service;

import com.zb.p2p.paychannel.api.common.BusinessTypeEnum;
import com.zb.p2p.paychannel.api.common.ChannelTypeEnum;
import com.zb.p2p.paychannel.api.req.LoanRequest;
import com.zb.p2p.paychannel.api.resp.TradeRespDTO;
import com.zb.p2p.trade.client.payment.PaymentClientService;
import com.zb.p2p.trade.common.constant.GlobalVar;
import com.zb.p2p.trade.common.enums.PayChannelEnum;
import com.zb.p2p.trade.common.enums.SequenceEnum;
import com.zb.p2p.trade.common.util.DateUtil;
import com.zb.p2p.trade.common.util.JsonUtil;
import com.zb.p2p.trade.persistence.dao.CreditorInfoMapper;
import com.zb.p2p.trade.persistence.dao.LoanRequestMapper;
import com.zb.p2p.trade.persistence.entity.CreditorInfoEntity;
import com.zb.p2p.trade.persistence.entity.LoanRequestEntity;
import com.zb.p2p.trade.service.common.DistributedSerialNoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

/**
 * 支付结果单元测试
 * Created by kaiyun on 2018/6/11 0011.
 */

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = {"log.base=${TRADE_LOG_CONF_PATH}/log", "spring.config.location=${TRADE_APP_CONF_PATH}"})
public class PaymentClientServiceTest {
    @Autowired
    private CreditorInfoMapper creditorInfoMapper;
    @Autowired
    private LoanRequestMapper loanRequestMapper;
    @Autowired
    private PaymentClientService paymentClientService;
    @Autowired
    private DistributedSerialNoService distributedSerialNoService;


    /**
     * 放款
     * @throws Exception
     */
    @Test
    public void testLoan() throws Exception {
//        long matchId = 4L;
//        // 债权信息
//        CreditorInfoEntity creditorInfoEntity = creditorInfoMapper.selectByMatchId(matchId);
//        LoanRequestEntity loanRequestEntity = loanRequestMapper.selectByLoanNo(creditorInfoEntity.getLoanNo());
//        String loanTradeNo = distributedSerialNoService.generatorSerialNoByIncrement(SequenceEnum.LOAD, loanRequestEntity.getSaleChannel(), 1);
//        LoanRequest loanToBalanceDTO = new LoanRequest();
//        loanToBalanceDTO.setOrderNo(loanTradeNo );// 交易流水号，唯一
//        loanToBalanceDTO.setOrderTime(DateUtil.format(new Date(), DateUtil.NEW_FORMAT) );
//        loanToBalanceDTO.setLoanerMemberId(creditorInfoEntity.getLoanMemberId() );
//        loanToBalanceDTO.setInvestorMemberId(creditorInfoEntity.getMemberId() );
//        loanToBalanceDTO.setAmount(creditorInfoEntity.getInvestAmount().toString() );
//        loanToBalanceDTO.setLoanOrderNo(loanRequestEntity.getLoanNo() );
//        loanToBalanceDTO.setInvestOrderNo(creditorInfoEntity.getOrderNo() );
//        loanToBalanceDTO.setChannelType(PayChannelEnum.BAOFU.getCode().equals(loanRequestEntity.getPayChannel())? ChannelTypeEnum.BAOFOO.getCode():ChannelTypeEnum.SINA.getCode() );
//        loanToBalanceDTO.setBusinessType(BusinessTypeEnum.LOAN.getCode() );
//        if (PayChannelEnum.BAOFU.getCode().equals(loanRequestEntity.getPayChannel()) ) {
//            loanToBalanceDTO.setBranchBankProvince(loanRequestEntity.getBranchBankProvince() );
//            loanToBalanceDTO.setBranchBankCity(loanRequestEntity.getBranchBankCity() );
//            loanToBalanceDTO.setBranchBankInst(loanRequestEntity.getBranchBankInst() );
//            loanToBalanceDTO.setBankName(loanRequestEntity.getBankName() );
//            loanToBalanceDTO.setBankAccountNo(loanRequestEntity.getBankAccountNo() );
//            loanToBalanceDTO.setMemberName(loanRequestEntity.getMemberName() );
//        }
//        loanToBalanceDTO.setNotifyUrl(GlobalVar.PAY_CALL_BACK_URL);
//
//        System.out.println("请求参数：" + JsonUtil.convertObjToStr(loanToBalanceDTO) );
//        com.zb.p2p.paychannel.api.common.CommonResp<TradeRespDTO> payResp = paymentClientService.loanToBalance(loanToBalanceDTO);
//        System.out.println("响应结果：" + JsonUtil.convertObjToStr(payResp) );
    }
}
