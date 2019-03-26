//package com.zb.p2p.cash.service.checker;
//
//import com.alibaba.druid.util.StringUtils;
//import com.zb.fincore.common.exception.CommonException;
//import com.zb.p2p.enums.SaleChannelEnum;
//import com.zb.p2p.facade.api.req.CashRecordReq;
//import org.springframework.stereotype.Component;
//
///**
// * Function:参数校验
// * Author: created by liguoliang
// * Date: 2017/9/1 0001 下午 2:22
// * Version: 1.0
// */
//@Component
//public class ParaCheck {
//
//    public static void checkQueryCash(CashRecordReq cashRecordReq) {
//        if (cashRecordReq == null) {
//            throw new CommonException("请求对象不能为空");
//        }
//        if (StringUtils.isEmpty(cashRecordReq.getSaleChannel())
//                || StringUtils.isEmpty(cashRecordReq.getProductCode())
//                || StringUtils.isEmpty(cashRecordReq.getMemberId())) {
//            throw new CommonException("必填参数不能为空");
//        }
//        if (!SaleChannelEnum.validateChannel(cashRecordReq.getSaleChannel())) {
//            throw new CommonException("渠道不合法");
//        }
//    }
//
//}
