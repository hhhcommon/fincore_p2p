package com.zb.p2p.customer.api.entity.card;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by laoguoliang
 */
@ApiModel
public class BindCardResultRes {
    @ApiModelProperty(value="状态[1-成功2-处理中3-失败]")
    private String status;
    @ApiModelProperty(value="绑卡结果内容")
    private String content;

    public BindCardResultRes() {
    }
    
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString(){
        return "BindCardResultRes["
                + "status=" + status + 
                ",content=" + content + 
                "]";
    }
}
