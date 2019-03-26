package com.zb.p2p.tradeprocess.web.controller;

import com.zb.p2p.cash.service.CashRecordService;
import com.zb.p2p.enums.CashStatusEnum;
import com.zb.p2p.enums.ResponseCodeEnum;
import com.zb.p2p.facade.api.req.CashRecordReq;
import com.zb.p2p.facade.api.resp.CommonResp;
import com.zb.p2p.service.common.DistributedLockService;
import com.zb.p2p.service.common.PaymentRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Function:兑付
 * Author: created by liguoliang
 * Date: 2017/9/1 0001 上午 9:41
 * Version: 1.0
 */
@RestController
@RequestMapping("/cash")
public class CashController {

    @Autowired
    private CashRecordService cashRecordService;
    @Autowired
    private PaymentRecordService paymentRecordService;
    @Autowired
    protected DistributedLockService distributedLockService;

    @RequestMapping(value = "/failList", method = RequestMethod.POST)
    public GenericResp failList(@RequestBody CashRecordReq cashRecordReq) {
        return GenericResp.convert(
                CommonResp.build(ResponseCodeEnum.RESPONSE_SUCCESS.getCode(), ResponseCodeEnum.RESPONSE_SUCCESS.getDesc(),
                        paymentRecordService.queryFailAll(cashRecordReq.getProductNo())));
    }

    @RequestMapping(value = "/exec", method = RequestMethod.POST)
    public GenericResp exec(@RequestBody CashRecordReq cashRecordReq) {
        String key = CashStatusEnum.CASHING.getCode() + cashRecordReq.getProductNo();
        try {
            distributedLockService.tryLock(key);
            if (cashRecordService.execCash(cashRecordReq.getProductNo()))
                return GenericResp.convert(CommonResp.build(ResponseCodeEnum.RESPONSE_SUCCESS.getCode(), "兑付指令处理中", "cashing"));
            else
                return GenericResp.convert(CommonResp.build(ResponseCodeEnum.RESPONSE_NOT_FUND.getCode(), "该产品下的资产都未还款，不能兑付", "noCash"));
        } catch (Exception e) {
            return GenericResp.convert(CommonResp.build(ResponseCodeEnum.RESPONSE_FAIL.getCode(), "兑付指令处理失败", "cashFailed"));
        } finally {
            try {
                distributedLockService.unLock(key);
            } catch (Exception e) {
            }
        }
    }

}
