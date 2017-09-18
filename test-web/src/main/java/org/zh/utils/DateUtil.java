package org.zh.utils;


/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company:
 * </p>
 *
 * @author Miller
 * @version 0.6
 */

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtil {

    private final static String shortDateFormatPatten = "yyyy/MM/dd";
    private final static String longDateFormatPatten = "yyyy/MM/dd HH:mm:ss:SS";
    private final static String shortDateFormatPatten2 = "yyyy-MM-dd";
    private final static String shortDateFormatPatten3 = "yyyy-MM-dd HH:mm:ss";
    private final static String shortDateFormatPatten4 = "yyyyMMddHHmmss";

    private final static String DateFormatPatten2 = "yyyy-MM";
    private final static String DateFormatPatten3 = "HH:mm";
    private final static String DateFormatPatten4 = "yyyy-MM-dd HH:mm";
    private final static String DateFormatPatten5 = "yyyyMMdd";
    private final static String DateFormatPatten6 = "yyyy-MM-dd HH:mm:ss";
    private final static String DateFormatPatten8 = "yyMMdd";

    public static Date getCurrentDate() {
        GregorianCalendar ca = new GregorianCalendar();
        return ca.getTime();
    }

    public static boolean checkAddict(String identityId) {
        try {
            String dateStr;
            boolean is18 = false;
            if(identityId.length() == 15) {
                dateStr = identityId.substring(6, 12);
            } else {
                dateStr = identityId.substring(6, 14);
                is18 = true;
            }

            DateFormat dateFormat = new SimpleDateFormat(is18 ? DateFormatPatten5 : DateFormatPatten8);
            Date fromDate = dateFormat.parse(dateStr);
            Date currentDate = new Date();
            Calendar fromCalendar = Calendar.getInstance();
            fromCalendar.setTime(fromDate);
            int fromYear = fromCalendar.get(Calendar.YEAR);
            int fromMonth = fromCalendar.get(Calendar.MONTH) + 1;
            int fromDay = fromCalendar.get(Calendar.DAY_OF_MONTH);

            Calendar currentCalendar = Calendar.getInstance();
            currentCalendar.setTime(currentDate);
            int currentYear = currentCalendar.get(Calendar.YEAR);
            int currentMonth = currentCalendar.get(Calendar.MONTH) + 1;
            int currentDay = currentCalendar.get(Calendar.DAY_OF_MONTH);

            int difYear = currentYear - fromYear;
            int difMonth = currentMonth - fromMonth;
            int difDay = currentDay - fromDay;

            if(difYear > 18 || (difYear == 18 && difMonth > 0)  || (difYear == 18 && difMonth == 0 && difDay >= 0)) {
                return true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return false;
    }

    public static Date parse2(String dateString) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat(shortDateFormatPatten2);
        return dateFormat.parse(dateString);
    }


    public static Date hourOffset(Date date, int offset) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.HOUR, offset);
        return c.getTime();
    }

    public static Date minOffset(Date date, int offset) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MINUTE, offset);
        return c.getTime();
    }

    public static boolean isLastDayOfMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        c.set(year, month + 1, 1);
        c.add(Calendar.DATE, -1);
        int maxDay = c.get(Calendar.DAY_OF_MONTH);

        return maxDay == day;
    }

    public static boolean isSameDate(Date d) {

        Calendar c = Calendar.getInstance();
        c.setTime(d);
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        Date date = new Date();
        Calendar currentDate = Calendar.getInstance();
        c.setTime(date);

        int currentYear = currentDate.get(Calendar.YEAR);
        int currentMonth = currentDate.get(Calendar.MONTH);
        int currentDay = currentDate.get(Calendar.DAY_OF_MONTH);

        if(currentYear > year || currentMonth > month || currentDay > day) return true;
        return false;
    }

    /**
     * 生命周期
     */
    public static long Interval(Date start, Date end) {
        // 计算离最后登录相差时闄1�7 几天 几小旄1�7
        long days = 0;
        long hours = 0;

        if (start != null && end != null) {
            long HOUR = 60L * 60L * 1000L;
            long allhours = (end.getTime() - start.getTime()) / HOUR;
            days = allhours / 24;
            hours = allhours - 24 * (days);
        }
        return days;
    }

}