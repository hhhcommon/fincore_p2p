package com.zb.p2p.trade.service.process.impl;

import com.zb.p2p.trade.common.domain.MatchRecord;
import com.zb.p2p.trade.common.enums.LoanPaymentStatusEnum;
import com.zb.p2p.trade.common.exception.BusinessException;
import com.zb.p2p.trade.common.queue.model.MatchMqResult;
import com.zb.p2p.trade.persistence.dto.CreateCashBilPlanRequest;
import com.zb.p2p.trade.persistence.entity.LoanRequestEntity;
import com.zb.p2p.trade.service.cash.RepayBillPlanService;
import com.zb.p2p.trade.service.contract.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.util.Assert;

import java.util.List;

/**
 * <p> 原始资产匹配完成结果处理 </p>
 *
 * @author Vinson
 * @version DefaultMatchResultProcessor.java v1.0 2018/4/20 20:37 Zhengwenquan Exp $
 */
@Component
public class DefaultMatchResultProcessor extends BaseMatchResultProcessor {

    @Autowired
    private ContractService contractService;
    @Autowired
    private RepayBillPlanService repayBillPlanService;

    @Override
    protected void processSuccess(final LoanRequestEntity loanRequest, final List<MatchRecord> matchRecordList,
                                  MatchMqResult result) throws BusinessException {
        // 校验
        validate(loanRequest);

        // 信息链处理保证同一个事物
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                // 根据匹配记录创建债权信息
                creditorInfoService.create(loanRequest, matchRecordList);

                // 生成还款计划
                repayBillPlanService.createRepayBillPlan(loanRequest);
            }
        });

        // 根据债权信息创建应收账款及兑付计划
        List<CreateCashBilPlanRequest> requestList = repayBillPlanService.buildCreateCashBillPlanRequest(loanRequest);
        commonProcessor.createCashBillPlanInfo(requestList);

        // 根据债权信息创建原始资产合同元数据
        contractService.generateDefaultContract(loanRequest, matchRecordList);

        // 通知兑付计划已生成完成并更新持仓
        creditorInfoService.updateCreditorAmountExpect(matchRecordList);
    }

    private void validate(LoanRequestEntity loanRequest) {
        LoanPaymentStatusEnum loanPaymentStatus = LoanPaymentStatusEnum.getByCode(loanRequest.getLoanPaymentStatus());
        Assert.isTrue(loanPaymentStatus == null
                || LoanPaymentStatusEnum.LOAN_MATCH_SUCCESS == loanPaymentStatus, "借款申请单的放款状态必须为匹配成功");
    }

    @Override
    protected void processFailed(LoanRequestEntity loanRequest, MatchMqResult result) throws BusinessException {

    }
}
