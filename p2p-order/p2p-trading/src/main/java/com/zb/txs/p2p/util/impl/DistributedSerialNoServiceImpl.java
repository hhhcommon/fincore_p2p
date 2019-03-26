package com.zb.txs.p2p.util.impl;

import com.zb.txs.p2p.business.enums.P2PSourceIdEnum;
import com.zb.txs.p2p.business.enums.SequenceEnum;
import com.zb.txs.p2p.util.DistributedSerialNoService;
import com.zb.txs.p2p.util.ZKIncreaseSequenceHandler;
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

    public long getLongSeqNo() {
        String seqStr = sequenceHandler.getBusinessCode(SequenceEnum.ORDER, P2PSourceIdEnum.ZD.name());
        return Long.parseLong(seqStr.substring(7));
    }
    
    public String generatorSerialNoByIncrement(SequenceEnum identify) {
        return sequenceHandler.getBusinessCode(identify);
    }
}
