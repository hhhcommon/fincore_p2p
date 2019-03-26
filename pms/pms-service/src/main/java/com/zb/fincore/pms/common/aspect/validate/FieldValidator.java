package com.zb.fincore.pms.common.aspect.validate;

import com.zb.fincore.pms.common.Constants;
import com.zb.fincore.pms.common.dto.BaseResponse;
import com.zb.fincore.pms.common.exception.BusinessException;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.groups.Default;
import java.util.Set;

/**
 * 功能: 字段基础校验器
 * 创建: liuchongguang - liuchongguang@zillionfortune.com
 * 日期: 2017/2/10 0010 09:27
 * 版本: V1.0
 */
@Component
public class FieldValidator {

    private Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    public <T> BaseResponse validate(T obj) {
        Set<ConstraintViolation<T>> set = validator.validate(obj, Default.class);
        if (CollectionUtils.isNotEmpty(set)) {
            for (ConstraintViolation<T> cv : set) {
                return BaseResponse.build(Constants.PARAM_NOBLANK_CODE, cv.getMessage());
            }
        }
        return null;
    }

    public <T> BaseResponse validateField(T obj) {
        Set<ConstraintViolation<T>> set = validator.validate(obj, Default.class);
        if (CollectionUtils.isNotEmpty(set)) {
            for (ConstraintViolation<T> cv : set) {
                throw new BusinessException(Constants.FAIL_RESP_CODE, cv.getMessage());
            }
        }
        return null;
    }

//    public static <T> ValidatorResult validateProperty(T obj,String propertyName){
//        ValidatorResult result = new ValidatorResult();
//        Set<ConstraintViolation<T>> set = validator.validateProperty(obj,propertyName,Default.class);
//        if( CollectionUtils.isNotEmpty(set) ){
//            result.setHasErrors(true);
//            Map<String,String> errorMsg = new HashMap<String,String>();
//            for(ConstraintViolation<T> cv : set){
//                errorMsg.put(propertyName, cv.getMessage());
//            }
//            result.setErrorMsg(errorMsg);
//        }
//        return result;
//    }
}
