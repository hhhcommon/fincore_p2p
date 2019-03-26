package com.zb.fincore.pms.facade.product.dto.resp;

import com.zb.fincore.pms.common.dto.BaseResponse;

/**
 * 产品统计响应对象
 *
 * @author
 * @create 2017-07-11 10:46
 */
public class ProductStatisticsResponse extends BaseResponse{

    /**
     * 归档产品总数
     */
    private Long archiveProTotal;

    /**
     * 审核通过产品总数
     */
    private Long approvalSuccessProTotal;

    /**
     * 审核失败产品总数
     */
    private Long approvalFailureProTotal;

    /**
     * 待审核产品总数
     */
    private Long approvalWaitProTotal;

    public Long getArchiveProTotal() {
        return archiveProTotal;
    }

    public void setArchiveProTotal(Long archiveProTotal) {
        this.archiveProTotal = archiveProTotal;
    }

    public Long getApprovalSuccessProTotal() {
        return approvalSuccessProTotal;
    }

    public void setApprovalSuccessProTotal(Long approvalSuccessProTotal) {
        this.approvalSuccessProTotal = approvalSuccessProTotal;
    }

    public Long getApprovalFailureProTotal() {
        return approvalFailureProTotal;
    }

    public void setApprovalFailureProTotal(Long approvalFailureProTotal) {
        this.approvalFailureProTotal = approvalFailureProTotal;
    }

    public Long getApprovalWaitProTotal() {
        return approvalWaitProTotal;
    }

    public void setApprovalWaitProTotal(Long approvalWaitProTotal) {
        this.approvalWaitProTotal = approvalWaitProTotal;
    }
}
