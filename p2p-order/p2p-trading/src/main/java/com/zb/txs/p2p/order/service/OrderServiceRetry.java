/* 
* Copyright (c) 2017, 资邦金服(上海)网络科技有限公司. All Rights Reserved.
*
*/

package com.zb.txs.p2p.order.service;

import com.zb.txs.foundation.response.Result;
import com.zb.txs.p2p.business.enums.TradeStatusEnum;
import com.zb.txs.p2p.business.order.request.QueryTradeStatus;
import com.zb.txs.p2p.business.order.response.TradeStatusResp;
import com.zb.txs.p2p.code.CodeEnum;
import com.zb.txs.p2p.order.httpclient.OrderClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * Function:   order service with retry <br/>
 * Date:   2017年09月30日 下午2:11 <br/>
 *
 * @author liguoliang@zillionfortune.com
 */

@Service
@Slf4j
public class OrderServiceRetry {

    @Autowired
    private OrderClient orderClient;


    public TradeStatusEnum queryTradeStatus(QueryTradeStatus queryTradeStatus) throws IOException {
        Result<TradeStatusResp> tradeStatusRespResult = orderClient.queryTradeStatus(queryTradeStatus).execute().body();
        return checkReponseState(tradeStatusRespResult);
    }

    public TradeStatusEnum checkReponseState(Result<TradeStatusResp> result) {
        try {
            final String code = result.getCode().getCode();
            if (CodeEnum.RESPONSE_SUCCESS.getCode().equals(code)) {
                final String status = result.getData().getTradeStatus();
                if (TradeStatusEnum.S.getValue().equals(status)) {
                    return TradeStatusEnum.S;
                } else if (TradeStatusEnum.P.getValue().equals(status)) {
                    return TradeStatusEnum.P;
                }
            } else if (CodeEnum.RESPONSE_PROCESSING.getCode().equals(code)) {
                return TradeStatusEnum.P;
            }
        } catch (Throwable t) {
            log.error("check trade response error", t);
        }
        return TradeStatusEnum.F;
    }
}
