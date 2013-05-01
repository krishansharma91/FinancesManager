package com.liangge.financesmanager.activity;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.*;
import com.liangge.financesmanager.R;
import com.liangge.financesmanager.database.PayClassifyDB;
import com.liangge.financesmanager.database.TradeDB;
import com.liangge.financesmanager.utils.FMUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: liangge
 * Date: 13-4-29
 * Time: 下午3:43
 */
public class PayActivity extends Activity {
    private TradeDB db;
    private int selectClassifyId = -1;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay);
        db = new TradeDB(this);
        /*设置网格数据*/
        GridView gridView = (GridView)findViewById(R.id.pay_gridview);

        PayClassifyDB payClassifyDB = new PayClassifyDB(this);
        List<Map<String,Object>> gridItemList = new ArrayList<Map<String,Object>>();
        Cursor cursor = payClassifyDB.select();
        for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("id", cursor.getInt(0));
            map.put("pay_gridview_text", cursor.getString(1));
            gridItemList.add(map);
        }
        /*适配数据源*/
        SimpleAdapter adapter = new SimpleAdapter(this,
                gridItemList,
                R.layout.pay_gridview_item,
                new String[]{"pay_gridview_text"},
                new int[]{R.id.pay_gridview_text});
        gridView.setAdapter(adapter);
        /*网格点击事件*/
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            private int preventPosition = -1;

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (preventPosition != -1) {
                    View itemView = adapterView.getChildAt(preventPosition);
                    itemView.setBackgroundColor(Color.BLACK);
                }
                preventPosition = i;
                Map<String, Object> itemMap = (Map<String, Object>)adapterView.getItemAtPosition(i);
                PayActivity.this.selectClassifyId = (Integer)itemMap.get("id");
                view.setBackgroundColor(Color.YELLOW);
            }
        });
        /*确认按钮*/
        Button paySubmit = (Button)findViewById(R.id.pay_submit);
        paySubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*效验金额*/
                EditText payMoney = (EditText)findViewById(R.id.pay_money);
                float money = 0;
                try{
                    money = Float.parseFloat(payMoney.getText().toString());
                }catch(Exception e){}
                if(money == 0){
                    Toast.makeText(PayActivity.this, "金额输入错误", Toast.LENGTH_SHORT).show();
                    return;
                }
                /*效验类别*/
                int classifyId = PayActivity.this.selectClassifyId;
                if(classifyId == -1){
                    Toast.makeText(PayActivity.this, "请选择记录类型", Toast.LENGTH_SHORT).show();
                    return;
                }
                /*插入记录*/
                ContentValues values = new ContentValues();
                values.put("payclassify_id", classifyId);
                values.put("trade_money", money);
                long id = PayActivity.this.db.insert(values);
                Log.v("info", id+"");
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
            FMUtils.switchActivity(PayActivity.this, MainActivity.class);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
