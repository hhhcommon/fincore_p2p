package com.zb.txs.p2p.util;


import com.zb.fincore.common.utils.DateUtils;
import com.zb.txs.p2p.business.enums.SequenceEnum;

/**
 * Created by wangwanbin on 2017/9/5.
 */
public abstract class SequenceHandler {

    /**
     * 获取sequence
     * @param sequenceEnum
     * @return
     */
    public abstract long nextId(SequenceEnum sequenceEnum);


    /**
     * 生成业务编码
     *
     * @param sequenceEnum
     * @return
     */
    public String getBusinessCode(SequenceEnum sequenceEnum, String channel) {
        String businessCode = null;
        String currentDate = DateUtils.format(DateUtils.now(), DateUtils.ONLY_DATA_FORMAT);
        //序列名称,前缀加年月 AMA + 1701
        long sequence = this.nextId(sequenceEnum);
        StringBuffer sb = new StringBuffer();
        sb.append(channel).append(sequenceEnum.getCode()).append(currentDate).append(String.format("%09d", sequence));
        businessCode = sb.toString();
        return businessCode;
    }
    
    public String getBusinessCode(SequenceEnum sequenceEnum ) {
        String businessCode = null;
        long sequence = this.nextId(sequenceEnum);
        StringBuffer sb = new StringBuffer();
        sb.append(sequenceEnum.getCode()).append( sequence);
        businessCode = sb.toString();
        return businessCode;
    }
}
