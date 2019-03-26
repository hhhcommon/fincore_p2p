package com.zb.p2p.trade.service.process;

import com.zb.p2p.trade.common.exception.BusinessException;
import com.zb.p2p.trade.common.queue.model.MatchMqResult;
import com.zb.p2p.trade.persistence.entity.LoanRequestEntity;

/**
 * <p> 匹配结果消息处理基类 </p>
 *
 * @author Vinson
 * @version MatchResultProcessor.java v1.0 2018/4/20 20:27 Zhengwenquan Exp $
 */

public interface MatchResultProcessor {

    /**
     * 匹配结果处理
     * @param loanRequest
     * @param result
     * @throws BusinessException
     */
    void process(LoanRequestEntity loanRequest, MatchMqResult result) throws BusinessException;

}
