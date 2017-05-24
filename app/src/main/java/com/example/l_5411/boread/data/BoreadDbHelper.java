package com.example.l_5411.boread.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by L_5411 on 2017/3/13.
 */

public class BoreadDbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;

    static final String DATABASE_NAME = "boread.db";

    public BoreadDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_ZHIHU_TABLE = "CREATE TABLE " + BoreadContract.ZhihuEntry.TABLE_NAME + " (" +
                BoreadContract.ZhihuEntry._ID + " INTEGER PRIMARY KEY," +
                BoreadContract.ZhihuEntry.COLUMN_ZHIHU_ID + " INTEGER UNIQUE NOT NULL, " +
                BoreadContract.ZhihuEntry.COLUMN_DATE + " TEXT NOT NULL, " +
                BoreadContract.ZhihuEntry.COLUMN_NEWS + " TEXT NOT NULL, " +
                BoreadContract.ZhihuEntry.COLUMN_CONTENT + " TEXT " +
                " );";
        final String SQL_CREATE_DOUBAN_MOVIE_TABLE = "CREATE TABLE " + BoreadContract.DoubanEntry.TABLE_NAME + " (" +
                BoreadContract.DoubanEntry._ID + " INTEGER PRIMARY KEY," +
                BoreadContract.DoubanEntry.COLUMN_MOVIE_ID + " INTEGER UNIQUE NOT NULL, " +
                BoreadContract.DoubanEntry.COLUMN_MOVIE_SUM + " TEXT NOT NULL, " +
                BoreadContract.DoubanEntry.COLUMN_MOVIE_CONTENT + " TEXT" +
                " );";
        db.execSQL(SQL_CREATE_ZHIHU_TABLE);
        db.execSQL(SQL_CREATE_DOUBAN_MOVIE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + BoreadContract.ZhihuEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + BoreadContract.DoubanEntry.TABLE_NAME);
        onCreate(db);
    }
}
