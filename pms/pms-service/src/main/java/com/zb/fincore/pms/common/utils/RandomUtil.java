package com.zb.fincore.pms.common.utils;

import com.zb.fincore.common.redis.RedisLock;
import com.zb.fincore.common.redis.RedisManager;
import com.zb.fincore.pms.common.Constants;
import com.zb.fincore.pms.common.enums.ChangeProductStockStatusEnum;
import com.zb.fincore.pms.common.enums.ChangeProductStockTypeEnum;
import com.zb.fincore.pms.common.exception.BusinessException;
import com.zb.fincore.pms.service.dal.model.ProductStock;
import com.zb.fincore.pms.service.dal.model.ProductStockChangeFlow;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * Created by kaiyun on 2018/3/16 0016.
 */
public class RandomUtil {

    @Autowired
    protected RedisManager redisManager;

    /**
     * 生成随机文件名：当前年月日时分秒+五位随机数
     *
     * @return
     */
    public static String getRandomFileName() {

        SimpleDateFormat simpleDateFormat;

        simpleDateFormat = new SimpleDateFormat("yyyyMMddhhmmss");

        Date date = new Date();

        String str = simpleDateFormat.format(date);

        Random random = new Random();

        int rannum = (int) (random.nextDouble() * (99999 - 10000 + 1)) + 10000;// 获取5位随机数

        return  str + rannum;// 当前时间
    }

    public static void main(String[] args) {

        String fileName = RandomUtil.getRandomFileName();

        System.out.println("生成随机文件名：当前年月日时分秒+五位随机数:" + fileName);//8835920140307
    }
}
