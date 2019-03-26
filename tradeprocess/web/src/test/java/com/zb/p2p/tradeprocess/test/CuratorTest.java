package com.zb.p2p.tradeprocess.test;

import com.zb.p2p.service.common.DistributedLockService;
import com.zb.p2p.tradeprocess.web.TradeProcessApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by mengkai on 2017/8/25.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TradeProcessApplication.class)
public class CuratorTest {

    @Autowired
    DistributedLockService distributedLockService;

    @Test
    public void testLock() throws Exception {
        for(int i=0;i<500;i++){
            new Thread(){
                public void run(){
                    try {
                        distributedLockService.tryLock("wangwanbin1");
                        System.out.println("加锁了----");
                        distributedLockService.unLock("wangwanbin1");
                        System.out.println("解锁了----");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        }
        this.wait(30000);
    }

    @Test
    public void testLock2() throws Exception {
        try {
            distributedLockService.tryLock("wangwanbin111");
            System.out.println("加锁了----1");
            distributedLockService.tryLock("wangwanbin222");
            System.out.println("加锁了----2");
            distributedLockService.unLock("wangwanbin222");
            System.out.println("解锁了----2");
            distributedLockService.unLock("wangwanbin111");
            System.out.println("解锁了----1");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}