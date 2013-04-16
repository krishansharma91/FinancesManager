package com.liangge.financesmanager.listener;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.Toast;

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

    private Context context;
    public MainGestureListener(Context context){
        this.context = context;
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

        /*向右滑*/
        if(instanceX > gestureInstanceX){
            Toast.makeText(context, "right", Toast.LENGTH_SHORT).show();
            return true;
        }
        /*向下滑*/
        if(instanceY > gestureInstanceY){
            Toast.makeText(context, "down", Toast.LENGTH_SHORT).show();
            return true;
        }
        /*向左滑*/
        if(Math.abs(instanceX) > gestureInstanceX){
            Toast.makeText(context, "left", Toast.LENGTH_SHORT).show();
            return true;
        }
        /*向上滑*/
        if(Math.abs(instanceY) > gestureInstanceY){
            Toast.makeText(context, "top", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onFling(e1, e2, velocityX, velocityY);
    }
}
