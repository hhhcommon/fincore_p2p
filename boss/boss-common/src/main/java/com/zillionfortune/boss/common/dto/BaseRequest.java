/*
 * Copyright (c) ${year}, 资邦金服（上海）网络科技有限公司. All Rights Reserved.
 *
 */
package com.zillionfortune.boss.common.dto;

import java.io.Serializable;

/**
 * ClassName: BaseRequest <br/>
 * Function: 请求对象基础类封装. <br/>
 * Date: 2016年11月8日 上午9:48:40 <br/>
 *
 * @author Administrators
 * @version 
 * @since JDK 1.7
 */
public class BaseRequest implements Serializable {

    private static final long serialVersionUID = 2583782607686129680L;
    
    /**
     * createBy: 创建人
     */
    private String createBy;
    
    /**
     * modifyBy:修改人
     */
    private String modifyBy;

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public String getModifyBy() {
		return modifyBy;
	}

	public void setModifyBy(String modifyBy) {
		this.modifyBy = modifyBy;
	}
}
