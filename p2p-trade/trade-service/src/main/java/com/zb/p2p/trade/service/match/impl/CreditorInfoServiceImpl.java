package com.zb.p2p.trade.service.match.impl;

import com.alibaba.fastjson.JSON;
import com.zb.p2p.order.api.OrderAPI;
import com.zb.p2p.order.api.order.request.OrderRelationReq;
import com.zb.p2p.order.api.order.response.CommonResponse;
import com.zb.p2p.trade.common.constant.GlobalVar;
import com.zb.p2p.trade.common.domain.CreditorInfo;
import com.zb.p2p.trade.common.domain.MatchRecord;
import com.zb.p2p.trade.common.enums.*;
import com.zb.p2p.trade.common.exception.BusinessException;
import com.zb.p2p.trade.common.util.BigDecimalUtil;
import com.zb.p2p.trade.common.util.DateUtil;
import com.zb.p2p.trade.common.util.StringUtils;
import com.zb.p2p.trade.persistence.converter.CreditorInfoConverter;
import com.zb.p2p.trade.persistence.dao.CashRecordMapper;
import com.zb.p2p.trade.persistence.dao.CreditorInfoMapper;
import com.zb.p2p.trade.persistence.dto.CashSumAmountEntity;
import com.zb.p2p.trade.persistence.entity.CashRecordEntity;
import com.zb.p2p.trade.persistence.entity.CreditorInfoEntity;
import com.zb.p2p.trade.persistence.entity.InterfaceRetryEntity;
import com.zb.p2p.trade.persistence.entity.LoanRequestEntity;
import com.zb.p2p.trade.service.common.DistributedSerialNoService;
import com.zb.p2p.trade.service.common.InterfaceRetryService;
import com.zb.p2p.trade.service.config.ReadOnlyConnection;
import com.zb.p2p.trade.service.match.CreditorInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p> 债权信息服务 </p>
 *
 * @author Vinson
 * @version CreditorInfoServiceImpl.java v1.0 2018/4/21 16:59 Zhengwenquan Exp $
 */
@Slf4j
@Service
public class CreditorInfoServiceImpl implements CreditorInfoService {

    @Autowired
    private CreditorInfoMapper creditorInfoMapper;
    @Autowired
    private CashRecordMapper cashRecordMapper;
    @Autowired
    private InterfaceRetryService interfaceRetryService;
    @Autowired
    private OrderAPI orderAPI;
    @Autowired
    private DistributedSerialNoService distributedSerialNoService;

    @Override
    public void create(LoanRequestEntity loanRequestEntity, List<MatchRecord> MatchRecordList) throws BusinessException {

        log.info("开始创建债权信息，{}", MatchRecordList);

        for (MatchRecord matchRecord : MatchRecordList) {
            // 1.校验
            validate(matchRecord);
            // 2. 转换
            CreditorInfo creditorInfo = CreditorInfoConverter.convertByMatchRecord(matchRecord);
            //生成流水号
            String creditorSerialNo = distributedSerialNoService.generatorSerialNoByIncrement(
                    SequenceEnum.CREDITOR, loanRequestEntity.getSaleChannel(), 1);
            creditorInfo.setCreditorNo(creditorSerialNo);
            // 2. 处理
            try {
                int rows = creditorInfoMapper.insert(CreditorInfoConverter.convert(creditorInfo));
                Assert.isTrue(rows == 1, "债权信息保存失败");
            } catch (DuplicateKeyException e) {
                log.warn("债权插入重复[{}]", creditorInfo);
                // 修改
                CreditorInfo creditorInfoUp = new CreditorInfo();
                creditorInfoUp.setInvestAmount(matchRecord.getMatchedAmount());
                creditorInfoUp.setId(creditorInfo.getId());
                int updateRows = updateByPrimaryKeySelective(creditorInfoUp);
                Assert.isTrue(updateRows == 1, "更新债权信息发生异常");
                // 继续处理下一个
            }
        }
        log.info("创建债权信息完成");
    }

    // 校验
    private void validate(MatchRecord matchRecord) throws BusinessException {
        Assert.isTrue(matchRecord.getMatchStatus() == MatchStatusEnum.MATCH_SUCCESS, "生成债权时匹配状态必须为匹配成功");
    }

    @Override
    public int insertSelective(CreditorInfo creditorInfo) {
        if (creditorInfo == null) {
            return 0;
        }
        return creditorInfoMapper.insertSelective(CreditorInfoConverter.convert(creditorInfo));
    }

    @Override
    public int updateByPrimaryKeySelective(CreditorInfo creditorInfo) {
        if (creditorInfo == null) {
            return 0;
        }
        return creditorInfoMapper.updateByPrimaryKeySelective(CreditorInfoConverter.convert(creditorInfo));
    }

    @Override
    @ReadOnlyConnection
    public CreditorInfo selectByOrderAndAssetNo(String orderNo, String assetNo) {
        if (StringUtils.isBlank(orderNo) || StringUtils.isBlank(assetNo)) {
            return null;
        }
        return CreditorInfoConverter.convert(creditorInfoMapper.selectByOrderAndAsset(orderNo, assetNo));
    }

    @Override
    @ReadOnlyConnection
    public CreditorInfo selectByOrderAndTransferAssetNo(String orderNo, String transferAssetNo) {
        if (StringUtils.isBlank(orderNo) || StringUtils.isBlank(transferAssetNo)) {
            return null;
        }
        return CreditorInfoConverter.convert(creditorInfoMapper.selectByOrderAndTransferAsset(orderNo, transferAssetNo));
    }

    @Override
    @ReadOnlyConnection
    public List<CreditorInfo> findByAssetNoStatus(String assetNo, CreditorStatusEnum curStatus) {
        return CreditorInfoConverter.convertToList(creditorInfoMapper.selectByAssetNoStatus(assetNo, curStatus.getCode()));
    }

    @Override
    @ReadOnlyConnection
    public BigDecimal loadTotalInvestedAmount(String assetNo) {
        return creditorInfoMapper.selectTotalInvestedAmount(assetNo, GlobalVar.CREDITOR_SUCCESS_STATUS);
    }

    @Override
    @ReadOnlyConnection
    public Map<String, BigDecimal> loadMemberTotalSuccessAmount(String assetNo, List<String> memberIds) {
        List<CashSumAmountEntity> sumAmountList = creditorInfoMapper.selectMemberTotalAmount(assetNo, memberIds, GlobalVar.CREDITOR_SUCCESS_STATUS);
        Assert.isTrue(!CollectionUtils.isEmpty(sumAmountList), "不存在投资记录");

        Map<String, BigDecimal> amountMap = new HashMap<>();
        for (CashSumAmountEntity assigned : sumAmountList) {
            amountMap.put(assigned.getMemberId(), assigned.getAmount());
        }
        return amountMap;
    }

    @Override
    @ReadOnlyConnection
    public Map<String, BigDecimal> loadOrderTotalSuccessAmount(String assetNo, List<String> orderIds) {
        List<CashSumAmountEntity> sumAmountList = creditorInfoMapper.selectOrderMatchedTotalAmount(assetNo, orderIds, GlobalVar.CREDITOR_SUCCESS_STATUS);
        Assert.isTrue(!CollectionUtils.isEmpty(sumAmountList), "不存在投资记录");

        Map<String, BigDecimal> amountMap = new HashMap<>();
        for (CashSumAmountEntity assigned : sumAmountList) {
            amountMap.put(assigned.getMemberId(), assigned.getAmount());
        }
        return amountMap;
    }

    @Override
    public void updateCreditorAmountAndNotifyOrder(CashRecordEntity cashRecordEntity) {

        CreditorInfoEntity updateCreditor = new CreditorInfoEntity();
        // 获取当前持仓债权
        CreditorInfoEntity creditorEntity = creditorInfoMapper.selectByPrimaryKey(Long.valueOf(cashRecordEntity.getRefNo()));
        //1. 扣减剩余待结本金和剩余待结利息
        BigDecimal payingPrincipal = creditorEntity.getPayingPrinciple().subtract(cashRecordEntity.getCashAmount());
        BigDecimal payingInterest = creditorEntity.getPayingInterest().subtract(cashRecordEntity.getCashIncome());
        updateCreditor.setPayingPrinciple(payingPrincipal);
        updateCreditor.setPayingInterest(payingInterest);

        // 2. 覆盖最后一次回款本金、利息及日期
        if(creditorEntity.getLatestCashDate() == null
                || !DateUtil.isSameDay(cashRecordEntity.getCashDate(), creditorEntity.getLatestCashDate())) {
            // 2.1覆盖
            updateCreditor.setLatestPrinciple(cashRecordEntity.getCashAmount());
            updateCreditor.setLatestInterest(cashRecordEntity.getCashIncome());
            updateCreditor.setLatestCashDate(cashRecordEntity.getCashDate());
        }else {
            // 2.2 如果上次回款日期为当前兑付日，则累加
            updateCreditor.setLatestPrinciple(BigDecimalUtil.add(creditorEntity.getLatestPrinciple(), cashRecordEntity.getCashAmount()));
            updateCreditor.setLatestInterest(BigDecimalUtil.add(creditorEntity.getLatestInterest(), cashRecordEntity.getCashIncome()));
        }

        // 3. 累加累计回款本金和利息
        updateCreditor.setPaidPrinciple(BigDecimalUtil.add(creditorEntity.getPaidPrinciple(), cashRecordEntity.getCashAmount()));
        updateCreditor.setPaidInterest(BigDecimalUtil.add(creditorEntity.getPaidInterest(), cashRecordEntity.getCashIncome()));

        // save
        updateCreditor.setId(creditorEntity.getId());
        if (updateCreditor.getPayingPrinciple().compareTo(BigDecimal.ZERO) == 0
                || updateCreditor.getPayingInterest().compareTo(BigDecimal.ZERO) == 0){
            updateCreditor.setStatus(CreditorStatusEnum.FINISHED.getCode());
        }
        int rows = creditorInfoMapper.updateAccountByPrimaryKey(updateCreditor);
        Assert.isTrue(rows == 1, "兑付后更新持仓发生数据异常，待更新信息：" + updateCreditor);

        // 通知订单服务兑付结果
        notifyOrderCashResult(creditorEntity, cashRecordEntity);
    }

    /**
     * 通知订单服务
     * @param creditorEntity
     * @param recordEntity
     */
    private void notifyOrderCashResult(CreditorInfoEntity creditorEntity, CashRecordEntity recordEntity) {
        OrderRelationReq cashReq = new OrderRelationReq();
        cashReq.setOriginalId(creditorEntity.getOrgOrderNo());
        cashReq.setParentId(creditorEntity.getFromOrderNo());
        cashReq.setAmount(recordEntity.getCashAmount().add(recordEntity.getCashIncome()));
        try {
            CommonResponse orderResp = orderAPI.loanRepayment(cashReq);
            log.info("通知订单服务兑付结果{}", orderResp);
        } catch (Exception e) {
            log.error("通知订单兑付结果失败，将交由定时任务发起重试", e);
            InterfaceRetryEntity interfaceRetryEntity = new InterfaceRetryEntity();
            interfaceRetryEntity.setRequestParam(JSON.toJSONString(cashReq));
            interfaceRetryEntity.setBusinessType(InterfaceRetryBusinessTypeEnum.CASH_RESULT_NOTIFY_TXS.getCode());

            interfaceRetryService.saveInterfaceRetryRecord(interfaceRetryEntity);
        }
    }

    @Override
    @ReadOnlyConnection
    public List<CashSumAmountEntity> selectCashSumAmountByMatch(List<MatchRecord> matchRecordList, CashStatusEnum status) {
        List<Long> matchIds = CreditorInfoConverter.convert2CreditorIds(matchRecordList);
        Assert.isTrue(!CollectionUtils.isEmpty(matchIds), "匹配记录为空");
        List<String> creditorIdList = creditorInfoMapper.selectCreditorIdzByMatchIdz(matchIds);
        Assert.isTrue(!CollectionUtils.isEmpty(creditorIdList), "根据匹配记录未找到债权Id");

        return cashRecordMapper.selectCreditorTotalAmount(creditorIdList, status.getCode());
    }

    @Override
    public void updateCreditorAmountExpect(List<MatchRecord> matchRecordList) {

        // 根据匹配记录逐个汇总兑付计划持仓
        List<CashSumAmountEntity> cashSumAmountList = selectCashSumAmountByMatch(matchRecordList, CashStatusEnum.CASH_WAIT_ACTUAL);

        Assert.isTrue(!CollectionUtils.isEmpty(cashSumAmountList), "根据匹配记录未找到债权持仓信息");

        // 更新债权记
        for (CashSumAmountEntity cashSumAmountEntity : cashSumAmountList) {
            // 设置
            CreditorInfoEntity updateCreditor = new CreditorInfoEntity();
            updateCreditor.setId(Long.valueOf(cashSumAmountEntity.getMemberId()));
            updateCreditor.setPayingPrinciple(cashSumAmountEntity.getExpectPrincipal());
            updateCreditor.setPayingInterest(cashSumAmountEntity.getExpectInterest());
            updateCreditor.setStatus(CreditorStatusEnum.WAIT_CASH.getCode());

            int rows = creditorInfoMapper.updateAccountByPrimaryKey(updateCreditor);
            if (rows != 1) {
                log.warn("计算应收后更新持仓发生数据异常，待更新信息：{}", updateCreditor);
            }
        }

    }
}
