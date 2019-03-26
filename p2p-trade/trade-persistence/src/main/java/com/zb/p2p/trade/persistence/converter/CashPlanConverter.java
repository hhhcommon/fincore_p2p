package com.zb.p2p.trade.persistence.converter;

import com.zb.fincore.common.utils.CollectionUtils;
import com.zb.p2p.trade.common.domain.CashPlan;
import com.zb.p2p.trade.common.enums.*;
import com.zb.p2p.trade.persistence.entity.CashRecordEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <p> 兑付计划转换工具 </p>
 *
 * @author Vinson
 * @version CashPlanConverter.java v1.0 2018/4/21 11:49 Zhengwenquan Exp $
 */
public class CashPlanConverter {

    public static CashPlan convert(CashRecordEntity from){

        CashPlan to = new CashPlan();
        to.setId(from.getId());
        to.setAssetNo(from.getAssetNo());
        to.setStageSeq(from.getStageSeq());
        to.setBillPlanId(from.getBillPlanId());
        to.setCashAmount(from.getCashAmount());
        to.setCashDate(from.getCashDate());
        to.setCashIncome(from.getCashIncome());
        to.setCashStatus(CashOverdueEnum.getByCode(from.getCashStatus()));
        to.setCreateTime(from.getCreateTime());
        to.setExpectDate(from.getExpectDate());
        to.setExpectInterest(from.getExpectInterest());
        to.setExpectPrinciple(from.getExpectPrinciple());
        to.setExtOrderNo(from.getExtOrderNo());
        to.setLoanMemberId(from.getLoanMemberId());
        to.setLockTag(YesNoEnum.getBooleanByCode(from.getLockTag()));
        to.setMemberId(from.getMemberId());
        to.setModifyTime(from.getModifyTime());
        to.setOrgAssetNo(from.getOrgAssetNo());
        to.setPayingInterest(from.getPayingInterest());
        to.setProductCode(from.getProductCode());
        to.setCreditorId(from.getRefNo());
        to.setRepayType(RepaymentTypeEnum.getByRepayType(from.getRepayType()));
        to.setReqNo(from.getReqNo());
        to.setSaleChannel(from.getSaleChannel());
        to.setStatus(CashStatusEnum.getByCode(from.getStatus()));

        to.setVersion(from.getVersion());
        to.setPayChannel(from.getPayChannel());

        return to;
    }

    public static CashRecordEntity convert(CashPlan from){

        CashRecordEntity to = new CashRecordEntity();
        to.setId(from.getId());
        to.setAssetNo(from.getAssetNo());
        to.setStageSeq(from.getStageSeq());
        to.setBillPlanId(from.getBillPlanId());
        to.setCashAmount(from.getCashAmount());
        to.setCashDate(from.getCashDate());
        to.setCashIncome(from.getCashIncome());
        if (from.getCashStatus() != null) {
            to.setCashStatus(from.getCashStatus().getCode());
        }
        to.setCreateTime(from.getCreateTime());
        to.setExpectDate(from.getExpectDate());
        to.setExpectInterest(from.getExpectInterest());
        to.setExpectPrinciple(from.getExpectPrinciple());
        to.setExtOrderNo(from.getExtOrderNo());
        to.setLoanMemberId(from.getLoanMemberId());
        to.setLockTag(from.isLockTag() ? 1 : 0);
        to.setMemberId(from.getMemberId());
        to.setModifyTime(from.getModifyTime());
        to.setPayingInterest(from.getPayingInterest());
        to.setProductCode(from.getProductCode());
        to.setRefNo(from.getCreditorId());
        if (from.getRepayType() != null) {
            to.setRepayType(from.getRepayType().getCode());
        }
        to.setReqNo(from.getReqNo());
        to.setSaleChannel(from.getSaleChannel());
        if (from.getStatus() != null) {
            to.setStatus(from.getStatus().getCode());
        }
        to.setOrgAssetNo(from.getOrgAssetNo());


        to.setVersion(from.getVersion());
        to.setPayChannel(from.getPayChannel());

        return to;
    }

    public static List<CashPlan> convertToList(List<CashRecordEntity> fromList){

        if (CollectionUtils.isNullOrEmpty(fromList)) {
            return Collections.emptyList();
        }
        List<CashPlan> toList = new ArrayList<>();
        for (CashRecordEntity infoEntity : fromList) {
            toList.add(convert(infoEntity));
        }
        return toList;
    }

    public static List<CashRecordEntity> convertToEntityList(List<CashPlan> fromList){

        if (CollectionUtils.isNullOrEmpty(fromList)) {
            return Collections.emptyList();
        }
        List<CashRecordEntity> toList = new ArrayList<>();
        for (CashPlan infoEntity : fromList) {
            toList.add(convert(infoEntity));
        }
        return toList;
    }

    public static List<String> convert2Member(List<CashPlan> planList) {
        if (CollectionUtils.isNullOrEmpty(planList)) {
            return null;
        }

        List<String> memberList = new ArrayList<>();
        for (CashPlan plan : planList) {
            memberList.add(plan.getMemberId());
        }

        return memberList;
    }

    public static List<String> convert2InvestOrder(List<CashPlan> planList) {
        if (CollectionUtils.isNullOrEmpty(planList)) {
            return null;
        }

        List<String> orderList = new ArrayList<>();
        for (CashPlan plan : planList) {
            orderList.add(plan.getExtOrderNo());
        }

        return orderList;
    }

    /**
     * 根据借款类型转换
     * @param loanType
     * @return
     */
    public static String convert2MemberType(String loanType) {
        if (LoanTypeEnum.PERSONAL.getCode().equals(loanType)) {
            return "10";
        }else if (LoanTypeEnum.COMPANY.getCode().equals(loanType)) {
            return "20";
        }
        return null;
    }

    /**
     * 还款类型 CASH-兑付，REINVEST_CASH -复投,FEE -手续费
     * @param repayType
     * @return
     */
    public static String convert2TradeType(String repayType){
        RepaymentTypeEnum repaymentType = RepaymentTypeEnum.getByRepayType(repayType);
        if (RepaymentTypeEnum.STAGE == repaymentType) {
            return "REINVEST_CASH";
        }
        return "CASH";
    }
}
