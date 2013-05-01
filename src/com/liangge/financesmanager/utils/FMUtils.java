package com.liangge.financesmanager.utils;

import android.app.Activity;
import android.content.Intent;
import com.liangge.financesmanager.R;

/**
 * User: liangge
 * Date: 13-4-30
 * Time: 下午6:11
 */
public class FMUtils {
    /**
     * 切换屏幕
     * @param start
     * @param end
     */
    public static void switchActivity(Activity start, Class end){
        Intent intent = new Intent(start, end);
        start.startActivity(intent);
        start.finish();
    }
}
