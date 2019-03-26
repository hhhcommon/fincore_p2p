package com.zb.p2p.trade.persistence.dao;

import com.zb.p2p.trade.persistence.entity.PaymentRecordEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * Created by limingxin on 2018/1/11.
 */
@Repository("paymentRecordMapper")
public interface PaymentRecordMapper {

    /**
     * 插入
     * @param paymentRecordEntity
     */
    void insertSelective(PaymentRecordEntity paymentRecordEntity);

    /**
     * 批量插入
     * @param recordList
     * @return
     */
    int batchInsert(@Param("recordList") List<PaymentRecordEntity> recordList);

    int updatePaymentRecord(PaymentRecordEntity paymentRecordEntity);

    List<PaymentRecordEntity> queryFailAll(@Param("productCode") String productCode);

    String queryBySerialNo(@Param("serialNo") String serialNo);

    PaymentRecordEntity selectBySerialNo(@Param("serialNo") String serialNo);

    /**
     * 根据状态和引用Id查询
     * @param refId
     * @param statusList
     * @return
     */
    List<PaymentRecordEntity> selectByRefAndType(@Param("refId") String refId, @Param("status") List<String> statusList);

    /**
     * 查询近X天还处于支付处理中的支付记录
     * @param tradeStatus
     * @param beginTime
     * @param endTime
     * @param limitRows
     * @return
     */
    List<PaymentRecordEntity> selectWaitingClose(@Param("tradeStatus") String tradeStatus,
                                                 @Param("beginTime") Date beginTime,
                                                 @Param("endTime") Date endTime,
                                                 @Param("limitRows") int limitRows);

    /**
     * 查询近X天还未成功的支付记录
     * @param tradeStatus
     * @param beginTime
     * @param endTime
     * @param limitRows
     * @return
     */
    List<PaymentRecordEntity> selectFinalFailed(@Param("tradeStatus") String tradeStatus,
                                                @Param("beginTime") Date beginTime,
                                                @Param("endTime") Date endTime,
                                                @Param("limitRows") int limitRows);

    /**
     * 根据交易类型和引用Id查询
     * @param refId
     * @param tradeType
     * @return
     */
    List<PaymentRecordEntity> selectByRefIdAndTradeTypeAndTradeStatus(@Param("refId") String refId,
                                                        @Param("tradeType") String tradeType,
                                                        @Param("tradeStatus") String tradeStatus);

    /**
     * 根据当前交易流水号（tradeSerialNo）查询支付流水
     * @param tradeSerialNo
     * @return
     */
    PaymentRecordEntity selectByTradeSerialNo(@Param("tradeSerialNo") String tradeSerialNo);

    /**
     * 根据原始交易流水号（tradeSerialNo）和交易状态（tradeStatus）查询支付流水列表
     * @param orgTradeSerialNo
     * @param tradeType
     * @return
     */
    List<PaymentRecordEntity> selectByOrgTradeSerialNoAndTradeType(@Param("orgTradeSerialNo") String orgTradeSerialNo,
                                              @Param("tradeType") String tradeType);

    /**
     * 统计已放款金额
     * @param tradeType
     * @param tradeStatus
     * @return
     */
    BigDecimal countAlreadyLoanAmtTotal(@Param("tradeType") String tradeType,
                                        @Param("tradeStatus") String tradeStatus);
}
