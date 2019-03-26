package com.zb.fincore.pms.facade.line.dto.req;

import com.zb.fincore.pms.common.dto.BaseRequest;
import org.hibernate.validator.constraints.NotBlank;

/**
 * 功能: 查询产品线详情请求对象
 * 创建: liuchongguang - liuchongguang@zillionfortune.com
 * 日期: 2017/3/29 0029 13:26
 * 版本: V1.0
 */
public class QueryProductLineRequest extends BaseRequest {

    /**
     * SerialVersionUID
     */
    private static final long serialVersionUID = 5876278051332515791L;

    /**
     * 产品线代码
     */
    @NotBlank(message = "产品线编码不能为空")
    private String lineCode;

    public String getLineCode() {
        return lineCode;
    }

    public void setLineCode(String lineCode) {
        this.lineCode = lineCode;
    }
}
