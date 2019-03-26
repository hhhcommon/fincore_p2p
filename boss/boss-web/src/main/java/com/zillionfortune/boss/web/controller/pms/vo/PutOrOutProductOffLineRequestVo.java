/*
 * Copyright (c) ${year}, 资邦金服（上海）网络科技有限公司. All Rights Reserved.
 *
 */
package com.zillionfortune.boss.web.controller.pms.vo;

/**
 * ClassName: PutOrOutProductOffLineRequestVo <br/>
 * Function: 产品上线/下线用Vo. <br/>
 * Date: 2017年5月10日 下午2:16:29 <br/>
 *
 * @author wangzinan_tech@zillionfortune.com
 * @version 
 * @since JDK 1.7
 */
public class PutOrOutProductOffLineRequestVo {
	/** productCode:产品编码 **/
	private String productCode;
	
	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
}
