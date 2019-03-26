package com.zb.p2p.api.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.zb.p2p.api.conf.CrossConf;
import com.zb.p2p.api.constant.Constants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Slf4j
public class CrossFilter extends ZuulFilter {
    @Autowired
    private CrossConf crossConf;

    @Override
    public boolean shouldFilter() {

        return true;
    }

    @Override
    public Object run() {

        RequestContext cxt = RequestContext.getCurrentContext();
        HttpServletRequest request = cxt.getRequest();
        String method = request.getMethod();
        String origin = request.getHeader(Constants.HTTP_HEADER_ORIGIN);
        log.info("url:{},method:{},origin:{}", request.getRequestURI(), method, origin);

        String allowOrigin = this.gennerateAllowOrigin(origin);
        if (StringUtils.isNotEmpty(allowOrigin)) {
            HttpServletResponse response = cxt.getResponse();
            //允许跨域的域名
            response.setHeader("Access-Control-Allow-Origin", allowOrigin);
            response.setHeader("Access-Control-Allow-Methods", "POST,GET,OPTIONS,DELETE");
            //预检请求,在此期间，不用发出另一条预检请求
            response.setHeader("Access-Control-Max-Age", "3600");
            //允许额外的请求头属性值
            response.setHeader("Access-Control-Allow-Headers", "Content-Type,Authorization,Accept,X-Requested-With,loginToken,"
            		+ "loginToken,userid,stalkerid,Platform,Access-Token,unionKey");
            //允许传递cookie
            response.setHeader("Access-Control-Allow-Credentials", "true");
        }


        if (StringUtils.equalsIgnoreCase(method, Constants.HTTP_METHOD_OPTIONS)) {
            cxt.setSendZuulResponse(false);
        }

        return null;
    }

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    private String gennerateAllowOrigin(String origin) {
        String allowOrigin = null;

        List<String> allowOriginList = this.crossConf.getAllowOriginList();
        if (allowOriginList.contains(Constants.ALLOW_ALL_ORIGIN)) {
            allowOrigin = Constants.ALLOW_ALL_ORIGIN;
        } else if (allowOriginList.contains(origin)) {
            allowOrigin = origin;
        }

        return allowOrigin;
    }
}
