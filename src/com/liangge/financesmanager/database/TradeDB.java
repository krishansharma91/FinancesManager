package com.liangge.financesmanager.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.liangge.financesmanager.utils.ConfigUtils;
import com.liangge.financesmanager.utils.XmlConfig;

/**
 * User: liangge
 * Date: 13-4-30
 * Time: 下午2:43
 */
public class TradeDB extends SQLiteOpenHelper{
    private static final String TABLE_NAME = "fm_trade";
    private Context context;
    private XmlConfig xmlConfig;
    public TradeDB(Context context){
        super(context, TABLE_NAME, null, 1);
        this.context = context;
        this.xmlConfig = ConfigUtils.getSqlConfig(context);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createSql = xmlConfig.getString("trade_create");
        sqLiteDatabase.execSQL(createSql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {
    }

    public long insert(ContentValues values){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.insert(TABLE_NAME, null, values);
    }
}
