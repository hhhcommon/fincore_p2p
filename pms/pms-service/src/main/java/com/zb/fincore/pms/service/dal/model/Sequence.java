package com.zb.fincore.pms.service.dal.model;

import com.zb.fincore.pms.common.model.BaseDo;

/**
 * 功能: 序列数据持久对象
 * 创建: liuchongguang - liuchongguang@zillionfortune.com
 * 日期: 2017/3/29 0029 09:36
 * 版本: V1.0
 */
public class Sequence extends BaseDo {

    /**
     * 序列名称
     */
    private String sequenceName;

    /**
     * 序列当前值
     */
    private Long currentVal;

    /**
     * 序列步长
     */
    private Integer sequenceStep;

    private Long version;

    public String getSequenceName() {
        return sequenceName;
    }

    public void setSequenceName(String sequenceName) {
        this.sequenceName = sequenceName == null ? null : sequenceName.trim();
    }

    public Long getCurrentVal() {
        return currentVal;
    }

    public void setCurrentVal(Long currentVal) {
        this.currentVal = currentVal;
    }

    public Integer getSequenceStep() {
        return sequenceStep;
    }

    public void setSequenceStep(Integer sequenceStep) {
        this.sequenceStep = sequenceStep;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
}