package com.zb.txs.p2p.business.order.response.pms;

import java.util.List;

/**
 * ClassName: GenericQueryListResponse <br/>
 * Function: 包装响应结果类. <br/>
 * Date: 2017年9月20日 上午9:14:46 <br/>
 *
 * @author wangzinan_tech@zillionfortune.com
 * @version @param <T>
 * @since JDK 1.7
 */
public class GenericQueryListResponse<T> {
    private String msg;
    private String code;
    private List<T> dataList;

    /**
     * 默认构造
     */
    public GenericQueryListResponse() {
    }

    /**
     * 重载构造
     *
     * @param respCode 业务响应码
     * @param respMsg  业务响应描述
     */
    public GenericQueryListResponse(String code, String msg) {
        this.code = code;
        this.msg = msg;
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
    public static GenericQueryListResponse build() {
        return new GenericQueryListResponse();
    }

    /**
     * 构建RPC响应对象
     *
     * @param respCode 业务响应码
     * @param respMsg  业务响应描述
     * @return RPC响应对象
     */
    public static GenericQueryListResponse build(String code, String msg) {
        return new GenericQueryListResponse(code, msg);
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<T> getDataList() {
        return dataList;
    }

    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
    }

}