package com.zb.p2p.trade.service.common.impl;

import com.zb.p2p.trade.common.enums.SequenceEnum;
import com.zb.p2p.trade.common.util.ZKIncreaseSequenceHandler;
import com.zb.p2p.trade.service.common.DistributedSerialNoService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * Created by limingxin on 2017/9/1.
 */
@Service
public class DistributedSerialNoServiceImpl implements DistributedSerialNoService {

    private ZKIncreaseSequenceHandler sequenceHandler;
    @Value("${id.genrenate.zkAddress}")
    private String zkAddress;
    @Value("${id.genrenate.path}")
    private String path;
    @Value("${id.genrenate.seq}")
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

    public long getLongSeqNo(SequenceEnum identify, String channel) {
        String seqStr = sequenceHandler.getBusinessCode(identify, channel);
        return Long.parseLong(seqStr.substring(7));
    }
}
