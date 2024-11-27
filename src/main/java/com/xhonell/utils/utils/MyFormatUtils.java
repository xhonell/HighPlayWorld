package com.xhonell.utils.utils;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * day7.wk.MyFormatUtils
 * User: hly
 * Date: 2024/10/24 9:02
 * motto:   逆水行舟不进则退
 * Description:
 * Version: v1.0
 */
public class MyFormatUtils {
    private MyFormatUtils() {
    }
    /////////=============日期直接相互转换=====================///////////////////////////
    /**
     * 默认格式设置字段
     */
    private static final String PATTERN_DATE = "yyyy-MM-dd";
    private static final String PATTERN_DATE_TIME = "yyyy-MM-dd HH:mm:ss";
    private static final String PATTERN_DATE_CN = "yyyy年MM月dd日";
    private static final String PATTERN_DATE_TIME_CN = "yyyy年MM月dd日 HH:mm:ss";

    /**
     * 字符转换本地日期
     *
     * @param date
     * @return
     */
    public static LocalDate toLocalDate(String date) {
        return toLocalDate(date, PATTERN_DATE);
    }

    /**
     * 按指定格式转换为本地日期
     *
     * @param date
     * @param pattern
     * @return
     */
    public static LocalDate toLocalDate(String date, String pattern) {
        //把本地日期按指定格式转换为LocalDate
        //localDate.format()  把数据类型格式化成字符串
        //parse 把字符串格式化成XX对象
        return LocalDate.parse(date, DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * 按默认的 yyyy-MM-dd 格式转换为日期
     *
     * @param date
     * @return
     * @throws ParseException
     */
    public static Date toDate(String date) {
        try {
            return toDate(date, PATTERN_DATE);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 按指定格式转换为日期
     *
     * @param date
     * @param pattern
     * @return
     */
    public static Date toDate(String date, String pattern) throws ParseException {
        Date date2 = null;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        date2 = simpleDateFormat.parse(date);
        return date2;
    }

    /**
     * 按默认格式把日期解析成字符串
     *
     * @param date
     * @return
     * @throws ParseException
     */
    public static String toString(Date date) {
        return toString(date, PATTERN_DATE);
    }

    public static String toString(LocalDate date){
        return toString(date, PATTERN_DATE);
    }

    public static String toString(Date date, String pattern){
        return new SimpleDateFormat(pattern).format(date);
    }

    public static String toString(LocalDate date, String pattern) {
        return date.format(DateTimeFormatter.ofPattern(pattern));
    }
    //LocalDateTime  ->  Date
    // LocalDateTime  ->  String
    //.............................................

    /**
     * 日期转换本地日期
     *
     * @param date
     * @return
     */
    public static LocalDate dateToLocalDate(Date date) {
        if (date == null)
            return null;
//        //时间点
//        Instant instant = date.toInstant();
//        //时区
//        ZoneId zoneId = ZoneId.systemDefault();
//        // atZone()方法返回在指定时区从此Instant生成的ZonedDateTime。
//        return instant.atZone(zoneId).toLocalDate();
        return LocalDate.of(date.getYear(), date.getMonth() + 1, date.getDay());
    }

    /**
     * 本地日期转换为Date
     *
     * @param localDate
     * @return
     */
    public static Date localDateToDate(LocalDate localDate) {
    //        //时区
    //       ZoneId zoneId = ZoneId.systemDefault();
    //        // 获取时区时间
    //       ZonedDateTime zdt = localDate.atStartOfDay(zoneId);
    //        //时区转换为Date
    //      return Date.from(zdt.toInstant());

        if (localDate == null)
            return null;
        return new Date(localDate.getYear(), localDate.getMonth().getValue() - 1, localDate.getDayOfMonth());
    }
    /////////=============字符串转数字=====================///////////////////////////
    /**
     * 把字符串转换为 Integer  int
     *
     * @param number
     * @return
     */
    public static Integer toInteger(String number)  {
        try {
            return Integer.parseInt(number); // 格式化错误 应该返回  null
        }catch (Exception  e){
           return  null;
        }
    }

    static  public Double toDouble(String number) {//"A"
        try {
            return Double.parseDouble(number);
        }catch (Exception  e){
            return  null;
        }
    }


    /////////=============字符验证=====================///////////////////////////

    public boolean isBank(String str) {//  null ""  ""
        return str == null || str.trim().length() == 0;
    }

    /**
     * 如果字符串是空字符串返回null  如果不是就把字符串去掉收尾空格进行返回
     * 空的例如:
     * null
     * ""
     * "    "
     *
     * @param str
     * @return
     */
    public String trim(String str) {//
        return isBank(str) ? null : str.trim();
    }


}
