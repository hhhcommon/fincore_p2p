
package com.zb.p2p.customer.api.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by laoguoliang
 */
@ApiModel(value = "会员查询条件", description="会员查询条件")
public class CustCond{
    @ApiModelProperty(value="当前页索引")
    private Integer pageNo;
    @ApiModelProperty(value="当前页大小")
    private Integer pageSize;

    public CustCond() {
    }

    /**
     * 页号和页大小必输
     * @return
     */
    public boolean isValid(){
    	if(pageNo == null || pageSize == null){
    		return false;
    	}
    	return true;
    }
	public Integer getPageNo() {
		return pageNo;
	}
	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

}
