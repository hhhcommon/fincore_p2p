package com.zb.p2p.trade.common.constant;

import com.zb.p2p.trade.common.enums.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

/**
 * Created by limingxin on 2017/10/19.
 */
public interface GlobalVar {

    // 日志Id
    String LOG_TRACE_ID = "traceId";
    // 合同模板文件存放地址
    String CONTRACT_TEMPLATE_FILE_PATH = "template";
    String LOAN_CONTRACT_FILE = "loanContract.html";

    //全局锁标识
    String GLOBAL_TRADE_LOCK_KEY = "tradeCoreLogicLock";
    String GLOBAL_LOANWITHDRAWAL_LOCK_KEY = "loanwithdrawalLock"; //放款
    String GLOBAL_DUBBOCALLBACK_LOCK_KEY = "dubboCallbackLock"; //dubbo回调
    String GLOBAL_CASH_AMOUNT_CALC_LOCK_KEY = "cashAmountCalcLock"; // 兑付收益计算
    String GLOBAL_INCOME_CALC_LOCK_KEY = "incomecalcLock";//每日收益计算任务
    String ASSET_MATCH_COMPLETE_NOTIFY_LOCK = "assetMatchCompleteNotifyLock";//匹配后通知

    String SYS_IDENTIFY_CODE = "TY";//天鼋
    /** 交易结果定义 */
    String PAYMENT_TRADE_CODE_SUCCESS = "0000";

    String PAYMENT_TRADE_STATUS_SUCCESS = "S";
    String PAYMENT_TRADE_STATUS_PROCESSING = "P";
    String PAYMENT_TRADE_STATUS_FAILED = "F";

    /** 缓存常量标识定义 */
    Integer INCOME_REDIS_CACHE_LIVE_TIME = 360;

    //短信标识
    String MESSAGE_SOURCECODE = "p2pTradeprocess";
    String LOAN_WITHDRAW_MESSAGE_TEMPLATE_CODE = "sms_loanNotify";//放款短信模版code
    String REPAYMENT_NOTICE_MESSAGE_TEMPLATE_CODE = "sms_repaymentNotify";//还款短信模版code

    // 金额计算常量
    BigDecimal YEAR_DAYS_365 = new BigDecimal(365);

    // 匹配成功状态
    List<String> LOAN_SUCCESS_STATUS = Arrays.asList(new String[]{LoanStatusEnum.PARTLY_LOAN_SUCCESS.getCode()
            , LoanStatusEnum.LOAN_SUCCESS.getCode()});

    // 债权成立状态
    List<String> CREDITOR_SUCCESS_STATUS = Arrays.asList(new String[]{CreditorStatusEnum.INIT.getCode()
            , CreditorStatusEnum.WAIT_CASH.getCode()});

    // 还款计划初始状态
    List<String> REPAY_BILL_INIT_STATUS = Arrays.asList(new String[]{BillStatusEnum.INIT.getCode()});

    // 放款成功状态
    List<String> LOAN_PAYMENT_SUCCESS_STATUS = Arrays.asList(new String[]{LoanPaymentStatusEnum.LOAN_PAYMENT_SUCCESS.getCode() ,
            LoanPaymentStatusEnum.LOAN_CARD_SUCCESS.getCode()});

    // 放款支付异步回调地址
    String PAY_CALL_BACK_URL = "/p2p-trade/callBack/payCallBack";
}
