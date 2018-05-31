package com.example.acer.cinema;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

public class DbTableClassificacao implements BaseColumns {
    public static final String TABLE_NAME = "classificacao";
    private static final String FIELD_TYPE = "tipo";

    private SQLiteDatabase db;

    public DbTableClassificacao(SQLiteDatabase db) {
        this.db = db;
    }

    public void create() {
        db.execSQL(
                "CREATE TABLE " + TABLE_NAME + " (" +
                        _ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        FIELD_TYPE + " TEXT NOT NULL" +
                        ")"
        );
    }

    public static ContentValues getContentValues(Classificacao classificacao) {
        ContentValues values = new ContentValues();

        values.put(_ID, classificacao.getId());
        values.put(FIELD_TYPE, classificacao.getName());

        return values;
    }


}
