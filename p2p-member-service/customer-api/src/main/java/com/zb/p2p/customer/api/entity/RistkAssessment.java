
package com.zb.p2p.customer.api.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by laoguoliang
 */
@ApiModel(value = "RistkAssessment", description="abc")
public class RistkAssessment {
    @ApiModelProperty(value="问题编号")
    private String questionNo;
    @ApiModelProperty(value="答案编号")
    private String answerNo;

    public RistkAssessment() {
    }

    public String getQuestionNo() {
        return questionNo;
    }

    public void setQuestionNo(String questionNo) {
        this.questionNo = questionNo;
    }

    public String getAnswerNo() {
        return answerNo;
    }

    public void setAnswerNo(String answerNo) {
        this.answerNo = answerNo;
    }
    
}
