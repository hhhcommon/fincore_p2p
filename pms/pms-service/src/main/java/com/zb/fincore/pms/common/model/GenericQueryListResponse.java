package com.zb.fincore.pms.common.model;

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
    /**
     * 业务响应码
     */
    private String respCode = "0000";

    /**
     * 业务响应描述
     */
    private String respMsg = "成功";
    private List<T> dataList;
    private Integer totalCount = 0;

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

    public List<T> getDataList() {
		return dataList;
	}

	public void setDataList(List<T> dataList) {
		this.dataList = dataList;
	}

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

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
    public GenericQueryListResponse(String respCode, String respMsg) {
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
    public static GenericQueryListResponse build() {
        return new GenericQueryListResponse();
    }

    /**
     * 构建RPC响应对象
     *
     * @param code 业务响应码
     * @param msg  业务响应描述
     * @return RPC响应对象
     */
    public static GenericQueryListResponse build(String code, String msg) {
        return new GenericQueryListResponse(code, msg);
    }

}