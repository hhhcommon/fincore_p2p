package com.zb.p2p.paychannel.common.queue;

import com.aliyun.mns.client.CloudAccount;
import com.aliyun.mns.client.CloudQueue;
import com.aliyun.mns.client.MNSClient;
import com.aliyun.mns.common.ClientException;
import com.aliyun.mns.common.ServiceException;
import com.aliyun.mns.model.Message;
import com.zb.p2p.paychannel.common.util.ExecutorsFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;

/**
 * 功能: MNS消息接收线程
 * 创建: liuchongguang - liuchongguang@zillionfortune.com
 * 日期: 2017/6/26 0026 16:07
 * 版本: V1.0
 */
public class MnsMsgHandlerThread extends Thread {

    /**
     * 日志器
     */
    private static Logger logger = LoggerFactory.getLogger(MnsMsgHandlerThread.class);

    /**
     * 阿里云授权信息
     */
    private CloudAccount cloudAccount;

    /**
     * 无消息时等待时间,默认5秒
     */
    private int waitSeconds = 5;

    /**
     * 监听队列名称
     */
    private String queueName;

    /**
     * 消息监听处理器
     */
    private MnsMsgHandler mnsTopicListener;

    /**
     * 是否运行
     */
    private boolean runnable = true;

    /**
     * 是否弱消费
     */
    private boolean weakConsumer = false;

    private ExecutorService es = ExecutorsFactory.getExecutorService("mqHandler");

    public MnsMsgHandlerThread() {

    }

    public MnsMsgHandlerThread(boolean weakConsumer, CloudAccount cloudAccount, int waitSeconds,
                               String queueName, MnsMsgHandler mnsTopicListener) {
        this.weakConsumer = weakConsumer;
        this.cloudAccount = cloudAccount;
        this.waitSeconds = waitSeconds;
        this.queueName = queueName;
        this.mnsTopicListener = mnsTopicListener;
    }

    @Override
    public void run() {
        logger.debug("正在监听:" + this.queueName);
        try {
            MNSClient client = cloudAccount.getMNSClient();
            final CloudQueue queue = client.getQueueRef(this.queueName);
            while (this.runnable) {
                try {
                    final Message popMsg = queue.popMessage(this.waitSeconds);
                    //让消息并行处理
                    es.execute(new Runnable() {
                        @Override
                        public void run() {
                            if (popMsg != null) {
                                try {
                                    //出队列超过5次报警
                                    if (popMsg.getDequeueCount() >= 5) {
                                        logger.warn("注意：出队列超过5次还没消费成功,msg={}", popMsg);
                                    }
                                    String jsonStr = popMsg.getMessageBodyAsString();
                                    logger.info("队列 {} 接收消息 {} ", queueName, jsonStr);
                                    if (weakConsumer) {
                                        try {
                                            mnsTopicListener.consume(jsonStr);
                                        } finally {
                                            queue.deleteMessage(popMsg.getReceiptHandle());
                                        }
                                    } else {
                                        if (mnsTopicListener.consume(jsonStr) == Action.CommitMessage) {
                                            int i = 5; //retry times
                                            do {
                                                try {
                                                    queue.deleteMessage(popMsg.getReceiptHandle());
                                                    break;
                                                } catch (Exception ne) {
                                                    logger.error("删除队列消息失败", ne);
                                                    Thread.sleep(300);
                                                }
                                            } while (i-- > 0);
                                        }
                                    }
                                } catch (Exception e) {
                                    logger.error("", e);
                                }
                            }
                        }
                    });

                } catch (ClientException ce) {
                    logger.error("客户端异常,请检查网络设置和DNS有效性", ce);
                    Thread.sleep(this.waitSeconds * 1000L);
                } catch (ServiceException se) {
                    logger.error("服务端异常,requestId:" + se.getRequestId(), se);
                    if (se.getErrorCode() != null) {
                        if (se.getErrorCode().equals("QueueNotExist")) {
                            logger.error(this.queueName + "队列不存在");
                        } else if (se.getErrorCode().equals("TimeExpired")) {
                            logger.error("请求过期,请检测本机服务器时间设置");
                        }
                    }
                    Thread.sleep(this.waitSeconds * 1000L);
                } catch (Exception ne) {
                    logger.error("阿里云队列消费者出现异常", ne);
                    Thread.sleep(this.waitSeconds * 1000L);
                }
            }
            logger.info("已停止:" + this.queueName);
        } catch (Exception e) {
            logger.error("连接阿里云MNS服务异常", e);
        }
    }

    public void shutdown() {
        logger.info("尝试停止" + this.queueName + "队列消费者");
        if (this.runnable) {
            this.runnable = false;
            //防止出现memory leak
            try {
                Thread.sleep(this.waitSeconds * 1000L);
            } catch (InterruptedException e) {
                logger.error("Interrupted thread", e);
                Thread.currentThread().interrupt();
            }
        } else {
            logger.info("已停止:" + this.queueName);
        }
    }

    public CloudAccount getCloudAccount() {
        return cloudAccount;
    }

    public void setCloudAccount(CloudAccount cloudAccount) {
        this.cloudAccount = cloudAccount;
    }

    public int getWaitSeconds() {
        return waitSeconds;
    }

    public void setWaitSeconds(int waitSeconds) {
        this.waitSeconds = waitSeconds;
    }

    public String getQueueName() {
        return queueName;
    }

    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }

    public MnsMsgHandler getMnsTopicListener() {
        return mnsTopicListener;
    }

    public void setMnsTopicListener(MnsMsgHandler mnsTopicListener) {
        this.mnsTopicListener = mnsTopicListener;
    }

    public boolean isRunnable() {
        return runnable;
    }

    public void setRunnable(boolean runnable) {
        this.runnable = runnable;
    }
}
