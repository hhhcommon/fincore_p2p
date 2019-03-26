package com.zb.p2p.api.constant;

public class Constants {
    public static final String ENV_CONF_PATH = "P2P_API_CONF";

    public static final String HTTP_METHOD_OPTIONS = "OPTIONS";

    public static final String HTTP_HEADER_ORIGIN = "Origin";

    public static final String ALLOW_ALL_ORIGIN = "*";

    public static final String RESP_A001_NOT_LOGIN = "{\"code\" : \"AA01\",\"message\" : \"未登录\",\"data\" : null}";
    public static final String RESP_F001_ILLEGAL_REQUEST = "{\"code\" : \"F001\",\"message\" : \"非法请求\",\"data\" : null}";
    public static final String INVOKE_CUSTOMER_SERVICE_EXCP = "{\"code\" : \"F001\",\"message\" : \"调用会员解析token异常\",\"data\" : null}";

    public static final String RESP_A001_NOT_TRADING_LOGIN = "{\"code\" : \"AA01\",\"msg\" : \"未登录\",\"data\" : null}";
    public static final String RESP_F001_ILLEGAL_TRADING_REQUEST = "{\"code\" : \"F001\",\"msg\" : \"非法请求\",\"data\" : null}";
    public static final String INVOKE_CUSTOMER_SERVICE_TRADING_EXCP = "{\"code\" : \"F001\",\"msg\" : \"调用会员解析token异常\",\"data\" : null}";

}
