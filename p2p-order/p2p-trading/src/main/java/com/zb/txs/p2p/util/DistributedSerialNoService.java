package com.zb.txs.p2p.util;


import com.zb.txs.p2p.business.enums.SequenceEnum;

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

    long getLongSeqNo();

    public String generatorSerialNoByIncrement(SequenceEnum identify) ;
    
    
}
