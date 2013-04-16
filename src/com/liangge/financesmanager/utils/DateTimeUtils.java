package com.liangge.financesmanager.utils;

import java.util.Calendar;

/**
 * User: liangge
 * Date: 13-4-16
 * Time: 下午10:46
 */
public class DateTimeUtils {
    /**
     * 获取日期中的日期
     * @return 0-6
     */
    public static int getDayOfWeek(){
        Calendar cl = Calendar.getInstance();
        return cl.get(Calendar.DAY_OF_WEEK) - 1;
    }
}
