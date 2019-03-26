package com.zb.p2p.trade.service.cash;


import com.zb.p2p.trade.client.dto.AssetBillPlanDto;
import com.zb.p2p.trade.common.domain.CashBillPlanKey;
import com.zb.p2p.trade.common.domain.CashPlan;
import com.zb.p2p.trade.common.domain.CreditorInfo;
import com.zb.p2p.trade.common.enums.CashAmountTypeEnum;
import com.zb.p2p.trade.common.enums.CashStatusEnum;
import com.zb.p2p.trade.common.enums.PayChannelEnum;
import com.zb.p2p.trade.common.exception.BusinessException;
import com.zb.p2p.trade.common.model.CashAmountSuite;
import com.zb.p2p.trade.persistence.entity.CashBillPlanEntity;
import com.zb.p2p.trade.persistence.entity.CashRecordEntity;
import com.zb.p2p.trade.persistence.entity.PaymentRecordEntity;
import com.zb.p2p.trade.persistence.entity.RepayBillPlanEntity;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Function:兑付服务
 * Author: created by liguoliang
 * Date: 2017/8/31 0031 上午 11:16
 * Version: 1.0
 */
public interface CashRecordService {

    /**
     * 根据状态保存
     * @param plan
     * @param preStatus
     * @return
     */
    int update(CashPlan plan, CashStatusEnum preStatus);

    /**
     * 批量更新
     * @param planList
     * @param preStatus
     * @return
     */
    int batchUpdate(List<CashPlan> planList, CashStatusEnum preStatus);

    /**
     * 该应收账款模板下是否全部计算完毕
     * @param billPlanId
     * @param nextStatus
     * @return
     */
    boolean isAllFinished(Long billPlanId, CashStatusEnum nextStatus);

    /**
     * 根据状态及兑付到余额时间来查询
     * @param status
     * @param payChannel
     * @param beginTime
     * @param endTime
     * @param limit
     * @return
     */
    List<CashRecordEntity> selectByWithdrawlStatus(CashStatusEnum status, PayChannelEnum payChannel, Date beginTime, Date endTime, int limit);

    /**
     * 根据key查询
     * @param key
     * @return
     */
    List<CashPlan> loadByKey(CashBillPlanKey key);

    Map<String, CashAmountSuite> loadMemberTotalAmount(CashBillPlanKey key, List<String> memberIds, CashAmountTypeEnum cashAmountType);

    /**
     *
     * @param key
     * @param orderIds
     * @param cashAmountType
     * @return
     */
    Map<String, CashAmountSuite> loadOrderTotalAmount(CashBillPlanKey key, List<String> orderIds, CashAmountTypeEnum cashAmountType);

    /**
     * 根据债权信息查询
     * @param creditorInfo
     * @return
     * @throws BusinessException
     */
    List<CashPlan> selectByTransferCreditor(CreditorInfo creditorInfo) throws BusinessException;

    /**
     * 兑付本息支付发放(重试)
     * @param billPlanEntity
     * @return
     */
    boolean cashPlanPayment(CashBillPlanEntity billPlanEntity, PaymentRecordEntity retryRecord);

    /**
     * 放款手续费按期收取
     * @param updateBill
     * @throws BusinessException
     */
    RepayBillPlanEntity repaymentTransferFee(RepayBillPlanEntity updateBill, AssetBillPlanDto repayPlan) throws BusinessException;

    /**
     * 兑付本息重试
     * @param paymentRecord
     * @throws BusinessException
     */
    void retryCash(PaymentRecordEntity paymentRecord) throws BusinessException;

    /**
     * 兑付手续费重试
     * @param paymentRecord
     * @throws BusinessException
     */
    void retryLoanFee(PaymentRecordEntity paymentRecord) throws BusinessException;
}
