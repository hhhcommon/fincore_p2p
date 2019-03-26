package com.zb.p2p.cash.service.impl;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.zb.fincore.ams.common.dto.PageQueryResponse;
import com.zb.fincore.ams.facade.LoanRepayPlanServiceFacade;
import com.zb.fincore.ams.facade.dto.req.QueryLoanRepayListRequest;
import com.zb.fincore.ams.facade.dto.req.UpdateCashStatusRequest;
import com.zb.fincore.ams.facade.model.DebtRightRepayModel;
import com.zb.fincore.ams.facade.model.UpdateLoanStatusModel;
import com.zb.fincore.common.utils.BeanUtils;
import com.zb.fincore.pms.facade.product.ProductServiceForP2PFacade;
import com.zb.p2p.CashNotify;
import com.zb.p2p.GlobalVar;
import com.zb.p2p.cash.service.CashRecordService;
import com.zb.p2p.dao.master.CashRecordDAO;
import com.zb.p2p.entity.CashRecordEntity;
import com.zb.p2p.entity.InterfaceRetryEntity;
import com.zb.p2p.entity.PaymentRecordEntity;
import com.zb.p2p.enums.*;
import com.zb.p2p.facade.service.MatchRecordFacade;
import com.zb.p2p.facade.service.internal.dto.MatchRecordDTO;
import com.zb.p2p.service.callback.MemberCallBackService;
import com.zb.p2p.service.callback.TXSCallBackService;
import com.zb.p2p.service.callback.api.req.GetMemberCardReq;
import com.zb.p2p.service.callback.api.req.NotifyTxsAssetMatchResultReq;
import com.zb.p2p.service.callback.api.resp.MemberCardResp;
import com.zb.p2p.service.callback.api.resp.NotifyResp;
import com.zb.p2p.service.common.DistributedSerialNoService;
import com.zb.p2p.service.common.InterfaceRetryService;
import com.zb.p2p.service.common.PaymentRecordService;
import com.zb.payment.comm.constant.MsdTradeType;
import com.zb.payment.msd.cashier.facade.TradeFacade;
import com.zb.payment.msd.cashier.facade.dto.req.AccountInfoDTO;
import com.zb.payment.msd.cashier.facade.dto.req.TransferAccountsReqDTO;
import com.zb.payment.msd.cashier.facade.dto.rsp.CashierRspDTO;
import com.zb.payment.msd.cashier.facade.dto.rsp.TradeBaseRspDTO;
import com.zb.payment.msd.cashier.facade.enums.AccountPurposeEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Function:兑付服务
 * Author: created by liguoliang
 * Date: 2017/8/31 0031 上午 11:16
 * Version: 1.0
 */
@Service
@Slf4j
public class CashRecordServiceImpl implements CashRecordService {
    @Autowired
    private CashRecordDAO cashRecordDAO;
    @Autowired
    private InterfaceRetryService interfaceRetryService;
    @Autowired
    private DistributedSerialNoService distributedSerialNoService;
    @Autowired
    private ProductServiceForP2PFacade productServiceForP2PFacade;
    @Autowired
    private LoanRepayPlanServiceFacade loanRepayPlanServiceFacade;
    @Autowired
    private TradeFacade tradeFacade;
    @Autowired
    private TXSCallBackService txsCallBackService;
    @Autowired
    private MemberCallBackService memberCallBackService;
    @Value("${dubbo.registry.address}")
    private String zookeeperConnectionString;
    @Value("${ext.gateway.enable:true}")
    boolean enableExtGateWay;
    @Autowired
    private MatchRecordFacade matchRecordFacade;
    @Autowired
    private PaymentRecordService paymentRecordService;
    @Value("${margin.accountNo:1234567890}")
    private String marginAccountNo;
    @Value("${fee.accountNo:1234567890}")
    private String feeAccountNo;

    /**
     * 生成还款计划(按照产品编号)
     *
     * @param cashRecord
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public void saveCashRecordAndBatch(MatchRecordDTO matchRecordDTO, CashRecordEntity cashRecord) throws Exception {
        //插入兑付计划表
        CashRecordEntity cashRecordEntity = new CashRecordEntity();
        BeanUtils.copy(matchRecordDTO, cashRecordEntity);
        cashRecordEntity.setId(null);
        //生成转账请求流水号
        String reqNo = distributedSerialNoService.generatorSerialNoByIncrement(SequenceEnum.TRANSFER, SourceIdEnum.MSD.getCode(), 1);
        cashRecordEntity.setReqNo(reqNo);
        cashRecordEntity.setRefNo(matchRecordDTO.getCreditorNo());
        cashRecordEntity.setExtOrderNo(matchRecordDTO.getExtOrderNo());
        cashRecordEntity.setCashAmount(matchRecordDTO.getMatchedAmount());
        cashRecordEntity.setRepayType(RepayEnum.CREDITOR.getCode());
        cashRecordEntity.setCashIncome(matchRecordDTO.getTotalIncome());
        cashRecordEntity.setCashDate(cashRecord.getCashDate());//兑付日期=产品到期日期
        cashRecordEntity.setStatus(CashStatusEnum.INIT.getCode());
        cashRecordEntity.setSaleChannel(SourceIdEnum.MSD.getCode());
        cashRecordDAO.insertSelective(cashRecordEntity);

        MatchRecordDTO matchRecordDTO_ = new MatchRecordDTO();
        //更新cash_flag=1：已生成兑付计划
        matchRecordDTO_.setCashFlag(CashFlagEnum.CREATED.getCode());
        matchRecordDTO_.setId(matchRecordDTO.getId());
        matchRecordFacade.updateMatchRecord(matchRecordDTO_);
    }


    /**
     * 查询指定产品下的资产状态
     *
     * @param productCode
     * @return
     */
    private Map<CashTypeEnum, List<String>> queryAssetRepaySts(String productCode) {
        //查询资产状态
        QueryLoanRepayListRequest queryLoanRepayListRequest = new QueryLoanRepayListRequest();
        queryLoanRepayListRequest.setProductCode(productCode);
        PageQueryResponse<DebtRightRepayModel> debtRightInfoModelPageQueryResponse = loanRepayPlanServiceFacade.queryLoanRepayPlanList(queryLoanRepayListRequest);
        List<DebtRightRepayModel> repayList = debtRightInfoModelPageQueryResponse.getDataList();
        if (!CollectionUtils.isEmpty(repayList)) {
            Map<CashTypeEnum, List<String>> datas = Maps.newHashMap();
            //筛选出逾期和正常还款的数据,如果是逾期走代偿,正常走代付
            for (DebtRightRepayModel debtRightInfoModel : repayList) {
                //4 超时(逾期)
                if (debtRightInfoModel.getAssetStatus().equals(CashTypeEnum.EXPIRE_CASH.getValue())) {
                    if (datas.get(CashTypeEnum.EXPIRE_CASH) == null) {
                        datas.put(CashTypeEnum.EXPIRE_CASH, Lists.<String>newArrayList());
                    }
                    datas.get(CashTypeEnum.EXPIRE_CASH).add(debtRightInfoModel.getLoanOrderNo());
                }
                //5 待兑付（已还款）
                else if (debtRightInfoModel.getAssetStatus().equals(CashTypeEnum.NORMAL_CASH.getValue())) {
                    if (datas.get(CashTypeEnum.NORMAL_CASH) == null) {
                        datas.put(CashTypeEnum.NORMAL_CASH, Lists.<String>newArrayList());
                    }
                    datas.get(CashTypeEnum.NORMAL_CASH).add(debtRightInfoModel.getLoanOrderNo());
                }
            }
            return datas;
        }
        return Maps.newHashMap();
    }

    @Transactional
    public boolean execCash(String productCode) {
        Map<CashTypeEnum, List<String>> relations = queryAssetRepaySts(productCode);
        //没数据不做兑付
        if (MapUtils.isEmpty(relations)) {
            return false;
        }
        for (Map.Entry<CashTypeEnum, List<String>> entrys : relations.entrySet()) {
            CashTypeEnum cashTypeEnum = entrys.getKey();
            this.cashProcess(productCode, entrys.getValue(), cashTypeEnum);
        }
        return true;
    }

    /**
     * 兑付处理
     *
     * @param
     * @return
     */
    public void cashProcess(String productCode, List<String> loanNoList, CashTypeEnum cashTypeEnum) {
        //转手续费
        List<CashNotify> cashNotifies = cashRecordDAO.queryLoanList(loanNoList);
        List<String> loanNoList_ = Lists.newArrayList();
        if (!CollectionUtils.isEmpty(cashNotifies)) {
            for (CashNotify cashNotify : cashNotifies) {
                //返回处理转移手续费成功的借款
                this.paymentTransferFee(cashNotify, loanNoList_, cashTypeEnum);
            }
        }
        if (CollectionUtils.isEmpty(loanNoList_)) {
            return;
        }
        //根据上面的资产数据list查找对应的债权去执行兑付处理（代付还是代偿）
        List<CashRecordEntity> cashRecordList = cashRecordDAO.listCashRecord(productCode, loanNoList_);
        Set<String> cashLoanSet = Sets.newHashSet();
        if (cashRecordList != null && !cashRecordList.isEmpty()) {
            for (CashRecordEntity cashRecordEntity : cashRecordList) {
                //调用支付接口转账
                String loanNo = this.paymentTransfer(cashRecordEntity, cashTypeEnum);
                if (loanNo != null)
                    cashLoanSet.add(loanNo);
            }
            //等待支付回调支付结果
        }
        if (!CollectionUtils.isEmpty(cashLoanSet)) {
            this.notifyAssetParty(Lists.newArrayList(cashLoanSet), CashStatusEnum.CASHING);
        }
        //通知订单兑付中
        this.notifyOrderParty();
    }

    /**
     * 转账流程
     *
     * @param cashRecordEntity
     * @param cashTypeEnum
     * @return
     */
    private String paymentTransfer(CashRecordEntity cashRecordEntity, CashTypeEnum cashTypeEnum) {
        TransferAccountsReqDTO transferAccountsReqDTO = new TransferAccountsReqDTO();
        //投资人账户
        AccountInfoDTO invest = new AccountInfoDTO();
        invest.setAccountPurpose(AccountPurposeEnum._102.getCode());
        invest.setMemberId(cashRecordEntity.getMemberId());
        transferAccountsReqDTO.setInAccountInfo(invest);
        //借款人账户
        AccountInfoDTO loan = new AccountInfoDTO();
        if (cashTypeEnum.equals(CashTypeEnum.EXPIRE_CASH)) {
            loan.setAccountPurpose(AccountPurposeEnum._207.getCode());
            loan.setMemberId(marginAccountNo);
        } else if (cashTypeEnum.equals(CashTypeEnum.NORMAL_CASH)) {
            loan.setAccountPurpose(AccountPurposeEnum._101.getCode());
            loan.setMemberId(cashRecordEntity.getLoanMemberId());
        }
        transferAccountsReqDTO.setOutAccountInfo(loan);
        transferAccountsReqDTO.setOrderNo(cashRecordEntity.getReqNo());//注意这里用了转账编号 那提现用提现编号
        transferAccountsReqDTO.setOrderTime(new Date());
        transferAccountsReqDTO.setTradeAmount(cashRecordEntity.getCashAmount().add(cashRecordEntity.getCashIncome()));
        transferAccountsReqDTO.setTradeType(MsdTradeType.WITHHOLDING_PUBLIC.getCode());//兑付转账
        transferAccountsReqDTO.setOrderFrom(GlobalVar.SYS_IDENTIFY_CODE);//天鼋
        transferAccountsReqDTO.setSourceId(SourceIdEnum.MSD.getCode());
        log.info("paymentTransfer start req={}", JSON.toJSONString(transferAccountsReqDTO));
        CashierRspDTO<TradeBaseRspDTO> cashierRspDTO = this.paymentTransferAccounts(cashRecordEntity, transferAccountsReqDTO);
        log.info("paymentTransfer end resp={}", JSON.toJSONString(cashierRspDTO));
        if (cashierRspDTO != null && cashierRspDTO.getCode().equals(GlobalVar.PAYMENT_SUCCESS_CODE)) {
            //标记兑付类型 转账处理中
            CashRecordEntity cashRecordEntity_ = new CashRecordEntity();
            cashRecordEntity_.setStatus(CashStatusEnum.CASHING.getCode());
            cashRecordEntity_.setCashStatus(cashTypeEnum.getCode());
            cashRecordEntity_.setId(cashRecordEntity.getId());
            cashRecordDAO.updateByPrimaryKeySelective(cashRecordEntity_);
            //记录支付转账流水
            PaymentRecordEntity paymentRecordEntity = new PaymentRecordEntity();
            paymentRecordEntity.setAmount(cashRecordEntity.getCashAmount().add(cashRecordEntity.getCashIncome()));
            paymentRecordEntity.setType(SequenceEnum.TRANSFER.getCode());
            paymentRecordEntity.setRefId(String.valueOf(cashRecordEntity.getId()));
            paymentRecordEntity.setPaymentSerialNo(cashRecordEntity.getReqNo());
            paymentRecordEntity.setCreateTime(new Date());
            paymentRecordEntity.setPayCode(cashierRspDTO.getCode());
            paymentRecordEntity.setPayMsg(cashierRspDTO.getMsg());
            paymentRecordEntity.setStartTime(new Date());
            paymentRecordService.savePaymentRecord(paymentRecordEntity);
            //返回资产端兑付处理中的资产
            return cashRecordDAO.queryLoanNoListByCreditorNo(cashRecordEntity.getRefNo());
        }
        return null;
    }


    /**
     * 转手续费（隔离当前事物 开启新事物提交，跟之后的逻辑互不影响，避免异常情况多次转手续费导致实际要兑付给投资人的钱不足）
     *
     * @param cashNotify
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    private void paymentTransferFee(CashNotify cashNotify, List<String> loanNos, CashTypeEnum cashTypeEnum) {
        TransferAccountsReqDTO transferAccountsReqDTO = new TransferAccountsReqDTO();
        //手续费账户
        AccountInfoDTO invest = new AccountInfoDTO();
        invest.setAccountPurpose(AccountPurposeEnum._204.getCode());
        invest.setMemberId(feeAccountNo);
        transferAccountsReqDTO.setInAccountInfo(invest);
        //借款人账户
        AccountInfoDTO loan = new AccountInfoDTO();
        if (cashTypeEnum.equals(CashTypeEnum.EXPIRE_CASH)) {
            loan.setAccountPurpose(AccountPurposeEnum._207.getCode());
            loan.setMemberId(marginAccountNo);
        } else if (cashTypeEnum.equals(CashTypeEnum.NORMAL_CASH)) {
            loan.setAccountPurpose(AccountPurposeEnum._101.getCode());
            loan.setMemberId(cashNotify.getMemberId());
        }
        transferAccountsReqDTO.setOutAccountInfo(loan);
        String reqNo = distributedSerialNoService.generatorSerialNoByIncrement(SequenceEnum.TRANSFER_FEE, SourceIdEnum.MSD.getCode(), 1);
        transferAccountsReqDTO.setOrderNo(reqNo);//请求编号
        transferAccountsReqDTO.setOrderTime(new Date());
        transferAccountsReqDTO.setTradeAmount(cashNotify.getTotalLoanCharge());
        transferAccountsReqDTO.setTradeType(MsdTradeType.WITHHOLDING_PUBLIC.getCode());//兑付转账
        transferAccountsReqDTO.setOrderFrom(GlobalVar.SYS_IDENTIFY_CODE);//天鼋
        transferAccountsReqDTO.setSourceId(SourceIdEnum.MSD.getCode());
        log.info("paymentTransfer fee start req={}", JSON.toJSONString(transferAccountsReqDTO));
        CashierRspDTO<TradeBaseRspDTO> cashierRspDTO = this.paymentTransferAccounts(cashNotify.getLoanNo(), transferAccountsReqDTO);
        log.info("paymentTransfer fee end resp={}", JSON.toJSONString(cashierRspDTO));
        if (cashierRspDTO != null && cashierRspDTO.getCode().equals(GlobalVar.PAYMENT_SUCCESS_CODE)) {
            //记录支付转账流水
            PaymentRecordEntity paymentRecordEntity = new PaymentRecordEntity();
            paymentRecordEntity.setAmount(cashNotify.getTotalLoanCharge());
            paymentRecordEntity.setType(SequenceEnum.TRANSFER_FEE.getCode());
            paymentRecordEntity.setRefId(cashNotify.getLoanNo());
            paymentRecordEntity.setPaymentSerialNo(reqNo);
            paymentRecordEntity.setCreateTime(new Date());
            paymentRecordEntity.setPayCode(cashierRspDTO.getCode());
            paymentRecordEntity.setPayMsg(cashierRspDTO.getMsg());
            paymentRecordEntity.setStartTime(new Date());
            paymentRecordService.savePaymentRecord(paymentRecordEntity);
            //更新借款表手续费已转
            cashRecordDAO.updateFeeStatusByLoanNo(cashNotify.getLoanNo(), FeeTransferStatusEnum.MATCH_SUCCESS.getCode());
            loanNos.add(cashNotify.getLoanNo());
        }
    }

    /**
     * 执行支付转账
     *
     * @param cashRecordEntity
     * @param transferAccountsReqDTO
     * @return
     */
    private CashierRspDTO<TradeBaseRspDTO> paymentTransferAccounts(CashRecordEntity cashRecordEntity, TransferAccountsReqDTO transferAccountsReqDTO) {
        try {
            return tradeFacade.transferAccounts(transferAccountsReqDTO);
        } catch (Exception e) {
            if (cashRecordEntity != null) {
                log.error("调用支付转账失败", e);
                //暂时注释掉，（兑付失败的情况在资产显示为原状态，避免区分不出实际的还款状态）
                CashRecordEntity cashRecordEntity_ = new CashRecordEntity();
                cashRecordEntity_.setId(cashRecordEntity.getId());
                cashRecordEntity_.setStatus(CashStatusEnum.CASH_EXCEPTION.getCode());
                cashRecordDAO.updateStatusByReqNo(cashRecordEntity_);
                //调用支付异常，根据债权编号查询借款单 通知资管兑付失败，便于执行重新发起兑付转账
                String loanNo = cashRecordDAO.queryLoanNoListByCreditorNo(cashRecordEntity.getRefNo());
                this.notifyAssetParty(Lists.newArrayList(loanNo), CashStatusEnum.CASH_EXCEPTION);
            }
            return null;
        }
    }

    private CashierRspDTO<TradeBaseRspDTO> paymentTransferAccounts(String loanNo, TransferAccountsReqDTO transferAccountsReqDTO) {
        try {
            return tradeFacade.transferAccounts(transferAccountsReqDTO);
        } catch (Exception e) {
            log.error("调用支付转手续费失败", e);
            //调用支付异常，根据债权编号查询借款单 通知资管兑付失败，便于执行重新发起兑付转账
            this.notifyAssetParty(Lists.newArrayList(loanNo), CashStatusEnum.CASH_EXCEPTION);
            return null;
        }
    }

    /**
     * 通知资管兑付情况
     *
     * @param loanList
     */
    public void notifyAssetParty(List<String> loanList, CashStatusEnum cashStatusEnum) {
        UpdateCashStatusRequest updateLoanStatusRequests = new UpdateCashStatusRequest();
        List<UpdateLoanStatusModel> loanFailList = Lists.newArrayList();
        for (String loanNo : loanList) {
            UpdateLoanStatusModel updateLoanStatusRequest = new UpdateLoanStatusModel();
            updateLoanStatusRequest.setLoanOrderNo(loanNo);
            updateLoanStatusRequest.setLoadStatus(cashStatusEnum.getValue());
            loanFailList.add(updateLoanStatusRequest);
        }
        updateLoanStatusRequests.setLoanList(loanFailList);
        if (!CollectionUtils.isEmpty(loanFailList)) {
            try {
                loanRepayPlanServiceFacade.updateCashStatus(updateLoanStatusRequests);
            } catch (Exception e) {
                log.error("", e);
                //增加重试
                InterfaceRetryEntity interfaceRetryEntity = new InterfaceRetryEntity();
                interfaceRetryEntity.setRequestParam(JSON.toJSONString(updateLoanStatusRequests));
                interfaceRetryEntity.setBusinessType(InterfaceRetryBusinessTypeEnum.CASH_RESULT_NOTIFY_ASSET.getCode());
                try {
                    interfaceRetryService.saveInterfaceRetryRecord(interfaceRetryEntity);
                } catch (Exception e1) {
                }
            }
        }
    }

    private void notifyOrderParty() {
        List<NotifyTxsAssetMatchResultReq> cashNotifyList = Lists.newArrayList();
        List<CashNotify> cashNotifies = cashRecordDAO.queryOrdersByCashing();
        for (CashNotify cashNotify_ : cashNotifies) {
            //投资端的兑付中数据
            NotifyTxsAssetMatchResultReq notifyTxsAssetMatchResultReq = new NotifyTxsAssetMatchResultReq();
            notifyTxsAssetMatchResultReq.setExtOrderNo(cashNotify_.getExtOrderNo());
            notifyTxsAssetMatchResultReq.setStatus(CashStatusEnum.CASHING.getCode());//订单根据code传
            notifyTxsAssetMatchResultReq.setType(NotifyTxsAssetMatchResultReq.Status.CASH.name());
            notifyTxsAssetMatchResultReq.setAmount(cashNotify_.getCashAmount().add(cashNotify_.getCashIncome()));
            cashNotifyList.add(notifyTxsAssetMatchResultReq);
        }
        try {
            if (!CollectionUtils.isEmpty(cashNotifyList)) {
                txsCallBackService.tradeNotifyOrder(cashNotifyList);
            }
        } catch (Exception e) {
            log.error("通知订单兑付中失败", e);
            InterfaceRetryEntity interfaceRetryEntity = new InterfaceRetryEntity();
            interfaceRetryEntity.setRequestParam(JSON.toJSONString(cashNotifyList));
            interfaceRetryEntity.setBusinessType(InterfaceRetryBusinessTypeEnum.CASH_RESULT_NOTIFY_TXS.getCode());
            try {
                interfaceRetryService.saveInterfaceRetryRecord(interfaceRetryEntity);
            } catch (Exception e1) {
            }
        }
    }

    /**
     * 查询绑卡标识
     *
     * @param cashRecordReq
     */
    public String getCardSignId(CashRecordEntity cashRecordReq) {
        String signId = "";
        GetMemberCardReq getMemberCardReq = new GetMemberCardReq();
        try {
            if (enableExtGateWay) {
                getMemberCardReq.setCustomerId(cashRecordReq.getMemberId());
                NotifyResp<MemberCardResp> resp = memberCallBackService.getMemberCard(getMemberCardReq);
                signId = resp.getData().getSignId();
            }
        } catch (Exception e) {
            log.error("调用唐小僧接口查询绑卡标识失败:{}", e);
        }
        return signId;
    }

}
