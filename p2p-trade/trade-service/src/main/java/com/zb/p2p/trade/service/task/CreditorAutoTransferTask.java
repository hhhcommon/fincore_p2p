package com.zb.p2p.trade.service.task;

import com.alibaba.fastjson.JSON;
import com.taobao.pamirs.schedule.TaskItemDefine;
import com.zb.p2p.trade.common.exception.BusinessException;
import com.zb.p2p.trade.persistence.entity.TransferRequestEntity;
import com.zb.p2p.trade.service.common.AbstractScheduleTask;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

/**
 * <p> 自动债权转让Job </p>
 *
 * @author Vinson
 * @version CreditorAutoTransferTask.java v1.0 2018/5/4 14:20 Zhengwenquan Exp $
 */

@Component("creditorAutoTransferTask")
public class CreditorAutoTransferTask extends AbstractScheduleTask<TransferRequestEntity> {


    @Override
    protected boolean process(TransferRequestEntity billPlanEntity, String ownSign) throws BusinessException {

//        billPlanEntity.setStatus(CashStatusEnum.CASH_SUCCESS.getCode());
//        billPlanEntity.setCashDate(new Date());// 完成兑付日期
//
//        logger.info("cashPlanFinishedTask中开始更新模板信息:{}", billPlanEntity.getId());
//        // 更新应收账款模板
//        int rows = cashBillPlanMapper.updateByPrimaryKeySelective(billPlanEntity);
//        if (rows != 1) {
//            logger.error("cashPlanFinishedTask中更新模板信息{}的记录数={}", billPlanEntity.getId(), rows);
//        }
        return true;
    }

    @Override
    public List<TransferRequestEntity> selectProcessItems(String taskParameter,
                                                                  List<TaskItemDefine> taskItemList, int eachFetchDataNum) throws BusinessException {
        logger.info("creditorAutoTransferTask配置的参数:任务过滤项{},每次获取数量{}", JSON.toJSONString(taskItemList), eachFetchDataNum);

        // 1.根据退出申请表的首投订单号查询债权记录表中状态为转让中的所有债权记录

        List<TransferRequestEntity> requestEntityList = null;

        if (CollectionUtils.isEmpty(requestEntityList)) {
            return Collections.emptyList();
        }
        logger.info("creditorAutoTransferTask本次处理任务量:{}", requestEntityList.size());

        return requestEntityList;
    }

    @Override
    protected String getTaskName() {
        return "自动债权转让Job";
    }
}
