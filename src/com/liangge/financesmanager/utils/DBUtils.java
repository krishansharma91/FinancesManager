package com.liangge.financesmanager.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * User: liangge
 * Date: 13-4-6
 * Time: 下午6:46
 */
public class DBUtils extends SQLiteOpenHelper{
    private XmlConfig dbConfig;

    public DBUtils(Context context) {
        super(context, "", null, 0);
        this.dbConfig = ConfigUtils.getDbConfig(context);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
