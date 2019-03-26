package com.zb.p2p.trade.common.queue;

import com.aliyun.mns.client.CloudAccount;
import com.aliyun.mns.client.MNSClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 消息消费者
 */
public class MnsMsgConsumer{

    /**
     * 日志器
     */
    private static Logger logger = LoggerFactory.getLogger(MnsMsgConsumer.class);

    /**
     * 阿里云授权信息
     */
    private CloudAccount cloudAccount;

    /**
     * 无消息时等待时间,默认5秒
     */
    private int waitSeconds = 5;

    /**
     * 消息接收线程池
     */
    private ThreadPoolTaskExecutor taskExecutor;

    /**
     * 消息处理器映射关系
     */
    private Map<String, AbstractMnsMsgHandler> topicListenerMap;

    /**
     * 消息处理线程列表
     */
    private List<MnsMsgHandlerThread> topicThreadList = new ArrayList<>();
    /**
     * 是否弱消费
     */
    private boolean weakConsumer = false;

    public void startup() {
        logger.info("MnsConsumer正在启动");
        for (String key : topicListenerMap.keySet()) {
            MnsMsgHandlerThread thread = new MnsMsgHandlerThread(weakConsumer, this.cloudAccount, this.waitSeconds,
                    key, topicListenerMap.get(key));
            topicThreadList.add(thread);
            taskExecutor.execute(thread);
        }
        logger.info("MnsConsumer启动完毕");
    }

    public void shutdown() {
        logger.info("MnsConsumer正在停止");
        for (MnsMsgHandlerThread thread : this.topicThreadList) {
            thread.shutdown();
        }
        try {
            Thread.sleep(this.waitSeconds * this.topicThreadList.size() * 1000L);
        } catch (InterruptedException e) {
            logger.error("Interrupted thread",e);
            Thread.currentThread().interrupt();
        } finally {
            MNSClient client = cloudAccount.getMNSClient();
            if (client != null) {
                client.close();
            }
        }
        logger.info("MnsConsumer停止完毕");
    }

    public CloudAccount getCloudAccount() {
        return cloudAccount;
    }

    public void setCloudAccount(CloudAccount cloudAccount) {
        this.cloudAccount = cloudAccount;
    }

    public ThreadPoolTaskExecutor getTaskExecutor() {
        return taskExecutor;
    }

    public void setTaskExecutor(ThreadPoolTaskExecutor taskExecutor) {
        this.taskExecutor = taskExecutor;
    }

    public Map<String, AbstractMnsMsgHandler> getTopicListenerMap() {
        return topicListenerMap;
    }

    public void setTopicListenerMap(Map<String, AbstractMnsMsgHandler> topicListenerMap) {
        this.topicListenerMap = topicListenerMap;
    }

    public int getWaitSeconds() {
        return waitSeconds;
    }

    public void setWaitSeconds(int waitSeconds) {
        this.waitSeconds = waitSeconds;
    }

    public boolean isWeakConsumer() {
        return weakConsumer;
    }

    public void setWeakConsumer(boolean weakConsumer) {
        this.weakConsumer = weakConsumer;
    }

}
