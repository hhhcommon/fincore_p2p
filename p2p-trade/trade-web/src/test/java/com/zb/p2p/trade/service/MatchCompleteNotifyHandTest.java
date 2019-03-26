package com.zb.p2p.trade.service;

import com.alibaba.fastjson.JSON;
import com.zb.p2p.trade.common.queue.model.MatchMqResult;
import com.zb.p2p.trade.service.topic.AssetMatchCompleteNotifyListener;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <p> 匹配完成结果处理 </p>
 *
 * @author Vinson
 * @version MatchCompleteNotifyHandTest.java v1.0 2018/4/27 18:53 Zhengwenquan Exp $
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = {"log.base=${TRADE_LOG_CONF_PATH/log", "spring.config.location=${TRADE_APP_CONF_PATH}/"})
public class MatchCompleteNotifyHandTest {

    @Autowired
    private AssetMatchCompleteNotifyListener assetMatchCompleteNotifyListener;

    @Test
    public void testMatchCompleteHandle() throws Exception{

        MatchMqResult result = new MatchMqResult();
        result.setAssetCode("201833060906335");
        result.setAssetStatus("PARTLY_LOAN_SUCCESS");
        result.setDownShelveTime(new Date());
        result.setMatchCounts(2);
        result.setMatchAmount(new BigDecimal(30000.00));

        assetMatchCompleteNotifyListener.consume(JSON.toJSONString(result));
    }
}
