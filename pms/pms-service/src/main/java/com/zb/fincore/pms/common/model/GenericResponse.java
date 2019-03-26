package com.zb.fincore.pms.common.model;

import java.util.List;

/**
 * ClassName: GenericResponse <br/>
 * Function: 包装响应结果类. <br/>
 * Date: 2017年9月20日 上午9:14:46 <br/>
 *
 * @author wangzinan_tech@zillionfortune.com
 * @version @param <T>
 * @since JDK 1.7
 */
public class GenericResponse<T> {
    /**
     * 业务响应码
     */
    private String respCode = "0000";

    /**
     * 业务响应描述
     */
    private String respMsg = "成功";
    private T data;

    public String getRespCode() {
        return respCode;
    }

    public void setRespCode(String respCode) {
        this.respCode = respCode;
    }

    public String getRespMsg() {
        return respMsg;
    }

    public void setRespMsg(String respMsg) {
        this.respMsg = respMsg;
    }

    public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	/**
     * 默认构造
     */
    public GenericResponse() {
    }

    /**
     * 重载构造
     *
     * @param respCode 业务响应码
     * @param respMsg  业务响应描述
     */
    public GenericResponse(String respCode, String respMsg) {
        this.respCode = respCode;
        this.respMsg = respMsg;
    }

    /**
     * 构建RPC响应对象
     *
     * @param clazz 类
     * @param <T>   RPC响应对象类型
     * @return RPC响应对象
     */
    public static <T> T build(Class clazz) {
        Object object = null;
        try {
            object = clazz.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (T) object;
    }

	/**
     * 构建RPC响应对象
     *
     * @return RPC响应对象
     */
    public static GenericResponse build() {
        return new GenericResponse();
    }

    /**
     * 构建RPC响应对象
     *
     * @param respCode 业务响应码
     * @param respMsg  业务响应描述
     * @return RPC响应对象
     */
    public static GenericResponse build(String respCode, String respMsg) {
        return new GenericResponse(respCode, respMsg);
    }

}