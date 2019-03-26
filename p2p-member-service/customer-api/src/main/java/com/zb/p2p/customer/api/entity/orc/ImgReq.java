/**
 * 
 */
package com.zb.p2p.customer.api.entity.orc;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author guolitao
 *
 */
@ApiModel
public class ImgReq {

	@ApiModelProperty(value="正面图片")
	private String frontFile;
	@ApiModelProperty(value="背面图片")
	private String backFile;
	public String getFrontFile() {
		return frontFile;
	}
	public void setFrontFile(String frontFile) {
		this.frontFile = frontFile;
	}
	public String getBackFile() {
		return backFile;
	}
	public void setBackFile(String backFile) {
		this.backFile = backFile;
	}
	
	
}
