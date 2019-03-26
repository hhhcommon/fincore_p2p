package com.zb.txs.p2p.order.state;

import com.zb.txs.p2p.order.state.entity.StateEntity;
import com.zb.txs.p2p.order.state.entity.StateMessage;
import com.zb.txs.p2p.order.state.enums.Events;
import com.zb.txs.p2p.order.state.enums.States;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateMachine;
import org.springframework.stereotype.Component;

/**
 * Function:   状态机操作 <br/>
 * Date:   2017年09月24日 下午3:45 <br/>
 *
 * @author liguoliang@zillionfortune.com
 */

@Component("stateHandler")
public class StateHandler {
    @Autowired
    private ObjectFactory<StateMachine<States, Events>> stateMachineObjectFactory;

    public StateMachine<States, Events> getStateMachine() {
        return stateMachineObjectFactory.getObject();
    }

    public void sendEvent(Events event, StateEntity entity) {
        StateMachine<States, Events> stateMachine = getStateMachine();

        sendEvent(stateMachine, event, entity);
    }

    public void sendEvent(StateMachine<States, Events> stateMachine, Events event, StateEntity entity) {
        boolean result = stateMachine.sendEvent(new StateMessage<Events>(event, entity));

        if (!result) {
            throw new StateException("Not match with correct state flow, source state=[" + entity.getSourceState() + "], event=[" + event + "] to send failed");
        }
    }
}