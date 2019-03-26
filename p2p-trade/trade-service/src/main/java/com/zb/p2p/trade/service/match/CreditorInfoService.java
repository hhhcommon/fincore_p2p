package com.zb.p2p.trade.service.match;

import com.zb.p2p.trade.common.domain.CashPlan;
import com.zb.p2p.trade.common.domain.CreditorInfo;
import com.zb.p2p.trade.common.domain.MatchRecord;
import com.zb.p2p.trade.common.enums.CashStatusEnum;
import com.zb.p2p.trade.common.enums.CreditorStatusEnum;
import com.zb.p2p.trade.common.exception.BusinessException;
import com.zb.p2p.trade.persistence.dto.CashSumAmountEntity;
import com.zb.p2p.trade.persistence.entity.CashRecordEntity;
import com.zb.p2p.trade.persistence.entity.LoanRequestEntity;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * <p> 债权信息服务接口 </p>
 *
 * @author Vinson
 * @version CreditorInfoService.java v1.0 2018/4/21 16:58 Zhengwenquan Exp $
 */
public interface CreditorInfoService {

    /**
     * 创建债权信息
     * @throws BusinessException
     */
    void create(LoanRequestEntity loanRequestEntity, List<MatchRecord> MatchRecordList) throws BusinessException;

    /**
     * 插入
     * @param creditorInfo
     * @return
     */
    int insertSelective(CreditorInfo creditorInfo);

    /**
     * 更新
     * @param creditorInfo
     * @return
     */
    int updateByPrimaryKeySelective(CreditorInfo creditorInfo);

    /**
     * 根据订单号和资产编号查询
     * @param orderNo
     * @param assetNo
     * @return
     */
    CreditorInfo selectByOrderAndAssetNo(String orderNo, String assetNo);

    /**
     * 根据订单号和转让资产编号查询
     * @param orderNo
     * @param transferAssetNo
     * @return
     */
    CreditorInfo selectByOrderAndTransferAssetNo(String orderNo, String transferAssetNo);

    /**
     * 根据资产编号（转让/原始）状态查询
     * @param assetNo
     * @param curStatus
     * @return
     */
    List<CreditorInfo> findByAssetNoStatus(String assetNo, CreditorStatusEnum curStatus);

    /**
     * 根据资产查询投资总额
     * @param assetNo
     * @return
     */
    BigDecimal loadTotalInvestedAmount(String assetNo);

    /**
     * 根据资产和会员统计投资总额
     * @param assetNo
     * @param memberIds
     * @return
     */
    Map<String, BigDecimal> loadMemberTotalSuccessAmount(String assetNo, List<String> memberIds);

    /**
     * 根据资产和订单统计匹配总额
     * @param assetNo
     * @param memberIds
     * @return
     */
    Map<String, BigDecimal> loadOrderTotalSuccessAmount(String assetNo, List<String> memberIds);

    /**
     * 单个更新持有债权的持仓信息(兑付后)
     * @param cashRecordEntity
     */
    void updateCreditorAmountAndNotifyOrder(CashRecordEntity cashRecordEntity);

    /**
     * 根据匹配记录及状态查询兑付持仓信息
     * @param matchRecordList
     * @param status
     * @return
     */
    List<CashSumAmountEntity> selectCashSumAmountByMatch(List<MatchRecord> matchRecordList, CashStatusEnum status);

    /**
     * 批量更新持有债权的持仓信息（计算应收后）
     * @param matchRecordList
     */
    void updateCreditorAmountExpect(List<MatchRecord> matchRecordList);
}
