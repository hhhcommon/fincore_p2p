package com.zb.p2p.customer.dao.domain;

import lombok.Data;

import java.util.Date;

@Data
public class LoginDevice {
    private Long id;

    private String customerId;

    private String serialNo;

    private String name;

    private String model;

    private Date createTime;

    private Date modifyTime;

    private String createBy;

    private String modifyBy;

}