package com.zb.txs.p2p.order.state.enums;

/**
 * Function:   事件枚举 <br/>
 * Date:   2017年09月24日 下午3:45 <br/>
 *
 * @author liguoliang@zillionfortune.com
 */


public enum Events {
    //初始化事件
    INIT_APPOINT_EVENT,
    //会员预约支付成功
    MEMBER_APPOINT_PAY_SUCCESS,
    //会员预约支付失败
    MEMBER_APPOINT_PAY_FAILURE,
    //会员预约支付处理中
    MEMBER_APPOINT_PAY_PROCESSING,
    //确认预约
    CONFIRM_APPOINT,
    //确认预约失败
    CONFIRM_APPOINT_FAILURE,
    //确认预约恢复
    CONFIRM_APPOINT_RECOVER,
    //匹配通知
    MATCH_NOTIFY,
    //下单成功
    ORDER_SUCCESS
}