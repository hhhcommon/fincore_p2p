package com.zb.p2p.paychannel.common.util;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * String常用工具类
 */
public class StringUtils extends org.apache.commons.lang3.StringUtils{

		/**
		* @param args
		*/
		public static void main(String[] args) {
		   //System.out.println(replaceUnderlineAndfirstToUpper("INSERT_TIME","_",""));
			//System.out.println(StringUtils.generUUId());
			System.out.println(convertStringAddChar("aspNet", "_"));
			//System.out.println(StringUtils.generUUId());
		}
		/**
		 * 变量夹下划线
		 *
		 * @param srcStr
		 * @return
		 */
		public static String convertStringAddChar(String srcStr,String replaceChar) {
			StringBuffer resultBuffer=new StringBuffer();
			for(int i=0;i<srcStr.length();i++){
				char charStr=srcStr.charAt(i);
				int  ascChar=srcStr.charAt(i);
				if(ascChar>=65 && ascChar<=90){
					ascChar=ascChar+32;
					charStr=(char)ascChar;
					resultBuffer.append(replaceChar);
					resultBuffer.append(charStr);
				}else{
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
		* @param srcStr
		* @param org
		* @param ob
		* @return
		*/
		public static String replaceUnderlineAndfirstToUpper(String srcStr,String org,String ob)
		{
		   String newString = "";
		   srcStr=srcStr.toLowerCase();
		   int first=0;
		   while(srcStr.indexOf(org)!=-1)
		   {
		    first=srcStr.indexOf(org);
		    if(first!=srcStr.length())
		    {
		     newString=newString+srcStr.substring(0,first)+ob;
		     srcStr=srcStr.substring(first+org.length(),srcStr.length());
		     srcStr=firstCharacterToUpper(srcStr);
		    }
		   }
		   newString=newString+srcStr;
		   return newString;
		}
       public static String generUUId(){
		   String uid=UUID.randomUUID().toString();
		   String radomId= uid.replace("-","");
		   return radomId;
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


	/**
	 * 获取18位身份证判断性别
	 * 第17位代表性别，奇数为男，偶数为女。
	 * @param idCard 身份证
	 * @return sex
	 */
	public static String getSex(String idCard){
		if(org.apache.commons.lang.StringUtils.isEmpty(idCard) || idCard.length() != 18){
			return "";
		}
		String sex="";
		char[] arry=idCard.toCharArray();
		int j=arry[16];
		if(j%2 == 0){
			sex="女";
		}else{
			sex="男";
		}
		return sex;
	}


	/**
	 * 获取18位身份证取年龄
	 * 第7、8、9、10位为出生年份(四位数)
	 * @param age 身份证
	 * @return age
	 */
	public static Integer getAge(String age){
		if(org.apache.commons.lang.StringUtils.isEmpty(age)  || age.length() != 18){
			return null;
		}
		Calendar calendar= Calendar.getInstance();
		calendar.setTime(new Date());
		int nowYear = calendar.get(Calendar.YEAR);
		int oldYear=Integer.parseInt(age.substring(6, 10));
		int year=nowYear-oldYear;
		return  year;
	}
	
	public static String bean2json(Object bean) {
		return JSONObject.toJSONString(bean);
	}
    
	public static String bean2jsonByDCRD(Object bean) {//禁止循环引用检测
		return JSONObject.toJSONString(bean,SerializerFeature.DisableCircularReferenceDetect);
		//return gsonForBean.toJson(bean);
	}
    
	public static String bean2jsonString(Object bean){
		return JSONObject.toJSONString(bean);
	}
	
}
