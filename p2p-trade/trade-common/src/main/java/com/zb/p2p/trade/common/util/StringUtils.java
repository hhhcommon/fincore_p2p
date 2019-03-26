package com.zb.p2p.trade.common.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

/**
 * String常用工具类
 */
@Slf4j
public class StringUtils extends org.apache.commons.lang3.StringUtils {

    /**
     * 获取UUID
     */
    public static String getUUID() {
        String uuid = UUID.randomUUID().toString();
        //去掉“-”符号
        return uuid.replaceAll("-", "");
    }

    /**
     * 前缀+时间Long数字
     * @param prefix
     * @return
     */
    public static String generateTimeLongByPrefix(String prefix){
        if (prefix == null) prefix = "";
        return prefix + new Date().getTime();
    }

    /**
     * 前缀+时间Long数字
     * @param prefix
     * @return
     */
    public static String concatStrBy_(String prefix, String suffix){
        return prefix.concat("_").concat(suffix);
    }

    /**
     * 变量夹下划线
     *
     * @param srcStr
     * @return
     */
    public static String convertStringAddChar(String srcStr, String replaceChar) {
        StringBuffer resultBuffer = new StringBuffer();
        for (int i = 0; i < srcStr.length(); i++) {
            char charStr = srcStr.charAt(i);
            int ascChar = srcStr.charAt(i);
            if (ascChar >= 65 && ascChar <= 90) {
                ascChar = ascChar + 32;
                charStr = (char) ascChar;
                resultBuffer.append(replaceChar);
                resultBuffer.append(charStr);
            } else {
                resultBuffer.append(charStr);
            }
        }
        return resultBuffer.toString();
    }

    /**
     * 第一个字符大写转换
     *
     * @param srcStr
     * @return
     */
    public static String firstCharacterToUpper(String srcStr) {
        return srcStr.substring(0, 1).toUpperCase() + srcStr.substring(1);
    }

    /**
     * 将字符转换成去掉下划线并首字母大写
     *
     * @param srcStr
     * @param org
     * @param ob
     * @return
     */
    public static String replaceUnderlineAndfirstToUpper(String srcStr, String org, String ob) {
        String newString = "";
        srcStr = srcStr.toLowerCase();
        int first = 0;
        while (srcStr.indexOf(org) != -1) {
            first = srcStr.indexOf(org);
            if (first != srcStr.length()) {
                newString = newString + srcStr.substring(0, first) + ob;
                srcStr = srcStr.substring(first + org.length(), srcStr.length());
                srcStr = firstCharacterToUpper(srcStr);
            }
        }
        newString = newString + srcStr;
        return newString;
    }

    /**
     * 获取18位身份证判断性别
     * 第17位代表性别，奇数为男，偶数为女。
     *
     * @param idCard 身份证
     * @return sex
     */
    public static String getSex(String idCard) {
        if (org.apache.commons.lang.StringUtils.isEmpty(idCard) || idCard.length() != 18) {
            return "";
        }
        String sex = "";
        char[] arry = idCard.toCharArray();
        int j = arry[16];
        if (j % 2 == 0) {
            sex = "女";
        } else {
            sex = "男";
        }
        return sex;
    }


    /**
     * 获取18位身份证取年龄
     * 第7、8、9、10位为出生年份(四位数)
     *
     * @param age 身份证
     * @return age
     */
    public static Integer getAge(String age) {
        if (org.apache.commons.lang.StringUtils.isEmpty(age) || age.length() != 18) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        int nowYear = calendar.get(Calendar.YEAR);
        int oldYear = Integer.parseInt(age.substring(6, 10));
        int year = nowYear - oldYear;
        return year;
    }

    /**
     * 读取资源文件内容
     *
     * @param path
     * @return
     */
    public static String readResource(String path) {
        String result = null;
        InputStream is = null;
        try {
            is = new FileInputStream(path);
            result = IOUtils.toString(is, "UTF-8");
        } catch (IOException e) {
            log.error("readResource error:" + e);
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(is);
        }
        return result;
    }
}
