package com.liangge.financesmanager.listener;

import android.view.GestureDetector;
import android.view.MotionEvent;

/**
 * User: liangge
 * Date: 13-4-15
 * Time: 下午10:51
 */
public class MainGestureListener extends GestureDetector.SimpleOnGestureListener {
    /**
     * 滑动事件
     * @param e1 手指按上屏幕的起点
     * @param e2 的按上屏幕的终点
     * @param velocityX X轴每秒速度
     * @param velocityY Y轴每秒速度
     */
    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

        return super.onFling(e1, e2, velocityX, velocityY);
    }
}
