package com.zb.p2p.trade.persistence.converter;

import com.zb.p2p.trade.common.constant.GlobalVar;
import com.zb.p2p.trade.common.domain.CashBillPlanKey;
import com.zb.p2p.trade.common.enums.BillStatusEnum;
import com.zb.p2p.trade.common.enums.RepaymentTypeEnum;
import com.zb.p2p.trade.persistence.dto.CreateCashBilPlanRequest;
import com.zb.p2p.trade.persistence.entity.LoanRequestEntity;
import com.zb.p2p.trade.persistence.entity.RepayBillPlanEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * <p> 还款计划转换工具类 </p>
 *
 * @author Vinson
 * @version RepayBillPlanConverter.java v1.0 2018/5/31 0031 下午 8:34 Zhengwenquan Exp $
 */
public class RepayBillPlanConverter {

    /**
     * 根据账单转换为创建兑付计划请求
     * @param from
     * @return
     */
    public static CreateCashBilPlanRequest convert2CashBillPlanRequest(RepayBillPlanEntity from) {
        // 组装生成兑付计划模板
        CreateCashBilPlanRequest request = new CreateCashBilPlanRequest();
        // 那种产品类型
        // 当前资产Key
        if (from.getStageCount() == 1) {
            request.setKey(new CashBillPlanKey(from.getAssetCode(), 1, RepaymentTypeEnum.CREDITOR));
        } else {
            request.setKey(new CashBillPlanKey(from.getAssetCode(), from.getStageSeq(), RepaymentTypeEnum.STAGE));
        }
        request.setExpectPrinciple(from.getExpectPrinciple());
        request.setExpectInterest(from.getExpectInterest());
        //借款手续费
        request.setLoanChargeFee(from.getRepaymentFee());
        request.setProductCode(from.getProductCode());
        request.setOrgAssetNo(from.getOrgAssetCode());
        request.setExpectDate(from.getBillEndDate());
        request.setLoanMemberId(from.getMemberId());
        request.setSaleChannel(from.getSaleChannel());
        request.setPayChannel(from.getPayChannel());

        return request;
    }

    /**
     * 批量
     * @param fromList
     * @return
     */
    public static List<CreateCashBilPlanRequest> convert2CashBillPlanRequestList(List<RepayBillPlanEntity> fromList) {
        List<CreateCashBilPlanRequest> requestList = new ArrayList<>();
        for (RepayBillPlanEntity billPlanEntity : fromList) {
            requestList.add(convert2CashBillPlanRequest(billPlanEntity));
        }
        return requestList;
    }

    public static void initBillCommonProperties(LoanRequestEntity loanRequest, RepayBillPlanEntity to) {

        to.setLoanNo(loanRequest.getLoanNo());
        to.setAssetCode(loanRequest.getTransferCode());
        to.setMemberId(loanRequest.getMemberId());
        to.setOrgAssetCode(loanRequest.getOriginalAssetCode());
        to.setPlatformId(GlobalVar.SYS_IDENTIFY_CODE);
        to.setSaleChannel(loanRequest.getSaleChannel());
        to.setPayChannel(loanRequest.getPayChannel());
        to.setProductCode(loanRequest.getProductCode());
        to.setStageCount(loanRequest.getLoanStage());
        to.setStatus(BillStatusEnum.INIT.getCode());
        to.setVersion(1);
    }
}
