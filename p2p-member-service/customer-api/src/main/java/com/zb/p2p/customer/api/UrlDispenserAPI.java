package com.zb.p2p.customer.api;


import com.zb.p2p.customer.api.entity.AuthregisterRes;
import com.zb.p2p.customer.api.entity.CheckCodeRes;
import com.zb.p2p.customer.api.entity.URLDispenserRequest;
import com.zb.p2p.customer.api.entity.URLDispenserResp;

import com.zb.p2p.customer.api.entity.AuthregisterReq;
import com.zb.p2p.customer.api.entity.CheckCodeRequest;
import com.zb.p2p.customer.common.model.BaseRes;

public interface UrlDispenserAPI {
    /**
     * 侨金所跳转分发器
     *
     * @param request
     * @return
     */


    BaseRes<URLDispenserResp> urlDispenser(URLDispenserRequest req);

    /**
     * 校验、code是否合法
     *
     * @param token
     * @return
     */

    BaseRes<CheckCodeRes> checkCode(CheckCodeRequest req);


    /**
     * 授权注册接口
     */

    BaseRes<AuthregisterRes> authregister(AuthregisterReq req);

	 /*//校验短信
	  public BaseRes<BindRes> bind(BindReq req);
	  
	  //发短信
	  public BaseRes<BindSMSSendResp> sendSms(BindSmsReq req);*/
}
