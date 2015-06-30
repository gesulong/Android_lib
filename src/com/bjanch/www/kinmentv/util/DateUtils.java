package com.bjanch.www.kinmentv.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

//import java.util.Calendar;

public class DateUtils {
    //	private final static Calendar c = Calendar.getInstance();
    private static TimeZone timezone = TimeZone.getTimeZone("GMT+8:00");

    public static String getDate() {
        Date d = new Date();
        SimpleDateFormat sf = new SimpleDateFormat("MM月dd日(E)", Locale.CHINA);
        sf.setTimeZone(timezone);
        return sf.format(d);
    }

    public static String getDate(long time) {
        Date d = new Date(time);
        SimpleDateFormat sf = new SimpleDateFormat("MM月dd日(E)", Locale.CHINA);
        sf.setTimeZone(timezone);
        return sf.format(d);
    }

    public static String getDateTwa(long time) {
        Date d = new Date(time);
        SimpleDateFormat sf = new SimpleDateFormat("yyyy年MM月dd日  (E) HH:mm", Locale.TAIWAN);
        sf.setTimeZone(timezone);
        return sf.format(d);
    }

    /* 获取系统时间 格式为："yyyy/MM/dd " */
    public static String getCurrentDate() {
        Date d = new Date();
        SimpleDateFormat sf = new SimpleDateFormat("yyyy年MM月dd日", Locale.CHINA);
        sf.setTimeZone(timezone);
        return sf.format(d);
    }

    /* 时间戳转换成字符窜 */
    public static String getDateToString(long time) {
        Date d = new Date(time);

        SimpleDateFormat sf = new SimpleDateFormat("yyyy年MM月dd日", Locale.CHINA);
        sf.setTimeZone(timezone);
        return sf.format(d);
    }

    /* 将字符串转为时间戳 */
    public static long getStringToDate(String time) {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy年MM月dd日", Locale.CHINA);
        sf.setTimeZone(timezone);
        Date date = new Date();
        try {
            date = sf.parse(time);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return date.getTime();
    }

    public static String getCurTime() {
        SimpleDateFormat sf = new SimpleDateFormat("HH:mm", Locale.CHINA);
        sf.setTimeZone(timezone);
        Date curDate = new Date(System.currentTimeMillis());

        return sf.format(curDate);
    }

    public static String getCurTime(long systime) {
        SimpleDateFormat sf = new SimpleDateFormat("HH:mm", Locale.CHINA);
        sf.setTimeZone(timezone);
        Date curDate = new Date(systime);

        return sf.format(curDate);
    }

    /**
     * 获取当前时间戳
     *
     * @return
     */
    public static Long getCurUnixTime() {
        return System.currentTimeMillis();
    }

    /**
     * 获取推送下载开始时间
     */
    public static long getDownLoadStartTime() {
        return getStringToDate(getCurrentDate()) + 21 * 3600 * 1000;
    }

    /**
     * 获取推送下载结束时间
     */
    public static long getDownLoadStopTime() {
        return getDownLoadStartTime() + 12 * 3600 * 1000;
    }

    /**
     * Convert time to a string
     *
     * @param millis
     *            e.g.time/length from file
     * @return formated string (hh:)mm:ss
     */
    public static String millisToString(int millis) {
        boolean negative = millis < 0;
        millis = Math.abs(millis);

        millis /= 1000;
        int sec = millis % 60;
        millis /= 60;
        int min = millis % 60;
        millis /= 60;
        int hours = millis;

        String time;
        // DecimalFormat format = new DecimalFormat("00");

        time = (negative ? "-" : "") + String.format("%02d", hours) + ":"
                + String.format("%02d", min) + ":" + String.format("%02d", sec);

        // if (millis > 0) {
        // time = (negative ? "-" : "") + hours + ":" + format.format(min)
        // + ":" + format.format(sec);
        // } else {
        // time = (negative ? "-" : "") + min + ":" + format.format(sec);
        // }
        return time;
    }

}
