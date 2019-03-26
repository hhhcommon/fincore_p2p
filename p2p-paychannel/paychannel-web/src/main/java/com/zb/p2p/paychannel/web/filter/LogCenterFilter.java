package com.zb.p2p.paychannel.web.filter;

import javax.servlet.annotation.WebFilter;

import com.zb.cloud.logcenter.http.filter.ZbHttpFilter;

@WebFilter(filterName="LogCenterFilter",urlPatterns="/*")
public class LogCenterFilter extends ZbHttpFilter{

}
