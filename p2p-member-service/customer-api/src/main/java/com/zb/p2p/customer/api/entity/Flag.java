/**
 * 
 */
package com.zb.p2p.customer.api.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 通用返回VO，返回标记位
 * @author guolitao
 *
 */
@ApiModel
@Data
public class Flag {

	@ApiModelProperty(value="标志状态")
	private String flag;
	@ApiModelProperty(value="标志描述")
	private String desc;
	@ApiModelProperty(value="是否唐小僧用户")
	private String isTXSUser;

	public Flag(String flag, String desc, String isTXSUser) {
		super();
		this.flag = flag;
		this.desc = desc;
		this.isTXSUser = isTXSUser;
	}


	
}
