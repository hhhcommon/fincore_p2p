package com.zb.p2p.trade.service.cash;

import com.zb.p2p.trade.api.req.RepayAmountReq;
import com.zb.p2p.trade.common.exception.BusinessException;
import com.zb.p2p.trade.persistence.dto.CreateCashBilPlanRequest;
import com.zb.p2p.trade.persistence.dto.RepayAmountDTO;
import com.zb.p2p.trade.persistence.entity.LoanRequestEntity;
import com.zb.p2p.trade.persistence.entity.RepayBillPlanEntity;

import java.util.List;

/**
 * <p> 还款计划服务 </p>
 *
 * @author Vinson
 * @version RepayBillPlanService.java v1.0 2018/5/31 20:08 Zhengwenquan Exp $
 */

public interface RepayBillPlanService {

    /**
     * 创建还款计划
     * @param loanRequest
     */
    List<RepayBillPlanEntity> createRepayBillPlan(LoanRequestEntity loanRequest) throws BusinessException;

    /**
     * 根据还款计划创建兑付计划创建请求
     * @param loanRequest
     * @return
     */
    List<CreateCashBilPlanRequest> buildCreateCashBillPlanRequest(LoanRequestEntity loanRequest) throws BusinessException;

    /**
     * 根据借款单号和状态查询
     * @param loanNo
     * @param statusList
     * @return
     */
    List<RepayBillPlanEntity> queryRepayBillPlanByLoanAndStatus(String loanNo, List<String> statusList);

    /**
     * 根据资产编号列表查询
     * @param repayAmountReq
     * @return
     */
    List<RepayAmountDTO> selectRepayAmountList(RepayAmountReq repayAmountReq);
}
