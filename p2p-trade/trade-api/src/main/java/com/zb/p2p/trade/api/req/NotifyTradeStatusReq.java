package com.zb.p2p.trade.api.req;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Created by limingxin on 2018/1/9.
 */
@Data
public class NotifyTradeStatusReq implements Serializable {

    private static final long serialVersionUID = 3505573632287937001L;

    /**
     *  调用支付的交易流水号
     */
    @NotNull(message = "交易流水号不能为空")
    @Size(min = 1, message = "交易流水号不能为空")
    private String orderNo;

    /**
     *  支付方返回的支付流水号
     */
    private String notifyNo;

    /**
     * 通知时间
     */
    @NotNull(message = "通知时间不能为空")
    @Size(min = 1, message = "通知时间不能为空")
    private String notifyTime;

    /**
     * 业务类型 枚举
     * 01：放款，02：还款，03：兑付，04：手续费划账
     */
    @NotNull(message = "业务类型不能为空")
    @Size(min = 1, message = "业务类型不能为空")
    private String businessType;

    /**
     * 支付结果状态：F失败，S成功；支付msg
     */
    @NotNull(message = "支付结果状态不能为空")
    private String status;

    private String errCode;

    private String errMsg;

}
