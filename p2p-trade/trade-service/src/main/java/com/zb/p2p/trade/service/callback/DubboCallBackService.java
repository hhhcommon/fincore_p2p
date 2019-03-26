package com.zb.p2p.trade.service.callback;

import com.zb.fincore.common.enums.ResultCodeEnum;
import com.zb.p2p.paychannel.api.common.BusinessTypeEnum;
import com.zb.p2p.trade.api.CashFacade;
import com.zb.p2p.trade.api.PaymentCallBackFacade;
import com.zb.p2p.trade.api.req.NotifyTradeStatusReq;
import com.zb.p2p.trade.common.constant.GlobalVar;
import com.zb.p2p.trade.common.model.CommonResp;
import com.zb.p2p.trade.common.util.StringUtils;
import com.zb.p2p.trade.persistence.entity.PaymentRecordEntity;
import com.zb.p2p.trade.service.common.DistributedLockService;
import com.zb.p2p.trade.service.order.OrderAsyncService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * Created by limingxin on 2018/1/9.
 * 接口回调
 */
@Service
@Slf4j
public class DubboCallBackService implements PaymentCallBackFacade {

    @Autowired
    private CashFacade cashFacade;
    @Autowired
    private OrderAsyncService orderAsyncService;
    @Autowired
    private DistributedLockService distributedLockService;


    @Override
    public CommonResp<String> callback(NotifyTradeStatusReq notifyTradeStatusReq) {

        log.info("接收到回调通知结果信息：{}", notifyTradeStatusReq);
        String lockKey = GlobalVar.GLOBAL_DUBBOCALLBACK_LOCK_KEY + "_" + notifyTradeStatusReq.getOrderNo();

        try {
            MDC.put(GlobalVar.LOG_TRACE_ID, StringUtils.getUUID());

            distributedLockService.tryLock(lockKey);

            String tradeStatus = notifyTradeStatusReq.getStatus();
            Assert.isTrue(GlobalVar.PAYMENT_TRADE_STATUS_SUCCESS.equals(tradeStatus)
                    || GlobalVar.PAYMENT_TRADE_STATUS_FAILED.equals(tradeStatus), "支付结果状态必须为成功或者失败");

            BusinessTypeEnum busiType = BusinessTypeEnum.getMessageByCode(notifyTradeStatusReq.getBusinessType());
            Assert.notNull(busiType, "BusinessType参数类型非法");
            // 根据busiType做不同处理
            switch (busiType) {
                case LOAN: // 借款放款到卡
                    orderAsyncService.loanWithdrawCallBack(notifyTradeStatusReq);
                    break;
                case REPAYMENT:
                    // 交易暂时不涉及还款
                    break;
                case HONOUR:
                    // 兑付明细
                    cashFacade.updateCashRecord2Balance(notifyTradeStatusReq);
                    break;
                case FEE:
                    // 手续费费用划转
                    cashFacade.processRepaymentFeeNotifyResult(notifyTradeStatusReq);
                    break;
                default:
                    return new CommonResp(ResultCodeEnum.PARAMS_VALIDATE_FAIL.code(), "busiType参数类型暂不支持");
            }
            // 返回
            return new CommonResp(ResultCodeEnum.SUCCESS.code(), ResultCodeEnum.SUCCESS.desc());
        } catch (Exception e) {
            log.error("callback error", e);
            return new CommonResp(ResultCodeEnum.FAIL.code(), ResultCodeEnum.FAIL.desc());
        } finally {
            try {
                distributedLockService.unLockAndDel(lockKey);
            } catch (Exception e1) {
                log.error("unLock异常", e1);
            }
            MDC.clear();
        }
    }

}
