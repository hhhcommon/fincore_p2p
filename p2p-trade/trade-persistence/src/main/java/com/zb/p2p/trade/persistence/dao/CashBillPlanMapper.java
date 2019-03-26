package com.zb.p2p.trade.persistence.dao;

import com.zb.p2p.trade.persistence.dto.CashRecordDto;
import com.zb.p2p.trade.persistence.dto.CashSumAmountEntity;
import com.zb.p2p.trade.persistence.entity.CashBillPlanEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository("cashBillPlanMapper")
public interface CashBillPlanMapper {
    int deleteByPrimaryKey(Long id);

    int insert(CashBillPlanEntity record);

    CashBillPlanEntity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CashBillPlanEntity record);

    int updateByPrimaryKey(CashBillPlanEntity record);

    /**
     * 根据状态更新
     * @param billPlan
     * @param preStatus
     * @return
     */
    int updateByPreStatus(@Param("billPlan") CashBillPlanEntity billPlan,
                          @Param("preStatus") String preStatus);

    /**
     * 根据key获取
     * @param assetNo
     * @param stageNo
     * @param repayType
     * @param lockTag
     * @return
     */
    CashBillPlanEntity selectByKey(@Param("assetNo") String assetNo,
                                   @Param("stageNo") Integer stageNo,
                                   @Param("repayType") String repayType,
                                   @Param("lockTag") boolean lockTag);

    /**
     * 根据资产编码统计总金额
     * @param assetNo
     * @param repayType
     * @return
     */
    CashSumAmountEntity selectCashAmountTotal(@Param("assetNo") String assetNo,
                                              @Param("repayType") String repayType);

    /**
     * 获取最大期号
     * @param assetNo
     * @param repayType
     * @param isPrincipal
     * @return
     */
    Integer selectMaxValidStage(@Param("assetNo") String assetNo,
                                @Param("repayType") String repayType,
                                @Param("isPrincipal") boolean isPrincipal);

    /**
     * 根据状态和时间获取模板列表
     * @param status
     * @param beginTime
     * @param endTime
     * @param limitRows
     * @return
     */
    List<CashBillPlanEntity> selectAllCashFinished(@Param("status") String status,
                                                   @Param("beginTime") Date beginTime,
                                                   @Param("endTime") Date endTime,
                                                   @Param("limitRows") int limitRows);

    List<CashBillPlanEntity> selectByAssetAndStatus(@Param("assetNoList") List<String> assetNoList,
                                                    @Param("status") String status);

    /**
     * 分页查询时间段内的所有兑付计划模板
     * @param status
     * @param beginTime
     * @param endTime
     * @param limitRows
     * @return
     */
    List<CashBillPlanEntity> selectWaitingPerform(@Param("status") String status,
                                                @Param("beginTime") Date beginTime,
                                                @Param("endTime") Date endTime,
                                                @Param("limitRows") int limitRows);

    /**
     * 根据条件查询
     * @param beginTime
     * @param assetNo
     * @param memberId
     * @param startIndex
     * @param endIndex
     * @return
     */
    List<CashRecordDto> queryCashPlanInfoByCondition(@Param("beginTime") Date beginTime,
                                                     @Param("endTime") Date endTime,
                                                     @Param("assetNo") String assetNo,
                                                     @Param("memberId") String memberId,
                                                     @Param("startIndex") int startIndex,
                                                     @Param("endIndex") int endIndex);
}