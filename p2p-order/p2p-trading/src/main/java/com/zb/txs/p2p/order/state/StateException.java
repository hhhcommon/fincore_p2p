package com.zb.txs.p2p.order.state;

/**
 * Function:   状态机异常 <br/>
 * Date:   2017年09月24日 下午3:45 <br/>
 *
 * @author liguoliang@zillionfortune.com
 */

public class StateException extends RuntimeException {
    private static final long serialVersionUID = 9220581907917573972L;

    public StateException() {
        super();
    }

    public StateException(String message) {
        super(message);
    }

    public StateException(String message, Throwable cause) {
        super(message, cause);
    }

    public StateException(Throwable cause) {
        super(cause);
    }
}