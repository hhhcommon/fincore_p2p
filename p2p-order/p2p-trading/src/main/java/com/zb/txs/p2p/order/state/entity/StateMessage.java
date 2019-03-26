package com.zb.txs.p2p.order.state.entity;

/**
 * Function:   状态通知 <br/>
 * Date:   2017年09月24日 下午3:45 <br/>
 *
 * @author liguoliang@zillionfortune.com
 */

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.springframework.messaging.support.GenericMessage;

public class StateMessage<T> extends GenericMessage<T> {
    private static final long serialVersionUID = 3822349578970192942L;

    private StateEntity entity;

    public StateMessage(T payload, StateEntity entity) {
        super(payload);

        this.entity = entity;
    }

    public StateEntity getEntity() {
        return entity;
    }

    @Override
    public boolean equals(Object object) {
        return EqualsBuilder.reflectionEquals(this, object);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}