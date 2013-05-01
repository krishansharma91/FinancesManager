package com.liangge.financesmanager.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.*;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.liangge.financesmanager.R;
import com.liangge.financesmanager.adapter.MainPageViewAdapter;
import com.liangge.financesmanager.listener.MainGestureListener;
import com.liangge.financesmanager.utils.ConfigUtils;
import com.liangge.financesmanager.utils.DateTimeUtils;
import com.liangge.financesmanager.utils.FMUtils;
import com.liangge.financesmanager.utils.XmlConfig;
import com.liangge.financesmanager.widget.CustomLineChart;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends Activity {
    private long exitTime = 0;
    /*屏幕滑动事件处理*/
    private GestureDetector gestureDetector;
    /*滑屏列表*/
    private List<View> pageViewList = new ArrayList<View>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
//        /*初始化图表当天日期*/
//        CustomLineChart lineChart = (CustomLineChart) findViewById(R.id.index_linechart);
//        lineChart.setDayOfWeek(DateTimeUtils.getDayOfWeek(todayStr));
//        /*初始化当天日期*/
//        TextView indexNow = (TextView) findViewById(R.id.index_now);
//        indexNow.setText(todayStr);
//        XmlConfig dbConfig = ConfigUtils.getDbConfig(this);

        /*初始化view*/
        LayoutInflater flater = getLayoutInflater();
        for(int i=0;i<5;i++){
            View view = flater.inflate(R.layout.main_pageview, null);
            TextView textView = (TextView)view.findViewById(R.id.index_week);
            textView.setText( i * 100 + "");
            pageViewList.add(view);
        }

        MainPageViewAdapter pageViewAdapter = new MainPageViewAdapter(pageViewList);
        ViewPager viewPager = (ViewPager)findViewById(R.id.main_viewpager);
        viewPager.setAdapter(pageViewAdapter);
        viewPager.setCurrentItem(1);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i2) {
            }
            @Override
            public void onPageSelected(int i) {
            }
            @Override
            public void onPageScrollStateChanged(int i) {
            }
        });

        /*测试*/
        Button button = (Button)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                FMUtils.switchActivity(MainActivity.this, PayActivity.class);
            }
        });
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
