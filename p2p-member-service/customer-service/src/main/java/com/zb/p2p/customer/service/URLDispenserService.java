package com.zb.p2p.customer.service;


import com.zb.p2p.customer.api.entity.*;
import com.zb.p2p.customer.common.model.BaseRes;

public interface URLDispenserService {

    URLDispenserResp urlDispenser(URLDispenserRequest request);

    BaseRes<CheckCodeRes> checkCode(CheckCodeRequest req);

    AuthregisterRes authregister(AuthregisterReq req);
}
