package com.zb.fincore.pms.service;

import com.zb.fincore.common.topic.OnsTopicProducer;
import com.zb.fincore.pms.common.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 消息生产者
 *
 * @author mabiao
 * @create 2017-04-19 10:11
 */
@Service
public class ProducerService {

    /**
     * 日志记录器
     */
    private static Logger logger = LoggerFactory.getLogger(ProducerService.class);

//    @Autowired
//    private OnsTopicProducer clusterProducer;

//    /**
//     * 发布消息
//     */
//    public void publishMessage(Object message,String topic) throws Exception{
//        if(!clusterProducer.publishMessage(topic,null,message)){
//            throw new BusinessException("9999","发送主题消息失败");
//        }
//    }

}
