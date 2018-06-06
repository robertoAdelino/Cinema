package com.example.acer.cinema;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

public class DbTableFilmes implements BaseColumns {

    public static final String TABLE_NAME = "filmes";
    private static final String FIELD_NAME = "nome";
    private static final String FIELD_DATE = "data";
    private static final String FIELD_CLASSIFICATION = "classificacao";

    private SQLiteDatabase db;

    public DbTableFilmes(SQLiteDatabase db) {
        this.db = db;
    }

    public void create() {
        db.execSQL(
                "CREATE TABLE " + TABLE_NAME + " (" +
                        _ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        FIELD_NAME + " TEXT NOT NULL," + FIELD_DATE+ "DATE NOT NULL,"+ FIELD_CLASSIFICATION+"INTEGER NOT NULL"+
                        ")"
        );
    }

    public static ContentValues getContentValues(Filmes filmes) {
        ContentValues values = new ContentValues();

        values.put(_ID, filmes.getId());

        values.put(FIELD_NAME, filmes.getName());

        values.put(FIELD_DATE, filmes.getDate().toString());

        values.put(FIELD_CLASSIFICATION, filmes.getClassificacao());

        return values;
    }

    public long insert(ContentValues values) {
        return db.insert(TABLE_NAME, null, values);
    }


    public int update(ContentValues values, String whereClause, String[] whereArgs) {
              return db.update(TABLE_NAME, values, whereClause, whereArgs);
           }

    public int delete(String whereClause, String[] whereArgs) {
                return db.delete(TABLE_NAME, whereClause, whereArgs);
           }

}
