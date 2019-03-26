package com.zb.p2p.trade.common.queue;

import com.zb.p2p.trade.common.constant.GlobalVar;
import com.zb.p2p.trade.common.exception.BusinessException;
import com.zb.p2p.trade.common.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import static com.zb.p2p.trade.common.constant.GlobalVar.LOG_TRACE_ID;

/**
 * 功能: MNS主题消息监听器
 * 创建: liuchongguang - liuchongguang@zillionfortune.com
 * 日期: 2017/6/26 0026 15:57
 * 版本: V1.0
 */
public abstract class AbstractMnsMsgHandler {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 消费消息接口，由应用来实现<br>
     *
     * @param message 消息
     * @return 消费结果，如果应用抛出异常或者返回Null等价于返回Action.ReconsumeLater
     */
    public Action consume(final String message) throws Exception {
        try {
            MDC.put(GlobalVar.LOG_TRACE_ID, StringUtils.getUUID());

            logger.info("APP ==> notify P2P-Trade, message:[{}]", message);

            handleMessage(message);

        } catch (IllegalArgumentException e) {
            logger.error("业务校验异常", e);
        } catch (BusinessException e) {
            logger.error("业务处理异常", e);
        } catch (Exception e) {
            logger.error("系统处理异常", e);
            return Action.ReconsumeLater;
        } finally {
            MDC.clear();
        }

        return Action.CommitMessage;
    }

    public abstract void handleMessage(String msg) throws Exception;
}
