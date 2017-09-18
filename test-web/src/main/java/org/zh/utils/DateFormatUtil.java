/**
 *
 */
package org.zh.utils;

import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 提供日期相关的工具类
 *
 * @author lishaoqing
 */
public class DateFormatUtil {

    /**
     * 使用指定格式格式化Date为字符串。 格式：yyyy-MM-dd HH:mm:ss
     *
     * @param date
     * @return
     */
    public static String dateToStr(Date date) {
        if (date == null) return null;
        SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleFormat.format(date);
    }

    /**
     * 使用指定格式格式化字符串为Date。   格式：yyyy-MM-dd HH:mm
     *
     * @param dateStr
     * @return
     */
    public static Date strToDate(String dateStr) {
        Date date = null;
        SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            date = simpleFormat.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();  //TODO: change body of catch statement.
        }
        return date;
    }

    public static Date formatToDate(String dateStr, String format) {
        if(StringUtils.isBlank(format)) format = "yyyy-MM-dd HH:mm";
        Date date = null;
        SimpleDateFormat simpleFormat = new SimpleDateFormat(format);
        try {
            date = simpleFormat.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
    /**
     * 根据指定格式，格式化当前日期
     *
     * @param format
     * @return
     */
    public static String currentDateFormat(String format) {
        if (StringUtils.isBlank(format)) format = "yyyyMMdd";
        SimpleDateFormat simpleFormat = new SimpleDateFormat(format);
        return simpleFormat.format(new Date());
    }

    public static boolean isSameDay(Date d,Date date) {
        Calendar calDateA = Calendar.getInstance();
        calDateA.setTime(d);

        Calendar calDateB = Calendar.getInstance();
        calDateB.setTime(date);

        return calDateA.get(Calendar.YEAR) == calDateB.get(Calendar.YEAR)
                && calDateA.get(Calendar.MONTH) == calDateB.get(Calendar.MONTH)
                &&  calDateA.get(Calendar.DAY_OF_MONTH) == calDateB.get(Calendar.DAY_OF_MONTH);
    }
}
