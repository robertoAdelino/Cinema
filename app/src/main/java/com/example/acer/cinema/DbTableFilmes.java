package com.example.acer.cinema;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import java.util.Date;

public class DbTableFilmes implements BaseColumns {
    public static final String TABLE_NAME = "Filmes";
    private static final String FIELD_TITLE = "title";
    private static final String FIELD_POINTS = "points";
    private static final String FIELD_ID_CATEGORY = "idCategory";

    public static final String [] ALL_COLUMNS = new String[] { _ID, FIELD_TITLE, FIELD_POINTS, FIELD_ID_CATEGORY };

    private SQLiteDatabase db;

    public DbTableFilmes(SQLiteDatabase db) {
        this.db = db;
    }

    public void create() {
        db.execSQL(
                "CREATE TABLE " + TABLE_NAME + " (" +
                        _ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        FIELD_TITLE + " TEXT NOT NULL," +
                        FIELD_POINTS + " REAL," +
                        FIELD_ID_CATEGORY + " INTEGER," +
                        "FOREIGN KEY (" + FIELD_ID_CATEGORY + ") REFERENCES " +
                        DbTableCategories.TABLE_NAME +
                        "(" + DbTableCategories._ID +")" +
                        ")"
        );
    }

    public static ContentValues getContentValues(Filmes filme) {
        ContentValues values = new ContentValues();

        values.put(FIELD_TITLE, filme.getTitle());
        values.put(FIELD_POINTS, filme.getPoints());
        values.put(FIELD_ID_CATEGORY, filme.getIdCategory());

        return values;
    }

    public static Filmes getCurrentFilmeFromCursor(Cursor cursor) {
        final int posId = cursor.getColumnIndex(_ID);
        final int posTitle = cursor.getColumnIndex(FIELD_TITLE);
        final int posPoints = cursor.getColumnIndex(FIELD_POINTS);
        final int posIdCategory = cursor.getColumnIndex(FIELD_ID_CATEGORY);

        Filmes filme = new Filmes();

        filme.setId(cursor.getInt(posId));
        filme.setTitle(cursor.getString(posTitle));
        filme.setPoints(cursor.getDouble(posPoints));
        filme.setIdCategory(cursor.getInt(posIdCategory));

        return filme;
    }

    /**
     * Convenience method for inserting a row into the categories table.
     *
     * @param values this map contains the initial column values for the
     *            row. The keys should be the column names and the values the
     *            column values
     * @return the row ID of the newly inserted row, or -1 if an error occurred
     */
    public long insert(ContentValues values) {
        return db.insert(TABLE_NAME, null, values);
    }

    /**
     * Convenience method for updating rows in the categories table.
     *
     * @param values a map from column names to new column values. null is a
     *            valid value that will be translated to NULL.
     * @param whereClause the optional WHERE clause to apply when updating.
     *            Passing null will update all rows.
     * @param whereArgs You may include ?s in the where clause, which
     *            will be replaced by the values from whereArgs. The values
     *            will be bound as Strings.
     * @return the number of rows affected
     */
    public int update(ContentValues values, String whereClause, String[] whereArgs) {
        return db.update(TABLE_NAME, values, whereClause, whereArgs);
    }

    /**
     * Convenience method for deleting rows in the categories table.
     *
     * @param whereClause the optional WHERE clause to apply when deleting.
     *            Passing null will delete all rows.
     * @param whereArgs You may include ?s in the where clause, which
     *            will be replaced by the values from whereArgs. The values
     *            will be bound as Strings.
     * @return the number of rows affected if a whereClause is passed in, 0
     *         otherwise. To remove all rows and get a count pass "1" as the
     *         whereClause.
     */
    public int delete(String whereClause, String[] whereArgs) {
        return db.delete(TABLE_NAME, whereClause, whereArgs);
    }

    /**
     * Query the categories table, returning a {@link Cursor} over the result set.
     *
     * @param columns A list of which columns to return. Passing null will
     *            return all columns, which is discouraged to prevent reading
     *            data from storage that isn't going to be used.
     * @param selection A filter declaring which rows to return, formatted as an
     *            SQL WHERE clause (excluding the WHERE itself). Passing null
     *            will return all rows for the given table.
     * @param selectionArgs You may include ?s in selection, which will be
     *         replaced by the values from selectionArgs, in order that they
     *         appear in the selection. The values will be bound as Strings.
     * @param groupBy A filter declaring how to group rows, formatted as an SQL
     *            GROUP BY clause (excluding the GROUP BY itself). Passing null
     *            will cause the rows to not be grouped.
     * @param having A filter declare which row groups to include in the cursor,
     *            if row grouping is being used, formatted as an SQL HAVING
     *            clause (excluding the HAVING itself). Passing null will cause
     *            all row groups to be included, and is required when row
     *            grouping is not being used.
     * @param orderBy How to order the rows, formatted as an SQL ORDER BY clause
     *            (excluding the ORDER BY itself). Passing null will use the
     *            default sort order, which may be unordered.
     * @return A {@link Cursor} object, which is positioned before the first entry. Note that
     * {@link Cursor}s are not synchronized, see the documentation for more details.
     * @see Cursor
     */
    public Cursor query (String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
        Cursor cursor = db.query(TABLE_NAME, columns, selection, selectionArgs, groupBy, having, orderBy);
        return cursor;
    }
}
