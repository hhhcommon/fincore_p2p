package com.zb.txs.p2p.order.state;

import com.zb.txs.p2p.order.persistence.model.Appointment;
import com.zb.txs.p2p.order.service.OrderService;
import com.zb.txs.p2p.order.state.entity.StateEntity;
import com.zb.txs.p2p.order.state.entity.StateMessage;
import com.zb.txs.p2p.order.state.enums.Events;
import com.zb.txs.p2p.order.state.enums.States;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

/**
 * Function:   状态机配置 <br/>
 * Date:   2017年09月24日 下午3:45 <br/>
 *
 * @author liguoliang@zillionfortune.com
 */

@Configuration
@EnableStateMachine
@Scope("prototype")
@Slf4j
public class StateMachineConfig extends EnumStateMachineConfigurerAdapter<States, Events> {
    @Autowired
    private OrderService orderService;

    @Override
    public void configure(StateMachineStateConfigurer<States, Events> states) throws Exception {
        states
                .withStates()
                .initial(States.INIT_APPOINT)
                .state(States.INIT_APPOINT)
                .and()
                .withStates()
                .parent(States.INIT_APPOINT)
                .initial(States.PENDING_APPOINT_PAYMENT)
                .state(States.PENDING_APPOINT_PAYMENT, action())
                .and()
                .withStates()
                .parent(States.PENDING_APPOINT_PAYMENT)
                .initial(States.PENDING_CONFIRM_APPOINT)
                .state(States.PENDING_CONFIRM_APPOINT, action())
                .and()
                .withStates()
                .parent(States.PENDING_CONFIRM_APPOINT)
                .state(States.APPOINT_CONFIRM_FAILURE, action())
                .and()
                .withStates()
                .parent(States.PENDING_CONFIRM_APPOINT)
                .initial(States.MATCHING)
                .state(States.MATCHING, action())
                .and()
                .withStates()
                .parent(States.MATCHING)
                .initial(States.DEAL_DONE)
                .state(States.DEAL_DONE, action())
                .and()
                .withStates()
                .parent(States.PENDING_APPOINT_PAYMENT)
                .state(States.APPOINT_PAY_PROCESSING, action())
                .and()
                .withStates()
                .parent(States.PENDING_APPOINT_PAYMENT)
                .state(States.APPOINT_CONFIRM_FAILURE, action());
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<States, Events> transitions) throws Exception {
        transitions
                .withExternal()
                .source(States.INIT_APPOINT).target(States.PENDING_APPOINT_PAYMENT)
                .event(Events.INIT_APPOINT_EVENT)
                .and()
                .withExternal()
                .source(States.PENDING_APPOINT_PAYMENT).target(States.PENDING_CONFIRM_APPOINT)
                .event(Events.MEMBER_APPOINT_PAY_SUCCESS)
                .and()
                .withExternal()
                .source(States.PENDING_APPOINT_PAYMENT).target(States.APPOINT_PAY_FAILURE)
                .event(Events.MEMBER_APPOINT_PAY_FAILURE)
                .and()
                .withExternal()
                .source(States.PENDING_APPOINT_PAYMENT).target(States.APPOINT_PAY_PROCESSING)
                .event(Events.MEMBER_APPOINT_PAY_PROCESSING)
                .and()
                .withExternal()
                .source(States.APPOINT_PAY_PROCESSING).target(States.PENDING_CONFIRM_APPOINT)
                .event(Events.MEMBER_APPOINT_PAY_SUCCESS)
                .and()
                .withExternal()
                .source(States.PENDING_CONFIRM_APPOINT).target(States.MATCHING)
                .event(Events.CONFIRM_APPOINT)
                .and()
                .withExternal()
                .source(States.PENDING_CONFIRM_APPOINT).target(States.APPOINT_CONFIRM_FAILURE)
                .event(Events.CONFIRM_APPOINT_FAILURE)
                .and()
                .withExternal()
                .source(States.APPOINT_CONFIRM_FAILURE).target(States.MATCHING)
                .event(Events.CONFIRM_APPOINT_RECOVER)
                .and()
                .withInternal()
                .source(States.MATCHING)
                .event(Events.MATCH_NOTIFY)
                .and()
                .withExternal()
                .source(States.MATCHING).target(States.DEAL_DONE)
                .event(Events.ORDER_SUCCESS);
    }

    @Override
    public void configure(StateMachineConfigurationConfigurer<States, Events> config)
            throws Exception {
        config
                .withConfiguration()
                .autoStartup(true);
    }


    @Bean
    public Action<States, Events> action() {
        return new Action<States, Events>() {
            @Override
            public void execute(StateContext<States, Events> context) {
                StateMessage<Events> message = (StateMessage<Events>) context.getMessage();
                Events event = message.getPayload();
                StateEntity<Appointment> entity = message.getEntity();
                Appointment a = entity.getE();
                States sourceState = context.getSource().getId();
                States targetState = context.getTarget().getId();

                //生成预约单
//                if (ObjectUtils.equals(event, Events.INIT_APPOINT_EVENT) && ObjectUtils.equals(sourceState, States.INIT_APPOINT)) {
//                    a.setStatus(States.PENDING_APPOINT_PAYMENT);
//                    orderService.insertAppointment(a);
//                } else if (ObjectUtils.equals(event, Events.MEMBER_APPOINT_PAY_SUCCESS) && ObjectUtils.equals(sourceState, States.PENDING_APPOINT_PAYMENT)) {
//                    //支付成功后，状态改为"待确认预约"
//                    a.setStatus(States.PENDING_CONFIRM_APPOINT);
//                    appointmentMapper.updateWhenPay(a);
//                } else if (ObjectUtils.equals(event, Events.MEMBER_APPOINT_PAY_PROCESSING) && ObjectUtils.equals(sourceState, States.PENDING_APPOINT_PAYMENT)) {
//                    //支付处理中，状态改为"预约支付处理中"
//                    orderService.updateAppointmentState(a.getId(), States.APPOINT_PAY_PROCESSING);
//                    a.setStatus(States.APPOINT_PAY_PROCESSING);
//                } else if (ObjectUtils.equals(event, Events.MEMBER_APPOINT_PAY_FAILURE) && ObjectUtils.equals(sourceState, States.PENDING_APPOINT_PAYMENT)) {
//                    //支付失败后，状态改为"预约支付失败"
//                    orderService.updateAppointmentState(a.getId(), States.APPOINT_PAY_FAILURE);
//                    a.setStatus(States.APPOINT_PAY_FAILURE);
//                } else if (ObjectUtils.equals(event, Events.CONFIRM_APPOINT) && ObjectUtils.equals(sourceState, States.PENDING_CONFIRM_APPOINT)) {
//                    //金核确认预约，状态改为"匹配中"
//                    a.setStatus(States.MATCHING);
//                    appointmentMapper.updateWhenConfirmSuccess(a);
//                } else if (ObjectUtils.equals(event, Events.CONFIRM_APPOINT_FAILURE) && ObjectUtils.equals(sourceState, States.PENDING_CONFIRM_APPOINT)) {
//                    //金核确认预约失败，状态改为"预约确认失败"
//                    orderService.updateAppointmentState(a.getId(), States.APPOINT_CONFIRM_FAILURE);
//                    a.setStatus(States.APPOINT_CONFIRM_FAILURE);
//                }
            }
        };
    }
}