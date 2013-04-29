package com.liangge.financesmanager.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.liangge.financesmanager.R;
import com.liangge.financesmanager.utils.ConfigUtils;
import com.liangge.financesmanager.utils.XmlConfig;

/**
 * User: liangge
 * Date: 13-4-29
 * Time: 上午10:52
 */
public class PayClassifyDB extends SQLiteOpenHelper{
    private static final String TABLE_NAME = "fm_pay_classify";
    private XmlConfig xmlConfig;
    private Context context;
    public PayClassifyDB(Context context){
        super(context, TABLE_NAME, null, 1);
        this.context = context;
        this.xmlConfig = ConfigUtils.getSqlConfig(context);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createSql = xmlConfig.getString("payclassify_create");
        sqLiteDatabase.execSQL(createSql);

        String[] payClassifyArray = context.getResources().getStringArray(R.array.payclassify_init);
        String initSql = xmlConfig.getString("payclassify_init");
        for(String classifyName : payClassifyArray){
            sqLiteDatabase.execSQL(initSql, new Object[]{classifyName, 1});
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {
    }

    public Cursor select(){
        SQLiteDatabase db = getReadableDatabase();
        String order = "trade_date desc";
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, order);
        return cursor;
    }
}
