package com.zb.p2p.trade.service.order;

import com.zb.p2p.trade.common.enums.LoanPaymentStatusEnum;
import com.zb.p2p.trade.common.enums.PayChannelEnum;
import com.zb.p2p.trade.persistence.dto.LoanRequestDTO;
import com.zb.p2p.trade.persistence.entity.LoanRequestEntity;
import com.zb.p2p.trade.persistence.entity.OrderRequestEntity;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by limingxin on 2017/8/31.
 */
public interface BasicDataService {

    /**
     * 插入操作记录
     * @param opType
     * @param referId
     * @return
     */
    boolean logOperation(String opType, String referId);

    List<LoanRequestEntity> getWaitMatchLoanRequestList(Map<String, Object> params) ;

    List<OrderRequestEntity> queryOrderListByParams(Map<String, Object> params) ;

    int updateLoanRequest(LoanRequestEntity loanRequest) ;

    void updateMatchStatusAndAmountById(LoanRequestDTO loanRequestResp) throws Exception ;

    List<LoanRequestEntity> selectByPaymentStatus(LoanPaymentStatusEnum paymentStatus,
                                                  PayChannelEnum payChannel,
                                                  Date beginTime, Date endTime, int limit) ;

    LoanRequestEntity selectByAssetCodeForLoan(String assetCode) ;

    LoanRequestEntity selectByAssetCodeForLoanByLoanNo(String loanNo);

    List<LoanRequestEntity> queryLoanByUnCalcIncome(Map<String, Object> params) ;

    /**
     *
     * @param beginTime
     * @param endTime
     * @param limitRows
     * @return
     */
    List<LoanRequestEntity> queryLoanForRepaySms(Date beginTime, Date endTime, int limitRows);
}
