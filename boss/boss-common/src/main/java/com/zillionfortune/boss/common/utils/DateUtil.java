package com.zillionfortune.boss.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import org.apache.commons.lang.time.DateFormatUtils;
import org.joda.time.DateTime;
public final class DateUtil {
	// 默认显示日期的格式
	public static final String DATAFORMAT_STR = "yyyy-MM-dd";
	// 默认显示日期的格式
	public static final String YYYY_MM_DATAFORMAT_STR = "yyyy-MM";
	// 默认显示日期时间的格式
	public static final String DATATIMEF_STR = "yyyy-MM-dd HH:mm:ss";
	public static final String DATATIMEF_NO_YEAR_STR = "MM-dd HH:mm:ss";

	public static final String DATATIMEF_HH_MM_SS = "HH:mm:ss";
	// 默认显示日期时间的格式 精确到毫秒
	public static final String DATATIMEF_STR_MIS = "yyyyMMddHHmmssSSS";
	// 默认显示日期时间的格式 精确到分钟
	public static final String DATATIMEF_STR_MI = "yyyy-MM-dd HH:mm";

	public static final String DATATIMEF_STR_MDHm = "MM-dd HH:mm";

    public static final String HH_STR = "HH";
    
    public static final String YYYYMMDD_STR = "yyyyMMdd";
    
    public static final String YYYYMM_STR = "yyyyMM";
	
    public static final String YYYY_MM_DD_HH = "yyyy-MM-dd HH";
	
	//精确到秒
	public static final String DATATIMEF_STR_SEC = "yyyyMMddHHmmss";
	// 默认显示简体中文日期的格式
	public static final String ZHCN_DATAFORMAT_STR = "yyyy年MM月dd日";
	
	public static final String ZHCN_MMDD_FORMAT_STR = "MM月dd日";
	// 默认显示简体中文日期时间的格式
	public static final String ZHCN_DATATIMEF_STR = "yyyy年MM月dd日HH时mm分ss秒";
	// 默认显示简体中文日期时间的格式
	public static final String ZHCN_DATATIMEF_STR_4yMMddHHmm = "yyyy年MM月dd日HH时mm分";

	// 默认显示月份和日期的格式
	public static final String MONTHANDDATE_STR = "MM.dd";

    public static final String MONTH_DATE_STR = "MM-dd";

//	public static java.text.DateFormat dateFormat = null;
//
//	public static java.text.DateFormat dateTimeFormat = null;
//	
//	public static java.text.DateFormat dateTimeMISFormat = null;
//	
//	public static java.text.DateFormat dateTimeMIFormat = null;
//
//	public static java.text.DateFormat zhcnDateFormat = null;
//
//	public static java.text.DateFormat zhcnDateTimeFormat = null;

//	public static java.text.DateFormat dateTimeFormat = null;
//	
//	public static java.text.DateFormat dateTimeMISFormat = null;
//	
//	public static java.text.DateFormat dateTimeMIFormat = null;
//
//	public static java.text.DateFormat zhcnDateFormat = null;
//
//	public static java.text.DateFormat zhcnDateTimeFormat = null;
	private static long q;

	private DateUtil() {
	}

//	static {
//		dateFormat = new SimpleDateFormat(DATAFORMAT_STR);
//		dateTimeFormat = new SimpleDateFormat(DATATIMEF_STR);
//		dateTimeMISFormat = new SimpleDateFormat(DATATIMEF_STR_MIS);
//		dateTimeMIFormat = new SimpleDateFormat(DATATIMEF_STR_MI);
//		zhcnDateFormat = new SimpleDateFormat(ZHCN_DATAFORMAT_STR);
//		zhcnDateTimeFormat = new SimpleDateFormat(ZHCN_DATATIMEF_STR);
//	}

	public static Date now() {
		return Calendar.getInstance(Locale.CHINESE).getTime();
	}

	public static Date daysAfter(Date baseDate, int increaseDate) {
		Calendar calendar = Calendar.getInstance(Locale.CHINESE);
		calendar.setTime(baseDate);
		calendar.add(Calendar.DATE, increaseDate);
		return calendar.getTime();
	}
	
	public static String daysAfterMMDD(Date baseDate, int increaseDate) {
		Calendar calendar = Calendar.getInstance(Locale.CHINESE);
		calendar.setTime(baseDate);
		calendar.add(Calendar.DATE, increaseDate);
		return dateToDateString(calendar.getTime(), ZHCN_MMDD_FORMAT_STR);
	}
	
	
	public static Date minuteAfter(Date baseDate, int increaseMonths){
        Calendar calendar = Calendar.getInstance(Locale.CHINESE);
        calendar.setTime(baseDate);
        calendar.add(Calendar.MINUTE, increaseMonths);
        return calendar.getTime();
    }

	public static Date hourAfter(Date baseDate, int increaseHours){
		Calendar calendar = Calendar.getInstance(Locale.CHINESE);
		calendar.setTime(baseDate);
		calendar.add(Calendar.HOUR, increaseHours);
		return calendar.getTime();
	}

	public static Date monthAfter(Date baseDate, int increaseMonths) {
		Calendar calendar = Calendar.getInstance(Locale.CHINESE);
		calendar.setTime(baseDate);
		calendar.add(Calendar.MONTH, increaseMonths);
		return calendar.getTime();
	}
	
	public static Date getInternalDateByDay(Date d, int days) {
		Calendar now = Calendar.getInstance(TimeZone.getDefault());
		now.setTime(d);
		now.add(Calendar.DATE, days);
		return now.getTime();
	}

	public static Date getInternalDateByMinute(Date d, int minutes) {
		Calendar now = Calendar.getInstance(TimeZone.getDefault());
		now.setTime(d);
		now.add(Calendar.MINUTE, minutes);
		return now.getTime();
	}

	public static Date toDate(String dateTimeStr) {
		return getDate(dateTimeStr, DATATIMEF_STR);
	}

	/**
	 * 将Date转换成字符串“yyyy-mm-dd hh:mm:ss”的字符串
	 * 
	 * @param date
	 * @return
	 */
	public static String dateToDateString(Date date) {
		return dateToDateString(date, DATATIMEF_STR);
	}
	

	/**
	 * 将Date转换成formatStr格式的字符串
	 * 
	 * @param date
	 * @param formatStr
	 * @return
	 */
	public static String dateToDateString(Date date, String formatStr) {
        if (date == null) {
            return null;
        }
        java.text.DateFormat df = getDateFormat(formatStr);
		return date!=null?df.format(date):"";
	}

	/**
	 * 按照默认formatStr的格式，转化dateTimeStr为Date类型 dateTimeStr必须是formatStr的形式
	 * 
	 * @param dateTimeStr
	 * @param formatStr
	 * @return
	 */
	public static Date getDate(String dateTimeStr, String formatStr) {
		try {
			if (dateTimeStr == null || dateTimeStr.equals("")) {
				return null;
			}
			java.text.DateFormat sdf = new SimpleDateFormat(formatStr);
			java.util.Date d = sdf.parse(dateTimeStr);
			return d;
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

	public static Date strToDate(String dateTimeStr, String formatStr) throws ParseException{
		if (dateTimeStr == null || dateTimeStr.equals("")) {
			return null;
		}
		java.text.DateFormat sdf = new SimpleDateFormat(formatStr);
		java.util.Date d = sdf.parse(dateTimeStr);
		return d;
	}
	
	public static String getCurDate() {
		return dateToDateString(Calendar.getInstance().getTime(), DATAFORMAT_STR);
	}
	
	public static String getCurHour() {
		return dateToDateString(Calendar.getInstance().getTime(), HH_STR);
	}

	public static int getThisMonth() {
		Calendar c = Calendar.getInstance(Locale.CHINESE);
		int month = c.get(Calendar.MONTH) + 1;
		return month;

	}

	public static int getThisWeek() {
		Calendar c = Calendar.getInstance(Locale.CHINESE);
		c.setFirstDayOfWeek(Calendar.MONDAY);
		int week = c.get(Calendar.WEEK_OF_YEAR);
		return week;

	}

	private static java.text.DateFormat getDateFormat(String formatStr) {
		if (formatStr.equalsIgnoreCase(DATAFORMAT_STR)) {
			return new SimpleDateFormat(DATAFORMAT_STR);
		} else if (formatStr.equalsIgnoreCase(DATATIMEF_STR)) {
			return new SimpleDateFormat(DATATIMEF_STR);
		} else if (formatStr.equalsIgnoreCase(ZHCN_DATAFORMAT_STR)) {
			return new SimpleDateFormat(ZHCN_DATAFORMAT_STR);
		} else if (formatStr.equalsIgnoreCase(ZHCN_DATATIMEF_STR)) {
			return new SimpleDateFormat(ZHCN_DATATIMEF_STR);
		} else if (formatStr.equalsIgnoreCase(DATATIMEF_STR_MIS)) {
			return new SimpleDateFormat(DATATIMEF_STR_MIS);
		} else if (formatStr.equalsIgnoreCase(DATATIMEF_STR_MI)) {
			return new SimpleDateFormat(DATATIMEF_STR_MI);
		} else {
			return new SimpleDateFormat(formatStr);
		}
	}

	@SuppressWarnings("deprecation")
	public static String getFirstDateOfMonth(Date now) {
		SimpleDateFormat df1 = new SimpleDateFormat(DATATIMEF_STR);
		Date da = new Date(now.getYear(), now.getMonth(), 01);
		return df1.format(da);
	}

	@SuppressWarnings("deprecation")
	public static String getLastDateOfMonth(Date now) {
		SimpleDateFormat df1 = new SimpleDateFormat(DATATIMEF_STR);
		Date da = new Date(now.getYear(), now.getMonth(), 31);
		return df1.format(da);
	}

	public static Date delayWorkDaysFromNow(Date date, int days) {
		int delay = days - 1;
		DateTime time = new DateTime(date.getTime());
		for (int i = 0; i < days;) {
			int d = time.getDayOfWeek();
			if (d == 6 || d == 7) {
				delay++;
			} else {
				i++;
			}
			time = time.plusDays(1);
		}
		return new DateTime(date.getTime()).plusDays(delay).toDateMidnight().toDate();
	}

	// 当前月的第一天
	public static Date fisrstDayOfCurrentMonth() {
		DateTime today = new DateTime();
		return today.plusDays(1 - today.getDayOfMonth()).toDateMidnight().toDateTime().toDate();
	}

	// 上个月的最后一秒
	public static Date lastMonthLastDay() {
		DateTime today = new DateTime();
		return today.plusDays(1 - today.getDayOfMonth()).toDateMidnight().toDateTime().plusSeconds(-1).toDate();
	}

	// 上个月的天数
	public static int daysOfLastMonth() {
		DateTime today = new DateTime();
		return today.plusDays(1 - today.getDayOfMonth()).toDateMidnight().toDateTime().plusSeconds(-1).getDayOfMonth();
	}

	// 当前时间在这个月的第几天
	public static int dayOfThisMonth() {
		return new DateTime().getDayOfMonth();
	}

	
	/**
	 * 获取两个毫秒间隔的分钟
	 * @param t1
	 * @param t2
	 * @return
	 */
	public static int getMinutesBetweenMillis(long t1,long t2){
		return (int) ((t1 - t2) / (60 * 1000));
	}
	
	/**
	 * 判断目标时间是否处于某一时间段内
	 * @param target
	 * @param begin
	 * @param end 
	 * @return
	 */
	public static boolean compareTargetTime(Date target,String begin,String end){
		//格式化时间 暂时不考虑传入参数的判断，其他地方如果要调用，最好扩展判断一下入参问题
		String targetTime = dateToDateString(target,DATATIMEF_HH_MM_SS);//HH:mm:ss
		return targetTime.compareTo(begin) >= 0 && end.compareTo(targetTime) >= 0;
	}
	
	public static int getCurrentHourBy24H(){
		Calendar calendar = Calendar.getInstance(Locale.CHINESE);
		return calendar.get(Calendar.HOUR_OF_DAY);
	}
	
	/**
	 * 
	 * @param time1
	 * @param timw2
	 * @return   time1 小于 time 2  返回 true
	 */
	public static boolean compareTime(Date time1,Date time2){
		long start =  time1.getTime();
        long end = time2.getTime();
        if(start < end){
        	return true;
        }
		
		return false;
	}
	
	/** 
	* 取得两个时间段的时间间隔 
	* return t2 与t1的间隔天数 
	* throws ParseException 如果输入的日期格式不是0000-00-00 格式抛出异常 
	*/ 
	public static int getBetweenDays(String t1,String t2) throws ParseException{ 
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"); 
		int betweenDays = 0; 
		Date d1 = format.parse(t1); 
		Date d2 = format.parse(t2); 
		Calendar c1 = Calendar.getInstance(); 
		Calendar c2 = Calendar.getInstance(); 
		c1.setTime(d1); 
		c2.setTime(d2); 
		// 保证第二个时间一定大于第一个时间 
		if(c1.after(c2)){ 
			c1 = c2; 
			c2.setTime(d1); 
		} 
		int betweenYears = c2.get(Calendar.YEAR)-c1.get(Calendar.YEAR); 
		betweenDays = c2.get(Calendar.DAY_OF_YEAR)-c1.get(Calendar.DAY_OF_YEAR); 
		for(int i=0;i<betweenYears;i++){ 
			c1.set(Calendar.YEAR,(c1.get(Calendar.YEAR)+1)); 
			betweenDays += c1.getMaximum(Calendar.DAY_OF_YEAR); 
		} 
		return betweenDays; 
	}
	
	
	/** 
	* 取得两个时间段的时间间隔 
	* return t2 与t1的间隔天数 
	* throws ParseException 如果输入的日期格式不是0000-00-00 格式抛出异常 
	*/ 
	public static int subdays(String t1,String t2) throws ParseException{ 
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"); 
		int betweenDays = 0; 
		Date d1 = format.parse(t1); 
		Date d2 = format.parse(t2); 
		Calendar c1 = Calendar.getInstance(); 
		Calendar c2 = Calendar.getInstance(); 
		c1.setTime(d1); 
		c2.setTime(d2); 
		if(c1.after(c2)){ 
			return -1;
		} 
		int betweenYears = c2.get(Calendar.YEAR)-c1.get(Calendar.YEAR); 
		betweenDays = c2.get(Calendar.DAY_OF_YEAR)-c1.get(Calendar.DAY_OF_YEAR); 
		for(int i=0;i<betweenYears;i++){ 
			c1.set(Calendar.YEAR,(c1.get(Calendar.YEAR)+1)); 
			betweenDays += c1.getMaximum(Calendar.DAY_OF_YEAR); 
		} 
		return betweenDays; 
	}
	
	/**
	 * 格式化时间  yyyy-MM-dd
	 * @return
	 */
	public static String getFormatDate(Date date){
			return DateFormatUtils.format(date, DATAFORMAT_STR);
	}
	
    /**
     * 格式化时间  yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String getDateTimeFormatDate(Date date){
        return new SimpleDateFormat(DATATIMEF_STR).format(date);
    }	
	
	
	/**
	 * 按照默认formatStr的格式，转化dateTimeStr为Date类型 dateTimeStr必须是formatStr的形式
	 * 
	 * @param dateTimeStr
	 * @param formatStr
	 * @return
	 */
	public static Date getFormatDate(Date dateTimer, String formatStr) {
		try {
			if (dateTimer == null) {
				return null;
			}
			java.text.DateFormat sdf = new SimpleDateFormat(formatStr);
			String timeStr = sdf.format(dateTimer);
			Date formateDate = sdf.parse(timeStr);
			return formateDate;
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 获取两个时间之间相差的天数
	 * @param time1
	 * @param time2
	 * @return
	 */
	public static long getQuot(String time1, String time2) {
		long quot = 0;
		SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date date1 = ft.parse(time1);
			Date date2 = ft.parse(time2);
			quot = date1.getTime() - date2.getTime();
			quot = quot / 1000 / 60 / 60 / 24;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return quot;
	}
	
	/**
	 * 获取和当前时间相差的分钟数
	 * @param begin
	 * @return
	 */
	public static long getDiffenceValue(Date begin){
		long value = 0;
		Calendar cal = Calendar.getInstance();
		Date now = cal.getTime();
		value = (now.getTime()-begin.getTime())/1000/60;
		return value;
	}
	
	public static long getMillsBetweenTwoDate(Date date1,Date date2){
		return date1.getTime()-date2.getTime();
	}
	
	public static long getDaysBetweenTwoDate(Date date1,Date date2){
		return date1.getTime()/86400000-date2.getTime()/86400000;
	}
	
	
	/**
	 * 求多少天前/后的日期
	 * 
	 * @param field 单位：年，月，日
	 * @param day  多少天
	 * @return
	 */
	public static final Date addDate(int field,int day){
		Calendar nowCalendar = Calendar.getInstance(Locale.CHINESE);
		nowCalendar.setTime(DateUtil.now());
		nowCalendar.add(field, day);
		return  nowCalendar.getTime();
	}

	public static final Date addDateDay(int field,int day){
		Calendar nowCalendar = Calendar.getInstance(Locale.CHINESE);
		nowCalendar.setTime(DateUtil.now());
		nowCalendar.add(field, day);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String sd = sdf.format(nowCalendar.getTime());
		try {
			return sdf.parse(sd);
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * 当前日
	 * @param date
	 * @return
	 */
	public static String getCurrenDay(Date date) {
		SimpleDateFormat sdf1 = new SimpleDateFormat("dd日");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		//calendar.add(Calendar.DAY_OF_MONTH, -1);
		date = calendar.getTime();
		return sdf1.format(date);
	}
	
	/**
	 * 前一天
	 * @param date
	 * @return
	 */
	public static String getNextDay(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, 1);
		date = calendar.getTime();
		return sdf.format(date);
	}

    public static String getNextDate(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        date = calendar.getTime();
        return sdf.format(date);
    }
    
    public static String getNextDate(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        date = calendar.getTime();
        return sdf.format(date);
    }

    public static Date createDateByString(String dateStr,String pattern){
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        Date date = null;
        try {
            date = sdf.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
	 * 将时间戳转换成时间
	 * @param timeStamp
	 * @return
	 */
	public static Date getDatebyTimeStamp(String timeStamp){
	    SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
	    String sd = sdf.format(new Date(Long.parseLong(timeStamp)));  
	    try {
			return sdf.parse(sd);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static int getDayOfMonth(Date d) {
		if (d == null) {
			throw new NullPointerException("");
		}
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		return c.get(Calendar.DAY_OF_MONTH);
	}
	
	/**
	 * @description 今天余下的秒数
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static long getTodaySurplusSec(){
		Date d = new Date();
		long today = d.getTime();
		d.setDate(d.getDate()+1);
		d.setHours(0);
		d.setMinutes(0);
		d.setSeconds(0);
		long yesday = d.getTime();
		return yesday - today;
	}
	
	/**
	 * 比较当前日期是否在指定的日期之前
	 * @param d
	 * @param endTime
	 * @return
	 */
	public static boolean compareCurrDateToDate(Date d, String endTime) {
		boolean flag = false;
		SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date end = ft.parse(endTime);
			if(d.before(end)){
				flag = true;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return flag;
	}
	
	public static String formatTwoDatesDifferent(String startTime, String endTime, String format) {
		SimpleDateFormat sd = new SimpleDateFormat(format);
		String result = "";
		try{
			long nd = 1000 * 24 * 60 * 60;//一天的毫秒数
			long nh = 1000 * 60 * 60;//一小时的毫秒数
			long nm = 1000 * 60;//一分钟的毫秒数
			long diff = sd.parse(endTime).getTime() - sd.parse(startTime).getTime();
			long day = diff / nd;//计算差多少天
			long hour = diff % nd / nh;//计算差多少小时
			long min = diff % nd % nh / nm;//计算差多少分钟
			long sec = diff % nd % nh % nm / 1000;//计算差多少秒
			
			if(day/30 > 0 ){
				result += (day/30) + "月";
			}
			if(day > 0 ){
				result += day + "天";
			}
			if(hour>0){
				result +=  hour + "小时";
			}
			if(min>0){
				result += min + "分钟";
			}
			if(sec>0){
				result += sec + "秒";
			}
		}catch(Exception e){
			return "";
		}
		
		return "".equals(result) ?  "已结束" : result;
	}
	
	public static boolean compareTargetDate(Date target,String begin,String end){
		//格式化时间 暂时不考虑传入参数的判断，其他地方如果要调用，最好扩展判断一下入参问题
		String targetTime = dateToDateString(target,DATATIMEF_STR);//HH:mm:ss
		return targetTime.compareTo(begin) >= 0 && end.compareTo(targetTime) >= 0;
	}

    public static String getCurDateFormatStr(String formatStr) {
        return dateToDateString(Calendar.getInstance().getTime(), formatStr);
    }

    /**
     * 获取前后日期，并指定时间格式
     * @param size
     * @param format
     * @return
     */
    public static String getDateUpDown(int size,String format){
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, size);
        date = calendar.getTime();
        return sdf.format(date);
    }
    
    /**
     * 昨天
     * @return
     */
    public static String getLastDate(int day){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, day);
        date = calendar.getTime();
        return sdf.format(date);
    }
    
    public static String getLastDate(int day,String format){
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, day);
        date = calendar.getTime();
        return sdf.format(date);
    }
    
    /**
     * 获取昨天的日期
     * @return
     */
    public static String getYesterDay(){
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		String yesterday = new SimpleDateFormat("yyyy-MM-dd ").format(cal
				.getTime());
		return yesterday;
    }
    
	public static String getCurDateyyyyMM() {
		return dateToDateString(Calendar.getInstance().getTime(), YYYYMM_STR);
	}
	
    
    public static String dateToDateString2(Date date){
        return dateToDateString(date, DATATIMEF_STR_SEC);
    }
    
	public static void main(String[] args) throws Exception {
	    Date date=new SimpleDateFormat("yyyy-MM-dd HH:MM:ss").parse("2016-08-31 12:32:10");
	    Date nextDate=daysAfter(date,5);
		System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:MM:ss").format(nextDate));
	}
	
}


