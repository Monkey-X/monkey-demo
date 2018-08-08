package com.example.xlc.monkey.utils;

import android.app.AlarmManager;
import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * created by xlc at 2018/8/8
 * 时间处理工具类
 */
public class DateTimeUtil {


    private static final Pattern datePattern = Pattern.compile(
            "([0-9]{4})-([0-9]{2})-([0-9]{2})[\\s]+([0-9]{2}):([0-9]{2}):([0-9]{2})");


    /**
     * 日期的格式
     **/
    public static final String DF_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    public static final String DF_YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";

    public static final String DF_YYYY_MM_DD = "yyyy-MM_dd";

    public static final String DF_HH_MM_SS = "HH:mm:ss";

    public static final String DF_HH_MM = "HH:mm";


    private final static ThreadLocal<SimpleDateFormat> YYYYMMDDHHMMSS = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat(DF_YYYY_MM_DD_HH_MM_SS, Locale.getDefault());
        }
    };


    private final static ThreadLocal<SimpleDateFormat> YYYYMMDDHHMM = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat(DF_YYYY_MM_DD_HH_MM, Locale.getDefault());
        }
    };


    private final static ThreadLocal<SimpleDateFormat> YYYYMMDD = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat(DF_YYYY_MM_DD, Locale.getDefault());
        }
    };

    /**
     * 格式化日期
     * transform date to string that's type like YYYY-MM-DD HH:mm:ss
     *
     * @param datel
     * @return
     */
    public static String formatDateTime(long datel) {
        SimpleDateFormat dateFormat = YYYYMMDDHHMMSS.get();
        Date date = new Date(datel);
        return dateFormat.format(date);
    }


    /**
     * dateString that's type like YYYY-MM-DD HH:mm:ss
     * 将日期字符串转为日期类型
     */
    public static Date toDate(String date) {

        return toDate(date, YYYYMMDDHHMMSS.get());
    }

    public static Date toDate(String date, SimpleDateFormat format) {
        try {
            return format.parse(date);
        } catch (ParseException e) {

            return null;
        }
    }

    /**
     * transform date to string that's type like YYYY-MM-DD HH:mm
     *
     * @param sdate
     * @return
     */
    public static String getDateString(String sdate) {
        if (TextUtils.isEmpty(sdate)) {
            return "";
        }
        try {
            return YYYYMMDDHHMM.get().format(toDate(sdate));
        } catch (Exception e) {
            return sdate;
        }

    }


    /**
     * YYYY-MM-DD HH:mm:ss格式的时间字符串转换为{@link Calendar}类型
     *
     * @param str
     * @return
     */
    public static Calendar parseCalendar(String str) {
        Matcher matcher = datePattern.matcher(str);
        Calendar calendar = Calendar.getInstance();
        if (!matcher.find()) {
            return null;
        }

        calendar.set(
                matcher.group(1) == null ? 0 : toInt(matcher.group(1)),
                matcher.group(2) == null ? 0 : toInt(matcher.group(2)),
                matcher.group(3) == null ? 0 : toInt(matcher.group(3)),
                matcher.group(4) == null ? 0 : toInt(matcher.group(4)),
                matcher.group(5) == null ? 0 : toInt(matcher.group(5)),
                matcher.group(6) == null ? 0 : toInt(matcher.group(6))
        );
        return calendar;
    }


    /**
     * 字符串整数转整数
     *
     * @param obj
     * @return
     */
    public static int toInt(Object obj) {
        if (obj == null) {
            return 0;
        }
        return toInt(obj.toString(), 0);
    }


    public static int toInt(String str, int defValue) {

        try {
            return Integer.parseInt(str);
        } catch (Exception e) {

        }
        return defValue;
    }


    /**
     * YYYY-MM-DD HH:mm:ss格式的时间字符串转换为2018年8月8日类型的字符串
     *
     * @param str
     * @return
     */
    public static String formatYearMonthDay(String str) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }

        Matcher matcher = datePattern.matcher(str);
        if (!matcher.find()) {
            return str;
        }

        return String.format("%s年%s月%s日",
                matcher.group(1) == null ? 0 : matcher.group(1),
                matcher.group(2) == null ? 0 : matcher.group(2),
                matcher.group(3) == null ? 0 : matcher.group(3));
    }

    /**
     * YYYY-MM-DD HH:mm:ss格式的时间字符串转换为2018/8/8类型的字符串
     *
     * @param str
     * @return
     */
    public static String formatYearMonthDayNew(String str) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }

        Matcher matcher = datePattern.matcher(str);
        if (!matcher.find()) {
            return str;
        }

        return String.format("%s/%s/%s",
                matcher.group(1) == null ? 0 : matcher.group(1),
                matcher.group(2) == null ? 0 : matcher.group(2),
                matcher.group(3) == null ? 0 : matcher.group(3));
    }


    /**
     * @param sdate YYYY-MM-DD HH:mm:ss
     * @return n分钟前, n小时前, 昨天, 前天, n天前, n个月前
     */
    public static String formatSomeAgo(String sdate) {
        if (sdate == null) {
            return "";
        }
        Calendar calendar = parseCalendar(sdate);
        if (calendar == null) {
            return sdate;
        }

        Calendar currentDate = Calendar.getInstance();
        long crim = currentDate.getTimeInMillis();//current
        long trim = calendar.getTimeInMillis();//target
        long diff = crim - trim;

        int year = currentDate.get(Calendar.YEAR);
        int month = currentDate.get(Calendar.MONTH);
        int day = currentDate.get(Calendar.DATE);

        if (diff < 60 * 1000) {
            return "刚刚";
        }

        if (diff >= 60 * 1000 && diff < AlarmManager.INTERVAL_HOUR) {
            return String.format("%s分钟前", diff / 60 / 1000);
        }

        currentDate.set(year, month, day, 0, 0, 0);
        if (trim >= currentDate.getTimeInMillis()) {
            return String.format("%s小时前", diff / AlarmManager.INTERVAL_HOUR);
        }

        currentDate.set(year, month, day - 1, 0, 0, 0);
        if (trim >= currentDate.getTimeInMillis()) {
            return "昨天";
        }

        currentDate.set(year, month, day - 2, 0, 0, 0);
        if (trim >= currentDate.getTimeInMillis()) {
            return "前天";
        }

        if (diff < AlarmManager.INTERVAL_DAY * 30) {
            return String.format("%s天前", diff / AlarmManager.INTERVAL_DAY);
        }
        if (diff < AlarmManager.INTERVAL_DAY * 30 * 12) {
            return String.format("%s月前", diff / (AlarmManager.INTERVAL_DAY * 30));
        }
        return String.format("%s年前", currentDate.get(Calendar.YEAR) - calendar.get(Calendar.YEAR));
    }


    /**
     * @param str YYYY-MM-DD HH:mm:ss string
     * @return 今天, 昨天, 前天, n天前
     */
    public static String formatSomeDay(String str) {
        return formatSomeDay(parseCalendar(str));
    }

    /**
     * @param calendar
     * @return 今天, 昨天, 前天, n天前
     */
    public static String formatSomeDay(Calendar calendar) {
        if (calendar == null) {
            return "?天前";
        }

        Calendar mCurrentDate = Calendar.getInstance();
        long crim = mCurrentDate.getTimeInMillis();
        long trim = calendar.getTimeInMillis();
        long diff = crim - trim;
        int year = mCurrentDate.get(Calendar.YEAR);
        int month = mCurrentDate.get(Calendar.MONTH);
        int day = mCurrentDate.get(Calendar.DATE);

        mCurrentDate.set(year, month, day, 0, 0, 0);
        if (trim >= mCurrentDate.getTimeInMillis()) {
            return "今天";
        }
        mCurrentDate.set(year, month, day - 1, 0, 0, 0);
        if (trim >= mCurrentDate.getTimeInMillis()) {
            return "昨天";
        }
        mCurrentDate.set(year, month, day - 2, 0, 0, 0);
        if (trim >= mCurrentDate.getTimeInMillis()) {
            return "前天";
        }
        return String.format("%s天前", diff / AlarmManager.INTERVAL_DAY);
    }


    /**
     * @param calendar
     * @return 星期几
     */
    public static String formatWeek(Calendar calendar) {
        if (calendar == null) {
            return "星期？";
        }
        return new String[]{"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"}[calendar.get(Calendar.DAY_OF_WEEK) - 1];
    }

    /**
     * @param str YYYY-MM-DD HH:mm:ss string
     * @return 星期n
     */
    public static String formatWeek(String str) {
        return formatWeek(parseCalendar(str));
    }

    /**
     * @param sdate YYYY-MM-DD HH:mm:ss string
     * @return
     */
    public static String formatDayWeek(String sdate) {
        Calendar calendar = parseCalendar(sdate);
        if (calendar == null)
            return "??/?? 星期?";
        Calendar mCurrentDate = Calendar.getInstance();
        String ws = formatWeek(calendar);
        int diff = mCurrentDate.get(Calendar.DATE) - calendar.get(Calendar.DATE);
        if (diff == 0) {
            return "今天 / " + ws;
        }
        if (diff == 1) {
            return "昨天 / " + ws;
        }
        int m = calendar.get(Calendar.MONTH);
        int d = calendar.get(Calendar.DATE);
        return String.format("%s/%s / %s", formatInt(m), formatInt(d), ws);
    }

    /**
     * format to HH
     *
     * @param i integer
     * @return HH
     */
    public static String formatInt(int i) {
        return (i < 10 ? "0" : "") + i;
    }

    /**
     * 智能格式化
     */
    public static String friendly_time3(String sdate) {
        Calendar calendar = parseCalendar(sdate);
        if (calendar == null) return sdate;

        Calendar mCurrentDate = Calendar.getInstance();
        SimpleDateFormat formatter = YYYYMMDDHHMMSS.get();

        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        String s = hour >= 0 && hour < 12 ? "上午" : "下午";
        s += " HH:mm";

        if (mCurrentDate.get(Calendar.DATE) == calendar.get(Calendar.DATE)) {
            formatter.applyPattern(s);
        } else if (mCurrentDate.get(Calendar.DATE) - calendar.get(Calendar.DATE) == 1) {
            formatter.applyPattern("昨天 " + s);
        } else if (mCurrentDate.get(Calendar.YEAR) == calendar.get(Calendar.YEAR)) {
            formatter.applyPattern("MM-dd " + s);
        } else {
            formatter.applyPattern("yyyy-MM-dd " + s);
        }
        return formatter.format(calendar.getTime());
    }

    /**
     * 判断给定字符串时间是否为今日
     *
     * @param sdate
     * @return boolean
     */
    public static boolean isToday(String sdate) {
        Date time = toDate(sdate);
        Date today = new Date();
        if (time != null) {
            String nowDate = YYYYMMDD.get().format(today);
            String timeDate = YYYYMMDD.get().format(time);
            if (nowDate.equals(timeDate)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 计算两个时间差，返回的是的秒s
     * @param date1
     * @param date2
     * @return
     */
    public static long calDateDifferent(String date1, String date2) {
        try {
            Date d1 = YYYYMMDDHHMMSS.get().parse(date1);
            Date d2 = YYYYMMDDHHMMSS.get().parse(date2);
            // 毫秒ms
            long diff = d2.getTime() - d1.getTime();
            return diff / 1000;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }


    /**
     * 获取当前时间为每年第几周
     *
     * @return
     */
    public static int getWeekOfYear() {
        return getWeekOfYear(new Date());
    }

    /**
     * 获取当前时间为每年第几周
     *
     * @param date
     * @return
     */
    public static int getWeekOfYear(Date date) {
        Calendar c = Calendar.getInstance();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.setTime(date);
        int week = c.get(Calendar.WEEK_OF_YEAR) - 1;
        week = week == 0 ? 52 : week;
        return week > 0 ? week : 1;
    }
}
