package com.zb.p2p.trade.service.cash;

import com.zb.p2p.trade.api.req.CashRecordReq;
import com.zb.p2p.trade.api.req.RepayAmountReq;
import com.zb.p2p.trade.api.req.RepaymentReq;
import com.zb.p2p.trade.common.domain.CashBillPlan;
import com.zb.p2p.trade.common.domain.CashBillPlanKey;
import com.zb.p2p.trade.common.enums.CashStatusEnum;
import com.zb.p2p.trade.common.enums.RepaymentTypeEnum;
import com.zb.p2p.trade.common.exception.BusinessException;
import com.zb.p2p.trade.common.model.CommonResp;
import com.zb.p2p.trade.persistence.dto.CashRecordDto;
import com.zb.p2p.trade.persistence.dto.CashSumAmountEntity;
import com.zb.p2p.trade.persistence.dto.CreateCashBilPlanRequest;
import com.zb.p2p.trade.persistence.dto.RepayAmountDTO;
import com.zb.p2p.trade.persistence.entity.CashBillPlanEntity;

import java.util.Date;
import java.util.List;

/**
 * <p> 兑付计划模板服务 </p>
 *
 * @author Vinson
 * @version CashBillPlanService.java v1.0 2018/4/21 16:08 Zhengwenquan Exp $
 */
public interface CashBillPlanService {

    /**
     * 保存模板信息
     * @param billPlan
     * @param preStatus
     * @return
     */
    int restore(CashBillPlan billPlan, CashStatusEnum preStatus);

    /**
     * 根据请求分页查询兑付信息
     * @param cashRecordReq
     * @return
     */
    List<CashRecordDto> queryCashPlanInfoByCondition(CashRecordReq cashRecordReq);

    /**
     * 根据key条件获取模板
     * @param key
     * @param isLock
     * @return
     */
    CashBillPlan load(CashBillPlanKey key, boolean isLock);

    /**
     * 统计总金额
     * @param assetNo
     * @param repaymentType
     * @return
     */
    CashSumAmountEntity loadCashAmountTotal(String assetNo, RepaymentTypeEnum repaymentType);

    /**
     * 是否最后一期
     * @param key
     * @param isPrincipal
     * @return
     */
    boolean isLastStage(CashBillPlanKey key, boolean isPrincipal);

    /**
     * 获取待发放状态的兑付计划
     * @param cashStatus
     * @param beginTime
     * @param endTime
     * @param fetchNum
     * @return
     */
    List<CashBillPlanEntity> selectWaitingPerform(CashStatusEnum cashStatus, Date beginTime, Date endTime, int fetchNum);

    /**
     * 创建兑付计划及模板
     * @param createRequestList
     * @throws BusinessException
     */
    void create(List<CreateCashBilPlanRequest> createRequestList) throws BusinessException;

    /**
     * 保存新增兑付计划模板
     * @param billPlan
     */
    int storeCashBillPlan(CashBillPlan billPlan);

    /**
     * 保存新增兑付计划
     * @param billPlan
     */
    int storeCashPlan(CashBillPlan billPlan);

    /**
     * 还款成功结果处理
     * @param repaymentReq
     * @return
     * @throws BusinessException
     */
    CommonResp repaymentTradeResultHandler(RepaymentReq repaymentReq);

}
