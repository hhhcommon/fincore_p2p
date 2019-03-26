package com.zb.p2p.customer.common.util;
import org.apache.commons.lang.StringUtils;

/**
 * @author
 * @create 2018-03-09 14:33
 */
public final class SensitiveInfoUtils {


    /**
     * [中文姓名] 只显示第一个汉字，其他隐藏为2个星号<例子：李**>
     *
     * @param fullName
     * @return
     */
    public static String chineseName(String fullName) {
        if (StringUtils.isBlank(fullName)) {
            return "";
        }
        String name = StringUtils.left(fullName, 1);
        return StringUtils.rightPad(name, StringUtils.length(fullName), "*");
    }

    /**
     * [中文姓名] 只显示第一个汉字，其他隐藏为2个星号<例子：李**>
     *
     * @param familyName
     * @param givenName
     * @return
     */
    public static String chineseName(String familyName, String givenName) {
        if (StringUtils.isBlank(familyName) || StringUtils.isBlank(givenName)) {
            return "";
        }
        return chineseName(familyName + givenName);
    }

    /**
     * 显示最后四位，其他隐藏。共计18位或者15位。<例子：*************5762>
     *
     * @param cardNo
     * @return
     */
    public static String bankCardSensitive(String cardNo) {
        if (StringUtils.isBlank(cardNo)) {
            return "";
        }
        String num = StringUtils.right(cardNo, 4);
        return StringUtils.leftPad(num, StringUtils.length(cardNo), "*");
    }

    /**
     * 前三位，后四位，其他隐藏<例子:138******1234>
     *
     * @param mobile
     * @return
     */
    public static String mobilePhone(String mobile) {
        if (StringUtils.isBlank(mobile)) {
            return "";
        }
        return StringUtils.left(mobile, 3).concat(StringUtils.removeStart(StringUtils.leftPad(StringUtils.right(mobile, 4), StringUtils.length(mobile), "*"), "***"));
    }


    /**
     * 前六位，后四位，其他用星号隐藏每位1个星号<例子:622260**********1234>
     *
     * @param cardNo
     * @return
     */
    public static String idCardSensitive(String cardNo) {
        if (StringUtils.isBlank(cardNo)) {
            return "";
        }
        return StringUtils.left(cardNo, 6).concat(StringUtils.removeStart(StringUtils.leftPad(StringUtils.right(cardNo, 4), StringUtils.length(cardNo), "*"), "******"));
    }

    /**
     * 全部隐藏
     *
     * @param date
     * @return
     */
    public static String all(String date) {
        if (StringUtils.isBlank(date)) {
            return "";
        }
        return StringUtils.repeat("*", StringUtils.length(date));
    }

    /**
     * 获取隐藏后的数据
     * @param leftNum 左侧显示数据长度
     * @param rightNum 右侧显示数据长度
     * @return
     */
    public static String getHiddenData(String srcData,int leftNum,int rightNum){
        if(org.apache.commons.lang.StringUtils.isEmpty(srcData)){
            return "";
        }
        StringBuffer resultBuffer=new StringBuffer();
        int srcLen=srcData.length();
        int subLen=leftNum+rightNum;
        if(srcLen<=subLen){
            return srcData;
        }
        resultBuffer.append(srcData.substring(0,leftNum));
        for(int i=0;i<srcLen-subLen;i++){
            resultBuffer.append("*");
        }
        resultBuffer.append(srcData.substring(srcLen-rightNum,srcLen));
        return resultBuffer.toString();
    }


    public static void main(String args[]){

        String mobile ="15921552426";
        String certNo = "310125198801205555";
        String bankNo="6227001815390114235";

        String secretMobile = mobilePhone(mobile);
        String secretCertNo = idCardSensitive(certNo);
        String secretbankNo = bankCardSensitive(bankNo);

        System.out.println("手机号：" + secretMobile);
        System.out.println("身份证号：" + secretCertNo);
        System.out.println("银行卡号：" + secretbankNo);

        System.out.println("手机号1：" + getHiddenData(mobile, 3, 4));
        System.out.println("身份证号1：" + getHiddenData(certNo, 6, 4));
        System.out.println("银行卡号1：" + getHiddenData(bankNo, 0, 4));
    }
}
