package ebu9.util;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class DateUtile {
    /**
     * 时间戳转换成日期格式字符串
     *
     * @param seconds 精确到秒的字符串
     * @param format
     * @return
     */
    public static String timeStamp2Date(String seconds, String format) {
        if (seconds == null || seconds.isEmpty() || seconds.equals("null")) {
            return "";
        }
        if (format == null || format.isEmpty()) {
            format = "yyyy-MM-dd HH:mm:ss";
        }
        TimeZone timeZone = TimeZone.getTimeZone("GMT+8:00");
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        sdf.setTimeZone(timeZone);
        return sdf.format(new Date(Long.valueOf(seconds + "000")));
    }

    /**
     * 时间戳转换成日期格式字符串
     *
     * @param timestamp 精确到毫秒
     * @param format
     * @return
     */
    public static String timeStamp2Date2(long timestamp, String format) {
        TimeZone timeZone = TimeZone.getTimeZone("GMT+8:00");
        if (format == null || format.isEmpty()) {
            format = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        sdf.setTimeZone(timeZone);
        return sdf.format(new Date(timestamp));
    }


    /**
     * java 获取 获取某年某月 所有日期（yyyy-mm-dd格式字符串）
     *
     * @param year
     * @param month
     * @return
     */
    public static List<String> getMonthFullDay(int year, int month) {
        SimpleDateFormat dateFormatYYYYMMDD = new SimpleDateFormat("yyyy-MM-dd");
        List<String> fullDayList = new ArrayList<>(32);
        // 获得当前日期对象
        Calendar cal = Calendar.getInstance();
        cal.clear();// 清除信息
        cal.set(Calendar.YEAR, year);
        // 1月从0开始
        cal.set(Calendar.MONTH, month - 1);
        // 当月1号
        cal.set(Calendar.DAY_OF_MONTH, 1);
        int count = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        for (int j = 1; j <= count; j++) {
            fullDayList.add(dateFormatYYYYMMDD.format(cal.getTime()));
            cal.add(Calendar.DAY_OF_MONTH, 1);
        }
        return fullDayList;
    }


    /**
     * java 获取 获取某年某月 所有日期（yyyy-mm-dd格式字符串）
     *
     * @param year
     * @param month
     * @return
     */
    public static int getMonthDays(int year, int month) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, (month - 1));
        cal.set(Calendar.DATE, 1);
        cal.roll(Calendar.DATE, -1);
        return cal.getActualMaximum(Calendar.DATE);
    }


    /**
     * 返回 yyyyMMddhhmmssSss 格式的日期串
     *
     * @return
     */
    public static long getMothBegin(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        TimeZone timeZone = TimeZone.getTimeZone("GMT+8:00");
        calendar.setTimeZone(timeZone);
        calendar.set(Calendar.YEAR, year);
        // 1月从0开始
        calendar.set(Calendar.MONTH, month - 1);
        // 当月1号
        calendar.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号,当前日期既为本月第一天
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTimeInMillis();

    }


    /**
     * 获取指定日期所在月份开始的时间戳
     *
     * @param year
     * @param month
     * @return
     */
    public static Long getMonthEnd(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        // 1月从0开始
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        //将小时至23
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        //将分钟至59
        calendar.set(Calendar.MINUTE, 59);
        //将秒至59
        calendar.set(Calendar.SECOND, 59);
        //将毫秒至999
        calendar.set(Calendar.MILLISECOND, 999);

        // 获取本月第一天的时间戳
        return calendar.getTimeInMillis();
    }


    /**
     * 日期格式字符串转换成时间戳
     *
     * @param date_str 字符串日期
     * @param format   如：yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String date2TimeStamp(String date_str, String format) {
        try {
            TimeZone timeZone = TimeZone.getTimeZone("GMT+8:00");
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            sdf.setTimeZone(timeZone);
            return String.valueOf(sdf.parse(date_str).getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


    /**
     * 日期格式字符串转换成时间戳
     *
     * @param date_str 字符串日期
     * @param format   如：yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static long date2TimeStampLong(String date_str, String format) {
        try {
            TimeZone timeZone = TimeZone.getTimeZone("GMT+8:00");
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            sdf.setTimeZone(timeZone);
            return sdf.parse(date_str).getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0L;
    }

    /**
     * 取得当前时间戳（精确到秒）
     *
     * @return
     */
    public static String timeStamp() {
        long time = System.currentTimeMillis();
        String t = String.valueOf(time / 1000);
        return t;
    }

    /**
     * 获得东八区日期
     *
     * @return
     */
    public static String getChinaDate() {
        TimeZone timeZone = TimeZone.getTimeZone("GMT+8:00");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        simpleDateFormat.setTimeZone(timeZone);
        return simpleDateFormat.format(new Date());
    }

    /**
     * 获得东八区日期
     *
     * @return
     */
    public static String getChinaTime() {
        TimeZone timeZone = TimeZone.getTimeZone("GMT+8:00");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        simpleDateFormat.setTimeZone(timeZone);
        return simpleDateFormat.format(new Date());
    }

    /**
     * 获取每月多少天
     *
     * @param date
     * @return
     */
    public static int getDaysOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    /**
     * 时间戳转字符串时间
     */
    public static String[] timestampTostr(long starTime, long endTime) {
        String[] range = new String[2];
        range[0] = String.valueOf(new BigDecimal(starTime).divide(new BigDecimal(1000)).setScale(3));
        range[1] = String.valueOf(new BigDecimal(endTime).divide(new BigDecimal(1000)).setScale(3));
        return range;
    }

    /**
     * 将数值时间转换为常见的时间格式
     * 设定时区为北京时间
     *
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String timesToBjStr(String time) {
        BigDecimal b = new BigDecimal(time);
        BigDecimal b1 = b.multiply(new BigDecimal(1000)).setScale(0);
        Date date = new Date(b1.longValue());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        return sdf.format(date);
    }


    // /**
    //  * 获取两个时间节点之间的月份列表
    //  **/
    // public static Set<Integer> getMonthBetween(long t1, long t2) {
    //     Set<Integer> result = new HashSet<>();
    //     Calendar min = Calendar.getInstance();
    //     Calendar max = Calendar.getInstance();
    //     TimeZone timeZone = TimeZone.getTimeZone("GMT+8:00");
    //     min.setTimeZone(timeZone);
    //     max.setTimeZone(timeZone);
    //     min.setTimeInMillis(t1);
    //     max.setTimeInMillis(t2);
    //     result.add(min.get(Calendar.MONTH));
    //     result.add(max.get(Calendar.MONTH));
    //     while (min.before(max)) {
    //         result.add(min.get(Calendar.MONTH));
    //         min.add(Calendar.MONTH, 1);
    //     }
    //     return result;
    // }


    /**
     * 获取两个时间节点之间的月份列表
     **/
    public static List<String> getMonthBetween(long t1, long t2) {
        List<String> result = new ArrayList<>();
        //格式化为年月
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        Calendar min = Calendar.getInstance();
        Calendar max = Calendar.getInstance();
        TimeZone timeZone = TimeZone.getTimeZone("GMT+8:00");
        min.setTimeZone(timeZone);
        max.setTimeZone(timeZone);
        min.setTimeInMillis(t1);
        max.setTimeInMillis(t2);
        result.add(sdf.format(min.getTime()));
        result.add(sdf.format(max.getTime()));
        while (min.before(max)) {
            result.add(sdf.format(min.getTime()));
            min.add(Calendar.MONTH, 1);
        }
        return result;
    }

    /**
     * 获取过去一年时间的月份列表
     **/
    public static List<String> getYearMonthBetween(long t1) {
        List<String> result = new ArrayList<>();
        //格式化为年月
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        Calendar max = Calendar.getInstance();
        TimeZone timeZone = TimeZone.getTimeZone("GMT+8:00");
        max.setTimeZone(timeZone);
        max.setTimeInMillis(t1);
        Calendar min = Calendar.getInstance();
        min.setTimeInMillis(t1);
        min.setTimeZone(timeZone);
        min.add(Calendar.YEAR, -1);
        while (min.before(max)) {
            min.add(Calendar.MONTH, 1);
            result.add(sdf.format(min.getTime()));
        }
        return result;
    }

    /**
     * 获取两个时间节点之间的星期列表
     **/
    public static Set<Integer> getWeekBetween(long t1, long t2) {
        Set<Integer> result = new HashSet<>();
        Calendar min = Calendar.getInstance();
        Calendar max = Calendar.getInstance();
        TimeZone timeZone = TimeZone.getTimeZone("GMT+8:00");
        min.setTimeZone(timeZone);
        max.setTimeZone(timeZone);
        min.setTimeInMillis(t1);
        max.setTimeInMillis(t2);
        result.add(min.get(Calendar.DAY_OF_WEEK));
        result.add(max.get(Calendar.DAY_OF_WEEK));
        while (min.before(max)) {
            result.add(min.get(Calendar.DAY_OF_WEEK));
            min.add(Calendar.DAY_OF_WEEK, 1);
        }
        return result;
    }


    /**
     * 返回 yyyyMMddhhmmssSss 格式的日期串
     *
     * @return
     */
    public static String getyyyyMMddhhmmssSssDateString() {
        DateFormat dateFormat2 = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        return dateFormat2.format(new Date());
    }

    /**
     * 获取当月开始时间戳
     *
     * @param timeStamp 毫秒级时间戳
     * @return 时间戳
     */
    public static Long getMonthStartTime(Long timeStamp) {
        Calendar calendar = Calendar.getInstance();// 获取当前日期
        calendar.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        calendar.setTimeInMillis(timeStamp);
        calendar.add(Calendar.YEAR, 0);
        calendar.add(Calendar.MONTH, 0);
        calendar.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号,当前日期既为本月第一天
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }

    /**
     * 获取当月的结束时间戳
     *
     * @param timeStamp 毫秒级时间戳
     * @return 时间戳
     */
    public static Long getMonthEndTime(Long timeStamp) {
        Calendar calendar = Calendar.getInstance();// 获取当前日期
        calendar.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        calendar.setTimeInMillis(timeStamp);
        calendar.add(Calendar.YEAR, 0);
        calendar.add(Calendar.MONTH, 0);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));// 获取当前月最后一天
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTimeInMillis();
    }

    /**
     * 获取上个月开始时间戳
     *
     * @param timeStamp 毫秒级时间戳
     * @return 时间戳
     */
    public static Long getLastMonthStartTime(Long timeStamp) {
        Calendar calendar = Calendar.getInstance();// 获取当前日期
        calendar.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        calendar.setTimeInMillis(timeStamp);
        calendar.add(Calendar.YEAR, 0);
        calendar.add(Calendar.MONTH, -1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }

    /**
     * 获取当前月的第一天
     *
     * @param timeStamp 时间戳
     * @return 毫秒值
     */
    public static Long getNowMonthStartTime(Long timeStamp) {
        Calendar calendar = Calendar.getInstance();// 获取当前日期
        calendar.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        calendar.setTimeInMillis(timeStamp);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }

    /**
     * 获取上个月的结束时间戳
     *
     * @param timeStamp 毫秒级时间戳
     * @return 时间戳
     */
    public static Long getLastMonthEndTime(Long timeStamp) {
        Calendar calendar = Calendar.getInstance();// 获取当前日期
        calendar.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        calendar.setTimeInMillis(timeStamp);
        calendar.add(Calendar.YEAR, 0);
        calendar.add(Calendar.MONTH, 0);
        calendar.set(Calendar.DAY_OF_MONTH, 0);// 获取当前月最后一天
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTimeInMillis();
    }


    /**
     * 根据当前时间获取这个月的天数
     *
     * @param time 当前时间
     * @return 天数
     */
    public static int getDayOfMonth(Long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        calendar.setTimeInMillis(time);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    /**
     * 通过时间秒毫秒数判断两个时间的间隔
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return 天数
     */
    public static int differentDaysByMillisecond(Long startTime, Long endTime) {
        Date startDate = new Date(startTime);
        Date endDate = new Date(endTime);
        return (int) ((endDate.getTime() - startDate.getTime()) / (1000 * 3600 * 24));
    }

    /**
     * 根据小时和分钟获取时间
     *
     * @param hour   小时
     * @param minute 分钟
     * @return Date
     */
    public static Date getTime(int hour, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        calendar.set(Calendar.HOUR, hour);
        calendar.set(Calendar.MINUTE, minute);
        return calendar.getTime();
    }

    /**
     * 两个日期相差的天数
     *
     * @param startTime
     * @param endTime
     * @return
     */
    public static int daysBetween2(String startTime, String endTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        long time1 = 0;
        long time2 = 0;
        try {
            cal.setTime(sdf.parse(startTime));
            time1 = cal.getTimeInMillis();
            cal.setTime(sdf.parse(endTime));
            time2 = cal.getTimeInMillis();
        } catch (Exception e) {
            e.printStackTrace();
        }
        long between_days = (time2 - time1) / (1000 * 60);
        return Integer.parseInt(String.valueOf(between_days));
    }

    /**
     * 比较两个日期大小,d1>d2赋值1,	d1<d2赋值-1,	d1=d2赋值0
     *
     * @param DATE1
     * @param DATE2
     * @return
     */
    public static int compare_date(String DATE1, String DATE2) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date dt1 = df.parse(DATE1);
            Date dt2 = df.parse(DATE2);
            if (dt1.getTime() > dt2.getTime()) {
                return 1;
            } else if (dt1.getTime() < dt2.getTime()) {
                return -1;
            } else {
                return 0;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return 0;
    }

}
