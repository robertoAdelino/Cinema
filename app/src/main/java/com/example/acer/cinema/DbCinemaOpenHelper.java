package com.example.acer.cinema;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbCinemaOpenHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "cinema.db";
    private static final int DATABASE_VERSION = 1;


    public DbCinemaOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
