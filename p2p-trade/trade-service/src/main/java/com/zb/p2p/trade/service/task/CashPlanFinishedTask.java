package com.zb.p2p.trade.service.task;

import com.taobao.pamirs.schedule.TaskItemDefine;
import com.zb.p2p.trade.common.enums.CashStatusEnum;
import com.zb.p2p.trade.common.exception.BusinessException;
import com.zb.p2p.trade.common.util.DateUtil;
import com.zb.p2p.trade.persistence.dao.CashBillPlanMapper;
import com.zb.p2p.trade.persistence.entity.CashBillPlanEntity;
import com.zb.p2p.trade.service.common.AbstractScheduleTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * <p> 兑付计划兑付完成任务(更新模板状态及转让完全退出检查) </p>
 *
 * @author Vinson
 * @version ProductManagerClient.java v1.0 2018/3/8 14:20 Zhengwenquan Exp $
 */

@Component("cashPlanFinishedTask")
public class CashPlanFinishedTask extends AbstractScheduleTask<CashBillPlanEntity> {

    @Autowired
    private CashBillPlanMapper cashBillPlanMapper;

    @Override
    protected boolean process(CashBillPlanEntity billPlanEntity, String ownSign) throws BusinessException {

        CashBillPlanEntity updateBillPlanEntity = new CashBillPlanEntity();
        updateBillPlanEntity.setId(billPlanEntity.getId());
        updateBillPlanEntity.setStatus(CashStatusEnum.CASH_SUCCESS.getCode());
        updateBillPlanEntity.setCashDate(new Date());// 完成兑付日期

        logger.info("cashPlanFinishedTask中开始更新模板信息:{}", billPlanEntity.getId());
        // 更新应收账款模板
        int rows = cashBillPlanMapper.updateByPrimaryKeySelective(updateBillPlanEntity);
        if (rows != 1) {
            logger.error("cashPlanFinishedTask中更新模板信息{}的记录数={}", updateBillPlanEntity.getId(), rows);
        }
        return true;
    }

    @Override
    public List<CashBillPlanEntity> selectProcessItems(String taskParameter,
                                            List<TaskItemDefine> taskItemList, int eachFetchDataNum) throws BusinessException {
        // 执行开始和结束时间，偏移量
        final Date now = new Date();
        final Date beginTime = DateUtil.addDays(now, -1 * 3);
        final Date endTime = DateUtil.addMinutes(now, -1 * 3);

        List<CashBillPlanEntity> billPlans = cashBillPlanMapper.selectAllCashFinished(
                CashStatusEnum.CASHING.getCode(), beginTime, endTime, eachFetchDataNum);

        if (CollectionUtils.isEmpty(billPlans)) {
            return Collections.emptyList();
        }

        return billPlans;
    }

    @Override
    protected String getTaskName() {
        return "兑付计划兑付完成任务";
    }
}
