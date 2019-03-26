package com.zb.p2p.customer.web.config;

import com.zb.p2p.customer.common.enums.AppCodeEnum;
import com.zb.p2p.customer.common.exception.AppException;
import com.zb.p2p.customer.common.model.BaseRes;
import com.zb.p2p.customer.common.util.AppContext;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
@Slf4j
//@Aspect
//@Component
public class JsonResponseInterceptor {
	@SuppressWarnings("unchecked")
//	@Around("@annotation(jsonResponse)")
	public Object proceed(ProceedingJoinPoint point, JsonResponse jsonResponse) throws Throwable {
		BaseRes<Object> res = null;
		String uri = null;
		try {
			HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
			uri = request.getRequestURI();
			String customerId = request.getHeader("customerId");
			if(customerId != null) {
				AppContext.getInstance().setCustomerId(Long.valueOf(customerId));
			}
			res = (BaseRes<Object>) point.proceed();
			//res.success();
		} catch (AppException e) {
			log.warn("{} is failed!",uri, e);
			if(res == null) {
				res = new BaseRes<>();
			}
			res.resetCode(e.getAppCodeEnum());
		} catch (Exception e) {
			log.error("{} is error!",uri, e);
			if(res == null) {
				res = new BaseRes<>();
			}
			res.resetCode(AppCodeEnum._9999_ERROR);
		}
		return res;
	}
	
	public int getOrder() {
		return 1;
	}
}
