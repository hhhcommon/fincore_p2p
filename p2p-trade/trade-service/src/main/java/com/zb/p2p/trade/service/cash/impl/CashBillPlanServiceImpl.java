package com.zb.p2p.trade.service.cash.impl;

import com.zb.fincore.common.enums.ResultCodeEnum;
import com.zb.p2p.trade.api.req.CashRecordReq;
import com.zb.p2p.trade.api.req.RepaymentReq;
import com.zb.p2p.trade.client.ams.AmsClientService;
import com.zb.p2p.trade.client.dto.AssetBillPlanDto;
import com.zb.p2p.trade.common.constant.GlobalVar;
import com.zb.p2p.trade.common.domain.CashBillPlan;
import com.zb.p2p.trade.common.domain.CashBillPlanKey;
import com.zb.p2p.trade.common.enums.BillStatusEnum;
import com.zb.p2p.trade.common.enums.CashOverdueEnum;
import com.zb.p2p.trade.common.enums.CashStatusEnum;
import com.zb.p2p.trade.common.enums.RepaymentTypeEnum;
import com.zb.p2p.trade.common.exception.BusinessException;
import com.zb.p2p.trade.common.model.CashAmountSuite;
import com.zb.p2p.trade.common.model.CommonResp;
import com.zb.p2p.trade.common.util.DateUtil;
import com.zb.p2p.trade.common.util.StringUtils;
import com.zb.p2p.trade.persistence.converter.CashBillPlanConverter;
import com.zb.p2p.trade.persistence.dao.CashBillPlanMapper;
import com.zb.p2p.trade.persistence.dao.CashRecordMapper;
import com.zb.p2p.trade.persistence.dao.RepayBillPlanMapper;
import com.zb.p2p.trade.persistence.dto.CashRecordDto;
import com.zb.p2p.trade.persistence.dto.CashSumAmountEntity;
import com.zb.p2p.trade.persistence.dto.CreateCashBilPlanRequest;
import com.zb.p2p.trade.persistence.entity.CashBillPlanEntity;
import com.zb.p2p.trade.persistence.entity.RepayBillPlanEntity;
import com.zb.p2p.trade.service.cash.CashBillPlanService;
import com.zb.p2p.trade.service.cash.CashRecordService;
import com.zb.p2p.trade.service.common.DistributedLockService;
import com.zb.p2p.trade.service.config.ReadOnlyConnection;
import com.zb.p2p.trade.service.process.CommonProcessor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * <p> 应收账款(模板)服务实现 </p>
 *
 * @author Vinson
 * @version CashBillPlanServiceImpl.java v1.0 2018/4/21 16:41 Zhengwenquan Exp $
 */
@Slf4j
@Service
public class CashBillPlanServiceImpl implements CashBillPlanService {

    @Autowired
    private CashBillPlanMapper cashBillPlanMapper;
    @Autowired
    private CashRecordMapper cashRecordMapper;
    @Autowired
    private RepayBillPlanMapper repayBillPlanMapper;
    @Autowired
    private AmsClientService amsClientService;
    @Autowired
    private CashRecordService cashRecordService;
    @Autowired
    private CommonProcessor commonProcessor;
    @Autowired
    protected DistributedLockService distributedLockService;


    @Override
    public int restore(CashBillPlan billPlan, CashStatusEnum preStatus) {
        return cashBillPlanMapper.updateByPreStatus(CashBillPlanConverter.convert(billPlan), preStatus.getCode());
    }

    @Override
    @ReadOnlyConnection
    public List<CashRecordDto> queryCashPlanInfoByCondition(CashRecordReq cashRecordReq) {
        Date beginDate = DateUtil.getBeginTime(new Date(), 0);
        Date endDate = DateUtil.addDays(beginDate, cashRecordReq.getLatestDays());
        int endIndex = cashRecordReq.getEndIndex() < 1 ? 20 : cashRecordReq.getEndIndex();

        return cashBillPlanMapper.queryCashPlanInfoByCondition(beginDate, endDate, cashRecordReq.getAssetNo(),
                cashRecordReq.getMemberId(), cashRecordReq.getStartIndex(), endIndex);
    }

    @Override
    public CashBillPlan load(CashBillPlanKey key, boolean isLock) {
        CashBillPlanEntity billPlanEntity = cashBillPlanMapper.selectByKey(key.getAssetNo(),
                key.getStageNo(), key.getRepaymentType().getCode(), isLock);
        return CashBillPlanConverter.convert(billPlanEntity);
    }

    @Override
    @ReadOnlyConnection
    public CashSumAmountEntity loadCashAmountTotal(String assetNo, RepaymentTypeEnum repaymentType) {
        return cashBillPlanMapper.selectCashAmountTotal(assetNo, repaymentType.getCode());
    }

    @Override
    @ReadOnlyConnection
    public boolean isLastStage(CashBillPlanKey key, boolean isPrincipal) {
        Integer stageNo = cashBillPlanMapper.selectMaxValidStage(key.getAssetNo(),
                key.getRepaymentType().getCode(), isPrincipal);
        return stageNo != null && stageNo == key.getStageNo();
    }

    @Override
    @ReadOnlyConnection
    public List<CashBillPlanEntity> selectWaitingPerform(CashStatusEnum cashStatus, Date beginTime, Date endTime, int fetchNum) {
        return cashBillPlanMapper.selectWaitingPerform(cashStatus.getCode(), beginTime, endTime, fetchNum);
    }

    @Override
    public int storeCashBillPlan(CashBillPlan billPlan) {
        CashBillPlanEntity billPlanEntity = CashBillPlanConverter.convert(billPlan);
        int rows = cashBillPlanMapper.insert(billPlanEntity);
        // 回填Id
        billPlan.setId(billPlanEntity.getId());

        return rows;
    }

    @Override
    public int storeCashPlan(CashBillPlan billPlan) {
        CashBillPlanEntity billPlanEntity = CashBillPlanConverter.convert(billPlan);
        // 根据模板初始化所有投资人的兑付计划
        return cashRecordMapper.batchInsertByBillPlan(billPlanEntity, GlobalVar.CREDITOR_SUCCESS_STATUS);
    }

    @Override
    public void create(List<CreateCashBilPlanRequest> createRequestList) throws BusinessException {

    }

    private void performBillPlan(AssetBillPlanDto repayPlan) throws BusinessException{

        CashBillPlanKey key;
        CashAmountSuite cashAmount;

        // 计算拆分本金和利息
        switch (CashOverdueEnum.getByRepayStatus(repayPlan.getRepayStatus())) {
            case EXPIRE:
            case NORMAL:
                // 组装参数
                key = new CashBillPlanKey(repayPlan.getAssetCode(),
                        repayPlan.getCurrentRepayCount(), RepaymentTypeEnum.CREDITOR);
                cashAmount = new CashAmountSuite(repayPlan.getRepayPrincipal(), repayPlan.getRepayInterest());
                break;
            default:
                throw new BusinessException(ResultCodeEnum.PARAMS_VALIDATE_FAIL.code(), "暂不支持部分还款垫付");
        }

        // 履行计算
        commonProcessor.performCashPlans(key, cashAmount);
    }

    @Override
    public CommonResp repaymentTradeResultHandler(RepaymentReq repaymentReq) {
        CommonResp resp = new CommonResp<>(false);
        String lockKey = StringUtils.concatStrBy_(CashStatusEnum.CASHING.getCode(), repaymentReq.getAssetCode());
        try {
            distributedLockService.tryLock(lockKey);
            MDC.put(GlobalVar.LOG_TRACE_ID, StringUtils.getUUID());
            // 1.查询该期的还款计划
            List<AssetBillPlanDto> repayPlanModels = amsClientService.queryRepayPlan(repaymentReq.getAssetCode(), repaymentReq.getRepayNum());
            Assert.isTrue(!CollectionUtils.isEmpty(repayPlanModels), "未查到对应该期的还款计划");
            AssetBillPlanDto repayPlan = repayPlanModels.get(0);

            // 获取还款计划
            RepayBillPlanEntity billPlanEntity = repayBillPlanMapper.selectByAssetNoStage(repayPlan.getAssetCode(),
                    repayPlan.getCurrentRepayCount());
            // 2.校验
            BillStatusEnum billStatus = validate(repayPlan, billPlanEntity);

            // 3.履行放（还）款手续费给平台
            RepayBillPlanEntity updateBill = cashRecordService.repaymentTransferFee(billPlanEntity, repayPlan);

            // 4.履行计算兑付计划
            performBillPlan(repayPlan);

            // 5.更新还款计划状态信息
            updateBill.setId(billPlanEntity.getId());
            updateBill.setStatus(billStatus.getCode());
            updateBill.setModifyTime(new Date());
            // 校验通过代表等同预期本息
            updateBill.setActualPrinciple(repayPlan.getRepayPrincipal());
            updateBill.setActualInterest(repayPlan.getRepayInterest());
            updateBill.setRemainAmount(repayPlan.getWaitingRepayAmount());

            int updateRows = repayBillPlanMapper.updateByPrimaryKeySelective(updateBill);
            Assert.isTrue(updateRows == 1, "还款计划更新实收信息失败");
            // success
            resp.success();
        } catch (BusinessException e) {
            log.error("业务处理异常：", e);
        } catch (Exception e) {
            log.error("系统处理异常：", e);
        }finally {
            MDC.clear();
            try {
                distributedLockService.unLockAndDel(lockKey);
            } catch (Exception e) {
                log.error("unLock异常", e);
            }
        }

        return resp;
    }

    private BillStatusEnum validate(AssetBillPlanDto repayPlan, RepayBillPlanEntity billPlanEntity) {
        // 交易还款计划
        Assert.notNull(billPlanEntity, "交易还款计划不存在");

        BillStatusEnum billStatus = BillStatusEnum.getByAssetRepayStatus(repayPlan.getRepayStatus());
        Assert.notNull(billStatus, "资产的还款计划还款状态非法");

        Date repayDate = DateUtil.parse(repayPlan.getRepayDate(), DateUtil.SHORT_FORMAT);
        Assert.isTrue(repayDate != null && repayDate.compareTo(new Date()) < 0,
                "还款日期非法");

        // 判断还款状态是否为已还款
        CashOverdueEnum overdueEnum = CashOverdueEnum.getByRepayStatus(repayPlan.getRepayStatus());
        Assert.notNull(overdueEnum == CashOverdueEnum.NORMAL
                || overdueEnum == CashOverdueEnum.EXPIRE, "还款计划状态非法");

        // 判断实际还款金额大于0
        Assert.notNull(repayPlan.getRepaidAmount(), "资产实际还款金额非法");
        BigDecimal expectAmount = billPlanEntity.getExpectPrinciple().add(billPlanEntity.getExpectInterest());
        Assert.isTrue(repayPlan.getRepaidAmount().compareTo(expectAmount) >= 0, "资产实际还款金额不能小于应还款金额");

        return billStatus;
    }

}
