package com.zb.fincore.pms.facade.line.dto.resp;

import com.zb.fincore.pms.common.dto.BaseResponse;

/**
 * 功能: 注册产品线响应对象
 * 创建: liuchongguang - liuchongguang@zillionfortune.com
 * 日期: 2017/3/28 0028 15:27
 * 版本: V1.0
 */
public class RegisterProductLineResponse extends BaseResponse {

    /**
     * 产品线编码
     */
    private String lineCode;

    public String getLineCode() {
        return lineCode;
    }

    public void setLineCode(String lineCode) {
        this.lineCode = lineCode;
    }
}
