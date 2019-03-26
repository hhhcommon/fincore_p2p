package com.zb.p2p.trade.service.process;

import com.zb.p2p.trade.common.domain.CashBillPlanKey;
import com.zb.p2p.trade.common.domain.CashPlan;
import com.zb.p2p.trade.common.domain.CreditorInfo;
import com.zb.p2p.trade.common.exception.BusinessException;
import com.zb.p2p.trade.common.model.CashAmountSuite;
import com.zb.p2p.trade.persistence.dto.CreateCashBilPlanRequest;
import com.zb.p2p.trade.persistence.entity.LoanRequestEntity;

import java.util.List;

/**
 * <p> 描述 </p>
 *
 * @author Vinson
 * @version CommonProcessor.java v1.0 2018/4/21 11:06 Zhengwenquan Exp $
 */
public interface CommonProcessor {

    /**
     * 一次性到期还本付息生成兑付计划生成请求
     * @param loanRequest
     * @return
     * @throws BusinessException
     */
    List<CreateCashBilPlanRequest> buildOneOffCreateCashBillPlanRequest(LoanRequestEntity loanRequest) throws BusinessException;

    /**
     * 根据债权信息生成受让人兑付计划生成请求
     * @param creditorInfo
     * @return
     */
    List<CreateCashBilPlanRequest> buildCreateCashBillPlanRequest(CreditorInfo creditorInfo,
                                                                  List<CashPlan> remainPlans) throws BusinessException;

    /**
     * 创建原始资产兑付计划及模板
     * @param requestList
     */
    void createCashBillPlanInfo(List<CreateCashBilPlanRequest> requestList) throws BusinessException;

    /**
     * 创建转让兑付计划及模板
     * @param creditorInfoList
     * @throws BusinessException
     */
    void createTransferBillPlanInfo(List<CreditorInfo> creditorInfoList) throws BusinessException;

    /**
     * 根据模板key履行兑付计划
     * @param key
     * @param actualAmount
     * @throws BusinessException
     */
    void performCashPlans(CashBillPlanKey key, CashAmountSuite actualAmount) throws BusinessException;
}
