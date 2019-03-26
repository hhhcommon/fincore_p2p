package com.zb.fincore.pms.facade.line.dto.req;

import com.zb.fincore.pms.common.dto.BaseRequest;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * 功能: 更新产品线请求对象
 * 创建: liuchongguang - liuchongguang@zillionfortune.com
 * 日期: 2017/3/29 0029 10:15
 * 版本: V1.0
 */
public class UpdateProductLineRequest extends BaseRequest {

    /**
     * SerialVersionUID
     */
    private static final long serialVersionUID = -6293364192861748188L;

    @NotBlank(message = "产品线编码不可为空")
    private String lineCode;

    @NotBlank(message = "产品线展示名不可为空")
    private String lineDisplayName;

    @NotBlank(message = "修改人不可为空")
    private String modifyBy;

    public String getLineCode() {
        return lineCode;
    }

    public void setLineCode(String lineCode) {
        this.lineCode = lineCode;
    }

    public String getLineDisplayName() {
        return lineDisplayName;
    }

    public void setLineDisplayName(String lineDisplayName) {
        this.lineDisplayName = lineDisplayName;
    }

    public String getModifyBy() {
        return modifyBy;
    }

    public void setModifyBy(String modifyBy) {
        this.modifyBy = modifyBy;
    }
}
