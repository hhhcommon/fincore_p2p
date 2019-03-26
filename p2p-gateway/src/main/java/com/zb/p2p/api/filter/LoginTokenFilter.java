package com.zb.p2p.api.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.zb.p2p.api.client.CustomerClient;
import com.zb.p2p.api.conf.WhitelistConf;
import com.zb.p2p.api.constant.Constants;
import com.zb.p2p.customer.api.entity.CustomerReq;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

@Slf4j
@Configuration
public class LoginTokenFilter extends ZuulFilter {
    private static final String LOGIN_TOKEN = "loginToken";

    @Autowired
    private WhitelistConf whitelistConf;
    @Autowired
    private CustomerClient customerClient;
    
    @Value("${canOutAccess}")
    private String canOutAccess;

    @Override
    public Object run() {
        // 对内服务  无需拦截
    	if(!"true".equals(canOutAccess)){
    		return null;
    	}
    	
        log.info("=================================================================================");
        RequestContext cxt = RequestContext.getCurrentContext();
        HttpServletRequest request = cxt.getRequest();
//        log.info("request Body is:{}",charReader(request));

        String requestURI = request.getRequestURI();
        String loginToken = request.getHeader(LOGIN_TOKEN);
        log.info("requestURI:{},loginToken:{}",requestURI,loginToken);
        if (!this.isAllowedUrl(requestURI)) {
            if (isStartUrl(requestURI,"/trading/")) {
                this.doResponse(cxt, Constants.RESP_F001_ILLEGAL_TRADING_REQUEST);
            } else {
                this.doResponse(cxt, Constants.RESP_F001_ILLEGAL_REQUEST);
            }
            return null;
        }

        // loginToken为空且不再免登陆列表中
        boolean allowedNotNeedLoginUrl = this.isAllowedNotNeedLoginUrl(requestURI);
        if (StringUtils.isEmpty(loginToken) && !allowedNotNeedLoginUrl) {
            if (isStartUrl(requestURI,"/trading/")) {
                this.doResponse(cxt, Constants.RESP_A001_NOT_TRADING_LOGIN);
            } else {
                this.doResponse(cxt, Constants.RESP_A001_NOT_LOGIN);
            }
            return null;
        }
        if (!StringUtils.isEmpty(loginToken)) {
            // 调用会员系统验证这个logintoken是否有效
            CustomerReq customerReq = new CustomerReq();
            customerReq.setLoginToken(loginToken);
            String customerId = "";
            try {
                customerId = this.customerClient.getCustomerId(customerReq).getData();
            } catch (Throwable throwable) {
                log.error("调用会员解析token异常", throwable);
                if (isStartUrl(requestURI,"/trading/")) {
                    this.doResponse(cxt, Constants.INVOKE_CUSTOMER_SERVICE_TRADING_EXCP);
                } else {
                    this.doResponse(cxt, Constants.INVOKE_CUSTOMER_SERVICE_EXCP);
                }
                return null;
            }
            log.info("loginToken:{},customerId:{}", loginToken, customerId);
            // customerId为空且不再免登陆列表中
            if (StringUtils.isEmpty(customerId) && !allowedNotNeedLoginUrl) {
                if (isStartUrl(requestURI,"/trading/")) {
                    this.doResponse(cxt, Constants.RESP_A001_NOT_TRADING_LOGIN);
                } else {
                    this.doResponse(cxt, Constants.RESP_A001_NOT_LOGIN);
                }
                return null;
            }

            if (!StringUtils.isEmpty(customerId)) {
                cxt.addZuulRequestHeader("customerId", customerId);
            }

        }
        return null;
    }

    @Override
    public boolean shouldFilter() {
        RequestContext cxt = RequestContext.getCurrentContext();
        HttpServletRequest request = cxt.getRequest();

        String method = request.getMethod();
        log.info("url:{},method:{}", request.getRequestURI(), method);
        if (StringUtils.equalsIgnoreCase(method, Constants.HTTP_METHOD_OPTIONS)) {
            return false;
        }

//		String requestURI = request.getRequestURI();
//		boolean ret = this.isAllowedNotNeedLoginUrl(requestURI);
//		if(ret) {
//			return false;
//		}
        return true;
    }

    @Override
    public int filterOrder() {
        return 2;
    }

    @Override
    public String filterType() {
        //前置拦截
        return "pre";
    }

    private void doResponse(RequestContext cxt, String respStr) {
        cxt.setSendZuulResponse(false);
        cxt.setResponseBody(respStr);
        cxt.getResponse().setContentType("utf-8");
        cxt.getResponse().setContentType("text/html;charset=utf-8");
    }

    private boolean isAllowedUrl(String requestURI) {
        final List<String> allowedUrl = whitelistConf.getAllowedUrl();
        return matches(allowedUrl, requestURI);
    }

    private boolean isAllowedNotNeedLoginUrl(String requestURI) {
        final List<String> allowedUrl = whitelistConf.getAllowedNotNeedLoginUrl();
        return matches(allowedUrl, requestURI);
    }

    private boolean matches(List<String> allowedUrls, String requestURI) {
        if (allowedUrls.contains(requestURI)) {
            return true;
        } else {
            for (String allowedUrl : allowedUrls) {
                if (requestURI.matches(allowedUrl)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isStartUrl(String requestURI,String startUrl) {
        if (requestURI.startsWith(startUrl)) {
            return true;
        }
        return false;
    }

    private String charReader(HttpServletRequest request) {
        String str, wholeStr = "";
        BufferedReader br = null;
        try {
            br = request.getReader();
            while((str = br.readLine()) != null){
                wholeStr += str;
            }
        } catch (IOException e) {
            log.error("charReader解析异常:", e);
        }
        return wholeStr;
    }


}
