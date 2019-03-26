package com.zb.p2p.paychannel.service.commonservice.impl;

import com.zb.p2p.paychannel.common.enums.SequenceEnum;
import com.zb.p2p.paychannel.common.util.ZKIncreaseSequenceHandler;
import com.zb.p2p.paychannel.service.commonservice.DistributedSerialNoService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * Created by limingxin on 2017/9/1.
 */
@Service
public class DistributedSerialNoServiceImpl implements DistributedSerialNoService {

    private ZKIncreaseSequenceHandler sequenceHandler;
    @Value("${zkid.zkAddress}")
    private String zkAddress;
    @Value("${zkid.path}")
    private String path;
    @Value("${zkid.seq}")
    private String seq;

    @PostConstruct
    public void init() {
        sequenceHandler = ZKIncreaseSequenceHandler.getInstance(zkAddress, path, seq);
    }


    @Override
    public String generatorSerialNoByIncrement(SequenceEnum identify, String channel, int offerSet) {
        return sequenceHandler.getBusinessCode(identify, channel);
    }
    
    public String generatorSerialNoByIncrement(SequenceEnum identify) {
        return sequenceHandler.getBusinessCode(identify);
    }
}
