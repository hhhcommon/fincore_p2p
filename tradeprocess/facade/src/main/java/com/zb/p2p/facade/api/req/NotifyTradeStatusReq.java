package com.zb.p2p.facade.api.req;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by limingxin on 2018/1/9.
 */
@Data
public class NotifyTradeStatusReq implements Serializable {

    private static final long serialVersionUID = 3505573632287937001L;

    /**
     * originalOrderNo	原绑卡订单号	String	是
     */
    private String orderNo;

    /**
     * 会员id
     */
    private String memberId;
    /**
     * busiType	业务类型	String	是
     * 10-预约投资|20-放款|30-还款|40-退款|50-兑付
     * 70-补账，
     * 80-对公充值
     * 90-提现
     */
    private String busiType;

    /**
     * tradeType:busiType=10，tradeType=null
     * busiType=20，tradeType=02-企业借款人放款代付
     * busiType=20，tradeType=null-放款代付重试
     * busiType=30，tradeType=08-还款转账
     * busiType=30，tradeType=09-手续费转账
     * busiType=40，tradeType=01-为匹配资产退款
     * busiType=40，tradeType=null-退款重试
     * busiType=50，tradeType=02-企业借款正常兑付
     * busiType=50，tradeType=03-企业借款逾期兑付
     * busiType=50，tradeType=null-兑付重试
     * busiType=70，tradeType=null-补账
     * busiType=80，tradeType=02-网银充值
     * busiType=90，tradeType=02-对公代付.
     */
    private String tradeType;

    /**
     * 系统标识
     */
    private String sourceId;

    /**
     * 支付状态：F失败；S成功，支付code，支付msg
     */
    private String tradeStatus, tradeCode, tradeMsg;

}
