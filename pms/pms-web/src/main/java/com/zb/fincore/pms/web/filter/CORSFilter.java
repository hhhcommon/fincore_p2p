package com.zb.fincore.pms.web.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * ClassName: CORSFilter <br/>
 * Function: tomcat服务器提供的接口，不能在其他域中访问的时候，需要增 Access-Control-Allow-Origin:* . <br/>
 * Date: 2017年1月9日 下午2:48:31 <br/>
 *
 * @author kaiyun@zillionfortune.com
 * @version 
 * @since JDK 1.7
 */
public class CORSFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
 
    }
 
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        String origin = httpRequest.getHeader("Origin");
        if (origin == null) {
            httpResponse.addHeader("Access-Control-Allow-Origin", "*");
        } else {
            httpResponse.addHeader("Access-Control-Allow-Origin", origin);
        }
        httpResponse.addHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, accessToken,access-token,platform,stalkerid,unionkey,userid");
        httpResponse.addHeader("Access-Control-Allow-Methods", "OPTIONS, POST, GET, DELETE");

        httpResponse.setHeader("Access-Control-Allow-Credentials","true");
        
        filterChain.doFilter(servletRequest, servletResponse);
    }
 
    @Override
    public void destroy() {
 
    }
}