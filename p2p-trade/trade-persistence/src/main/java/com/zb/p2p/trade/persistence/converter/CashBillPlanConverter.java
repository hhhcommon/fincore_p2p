package com.zb.p2p.trade.persistence.converter;

import com.zb.p2p.trade.common.domain.CashBillPlan;
import com.zb.p2p.trade.common.enums.CashOverdueEnum;
import com.zb.p2p.trade.common.enums.CashStatusEnum;
import com.zb.p2p.trade.common.enums.RepaymentTypeEnum;
import com.zb.p2p.trade.common.enums.YesNoEnum;
import com.zb.p2p.trade.persistence.dto.CreateCashBilPlanRequest;
import com.zb.p2p.trade.persistence.entity.CashBillPlanEntity;

import java.util.Date;

/**
 * <p> 兑付计划模板转换工具 </p>
 *
 * @author Vinson
 * @version CashBillPlanConverter.java v1.0 2018/4/21 11:49 Zhengwenquan Exp $
 */
public class CashBillPlanConverter {

    public static CashBillPlan convert(CashBillPlanEntity from){

        CashBillPlan to = new CashBillPlan();
        to.setId(from.getId());
        to.setAssetNo(from.getAssetNo());
        to.setCashAmount(from.getCashAmount());
        to.setCashDate(from.getCashDate());
        to.setCashIncome(from.getCashIncome());
        to.setCashStatus(CashOverdueEnum.getByCode(from.getCashStatus()));
        to.setCreateTime(from.getCreateTime());
        to.setExpectDate(from.getExpectDate());
        to.setExpectInterest(from.getExpectInterest());
        to.setExpectPrinciple(from.getExpectPrinciple());
        to.setLoanMemberId(from.getLoanMemberId());
        to.setLockTag(YesNoEnum.getBooleanByCode(from.getLockTag()));
        to.setModifyTime(from.getModifyTime());
        to.setProductCode(from.getProductCode());
        to.setRepayType(RepaymentTypeEnum.getByRepayType(from.getRepayType()));
        to.setStatus(CashStatusEnum.getByCode(from.getStatus()));
        to.setOrgAssetNo(from.getOrgAssetNo());
        to.setStageSeq(from.getStageSeq());

        to.setVersion(from.getVersion());
        to.setSaleChannel(from.getSaleChannel());
        to.setPayChannel(from.getPayChannel());

        return to;
    }

    public static CashBillPlanEntity convert(CashBillPlan from){

        CashBillPlanEntity to = new CashBillPlanEntity();
        to.setId(from.getId());

        to.setAssetNo(from.getAssetNo());
        to.setCashAmount(from.getCashAmount());
        to.setCashDate(from.getCashDate());
        to.setCashIncome(from.getCashIncome());
        to.setCreateTime(from.getCreateTime());
        to.setExpectDate(from.getExpectDate());
        to.setExpectInterest(from.getExpectInterest());
        to.setExpectPrinciple(from.getExpectPrinciple());
        to.setLoanMemberId(from.getLoanMemberId());
        to.setLockTag(from.isLockTag() ? 1 : 0);
        to.setModifyTime(from.getModifyTime());
        to.setOrgAssetNo(from.getOrgAssetNo());
        to.setProductCode(from.getProductCode());
        if (from.getRepayType() != null) {
            to.setRepayType(from.getRepayType().getCode());
        }
        if (from.getStatus() != null) {
            to.setStatus(from.getStatus().getCode());
        } else {
            to.setStatus(CashStatusEnum.INIT.getCode());
        }
        if (from.getCashStatus() != null) {
            to.setCashStatus(from.getCashStatus().getCode());
        } else {
            to.setCashStatus(CashOverdueEnum.NORMAL.getCode());
        }
        to.setStageSeq(from.getStageSeq());

        to.setVersion(from.getVersion());
        to.setSaleChannel(from.getSaleChannel());
        to.setPayChannel(from.getPayChannel());

        return to;
    }

    /**
     * 兑付生成请求转为兑付计划模板
     * @param from
     * @return
     */
    public static CashBillPlan convert(CreateCashBilPlanRequest from){
        CashBillPlan to = new CashBillPlan();

        // 多个债权Id对应一个应收款
        to.setAssetNo(from.getKey().getAssetNo());
        to.setStageSeq(from.getKey().getStageNo());
        to.setRepayType(from.getKey().getRepaymentType());
        to.setCashStatus(CashOverdueEnum.NORMAL);
        to.setExpectDate(from.getExpectDate());
        to.setExpectInterest(from.getExpectInterest());
        to.setExpectPrinciple(from.getExpectPrinciple());
        to.setLoanMemberId(from.getLoanMemberId());
        to.setLockTag(false);
        to.setOrgAssetNo(from.getOrgAssetNo());
        to.setProductCode(from.getProductCode());
        to.setSaleChannel(from.getSaleChannel());
        to.setPayChannel(from.getPayChannel());
        // 初始
        to.setStatus(CashStatusEnum.INIT);
        to.setVersion(1);
        to.setCreateTime(new Date());

        return to;
    }

}
