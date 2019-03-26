/* 
* Copyright (c) 2017, 资邦金服(上海)网络科技有限公司. All Rights Reserved.
*
*/

package com.zb.txs.p2p.code;

import com.zb.txs.foundation.response.Result;

/**
 * Function:   状态码枚举 <br/>
 * Date:   2017年09月29日 下午5:05 <br/>
 *
 * @author liguoliang@zillionfortune.com
 */
public class CodeEnum {

    public static final Result.Code RESPONSE_SUCCESS = Result.Code.builder().code("0000").defaultMsg("响应成功").desc("响应成功").build();
    public static final Result.Code RESPONSE_FAIL = Result.Code.builder().code("9999").defaultMsg("响应失败").desc("响应失败").build();
    public static final Result.Code RESPONSE_REPEAT_ORDER = Result.Code.builder().code("B001").defaultMsg("重复下单").desc("重复下单").build();
    public static final Result.Code RESPONSE_NOT_FUND = Result.Code.builder().code("9000").defaultMsg("未找到信息").desc("未找到信息").build();
    public static final Result.Code RESPONSE_PARAM_FAIL = Result.Code.builder().code("9003").defaultMsg("参数校验失败").desc("参数校验失败").build();
    public static final Result.Code RESPONSE_PROCESSING = Result.Code.builder().code("3T05").defaultMsg("处理中").desc("处理中").build();
    public static final Result.Code PAY_FAIL = Result.Code.builder().code("4001").defaultMsg("下单支付失败").desc("下单支付失败").build();
    public static final Result.Code RESERVATION_FAIL = Result.Code.builder().code("4002").defaultMsg("下单登记失败").desc("下单登记失败").build();
    public static final Result.Code ORDER_SUCCESS = Result.Code.builder().code("200").defaultMsg("下单成功").desc("下单成功").build();
    public static final Result.Code STOCK_LESS = Result.Code.builder().code("402").defaultMsg("库存不足").desc("库存不足").build();
    public static final Result.Code MATCH_DUPLICATION = Result.Code.builder().code("8000").defaultMsg("重复的匹配记录 UNIQUE (ext_reservation_no, loan_order_no)").desc("重复的匹配记录 UNIQUE (ext_reservation_no, loan_order_no)").build();
    public static final Result.Code APPOINTMENT_UPDATE_FAIL = Result.Code.builder().code("8002").defaultMsg("update 预约表失败").desc("update 预约表失败").build();
    public static final Result.Code NO_MATCH_APPOINTMENT = Result.Code.builder().code("8001").defaultMsg("未在 预约表 中找到对应记录!").desc("未在 预约表 中找到对应记录!").build();
    public static final Result.Code APPOINTMENT_UPDATE_FAIL_AMOUNT_ERROR = Result.Code.builder().code("8003").defaultMsg("update 预约表失败!,已匹配金额 大于 预约总金额").desc("update 预约表失败!,已匹配金额 大于 预约总金额").build();
    public static final Result.Code NO_STATES_MATCHING = Result.Code.builder().code("8004").defaultMsg("<<预约表>>状态不是 匹配中(MATCHING)").desc("<<预约表>>状态不是 匹配中(MATCHING)").build();
    public static final Result.Code UPDATE_HOLDASSETSP2P_FAIL = Result.Code.builder().code("8005").defaultMsg("更新用户的持仓记录失败").desc("更新用户的持仓记录失败").build();
    public static final Result.Code NO_MATCH_HOLDASSETSP2P = Result.Code.builder().code("8006").defaultMsg("未在 持仓列表 中找到对应记录").desc("未在 持仓列表 中找到对应记录").build();
    public static final Result.Code HOLDASSETS_UPDATE_FAIL_AMOUNT_ERROR = Result.Code.builder().code("8007").defaultMsg("update 持仓列表 失败,已匹配金额 大于 预约总金额").desc("update 持仓列表 失败,已匹配金额 大于 预约总金额").build();
    public static final Result.Code UPDATE_PRODUCTBID_FAIL = Result.Code.builder().code("8008").defaultMsg("调用 <<交易处理系统(产品投资申请)>>后! 更新状态为 已投资 失败").desc("调用 <<交易处理系统(产品投资申请)>>后! 更新状态为 已投资 失败").build();
    public static final Result.Code PRODUCTBID_UPDATE_FAIL = Result.Code.builder().code("8009").defaultMsg("update 持仓表 未找到匹配记录").desc("update 持仓表 未找到匹配记录").build();
    
    public static final Result.Code PAY_SYSTEM_LOANTRANSFER_CODE_ERROR = Result.Code.builder().code("8500").defaultMsg("调用 支付系统(放款转账) 成功, 但 code 失败").desc("调用 支付系统(放款转账) 成功, 但 code 失败").build();
    public static final Result.Code PAY_SYSTEM_LOANTRANSFER_ERROR = Result.Code.builder().code("8501").defaultMsg("调用 支付系统(放款转账) 失败").desc("调用 支付系统(放款转账) 失败").build();
    
    public static final Result.Code TRANSACTION_SYSTEM_ORDERINVEST_CODE_ERROR = Result.Code.builder().code("8600").defaultMsg("调用 交易处理系统(产品投资申请) 成功, 但 code 失败").desc("调用 交易处理系统(产品投资申请) 成功, 但 code 失败").build();
    public static final Result.Code TRANSACTION_SYSTEM_ORDERINVEST_ERROR = Result.Code.builder().code("8601").defaultMsg("调用 交易处理系统(产品投资申请) 失败").desc("调用 交易处理系统(产品投资申请) 失败").build();


    static {
        Result.CodeManager.register(RESPONSE_SUCCESS);
        Result.CodeManager.register(RESPONSE_FAIL);
        Result.CodeManager.register(RESPONSE_REPEAT_ORDER);
        Result.CodeManager.register(RESPONSE_NOT_FUND);
        Result.CodeManager.register(RESPONSE_PARAM_FAIL);
        Result.CodeManager.register(RESPONSE_PROCESSING);
        Result.CodeManager.register(PAY_FAIL);
        Result.CodeManager.register(RESERVATION_FAIL);
        Result.CodeManager.register(STOCK_LESS);
        Result.CodeManager.register(ORDER_SUCCESS);
        Result.CodeManager.register(MATCH_DUPLICATION);
        Result.CodeManager.register(APPOINTMENT_UPDATE_FAIL);
        Result.CodeManager.register(NO_MATCH_APPOINTMENT);
        Result.CodeManager.register(APPOINTMENT_UPDATE_FAIL_AMOUNT_ERROR);
        Result.CodeManager.register(NO_STATES_MATCHING);
        Result.CodeManager.register(UPDATE_HOLDASSETSP2P_FAIL);
        Result.CodeManager.register(NO_MATCH_HOLDASSETSP2P);
        Result.CodeManager.register(HOLDASSETS_UPDATE_FAIL_AMOUNT_ERROR);
        Result.CodeManager.register(PAY_SYSTEM_LOANTRANSFER_CODE_ERROR);
        Result.CodeManager.register(PAY_SYSTEM_LOANTRANSFER_ERROR);
        Result.CodeManager.register(TRANSACTION_SYSTEM_ORDERINVEST_CODE_ERROR);
        Result.CodeManager.register(TRANSACTION_SYSTEM_ORDERINVEST_ERROR);
        Result.CodeManager.register(UPDATE_PRODUCTBID_FAIL);
        Result.CodeManager.register(PRODUCTBID_UPDATE_FAIL);
    }
}
