package com.zb.fincore.pms.web.test.p2p;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.zb.fincore.common.utils.JsonUtils;

import net.sf.json.JSONArray;

import org.apache.commons.lang3.StringUtils;

public class CommonTest {

	public static void main(String[] args) {
//		BigDecimal amt = new BigDecimal("0.000"); 
//		int i = amt.compareTo(BigDecimal.ZERO); 
//		boolean flag = amt.equals(BigDecimal.ZERO);
//		System.out.println(i + "\n" + flag);
		
//		List<String> list = new ArrayList<String>();
//		list.add("a");
//		boolean flag = false ;
//		if (list!=null && !list.isEmpty()) {
//			flag = true;
//		}
//		System.out.println(flag);
		
		
//		long nanoTime = System.nanoTime();
//		long secondsToNanoseconds = TimeUnit.SECONDS.toNanos(3L);//3秒转成毫微秒
//		long mullisecondsToNanoseconds = TimeUnit.MILLISECONDS.toNanos(3000L);//3000毫秒转成毫微秒
//		System.out.println("nanoTime=" + nanoTime + "\n" 
//		+ "secondsToNanoseconds=" + secondsToNanoseconds + "\n"
//		+ "mullisecondsToNanoseconds=" + mullisecondsToNanoseconds + "\n");
		
		
//		BigDecimal amt = new BigDecimal(100);
//        BigDecimal[] results = amt.divideAndRemainder(BigDecimal.valueOf(20));
//        System.out.println(results[0]);
//        System.out.println(results[1]);
        
//        BigDecimal increaseAmt = new BigDecimal(20.0000);
//        BigDecimal reservationAmt = new BigDecimal(100.0000);
//        BigDecimal[] modArray = reservationAmt.divideAndRemainder(increaseAmt);
//	    System.out.println(modArray[0] + "\t" + modArray[1] + "\n" + modArray[1].compareTo(BigDecimal.ZERO));//0等于， 1大于， -1小于
//	    System.out.println(reservationAmt.subtract(increaseAmt).setScale(4,BigDecimal.ROUND_HALF_UP ));
		
//		Integer a = null;
//		System.out.println(a.toString());



//        System.out.println( CommonTest.getSpecifiedDayBefore("2018-01-09"));
		
		
//		System.out.println(StringUtils.leftPad("12", 10, "0"));
//		System.out.println(StringUtils.substring("abcd", 1, 3));
		
//		int a = 8;
//        String s = String.format("%04d", a);
//        System.out.println(s);

		
//		List<String> productCodes = new ArrayList<String>();
//		productCodes.add("a");
//		productCodes.add("b");
//		System.out.println(productCodes.toString());
//
//        String str = StringUtils.join(productCodes, ",");
//        System.out.println(str); //jdk 1.8
//
//        System.out.println(Arrays.asList(StringUtils.split(str, ",")));

//        String listToStr = JsonUtils.objectToJson(productCodes);
//        System.out.println(listToStr);
//        System.out.println(JsonUtils.json2Object(listToStr, productCodes.getClass()) );
		
//		String investAmt = "0.0";
//		BigDecimal result = BigDecimal.ZERO;
//		result = StringUtils.isBlank(investAmt)?result:new BigDecimal(investAmt);
//		System.out.println(result);
		
		StringBuffer productCodes = new StringBuffer();
		productCodes.append("111").append(",").append("2222").append(",");
		System.out.println(productCodes.toString().substring(0,productCodes.toString().length()-1));




    }
	
	/** 
	* 获得指定日期的前一天 
	* @param specifiedDay 
	* @return 
	* @throws Exception 
	*/ 
	public static String getSpecifiedDayBefore(String specifiedDay){ 
		//SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd"); 
		Calendar c = Calendar.getInstance(); 
		Date date=null; 
		try { 
		date = new SimpleDateFormat("yy-MM-dd").parse(specifiedDay); 
		} catch (ParseException e) { 
		e.printStackTrace(); 
		} 
		c.setTime(date); 
		int day=c.get(Calendar.DATE); 
		c.set(Calendar.DATE,day-1); 
	
		String dayBefore=new SimpleDateFormat("yyyy-MM-dd").format(c.getTime()); 
		return dayBefore; 
		}

}
