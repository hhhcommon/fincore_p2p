package com.zb.p2p.trade.service.cash.impl;

import com.zb.p2p.trade.api.req.RepayAmountReq;
import com.zb.p2p.trade.common.constant.GlobalVar;
import com.zb.p2p.trade.common.enums.RepaymentTypeEnum;
import com.zb.p2p.trade.persistence.converter.RepayBillPlanConverter;
import com.zb.p2p.trade.persistence.dao.RepayBillPlanMapper;
import com.zb.p2p.trade.persistence.dto.CreateCashBilPlanRequest;
import com.zb.p2p.trade.persistence.dto.RepayAmountDTO;
import com.zb.p2p.trade.persistence.entity.LoanRequestEntity;
import com.zb.p2p.trade.persistence.entity.RepayBillPlanEntity;
import com.zb.p2p.trade.service.assigner.CashPlanAssignerFactory;
import com.zb.p2p.trade.service.cash.RepayBillPlanService;
import com.zb.p2p.trade.service.config.ReadOnlyConnection;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p> 描述 </p>
 *
 * @author Vinson
 * @version RepayBillPlanServiceImpl.java v1.0 2018/5/31 0031 下午 8:55 Zhengwenquan Exp $
 */
@Slf4j
@Service
public class RepayBillPlanServiceImpl implements RepayBillPlanService {

    @Autowired
    private CashPlanAssignerFactory cashPlanAssignerFactory;
    @Autowired
    private RepayBillPlanMapper repayBillPlanMapper;

    @Override
    public List<RepayBillPlanEntity> createRepayBillPlan(LoanRequestEntity loanRequest) {
        log.info("开始创建还款计划，借款单号：[{}],总期数：[{}]", loanRequest.getLoanNo(), loanRequest.getLoanStage());

        // 校验
        validate(loanRequest);

        // 执行
        RepaymentTypeEnum repaymentType = RepaymentTypeEnum.getByRepayType(loanRequest.getRepaymentType());
        List<RepayBillPlanEntity> billPlanList = cashPlanAssignerFactory.loadBillAssigner(repaymentType).createBill(loanRequest);

        // 落地
        try {
            repayBillPlanMapper.batchInsert(billPlanList);

        } catch (DuplicateKeyException e) {
            log.warn("重复创建还款计划，重复结果：", e.getCause());
            // 查询获取
            billPlanList = repayBillPlanMapper.selectByLoanNoStatus(loanRequest.getLoanNo(), GlobalVar.REPAY_BILL_INIT_STATUS);
        }

        return billPlanList;
    }

    private void validate(LoanRequestEntity loanRequest) {
        Assert.isTrue(loanRequest.getLoanFeeRate() != null
                && loanRequest.getLoanRate().compareTo(BigDecimal.ZERO) > 0, "借款预期年化收益率不能小于等于0");
        Assert.notNull(loanRequest.getPayChannel(), "支付渠道不能为空");
    }

    @Override
    public List<CreateCashBilPlanRequest> buildCreateCashBillPlanRequest(LoanRequestEntity loanRequest) {
        // 1.获取原始资产的还款计划
        List<RepayBillPlanEntity> repayPlanList = repayBillPlanMapper.selectByLoanNoStatus(
                loanRequest.getLoanNo(), GlobalVar.REPAY_BILL_INIT_STATUS);
        Assert.isTrue(repayPlanList != null && repayPlanList.size() > 0, "未获取到资产还款计划");

        // 2.根据还款计划组装
        return RepayBillPlanConverter.convert2CashBillPlanRequestList(repayPlanList);
    }


    @Override
    public List<RepayBillPlanEntity> queryRepayBillPlanByLoanAndStatus(String loanNo, List<String> statusList) {
        if (StringUtils.isEmpty(loanNo)) {
            return null;
        }
        return repayBillPlanMapper.selectByLoanNoStatus(loanNo, GlobalVar.REPAY_BILL_INIT_STATUS);
    }

    @Override
    @ReadOnlyConnection
    @Cacheable(value = "Repay_Principle_Interest_Cache", key = "'Repay'.concat(#req.generateCacheKey())")
    public List<RepayAmountDTO> selectRepayAmountList(RepayAmountReq req) {
        log.info("从从数据库查询数据结果", req.generateCacheKey());
        return repayBillPlanMapper.selectRepayAmountList(req.getAssetCodeList());
    }
}
