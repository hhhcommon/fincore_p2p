package com.zb.fincore.pms.facade.product.dto.req;

import com.zb.fincore.pms.common.dto.BaseRequest;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * 功能: 产品审核请求
 * 日期: 2017/4/13 0011 10:47
 * 版本: V1.0
 */
public class ApproveProductRequest extends BaseRequest {

    /**
     * SerialVersionUID
     */
    private static final long serialVersionUID = -4246572505185932501L;

    @NotBlank(message = "产品编号不能为空")
    private String productCode;

    @NotNull(message = "审核状态不能为空")
    private Integer approvalStatus;

    private String approvalSuggestion;

    @NotBlank(message = "审核人不能为空")
    private String approvalBy;

    @NotBlank(message = "授权级别不能为空")
    private String sign;

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public Integer getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(Integer approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public String getApprovalSuggestion() {
        return approvalSuggestion;
    }

    public void setApprovalSuggestion(String approvalSuggestion) {
        this.approvalSuggestion = approvalSuggestion;
    }

    public String getApprovalBy() {
        return approvalBy;
    }

    public void setApprovalBy(String approvalBy) {
        this.approvalBy = approvalBy;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
