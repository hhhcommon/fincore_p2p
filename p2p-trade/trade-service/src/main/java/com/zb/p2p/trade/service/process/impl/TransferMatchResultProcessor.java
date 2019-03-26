package com.zb.p2p.trade.service.process.impl;

import com.zb.p2p.trade.common.domain.CreditorInfo;
import com.zb.p2p.trade.common.domain.MatchRecord;
import com.zb.p2p.trade.common.enums.CreditorStatusEnum;
import com.zb.p2p.trade.common.exception.BusinessException;
import com.zb.p2p.trade.common.queue.model.MatchMqResult;
import com.zb.p2p.trade.persistence.entity.LoanRequestEntity;
import com.zb.p2p.trade.service.contract.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;

import java.util.List;

/**
 * <p> 转让资产匹配结果完成处理 </p>
 *
 * @author Vinson
 * @version TransferMatchResultProcessor.java v1.0 2018/4/20 20:37 Zhengwenquan Exp $
 */
@Component
public class TransferMatchResultProcessor extends BaseMatchResultProcessor {

    @Autowired
    private ContractService contractService;

    @Override
    protected void processSuccess(final LoanRequestEntity loanRequest, final List<MatchRecord> matchRecordList,
                                  MatchMqResult result) throws BusinessException {

        // 信息链处理保证同一个事物
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                // 根据匹配记录创建债权信息
                creditorInfoService.create(loanRequest, matchRecordList);

                // 根据债权信息创建原始资产合同
                contractService.generateDefaultContract(loanRequest, matchRecordList);
            }
        });

        // 查询初始化好的债权信息
        List<CreditorInfo> creditorInfoList = creditorInfoService.findByAssetNoStatus(loanRequest.getTransferCode(),
                CreditorStatusEnum.INIT);

        // 根据债权信息创建兑付计划及模板
        commonProcessor.createTransferBillPlanInfo(creditorInfoList);

        // 履行转让人兑付计划(更新为待放款)

        // 转让资产交易完成，等待支付结果通知

    }

    @Override
    protected void processFailed(LoanRequestEntity loanRequest, MatchMqResult result) throws BusinessException {

    }
}
