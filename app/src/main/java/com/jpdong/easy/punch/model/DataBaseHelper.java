package com.jpdong.easy.punch.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by dong on 2017/4/10.
 */

public class DataBaseHelper extends SQLiteOpenHelper {

    private static DataBaseHelper mInstance;

    public static final String DB_NAME = "easy.db";
    public static final int DB_VERSION = 1;
    public static final String PUNCH_TABLE_NAME = "punchs";
    public static final String PUNCH_TIMES_TABLE_NAME = "punch_times";

    private DataBaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public synchronized static DataBaseHelper getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new DataBaseHelper(context);
        }
        return mInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createPunchTable = String.format("create table %s (id text primary key, title text,duration integer,last_time text)",PUNCH_TABLE_NAME);
        String createPunchTimesTable = String.format("create table %s (id text, punch_time text primary key)", PUNCH_TIMES_TABLE_NAME);
        db.execSQL(createPunchTable);
        db.execSQL(createPunchTimesTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
