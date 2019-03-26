package com.zb.p2p.customer.client.domain;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class SyncCorpInfoReq {

    private String lastId; //上次同步的最后一个会员Id

    @NotNull(message = "数据数量参数不能为空")
    @Min(value = 1, message = "数据数量参数不能小于1")
    private Integer pageSize; //同步多少个账户

}
