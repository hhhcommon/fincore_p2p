package com.zb.p2p.api.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 打印拦截链中未捕获的异常信息，便于跟踪问题
 * Created by limingxin on 2018/3/13.
 */
@Slf4j
@Component
public class ErrorFilter extends ZuulFilter {

    @Override
    public String filterType() {
        return "error";
    }

    @Override
    public int filterOrder() {
        return 10;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        RequestContext cxt = RequestContext.getCurrentContext();
        Throwable throwable = cxt.getThrowable();
        if (throwable != null) {
            log.error("inner error", throwable);
        }
        return null;
    }
}
