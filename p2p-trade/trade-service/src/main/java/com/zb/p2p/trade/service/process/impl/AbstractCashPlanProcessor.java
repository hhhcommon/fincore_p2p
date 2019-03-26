package com.zb.p2p.trade.service.process.impl;

import com.zb.fincore.common.enums.ResultCodeEnum;
import com.zb.p2p.trade.common.domain.CashBillPlan;
import com.zb.p2p.trade.common.domain.CashBillPlanKey;
import com.zb.p2p.trade.common.enums.CashAmountTypeEnum;
import com.zb.p2p.trade.common.enums.CashStatusEnum;
import com.zb.p2p.trade.common.exception.BusinessException;
import com.zb.p2p.trade.common.model.CashAmountSuite;
import com.zb.p2p.trade.common.model.CashPlanHolder;
import com.zb.p2p.trade.service.assigner.CashPlanAssignerFactory;
import com.zb.p2p.trade.service.cash.CashBillPlanService;
import com.zb.p2p.trade.service.cash.CashRecordService;
import com.zb.p2p.trade.service.process.CashPlanProcessor;
import com.zb.p2p.trade.service.process.CashPlanProcessorFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.Assert;

import javax.annotation.Resource;

/**
 * <p> 兑付金额处理抽象 </p>
 *
 * @author Vinson
 * @version AbstractCashPlanProcessor.java v1.0 2018/4/23 19:21 Zhengwenquan Exp $
 */
public abstract class AbstractCashPlanProcessor implements CashPlanProcessor{

    @Autowired
    private CashPlanProcessorFactory cashPlanProcessorFactory;
    @Autowired
    private CashPlanAssignerFactory cashPlanAssignerFactory;
    @Resource
    private TransactionTemplate transactionTemplate;
    @Autowired
    private CashBillPlanService cashBillPlanService;
    @Autowired
    private CashRecordService cashRecordService;

    @Override
    public void process() throws BusinessException {
        // 1、初始化模板
        CashBillPlan billPlan ;

        // 校验及锁定
        try {
            billPlan = transactionTemplate.execute(transactionStatus -> prepare());

            Assert.notNull(billPlan, "兑付计划应收账款模板不存在");

        } catch (IllegalArgumentException e) {
            throw  new BusinessException(ResultCodeEnum.PARAMS_VALIDATE_FAIL.code(), e.getMessage());
        }

        // 处理
        CashStatusEnum nextStatus = process(billPlan);


        // 更新模板结果
        if (nextStatus != null) {
            // 全部处理完成后继续
            if (cashRecordService.isAllFinished(billPlan.getId(), nextStatus)) {
                billPlan.setStatus(nextStatus);

            } else {
                nextStatus = null;
            }
        }
        billPlan.setLockTag(false);
        // 更新并解锁
        cashBillPlanService.restore(billPlan, getExpectStatus());

        // 处理终止
        if (nextStatus == null || !nextStatus.isAutoNext()) {
            return;
        }

        // 下一个处理
        CashPlanProcessor processor = cashPlanProcessorFactory.load(nextStatus);
        if (processor != null) {
            processor.process();
        }

    }

    private CashBillPlan prepare() {
        CashBillPlan billPlan = cashBillPlanService.load(CashPlanHolder.get().getKey(), true);
        Assert.notNull(billPlan, "兑付计划应收账款模板不存在");
        Assert.isTrue(!billPlan.isLockTag(), "兑付计划应收账款模板已被锁定");
        Assert.isTrue(billPlan.getStatus() == getExpectStatus(), "兑付计划应收账款模板状态必须为：" + getExpectStatus());

        return billPlan;
    }

    protected void assign(CashBillPlan billPlan, CashAmountTypeEnum cashAmountType) throws BusinessException{
        try{
            CashPlanHolder.get().setAmountType(cashAmountType);

            CashBillPlanKey key = new CashBillPlanKey(billPlan.getAssetNo(), billPlan.getStageSeq(), billPlan.getRepayType());
            CashAmountSuite cashAmountSuite = new CashAmountSuite(billPlan.getExpectPrinciple(), billPlan.getExpectInterest());

            cashPlanAssignerFactory.loadCashAssigner(billPlan, cashAmountType).assign(key, cashAmountSuite);

        } catch (IllegalArgumentException e) {
            throw new BusinessException(ResultCodeEnum.PARAMS_VALIDATE_FAIL.code(), e.getMessage());
        } catch (IllegalStateException e) {
            throw new BusinessException(ResultCodeEnum.DATA_PROCESSING.code(), e.getMessage());
        }
    }

    protected abstract CashStatusEnum process(CashBillPlan billPlan) throws BusinessException;

    /**
     * 设定处理的期望状态
     * @return
     */
    protected abstract CashStatusEnum getExpectStatus();
}
