package com.zillionfortune.boss.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.zillionfortune.boss.common.constants.CommonConstants;
import com.zillionfortune.boss.common.dto.BaseWebResponse;
import com.zillionfortune.boss.common.enums.RespCode;
import com.zillionfortune.boss.common.enums.ResultCode;
import com.zillionfortune.boss.common.exception.BusinessException;
import com.zillionfortune.boss.common.utils.Endecrypt;
import com.zillionfortune.boss.service.redis.TokenManager;
import com.zillionfortune.boss.service.redis.UserRedisService;

@Component
public class LoginCheckFilter implements Filter {

	@Autowired
	UserRedisService userRedisService;

	@Autowired
	TokenManager tokenManager;

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		log.info("------------校验是否登录--------------intit方法");

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		log.info("------------校验是否登录--------------doFilter方法--begin");

		try {
			HttpServletResponse resp = (HttpServletResponse) response;
			resp.setHeader("Content-type", "text/html;charset=UTF-8");
			resp.setCharacterEncoding("UTF-8");
			resp.setContentType("application/json");

			BaseWebResponse webResponse = null;
			HttpServletRequest req = (HttpServletRequest) request;

			if (req.getMethod().equalsIgnoreCase("options")) {
				chain.doFilter(request, response);
				return;
			}

			// 过滤URL
			String reqUrl = req.getRequestURI();
			if (StringUtils.isNotBlank(reqUrl) && (reqUrl.indexOf("operationLogin.html") > 0 || reqUrl.indexOf("download.html") > 0|| reqUrl.indexOf("getloanagreement.html") > 0)) {
				chain.doFilter(request, response);
				return;
			}

			// 取header信息
			String sessionId = req.getHeader("sessionId");
			if (sessionId == null || StringUtils.isBlank(sessionId)) {
				webResponse = new BaseWebResponse(RespCode.SUCCESS.code(), ResultCode.USER_NOT_LOGIN.code(), ResultCode.USER_NOT_LOGIN.desc());
				resp.getWriter().print(JSONObject.toJSON(webResponse));
				return;
			} else {
				String userId = new Endecrypt().get3DESDecrypt(sessionId, CommonConstants.ENDECRYPT_KEY);
				if (userRedisService.check(userId) == false) {// userInfo信息不存在
					webResponse = new BaseWebResponse(RespCode.SUCCESS.code(), ResultCode.USER_NOT_LOGIN.code(), ResultCode.USER_NOT_LOGIN.desc());
					resp.getWriter().print(JSONObject.toJSON(webResponse));
					return;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		chain.doFilter(request, response);

		log.info("------------校验是否登录--------------doFilter方法--end");

	}

	@Override
	public void destroy() {
		log.info("------------校验是否登录--------------destroy方法");

	}

}
