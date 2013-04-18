package com.liangge.financesmanager.listener;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.Toast;
import com.liangge.financesmanager.R;
import com.liangge.financesmanager.activity.MainActivity;
import com.liangge.financesmanager.utils.DateTimeUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.util.Date;

/**
 * User: liangge
 * Date: 13-4-15
 * Time: 下午10:51
 */
public class MainGestureListener extends GestureDetector.SimpleOnGestureListener {
    /*X方向滑动距离*/
    private float gestureInstanceX;
    /*Y方向滑动距离*/
    private float gestureInstanceY;

    private MainActivity context;
    public MainGestureListener(Context context){
        this.context = (MainActivity)context;
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        this.gestureInstanceX = windowManager.getDefaultDisplay().getWidth() / 8;
        this.gestureInstanceY = windowManager.getDefaultDisplay().getHeight() / 10;
    }
    /**
     * 滑动事件
     * @param e1 手指按上屏幕的起点
     * @param e2 的按上屏幕的终点
     * @param velocityX X轴每秒速度
     * @param velocityY Y轴每秒速度
     */
    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        float instanceX = e2.getX() - e1.getX();
        float instanceY = e2.getY() - e1.getY();

        /*向右滑，查第二天*/
        if(instanceX > gestureInstanceX){
            startSwapActivity(DateTimeUtils.getTomorrow(context.getTodayStr()));
            return true;
        }
        /*向下滑*/
        if(instanceY > gestureInstanceY){
            Toast.makeText(context, "down", Toast.LENGTH_SHORT).show();
            return true;
        }
        /*向左滑，查昨天*/
        if(Math.abs(instanceX) > gestureInstanceX){
            startSwapActivity(DateTimeUtils.getYesterday(context.getTodayStr()));
            return true;
        }
        /*向上滑，查今天*/
        if(Math.abs(instanceY) > gestureInstanceY){
            startSwapActivity(DateFormatUtils.format(new Date(), DateTimeUtils.DATA_FORMAT_PATTERN));
            return true;
        }

        return super.onFling(e1, e2, velocityX, velocityY);
    }

    /**
     * 切换屏幕
     * @param dateStr
     */
    private void startSwapActivity(String dateStr){
        Intent intent = new Intent(context, MainActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("date", dateStr);
        intent.putExtras(bundle);
        context.startActivity(intent);
        context.overridePendingTransition(R.anim.dissolve_in, R.anim.dissolve_out);
        context.finish();
    }
}
