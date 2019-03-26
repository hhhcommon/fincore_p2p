package com.zb.p2p.cash.schedule;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.taobao.pamirs.schedule.IScheduleTaskDealSingle;
import com.taobao.pamirs.schedule.TaskItemDefine;
import com.zb.fincore.ams.facade.LoanRepayPlanServiceFacade;
import com.zb.fincore.ams.facade.dto.req.UpdateCashStatusRequest;
import com.zb.fincore.common.utils.CollectionUtils;
import com.zb.p2p.entity.InterfaceRetryEntity;
import com.zb.p2p.enums.InterfaceRetryBusinessTypeEnum;
import com.zb.p2p.enums.InterfaceRetryStatusEnum;
import com.zb.p2p.service.callback.TXSCallBackService;
import com.zb.p2p.service.callback.api.req.NotifyTxsAssetMatchResultReq;
import com.zb.p2p.service.common.InterfaceRetryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Function:兑付相关重试处理任务
 * Author: created by liguoliang
 * Date: 2017/9/7 0006 下午 4:57
 * Version: 1.0
 */
@Component("cashRetryTask")
public class CashRetryTask implements IScheduleTaskDealSingle<InterfaceRetryEntity> {

    private static Logger logger = LoggerFactory.getLogger(IScheduleTaskDealSingle.class);

    @Autowired
    private InterfaceRetryService interfaceRetryService;
    @Autowired
    private TXSCallBackService txsCallBackService;
    @Autowired
    private LoanRepayPlanServiceFacade loanRepayPlanServiceFacade;

    @Override
    public boolean execute(InterfaceRetryEntity interfaceRetry, String ownSign) throws Exception {
        String reqStr = interfaceRetry.getRequestParam();
        String bizType = interfaceRetry.getBusinessType();
        if (StringUtils.isBlank(reqStr) || StringUtils.isBlank(bizType)) {
            return false;
        }
        try {
            switch (InterfaceRetryBusinessTypeEnum.convertFromCode(bizType)) {
                case CASH_RESULT_NOTIFY_ASSET:
                    UpdateCashStatusRequest updateCashStatusRequest = JSONObject.parseObject(reqStr, UpdateCashStatusRequest.class);
                    loanRepayPlanServiceFacade.updateCashStatus(updateCashStatusRequest);
                    break;
                case CASH_RESULT_NOTIFY_TXS:
                    List<NotifyTxsAssetMatchResultReq> cashNotifyList = JSONObject.parseObject(reqStr, List.class);
                    txsCallBackService.tradeNotifyOrder(cashNotifyList);
                    break;
            }
            interfaceRetry.setStatus(InterfaceRetryStatusEnum.SUCCESS.getCode());
            interfaceRetryService.updateRetryTimesAndStatusByKey(interfaceRetry);
        } catch (Exception e) {
            interfaceRetry.setStatus(InterfaceRetryStatusEnum.FAILURE.getCode());
            interfaceRetryService.updateRetryTimesAndStatusByKey(interfaceRetry);
        }
        return true;
    }

    @Override
    public List<InterfaceRetryEntity> selectTasks(String taskParameter, String ownSign, int taskQueueNum,
                                                  List<TaskItemDefine> taskItemList, int eachFetchDataNum) throws Exception {
        logger.info("CashRetryTask start running ....");
        Map<String, Object> params = new HashMap<>();
        List<String> businessTypeList= new ArrayList<>();
        businessTypeList.add(InterfaceRetryBusinessTypeEnum.CASH_RESULT_NOTIFY_TXS.getCode());
        businessTypeList.add(InterfaceRetryBusinessTypeEnum.CASH_RESULT_NOTIFY_ASSET.getCode());
        params.put("businessTypeList", businessTypeList);
        List<InterfaceRetryEntity> interfaceRetryEntityList = interfaceRetryService.queryWaitRetryRecordListByParams(params);
        if (CollectionUtils.isNullOrEmpty(interfaceRetryEntityList)) {
            return new ArrayList<>(0);
        }
        logger.info("CashRetryTask本次处理任务量:{}", interfaceRetryEntityList.size());
        return interfaceRetryEntityList;
    }

    @Override
    public Comparator<InterfaceRetryEntity> getComparator() {
        return null;
    }
}
