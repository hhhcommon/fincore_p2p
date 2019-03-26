package com.zillionfortune.boss.common.constants;

/**
 *
 */
public class Constants {

    /**
     * sequence name 产品前缀
     */
    public static final String SEQUENCE_NAME_PREFIX_PRODUCT = "P";

    /**
     * sequence name 产品线前缀
     */
    public static final String SEQUENCE_NAME_PREFIX_PRODUCT_LINE = "PL";

    public static final String CONSTANT_VALUE_1 = "1";

    public static final String PRODUCT_STOCK_CHANGE_LOCK_PREFIX = "PRODUCT_STOCK_CHANGE_";

    public static final String PRODUCT_STOCK_PREFIX = "PRODUCT_STOCK:";

    public static final String PRODUCT_APPROVAL_SIGN_PROP_NAME = "approval_require_sign";

    /**
     * ************************************** 通用状态 start ************************************
     */
    public static final String SUCCESS_RESP_CODE = "0000";
    public static final String SUCCESS_RESP_DESC = "成功";

    public static final String FAIL_RESP_CODE = "9999";
    public static final String FAIL_RESP_DESC = "处理失败";

    public static final String PARAM_NOBLANK_CODE = "9001";
    public static final String PARAM_NOBLANK_DESC = "必填参数不能为空";

    public static final String PARAM_RESULTBLANK_CODE = "9998";
    public static final String PARAM_RESULTBLANK_DESC = "查询结果为空";

    /*******************************************
     * 通用状态 end
     ***************************************/

    public static final String PRODUCT_RISK_LEVEL_NOT_IN_ENUMS_RETURN_CODE = "9002";
    public static final String PRODUCT_RISK_LEVEL_NOT_IN_ENUMS_RETURN_DESC = "风险等级不在约定的范围";

    public static final String PRODUCT_PATTERN_CODE_NOT_IN_ENUMS_RETURN_CODE = "9003";
    public static final String PRODUCT_PATTERN_CODE_NOT_IN_ENUMS_RETURN_DESC = "形态代码参数值不在约定的范围";

    public static final String PRODUCT_LINE_EXIST_RETURN_CODE = "9004";
    public static final String PRODUCT_LINE_EXIST_RETURN_DESC = "产品线信息已存在";

    public static final String PRODUCT_NOT_EXIST_RETURN_CODE = "9005";
    public static final String PRODUCT_NOT_EXIST_RETURN_DESC = "没有查询到对应产品";

    public static final String PRODUCT_DISABLE_EDIT_RETURN_CODE = "9006";
    public static final String PRODUCT_DISABLE_EDIT_RETURN_DESC = "已上线或归档产品，信息不可修改";

    public static final String PRODUCT_INVESTED_RETURN_CODE = "9007";
    public static final String PRODUCT_INVESTED_RETURN_DESC = "产品已有投资人，信息不可修改";

    public static final String PRODUCT_PRODUCT_UNIT_CODE = "9008";
    public static final String PRODUCT_PRODUCT_UNIT_CODE_DESC = "产品计量单位参数值不在约定的范围";

    public static final String PRODUCT_BASIC_INTERESTS_PERIOD_CODE = "9009";
    public static final String PRODUCT_BASIC_INTERESTS_PERIOD_CODE_DESC = "年基础计息周期参数值不在约定的范围";

    public static final String PRODUCT_EXPECT_ONLINE_TIME_AFTER_SALE_END_DATE_CODE = "9010";
    public static final String PRODUCT_EXPECT_ONLINE_TIME_AFTER_SALE_END_DATE_CODE_DESC = "产品预期上线时间不能晚于募集截止时间";

    public static final String PRODUCT_SALE_START_DATE_AFTER_OR_EQUAL_SALE_END_DATE_CODE = "9011";
    public static final String PRODUCT_SALE_START_DATE_AFTER_OR_EQUAL_SALE_END_DATE_CODE_DESC = "产品募集起始时间必须小于等于募集截止时间";

    public static final String PRODUCT_SALE_END_DATE_AFTER_ESTABLISH_DATE_CODE = "9012";
    public static final String PRODUCT_SALE_END_DATE_AFTER_ESTABLISH_DATE_CODE_DESC = "产品募集截止时间不能晚于预期成立时间";

    public static final String PRODUCT_ESTABLISH_DATE_AFTER_VALUE_DATE_CODE = "9013";
    public static final String PRODUCT_ESTABLISH_DATE_AFTER_VALUE_DATE_CODE_DESC = "预期成立时间不能晚于起息时间";

    public static final String PRODUCT_VALUE_DATE_AFTER_EXPIRE_DATE_CODE = "9014";
    public static final String PRODUCT_VALUE_DATE_AFTER_EXPIRE_DATE_CODE_DESC = "产品起息时间必须小于预期到期日期";

    public static final String PRODUCT_EXPIRE_DATE_AFTER_EXPECT_CLEAR_PAYMENT_DATE_CODE = "9015";
    public static final String PRODUCT_EXPIRE_DATE_AFTER_EXPECT_CLEAR_PAYMENT_DATE_CODE_DESC = "产品预期到期日期必须小于产品预期结清日期";

    public static final String PRODUCT_EXIST_RESULT_CODE = "9016";
    public static final String PRODUCT_EXIST_RESULT_CODE_DESC = "相同编号或名称的产品信息已存在";

    public static final String PRODUCT_CHANNEL_CODE_NOT_IN_ENUMS_RESULT_CODE = "9017";
    public static final String PRODUCT_CHANNEL_CODE_NOT_IN_ENUMS_RESULT_DESC = "渠道编号不在约定的范围";

    public static final String PRODUCT_COLLECT_STATUS_CHANGED_RESULT_CODE = "9018";
    public static final String PRODUCT_COLLECT_STATUS_CHANGED_RESULT_CODE_DESC = "产品状态已变更，请刷新列表后重试";

    public static final String PRODUCT_UNKNOWN_COLLECT_STATUS_CHANGED_RESULT_CODE = "9019";
    public static final String PRODUCT_UNKNOWN_COLLECT_STATUS_CHANGED_RESULT_CODE_DESC = "未知的产品状态变更流程";

    public static final String PRODUCT_LINE_ALREADY_APPROVAL_RESULT_CODE = "9020";
    public static final String PRODUCT_LINE_ALREADY_APPROVAL_RESULT_CODE_DESC = "产品线已审核通过";

    public static final String PRODUCT_LINE_EXIST_UN_ARCHIVE_PRODUCT_CODE = "9021";
    public static final String PRODUCT_LINE_EXIST_UN_ARCHIVE_PRODUCT_CODE_DESC = "产品线存在未归档产品";

    public static final String PRODUCT_LINE_ALREADY_ABANDON_CANNOT_ON_SHELVE_CODE = "9022";
    public static final String PRODUCT_LINE_ALREADY_ABANDON_CANNOT_ON_SHELVE_CODE_DESC = "产品线已注销，不能上架";

    public static final String PRODUCT_STOCK_NOT_EXIST_CODE = "9023";
    public static final String PRODUCT_STOCK_NOT_EXIST_CODE_DESC = "产品库存不存在";

    public static final String PRODUCT_LADDER_INVEST_PERIOD_LOOP_UNIT_EMPTY_CODE = "9024";
    public static final String PRODUCT_LADDER_INVEST_PERIOD_LOOP_UNIT_EMPTY_CODE_DESC = "投资循环周期必须为大于0的整数";

    public static final String PRODUCT_PROFIT_TYPE_CODE = "9025";
    public static final String PRODUCT_PROFIT_TYPE_CODE_DESC = "收益方式参数值不在约定的范围";

    public static final String PRODUCT_CALCULATE_INVEST_TYPE_CODE = "9026";
    public static final String PRODUCT_CALCULATE_INVEST_TYPE_CODE_DESC = "计息方式参数值不在约定的范围";

    public static final String PRODUCT_CONTRACT_LIST_INFO_EMPTY_CODE = "9027";
    public static final String PRODUCT_CONTRACT_LIST_INFO_EMPTY_CODE_DESC = "产品合同信息列表不能为空";

    public static final String PRODUCT_CONTRACT_INFO_UN_COMPLETELY_CODE = "9028";
    public static final String PRODUCT_CONTRACT_INFO_UN_COMPLETELY_CODE_DESC = "产品合同类型、名称、展示名称或文件路径信息为空";

    public static final String PRODUCT_PERIOD_INFO_UN_EXISTS_CODE = "9029";
    public static final String PRODUCT_PERIOD_INFO_UN_EXISTS_CODE_DESC = "产品期限信息为空";

    public static final String PRODUCT_PROFIT_INFO_UN_EXISTS_CODE = "9030";
    public static final String PRODUCT_PROFIT_INFO_UN_EXISTS_CODE_DESC = "产品投资收益信息为空";

    public static final String PRODUCT_CHANGE_STOCK_FLOW_FAILED_CODE = "9031";
    public static final String PRODUCT_CHANGE_STOCK_FLOW_FAILED_CODE_DESC = "保存库存变更流失失败";

    public static final String PRODUCT_DISPLAY_NAME_EXIST_RESULT_CODE = "9032";
    public static final String PRODUCT_DISPLAY_NAME_EXIST_RESULT_CODE_DESC = "产品展示名称已存在";

    public static final String PRODUCT_APPROVAL_STATUS_NOT_IN_ENUMS_CODE = "9033";
    public static final String PRODUCT_APPROVAL_STATUS_NOT_IN_ENUMS_CODE_DESC = "产品审核状态参数值不在约定的范围";

    public static final String PRODUCT_LINE_UN_EXIST_RETURN_CODE = "9034";
    public static final String PRODUCT_LINE_UN_EXIST_RETURN_DESC = "没有查询到相应的产品线信息，无法更新";

    public static final String PRODUCT_APPROVAL_STATUS_CHANGED_RESULT_CODE = "9035";
    public static final String PRODUCT_APPROVAL_STATUS_CHANGED_RESULT_CODE_DESC = "产品审核状态已变更，请刷新后重试";

    public static final String PRODUCT_APPROVAL_SIGN_UN_CONFIG_CODE = "9036";
    public static final String PRODUCT_APPROVAL_SIGN_UN_CONFIG_CODE_DESC = "产品审核授权信息未配置";

    public static final String PRODUCT_APPROVAL_NO_PRIVILEGE_CODE = "9037";
    public static final String PRODUCT_APPROVAL_NO_PRIVILEGE_CODE_DESC = "您无权审核该产品信息";

    public static final String PRODUCT_ON_LINE_NOT_IN_CORRECT_STATUS_CODE = "9038";
    public static final String PRODUCT_ON_LINE_NOT_IN_CORRECT_STATUS_CODE_DESC = "当前状态下，不允许执行产品上线操作";

    public static final String PRODUCT_SALE_END_DATE_BEFORE_VALUE_DATE_CODE = "9039";
    public static final String PRODUCT_SALE_END_DATE_BEFORE_VALUE_DATE_CODE_DESC = "产品募集截止时间必须小于起息时间";

    public static final String PRODUCT_ALREADY_ON_SHELF_RESULT_CODE = "9040";
    public static final String PRODUCT_ALREADY_ON_SHELF_RESULT_CODE_DESC = "该产品已上架";

    public static final String PRODUCT_MIN_INVEST_AMOUNT_GT_TOTAL_AMOUNT_RESULT_CODE = "9041";
    public static final String PRODUCT_MIN_INVEST_AMOUNT_GT_TOTAL_AMOUNT_RESULT_CODE_DESC = "产品起投金额不能大于募集总规模";

    public static final String PRODUCT_INCREASE_AMOUNT_GT_TOTAL_AMOUNT_RESULT_CODE = "9042";
    public static final String PRODUCT_INCREASE_AMOUNT_GT_TOTAL_AMOUNT_RESULT_CODE_DESC = "产品步长不能大于募集总规模";

    public static final String PRODUCT_MIN_HOLD_AMOUNT_GT_TOTAL_AMOUNT_RESULT_CODE = "9043";
    public static final String PRODUCT_MIN_HOLD_AMOUNT_GT_TOTAL_AMOUNT_RESULT_CODE_DESC = "最低可持有金额不能大于募集总规模";

    public static final String PRODUCT_MAX_INVEST_AMOUNT_GT_TOTAL_AMOUNT_RESULT_CODE = "9044";
    public static final String PRODUCT_MAX_INVEST_AMOUNT_GT_TOTAL_AMOUNT_RESULT_CODE_DESC = "个人最大投资限额不能大于募集总规模";

    public static final String PRODUCT_UN_APPROVALED_OR_UN_ONLINE_RESULT_CODE = "9045";
    public static final String PRODUCT_UN_APPROVALED_OR_UN_ONLINE_RESULT_CODE_DESC = "产品未审核通过或未上线，不允许上架";

    public static final String PRODUCT_MIN_YIELD_RATE_ABOVE_MAX_YIELD_RATE_RESULT_CODE = "9046";
    public static final String PRODUCT_MIN_YIELD_RATE_ABOVE_MAX_YIELD_RATE_RESULT_CODE_DESC = "产品最低预期收益率不能高于最高预期收益率";

    public static final String PRODUCT_ALREADY_UN_DISPLAY_RESULT_CODE = "9047";
    public static final String PRODUCT_ALREADY_UN_DISPLAY_RESULT_CODE_DESC = "该产品已下架";

    public static final String PRODUCT_ALREADY_OFF_LINE_RESULT_CODE = "9048";
    public static final String PRODUCT_ALREADY_OFF_LINE_RESULT_CODE_DESC = "该产品已下线";

    public static final String PRODUCT_LOOP_PERIOD_COUNT_NOT_CORRECT_RETURN_CODE = "9049";
    public static final String PRODUCT_LOOP_PERIOD_COUNT_NOT_CORRECT_RETURN_CODE_DESC = "(到期日-起息日)/循环周期必须等于正整数";

    public static final String PRODUCT_UN_ON_LINE_OFF_LINE_RETURN_CODE = "9050";
    public static final String PRODUCT_UN_ON_LINE_OFF_LINE_RETURN_CODE_DESC = "产品未上线不允许下线";

    public static final String PRODUCT_JOIN_CHANNEL_CODE_NOT_IN_ENUMS_CODE = "9051";
    public static final String PRODUCT_JOIN_CHANNEL_CODE_NOT_IN_ENUMS_CODE_DESC = "产品接入渠道参数值不在约定的范围";

    public static final String PRODUCT_SINGLE_MAX_INVEST_AMOUNT_GT_TOTAL_AMOUNT_RESULT_CODE = "9052";
    public static final String PRODUCT_SINGLE_MAX_INVEST_AMOUNT_GT_TOTAL_AMOUNT_RESULT_CODE_DESC = "产品单笔起投金额不能大于募集总规模";

    public static final String PRODUCT_SINGLE_MAX_INVEST_AMOUNT_GE_MAX_INVEST_AMOUNT_RESULT_CODE = "9053";
    public static final String PRODUCT_SINGLE_MAX_INVEST_AMOUNT_GE_MAX_INVEST_AMOUNT_RESULT_CODE_DESC = "单笔投资限额必须小于个人最大投资限额";

    public static final String PRODUCT_MIN_INVEST_AMOUNT_GE_MAX_INVEST_AMOUNT_RESULT_CODE = "9054";
    public static final String PRODUCT_MIN_INVEST_AMOUNT_GE_MAX_INVEST_AMOUNT_RESULT_CODE_DESC = "起投金额必须小于等于个人最大投资限额";

    public static final String PRODUCT_COMMIT_APPROVAL_START_TIME_AFTER_END_TIME_RESULT_CODE = "9055";
    public static final String PRODUCT_COMMIT_APPROVAL_START_TIME_AFTER_END_TIME_RESULT_CODE_DESC = "产品提交审核开始时间必须小于等于结束时间";

    public static final String PRODUCT_APPROVAL_START_TIME_AFTER_END_TIME_RESULT_CODE = "9056";
    public static final String PRODUCT_APPROVAL_START_TIME_AFTER_END_TIME_RESULT_CODE_DESC = "产品审核开始时间必须小于等于结束时间";

    public static final String PRODUCT_SALE_END_TIME_AFTER_CUR_TIME_RESULT_CODE = "9057";
    public static final String PRODUCT_SALE_END_TIME_AFTER_CUR_TIME_RESULT_CODE_DESC = "产品募集截止时间不能晚于当前日期";

    public static final String PRODUCT_LADDER_LIST_INFO_EMPTY_CODE = "9058";
    public static final String PRODUCT_LADDER_LIST_INFO_EMPTY_CODE_DESC = "产品阶梯信息列表不能为空";

    public static final String PRODUCT_LADDER_INFO_UN_COMPLETELY_CODE = "9059";
    public static final String PRODUCT_LADDER_INFO_UN_COMPLETELY_CODE_DESC = "产品阶梯信息轮次、手续费、实际利率信息不能为空";

    public static final String PRODUCT_MIN_HOLD_AMOUNT_GE_ZERO = "9060";
    public static final String PRODUCT_MIN_HOLD_AMOUNT_GE_ZERO_RESULT_CODE_DESC = "最低可持有金额不能为空且必须大于等于零";

    public static final String PRODUCT_SALE_END_DATE_LE_VALUE_DATE_CODE = "9061";
    public static final String PRODUCT_SALE_END_DATE_LE_VALUE_DATE_CODE_DESC = "产品募集截止时间必须小于等于起息时间";

    public static final String PRODUCT_EXPIRE_DATE_LE_EXPECT_CLEAR_PAYMENT_DATE_CODE = "9062";
    public static final String PRODUCT_EXPIRE_DATE_LE_EXPECT_CLEAR_PAYMENT_DATE_CODE_DESC = "产品预期到期日期必须小于等于产品预期结清日期";
    
    public static final String PRODUCT_TOTAL_AMOUNT_GT_MIN_INVEST_AMOUNT_RESULT_CODE = "9063";
    public static final String PRODUCT_TOTAL_AMOUNT_GT_MIN_INVEST_AMOUNT_RESULT_CODE_DESC = "产品募集总规模不能小于起投金额";
    
    public static final String PRODUCT_SAME_ASSETPOOL_VALUETIME_INVESTPERIOD_ONLINEEXIST_CODE = "9064";
    public static final String PRODUCT_SAME_ASSETPOOL_VALUETIME_INVESTPERIOD_ONLINEEXIST_CODE_DESC = "同一资产池，同一起息日，同一产品期限，并且产品销售状态为上线的产品已存在";

    public static final String PRODUCT_TOTAL_AMOUNT_NOT_NULL_RESULT_CODE = "9065";
    public static final String PRODUCT_TOTAL_AMOUNT_NOT_NULL_RESULT_CODE_DESC = "产品募集总规模不能为空";
}
