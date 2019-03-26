/**
 * 
 */
package com.zb.p2p.customer.api.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author liguoliang
 *
 */
@ApiModel
@Data
public class LoginDeviceDetail {

	@ApiModelProperty(value="当前登录设备")
	private String loginDevice;
	@ApiModelProperty(value="最后一次登录设备")
	private String hisDevice;
	@ApiModelProperty(value="最后一次登录时间\n")
	private String lastLoginTime;

}
