package com.zb.p2p.customer.web.config;

import com.zb.p2p.customer.common.enums.AppCodeEnum;
import com.zb.p2p.customer.common.exception.AppException;
import com.zb.p2p.customer.common.model.BaseRes;
import com.zb.p2p.customer.common.util.AppContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.time.StopWatch;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * <p> 控制器的基础拦截器 </p>
 *
 * @author Vinson
 * @version BasicControllerAspect.java v1.0 2018/3/21 19:54 Zhengwenquan Exp $
 */
@Aspect
@Component
@Slf4j
public class BasicControllerAspect {

    @Pointcut("execution(public * com.zb.p2p.customer.web.controller..*(..))")
    public void invoke() {}


    @Around("invoke()")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        BaseRes<Object> result = null;
        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = sra.getRequest();

        StopWatch watch = new StopWatch();
        watch.start();

        try {
            // 转换设置customerId
            String customerId = request.getHeader("customerId");
            if(customerId != null) {
                AppContext.getInstance().setCustomerId(Long.valueOf(customerId));
            }
            // 校验
            Assert.notNull(request, "请求信息不能为空");

            StringBuffer url = request.getRequestURL();

            log.info("APP==>p2p-member: url= {}，method= {}，ip= {}，params= {}",
                    url, request.getMethod(), request.getRemoteAddr(), pjp.getArgs());

            // 执行结果
            result = (BaseRes<Object>) pjp.proceed();

        } catch (Throwable e) {
            log.error("请求异常", e);
            result = new BaseRes<>(false);
            if (e instanceof IllegalArgumentException) {
                result.failure(AppCodeEnum._0001_ILLEGAL_PARAMETER.getCode(), e.getMessage());
            } else if (e instanceof AppException) {
                result.failure(AppCodeEnum._9999_ERROR.getCode(), e.getMessage());
            } else {
                result.setMessage(AppCodeEnum._9999_ERROR.getMessage());
            }
        } finally {
            watch.stop();
            log.info("APP<==p2p-member Cost：[{}]ms，Response：{}", watch.getTime(), result);
        }

        return result;
    }

}
