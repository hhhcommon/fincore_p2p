package com.zb.p2p.trade.service.task;

import com.taobao.pamirs.schedule.TaskItemDefine;
import com.zb.p2p.trade.common.domain.CashBillPlan;
import com.zb.p2p.trade.common.enums.CashStatusEnum;
import com.zb.p2p.trade.common.exception.BusinessException;
import com.zb.p2p.trade.common.util.DateUtil;
import com.zb.p2p.trade.persistence.entity.CashBillPlanEntity;
import com.zb.p2p.trade.service.cash.CashBillPlanService;
import com.zb.p2p.trade.service.cash.CashRecordService;
import com.zb.p2p.trade.service.common.AbstractScheduleTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * <p> 兑付计划兑付发放任务(发放本息) </p>
 *
 * @author Vinson
 * @version CashPlanPayTask.java v1.0 2018/3/8 14:20 Zhengwenquan Exp $
 */

@Component("cashPlanPayTask")
public class CashPlanPayTask extends AbstractScheduleTask<CashBillPlanEntity> {

    @Autowired
    private CashRecordService cashRecordService;
    @Autowired
    private CashBillPlanService cashBillPlanService;


    @Override
    protected boolean process(CashBillPlanEntity billPlanEntity, String ownSign) throws BusinessException {

        logger.info("cashPlanPayTask中开始发起支付:{}", billPlanEntity.getId());

        // 调用支付兑付
        if (cashRecordService.cashPlanPayment(billPlanEntity, null)) {
            CashBillPlan billPlan = new CashBillPlan();
            billPlan.setStatus(CashStatusEnum.CASHING);
            billPlan.setId(billPlanEntity.getId());

            logger.info("cashPlanPayTask中开始更新模板信息:{}", billPlanEntity.getId());
            // 更新应收账款模板
            int rows = cashBillPlanService.restore(billPlan, CashStatusEnum.CASH_WAIT_PERFORM);
            if (rows != 1) {
                logger.error("cashPlanPayTask中更新模板信息{}的记录数={}", billPlanEntity.getId(), rows);
            }
        } else {
            logger.error("cashPlanPayTask中调用支付发生异常，兑付计划模板Id={}", billPlanEntity.getId());
        }
        return true;
    }

    @Override
    public List<CashBillPlanEntity> selectProcessItems(String taskParameter,
                                                       List<TaskItemDefine> taskItemList, int eachFetchDataNum) throws BusinessException{
        // 执行开始和结束时间，偏移量
        final Date now = new Date();
        final Date beginTime = DateUtil.addDays(now, -1 * 3);
        final Date endTime = DateUtil.addMinutes(now, -1 * 5);

        List<CashBillPlanEntity> billPlans = cashBillPlanService.selectWaitingPerform(CashStatusEnum.CASH_WAIT_PERFORM,
                beginTime, endTime, eachFetchDataNum);

        if (CollectionUtils.isEmpty(billPlans)) {
            return Collections.emptyList();
        }

        return billPlans;
    }

    @Override
    protected String getTaskName() {
        return "兑付支付本息任务";
    }
}
