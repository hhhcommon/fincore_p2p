package com.zb.p2p.customer.api.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by haoyifen on 16-9-28 2016 下午5:53
 */
@ApiModel
@Data
public class ImgCaptchaResp {
	@ApiModelProperty(value="图片验证码")
    private byte[] imgCaptcha;

    public ImgCaptchaResp() {
    }

    public ImgCaptchaResp(byte[] imgCaptcha) {
        this.imgCaptcha = imgCaptcha;
    }

}
