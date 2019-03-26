package com.zb.p2p.customer.client.domain;/**
 * Created by zhengwenquan on 2018/3/9.
 */

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

/**
 * <p> 描述 </p>
 *
 * @author Vinson
 * @version CompanyAuthentRes.java v1.0 2018/3/9 17:52 Zhengwenquan Exp $
 */
@Data
public class CompanyAuthentRes {

    /**
     * 结果编码
     */
    private String resultCode;

    /**
     * 结果描述
     */
    private String resultMsg;

    /**
     * 响应结果的内容
     */
    private JSONObject data;

    /**
     * 认证链接（第三方公司）
     * @return
     */
    public String getAuthenUrl() {
        return this.data == null ? null : this.data.getString("authentUrl");
    }

    /**
     * 认证结果查询链接（第三方公司）
     * @return
     */
    public String getAuthentResultUrl(){
        return this.data == null ? null : this.data.getString("authentResultUrl");
    }
}
