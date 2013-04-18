package com.liangge.financesmanager.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.widget.TextView;
import android.widget.Toast;
import com.liangge.financesmanager.R;
import com.liangge.financesmanager.listener.MainGestureListener;
import com.liangge.financesmanager.utils.ConfigUtils;
import com.liangge.financesmanager.utils.DateTimeUtils;
import com.liangge.financesmanager.utils.XmlConfig;
import com.liangge.financesmanager.widget.CustomLineChart;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.util.Date;

public class MainActivity extends Activity {
    private long exitTime = 0;
    /*屏幕滑动事件处理*/
    private GestureDetector gestureDetector;
    /*当天时间参数 yyyy-MM-dd*/
    private String todayStr;
    public String getTodayStr() {
        return todayStr;
    }
    public void setTodayStr(String todayStr) {
        this.todayStr = todayStr;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        /*获取参数*/
        Bundle bundle = getIntent().getExtras();
        if(bundle != null && bundle.containsKey("date")){
            setTodayStr(bundle.getString("date"));
        }else{
            setTodayStr(DateFormatUtils.format(new Date(), DateTimeUtils.DATA_FORMAT_PATTERN));
        }
        /*初始化滑动手势事件*/
        gestureDetector = new GestureDetector(this, new MainGestureListener(this));
        /*初始化图表当天日期*/
        CustomLineChart lineChart = (CustomLineChart) findViewById(R.id.index_linechart);
        lineChart.setDayOfWeek(DateTimeUtils.getDayOfWeek(todayStr));
        /*初始化当天日期*/
        TextView indexNow = (TextView) findViewById(R.id.index_now);
        indexNow.setText(todayStr);
//        XmlConfig dbConfig = ConfigUtils.getDbConfig(this);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
            long now = System.currentTimeMillis();
            if(now - exitTime > 2000){
                Toast.makeText(getApplicationContext(), R.string.app_exit_tips, Toast.LENGTH_SHORT).show();
                exitTime = now;
            }else{
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }
}
