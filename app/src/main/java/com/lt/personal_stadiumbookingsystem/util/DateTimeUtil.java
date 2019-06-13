package com.lt.personal_stadiumbookingsystem.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @作者: LinTan
 * @日期: 2019/5/1 20:57
 * @版本: 1.0
 * @描述: //日期时间的工具类。
 * 1.0: Initial Commit
 */

public class DateTimeUtil {
    private static Calendar calendar = Calendar.getInstance();
    private static final String FORMAT_DATE = "yyyy-MM-dd";
    private static final String FORMAT_TIME = "HH:mm:ss";//HH是24小时制
    private static final String FORMAT_DATE_TIME = "yyyy-MM-dd HH:mm:ss";//HH是24小时制

    private DateTimeUtil() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    public static String getDate() {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(FORMAT_DATE);
        String stringDate = simpleDateFormat.format(date);
        return stringDate;
    }//获取日期

    public static String getDate(int offsetDay) {
        String stringDate = null;
        try {
            Date date = new Date();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(FORMAT_DATE);
            String currentDate = simpleDateFormat.format(date);
            calendar.setTime(simpleDateFormat.parse(currentDate));
            calendar.add(Calendar.DAY_OF_YEAR, offsetDay);
            stringDate = simpleDateFormat.format(calendar.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return stringDate;
    }//获取日期，并添加偏移量。eg: offsetDay为1，即得到明天的日期，offsetDay为-1，即得到昨天的日期，

    public static String getTime() {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(FORMAT_TIME);
        String stringTime = simpleDateFormat.format(date);
        return stringTime;
    }//获取时间

    public static String getDateTime() {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(FORMAT_DATE_TIME);
        String stringDateTime = simpleDateFormat.format(date);
        return stringDateTime;
    }//获取日期和时间

    public static String getDayOfWeek(int offsetDay) {
        int index = getCurrentDayOfWeek() + offsetDay;
        if (index < 0) {
            index = 7 - index * -1 % 7;
        }//规范索引的范围
        String[] strings = {"日", "一", "二", "三", "四", "五", "六"};
        String stringDay = strings[index];
        return stringDay;
    }//获取星期

    public static int getCurrentYear() {
        int index = calendar.get(Calendar.YEAR);
        return index;
    }//获取当前的年数

    public static int getCurrentMonth() {
        int indexTemp = calendar.get(Calendar.MONTH);
        int index = indexTemp + 1;
        return index;
    }//获取今年的月数

    public static int getCurrentDayOfMonth() {
        int index = calendar.get(Calendar.DAY_OF_MONTH);
        return index;
    }//获取本月的天数

    public static int getCurrentDayOfWeek() {
        int indexTemp = calendar.get(Calendar.DAY_OF_WEEK);//注意，calendar.get返回的是数组中元素的索引值，而不是元素值
        int index = indexTemp - 1;//Java中一周的第一天是周日，eg: [周日, 周一, 周二, 周三, 周四, 周五, 周六]
        return index;
    }//获取本周的天数

    public static int getCurrentHour() {
        int index = calendar.get(Calendar.HOUR_OF_DAY);
        return index;
    }//获取今天的小时数

    public static int getCurrentMinute() {
        int index = calendar.get(Calendar.MINUTE);
        return index;
    }//获取该小时的分数

    public static int getCurrentSecond() {
        int index = calendar.get(Calendar.SECOND);
        return index;
    }//获取该分钟的秒数
}
