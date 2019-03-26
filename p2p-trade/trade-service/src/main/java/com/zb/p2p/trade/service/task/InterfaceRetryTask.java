package com.zb.p2p.trade.service.task;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.taobao.pamirs.schedule.TaskItemDefine;
import com.zb.fincore.common.utils.CollectionUtils;
import com.zb.p2p.trade.client.request.CashedNotifyTxsReq;
import com.zb.p2p.trade.client.request.NotifyLoanStatusReq;
import com.zb.p2p.trade.common.enums.InterfaceRetryBusinessTypeEnum;
import com.zb.p2p.trade.common.enums.InterfaceRetryStatusEnum;
import com.zb.p2p.trade.common.exception.BusinessException;
import com.zb.p2p.trade.common.model.CommonResp;
import com.zb.p2p.trade.persistence.dao.CashRecordMapper;
import com.zb.p2p.trade.persistence.entity.CashRecordEntity;
import com.zb.p2p.trade.persistence.entity.InterfaceRetryEntity;
import com.zb.p2p.trade.service.common.AbstractScheduleTask;
import com.zb.p2p.trade.service.common.InterfaceRetryService;
import com.zb.p2p.trade.service.contract.ContractService;
import com.zb.p2p.trade.service.order.OrderAsyncService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * 通知重试JOB
 */
@Slf4j
@Component("interfaceRetryTask")
public class InterfaceRetryTask extends AbstractScheduleTask<InterfaceRetryEntity> {

    @Autowired
    private InterfaceRetryService interfaceRetryService;
    @Autowired
    private ContractService contractService;
    @Autowired
    private OrderAsyncService orderAsyncService;
    @Autowired
    private CashRecordMapper cashRecordMapper;


    @Override
    public boolean process(InterfaceRetryEntity interfaceRetry, String s) throws BusinessException {
        String reqStr = interfaceRetry.getRequestParam();
        String bizType = interfaceRetry.getBusinessType();
        if (StringUtils.isBlank(reqStr) || StringUtils.isBlank(bizType)) {
            return false;
        }
        CommonResp resp = null;
        try {
            //根据业务类型做不同的重试操作
            switch (InterfaceRetryBusinessTypeEnum.convertFromCode(bizType)) {
                case LOAN_NOTIFY_PAYMENT: //放款
                    orderAsyncService.notifyAssertLoanPaymentStatus(JSON.parseObject(reqStr, NotifyLoanStatusReq.class));
                    break;
                case SIGN_CONTRACT: //合同盖章
                    contractService.signContract(JSON.parseObject(reqStr, String.class));
                    break;
                case CASH_RESULT_NOTIFY_TXS:
                    CashedNotifyTxsReq notifyTxsReq = JSON.parseObject(reqStr, CashedNotifyTxsReq.class);
                    CashRecordEntity cashRecordEntity = cashRecordMapper.selectByReqNo(notifyTxsReq.getRepayno());
                    Assert.notNull(cashRecordEntity, "兑付结果通知TXS请求重试参数信息错误，未找到兑付计划信息");
                    orderAsyncService.sendCashResultToTxsOrder(cashRecordEntity);
                    break;
            }
            interfaceRetry.setStatus(InterfaceRetryStatusEnum.SUCCESS.getCode());
            interfaceRetry.setResponseParam(null != resp ? JSONObject.toJSONString(resp) : null);
            interfaceRetryService.updateRetryTimesAndStatusByKey(interfaceRetry);
        } catch (Exception e) {
            log.error("请求重试任务执行失败 bizType={}", bizType, e);
            interfaceRetry.setStatus(InterfaceRetryStatusEnum.FAILURE.getCode());
            interfaceRetry.setResponseParam(null != resp ? JSONObject.toJSONString(resp) : null);
            interfaceRetryService.updateRetryTimesAndStatusByKey(interfaceRetry);
        }
        return true;
    }

    @Override
    protected String getTaskName() {
        return "放款结果通知重试任务";
    }

    @Override
    public List<InterfaceRetryEntity> selectProcessItems(String taskParameter,
                                                         List<TaskItemDefine> taskItemList, int eachFetchDataNum) throws BusinessException {
        log.info("OrderNotifyRetryTask start running ....");

        String bizList = "'LOAN_SUCCESS_NOTIFY_ASSERT','CASH_RESULT_NOTIFY_TXS','SIGN_CONTRACT'";

        List<InterfaceRetryEntity> interfaceRetryEntityList = interfaceRetryService.queryWaitRetryRecordListByBizType(bizList);
        if (CollectionUtils.isNullOrEmpty(interfaceRetryEntityList)) {
            return new ArrayList<>(0);
        }
        return interfaceRetryEntityList;
    }

}
