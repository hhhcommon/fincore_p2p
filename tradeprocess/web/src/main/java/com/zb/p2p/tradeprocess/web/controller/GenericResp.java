package com.zb.p2p.tradeprocess.web.controller;

import com.zb.p2p.facade.api.resp.CommonResp;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by limingxin on 2017/9/19.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GenericResp<T> {
    private String msg;
    private String code;
    private T data;

    public static GenericResp convert(CommonResp commonResp) {
        return GenericResp.builder().code(commonResp.getCode()).msg(commonResp.getMessage()).data(commonResp.getData()).build();
    }
}
