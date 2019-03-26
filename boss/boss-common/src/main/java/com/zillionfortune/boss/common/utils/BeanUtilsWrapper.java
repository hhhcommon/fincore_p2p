package com.zillionfortune.boss.common.utils;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.BigDecimalConverter;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.beanutils.converters.IntegerConverter;

/**
 * BeanUtilsWrapper
 *
 * @author fangyang
 */
public class BeanUtilsWrapper {
    //null converter
    static {
        ConvertUtils.register(new BigDecimalConverter(null), BigDecimal.class);
        ConvertUtils.register(new DateConverter(null),Date.class);
        ConvertUtils.register(new IntegerConverter(null), Integer.class);
    }

    public static  void copyProperties(Object des ,Object ori){
        try{
            BeanUtils.copyProperties(des, ori);
        }catch (Throwable ex){
            new RuntimeException(ex);
        }
    }
}
