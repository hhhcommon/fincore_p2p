package com.zb.p2p.trade.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class WorkDateUtil {
    
    /**
     * 判断时间是否是工作日
     * @param d
     * @return
     */
    public static boolean isWork(Date d){
        if(d == null) return false;
        int weeks = DateUtil.dayForWeek(d); 
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        int month = calendar.get(Calendar.MONTH)+1;
        int day = calendar.get(Calendar.DATE);
        if(month == 2 && day > 14 && day < 22 ){ //春节
            return false;
        }else if(month == 4 && day > 4 && day < 8 ){ //清明
            return false;
        }else if((month == 4 && day == 29)|| (month == 4 && day == 30)||(month == 5 && day == 1)){ //劳动节
            return false;
        }else if(month == 6 && day >15 && day < 19){ //端午节
            return false;
        }else if(month == 9 && day >21 && day < 25){ //中秋节
            return false;
        }else if(month == 10 && day > 0 && day < 8){//国庆节
            return false;
        }else if(weeks == 6 || weeks == 7){//正常双休
            if((month == 2 && day == 11) || (month == 2 && day == 24)){ //春节补班
                return true;
            }else if(month == 4 && day == 8){ //清明补班
                return true;
            }else if(month == 4 && day == 28){ //劳动节补班
                return true;
            }else if((month == 9 && day == 29) || (month == 9 && day == 30)){//国庆节补班
                return true;
            }
            return false;
        }
        return true;
    }
    
    /**
     * 判断是否可以开户
     * @return 0可开 1不可开
     * @throws ParseException 
     */
    public static int curretOpen(Date d, Date beginTime,Date endTime) throws ParseException{
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        Date now = format.parse(format.format(d));
        
        Calendar date = Calendar.getInstance();
        date.setTime(now);

        Calendar begin = Calendar.getInstance();
        begin.setTime(beginTime);

        Calendar end = Calendar.getInstance();
        end.setTime(endTime);

        if (date.after(begin) && date.before(end)) {
            return 0;
        }
        return 1;
    }
}
