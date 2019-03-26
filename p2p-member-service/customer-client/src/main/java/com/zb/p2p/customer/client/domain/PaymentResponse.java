/**
 *
 */
package com.zb.p2p.customer.client.domain;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

/**
 * @author guolitao
 */
@Data
public class PaymentResponse {

    private String code;
    private String msg;

    /**
     * 响应结果的内容
     */
    private JSONObject data;

    /**
     * 账务账号
     *
     * @return
     */
    public String getAccountNo() {
        return this.data == null ? null : this.data.getString("accountNo");
    }

    /**
     * 个人实名认证结果状态
     * @return
     */
    public String getValidateStatus(){
        return this.data == null ? null : this.data.getString("validateStatus");
    }
}
