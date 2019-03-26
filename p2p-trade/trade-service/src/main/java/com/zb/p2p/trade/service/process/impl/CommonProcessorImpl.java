package com.zb.p2p.trade.service.process.impl;

import com.zb.p2p.trade.common.constant.GlobalVar;
import com.zb.p2p.trade.common.domain.CashBillPlan;
import com.zb.p2p.trade.common.domain.CashBillPlanKey;
import com.zb.p2p.trade.common.domain.CashPlan;
import com.zb.p2p.trade.common.domain.CreditorInfo;
import com.zb.p2p.trade.common.enums.CashStatusEnum;
import com.zb.p2p.trade.common.enums.RepaymentTypeEnum;
import com.zb.p2p.trade.common.exception.BusinessException;
import com.zb.p2p.trade.common.model.CashAmountSuite;
import com.zb.p2p.trade.common.model.CashPlanHolder;
import com.zb.p2p.trade.persistence.converter.CashBillPlanConverter;
import com.zb.p2p.trade.persistence.dto.CreateCashBilPlanRequest;
import com.zb.p2p.trade.persistence.entity.LoanRequestEntity;
import com.zb.p2p.trade.service.cash.CashBillPlanService;
import com.zb.p2p.trade.service.cash.CashRecordService;
import com.zb.p2p.trade.service.cash.RepayBillPlanService;
import com.zb.p2p.trade.service.common.DistributedLockService;
import com.zb.p2p.trade.service.process.CashPlanProcessor;
import com.zb.p2p.trade.service.process.CashPlanProcessorFactory;
import com.zb.p2p.trade.service.process.CommonProcessor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <p> 通用处理器实现 </p>
 *
 * @author Vinson
 * @version CommonProcessorImpl.java v1.0 2018/4/21 15:59 Zhengwenquan Exp $
 */
@Slf4j
@Service
public class CommonProcessorImpl implements CommonProcessor {

    @Autowired
    private DistributedLockService distributedLockService;
    @Autowired
    private CashBillPlanService cashBillPlanService;
    @Autowired
    private CashRecordService cashRecordService;
    @Resource
    private TransactionTemplate transactionTemplate;
    @Autowired
    private CashPlanProcessorFactory cashPlanProcessorFactory;
    @Autowired
    private RepayBillPlanService repayBillPlanService;


    @Override
    public List<CreateCashBilPlanRequest> buildOneOffCreateCashBillPlanRequest(LoanRequestEntity loanRequest) throws BusinessException {
        /**
         * 一次性到期还本付息计算公式：
         * 每天费率 9位向下取整，计算结果 小数第三位均做向下取整
         * 借款金额 * 期限 * 利率/365 = 总借款利息
         * 投资金额/资产总金额 * 总借款利息
         */
        // 获取每日费率
        BigDecimal dayRate = loanRequest.getLoanRate().divide(GlobalVar.YEAR_DAYS_365, 8, BigDecimal.ROUND_DOWN);
        BigDecimal loanInterest = loanRequest.getMatchedAmount()
                .multiply(dayRate)
                .multiply(new BigDecimal(loanRequest.getLockDate()))
                .setScale(2, BigDecimal.ROUND_DOWN);
        loanRequest.setLoanInterests(loanInterest);

        // 组装生成兑付计划模板
        List<CreateCashBilPlanRequest> requestList = new ArrayList<>();
        CreateCashBilPlanRequest request = new CreateCashBilPlanRequest();
        // 当前资产Key
        request.setKey(new CashBillPlanKey(loanRequest.getOriginalAssetCode(), 1, RepaymentTypeEnum.CREDITOR));
        request.setExpectPrinciple(loanRequest.getMatchedAmount());
        request.setExpectInterest(loanInterest);
        //借款手续费
        BigDecimal totalLoanCharge = loanRequest.getMatchedAmount().multiply(loanRequest.getLoanFeeRate())
                .multiply(new BigDecimal( loanRequest.getLockDate()))
                .multiply(dayRate)
                .setScale(2, BigDecimal.ROUND_CEILING);
        request.setLoanChargeFee(totalLoanCharge);
        request.setProductCode(loanRequest.getProductCode());
        request.setOrgAssetNo(loanRequest.getOriginalAssetCode());
        request.setExpectDate(loanRequest.getExpireDate());
        request.setLoanMemberId(loanRequest.getMemberId());
        request.setSaleChannel(loanRequest.getSaleChannel());
        // Add
        requestList.add(request);

        return requestList;
    }

    @Override
    public List<CreateCashBilPlanRequest> buildCreateCashBillPlanRequest(CreditorInfo creditorInfo, List<CashPlan> remainPlans) {
        // 1.组装兑付计划请求
        List<CreateCashBilPlanRequest> requestList = new ArrayList<>();
        for (CashPlan plan : remainPlans) {
            // 兑付计划未锁定则终止
            Assert.isTrue(plan.isLockTag(), String.format("兑付计划[{}]锁定状态异常", plan.getId()));

            CreateCashBilPlanRequest request = new CreateCashBilPlanRequest();
            request.setProductCode(plan.getProductCode());
            request.setOrgAssetNo(plan.getOrgAssetNo());
            // 当前资产未转让资产Key
            request.setKey(new CashBillPlanKey(plan.getAssetNo(), plan.getStageSeq(), RepaymentTypeEnum.STAGE));
            request.setExpectPrinciple(plan.getExpectPrinciple());
            request.setExpectInterest(plan.getExpectInterest());
            // LoanFee放款手续费不继承

            request.setExpectDate(plan.getExpectDate());
            request.setLoanMemberId(plan.getLoanMemberId());
            request.setSaleChannel(plan.getSaleChannel());
            request.setPayChannel(plan.getPayChannel());

            // Add
            requestList.add(request);
        }

        return requestList;
    }

    @Override
    public void createCashBillPlanInfo(List<CreateCashBilPlanRequest> requestList) throws BusinessException{

        log.info("开始创建投资人的兑付计划，{}", requestList);

        // 1.校验
        for (CreateCashBilPlanRequest request : requestList) {
            validate(request);
        }
        // 2.按照期号排序
        Collections.sort(requestList);

        // 3.创建
        for (final CreateCashBilPlanRequest request : requestList) {
            try{
                transactionTemplate.execute(new TransactionCallbackWithoutResult() {
                    @Override
                    protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                        storePlan(CashBillPlanConverter.convert(request));
                    }
                });
            }catch (DuplicateKeyException e) {
                log.warn("兑付计划插入重复[{}]", request);
                // 继续处理下一个
                continue;
            }
        }
        // 4.履行平摊
        for (final CreateCashBilPlanRequest request : requestList) {
            process(request.getKey(), CashStatusEnum.INIT);
        }

    }

    // 保存兑付计划及模板，必须保证在事物中
    private void storePlan(CashBillPlan billPlan) {
        // 创建兑付计划模板
        cashBillPlanService.storeCashBillPlan(billPlan);

        // 初始化创建兑付计划
        cashBillPlanService.storeCashPlan(billPlan);
    }

    private void validate(CreateCashBilPlanRequest request) {
        Assert.notNull(request.getKey().getAssetNo(), "资产编码不能为空");
        Assert.notNull(request.getExpectPrinciple(), "预期履行本金不能为空");
        Assert.notNull(request.getExpectInterest(), "预期履行本金不能为空");
        Assert.isTrue(request.getExpectPrinciple().compareTo(BigDecimal.ZERO) > 0
                || request.getExpectPrinciple().compareTo(BigDecimal.ZERO) > 0, "本金和利息至少一项要大于零");
    }

    /**
     * 根据状态处理
     * @param key
     * @param status
     * @throws BusinessException
     */
    private void process(CashBillPlanKey key, CashStatusEnum status) throws BusinessException {
        try{
            // 装载体
            CashPlanHolder.set(new CashPlanHolder.CashPlanCarrier(key));

            // 处理
            CashPlanProcessor processor = cashPlanProcessorFactory.load(status);
            if (processor != null) {
                processor.process();
            }

        } finally {
            CashPlanHolder.clear();
            log.info("兑付计划处理完成，{}", key);
        }
    }

    @Transactional
    @Override
    public void createTransferBillPlanInfo(List<CreditorInfo> creditorInfoList) throws BusinessException{

        Assert.isTrue(CollectionUtils.isNotEmpty(creditorInfoList), "债权转让创建兑付计划处理，未找到债权信息");

        // 循环创建
        for (CreditorInfo creditorInfo : creditorInfoList) {
            log.info("债权转让创建兑付计划处理，转让债权编号：[{}]", creditorInfo.getId());
            // 1、查询该债权下转让人所有持有兑付计划
            List<CashPlan> cashPlanList = cashRecordService.selectByTransferCreditor(creditorInfo);
            Assert.isTrue(CollectionUtils.isNotEmpty(cashPlanList), "未找到债权转让的兑付计划信息");
            // 2、创建受让人兑付计划

            // 3、解锁转让人兑付计划，并更新兑付计划为已转让

            // 4、更新转让人债权状态为已完成

        }
    }

    @Override
    public void performCashPlans(CashBillPlanKey key, final CashAmountSuite actualAmount) throws BusinessException {

        log.info("开始履行兑付计划：KEY={}，AMOUNT={}", key, actualAmount);
        // 1. 校验
        // 2. 检查应收账款模板状态，更新实收
        String lockKey = cashPlanProcessorFactory.getCashAmountLockKey(key.getAssetNo(), key.getStageNo());
        try {
            distributedLockService.tryLock(lockKey);

            CashBillPlan billPlan = cashBillPlanService.load(key, false);
            Assert.notNull(billPlan, "应收账款模板不存在");
            Assert.isTrue(!billPlan.isLockTag(), "应收账款模板被锁定");

            billPlan.setCashAmount(actualAmount.getPrinciple());
            billPlan.setCashIncome(actualAmount.getInterest());

            int rows = cashBillPlanService.restore(billPlan, CashStatusEnum.CASH_WAIT_ACTUAL);
            Assert.isTrue(rows > 0 , "应收账款模板状态必须为待计算实收");

        } catch (Exception e) {
            log.error("履行兑付计划异常", e);
        } finally {
            try {
                distributedLockService.unLockAndDel(lockKey);
            } catch (Exception e1) {
                log.error("解锁兑付金额计算异常" + lockKey , e1);
            }
        }

        // 3.0 计算
        process(key, CashStatusEnum.CASH_WAIT_ACTUAL);
    }

}
