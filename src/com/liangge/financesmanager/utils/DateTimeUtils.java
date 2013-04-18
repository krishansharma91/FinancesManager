package com.liangge.financesmanager.utils;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

/**
 * User: liangge
 * Date: 13-4-16
 * Time: 下午10:46
 */
public class DateTimeUtils {
    public static final String DATA_FORMAT_PATTERN = "yyyy-MM-dd";
    /**
     * 获取日期中的日期
     * @return 0-6
     */
    public static int getDayOfWeek(String dateStr){
        Calendar cl = Calendar.getInstance();
        try {
            cl.setTime(DateUtils.parseDate(dateStr, DATA_FORMAT_PATTERN));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return cl.get(Calendar.DAY_OF_WEEK) - 1;
    }

    /**
     * 根据传入的yyyy-MM-dd获取昨天
     * @return yyyy-MM-dd
     */
    public static String getYesterday(String dateStr){
        String result = "";
        try {
            Date date = DateUtils.parseDate(dateStr, DATA_FORMAT_PATTERN);
            Date yesterday = DateUtils.addDays(date, -1);
            result = DateFormatUtils.format(yesterday, DATA_FORMAT_PATTERN);
        } catch (ParseException e) {
            e.printStackTrace();
            return dateStr;
        }
        return result;
    }
    /**
     * 根据传入的yyyy-MM-dd获取第二天
     * @return yyyy-MM-dd
     */
    public static String getTomorrow(String dateStr){
        String result = "";
        try {
            Date date = DateUtils.parseDate(dateStr, DATA_FORMAT_PATTERN);
            Date yesterday = DateUtils.addDays(date, 1);
            result = DateFormatUtils.format(yesterday, DATA_FORMAT_PATTERN);
        } catch (ParseException e) {
            e.printStackTrace();
            return dateStr;
        }
        return result;
    }
}
