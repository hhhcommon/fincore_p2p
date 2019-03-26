package com.zb.p2p.trade.service.process.impl;

import com.zb.p2p.trade.common.domain.MatchRecord;
import com.zb.p2p.trade.common.enums.AppCodeEnum;
import com.zb.p2p.trade.common.enums.LoanStatusEnum;
import com.zb.p2p.trade.common.exception.BusinessException;
import com.zb.p2p.trade.common.queue.model.MatchMqResult;
import com.zb.p2p.trade.persistence.entity.LoanRequestEntity;
import com.zb.p2p.trade.service.match.CreditorInfoService;
import com.zb.p2p.trade.service.match.MatchRecordService;
import com.zb.p2p.trade.service.process.CommonProcessor;
import com.zb.p2p.trade.service.process.MatchResultProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

/**
 * <p> 匹配结果消息处理基类 </p>
 *
 * @author Vinson
 * @version BaseMatchResultProcessor.java v1.0 2018/4/20 20:27 Zhengwenquan Exp $
 */
public abstract class BaseMatchResultProcessor implements MatchResultProcessor {

    @Resource
    protected TransactionTemplate transactionTemplate;

    @Autowired
    protected CommonProcessor commonProcessor;
    @Autowired
    protected CreditorInfoService creditorInfoService;
    @Autowired
    protected MatchRecordService matchRecordService;


    @Override
    public void process(LoanRequestEntity loanRequest, MatchMqResult result) throws BusinessException {

        // 根据下架资产获取匹配记录
        List<MatchRecord> matchRecordList = matchRecordService.listMatchRecordsByAssetNo(loanRequest.getTransferCode());

        Assert.notNull(matchRecordList, "未查到下架资产的匹配完成状态的匹配记录");
        Assert.isTrue(matchRecordList.size() == result.getMatchCounts(), "资产的匹配记录数量不正确");

        // 校验
        validateMatchAssetsInfo(loanRequest, matchRecordList);

        LoanStatusEnum status = LoanStatusEnum.getByCode(result.getAssetStatus());

        switch (status) {
            case PARTLY_LOAN_SUCCESS:
            case LOAN_SUCCESS:// 匹配成功
                processSuccess(loanRequest, matchRecordList, result);
                break;
            case LOAN_FAILED:// 匹配失败
                processFailed(loanRequest, result);
                break;
            default:
                throw new BusinessException(AppCodeEnum._0001_ILLEGAL_PARAMETER.getCode(), "不支持的结果类型");
        }
    }

    /**
     * 校验
     * @param loanRequest
     * @param matchRecordList
     */
    protected void validateMatchAssetsInfo(LoanRequestEntity loanRequest, List<MatchRecord> matchRecordList) {
        // 校验匹配金额是否等于资产发行额度
        BigDecimal totalAmount = BigDecimal.ZERO;
        for (MatchRecord matchRecord : matchRecordList) {
            totalAmount = totalAmount.add(matchRecord.getMatchedAmount());
        }
        Assert.isTrue(totalAmount.compareTo(loanRequest.getMatchedAmount()) == 0, "匹配投资总金额不等于借款金额");
    }


    protected abstract void processFailed(LoanRequestEntity loanRequest, MatchMqResult result) throws BusinessException;

    protected abstract void processSuccess(LoanRequestEntity loanRequest, List<MatchRecord> matchRecordList, MatchMqResult result) throws BusinessException;
}
