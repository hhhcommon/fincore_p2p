package com.zb.fincore.pms.common;

/**
 * 产品系统配置表 属性名称 常量
 */
public class GlobalConfigConstants {


    /**
     * 产品审核授权信息配置
     */
    public static final String PRODUCT_APPROVAL_SIGN = "product_approval_sign";
    
	/**
	 * 是否开放测试产品标的（on/off）
	 */
    public static final String TEST_PRODUCT_SWITCH = "test_product_switch";
    
    /**
	 * N复投计划产品的开放时间节点（用#分割，单位：小时）
	 */
    public static final String N_PLAN_OPEN_TIME_NODES = "n_plan_open_time_nodes";
    
    /**
	 * N复投计划对外产品计算库存的时间间隔（单位：分钟），相对开放时间节点
	 */
    public static final String N_PLAN_COUNT_STOCK_INTERVAL_OUT = "n_plan_count_stock_interval_out";
    
    /**
	 * N复投计划对外产品金额阈值（单元：元）
	 */
    public static final String N_PLAN_PRODUCT_OPEN_AMT_THRESHOLD_OUT = "n_plan_product_open_amt_threshold_out";
    
    /**
	 * N复投计划对内产品计算库存的时间间隔（单位：分钟），相对当前日期
	 */
    public static final String N_PLAN_PRODUCT_CLOSE_TIME = "n_plan_product_close_time";
    
    /**
	 * N复投计划对内产品计算库存的时间间隔（单位：分钟），相对当前日期
	 */
    public static final String N_PLAN_COUNT_STOCK_INTERVAL_IN = "n_plan_count_stock_interval_in";
    
    /**
	 * N复投计划对内产品开放产品的时间间隔（单位：分钟），相对n_plan_count_stock_interval_in
	 */
    public static final String N_PLAN_OPEN_TIME_INTERVAL_IN = "n_plan_open_time_interval_in";

}
