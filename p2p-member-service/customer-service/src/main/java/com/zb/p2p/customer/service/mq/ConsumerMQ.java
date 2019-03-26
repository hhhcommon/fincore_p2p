package com.zb.p2p.customer.service.mq;

import com.aliyun.openservices.ons.api.Action;
import com.aliyun.openservices.ons.api.Consumer;
import com.zb.p2p.customer.client.domain.RegisteredBackReq;
import com.zb.p2p.customer.client.domain.TxsResponse;
import com.zb.p2p.customer.client.txs.TxsClient;
import com.zb.p2p.customer.common.enums.AppCodeEnum;
import com.zb.p2p.customer.common.exception.AppException;
import com.zb.p2p.customer.common.util.JsonUtil;
import com.zb.p2p.customer.dao.NotifyMapper;
import com.zb.p2p.customer.dao.domain.NotifyInfo;
import com.zb.qjs.common.mq.runner.MQConsumerRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class ConsumerMQ implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(CommandLineRunner.class);

    @Autowired
    NotifyMapper notifyMapper;

    @Value("${ons.ali.topic}")
    private String topic;//

    @Autowired
    TxsClient txsClient;


    @Override
    public void run(String... arg0) throws Exception {
        Consumer consumer = MQConsumerRunner.getConsumer();
        consumer.subscribe(topic, "*", (message, context) -> {
            String body = new String(message.getBody());

            logger.info(body + "=====body====");
            RegisteredBackReq registeredBackReq = (RegisteredBackReq) JsonUtil.getObjectByJsonStr(body, RegisteredBackReq.class);

            logger.info("回调唐小僧request:====" + registeredBackReq);
            TxsResponse registeredBackResp = txsClient.onregistered(registeredBackReq);
            logger.info("回调唐小僧response:" + registeredBackResp.getCode() + " data:" + registeredBackResp.getData() + "---");

            logger.info("===========registeredBackResp回调txs=============" + registeredBackResp.getMsg());

            //0108 表示重复
            if ("0000".equals(registeredBackResp.getCode()) || "0108".equals(registeredBackResp.getCode())) {
                logger.info("回调成功TXN TXNCODE:B0111");

                NotifyInfo n = new NotifyInfo();
                n.setNotifyResult(1);
                n.setCustomerId(Long.parseLong(registeredBackReq.getP2pAccountId()));
                n.setUpdateTime(new Date());

                logger.info("回调成功TXN 开始更新 回调状态 QJScusId:" + registeredBackReq.getP2pAccountId());
                int a = notifyMapper.updateBycustomerId(n);
                logger.info("回调成功TXN 开始更新 回调状态结果：" + a);
            } else {
                logger.info("回调失败TXN TXNCODE:B0111");
                throw AppException.getInstance(AppCodeEnum._A121_MQ_CONSUM);
            }

            return Action.CommitMessage;
        });
        logger.info("----consumer.start()---");
        consumer.start();
        logger.info("----consumer.finnish---");

    }


}
