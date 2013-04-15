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
import com.liangge.financesmanager.utils.XmlConfig;

public class MainActivity extends Activity {
    private long exitTime = 0;
    /*屏幕滑动事件处理*/
    private GestureDetector gestureDetector = new GestureDetector(this, new MainGestureListener());

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        TextView text = (TextView)findViewById(R.id.index_now);
        XmlConfig dbConfig = ConfigUtils.getDbConfig(this);
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
