package com.goodcat.vkclient.application.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper{
    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "GoodCatVKclient.db";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DbBone.CREATE_USER_TABLE);
        db.execSQL(DbBone.CREATE_AUDIO_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+DbBone.USER_TABLE+";");
        db.execSQL("DROP TABLE IF EXISTS "+DbBone.AUDIO_TABLE+";");
        onCreate(db);
    }
}
