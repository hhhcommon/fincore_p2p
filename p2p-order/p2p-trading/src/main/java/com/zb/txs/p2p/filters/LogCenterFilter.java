/*
 *Copyright (c) 2017, 资邦金服(上海)网络科技有限公司. All Rights Reserved.
 *
*/
package com.zb.txs.p2p.filters;


import com.zb.cloud.logcenter.http.filter.ZbHttpFilter;

import javax.servlet.annotation.WebFilter;

@WebFilter(filterName = "LogCenterFilter",urlPatterns = "/*")
public class LogCenterFilter extends ZbHttpFilter {
}
