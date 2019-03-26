package com.zb.p2p.trade.service.common;


import com.zb.p2p.trade.common.enums.SequenceEnum;

/**
 * 分布式序列号生成服务
 * Created by limingxin on 2017/9/1.
 */
public interface DistributedSerialNoService {
    /**
     * 自增生成序列号
     *
     * @param identify 序列号标识
     * @param channel      渠道
     * @param offerSet 自增值
     * @return
     */
    String generatorSerialNoByIncrement(SequenceEnum identify, String channel, int offerSet);
    
    String generatorSerialNoByIncrement(SequenceEnum identify) ;

    long getLongSeqNo(SequenceEnum identify, String channel);
    
    
}
