package com.zb.fincore.pms.facade.line.dto.req;

import com.zb.fincore.pms.common.dto.BaseRequest;
import org.hibernate.validator.constraints.NotBlank;

/**
 * 产品线上架请求对象
 *
 * @author
 * @create 2017-04-07 16:06
 */
public class OnShelveProductLineRequest extends BaseRequest {

    /**
     * SerialVersionUID
     */
    private static final long serialVersionUID = 7044359075120148484L;

    @NotBlank(message = "产品线编码不可为空")
    private String lineCode;

    public String getLineCode() {
        return lineCode;
    }

    public void setLineCode(String lineCode) {
        this.lineCode = lineCode;
    }

}
