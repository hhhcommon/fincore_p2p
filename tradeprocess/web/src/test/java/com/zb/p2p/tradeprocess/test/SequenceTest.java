package com.zb.p2p.tradeprocess.test;

import com.zb.p2p.tradeprocess.web.TradeProcessApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Iterator;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by zhangxin on 2017/8/25.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TradeProcessApplication.class)
public class SequenceTest {
    private static ExecutorService fixedThreadPool = Executors.newFixedThreadPool(1);
    private Queue<Long> preNodes = new ConcurrentLinkedQueue<>();
    private static String ZK_ADDRESS = "192.168.0.65"; //192.168.0.65
    private static String PATH = "sequence/p2p";//  /sequence/p2p
    private static String SEQ = "seq";//seq;
    @Test
    public void assetMatch(){
        final String idstr = "seq0000000178";
        final long id = Long.parseLong(idstr);
        System.out.println("id====================="+id);
        preNodes.add(id);
        //删除上一个节点
        fixedThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                Iterator<Long> iterator = preNodes.iterator();
                if (iterator.hasNext()) {
                    long preNode = iterator.next();
                    if (preNode < id) {
                        final String format = "%0" + idstr.length() + "d";
                        String preIdstr = String.format(format, preNode);
                        final String prePath = PATH + "/" + "RESERVATION" + "/" + SEQ + preIdstr;
                        System.out.println("prePath================"+prePath);
                    }
                }
            }
        });

    }

}