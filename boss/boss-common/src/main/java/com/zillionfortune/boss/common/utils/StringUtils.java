package com.zillionfortune.boss.common.utils;

import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.security.authentication.encoding.Md5PasswordEncoder;

import com.alibaba.fastjson.JSONObject;

public class StringUtils {
	private static String regJs = "<([^>]*)>";
	private final static String md5Key = "qianbao666";
	private final static String userCenterKey = "1qaz2wsx";
	private final static String numberReg = "^[+]?(([1-9]\\d*[.]?)|(0.))(\\d{0,2})?$";

	private static final String qbaourl = "http://a.qbao.com/short";

	private static String shortSpreeUrl=null;



	//qianbao shortUrl Service
	public static String getQbaoShortUrl(String longUrl){
		String shareUrl = longUrl;
		Map<String, String> map = new HashMap<String, String>();
		map.put("longUrl",longUrl);
		try {
			String responseStr = HttpClientUtil.sendPostRequest(qbaourl, map);
			JSONObject jsonObject = JSONObject.parseObject(responseStr);
			Boolean responseCode = jsonObject.getBoolean("ok");
			if(responseCode){
				shareUrl = jsonObject.getString("shortUrl");
			}
		} catch (Exception e) {
			e.printStackTrace();
			shareUrl=longUrl;
		}
		return shareUrl;
	}

	public static boolean isHtmlTag(String content) {
		Pattern pattern = Pattern.compile(regJs);
		Matcher matcher = pattern.matcher(content);
		return matcher.find();
	}

	/**
	 * 将包含unicode编码的字符串转化为汉字
	 */
	public static String decodeUnicode(String theString) {
		char aChar;
		int len = theString.length();
		StringBuffer outBuffer = new StringBuffer(len);
		for (int x = 0; x < len;) {
			aChar = theString.charAt(x++);
			if (aChar == '\\') {
				aChar = theString.charAt(x++);
				if (aChar == 'u') {
					int value = 0;
					for (int i = 0; i < 4; i++) {
						aChar = theString.charAt(x++);
						switch (aChar) {
						case '0':
						case '1':
						case '2':
						case '3':
						case '4':
						case '5':
						case '6':
						case '7':
						case '8':
						case '9':
							value = (value << 4) + aChar - '0';
							break;
						case 'a':
						case 'b':
						case 'c':
						case 'd':
						case 'e':
						case 'f':
							value = (value << 4) + 10 + aChar - 'a';
							break;
						case 'A':
						case 'B':
						case 'C':
						case 'D':
						case 'E':
						case 'F':
							value = (value << 4) + 10 + aChar - 'A';
							break;
						default:
							throw new IllegalArgumentException("Malformed      encoding.");
						}

					}
					outBuffer.append((char) value);
				} else {
					if (aChar == 't') {
						aChar = '\t';
					} else if (aChar == 'r') {
						aChar = '\r';
					} else if (aChar == 'n') {
						aChar = '\n';
					} else if (aChar == 'f') {
						aChar = '\f';
					}
					outBuffer.append(aChar);
				}
			} else {
				outBuffer.append(aChar);
			}

		}
		return outBuffer.toString();
	}

	public static String tractransfer(String msg) {
		return org.apache.commons.lang.StringUtils.replaceEach(msg, new String[] { "<", ">", "%", "/", "\\", "&", "#", "$" }, new String[] { "", "", "", "", "", "", "", "" });
	}

	public static String tractransfer4JavaScript(String msg) {
		return org.apache.commons.lang.StringUtils.replaceEach(msg, new String[] { "<", ">" }, new String[] { "&lt;", "&gt;" });
	}

	public static boolean isEmpty(String str) {
		return (str == null) || str.trim().equals("") || str.trim().equalsIgnoreCase("null");
	}
	
	   public static boolean isNotEmpty(String str) {
	        return !((str == null) || str.trim().equals("") || str.trim().equalsIgnoreCase("null"));
	    }

	public static String getMd5Encoder(String... args) {
		StringBuilder sb = new StringBuilder();
		for (String string : args) {
			sb.append(string);
		}
		Md5PasswordEncoder md5 = new Md5PasswordEncoder();
		return md5.encodePassword(sb.toString(), md5Key);
	}

	public static String getUserCenterMd5Encoder(String... args) {
		StringBuilder sb = new StringBuilder();
		for (String string : args) {
			sb.append(string);
		}
		Md5PasswordEncoder md5 = new Md5PasswordEncoder();
		return md5.encodePassword(sb.toString(), userCenterKey);
	}
	
	public static String chargeEmptyString4Client(String str) {
		return isEmpty(str) ? "" : str;
	}

	
	/**
	 * 处理任务返回时间
	 * @param dateTime
	 * @return
	 */
	public static String getStringDateTime(String dateTime){
		if(!dateTime.isEmpty()){
			
			if("-".equals(dateTime.substring(0, 1))){
				return "已完成";
			}
			if(dateTime.length()>5){
				
				
				
				if(dateTime.indexOf("天")>-1 && dateTime.indexOf("时")>-1 && dateTime.indexOf("分")>-1){
					int t=dateTime.indexOf("天");
					int s=dateTime.indexOf("时");
					//int f=dateTime.indexOf("分");
					
					String date=dateTime.substring(0,t);
					String ss=dateTime.substring(t+1,s);
					//String ff=dateTime.substring(s+1,f);
					
					if("00".equals(date)){
						if("00".equals(ss)){
							return "剩"+dateTime.substring(s+1);
						}else{
							return "剩"+dateTime.substring(t+1,s+1);
						}
					}else{
						return "剩"+dateTime.substring(0,t+1); 
					}	
				}
			}
			return "日期格式错误";
		}		
		return "剩00分";	
	}
	
	public static String formatTwoDatesDifferent(String startTime, String endTime, String format) throws ParseException {
		SimpleDateFormat sd = new SimpleDateFormat(format);
		long nd = 1000 * 24 * 60 * 60;//一天的毫秒数
		long nh = 1000 * 60 * 60;//一小时的毫秒数
		long nm = 1000 * 60;//一分钟的毫秒数
		long diff = sd.parse(endTime).getTime() - sd.parse(startTime).getTime();
		long day = diff / nd;//计算差多少天
		long hour = diff % nd / nh;//计算差多少小时
		long min = diff % nd % nh / nm;//计算差多少分钟
		if(day/30 > 0 ){
			return (day/30) + "月";
		}
		if(day > 0 ){
			return day + "天";
		}
		if(hour>0){
			return hour + "小时";
		}
		if(min>0){
			return min + "分钟";
		}
		
		return "0天";
	}
	
	public static String  getDiffTime( String startTime, String endTime, String format){
		try {	
				SimpleDateFormat sd = new SimpleDateFormat(format);
				Date endDate = sd.parse(endTime);
				Date startDate= sd.parse(startTime);
			   Calendar nowCal = Calendar.getInstance();
			   nowCal.setTime(endDate);
			   int nowYear = nowCal.get(Calendar.YEAR);
			   int nowMonth = nowCal.get(Calendar.MONTH);
			   int nowDay = nowCal.get(Calendar.DATE);
			   int nowHour = nowCal.get(Calendar.HOUR_OF_DAY);
			   int nowMis = nowCal.get(Calendar.MINUTE);
			   int nowSecond = nowCal.get(Calendar.SECOND);
			   Calendar createTimeCal = Calendar.getInstance();
			   createTimeCal.setTime(startDate);
			   int createYear = createTimeCal.get(Calendar.YEAR);
			   int createMonth = createTimeCal.get(Calendar.MONTH);   
			   int createDay = createTimeCal.get(Calendar.DATE);
			   int createHour = createTimeCal.get(Calendar.HOUR_OF_DAY);
			   int createMis = createTimeCal.get(Calendar.MINUTE);
			   int createSecond = createTimeCal.get(Calendar.SECOND);
	
			   if(nowYear>createYear){
				   return  (nowYear - createYear)+"年"; 
			   }
			   else if (nowMonth > createMonth){
				   return (nowMonth - createMonth) + "月";
			   }else if(nowDay > createDay){
				   return  (nowDay - createDay)+"天"; 
			   }else if(nowHour > createHour){
				   return (nowHour - createHour)+"小时";
			   }else if(nowMis>createMis){
				   return (nowMis - createMis)+"分钟";
			   }else if(nowSecond > createSecond ){
				   return (nowSecond -createSecond ) +"秒";
			   }else{
				   return "0";
			   }
			} catch (ParseException e) {
				return "0";
			}
		}
	
	public static boolean validNumber(String str){
		Pattern pattern = Pattern.compile(numberReg);
		Matcher matcher = pattern.matcher(str);
		return matcher.find(); 
	}
	
	/**
	 * 验证手机号码
	 * 
	 * @param mobiles
	 */
	public static boolean isPhoneNumber4Username(String mobiles) {
		boolean flag = false;
		try {
			Pattern p = Pattern.compile("^((13[0-9])|(14[7,\\D])|(15[^4,\\D])|(18[0-9]))\\d{8}$");
			Matcher m = p.matcher(mobiles);
			flag = m.matches();
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}
	
	public static String formatText(String text, int expectedLength) {
		if (org.apache.commons.lang.StringUtils.isBlank(text)) {
			return "";
		}
		if (text.length() <= (expectedLength + 1)) { // ...占用一个中文的宽度
			return text;
		}
		return text.replaceAll("^(?s)(." + "{" + expectedLength + "})(.*)$", "$1...");
	}
	
	/**
	 * 生成6位随机码
	 * @return
	 */
	public static String generateVerifyCode() {
		return String.valueOf(new Random().nextInt(899999) + 100000);
	}
	
	/**
	 * 从右向左每三个按指定的符号分隔
	 * @param s
	 * @param seper
	 * @return
	 */
	public static String SCut(String s, char seper) {
		StringBuilder temp = new StringBuilder();
		int number = s.length();
		if (number % 3 != 0) {
			temp.append(s.substring(0, number % 3));
			if (number > 3)
				temp.append(seper);
		}
		for (int i = number % 3; i < number; i += 3) {
			temp.append(s.substring(i, i + 3));
			if (i < number - 3)
				temp.append(seper);
		}
		return temp.toString();
	}
	
	
	public static String stringNullToEmpty(String msg) {
		return null == msg ? "" : msg;
	}
	
	public static String formatNumber(String str){
		if(str.contains(".")){
			String[] s = str.split("\\.");
			return s[0].replaceAll("(?<=\\d)(?=(\\d{3})+$)", ",")+"."+s[1];
		}else{
			return str.replaceAll("(?<=\\d)(?=(\\d{3})+$)", ",");
		}
	}

	public static boolean checkWithSpecialCharacters(String content) {
        String[] specialCharacters = new String[] { "@", "!", "#", "$", "%", "/", "\\", "'", "\"", "*", "‘", "“", "”", "’", "<", ">", "=" };
        boolean flag = false;
        for (int i = 0; i < specialCharacters.length; i++) {
            if (content.indexOf(specialCharacters[i]) >= 0) {
                flag = true;
                break;
            }
        }
        return flag;
    }
	
	/**
	 * 判断当前参数是否为整数型
	 * 
	 * @param input
	 * @return
	 */
	public static boolean isNumber(String input){
		if(isEmpty(input)) {
			return false;
		}
		try {
			Long.parseLong(input);
			return true;
		} catch (Exception e) {
			return false;
		}
	}


	/**
	 * 是否含有四字节的utf8
	 *
	 * @param s
	 * @return true含有    false不含有
	 */
	public static boolean has4Butf8(String s) {
		byte[] bs = s.getBytes(Charset.forName("utf8"));
		int size = bs.length;
		byte b;
		for (int i = 0; i < size; i++) {
			b = bs[i];
			if ((b & 0xF0) == 0xF0 && (i + 3 < size)) {
				if ((bs[i + 1] & 0x80) == 0x80 &&
						(bs[i + 2] & 0x80) == 0x80 &&
						(bs[i + 3] & 0x80) == 0x80) {
					return true;
				}
			}
		}
		return false;
	}
	
	public static String getRandomChars(int length) {
        String str = "0123456789ABCDEFGHIJKLMNPQRSTUVWXYZ";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; ++i) {
            int number = random.nextInt(35);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }

    public static String ReplaceRestful(String origin,String[] bodys){
        char[] chars = origin.trim().toCharArray();
        int count = 0;
        for(int i=0;i<chars.length;i++){
            if(chars[i] == '{'){
                count ++;
            }
        }
        for(int j=0;j<count;j++){
            origin = origin.replaceFirst("\\{[a-zA-Z0-9]+\\}",bodys[j]);
        }
        return origin;
    }
    
    /**
     * 屏蔽html特殊字符
     */
    public static String escapeHtml(String str){
    	if(str == null){
    		return null;
    	}
    	str = str.replaceAll("script", "");
        str = str.replaceAll("alert", "");
        str = str.replaceAll("</", "");
		str = str.replaceAll("&", "");
		str = str.replaceAll("<", "");
		str = str.replaceAll(">", "");
		str = str.replaceAll("\\|", "");
		str = str.replaceAll("\"", "");
        str = str.replaceAll("\\;", "");
        str = str.replaceAll("\\'", "");
        str = str.replaceAll("\\'", "");
        str = str.replaceAll("\\(", "");
        str = str.replaceAll("\\)", "");
        str = str.replaceAll("\\+", "");
        str = str.replaceAll("\r", "");
        str = str.replaceAll("\n", "");
        str = str.replaceAll("\t", "");
        str = str.replaceAll("\\/", "");
        str = str.replaceAll("\\~", "");
        str = str.replaceAll("\\`", "");
        str = str.replaceAll("\\!", "");
        //str = str.replaceAll("\\@", "");
        str = str.replaceAll("\\#", "");
        str = str.replaceAll("\\$", "");
        str = str.replaceAll("\\%", "");
        str = str.replaceAll("\\^", "");
        str = str.replaceAll("\\*", "");
        str = str.replaceAll("\\+", "");
        str = str.replaceAll("\\=", "");
        //str = str.replaceAll("\\-", "");
        str = str.replaceAll("\\|", "");
        str = str.replaceAll("\\?", "");
        str = str.replaceAll("\\,", "");
		return str;
    }
    
    public static void main(String[] args) {
		System.out.println(escapeHtml("u_4231025<script>alert(1)</script>"));
	}
}
