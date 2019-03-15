package com.breeziness.timetable.data.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * 一定要注意使用Rxjava后，访问数据库会在子线程，不要操作UI
 */
import androidx.annotation.Nullable;

public class DataBaseHelper extends SQLiteOpenHelper {


    private static final String TAG = "DataBaseHelper";
    private Context mContext;

    //建主课程表
    private static final String CREATE_COURSE = "CREATE TABLE course ("
            + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
            + "itemid INTEGER NOT NULL,"
            + "courseid TEXT NOT NULL,"
            + "cname TEXT NOT NULL,"
            + "teachername TEXT NOT NULL,"
            + "term TEXT NOT NULL,"
            + "startweek INTEGER NOT NULL,"
            + "endweek INTEGER NOT NULL,"
            + "week INTEGER NOT NULL)";

    /**
     * @param context
     * @param name    数据库名字
     * @param factory 返回自定义cursor ，一般传null
     * @param version 数据库版本号，更新时需要改大
     */

    public DataBaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_COURSE);
        Log.e(TAG, "onCreate:-------ok---- ");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists course");
        onCreate(db);
    }
}
