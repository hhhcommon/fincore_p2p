package com.zb.p2p.paychannel.service.commonservice;


import com.zb.p2p.paychannel.common.enums.SequenceEnum;

/**
 * 分布式序列号生成服务
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
    
    public String generatorSerialNoByIncrement(SequenceEnum identify) ;
    
    
}
