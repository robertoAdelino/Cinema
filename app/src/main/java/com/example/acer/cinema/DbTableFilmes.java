package com.example.acer.cinema;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import java.util.Date;

public class DbTableFilmes implements BaseColumns {

    public static final String TABLE_NAME = "filmes";
    private static final String FIELD_NAME = "nome";
    private static final String FIELD_DATE = "data";
    private static final String FIELD_CLASSIFICATION = "classificacao";
    public static final String [] ALL_COLUMNS = new String[] {
            _ID, FIELD_NAME,FIELD_DATE,FIELD_CLASSIFICATION };

    private SQLiteDatabase db;

    public DbTableFilmes(SQLiteDatabase db) {
        this.db = db;
    }

    public void create() {
        db.execSQL(
                "CREATE TABLE " + TABLE_NAME + " (" +
                        _ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        FIELD_NAME + " TEXT NOT NULL," + FIELD_DATE+ "TEXT NOT NULL,"+ FIELD_CLASSIFICATION+"TEXT NOT NULL"+
                        ")"
        );
    }

    public static ContentValues getContentValues(Filmes filmes) {

        ContentValues values = new ContentValues();


        values.put(FIELD_NAME, filmes.getName());

        values.put(FIELD_DATE, filmes.getDate());

        values.put(FIELD_CLASSIFICATION, filmes.getClassificacao());

        return values;
    }


    public static Filmes getCurrentFilmesFromCursor(Cursor cursor) {
        final int posId = cursor.getColumnIndex(_ID);
        final int posName = cursor.getColumnIndex(FIELD_NAME);
        final int posClassificacao = cursor.getColumnIndex(FIELD_CLASSIFICATION);
        final int posDate = cursor.getColumnIndex(FIELD_DATE);

        Filmes filmes = new Filmes();

        filmes.setId(cursor.getInt(posId));
        filmes.setName(cursor.getString(posName));
        filmes.setClassificacao(cursor.getString(posClassificacao));
        filmes.setDate(cursor.getString(posDate));

        return filmes;
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

    public Cursor query (String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
               return db.query(TABLE_NAME, columns, selection, selectionArgs, groupBy, having, orderBy);
           }


}
