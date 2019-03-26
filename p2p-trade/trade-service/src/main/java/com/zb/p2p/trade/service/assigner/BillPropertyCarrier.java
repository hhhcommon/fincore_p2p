package com.zb.p2p.trade.service.assigner;

import com.zb.p2p.trade.common.enums.RepaymentTypeEnum;
import com.zb.p2p.trade.common.model.CashAmountSuite;
import com.zb.p2p.trade.persistence.entity.RepayBillPlanEntity;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;

/**
 * <p>账单属性持有器</p>
 * @author zhou.liu
 * @version $Id: BillPropertyCarrier.java, v 0.1 2015年4月23日 下午1:38:15 Administrator Exp $
 */
public class BillPropertyCarrier {
    /**
     * 当前账单金额
     */
    private CashAmountSuite currentBillDebtAmount = new CashAmountSuite();
    /**
     * 协议总金额
     */
    private CashAmountSuite totalDebtAmount       = new CashAmountSuite();
    /**
     * 当前账单期号
     */
    private Integer    currentIssueNumber    = 1;
    /**
     * 总期数
     */
    private Integer totalStageNumber;
    /**
     * 当前账单开始时间
     */
    private Date currentBillStartDate;
    /**
     * 当前账单还款时间
     */
    private Date currentBillEndTime;
    /**
     * 已分配本金
     */
    private BigDecimal assignedPrincipal = BigDecimal.ZERO;
    /**
     * 已分配利息
     */
    private BigDecimal assignedInterest  = BigDecimal.ZERO;
    /**
     * 账单类型
     */
    private RepaymentTypeEnum repaymentType;

    /**
     * 已分配手续费
     */
    private BigDecimal assignedFee = BigDecimal.ZERO;

    public BigDecimal getCurrentBillAverageInterest() {
        BigDecimal interest = calcAverage(this.getTotalDebtAmount().getInterest(),
                new BigDecimal(this.getTotalStageNumber()), assignedInterest, isLastIssueNum());
        this.getCurrentBillDebtAmount().setInterest(interest);

        //累加已分配利息
        this.addUpAssignedInterest();
        return interest;
    }

    public BigDecimal getCurrentBillAveragePrincipal() {
        BigDecimal principle = calcAverage(this.getTotalDebtAmount().getPrinciple(),
                new BigDecimal(this.getTotalStageNumber()), assignedPrincipal, isLastIssueNum());
        this.getCurrentBillDebtAmount().setPrinciple(principle);

        //累加已分配本金
        this.addUpAssignedPrincipal();
        return principle;
    }

    public BigDecimal getCurrentBillAverageFee() {
        BigDecimal fee = calcAverage(this.getTotalDebtAmount().getFee(),
                new BigDecimal(this.getTotalStageNumber()), assignedFee, isLastIssueNum());
        this.getCurrentBillDebtAmount().setFee(fee);

        //累加已分配手续费
        this.addUpAssignedFee();
        return fee;
    }

    public void addUpAssignedPrincipal() {
        this.setAssignedPrincipal(this.getAssignedPrincipal().add(this.getCurrentBillDebtAmount().getPrinciple()));
    }

    public void addUpAssignedInterest() {
        this.setAssignedInterest(this.getAssignedInterest().add(this.getCurrentBillDebtAmount().getInterest()));
    }

    public void addUpAssignedFee() {
        this.setAssignedFee(this.getAssignedFee().add(this.getCurrentBillDebtAmount().getFee()));
    }

    public void addUpIssueNum() {
        ++this.currentIssueNumber;
    }

    public boolean isFirstIssueNum() {
        return currentIssueNumber == 1;
    }

    public boolean isLastIssueNum() {
        return currentIssueNumber == totalStageNumber;
    }

    public CashAmountSuite getCurrentBillDebtAmount() {
        return currentBillDebtAmount;
    }

    public void setCurrentBillDebtAmount(CashAmountSuite currentBillDebtAmount) {
        this.currentBillDebtAmount = currentBillDebtAmount;
    }

    public CashAmountSuite getTotalDebtAmount() {
        return totalDebtAmount;
    }

    public void setTotalDebtAmount(CashAmountSuite totalDedtAmount) {
        this.totalDebtAmount = totalDedtAmount;
    }

    public Integer getCurrentIssueNumber() {
        return currentIssueNumber;
    }

    public void setCurrentIssueNumber(Integer currentIssueNumber) {
        this.currentIssueNumber = currentIssueNumber;
    }

    public Integer getTotalStageNumber() {
        return totalStageNumber;
    }

    public void setTotalStageNumber(Integer totalStageNumber) {
        this.totalStageNumber = totalStageNumber;
    }

    public Date getCurrentBillStartDate() {
        return currentBillStartDate;
    }

    public void setCurrentBillStartDate(Date currentBillStartDate) {
        this.currentBillStartDate = currentBillStartDate;
    }

    public Date getCurrentBillEndTime() {
        return currentBillEndTime;
    }

    public void setCurrentBillEndTime(Date currentBillEndTime) {
        this.currentBillEndTime = currentBillEndTime;
    }

    public Date getPreBillEndTime(List<RepayBillPlanEntity> billList) {
        Assert.isTrue(this.currentIssueNumber > 1, "第一期账单不存在上期账单还款时间");
        return billList.get(this.currentIssueNumber - 2).getBillEndDate();
    }

    public BigDecimal getAssignedPrincipal() {
        return assignedPrincipal;
    }

    public void setAssignedPrincipal(BigDecimal assignedPrincipal) {
        this.assignedPrincipal = assignedPrincipal;
    }

    public BigDecimal getAssignedInterest() {
        return assignedInterest;
    }

    public void setAssignedInterest(BigDecimal assignedInterest) {
        this.assignedInterest = assignedInterest;
    }


    public BigDecimal getAssignedFee() {
        return assignedFee;
    }

    public void setAssignedFee(BigDecimal assignedFee) {
        this.assignedFee = assignedFee;
    }

    public RepaymentTypeEnum getRepaymentType() {
        return repaymentType;
    }

    public void setRepaymentType(RepaymentTypeEnum repaymentType) {
        this.repaymentType = repaymentType;
    }

    /**
     * 计算平均收益
     * @param total
     * @param duration
     * @param assigned
     * @return
     */
    public static BigDecimal calcAverage(BigDecimal total, BigDecimal duration, BigDecimal assigned,
                                    boolean isLastIssueNum) {
        //保留2位小数，向下取整
        BigDecimal result = total.divide(duration, 2, RoundingMode.DOWN);

        if (isLastIssueNum) {
            return total.subtract(assigned);
        }

        return result;
    }
}