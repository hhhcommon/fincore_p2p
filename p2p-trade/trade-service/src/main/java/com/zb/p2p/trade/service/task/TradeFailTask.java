package com.zb.p2p.trade.service.task;

import com.alibaba.fastjson.JSON;
import com.taobao.pamirs.schedule.TaskItemDefine;
import com.zb.fincore.common.enums.ResultCodeEnum;
import com.zb.p2p.trade.common.enums.TradeStatusEnum;
import com.zb.p2p.trade.common.enums.TradeTypeEnum;
import com.zb.p2p.trade.common.exception.BusinessException;
import com.zb.p2p.trade.common.util.DateUtil;
import com.zb.p2p.trade.persistence.dao.CreditorInfoMapper;
import com.zb.p2p.trade.persistence.entity.PaymentRecordEntity;
import com.zb.p2p.trade.service.cash.CashRecordService;
import com.zb.p2p.trade.service.common.AbstractScheduleTask;
import com.zb.p2p.trade.service.common.PaymentRecordService;
import com.zb.p2p.trade.service.order.OrderAsyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * <p> 交易失败失败Job </p>
 *
 */

@Component("tradeFailTask")
public class TradeFailTask extends AbstractScheduleTask<PaymentRecordEntity> {

    @Autowired
    private CreditorInfoMapper creditorInfoMapper;
    @Autowired
    private PaymentRecordService paymentRecordService;
    @Autowired
    private CashRecordService cashRecordService;
    @Autowired
    private OrderAsyncService orderAsyncService;

    @Override
    protected boolean process(PaymentRecordEntity recordEntity, String ownSign) throws Exception {

        logger.info("tradeFailTask中开始处理信息:交易类型={},支付记录ID={}", recordEntity.getTradeType(), recordEntity.getId());

        TradeStatusEnum status = TradeStatusEnum.getByCode(recordEntity.getTradeStatus());
        if (TradeStatusEnum.FAIL == status) {
            // 根据交易类型处理
            TradeTypeEnum tradeType = TradeTypeEnum.getByCode(recordEntity.getTradeType());
            switch (tradeType) {
                case LOAN:
                    //重新发起放款
                    orderAsyncService.doRetryLoanHandle(recordEntity);
                    break;
                case CASH: // 兑付
                    cashRecordService.retryCash(recordEntity);
                    break;
                case LOAN_FEE: // 手续费
                    cashRecordService.retryLoanFee(recordEntity);
                    break;
                default:
                    throw new BusinessException(ResultCodeEnum.PARAMS_VALIDATE_FAIL.code(), "暂不支持此种交易类型：" + tradeType);
            }
            // 更新
            PaymentRecordEntity record = new PaymentRecordEntity();
            record.setId(recordEntity.getId());
            record.setTradeStatus(TradeStatusEnum.CLOSED.getCode());
            paymentRecordService.updatePaymentRecord(record);
        }else {
            logger.warn("{}支付结果成功或者还在处理中，不做重试处理", recordEntity.getTradeSerialNo());
        }

        return true;
    }

    @Override
    public List<PaymentRecordEntity> selectProcessItems(String taskParameter,
                                                                  List<TaskItemDefine> taskItemList, int eachFetchDataNum) throws Exception {
        logger.info("tradeFailTask配置的参数:任务过滤项{},每次获取数量{}", JSON.toJSONString(taskItemList), eachFetchDataNum);

        // 执行开始和结束时间，偏移量
        final Date now = new Date();
        final Date beginTime = DateUtil.addDays(now, -1 * 2);
        final Date endTime = DateUtil.addMinutes(now, -1 * 15);

        // 1.查询支付记录表中状态为"交易失败"的所有记录，且没再成功过
        List<PaymentRecordEntity> recordEntityList = paymentRecordService.queryFinalFailed(TradeStatusEnum.FAIL,
                beginTime, endTime, eachFetchDataNum);

        if (CollectionUtils.isEmpty(recordEntityList)) {
            return Collections.emptyList();
        }
        logger.info("tradeFailTask本次处理任务量:{}", recordEntityList.size());

        return recordEntityList;
    }

    @Override
    protected String getTaskName() {
        return "交易失败Job";
    }
}
