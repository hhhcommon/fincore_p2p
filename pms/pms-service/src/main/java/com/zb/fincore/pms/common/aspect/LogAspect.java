package com.zb.fincore.pms.common.aspect;

import com.zb.cloud.logcenter.utils.StringUtils;
import com.zb.fincore.common.utils.JsonUtils;
import org.apache.commons.lang.ArrayUtils;
import org.aspectj.lang.JoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;


/**
 * Created by wangwanbin on 2017/1/3.
 * 使用springAop打印请求参数和返回参数及异常信息
 */
public class LogAspect {

    private static Logger logger = LoggerFactory.getLogger(LogAspect.class);

    private static Set<String> setVals = new HashSet<String>();

    private static String methods = com.zb.fincore.pms.common.utils.PropertiesUtil.getEnvProp("print_log_not_by_aspect_methods");

    //不需要通知切面打印日志的方法
    static  {

        if (StringUtils.isNotBlank(methods)) {
            String[] methodAarry = methods.split(",");
            if (ArrayUtils.isNotEmpty(methodAarry)) {
                for (int index = 0; index < methodAarry.length; index ++) {
                    setVals.add(methodAarry[index]);
                }
            }
//    	setVals.add("queryProductList");
//    	setVals.add("queryAllKindsOfLastSoldOutProductList");
//    	setVals.add("queryProductLineForVouchersList");
//    	setVals.add("queryProductLineList");
//    	setVals.add("queryProductInfo");
//    	setVals.add("queryProductListForCash");
        }
    }

    //任何通知方法都可以将第一个参数定义为 org.aspectj.lang.JoinPoint类型
    public void before(JoinPoint call) {
        //获取目标对象对应的类名
        String className = call.getTarget().getClass().getName();
        //获取目标对象上正在执行的方法名
        String methodName = call.getSignature().getName();
        //过滤方法
        if (setVals.contains(methodName)) {
            return;
        }

        logger.info(className + "." + methodName + "开始执行");
        //获取参数
        Object[] args = call.getArgs();
        for (int i = 0; i < args.length; i++) {
            Object obj = args[i];
            if (obj == null) {
                logger.info("请求参数" + (i + 1) + ":");
            } else {
                String req = JsonUtils.beanToJson(obj);
                logger.info("请求参数" + (i + 1) + ":" + req);
            }
        }
    }

    public void afterReturn(JoinPoint call, Object retValue) {
        //获取目标对象对应的类名
        String className = call.getTarget().getClass().getName();
        //获取目标对象上正在执行的方法名
        String methodName = call.getSignature().getName();
        //过滤方法
        if (setVals.contains(methodName)) {
            return;
        }
        String resp = JsonUtils.beanToJson(retValue);
        logger.info(className + "." + methodName + "返回值:" + resp);
    }

//    public void after() {
//        System.out.println("最终通知:不管方法有没有正常执行完成，一定会返回的");
//    }

    public void afterThrowing(JoinPoint call, Throwable e) {
        //获取目标对象对应的类名
        String className = call.getTarget().getClass().getName();
        //获取目标对象上正在执行的方法名
        String methodName = call.getSignature().getName();
        //过滤方法
        if (setVals.contains(methodName)) {
            return;
        }
        logger.error(className + "." + methodName + "发生异常:", e);
    }
}
