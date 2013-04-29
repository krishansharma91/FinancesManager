package com.liangge.financesmanager.activity;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import com.liangge.financesmanager.R;
import com.liangge.financesmanager.database.PayClassifyDB;

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
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay);
        /*设置网格数据*/
        GridView gridView = (GridView)findViewById(R.id.pay_gridview);
        PayClassifyDB payClassifyDB = new PayClassifyDB(this);
        List<Map<String,Object>> gridItemList = new ArrayList<Map<String,Object>>();
        Cursor cursor = payClassifyDB.select();
        for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){
            Map<String,Object> map = new HashMap<String,Object>();
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

//        EditText payMoney = (EditText)findViewById(R.id.pay_money);
//        payMoney.addTextChangedListener(new PayEditTextListener(payMoney));
    }
}
