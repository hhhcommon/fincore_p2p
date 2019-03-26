package com.zb.p2p.trade.service.task;

import com.alibaba.fastjson.JSON;
import com.taobao.pamirs.schedule.TaskItemDefine;
import com.zb.fincore.common.enums.ResultCodeEnum;
import com.zb.p2p.paychannel.api.common.BusinessTypeEnum;
import com.zb.p2p.paychannel.api.resp.TradeRespDTO;
import com.zb.p2p.trade.api.req.NotifyTradeStatusReq;
import com.zb.p2p.trade.client.payment.PaymentClientService;
import com.zb.p2p.trade.common.constant.GlobalVar;
import com.zb.p2p.trade.common.enums.TradeStatusEnum;
import com.zb.p2p.trade.common.enums.TradeTypeEnum;
import com.zb.p2p.trade.common.exception.BusinessException;
import com.zb.p2p.trade.common.util.DateUtil;
import com.zb.p2p.trade.persistence.entity.PaymentRecordEntity;
import com.zb.p2p.trade.service.callback.DubboCallBackService;
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
 * <p> 交易结果确认Job </p>
 *
 * @author Vinson
 * @version TradeResultConfirmTask.java v1.0 2018/5/21 14:10 Zhengwenquan Exp $
 */

@Component("tradeResultConfirmTask")
public class TradeResultConfirmTask extends AbstractScheduleTask<PaymentRecordEntity> {

    @Autowired
    private PaymentRecordService paymentRecordService;
    @Autowired
    private DubboCallBackService dubboCallBackService;
    @Autowired
    private PaymentClientService paymentClientService;
    @Autowired
    private OrderAsyncService orderAsyncService;

    @Override
    protected boolean process(PaymentRecordEntity recordEntity, String ownSign) throws BusinessException {

        logger.info("tradeResultConfirmTask中开始处理信息:交易类型={},支付记录ID={}", recordEntity.getTradeType(), recordEntity.getId());

        // 查询支付结果
        TradeRespDTO result = paymentClientService.queryTradeResult(recordEntity.getTradeSerialNo(), recordEntity.getMemberId());
        if (result != null) {
            TradeStatusEnum status = TradeStatusEnum.getByCode(result.getTradeStatus());
            BusinessTypeEnum businessType = null;
            if (status != null && TradeStatusEnum.PROCESSING != status) {
                // 根据交易类型处理
                TradeTypeEnum tradeType = TradeTypeEnum.getByCode(recordEntity.getTradeType());
                switch (tradeType) {
                    case LOAN:
                        orderAsyncService.doTradeResultHandle(result.getTradeStatus(), recordEntity);
                    case LOAN_FEE:
                        businessType = BusinessTypeEnum.FEE;
                        break;
                    case CASH:
                        businessType = BusinessTypeEnum.HONOUR;
                        break;
                    default:
                        throw new BusinessException(ResultCodeEnum.PARAMS_VALIDATE_FAIL.code(), "暂不支持此种交易类型：" + tradeType);
                }
                // 组装结果回调参数模拟回调
                if (businessType != null) {
                    NotifyTradeStatusReq notifyTradeReq = new NotifyTradeStatusReq();
                    notifyTradeReq.setBusinessType(businessType.getCode());
                    notifyTradeReq.setBusinessType(recordEntity.getMemberId());
                    notifyTradeReq.setOrderNo(recordEntity.getTradeSerialNo());
                    notifyTradeReq.setErrMsg(result.getTradeMsg());
                    notifyTradeReq.setErrCode(GlobalVar.PAYMENT_TRADE_CODE_SUCCESS);
                    notifyTradeReq.setStatus(result.getTradeStatus());

                    dubboCallBackService.callback(notifyTradeReq);
                }
            }else {
                logger.warn("{}支付结果为空或者还在处理中，不做回调处理", recordEntity.getTradeSerialNo());
            }
        }

        return true;
    }

    @Override
    public List<PaymentRecordEntity> selectProcessItems(String taskParameter,
                                                                  List<TaskItemDefine> taskItemList, int eachFetchDataNum) throws BusinessException {
        logger.info("tradeResultConfirmTask配置的参数:任务过滤项{},每次获取数量{}", JSON.toJSONString(taskItemList), eachFetchDataNum);

        // 执行开始和结束时间，偏移量
        final Date now = new Date();
        final Date beginTime = DateUtil.addDays(now, -1 * 3);
        final Date endTime = DateUtil.addMinutes(now, -1 * 3);

        // 1.查询支付记录表中状态为支付处理中的所有记录
        List<PaymentRecordEntity> recordEntityList = paymentRecordService.queryWaitingClose(TradeStatusEnum.PROCESSING,
                beginTime, endTime, eachFetchDataNum);

        if (CollectionUtils.isEmpty(recordEntityList)) {
            return Collections.emptyList();
        }
        logger.info("tradeResultConfirmTask本次处理任务量:{}", recordEntityList.size());

        return recordEntityList;
    }

    @Override
    protected String getTaskName() {
        return "交易结果确认Job";
    }
}
