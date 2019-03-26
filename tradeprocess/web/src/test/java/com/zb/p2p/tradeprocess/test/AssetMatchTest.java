package com.zb.p2p.tradeprocess.test;

import com.zb.p2p.service.match.MatchRecordService;
import com.zb.p2p.service.order.OrderService;
import com.zb.p2p.tradeprocess.web.TradeProcessApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

/**
 * Created by zhangxin on 2017/8/25.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TradeProcessApplication.class)
public class AssetMatchTest {

    @Autowired
    MatchRecordService matchRecordService;

    @Autowired
    OrderService orderService;

    @Test
    public void assetMatch(){
        System.out.println(new Date());
//        matchRecordService.assetMatch();
        System.out.println(new Date());

    }

}