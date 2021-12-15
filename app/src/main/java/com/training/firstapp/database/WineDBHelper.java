package com.training.firstapp.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class WineDBHelper extends SQLiteOpenHelper {

    private final static String CREATE_SQL = "create table bottle(id integer primary key autoincrement not null, title text, year number, price real)";
    private final static String DROP_SQL = "drop table bottle";
    private final static int SCHEMA_VERSION = 1;
    private final static String DB_NAME = "wine.db";

    public WineDBHelper(@Nullable Context context) {
        super(context, DB_NAME, null, SCHEMA_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_SQL);
        db.execSQL(CREATE_SQL);
    }
}
