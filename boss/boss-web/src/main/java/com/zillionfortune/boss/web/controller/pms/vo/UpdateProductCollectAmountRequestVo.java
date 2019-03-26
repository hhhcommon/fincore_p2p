/*
 * Copyright (c) ${year}, 资邦金服（上海）网络科技有限公司. All Rights Reserved.
 *
 */
package com.zillionfortune.boss.web.controller.pms.vo;

import java.math.BigDecimal;

/**
 * ClassName: PutOrOutProductOffLineRequestVo <br/>
 * Function: 更新产品募集金额Vo. <br/>
 * Date: 2017年5月10日 下午2:16:29 <br/>
 *
 * @author wangzinan_tech@zillionfortune.com
 * @version 
 * @since JDK 1.7
 */
public class UpdateProductCollectAmountRequestVo {
	/** productCode:产品编码 **/
	private String productCode;
	
	/** 募集金额 */
	private BigDecimal collectAmount;
	
	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public BigDecimal getCollectAmount() {
		return collectAmount;
	}

	public void setCollectAmount(BigDecimal collectAmount) {
		this.collectAmount = collectAmount;
	}
	
}
