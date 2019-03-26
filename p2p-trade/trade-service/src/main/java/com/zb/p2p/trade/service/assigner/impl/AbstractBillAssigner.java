package com.zb.p2p.trade.service.assigner.impl;

import com.zb.p2p.trade.common.constant.GlobalVar;
import com.zb.p2p.trade.common.enums.LoanPaymentStatusEnum;
import com.zb.p2p.trade.common.model.CashAmountSuite;
import com.zb.p2p.trade.persistence.converter.RepayBillPlanConverter;
import com.zb.p2p.trade.persistence.entity.LoanRequestEntity;
import com.zb.p2p.trade.persistence.entity.RepayBillPlanEntity;
import com.zb.p2p.trade.service.assigner.BillAssigner;
import com.zb.p2p.trade.service.assigner.BillPropertyCarrier;
import com.zb.p2p.trade.service.order.BasicDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * <p> 还款计划分配器抽象 </p>
 *
 * @author Vinson
 * @version AbstractBillAssigner.java v1.0 2018/5/31 0031 下午 9:09 Zhengwenquan Exp $
 */
public class AbstractBillAssigner implements BillAssigner {

    @Autowired
    private BasicDataService basicDataService;

    protected ThreadLocal<BillPropertyCarrier> billPropertyHolder = new ThreadLocal<>();

    @Override
    public List<RepayBillPlanEntity> createBill(LoanRequestEntity loanRequest) {

        // 获取每日费率
        BigDecimal dayRate = loanRequest.getLoanRate().divide(GlobalVar.YEAR_DAYS_365, 8, BigDecimal.ROUND_DOWN);
        BigDecimal loanInterest = loanRequest.getMatchedAmount()
                .multiply(dayRate)
                .multiply(new BigDecimal(loanRequest.getLockDate()))
                .setScale(2, BigDecimal.ROUND_DOWN);

        // 设置总本金、利息及手续费
        CashAmountSuite amountSuite = new CashAmountSuite(loanRequest.getMatchedAmount(), loanInterest);
        amountSuite.setFee(calcRepaymentFee(loanRequest));

        List<RepayBillPlanEntity> billPlanList = generateBill(loanRequest, amountSuite);

        // 保存实际利息、总手续费及状态为匹配成功
        LoanRequestEntity updateLoanRequest = new LoanRequestEntity();
        updateLoanRequest.setId(loanRequest.getId());
        updateLoanRequest.setLoanPaymentStatus(LoanPaymentStatusEnum.LOAN_MATCH_SUCCESS.getCode());
        updateLoanRequest.setActualLoanInterests(amountSuite.getInterest());
        updateLoanRequest.setTotalLoanCharge(amountSuite.getFee());
        int rows = basicDataService.updateLoanRequest(updateLoanRequest);

        Assert.isTrue(rows == 1, "更新借款申请单信息失败");
        return billPlanList;
    }

    private List<RepayBillPlanEntity> generateBill(LoanRequestEntity loanRequest, CashAmountSuite amountSuite) {

        List<RepayBillPlanEntity> billPlanList = constructRepayBill(loanRequest);
        try {
            BillPropertyCarrier carrier = new BillPropertyCarrier();
            carrier.setTotalDebtAmount(amountSuite);
            carrier.setTotalStageNumber(loanRequest.getLoanStage());
            carrier.setCurrentBillStartDate(loanRequest.getValueStartTime());
            billPropertyHolder.set(carrier);
            for (RepayBillPlanEntity bill : billPlanList) {
                // 设置账单日期
                setBillDate(bill, billPlanList, loanRequest);

                // 本金利息手续费等个性化加工
                reBuildRepayBillPlan(loanRequest, bill);

                // 累计期号
                billPropertyHolder.get().addUpIssueNum();
            }
        } finally {
            billPropertyHolder.remove();
        }

        return billPlanList;
    }

    private List<RepayBillPlanEntity> constructRepayBill(LoanRequestEntity loanRequest) {
        Integer stageNumber = loanRequest.getLoanStage();

        List<RepayBillPlanEntity> billPlanList = new ArrayList<>();
        for (int i = 0; i < stageNumber; i++) {
            RepayBillPlanEntity billPlanEntity = new RepayBillPlanEntity();

            RepayBillPlanConverter.initBillCommonProperties(loanRequest, billPlanEntity);

            billPlanList.add(billPlanEntity);
        }
        return billPlanList;
    }

    private void setBillDate(RepayBillPlanEntity bill, List<RepayBillPlanEntity> billPlanList, LoanRequestEntity loanRequest) {
        if (billPropertyHolder.get().isFirstIssueNum()) {
            billPropertyHolder.get().setCurrentBillStartDate(loanRequest.getValueStartTime());
        } else {
            billPropertyHolder.get().setCurrentBillStartDate(billPropertyHolder.get().getPreBillEndTime(billPlanList));
        }

        // 计算账单还款日，最后一期，则还款结束日为项目到期时间，否则为当期账单日+1月
        if (billPropertyHolder.get().isLastIssueNum()){
            billPropertyHolder.get().setCurrentBillEndTime(loanRequest.getValueEndTime());
        } else {
            billPropertyHolder.get().setCurrentBillEndTime(loanRequest.getValueEndTime());
            // ToDo 分期
        }

        // 账单期号,账单期限类型默认为月
        bill.setStageSeq(billPropertyHolder.get().getCurrentIssueNumber());

        // 账单起始日
        bill.setBillStartDate(billPropertyHolder.get().getCurrentBillStartDate());
        bill.setBillEndDate(billPropertyHolder.get().getCurrentBillEndTime());

    }

    protected void reBuildRepayBillPlan(LoanRequestEntity loanRequest, RepayBillPlanEntity billPlanEntity) {
    }

    private BigDecimal calcRepaymentFee(LoanRequestEntity loanRequest) {
        BigDecimal loanFeeRate = loanRequest.getLoanFeeRate();
        Assert.isTrue(loanFeeRate != null && loanFeeRate.compareTo(BigDecimal.ZERO) >= 0, "借款手续费费率不合法");
        // 每日手续费率
        BigDecimal dayFeeRate = loanFeeRate.divide(GlobalVar.YEAR_DAYS_365, 8, BigDecimal.ROUND_DOWN);
        BigDecimal loanFee = loanRequest.getMatchedAmount()
                .multiply(dayFeeRate)
                .multiply(new BigDecimal(loanRequest.getLockDate()))
                .setScale(2, BigDecimal.ROUND_DOWN);

        return loanFee;
    }

}
