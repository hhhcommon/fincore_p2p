package com.zb.p2p.trade.persistence.dao;

import com.zb.p2p.trade.persistence.dto.CashSumAmountEntity;
import com.zb.p2p.trade.persistence.entity.CreditorInfoEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository("creditorInfoMapper")
public interface CreditorInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(CreditorInfoEntity record);

    int insertSelective(CreditorInfoEntity record);

    CreditorInfoEntity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CreditorInfoEntity record);

    int updateByPrimaryKey(CreditorInfoEntity record);

    CreditorInfoEntity selectByOrderAndAsset(@Param("orderNo") String orderNo, @Param("assetNo") String assetNo);

    /**
     * 根据订单编号及转让资产编号查询
     * @param orderNo
     * @param transferAssetNo
     * @return
     */
    CreditorInfoEntity selectByOrderAndTransferAsset(@Param("orderNo") String orderNo, @Param("transferAssetNo") String transferAssetNo);

    /**
     * 根据资产编号和状态查询（2.0生成兑付计划）
     * @param status
     * @return
     */
    List<CreditorInfoEntity> selectByAssetNoStatus(@Param("assetNo") String assetNo, @Param("status") String status);

    /**
     *
     * @param assetCode
     * @param statusList
     * @return
     */
    BigDecimal selectTotalInvestedAmount(@Param("assetNo") String assetCode,
                                         @Param("statusList") List<String> statusList);

    /**
     * 统计投资人投资金额
     * @param assetCode
     * @param memberIds
     * @return
     */
    List<CashSumAmountEntity> selectMemberTotalAmount(@Param("assetNo") String assetCode,
                                                      @Param("memberIds") List<String> memberIds,
                                                      @Param("statusList") List<String> statusList);

    /**
     * 统计投资订单投资金额
     * @param assetCode
     * @param orderIds
     * @return
     */
    List<CashSumAmountEntity> selectOrderMatchedTotalAmount(@Param("assetNo") String assetCode,
                                                            @Param("orderIds") List<String> orderIds,
                                                            @Param("statusList") List<String> statusList);

    /**
     * 根据债权Id查LoanNo
     * @param assetNo
     * @return
     */
    String queryLoanNoListByAssetNo(@Param("assetNo") String assetNo);

    /**
     *
     * @param record
     * @return
     */
    int updateAccountByPrimaryKey(CreditorInfoEntity record);

    /**
     * 查询匹配对应的所有债权记录Id
     * @param matchIds
     * @return
     */
    List<String> selectCreditorIdzByMatchIdz(@Param("matchIds") List<Long> matchIds);

    /**
     * 根据matchId查询债权记录
     * @param matchId
     * @return
     */
    CreditorInfoEntity selectByMatchId(@Param("matchId") long matchId);

    /**
     * 根据creditorNo查询债权记录
     * @param creditorNo
     * @return
     */
    CreditorInfoEntity selectByCreditorNo(@Param("creditorNo") String creditorNo);

    /**
     * 根据资产编号查询债权
     * @param assetNo
     * @return
     */
    List<CreditorInfoEntity> selectByAssetNo(@Param("assetNo") String assetNo);
}