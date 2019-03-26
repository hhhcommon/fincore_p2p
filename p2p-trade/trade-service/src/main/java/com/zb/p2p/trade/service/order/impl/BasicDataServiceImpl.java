package com.zb.p2p.trade.service.order.impl;

import com.zb.fincore.common.utils.BeanUtils;
import com.zb.p2p.trade.common.enums.LoanPaymentStatusEnum;
import com.zb.p2p.trade.common.enums.PayChannelEnum;
import com.zb.p2p.trade.persistence.dao.LoanRequestMapper;
import com.zb.p2p.trade.persistence.dao.OperationRecordMapper;
import com.zb.p2p.trade.persistence.dao.OrderRequestMapper;
import com.zb.p2p.trade.persistence.dto.LoanRequestDTO;
import com.zb.p2p.trade.persistence.entity.LoanRequestEntity;
import com.zb.p2p.trade.persistence.entity.OperationRecordEntity;
import com.zb.p2p.trade.persistence.entity.OrderRequestEntity;
import com.zb.p2p.trade.service.config.ReadOnlyConnection;
import com.zb.p2p.trade.service.order.BasicDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p> 描述 </p>
 *
 * @author Vinson
 * @version BasicDataServiceImpl.java v1.0 2018/6/1 0001 下午 6:31 Zhengwenquan Exp $
 */
@Slf4j
@Service
public class BasicDataServiceImpl implements BasicDataService{

    @Autowired
    OperationRecordMapper operationRecordMapper;
    @Autowired
    private LoanRequestMapper loanRequestMapper;
    @Autowired
    private OrderRequestMapper orderRequestMapper;

    @Override
    public boolean logOperation(String opType, String referId) {
        OperationRecordEntity operationRecord = new OperationRecordEntity();
        operationRecord.setOperationType(opType);
        operationRecord.setReferId(referId);
        try {
            return operationRecordMapper.insertSelective(operationRecord) == 1;
        } catch (DuplicateKeyException e) {
            log.error("幂等控制错误,操作={}", operationRecord, e);
        }
        return false;
    }

    public List<LoanRequestEntity> getWaitMatchLoanRequestList(Map<String, Object> params) {
        return loanRequestMapper.getWaitMatchLoanRequestList(params);
    }

    public List<OrderRequestEntity> queryOrderListByParams(Map<String, Object> params) {
        return orderRequestMapper.queryOrderListByParams(params);
    }

    public int updateLoanRequest(LoanRequestEntity loanRequest) {
        return loanRequestMapper.updateByPrimaryKeySelective(loanRequest);
    }

    public void updateMatchStatusAndAmountById(LoanRequestDTO loanRequestResp) throws Exception {
        loanRequestMapper.updateMatchStatusAndAmountById(BeanUtils.copyAs(loanRequestResp, LoanRequestEntity.class));
    }

    public List<LoanRequestEntity> selectByPaymentStatus(LoanPaymentStatusEnum paymentStatus, PayChannelEnum payChannel,
                                                         Date beginTime, Date endTime, int limit) {
        return loanRequestMapper.selectByPaymentStatus(paymentStatus.getCode(), payChannel.getCode(), beginTime, endTime, limit);
    }

    @Override
    @ReadOnlyConnection
    public LoanRequestEntity selectByAssetCodeForLoan(String assetCode) {
        return loanRequestMapper.selectByAssetCode(assetCode);
    }

    public LoanRequestEntity selectByAssetCodeForLoanByLoanNo(String loanNo) {
        return loanRequestMapper.selectByLoanNo(loanNo);
    }

    @Override
    @ReadOnlyConnection
    public List<LoanRequestEntity> queryLoanByUnCalcIncome(Map<String, Object> params) {
        return loanRequestMapper.queryLoanByUnCalcIncome(params);
    }

    @Override
    @ReadOnlyConnection
    public List<LoanRequestEntity> queryLoanForRepaySms(Date beginTime, Date endTime, int limitRows) {
        return loanRequestMapper.selectLoanForRepaySms(beginTime, endTime, limitRows);
    }
}
