package com.zb.p2p.paychannel.common.queue;

import com.alibaba.fastjson.JSON;
import com.aliyun.mns.client.CloudAccount;
import com.aliyun.mns.client.CloudQueue;
import com.aliyun.mns.client.MNSClient;
import com.aliyun.mns.model.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 消息生产者
 */
public class MnsMsgProducer {

    /**
     * 日志器
     */
    private static Logger logger = LoggerFactory.getLogger(MnsMsgProducer.class);

    /**
     * 阿里云授权信息
     */
    private CloudAccount cloudAccount;

    /**
     * 发送消息
     *
     * @param queueName 队列名称
     * @param message   消息体
     * @return 消息唯一标识
     */
    public <T> String putMessage(String queueName, T message) {
        try {
            MNSClient client = cloudAccount.getMNSClient();
            CloudQueue queueRef = client.getQueueRef(queueName);
            Message msg = new Message();
            msg.setMessageBody(JSON.toJSONString(message));
            queueRef.putMessage(msg);
        } catch (Exception e) {
            logger.error("MNS ENQUEUE ERROR", e);
        }
        return null;
    }

    public CloudAccount getCloudAccount() {
        return cloudAccount;
    }

    public void setCloudAccount(CloudAccount cloudAccount) {
        this.cloudAccount = cloudAccount;
    }
}
