package com.zb.p2p.trade.persistence.dao;


import com.taobao.pamirs.schedule.TaskItemDefine;
import com.zb.p2p.trade.persistence.dto.RepayAmountDTO;
import com.zb.p2p.trade.persistence.entity.LoanRequestEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository("loanRequestMapper")
public interface LoanRequestMapper {

    /**
     * 根据支付状态查询
     *
     * @param loanPaymentStatus
     * @return
     */
    List<LoanRequestEntity> selectByPaymentStatus(@Param("loanPaymentStatus") String loanPaymentStatus,
                                                  @Param("payChannel") String payChannel,
                                                  @Param("beginTime") Date beginTime,
                                                  @Param("endTime") Date endTime,
                                                  @Param("limit") int limit);

    LoanRequestEntity selectByPrimaryKey(Integer id);

    List<LoanRequestEntity> getWaitMatchLoanRequestList(Map<String, Object> params);

    int updateByPrimaryKeySelective(LoanRequestEntity record);

    int updateByPrimaryKey(LoanRequestEntity record);

    int updateMatchStatusAndAmountById(LoanRequestEntity record);

    /**
     * 根据assetCode查询
     */
    LoanRequestEntity selectByAssetCode(@Param("assetNo") String assetCode);

    /**
     * 根据loanNo查询
     */
    LoanRequestEntity selectByLoanNo(@Param("loanNo") String loanNo);

    List<LoanRequestEntity> queryLoanByUnCalcIncome(Map<String, Object> params);

    /**
     * 根据条件更新借款单状态
     * @param id 借款单主键
     * @param curLoanStatus 借款匹配状态
     * @param loan_payment_status 放款状态
     * @param loanPaymentTime 放款状态更新时间
     * @param contractStatus 是否已生成合同
     * @return
     */
    int updateLoanInfoStatus(@Param("id") long id,
                             @Param("curLoanStatus") String curLoanStatus,
                             @Param("loanPaymentStatus") String loan_payment_status,
                             @Param("loanPaymentTime") Date loanPaymentTime,
                             @Param("contractStatus") String contractStatus);

    /**
     * 待放款借款单
     * @param loanPaymentStatus
     * @param limit
     * @return
     */
    List<LoanRequestEntity> selectForLoanWithdrawal(@Param("loanPaymentStatus") String loanPaymentStatus,
                                                    @Param("deadlineTime") String deadlineTime,
                                                    @Param("list") List<TaskItemDefine> list,
                                                    @Param("limit") int limit);

    List<LoanRequestEntity> selectLoanForRepaySms(@Param("beginTime") Date beginTime,
                                                  @Param("endTime") Date endTime,
                                                  @Param("limitRows") int limitRows);
}