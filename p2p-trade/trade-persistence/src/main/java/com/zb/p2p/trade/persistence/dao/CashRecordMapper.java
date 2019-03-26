package com.zb.p2p.trade.persistence.dao;


import com.zb.p2p.trade.persistence.dto.CashKeyState;
import com.zb.p2p.trade.persistence.dto.CashNotify;
import com.zb.p2p.trade.persistence.dto.CashSumAmountEntity;
import com.zb.p2p.trade.persistence.entity.CashBillPlanEntity;
import com.zb.p2p.trade.persistence.entity.CashRecordEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Repository("cashRecordMapper")
public interface CashRecordMapper {
    int deleteByPrimaryKey(Long id);

    int insert(CashRecordEntity record);

    int insertSelective(CashRecordEntity record);

    CashRecordEntity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CashRecordEntity record);

    int updateByPrimaryKey(CashRecordEntity record);

    /**
     * 去掉日期验证，因为执行兑付可能滞后，而且我们也有一个前置条件：兑付产品的所有资产是否还款来控制能否执行
     *
     * @param productCode
     * @param loanNoList
     * @return
     */
    List<CashRecordEntity> listCashRecord(@Param("productCode") String productCode, @Param("list") List<String> loanNoList);

    /**
     * 分页查询时间段内的所有兑付计划
     * @param status
     * @param beginTime
     * @param endTime
     * @param limitRows
     * @return
     */
    List<CashRecordEntity> selectWaitingPerform(@Param("status") String status,
                                                @Param("beginTime") Date beginTime,
                                                @Param("endTime") Date endTime,
                                                @Param("limitRows") int limitRows);

    /**
     * 查询产品是否全部兑付完成
     *
     * @param productCode
     * @return
     */
    int countCashRecord(@Param("productCode") String productCode);

    int updateStatusByReqNo(CashRecordEntity record);

    int updateStatusByPCodeAndMemId(CashRecordEntity record);

    /**
     * 根据模板批量插入
     * @param cashBillPlanEntity
     * @param statusList
     */
    int batchInsertByBillPlan(@Param("billPlan") CashBillPlanEntity cashBillPlanEntity,
                              @Param("statusList") List<String> statusList);

    /**
     * 更新
     * @param plan
     * @param preStatus
     * @return
     */
    int updateByKey(@Param("plan") CashRecordEntity plan,
                    @Param("preStatus") String preStatus);

    /**
     * 查询总额
     *
     * @param productCode
     */
    CashRecordEntity getSumCashAmount(@Param("productCode") String productCode);

    List<CashRecordEntity> queryCashRecord(CashRecordEntity record);

    CashRecordEntity selectByReqNo(@Param("reqNo") String reqNo);

    /**
     * 查询待提现（成功到余额）的列表
     *
     * @return
     */
    List<CashRecordEntity> selectByWithdrawlStatus(@Param("status") String status,
                                                   @Param("payChannel") String payChannel,
                                                   @Param("beginTime") Date beginTime,
                                                   @Param("endTime") Date endTime,
                                                   @Param("limit") int limit);

    /**
     * 以资产纬度查询已经兑付到卡成功的记录
     *
     * @return
     */
    List<CashNotify> queryAssetsByCashEnd();

    /**
     * 以订单纬度查询已经兑付到卡成功的记录
     *
     * @return
     */
    List<CashNotify> queryOrdersByCashEnd();

    List<CashNotify> queryOrdersByCashing();

    void updateCashByCashCard();

    /**
     * 查询兑付过程中调用支付异常的兑付数据
     *
     * @return
     */
    List<String> notifyAssetPartyCashFail();

    int updateByPreStatus(@Param("plan") CashRecordEntity plan,
                          @Param("preStatus") String preStatus);


    /**
     * 根据模板ID和状态查询统计
     * @param billPlanId
     * @return
     */
    List<CashRecordEntity> selectByBillPlanId(@Param("billPlanId") Long billPlanId,
                                              @Param("status") String status);

    /**
     * 根据模板ID查询统计
     * @param billPlanId
     * @return
     */
    List<CashKeyState> selectStatByBillPlanId(@Param("billPlanId") Long billPlanId);

    /**
     * 根据key查询
     * @param assetNo
     * @param stageNo
     * @param repayType
     * @return
     */
    List<CashRecordEntity> selectByKey(@Param("assetNo") String assetNo,
                                       @Param("stageNo") int stageNo,
                                       @Param("repayType") String repayType);

    /**
     * 根据资产统计投资人金额
     * @param assetNo
     * @param memberIds
     * @param repayType
     * @return
     */
    List<CashSumAmountEntity> selectMemberTotalAmount(@Param("assetNo") String assetNo,
                                                    @Param("memberIds") List<String> memberIds,
                                                    @Param("repayType") String repayType);

    /**
     * 根据资产统计投资订单的金额
     * @param assetNo
     * @param orderIds
     * @param repayType
     * @return
     */
    List<CashSumAmountEntity> selectOrderTotalAmount(@Param("assetNo") String assetNo,
                                                      @Param("orderIds") List<String> orderIds,
                                                      @Param("repayType") String repayType);

    /**
     * 根据转让债权查询
     * @param memberId
     * @param creditorId
     * @return
     */
    List<CashRecordEntity> selectByAttornCreditor(@Param("notifyTime") String memberId,
                                                  @Param("creditorId") String creditorId);

    /**
     * 根据债权Id统计持仓信息
     * @param creditorIdList
     * @param status
     * @return
     */
    List<CashSumAmountEntity> selectCreditorTotalAmount(@Param("creditorIdList") List<String> creditorIdList,
                                                        @Param("status") String status);

    /**
     * 根据资产和投资订单号查询
     * @param extOrderNo
     * @return
     */
    BigDecimal selectByAssetOrderNo(@Param("assetNo") String assetNo,
                                    @Param("extOrderNo") String extOrderNo);
}