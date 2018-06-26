package com.example.acer.cinema;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

public class DbTableClassificacao implements BaseColumns {
    public static final String TABLE_NAME = "classificacao";
    private static final String FIELD_TYPE = "tipo";
    public static final String [] ALL_COLUMNS = new String[] {
            _ID, FIELD_TYPE
    };

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

        values.put(FIELD_TYPE, classificacao.getType());

        return values;
    }


    public static Classificacao getCurrentClassificacaoFromCursor(Cursor cursor) {
        final int posId = cursor.getColumnIndex(_ID);
        final int posType = cursor.getColumnIndex(FIELD_TYPE);


        Classificacao classificacao = new Classificacao();

        classificacao.setId(cursor.getInt(posId));
        classificacao.setType(cursor.getString(posType));

        return classificacao;
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
        Cursor cursor = db.query(TABLE_NAME, columns, selection, selectionArgs, groupBy, having, orderBy);
               return cursor;    }
}
